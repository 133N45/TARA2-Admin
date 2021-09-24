/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (3.3.4).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package ee.ria.tara.api;

import ee.ria.tara.model.LoginRequest;
import ee.ria.tara.model.LoginResponse;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Validated
@Api(value = "login", description = "the login API")
public interface LoginApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    @ApiOperation(value = "Logs user into the system", nickname = "loginUser", notes = "Logs user into the system. Returns a session cookie if login is successful or HTTP 401 on failure", response = LoginResponse.class, authorizations = {
        @Authorization(value = "cookieAuth")
    }, tags={ "user", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = LoginResponse.class),
        @ApiResponse(code = 401, message = "Invalid username/password supplied") })
    @RequestMapping(value = "/login",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<LoginResponse> loginUser(@ApiParam(value = "Authentication request object" ,required=true )  @Valid @RequestBody LoginRequest loginRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "{  \"error\" : \"error\",  \"username\" : \"username\"}");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
