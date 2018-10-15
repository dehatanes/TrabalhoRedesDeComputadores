package Models;

public class GameModel {
    public int id;
    public String player1;
    public String player2;
    public int qtdPeopleWatching;
    public String thisTurnPlayer;
    public String[][] board;
    public boolean isOver;

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
