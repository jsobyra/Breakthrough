package Model;

import java.util.List;
import java.util.Random;

/**
 * Created by KUBA on 2016-07-22.
 */
public class StrategyTakePawn implements Move {

    @Override
    public void move(int position, int[][] boardState){
        List<Coordinates> possibleMoves;
        Random rand = new Random();
        Coordinates coordinates;
        int move;
        int pawn;
        boolean flag = true;

        if(position == 1){

            for(int i = 0; i < WhitePawn.getInstance().whitePawnAmount(); i++){
                possibleMoves = WhitePawn.getInstance().checkWhiteTake(WhitePawn.getInstance().getWhitePawns().get(i));
                coordinates = WhitePawn.getInstance().getWhitePawns().get(i);
                if(possibleMoves.size() > 0){
                    move = rand.nextInt(possibleMoves.size());
                    WhitePawn.getInstance().getWhitePawns().set(i, possibleMoves.get(move));
                    BlackPawn.getInstance().checkBlackPawnsCoordinates(possibleMoves.get(move));
                    Board.getInstance().actualizeFieldBoard(coordinates, possibleMoves.get(move));
                    WhitePawn.getInstance().setMoveNumber(WhitePawn.getInstance().getMoveNumber() + 1);
                    flag = false;
                    break;
                }
            }
            if(flag){
                do {
                    pawn = rand.nextInt(WhitePawn.getInstance().whitePawnAmount());
                    coordinates = WhitePawn.getInstance().getWhitePawns().get(pawn);
                    possibleMoves = WhitePawn.getInstance().checkWhitePawnMoves(coordinates);

                } while(possibleMoves.size() == 0);

                move = rand.nextInt(possibleMoves.size());
                WhitePawn.getInstance().getWhitePawns().set(pawn, possibleMoves.get(move));
                BlackPawn.getInstance().checkBlackPawnsCoordinates(possibleMoves.get(move));
                Board.getInstance().actualizeFieldBoard(coordinates, possibleMoves.get(move));
                WhitePawn.getInstance().setMoveNumber(WhitePawn.getInstance().getMoveNumber() + 1);
            }
        }
        else if(position == 2){
            for(int i = 0; i < BlackPawn.getInstance().blackPawnAmount(); i++){
                possibleMoves = BlackPawn.getInstance().checkBlackTake(BlackPawn.getInstance().getBlackPawns().get(i));
                coordinates = BlackPawn.getInstance().getBlackPawns().get(i);
                if(possibleMoves.size() > 0){
                    move = rand.nextInt(possibleMoves.size());
                    BlackPawn.getInstance().getBlackPawns().set(i, possibleMoves.get(move));
                    WhitePawn.getInstance().checkWhitePawnsCoordinates(possibleMoves.get(move));
                    Board.getInstance().actualizeFieldBoard(coordinates, possibleMoves.get(move));
                    BlackPawn.getInstance().setMoveNumber(BlackPawn.getInstance().getMoveNumber() + 1);
                    flag = false;
                    break;
                }
            }
            if(flag){
                do {
                    pawn = rand.nextInt(BlackPawn.getInstance().blackPawnAmount());
                    coordinates = BlackPawn.getInstance().getBlackPawns().get(pawn);
                    possibleMoves = BlackPawn.getInstance().checkBlackPawnMoves(coordinates);

                } while(possibleMoves.size() == 0);

                move = rand.nextInt(possibleMoves.size());
                BlackPawn.getInstance().getBlackPawns().set(pawn, possibleMoves.get(move));
                WhitePawn.getInstance().checkWhitePawnsCoordinates(possibleMoves.get(move));
                Board.getInstance().actualizeFieldBoard(coordinates, possibleMoves.get(move));
                BlackPawn.getInstance().setMoveNumber(BlackPawn.getInstance().getMoveNumber() + 1);
            }
        }
    }

}