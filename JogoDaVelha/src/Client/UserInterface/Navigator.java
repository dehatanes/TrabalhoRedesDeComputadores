package Client.UserInterface;

import Models.GameModel;

import java.util.Scanner;

import static Client.UserInterface.Interfaces.*;


public class ScreenFactory {

    private static Scanner scanner  = new Scanner(System.in);

    public static String goToStartAndGetNewClient(){
        showStartScreen();
        return scanner.nextLine();
    }

    public static void goToLoginError(){
        showFailToLoginScreen();
        scanner.next();
    }

    public static int goToMenu(String username){
        showMenuScreen(username);
        int opt;
        do {
            try {
                opt = scanner.nextInt();
            } catch (Exception e){
                showInvalidMenuOptionMessage();
                opt = 0;
            }
        } while(opt > 0 && opt < 5);
        return opt;
    }

    public static void goToLoadingScreen(int numWaitingDots) {
        showLoadingScreen(numWaitingDots);
    }

    public static int goToGamesList(String[] gamesList){
        showActiveGamesListScreenHeader(gamesList);
        return scanner.nextInt();
    }

    public static void showSelectGameError(){
        showInvalidGameOptionMessage();
        scanner.next();
    }

    public static void displayGameInWatchMode(GameModel game){
        showGameScreenHeader(game.id, game.player1, game.player2, game.qtdPeopleWatching);
        showPlayerTurnFragment(game.thisTurnPlayer);
        displayBoardFragment(game.board);
        if(game.isOver){
            showWinnerPlayerFragment(game.thisTurnPlayer);
            scanner.next();
        }
    }
}
