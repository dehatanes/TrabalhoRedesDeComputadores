package ClientPackage.UserInterface;

import java.io.IOException;

class Interfaces  {

    static void showStartScreen() {
        clearConsole();
        System.out.println("---------------------------");
        System.out.println("BEM VINDO AO JOGO DA VELHA");
        System.out.println("---------------------------");
        System.out.println();
        System.out.print("Digite seu nome de usuario para continuar: ");
    }

    static void showUsernameError(){
        clearConsole();
        System.out.println("---------------------------");
        System.out.println("FALHA AO LOGAR");
        System.out.println("---------------------------");
        System.out.println();
        System.out.print("Digite um novo nome de usuario para continuar: ");
    }

    static void showFailToLoginScreen() {
        clearConsole();
        System.out.println("---------------------------");
        System.out.println("FALHA AO ACESSAR O SERVIDOR");
        System.out.println("---------------------------");
        System.out.print("Houve um erro ao tentar entrar no servidor de jogos. Tente novamente mais tarde.");
        System.out.print("Pressione qualquer tecla para sair...");
    }

    static void showMenuScreen(String userName) {
        clearConsole();
        System.out.println("---------------------------");
        System.out.println("      MENU PRINCIPAL");
        System.out.println("---------------------------");
        System.out.println();
        System.out.println("Bem vindo(a), " + userName);
        System.out.println("Escolha uma das opcoes abaixo:");
        System.out.println("1) Assistir um jogo");
        System.out.println("2) Comecar novo jogo contra outro jogador");
        System.out.println("3) Comecar novo jogo contra um bot");
        System.out.println("4) SAIR");
        System.out.println();
        System.out.print("Opcao: ");
    }

    static void showInvalidMenuOptionMessage(){
        System.out.println();
        System.out.println("Opcao invalida! Por favor, escolha uma das opcoes de 1 a 4.");
        System.out.print("Opcao: ");
    }

    static void showActiveGamesListScreenHeader(String[] games) {
        clearConsole();
        System.out.println("---------------------------");
        System.out.println("         JOGOS ATIVOS");
        System.out.println("---------------------------");
        System.out.println();
        for(String game : games){
            System.out.println(game);
        }
        System.out.println("Digite o id do jogo que deseja ingressar: ");
        System.out.println();
    }

    static void showInvalidGameOptionMessage(){
        System.out.println();
        System.out.println("Opcao invalida! O jogo acabou ou não existe :(");
        System.out.print("Aperte qualquer tecla para atualizar a lista de jogos. ");
    }

    static void showLoadingScreen(int numWaitingDots) {
        clearConsole();
        System.out.println("---------------------------");
        System.out.println("         AGUARDE");
        System.out.println("---------------------------");
        System.out.print("Esperando resposta do servidor" + waitingDots(numWaitingDots));
        System.out.println();
    }

    static void showGameScreenHeader(int gameName, String player1, String player2, int qtdPeopleWatching) {
        clearConsole();
        System.out.println("---------------------------");
        System.out.println("JOGO " + gameName);
        System.out.println("---------------------------");
        System.out.println();
        System.out.println(player1 + " (X) vs. " + player2 + " (O)");
        System.out.println(qtdPeopleWatching + " pessoas assistindo");
        System.out.println();
    }

    static void showPlayerTurnFragment(String thisTurnPlayerName){
        System.out.println("Vez do jogador " + thisTurnPlayerName);

    }

    static void showWaitForYourTurnFragment(int numWaitingDots){
        System.out.println("Aguarde sua vez" + waitingDots(numWaitingDots));
    }

    static void showYourTurnFragment() {
        System.out.println("-> EH A SUA VEZ!");
        System.out.print("Escolha uma casa para marcar: ");
    }

    static void showInvalidBoardOptionMessage(){
        System.out.println();
        System.out.println("Casa invalida! Por favor, escolha um numero disponivel no tabuleiro.");
        System.out.print("Opcao: ");
    }

    static void showYouWinFragment(){
        System.out.println("---------------------------");
        System.out.println(" PARABENS VOCE GANHOU!!!");
        System.out.println("---------------------------");
        System.out.println("Pressione qualquer tecla para voltar ao menu...");
    }

    static void showYouLooseFragment(){
        System.out.println("-----------------------------------");
        System.out.println(" NAO FOI DESSA VEZ... VOCE PERDEU!!!");
        System.out.println("-----------------------------------");
        System.out.println("Pressione qualquer tecla para voltar ao menu...");
    }

    static void showWinnerPlayerFragment(String winnerPlayer){
        System.out.println("--------------------------------------------");
        System.out.println(" O JOGADOR " + winnerPlayer + " GANHOU!!!");
        System.out.println("--------------------------------------------");
        System.out.println("Pressione qualquer tecla para voltar ao menu...");
    }

    static void showPlayerGiveUp(String playerName){
        System.out.println("------------------------------------------------");
        System.out.println(" DESISTENCIA DO JOGADOR " + playerName);
        System.out.println("------------------------------------------------");
        System.out.println("Jogo encerrado por inatividade do jogador " + playerName);
        System.out.println("Pressione qualquer tecla para voltar ao menu...");
    }

    static void displayBoardFragment(String[][] board){
        System.out.println("ESTADO DO TABULEIRO:");
        System.out.println(String.join("|", board[0]));
        System.out.println(String.join("|", board[1]));
        System.out.println(String.join("|", board[2]));
    }


    private static String waitingDots(int numDots) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i<numDots; i++){
            stringBuilder.append(".");
        }
        return stringBuilder.toString();
    }

    private static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (final IOException | InterruptedException e){
            // pass
            System.out.println();
            System.out.println();
        }
    }
}
