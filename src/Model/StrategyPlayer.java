package Model;

import Controller.BreakthroughController;

import java.util.List;

/**
 * Created by KUBA on 2016-07-22.
 */
public class StrategyPlayer implements Move {
    @Override
    public void move(int position, int[][] boardState) {
        List<Coordinates> moveCoordinates = BreakthroughController.getCoordinates(Helper.getInstance().getValue());

        if (position == 1) {
            if (WhitePawn.getInstance().checkPossibleMove(moveCoordinates)) {
                WhitePawn.getInstance().getWhitePawns().set(WhitePawn.getInstance().findPawnIndex(moveCoordinates.get(0)), moveCoordinates.get(1));
                BlackPawn.getInstance().checkBlackPawnsCoordinates(moveCoordinates.get(1));
                Board.getInstance().actualizeFieldBoard(moveCoordinates.get(0), moveCoordinates.get(1));
                WhitePawn.getInstance().setMoveNumber(WhitePawn.getInstance().getMoveNumber() + 1);
            }
        } else if (position == 2) {
            if (BlackPawn.getInstance().checkPossibleMove(moveCoordinates)) {
                BlackPawn.getInstance().getBlackPawns().set(BlackPawn.getInstance().findPawnIndex(moveCoordinates.get(0)), moveCoordinates.get(1));
                WhitePawn.getInstance().checkWhitePawnsCoordinates(moveCoordinates.get(1));
                Board.getInstance().actualizeFieldBoard(moveCoordinates.get(0), moveCoordinates.get(1));
                BlackPawn.getInstance().setMoveNumber(BlackPawn.getInstance().getMoveNumber() + 1);
            }
        }
    }
}