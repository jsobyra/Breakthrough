package Model;

import java.util.List;
import java.util.Random;

/**
 * Created by KUBA on 2016-07-17.
 */
public class StrategyRandom implements Move {

    @Override
    public void move(int position, int[][] boardState){
        List<Coordinates> possibleMoves;
        Random rand;
        Coordinates coordinates;
        int pawn;

        if(position == 1){

            do {
                rand = new Random();
                pawn = rand.nextInt(WhitePawn.getInstance().whitePawnAmount());
                coordinates = WhitePawn.getInstance().getWhitePawns().get(pawn);
                possibleMoves = WhitePawn.getInstance().checkWhitePawnMoves(coordinates);

            } while(possibleMoves.size() == 0);

            int move = rand.nextInt(possibleMoves.size());
            WhitePawn.getInstance().getWhitePawns().set(pawn, possibleMoves.get(move));
            BlackPawn.getInstance().checkBlackPawnsCoordinates(possibleMoves.get(move));
            Board.getInstance().actualizeFieldBoard(coordinates, possibleMoves.get(move));
            WhitePawn.getInstance().setMoveNumber(WhitePawn.getInstance().getMoveNumber() + 1);
        }
        else if(position == 2){

            do {
                rand = new Random();
                pawn = rand.nextInt(BlackPawn.getInstance().blackPawnAmount());
                coordinates = BlackPawn.getInstance().getBlackPawns().get(pawn);
                possibleMoves = BlackPawn.getInstance().checkBlackPawnMoves(coordinates);

            } while(possibleMoves.size() == 0);

            int move = rand.nextInt(possibleMoves.size());
            BlackPawn.getInstance().getBlackPawns().set(pawn, possibleMoves.get(move));
            WhitePawn.getInstance().checkWhitePawnsCoordinates(possibleMoves.get(move));
            Board.getInstance().actualizeFieldBoard(coordinates, possibleMoves.get(move));
            BlackPawn.getInstance().setMoveNumber(BlackPawn.getInstance().getMoveNumber() + 1);
        }
    }
}
