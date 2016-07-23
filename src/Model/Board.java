package Model;

/**
 * Created by KUBA on 2016-07-16.
 */
public class Board {

    private static Board instance = new Board();
    private int[][] board;
    private final int SIZE = 8;
    private final int WHITE = 1;
    private final int BLACK = 2;


    private Board(){
        board = new int[SIZE][SIZE];

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < SIZE; j++)
                board[i][j] = BLACK;

        for(int i = 6; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                board[i][j] = WHITE;
    }

    public int[][] getBoard(){
        return board;
    }

    public static Board getInstance(){
        return instance;
    }

    public void showBoard(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }

    public void actualizeFieldBoard(Coordinates pawn, Coordinates move){

        int pawnX = pawn.getX();
        int pawnY = pawn.getY();
        int moveX = move.getX();
        int moveY = move.getY();

        int temp = getBoard()[pawnY][pawnX];
        getBoard()[moveY][moveX] = temp;
        getBoard()[pawnY][pawnX] = 0;
    }

    public void redrawBoard(){
        board = new int[SIZE][SIZE];

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < SIZE; j++)
                board[i][j] = BLACK;

        for(int i = 6; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                board[i][j] = WHITE;
    }
}
