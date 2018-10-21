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
                this.logout();
                break;
            }
            try {
                received = (Request) ois.readObject();
                Request send = new Request();
                System.out.println("Received something");
                switch (received.status) {
                    case Constants.STATUS_NEW_CLIENT:
                        if (isUsernameAlreadyInUse(received.username)) {
                            send.status = Constants.STATUS_USERNAME_UNAVAILABLE;
                        } else {
                            this.name = received.username;
                            send.status = Constants.STATUS_CLIENT_CREATED;
                        }
                        break;
                    case Constants.STATUS_LIST_GAMES:
                        System.out.println("List games");
                        break;
                    case Constants.STATUS_NEW_MULT_GAME:
                        System.out.println("Multi game começado");
                        break;
                    case Constants.STATUS_NEW_SINGL_GAME:
                        System.out.println("STATUS_NEW_SINGL_GAME");
                        break;
                    case Constants.STATUS_NEXT_TURN:
                        System.out.println("STATUS_NEXT_TURN");
                        break;
                    case Constants.STATUS_GAME_OVER:
                        System.out.println("STATUS_GAME_OVER");
                        break;
                    case Constants.STATUS_WATCH:
                        System.out.println("STATUS_WATCH");
                        break;
                    case Constants.STATUS_CLIENT_LOGOUT:
                        System.out.println("STATUS_CLIENT_LOGOUT");
                        this.logout();
                        break;
                    default:
                        // todo remove -> just for test
                        this.name = received.username;
                        send.status = Constants.STATUS_CLIENT_CREATED;
                        break;
                }
                if (send.status != Constants.UNKNOWN_STATUS && send.status != Constants.STATUS_ERRO) {
                    oos.writeObject(send);
                    oos.flush();
                }

            } catch (IOException e) {
                this.logout();
                break;
            } catch (ClassNotFoundException e) {

            }
        }
    }

    public void logout() {
        try {
            ClientHandler mc = this.findClientHandlerInServer(this.name);
            if (mc != null) {
                mc.isloggedin = false;
                mc.ois.close();
                mc.oos.close();
                mc.s.close();
                Server.ar.remove(mc);
            } else {
                System.out.println("Não encontrou o client handler");
            }
        } catch (IOException e) {
            System.out.println("Deu erro na hora de fechar alguma coisa");
        }
    }

    private ClientHandler findClientHandlerInServer(String username) {
        for (ClientHandler mc : Server.ar) {
            if (mc.name.equals(username) && mc.isloggedin == true) {
                return mc;
            }
        }
        return null;
    }

    private boolean isUsernameAlreadyInUse(String username) {
        ClientHandler mc = this.findClientHandlerInServer(username);
        if (mc == null) {
            return false;
        }
        return true;
    }

    private void sendRequest(Request r) {
        try {
            this.oos.writeObject(r);
            this.oos.flush();
        } catch (IOException e) {

        }
    }
}