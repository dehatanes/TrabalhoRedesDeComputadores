package ServerPackage;

import Models.Constants;
import Models.Request;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    String name;
    boolean isloggedin;

    final ObjectInputStream ois;
    final ObjectOutputStream oos;
    private Socket s;

    ////////////////////
    // SETUP HANDLER 
    ////////////////////

    public ClientHandler(Socket s, ObjectInputStream ois, ObjectOutputStream oos) {
        this.isloggedin = true;
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
        while (isloggedin) {
            try {
                received = (Request) ois.readObject();
                // PREPARE RESPONSE
                Request requestToSend = new Request();
                switch (received.status) {
                    case Constants.STATUS_NEW_CLIENT:
                        System.out.println("\nSTATUS_NEW_CLIENT player: " + received.username);
                        if(Server.addNewClient(received.username, this)){ 
                            // sucesso ao adicionar cliente
                            this.name = received.username;
                            requestToSend.status = Constants.STATUS_CLIENT_CREATED;
                        } else { // usuario ja em uso
                            requestToSend.status = Constants.STATUS_USERNAME_UNAVAILABLE;
                        }
                        break;
                    case Constants.STATUS_LIST_GAMES:
                        System.out.println("\nSTATUS_LIST_GAMES player: " + received.username);
                        // todo change
                        requestToSend.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_NEW_MULT_GAME:
                        System.out.println("\nSTATUS_NEW_MULT_GAME player: " + received.username);
                        // todo change
                        requestToSend.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_NEW_SINGL_GAME:
                        System.out.println("\nSTATUS_NEW_SINGL_GAME player: " + received.username);
                        // todo change
                        requestToSend.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_NEXT_TURN:
                        System.out.println("\nSTATUS_NEXT_TURN player: " + received.username);
                        // todo change
                        requestToSend.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_GAME_OVER:
                        System.out.println("\nSTATUS_GAME_OVER player: " + received.username);
                        // todo change
                        requestToSend.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_EXISTING_GAME:
                        System.out.println("\nSTATUS_EXISTING_GAME player: " + received.username);
                        // todo change
                        requestToSend.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_CLIENT_LOGOUT:
                        System.out.println("\nSTATUS_CLIENT_LOGOUT player: " + received.username);
                        logout();
                        break;
                }
                // SEND RESPONSE
                if (requestToSend.status != Constants.UNKNOWN_STATUS && requestToSend.status != Constants.STATUS_ERRO) {
                    sendRequest(requestToSend);
                }

            } catch (IOException e) {
                if(isloggedin) {
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

    private void sendRequest(Request r) {
        try {
            // complete response
            r.username = this.name;
            // send response
            this.oos.writeObject(r);
            this.oos.flush();
        } catch (IOException e) {
            // pass
        }
    }

    // LOGOUT

    private void logout() {
        try {
            this.isloggedin = false;
            Server.removeClient(this.name);
            this.ois.close();
            this.oos.close();
            this.s.close();
        } catch (IOException e) {
            System.out.println("Erro na hora de fechar o cliente: " + this.name);
            e.printStackTrace();
            System.out.println();
        }
    }
}