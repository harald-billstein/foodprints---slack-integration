package model.response;

import java.util.List;

public class Response {

  private String response_type;
  private String text;
  private List<ResponseAttachment> attachments;

  public Response() {
  }

  public String getResponse_type() {
    return response_type;
  }

  public void setResponse_type(String response_type) {
    this.response_type = response_type;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public List<ResponseAttachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<ResponseAttachment> attachments) {
    this.attachments = attachments;
  }
}
