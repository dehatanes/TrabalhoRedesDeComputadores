package ClientPackage;

import ClientPackage.UserInterface.Navigator;
import Models.Constants;
import Models.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private static String hostName;
    private static String myPlayerName;

    private static ObjectOutputStream oos = null;
    private static ObjectInputStream ois = null;

    public static void main(String[] args) throws UnknownHostException, IOException {
        //////////////////////////////
        // SETUP CLIENT APPLICATION
        //////////////////////////////

        // if no hostname is provided, quit
        if (args.length == 0) {
            Navigator.goToErrorGettingIp();
            System.exit(1);
        }

        /////////////////////////////
        // START CLIENT APPLICATION
        /////////////////////////////

        // setup host communication
        try {
            hostName = args[0];
            InetAddress ip = InetAddress.getByName(hostName);
            // setup socket
            Socket s = new Socket(ip, Constants.SERVER_SOCKET);
            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
        } catch (IOException e) {
            Navigator.goToErrorConnectingWithServer();
            System.exit(0);
        }

        // get user infos and login
        getNewUser();
        
        // setup server messages handler
        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // get the server message
                        Request r = (Request) ois.readObject();
                        // handle the server message
                        switch (r.status) {
                            case Constants.STATUS_CLIENT_CREATED:
                                myPlayerName = r.username;
                                getUserMenuOption();
                                break;
                            case Constants.STATUS_USERNAME_UNAVAILABLE:
                                tryAgainGetNewUser();
                                break;
                            default:
                                // todo remove it when all STATUS are mapped
                                System.out.println("MENSAGEM_RECEBIDA: Status: " + r.status);
                                System.out.println("Nao sei como lidar.");
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        // pass
                    }
                }
            }
        });
        readMessage.start();
    }

    //////////////////////////
    // HANDLER METHODS
    //////////////////////////

    // GENERAL PURPOSES

    private static void sendMessageToServer(Request r) throws IOException {
        oos.writeObject(r);
        oos.flush();
        startLoadingScreen();
    }

    private static void startLoadingScreen() {
        // START LOADING SCREEN
    }

    // LOGIN

    private static void getNewUser() throws IOException {
        String username = Navigator.goToStartAndGetNewClient();
        createUser(username);
    }

    private static void tryAgainGetNewUser() throws IOException {
        String username = Navigator.reenterUsername();
        createUser(username);
    }

    private static void createUser(String username) throws IOException {
        Request r = new Request();
        r.status = Constants.STATUS_NEW_CLIENT;
        r.username = username;
        sendMessageToServer(r);
    }

    // MENU

    private static void getUserMenuOption() throws IOException {
        Request r = new Request();
        r.username = myPlayerName;
        int optionSelected = Navigator.goToMenu(myPlayerName);
        switch (optionSelected) {
            case 1:
                r.status = Constants.STATUS_EXISTING_GAME;
                break;
            case 2:
                r.status = Constants.STATUS_NEW_MULT_GAME;
                break;
            case 3:
                r.status = Constants.STATUS_NEW_SINGL_GAME;
                break;
            default:
                logoutFromServer();
                System.exit(0);
                break;
        }
        sendMessageToServer(r);
    }

    // LOGOUT   

    private static void logoutFromServer() throws IOException {
        Request r = new Request();
        r.status = Constants.STATUS_CLIENT_LOGOUT;
        r.username = myPlayerName;
        sendMessageToServer(r);
    }
}
