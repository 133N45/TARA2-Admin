package ee.ria.tara.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ee.ria.tara.configuration.providers.AdminConfigurationProvider;
import ee.ria.tara.controllers.handler.ErrorHandler;
import ee.ria.tara.model.Alert;
import ee.ria.tara.model.EmailAlert;
import ee.ria.tara.model.LoginAlert;
import ee.ria.tara.service.AlertsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(MockitoExtension.class)
public class AlertsControllerTest {
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    private JacksonTester<Alert> jsonAlert;
    private Alert alert;
    @Mock
    private MessageSource messageSource;
    @Mock
    private ErrorHandler errorHandler;
    @Mock
    private HttpServletRequest request;
    @Mock
    private AlertsService service;
    @Mock
    private AdminConfigurationProvider adminConfigurationProvider;

    @InjectMocks
    private AlertsController controller;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(errorHandler, "messageSource", messageSource);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        alert = createAlert();

        JacksonTester.initFields(this, objectMapper);

        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(errorHandler)
                .build();
    }


    @Test
    public void testGetAllAlerts() throws Exception {
        List<Alert> alertList = List.of(alert);

        doReturn(alertList).when(service).getAlerts();

        MockHttpServletResponse response = mvc.perform(
                get("/alerts")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertTrue(response.getContentAsString().contains(jsonAlert.write(alert).getJson()));
        verify(service, times(1)).getAlerts();
    }

    @Test
    public void testAddAlert() throws Exception {
        doReturn(alert).when(service).addAlert(any());

        MockHttpServletResponse response = mvc.perform(
                post("/alerts")
                        .content(jsonAlert.write(alert).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
        verify(service, times(1)).addAlert(any());
    }

    @Test
    public void testUpdateAlert() throws Exception {
        doReturn(alert).when(service).updateAlert(any());

        MockHttpServletResponse response = mvc.perform(
                put(String.format("/alerts/%s", alert.getId()))
                        .content(jsonAlert.write(alert).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
        verify(service, times(1)).updateAlert(any());
    }

    @Test
    public void testDeleteAlert() throws Exception {
        doNothing().when(service).deleteAlert(anyString());

        MockHttpServletResponse response = mvc.perform(
                delete(String.format("/alerts/%s", alert.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
        verify(service, times(1)).deleteAlert(any());
    }


    private Alert createAlert() {
        Alert alert = new Alert();

        alert.setTitle("Title");
        alert.setEmailAlert(new EmailAlert());
        LoginAlert loginAlert = new LoginAlert();
        loginAlert.setAuthMethods(List.of("AuthMethod"));
        alert.setLoginAlert(loginAlert);

        alert.setCreatedAt(OffsetDateTime.now());
        alert.setUpdatedAt(OffsetDateTime.now());
        alert.setEndTime(OffsetDateTime.now());
        alert.setStartTime(OffsetDateTime.now());

        return alert;
    }
}
