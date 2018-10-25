package ServerPackage;

import Models.Constants;
import Models.Request;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    String name;
    boolean isActiveSession;

    final ObjectInputStream ois;
    final ObjectOutputStream oos;
    private Socket s;

    ////////////////////
    // SETUP HANDLER 
    ////////////////////

    public ClientHandler(Socket s, ObjectInputStream ois, ObjectOutputStream oos) {
        this.isActiveSession = true;
        this.ois = ois;
        this.oos = oos;
        this.s = s;
    }

    ////////////////////
    // CLIENT HANDLER 
    ////////////////////

    @Override
    public void run() {
        Request received;
        while (isActiveSession) {
            try {

                received = (Request) ois.readObject();
                Request response = new Request();
                
                // HANDLE LOGIN
                if(!checkIfUserIsLogged()) {
                    if(received.status == Constants.STATUS_NEW_CLIENT) {
                        response.status = tryToPerformLogin(received);
                        sendRequest(response);
                    }
                    continue;
                } 
                
                // VERIFY MESSAGE AUTH
                if(!authVerificationCheck(received)){
                    response.status = Constants.UNAUTHORIZED_STATUS;
                    sendRequest(response);
                    continue;
                }

                // HANDLE VALID MESSAGES
                switch (received.status) {
                    case Constants.STATUS_LIST_GAMES:
                        System.out.println("\nSTATUS_LIST_GAMES player: " + received.username);
                        // todo change
                        response.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_NEW_MULT_GAME:
                        System.out.println("\nSTATUS_NEW_MULT_GAME player: " + received.username);
                        // todo change
                        response.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_NEW_SINGL_GAME:
                        System.out.println("\nSTATUS_NEW_SINGL_GAME player: " + received.username);
                        // todo change
                        response.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_NEXT_TURN:
                        System.out.println("\nSTATUS_NEXT_TURN player: " + received.username);
                        // todo change
                        response.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_GAME_OVER:
                        System.out.println("\nSTATUS_GAME_OVER player: " + received.username);
                        // todo change
                        response.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_EXISTING_GAME:
                        System.out.println("\nSTATUS_EXISTING_GAME player: " + received.username);
                        // todo change
                        response.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_CLIENT_LOGOUT:
                        System.out.println("\nSTATUS_CLIENT_LOGOUT player: " + received.username);
                        logout();
                        break;
                }
                // SEND RESPONSE
                if (response.status != Constants.UNKNOWN_STATUS && response.status != Constants.STATUS_ERRO) {
                    sendRequest(response);
                }

            } catch (IOException e) {
                if(isActiveSession) {
                    logout();
                }
                break;
            } catch (ClassNotFoundException e) {

            }
        }
    }

    /////////////////////
    // RESPONSE METHODS
    /////////////////////

    // GENERAL PURPOSES

    private boolean checkIfUserIsLogged() {
        if(this.name == null) {
            return false;
        }
        return true;
    }

    private boolean authVerificationCheck(Request receivedRequest){
        if(this.name != null && this.name.equals(receivedRequest.username)) {
            return true;
        }
        return false;
    }

    private Request requestAuthenticator(Request request) {
        request.username = this.name;
        return request;
    }

    private void sendRequest(Request response) {
        try {
            response  = requestAuthenticator(response);
            // send response
            this.oos.writeObject(response);
            this.oos.flush();
        } catch (IOException e) {
            // pass
        }
    }

    // LOGIN

    private int tryToPerformLogin(Request receivedRequest) {
        System.out.println("\nSTATUS_NEW_CLIENT");
        // Perform login
        boolean successOnLogin = Server.loginNewClient(receivedRequest.username, this); 
        if(successOnLogin){
            // update session
            this.name = receivedRequest.username;
            return Constants.STATUS_CLIENT_CREATED;
        } else { 
            return Constants.STATUS_USERNAME_UNAVAILABLE;
        }
    }

    // LOGOUT

    private void logout() {
        try {
            Server.removeClient(this.name);
            this.ois.close();
            this.oos.close();
            this.s.close();
            this.name = null;
            this.isActiveSession = false;
        } catch (IOException e) {
            System.out.println("Error closing client: " + this.name);
            e.printStackTrace();
            System.out.println();
        }
    }
}