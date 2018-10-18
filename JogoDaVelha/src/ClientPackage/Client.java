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

public class Client {

    private static String hostName;
    private static String myPlayerName;
    private static ObjectOutputStream oos = null;
    private static ObjectInputStream ois = null;

    public static void main(String[] args) throws UnknownHostException, IOException {
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


        // todo -> enviar nome do jogador pro host
        // todo -> esperar resposta do servidor
        /*
        if(resposta == erro || sem resposta no timeLimit){
            Navigator.goToLoginError();
            System.exit(0);
        }
         */

        Socket s = new Socket(ip, Constants.SERVER_SOCKET);
        oos = new ObjectOutputStream(s.getOutputStream());
        ois = new ObjectInputStream(s.getInputStream());
        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    myPlayerName = Navigator.goToStartAndGetNewClient();
                    createUsername();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                boolean finish = false;
                while (!finish) {
                    try {
                        Request r = new Request();
                        int optionSelected = Navigator.goToMenu(myPlayerName);
                        switch (optionSelected) {
                            case 1:
                                System.out.println("Watch Game");
                                //watchGame();
                                break;
                            case 2:
                                System.out.println("New Multiplayer Game");
                                //startNewMultiplayerGame();
                                break;
                            case 3:
                                System.out.println("Start New Solo Game");
                                //startNewSoloGame();
                                break;
                            default:
                                r.status = Constants.STATUS_CLIENT_LOGOUT;
                                System.out.println("Logout");
                                finish = true;
                                break;
                        }
                        if (r.status != Constants.UNKNOWN_STATUS) {
                            oos.writeObject(r);
                            oos.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Request r = (Request) ois.readObject();
                        switch (r.status) {
                            case Constants.STATUS_CLIENT_CREATED:
                                System.out.println("Conexão criada com sucesso");
                                break;
                            case Constants.STATUS_USERNAME_UNAVAILABLE:
                                myPlayerName = Navigator.reenterUsername();
                                createUsername();
                                break;
                            default:
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {

                    }
                }
            }
        });

        sendMessage.start();
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

}