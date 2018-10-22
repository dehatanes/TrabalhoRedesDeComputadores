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
        //////////////////////////
        // SETUP APPLICATION
        //////////////////////////

        // if no hostname is provided, quit
        if (args.length == 0) {
            System.out.println("User did not enter a host name. Client program exiting.");
            System.exit(1);
        }
        // setup host communication
        hostName = args[0];
        InetAddress ip = InetAddress.getByName(hostName);

        //////////////////////////
        // START APPLICATION
        //////////////////////////

        Socket s = new Socket(ip, Constants.SERVER_SOCKET);
        oos = new ObjectOutputStream(s.getOutputStream());
        ois = new ObjectInputStream(s.getInputStream());

        try {
            myPlayerName = Navigator.goToStartAndGetNewClient();
            createUsername();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Request r = (Request) ois.readObject();
                        switch (r.status) {
                            case Constants.STATUS_USERNAME_UNAVAILABLE:
                                myPlayerName = Navigator.reenterUsername();
                                createUsername();
                                break;
                            case Constants.STATUS_CLIENT_CREATED:
                                getUserMenuOption();
                                break;
                            default:
                                System.out.println("Message received. status: " + r.status);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {

                    }
                }
            }
        });

        readMessage.start();
    }

    //private static void watchGame(){
    //    // while in this mode -> handle server communication
    //    //////////////////////////
    //    // CONNECT WITH SERVER
    //    //////////////////////////
    //    // pedir para o servidor a lista de jogos
    //    int qtdEspera;
    //    while(/*Server nao responde*/){
    //        // a cada keep alive incrementar qtd de espera (só pra aparecer bonitinho o loading)
    //        Navigator.goToLoadingScreen(qtdEspera);
    //    }
    //    //////////////////////////
    //    // CHOOSE GAME
    //    //////////////////////////
    //    do {
    //        String[] gamesList;// temos lista de jogos ativos com a resposta do servidor
    //        /*
    //         * Como eu nao sabia quais iam ser as informações nessa lista
    //         * (se iam ser soh os id's ou se ia ter data de inicio tbm, sei la)
    //         * eu coloquei pra pegar esse array de strings e imprimir linha por linha
    //         * Qualquer coisa soh passar as respostas pra strings.
    //         */
    //        int gameSelected = Navigator.goToGamesList(gamesList);
    //        //  enviar o jogo escolhido pro servidor
    //        // pegar resposta do servidor
    //        if(/*resposta de erro*/){
    //            Navigator.showSelectGameError();
    //        }
    //    } while (/*Resposta do servidor for de erro*/);
    //    //////////////////////////
    //    // START STREAMING
    //    //////////////////////////
    //    do{
    //        GameModel partida = null;// recebe resposta do servidor com jogo
    //        Navigator.displayGameInWatchMode(partida); // atualiza a cada keep alive
    //    } while(/*partida nao acabou*/);
    //}
//
    //private static void startNewMultiplayerGame() {
    //    // while in this mode -> handle server communication
    //    //////////////////////////
    //    // CONNECT WITH SERVER
    //    //////////////////////////
    //    int qtdEspera;
    //    while(/*Server nao responde*/){
    //        // a cada keep alive incrementar qtd de espera (só pra aparecer bonitinho o loading)
    //        Navigator.goToLoadingScreen(qtdEspera);
    //    }
    //    //////////////////////////
    //    // START GAME
    //    //////////////////////////
    //    qtdEspera = 0;
    //    GameModel partida;
    //    do{
    //        // requisicao de status do jogo
    //        partida = null;// recebe resposta do servidor com jogo
    //        if(partida.playerGiveUp) {
    //            Navigator.displayPlayerGiveUp(partida);
    //            break;
    //        }
    //        if(partida.thisTurnPlayer.equals(myPlayerName)){
    //            qtdEspera = 0;
    //            if(partida.isOver){
    //                Navigator.displayIwin(partida);
    //            } else {
    //                int selectedField = Navigator.displayGameInMyTurnMode(partida);
    //                // enviar pro servidor a casa que eu escolhi
    //            }
    //        } else {
    //            if(partida.isOver){
    //                Navigator.displayIloose(partida);
    //            } else {
    //                Navigator.displayGameNotInMyTurnMode(partida, qtdEspera);
    //            }
    //            qtdEspera++;
    //        }
    //    } while(!partida.isOver);
    //}
//
    //private static void startNewSoloGame(){
    //    // while in this mode -> handle server communication
    //    /*
    //    * Se basear no que foi feito com o multiplayer (mas sem necessidade de mostrar que o jogador desistiu)
    //    */
    //}

    private static void createUsername() throws IOException {
        Request r = new Request();
        r.status = Constants.STATUS_NEW_CLIENT;
        r.username = myPlayerName;
        oos.writeObject(r);
        oos.flush();
    }

    private static void getUserMenuOption() throws IOException {
        Request r = new Request();
        r.username = myPlayerName;

        int optionSelected = Navigator.goToMenu(myPlayerName);
        switch (optionSelected) {
            case 1:
                r.status = Constants.STATUS_WATCH;
                break;
            case 2:
                r.status = Constants.STATUS_NEW_MULT_GAME;
                //startNewMultiplayerGame();
                break;
            case 3:
                r.status = Constants.STATUS_NEW_SINGL_GAME;
                //startNewSoloGame();
                break;
            default:
                logoutFromServer();
                System.exit(0);
                break;
        }
        
        oos.writeObject(r);
        oos.flush();
    }

    private static void logoutFromServer() throws IOException {
        Request r = new Request();
        r.status = Constants.STATUS_CLIENT_LOGOUT;
        r.username = myPlayerName;
        oos.writeObject(r);
        oos.flush();
    }
}
