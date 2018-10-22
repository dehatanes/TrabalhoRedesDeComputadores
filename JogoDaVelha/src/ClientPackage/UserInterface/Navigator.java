package ClientPackage.UserInterface;

import Models.GameModel;

import java.util.Scanner;

import static ClientPackage.UserInterface.Interfaces.*;


public class Navigator {

    public static String goToStartAndGetNewClient() {
        Scanner scanner = new Scanner(System.in);
        showStartScreen();
        String resp = scanner.nextLine();
        scanner.close;
        return resp;
    }

    public static String reenterUsername() {
        Scanner scanner = new Scanner(System.in);
        showUsernameError();
        String resp = scanner.nextLine();
        scanner.close;
        return resp;
    }

    public static void goToLoginError() {
        Scanner scanner = new Scanner(System.in);
        showFailToLoginScreen();
        scanner.next();
        scanner.close();
    }

    public static int goToMenu(String username) {
        Scanner scanner = new Scanner(System.in);
        showMenuScreen(username);
        int opt = 0;
        while(true) {
            try {
                opt = scanner.nextInt();
                if(opt <= 4 && opt >= 1) {
                    break;
                } else {
                    showInvalidMenuOptionMessage();
                    opt = 0;
                }
            } catch (Exception e) {
                showInvalidMenuOptionMessage();
                scanner.nextLine(); // clean buffer
                opt = 0;
            }
        }
        scanner.close();
        return opt;
    }

    public static void goToLoadingScreen(int numWaitingDots) {
        showLoadingScreen(numWaitingDots);
    }

    public static int goToGamesList(String[] gamesList) {
        Scanner scanner = new Scanner(System.in);
        showActiveGamesListScreenHeader(gamesList);
        int resp = scanner.nextInt();
        scanner.close;
        return resp;
    }

    public static void showSelectGameError() {
        Scanner scanner = new Scanner(System.in);
        showInvalidGameOptionMessage();
        scanner.next();
        scanner.close;
    }

    public static void displayGameInWatchMode(GameModel game) {
        Scanner scanner = new Scanner(System.in);
        showGameScreenHeader(game.id, game.player1, game.player2, game.qtdPeopleWatching);
        showPlayerTurnFragment(game.thisTurnPlayer);
        displayBoardFragment(game.board);
        if (game.playerGiveUp) {
            showPlayerGiveUp(game.thisTurnPlayer);
        } else if (game.isOver) {
            showWinnerPlayerFragment(game.thisTurnPlayer);
        }
        scanner.next();
        scanner.close;
    }

    public static int displayGameInMyTurnMode(GameModel game) {
        Scanner scanner = new Scanner(System.in);
        showGameScreenHeader(game.id, game.player1, game.player2, game.qtdPeopleWatching);
        showPlayerTurnFragment(game.thisTurnPlayer);
        displayBoardFragment(game.board);
        showYourTurnFragment();
        int opt;
        while(true) {
            try {
                opt = scanner.nextInt();
                if(opt >= 1 || opt <= 9 || game.isValidPosition(game.board, opt)) {
                    break;
                } else {
                    showInvalidBoardOptionMessage();
                    opt = 0;
                }
            } catch (Exception e) {
                showInvalidBoardOptionMessage();
                scanner.nextLine(); // clean buffer
                opt = 0;
            }
        }
        scanner.close;
        return opt;
    }

    public static int displayGameNotInMyTurnMode(GameModel game, int numWaitingDots) {
        showGameScreenHeader(game.id, game.player1, game.player2, game.qtdPeopleWatching);
        showPlayerTurnFragment(game.thisTurnPlayer);
        displayBoardFragment(game.board);
        showWaitForYourTurnFragment(numWaitingDots);
        return 0;
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
