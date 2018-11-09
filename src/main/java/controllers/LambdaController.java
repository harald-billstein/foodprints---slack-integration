package controllers;

import services.FeedbackService;
import services.FoodService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import services.CommandService;
import model.IncomingRequest;
import model.response.Response;
import model.response.ResponseMessage;

public class LambdaController implements RequestHandler<IncomingRequest, String> {

  private FoodService foodService = new FoodService();
  private FeedbackService feedbackService = new FeedbackService();
  private CommandService commandService = new CommandService();

  @Override
  public String handleRequest(IncomingRequest incomingRequest, Context context) {
    executeRequest(incomingRequest);

    // AWS Lambda sometimes take longer then 3 sec. to execute the request. AWS api gateway returns
    // 200 directly. The response to slack is made by an URL delivered in the incomingRequest.
    // Threads wont work, so yeah, null.
    return null;
  }

  private void executeRequest(IncomingRequest incomingRequest) {

    if (foodService.validateVote(incomingRequest.getText())) {
      System.out.println("LOGGING FOOD");
      ResponseMessage responseMessage = foodService.registerProtein(incomingRequest);
      Response response = feedbackService.generateResponse(responseMessage);
      feedbackService.send(incomingRequest,response);

    } else if (commandService.validateCommand(incomingRequest.getText())) {
      System.out.println("EXECUTING COMMANDS");
      ResponseMessage responseMessage = commandService.execute(incomingRequest);
      Response response = feedbackService.generateResponse(responseMessage);
      feedbackService.send(incomingRequest, response);

    } else {
      System.out.println("DID NOT MATCH ANY ACTIONS");
      ResponseMessage responseMessage = new ResponseMessage();
      responseMessage.setMessage("I beg your pardon?" + incomingRequest.getText());
      responseMessage.setSubMessage("for help typ /foodprints -help");
      feedbackService.send(incomingRequest, feedbackService.generateResponse(responseMessage));
    }
  }
}
