package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KUBA on 2016-07-16.
 */
public class BlackPawn {

    public List<Coordinates> blackPawns;
    private static BlackPawn instance = new BlackPawn();
    private boolean blackWin;
    private int moveNumber;

    private BlackPawn(){
        blackPawns = new ArrayList<Coordinates>(16);
        moveNumber = 0;

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 8; j++)
                blackPawns.add(new Coordinates(j, i));
    }

    public List<Coordinates> getBlackPawns(){
        return blackPawns;
    }

    public int blackPawnAmount(){
        return blackPawns.size();
    }

    public static BlackPawn getInstance(){
        return instance;
    }

    public void checkBlackPawnsCoordinates(Coordinates blackPawn){
        for(int i = 0; i < blackPawns.size(); i++){
            if(blackPawns.get(i).getX() == blackPawn.getX() && blackPawns.get(i).getY() == blackPawn.getY())
                blackPawns.remove(i);
        }
    }

    public int getMoveNumber(){
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber){
        this.moveNumber = moveNumber;
    }

    private boolean isBlack(Coordinates a){
        if(Board.getInstance().getBoard()[a.getY()][a.getX()] == 2)
            return true;
        else
            return false;
    }

    public void actualizeBlack(){
        List<Coordinates> temp = new ArrayList<Coordinates>(16);
        int[][] board = Board.getInstance().getBoard();
        for(int i = 7; i >= 0; i--)
            for(int j = 0; j < 8; j++){
                if(isBlack(new Coordinates(i, j)))
                    temp.add(new Coordinates(i, j));
            }
        blackPawns = temp;
    }

    public List<Coordinates> checkBlackPawnMoves(Coordinates coordinates){
        List<Coordinates> possibleMoves = new ArrayList<>();
        List<Integer> possibilities = new ArrayList<>();
        int x = coordinates.getX();
        int y = coordinates.getY();


        if(y < 7 && x > 0 && x < 7){
            for(int i = 0; i < 3; i++){
                possibilities.add(Board.getInstance().getBoard()[y+1][x-1+i]);
            }

            if(possibilities.get(0) == 0 || possibilities.get(0) == 1)
                possibleMoves.add(new Coordinates(x-1, y+1));
            if(possibilities.get(1) == 0)
                possibleMoves.add(new Coordinates(x, y+1));
            if(possibilities.get(2) == 0 || possibilities.get(2) == 1)
                possibleMoves.add(new Coordinates(x+1, y+1));
        }
        else if(y < 7 && x == 7){
            for(int i = 0; i < 2; i++)
                possibilities.add(Board.getInstance().getBoard()[y+1][x-1+i]);

            if(possibilities.get(0) == 0 || possibilities.get(0) == 1)
                possibleMoves.add(new Coordinates(x-1, y+1));
            if(possibilities.get(1) == 0)
                possibleMoves.add(new Coordinates(x, y+1));

        }
        else if(y < 7 && x == 0){
            for(int i = 0; i < 2; i++)
                possibilities.add(Board.getInstance().getBoard()[y+1][x+i]);

            if(possibilities.get(0) == 0)
                possibleMoves.add(new Coordinates(x, y+1));
            if(possibilities.get(1) == 0 || possibilities.get(1) == 1)
                possibleMoves.add(new Coordinates(x+1, y+1));
        }

        return possibleMoves;
    }

    public boolean checkBlackWin(){
        for(int i = 0; i < blackPawnAmount(); i++){
            if(blackPawns.get(i).getY() == 7){
                blackWin = true;
                return blackWin;
            }
        }
        blackWin = false;
        return blackWin;
    }

    public List<Coordinates> checkBlackTake(Coordinates coordinates){
        List<Coordinates> possibleMoves = new ArrayList<>();
        List<Integer> possibilities = new ArrayList<>();
        int x = coordinates.getX();
        int y = coordinates.getY();

        if(y < 7 && x > 0 && x < 7){
            for(int i = 0; i < 2; i++){
                possibilities.add(Board.getInstance().getBoard()[y+1][x-1+i*2]);
            }

            if(possibilities.get(0) == 1)
                possibleMoves.add(new Coordinates(x-1, y+1));
            if(possibilities.get(1) == 1)
                possibleMoves.add(new Coordinates(x+1, y+1));
        }
        else if(y < 7 && x == 7){
            possibilities.add(Board.getInstance().getBoard()[y+1][x-1]);

            if(possibilities.get(0) == 1)
                possibleMoves.add(new Coordinates(x-1, y+1));
        }
        else if(y < 7 && x == 0){
            possibilities.add(Board.getInstance().getBoard()[y+1][x+1]);

            if(possibilities.get(0) == 1)
                possibleMoves.add(new Coordinates(x+1, y+1));
        }

        return possibleMoves;
    }

    public boolean checkPossibleMove(List<Coordinates> move){
        int pawnNowX = move.get(0).getX();
        int pawnNowY = move.get(0).getY();
        int pawnMoveX = move.get(1).getX();
        int pawnMoveY = move.get(1).getY();


        if( (pawnMoveX - pawnNowX == 1 || pawnMoveX - pawnNowX == 0 || pawnMoveX - pawnNowX == -1) && (pawnNowY < pawnMoveY)){
            if((pawnMoveX - pawnNowX == -1 || pawnMoveX - pawnNowX == 1) && (Board.getInstance().getBoard()[pawnMoveY][pawnMoveX] == 2 || Board.getInstance().getBoard()[pawnMoveY][pawnMoveX] == 0))
                return true;
            else if(pawnMoveX - pawnNowX == 0 && pawnNowY < pawnMoveY && Board.getInstance().getBoard()[pawnMoveY][pawnMoveX] == 0)
                return true;
        }

        return false;
    }

    public int findPawnIndex(Coordinates coordinates){
        for(int i = 0; i < blackPawnAmount(); i++){
            if(blackPawns.get(i).getY() == coordinates.getY() && blackPawns.get(i).getX() == coordinates.getX())
                return i;
        }

        return -1;
    }

    public void renewBlackPawn(){
        blackPawns = new ArrayList<Coordinates>(16);
        moveNumber = 0;

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 8; j++)
                blackPawns.add(new Coordinates(j, i));
    }
}

