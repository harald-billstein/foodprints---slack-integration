package services;

import com.google.gson.Gson;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import model.IncomingRequest;
import model.response.Response;
import model.response.ResponseAttachment;
import model.response.ResponseMessage;

public class FeedbackService {

  public void send(IncomingRequest incomingRequest, Response response) {
    System.out.println("FeedbackService - sending");

    try {
      URL url = new URL(incomingRequest.getResponse_url());
      HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
      httpsURLConnection.setRequestMethod("POST");
      httpsURLConnection.setDoOutput(true);
      httpsURLConnection.setRequestProperty("Content-Type", "application/json");

      DataOutputStream outputStream = new DataOutputStream(httpsURLConnection.getOutputStream());

      outputStream.writeBytes(new Gson().toJson(response));
      System.out.println(incomingRequest.getResponse_url());
      System.out.println(httpsURLConnection.getResponseCode());
      outputStream.close();
      System.out.println("FeedbackService - sending complete");

    } catch (IOException e) {
      System.out.println("FeedbackService - sending failure " + e.getMessage());
      e.printStackTrace();
    }
  }

  public Response generateResponse(ResponseMessage responseMessage) {
    System.out.println("FeedbackService - generateResponse");
    Response response = new Response();
    response.setResponse_type("ephemeral");
    response.setText(responseMessage.getMessage());
    List<ResponseAttachment> responseAttachments = new ArrayList<>();
    ResponseAttachment responseAttachment = new ResponseAttachment();
    responseAttachment.setText(responseMessage.getSubMessage());
    responseAttachments.add(responseAttachment);
    response.setAttachments(responseAttachments);

    return response;
  }

}
