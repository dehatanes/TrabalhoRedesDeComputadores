package Models;

public class GameModel {
    public int id;
    public String player1;
    public String player2;
    public int qtdPeopleWatching;
    public String thisTurnPlayer; // por esse atributo que saberemos o ganhador quando game.isOver ou quem desistiu quando game.playerGiveUp
    public boolean isOver;
    public boolean playerGiveUp;
    public String[][] board;

    // util method
    public boolean isValidPosition(String[][] board, int position){
        switch(position){
            case 1:
                if(board[0][0].equals("1")) return true;
                break;
            case 2:
                if(board[0][1].equals("2")) return true;
                break;
            case 3:
                if(board[0][2].equals("3")) return true;
                break;
            case 4:
                if(board[1][0].equals("4")) return true;
                break;
            case 5:
                if(board[1][1].equals("5")) return true;
                break;
            case 6:
                if(board[1][2].equals("6")) return true;
                break;
            case 7:
                if(board[2][0].equals("7")) return true;
                break;
            case 8:
                if(board[2][1].equals("8")) return true;
                break;
            case 9:
                if(board[2][2].equals("9")) return true;
                break;
        }
        return false;
    }

    // usar no servidor
    private void initializeBoard(){
        this.board = new String[3][3];
        int label = 1;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = "" + label;
                label++;
            }
        }
    }
}
