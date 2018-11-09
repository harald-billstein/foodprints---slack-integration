package services;

import com.google.gson.Gson;
import java.io.DataOutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import model.IncomingRequest;
import model.Protein;
import model.response.ResponseMessage;

public class FoodService {

  public ResponseMessage registerProtein(IncomingRequest incomingRequest) {
    System.out.println("FoodService - registering Protein");

    ResponseMessage responseMessage = new ResponseMessage();
    try {
      URL url = new URL("https://foodprints.info/v1/votes/");
      HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
      httpsURLConnection.setRequestMethod("POST");
      httpsURLConnection.setDoOutput(true);
      httpsURLConnection.setRequestProperty("Content-Type", "application/json");

      Protein protein = new Protein();
      protein.setVote(incomingRequest.getText());

      DataOutputStream outputStream = new DataOutputStream(httpsURLConnection.getOutputStream());
      outputStream.writeBytes(new Gson().toJson(protein));
      System.out.println(httpsURLConnection.getResponseCode());

      httpsURLConnection.disconnect();
      outputStream.close();

      responseMessage.setMessage("\"" + incomingRequest.getText().toLowerCase() + "\"" + " Registered");
      responseMessage.setSubMessage("Thank you - " + incomingRequest.getUser_name());
      System.out.println("FoodService - registering Protein complete");

    } catch (Exception e) {
      System.out.println("FoodService - registering Protein failure " + e.getMessage());
    }
    return responseMessage;
  }

  public boolean validateVote(String vote) {
    System.out.println("FoodService - validateVote");

    return vote.equalsIgnoreCase("BEEF") ||
        vote.equalsIgnoreCase("PORK") ||
        vote.equalsIgnoreCase("CHICKEN") ||
        vote.equalsIgnoreCase("FISH") ||
        vote.equalsIgnoreCase("VEGETARIAN") ||
        vote.equalsIgnoreCase("VEGAN");
  }
}
