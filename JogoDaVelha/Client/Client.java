package Client;

import Client.UserInterface.Navigator;
import Models.GameModel;

public class Client{

    private static String hostName;

    public static void main(String[] args) {
        // if no hostname is provided, quit
        if (args.length == 0) {
            System.out.println("User did not enter a host name. Client program exiting.");
            System.exit(1);
        }
        // setup host communication
        hostName = args[0];

        ///////////////////////////
        // START APPLICATION
        //////////////////////////

        String newPlayerName = Navigator.goToStartAndGetNewClient();
        // todo -> enviar nome do jogador pro host
        // todo -> esperar resposta do servidor
        /*
        if(resposta == erro || sem resposta no timeLimit){
            Navigator.goToLoginError();
            System.exit(0);
        }
         */

        boolean finish = false;
        while(!finish){
            int optionSelected = Navigator.goToMenu(newPlayerName);
            switch(optionSelected) {
                case 1:
                    watchGame();
                    break;
                case 2:
                    startNewMultiplayerGame();
                    break;
                case 3:
                    startNewSoloGame();
                    break;
                default:
                    finish = true;
            }
        }
    }

    private static void watchGame(){
        // while in this mode -> handle server communication
        // pedir para o servidor a lista de jogos
        int qtdEspera;
        while(/*Server nao responde*/){
            // a cada keep alive incrementar qtd de espera (só pra aparecer bonitinho o loading)
            Navigator.goToLoadingScreen(qtdEspera);
        }
        do {
            String[] gamesList;// temos lista de jogos ativos com a resposta do servidor
            /*
            * Como eu nao sabia quais iam ser as informações nessa lista
            * (se iam ser soh os id's ou se ia ter data de inicio tbm, sei la)
            * eu coloquei pra pegar esse array de strings e imprimir linha por linha
            * Qualquer coisa soh passar as respostas pra strings.
            */
            int gameSelected = Navigator.goToGamesList(gamesList);
            //  enviar o jogo escolhido pro servidor
            // pegar resposta do servidor
            if(/*resposta de erro*/){
                Navigator.showSelectGameError();
            }
        } while (/*Resposta do servidor for de erro*/);

        do{
            GameModel partida = null;// recebe resposta do servidor com jogo
            Navigator.displayGameInWatchMode(partida); // atualiza a cada keep alive
        } while(/*partida nao acabou*/);

    }

    private static void startNewMultiplayerGame() {
        // while in this mode -> handle server communication

    }

    private static void startNewSoloGame(){
        // while in this mode -> handle server communication

    }
}
