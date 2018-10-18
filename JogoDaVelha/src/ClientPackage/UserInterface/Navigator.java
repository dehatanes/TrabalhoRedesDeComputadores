package ClientPackage.UserInterface;

import Models.GameModel;

import java.util.Scanner;

import static ClientPackage.UserInterface.Interfaces.*;


public class Navigator {

    private static Scanner scanner = new Scanner(System.in);

    public static String goToStartAndGetNewClient() {
        showStartScreen();
        return scanner.nextLine();
    }

    public static String reenterUsername() {
        showUsernameError();
        return scanner.nextLine();
    }

    public static void goToLoginError() {
        showFailToLoginScreen();
        scanner.next();
    }

    public static int goToMenu(String username) {
        showMenuScreen(username);
        int opt;
        do {
            try {
                opt = scanner.nextInt();
            } catch (Exception e) {
                showInvalidMenuOptionMessage();
                opt = 0;
            }
        } while (opt < 1 || opt > 4);
        return opt;
    }

    public static void goToLoadingScreen(int numWaitingDots) {
        showLoadingScreen(numWaitingDots);
    }

    public static int goToGamesList(String[] gamesList) {
        showActiveGamesListScreenHeader(gamesList);
        return scanner.nextInt();
    }

    public static void showSelectGameError() {
        showInvalidGameOptionMessage();
        scanner.next();
    }

    public static void displayGameInWatchMode(GameModel game) {
        showGameScreenHeader(game.id, game.player1, game.player2, game.qtdPeopleWatching);
        showPlayerTurnFragment(game.thisTurnPlayer);
        displayBoardFragment(game.board);
        if (game.playerGiveUp) {
            showPlayerGiveUp(game.thisTurnPlayer);
        } else if (game.isOver) {
            showWinnerPlayerFragment(game.thisTurnPlayer);
        }
        scanner.next();
    }

    public static int displayGameInMyTurnMode(GameModel game) {
        showGameScreenHeader(game.id, game.player1, game.player2, game.qtdPeopleWatching);
        showPlayerTurnFragment(game.thisTurnPlayer);
        displayBoardFragment(game.board);
        showYourTurnFragment();
        int opt;
        do {
            try {
                opt = scanner.nextInt();
            } catch (Exception e) {
                showInvalidBoardOptionMessage();
                opt = 0;
            }
        } while (opt < 1 || opt > 9 || !game.isValidPosition(game.board, opt));
        return opt;
    }

    public static int displayGameNotInMyTurnMode(GameModel game, int numWaitingDots) {
        showGameScreenHeader(game.id, game.player1, game.player2, game.qtdPeopleWatching);
        showPlayerTurnFragment(game.thisTurnPlayer);
        displayBoardFragment(game.board);
        showWaitForYourTurnFragment(numWaitingDots);
        return 0; //????
    }

    public static void displayIwin(GameModel game) {
        showGameScreenHeader(game.id, game.player1, game.player2, game.qtdPeopleWatching);
        showPlayerTurnFragment(game.thisTurnPlayer);
        displayBoardFragment(game.board);
        showYouWinFragment();
    }

    public static void displayIloose(GameModel game) {
        showGameScreenHeader(game.id, game.player1, game.player2, game.qtdPeopleWatching);
        showPlayerTurnFragment(game.thisTurnPlayer);
        displayBoardFragment(game.board);
        showYouLooseFragment();
    }

    public static void displayPlayerGiveUp(GameModel game) {
        showGameScreenHeader(game.id, game.player1, game.player2, game.qtdPeopleWatching);
        showPlayerTurnFragment(game.thisTurnPlayer);
        displayBoardFragment(game.board);
        showPlayerGiveUp(game.thisTurnPlayer);
    }
}
