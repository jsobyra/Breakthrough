package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KUBA on 2016-07-16.
 */
public class WhitePawn {

    private List<Coordinates> whitePawns;
    private static WhitePawn instance = new WhitePawn();
    private boolean whiteWin;
    private int moveNumber;

    private WhitePawn(){
        whitePawns = new ArrayList<Coordinates>(16);
        moveNumber = 0;

        for(int i = 6; i < 8; i++)
            for(int j = 0; j < 8; j++){
                whitePawns.add(new Coordinates(j, i));
            }
        whiteWin = false;
    }

    public List<Coordinates> copyWhitePawnList(){
        List<Coordinates> copy = new ArrayList<>();
        for(int i = 0; i < whitePawnAmount(); i++){
            Coordinates temp = new Coordinates(whitePawns.get(i).getX(), whitePawns.get(i).getY());
            copy.add(temp);
        }

        return copy;
    }

    public void actualizePawn(int index, Coordinates value){
        whitePawns.set(index, value);
    }

    public int findPawnIndex(Coordinates coordinates){
        for(int i = 0; i < whitePawnAmount(); i++){
            if(whitePawns.get(i).getY() == coordinates.getY() && whitePawns.get(i).getX() == coordinates.getX())
                return i;
        }

        return -1;
    }

    public static WhitePawn getInstance(){
        return instance;
    }

    public List<Coordinates> getWhitePawns(){
        return whitePawns;
    }

    private boolean isWhite(Coordinates a){
        if(Board.getInstance().getBoard()[a.getY()][a.getX()] == 1)
            return true;
        else
            return false;
    }

    public int whitePawnAmount(){
        return whitePawns.size();
    }

    public void actualizeWhite(){
        List<Coordinates> temp = new ArrayList<Coordinates>(16);
        int[][] board = Board.getInstance().getBoard();
        for(int i = 7; i >= 0; i--)
            for(int j = 0; j < 8; j++){
                if(isWhite(new Coordinates(i, j)))
                    temp.add(new Coordinates(i, j));
            }
        whitePawns = temp;
    }

    public void checkWhitePawnsCoordinates(Coordinates whitePawn){
        for(int i = 0; i < whitePawns.size(); i++){
            if(whitePawns.get(i).getX() == whitePawn.getX() && whitePawns.get(i).getY() == whitePawn.getY())
                whitePawns.remove(i);
        }
    }

    public boolean checkWhiteWin(){
        for(int i = 0; i < whitePawnAmount(); i++){
            if(whitePawns.get(i).getY() == 0){
                whiteWin = true;
                return whiteWin;
            }
        }
        whiteWin = false;
        return whiteWin;
    }

    public List<Coordinates> checkWhitePawnMoves(Coordinates coordinates){
        List<Coordinates> possibleMoves = new ArrayList<>();
        List<Integer> possibilities = new ArrayList<>();
        int x = coordinates.getX();
        int y = coordinates.getY();


        if(y > 0 && x > 0 && x < 7){
            for(int i = 0; i < 3; i++){
                possibilities.add(Board.getInstance().getBoard()[y-1][x-1+i]);
            }

            if(possibilities.get(0) == 0 || possibilities.get(0) == 2)
                possibleMoves.add(new Coordinates(x-1, y-1));
            if(possibilities.get(1) == 0)
                possibleMoves.add(new Coordinates(x, y-1));
            if(possibilities.get(2) == 0 || possibilities.get(2) == 2)
                possibleMoves.add(new Coordinates(x+1, y-1));
        }
        else if(y > 0 && x == 7){
            for(int i = 0; i < 2; i++)
                possibilities.add(Board.getInstance().getBoard()[y-1][x-1+i]);

            if(possibilities.get(0) == 0 || possibilities.get(0) == 2)
                possibleMoves.add(new Coordinates(x-1, y-1));
            if(possibilities.get(1) == 0)
                possibleMoves.add(new Coordinates(x, y-1));

        }
        else if(y > 0 && x == 0){
            for(int i = 0; i < 2; i++)
                possibilities.add(Board.getInstance().getBoard()[y-1][x+i]);

            if(possibilities.get(0) == 0)
                possibleMoves.add(new Coordinates(x, y-1));
            if(possibilities.get(1) == 0 || possibilities.get(1) == 2)
                possibleMoves.add(new Coordinates(x+1, y-1));
        }

        return possibleMoves;
    }

    public List<Coordinates> checkWhiteTake(Coordinates coordinates){
        List<Coordinates> possibleMoves = new ArrayList<>();
        List<Integer> possibilities = new ArrayList<>();
        int x = coordinates.getX();
        int y = coordinates.getY();

        if(y > 0 && x > 0 && x < 7){
            for(int i = 0; i < 2; i++){
                possibilities.add(Board.getInstance().getBoard()[y-1][x-1+i*2]);
            }

            if(possibilities.get(0) == 2)
                possibleMoves.add(new Coordinates(x-1, y-1));
            if(possibilities.get(1) == 2)
                possibleMoves.add(new Coordinates(x+1, y-1));
        }
        else if(y > 0 && x == 7){
            possibilities.add(Board.getInstance().getBoard()[y-1][x-1]);

            if(possibilities.get(0) == 2)
                possibleMoves.add(new Coordinates(x-1, y-1));
        }
        else if(y > 0 && x == 0){
            possibilities.add(Board.getInstance().getBoard()[y-1][x+1]);

            if(possibilities.get(0) == 2)
                possibleMoves.add(new Coordinates(x+1, y-1));
        }

        return possibleMoves;
    }

    public int getMoveNumber(){
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber){
        this.moveNumber = moveNumber;
    }

    public boolean checkPossibleMove(List<Coordinates> move){
        int pawnNowX = move.get(0).getX();
        int pawnNowY = move.get(0).getY();
        int pawnMoveX = move.get(1).getX();
        int pawnMoveY = move.get(1).getY();


        if( (pawnMoveX - pawnNowX == 1 || pawnMoveX - pawnNowX == 0 || pawnMoveX - pawnNowX == -1) && (pawnNowY > pawnMoveY)){
            if((pawnMoveX - pawnNowX == -1 || pawnMoveX - pawnNowX == 1) && (Board.getInstance().getBoard()[pawnMoveY][pawnMoveX] == 2 || Board.getInstance().getBoard()[pawnMoveY][pawnMoveX] == 0))
                return true;
            else if(pawnMoveX - pawnNowX == 0 && pawnNowY > pawnMoveY && Board.getInstance().getBoard()[pawnMoveY][pawnMoveX] == 0)
                return true;
        }

        return false;
    }

    public void renewWhitePawn(){
        whitePawns = new ArrayList<Coordinates>(16);
        moveNumber = 0;

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 8; j++)
                whitePawns.add(new Coordinates(j, i));
    }

}








