package ee.ria.tara;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import ee.ria.tara.repository.ClientRepository;
import ee.ria.tara.repository.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(classes = ClientUtilsApplication.class, webEnvironment = NONE)
@ContextConfiguration(initializers = {ImportClientFromJsonFileTest.MockOidcServiceInitializer.class})
@TestPropertySource(
        properties = {
                "spring.profiles.active=importFromFile",
                "file-import.file-name=src/test/resources/import_files/mock-client.json",
                "tara-oidc.url=http://localhost:3779",
                "auth.tls-truststore-path=file:../tara-admin-api/src/main/resources/tls-truststore.p12",
                "auth.tls-truststore-password=changeit",
                "spring.datasource.continue-on-error=false",
                "spring.datasource.platform=h2",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
                "spring.datasource.username=sa",
                "spring.datasource.password=",
                "spring.liquibase.enabled=true",
                "spring.liquibase.parameters.admin-service-user-name=h2"
        })
public class ImportClientFromJsonFileTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WireMockServer wireMockServer;

    @Test
    public void importClient() {
        List<Client> clients = clientRepository.findAll();
        Assertions.assertEquals(1, clients.size());
        Assertions.assertEquals("mock_client_id", clients.get(0).getClientId());
        Assertions.assertEquals("Example Institution", clients.get(0).getInstitution().getName());
        wireMockServer.verify(getRequestedFor(urlEqualTo("/clients/mock_client_id")));
        wireMockServer.verify(putRequestedFor(urlEqualTo("/clients/mock_client_id"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"client_id\" : \"mock_client_id\",\n" +
                        "  \"client_name\" : \"name_et\",\n" +
                        "  \"client_secret\" : \"2bb80d537b1da3e38bd30361aa855686bde0eacd7162fef6a25fe97bf527a25b\",\n" +
                        "  \"grant_types\" : null,\n" +
                        "  \"redirect_uris\" : [ \"https://back.ee\" ],\n" +
                        "  \"response_types\" : [ \"code\" ],\n" +
                        "  \"scope\" : \"openid idcard mid smartid eidas eidasonly eidas:country:* email phone\",\n" +
                        "  \"subject_type\" : \"public\",\n" +
                        "  \"token_endpoint_auth_method\" : \"client_secret_basic\",\n" +
                        "  \"backchannel_logout_uri\" : null,\n" +
                        "  \"metadata\" : {\n" +
                        "    \"display_user_consent\" : false,\n" +
                        "    \"oidc_client\" : {\n" +
                        "      \"name\" : \"name_et\",\n" +
                        "      \"name_translations\" : {\n" +
                        "        \"et\" : \"name_et\",\n" +
                        "        \"en\" : null,\n" +
                        "        \"ru\" : null\n" +
                        "      },\n" +
                        "      \"short_name\" : \"sn_et\",\n" +
                        "      \"short_name_translations\" : {\n" +
                        "        \"et\" : \"sn_et\",\n" +
                        "        \"en\" : null,\n" +
                        "        \"ru\" : null\n" +
                        "      },\n" +
                        "      \"legacy_return_url\" : null,\n" +
                        "      \"institution\" : {\n" +
                        "        \"registry_code\" : \"12345678\",\n" +
                        "        \"sector\" : \"public\"\n" +
                        "      },\n" +
                        "      \"mid_settings\" : null,\n" +
                        "      \"smartid_settings\" : {\n" +
                        "        \"relying_party_UUID\" : null,\n" +
                        "        \"relying_party_name\" : null,\n" +
                        "        \"should_use_additional_verification_code_check\" : true\n" +
                        "      }\n" +
                        "    }\n" +
                        "  },\n" +
                        "  \"created_at\" : null,\n" +
                        "  \"updated_at\" : null\n" +
                        "}"))
        );
    }

    public static class MockOidcServiceInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().port(3779));
            wireMockServer.start();

            configurableApplicationContext
                    .getBeanFactory()
                    .registerSingleton("wireMockServer", wireMockServer);

            configurableApplicationContext.addApplicationListener(applicationEvent -> {
                if (applicationEvent instanceof ContextClosedEvent) {
                    wireMockServer.stop();
                }
            });

            wireMockServer.stubFor(
                    WireMock.post("/clients")
                            .willReturn(WireMock.aResponse())
            );
            wireMockServer.stubFor(
                    WireMock.get("/clients/mock_client_id")
                            .willReturn(WireMock.aResponse())
            );
            wireMockServer.stubFor(
                    WireMock.put("/clients/mock_client_id")
                            .willReturn(WireMock.aResponse())
            );
        }
    }
}
