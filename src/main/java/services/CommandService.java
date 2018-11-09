package services;

import model.IncomingRequest;
import model.response.ResponseMessage;

public class CommandService {


  public ResponseMessage execute(IncomingRequest incomingRequest) {

    ResponseMessage responseMessage;

    if (incomingRequest.getText().equalsIgnoreCase("-help")) {
      responseMessage = getHelpMessage();
    } else {
      throw new RuntimeException("CommandService Execute exception - Error - 12");
    }

    return responseMessage;
  }

  public boolean validateCommand(String command) {
    return command.equalsIgnoreCase("-help");
  }

  private ResponseMessage getHelpMessage() {
    ResponseMessage responseMessage = new ResponseMessage();
    responseMessage.setMessage("Available commands: ");
    responseMessage.setSubMessage("/foodprints [protein] \n"
        + "/foodprints -help");
    return responseMessage;
  }
}
