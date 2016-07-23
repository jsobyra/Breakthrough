package Model;

/**
 * Created by KUBA on 2016-07-19.
 */

public class Contestant {
    private Move strategy;

    public Contestant(Move strategy){
        this.strategy = strategy;
    }

    public void move(int position, int[][] boardState){
        strategy.move(position, boardState);
    }
}