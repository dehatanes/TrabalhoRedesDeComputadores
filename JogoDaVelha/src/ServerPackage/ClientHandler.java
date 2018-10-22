package ServerPackage;

import Models.Constants;
import Models.Request;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    public String name;
    final ObjectInputStream ois;
    final ObjectOutputStream oos;
    Socket s;
    boolean isloggedin;

    public ClientHandler(Socket s, ObjectInputStream ois, ObjectOutputStream oos) {
        this.ois = ois;
        this.oos = oos;
        this.s = s;
        this.isloggedin = true;
    }

    @Override
    public void run() {
        Request received;
        while (true) {
            if (!this.isloggedin) {
                break;
            }
            try {
                received = (Request) ois.readObject();
                Request send = new Request();
                switch (received.status) {
                    case Constants.STATUS_NEW_CLIENT:
                        if(Server.addNewClient(received.username, this)){ // sucesso ao adicionar cliente
                            this.name = received.username;
                            send.status = Constants.STATUS_CLIENT_CREATED;
                        } else { // usuario ja em uso
                            send.status = Constants.STATUS_USERNAME_UNAVAILABLE;
                        }
                        break;
                    case Constants.STATUS_LIST_GAMES:
                        System.out.println("\nSTATUS_LIST_GAMES player: " + received.username);
                        // todo change
                        this.name = received.username;
                        send.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_NEW_MULT_GAME:
                        System.out.println("\nSTATUS_NEW_MULT_GAME player: " + received.username);
                        // todo change
                        this.name = received.username;
                        send.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_NEW_SINGL_GAME:
                        System.out.println("\nSTATUS_NEW_SINGL_GAME player: " + received.username);
                        // todo change
                        this.name = received.username;
                        send.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_NEXT_TURN:
                        System.out.println("\nSTATUS_NEXT_TURN player: " + received.username);
                        // todo change
                        this.name = received.username;
                        send.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_GAME_OVER:
                        System.out.println("\nSTATUS_GAME_OVER player: " + received.username);
                        // todo change
                        this.name = received.username;
                        send.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_WATCH:
                        System.out.println("\nSTATUS_WATCH player: " + received.username);
                        // todo change
                        this.name = received.username;
                        send.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                    case Constants.STATUS_CLIENT_LOGOUT:
                        System.out.println("\nSTATUS_CLIENT_LOGOUT player: " + received.username);
                        this.logout();
                        break;
                }
                if (send.status != Constants.UNKNOWN_STATUS && send.status != Constants.STATUS_ERRO) {
                    oos.writeObject(send);
                    oos.flush();
                }

            } catch (IOException e) {
                if(this.isloggedin) {
                    this.logout();
                }
                break;
            } catch (ClassNotFoundException e) {

            }
        }
    }

    public void logout() {
        try {
            if(Server.removeClient(this.name)) {
                this.isloggedin = false;
                this.ois.close();
                this.oos.close();
                this.s.close();
            } else {
                this.isloggedin = false;
            }
        } catch (IOException e) {
            System.out.println("Erro na hora de fechar o cliente: " + this.name);
            e.printStackTrace();
            System.out.println();
        }
    }

    private void sendRequest(Request r) {
        try {
            this.oos.writeObject(r);
            this.oos.flush();
        } catch (IOException e) {

        }
    }
}