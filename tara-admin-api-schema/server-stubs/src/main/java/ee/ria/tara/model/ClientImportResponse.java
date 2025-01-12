package ee.ria.tara.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ClientImportResponse
 */

public class ClientImportResponse   {
  @JsonProperty("status")
  private String status;

  @JsonProperty("clients_count")
  private Integer clientsCount;

  @JsonProperty("clients_import_success_count")
  private Integer clientsImportSuccessCount;

  @JsonProperty("clients_import_failed_count")
  private Integer clientsImportFailedCount;

  @JsonProperty("clients_not_imported")
  @Valid
  private List<String> clientsNotImported = null;

  public ClientImportResponse status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @ApiModelProperty(value = "")


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public ClientImportResponse clientsCount(Integer clientsCount) {
    this.clientsCount = clientsCount;
    return this;
  }

  /**
   * Get clientsCount
   * @return clientsCount
  */
  @ApiModelProperty(value = "")


  public Integer getClientsCount() {
    return clientsCount;
  }

  public void setClientsCount(Integer clientsCount) {
    this.clientsCount = clientsCount;
  }

  public ClientImportResponse clientsImportSuccessCount(Integer clientsImportSuccessCount) {
    this.clientsImportSuccessCount = clientsImportSuccessCount;
    return this;
  }

  /**
   * Get clientsImportSuccessCount
   * @return clientsImportSuccessCount
  */
  @ApiModelProperty(value = "")


  public Integer getClientsImportSuccessCount() {
    return clientsImportSuccessCount;
  }

  public void setClientsImportSuccessCount(Integer clientsImportSuccessCount) {
    this.clientsImportSuccessCount = clientsImportSuccessCount;
  }

  public ClientImportResponse clientsImportFailedCount(Integer clientsImportFailedCount) {
    this.clientsImportFailedCount = clientsImportFailedCount;
    return this;
  }

  /**
   * Get clientsImportFailedCount
   * @return clientsImportFailedCount
  */
  @ApiModelProperty(value = "")


  public Integer getClientsImportFailedCount() {
    return clientsImportFailedCount;
  }

  public void setClientsImportFailedCount(Integer clientsImportFailedCount) {
    this.clientsImportFailedCount = clientsImportFailedCount;
  }

  public ClientImportResponse clientsNotImported(List<String> clientsNotImported) {
    this.clientsNotImported = clientsNotImported;
    return this;
  }

  public ClientImportResponse addClientsNotImportedItem(String clientsNotImportedItem) {
    if (this.clientsNotImported == null) {
      this.clientsNotImported = new ArrayList<>();
    }
    this.clientsNotImported.add(clientsNotImportedItem);
    return this;
  }

  /**
   * Get clientsNotImported
   * @return clientsNotImported
  */
  @ApiModelProperty(value = "")


  public List<String> getClientsNotImported() {
    return clientsNotImported;
  }

  public void setClientsNotImported(List<String> clientsNotImported) {
    this.clientsNotImported = clientsNotImported;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientImportResponse clientImportResponse = (ClientImportResponse) o;
    return Objects.equals(this.status, clientImportResponse.status) &&
        Objects.equals(this.clientsCount, clientImportResponse.clientsCount) &&
        Objects.equals(this.clientsImportSuccessCount, clientImportResponse.clientsImportSuccessCount) &&
        Objects.equals(this.clientsImportFailedCount, clientImportResponse.clientsImportFailedCount) &&
        Objects.equals(this.clientsNotImported, clientImportResponse.clientsNotImported);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, clientsCount, clientsImportSuccessCount, clientsImportFailedCount, clientsNotImported);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClientImportResponse {\n");
    
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    clientsCount: ").append(toIndentedString(clientsCount)).append("\n");
    sb.append("    clientsImportSuccessCount: ").append(toIndentedString(clientsImportSuccessCount)).append("\n");
    sb.append("    clientsImportFailedCount: ").append(toIndentedString(clientsImportFailedCount)).append("\n");
    sb.append("    clientsNotImported: ").append(toIndentedString(clientsNotImported)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

