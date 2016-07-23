package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by KUBA on 2016-07-20.
 */
public class BreakthroughController {
    Contestant player;
    Contestant computer;
    private final int canvasSize = 440;
    private final int size = 8;
    private final int fieldSize = canvasSize/size;

    @FXML
    Button beginGameButton;
    @FXML
    Button endGameButton;
    @FXML
    ChoiceBox strategyChoice;
    @FXML
    ChoiceBox colorChoice;
    @FXML
    TextField actualMoveField;
    @FXML
    TextArea madeMovesField;
    @FXML
    Canvas board;

    @FXML
    private void initialize() {
        prepareBoard();
        preparePawns();
        colorChoice.setDisable(false);
        strategyChoice.setDisable(false);
        beginGameButton.setDisable(false);
        endGameButton.setDisable(true);
        actualMoveField.setDisable(true);
        madeMovesField.setDisable(true);
    }

    @FXML
    private void handleBeginGameButton(ActionEvent event){
        colorChoice.setDisable(true);
        strategyChoice.setDisable(true);
        beginGameButton.setDisable(true);
        actualMoveField.setDisable(false);
        madeMovesField.setDisable(false);
        endGameButton.setDisable(false);
        actualMoveField.setText("");

        preparePlayers();
        if(colorChoice.getValue().toString().equals("Black"))
            moveWhiteComputer();

    }

    @FXML
    private void handleActualMove(ActionEvent event){
        if(colorChoice.getValue().toString().equals("White")){
            Helper.getInstance().setValue(actualMoveField.getText().toString());
            if(WhitePawn.getInstance().checkPossibleMove(BreakthroughController.getCoordinates(Helper.getInstance().getValue())) == false){
                madeMovesField.setText("Move " + actualMoveField.getText().toString() + " is not allowed!!!" + "\n" + madeMovesField.getText().toString());
                actualMoveField.setText("");
            }

            else{
                moveWhitePlayer();
                moveBlackComputer();
            }
        }
        else if(colorChoice.getValue().toString().equals("Black")){
            Helper.getInstance().setValue(actualMoveField.getText().toString());
            if(BlackPawn.getInstance().checkPossibleMove(BreakthroughController.getCoordinates(Helper.getInstance().getValue())) == false){
                madeMovesField.setText("Move " + actualMoveField.getText().toString() + " is not allowed!!!" + "\n" + madeMovesField.getText().toString());
                actualMoveField.setText("");
            }
            else
                moveBlackPlayer();

            if(WhitePawn.getInstance().getMoveNumber() == BlackPawn.getInstance().getMoveNumber())
                moveWhiteComputer();
        }
    }

    @FXML
    private void handleEndGameButton(ActionEvent event){
        WhitePawn.getInstance().renewWhitePawn();
        BlackPawn.getInstance().renewBlackPawn();
        Board.getInstance().redrawBoard();
        redrawBoard();
        colorChoice.setDisable(false);
        strategyChoice.setDisable(false);
        beginGameButton.setDisable(false);
        actualMoveField.setText("Enter your move here ...");
        actualMoveField.setDisable(true);
        madeMovesField.setText("");
        madeMovesField.setDisable(true);
        endGameButton.setDisable(true);

    }

    private void preparePlayers(){
        if(colorChoice.getValue().toString().equals("White")){
            player = new Contestant(new StrategyPlayer());
        }
        else if(colorChoice.getValue().toString().equals("Black")){
            player = new Contestant(new StrategyPlayer());
        }

        if(strategyChoice.getValue().toString().equals("Random move")){
            computer = new Contestant(new StrategyRandom());
        }
        else if(strategyChoice.getValue().toString().equals("Take pawn if possible")){
            computer = new Contestant(new StrategyTakePawn());
        }
    }

    private void moveWhitePlayer(){
        Helper.getInstance().setValue(actualMoveField.getText().toString());
        if(WhitePawn.getInstance().checkPossibleMove(BreakthroughController.getCoordinates(Helper.getInstance().getValue()))){
            if(!WhitePawn.getInstance().checkWhiteWin()){
                player.move(1, Board.getInstance().getBoard());
                if(WhitePawn.getInstance().checkWhiteWin()){
                    madeMovesField.setText("WHITE WINS!!!!" + "\n" + madeMovesField.getText().toString());
                    actualMoveField.setText("");
                }
                else{
                    madeMovesField.setText(actualMoveField.getText().toString() + "\n" + madeMovesField.getText().toString());
                    actualMoveField.setText("");
                }
                redrawBoard();
            }
        }
    }

    private void moveBlackPlayer(){
        Helper.getInstance().setValue(actualMoveField.getText().toString());
        if(BlackPawn.getInstance().checkPossibleMove(BreakthroughController.getCoordinates(Helper.getInstance().getValue()))){
            if(!BlackPawn.getInstance().checkBlackWin()){
                player.move(2, Board.getInstance().getBoard());
                if(BlackPawn.getInstance().checkBlackWin()){
                    madeMovesField.setText("BLACK WINS!!!!" + "\n" + madeMovesField.getText().toString());
                    actualMoveField.setText("");
                }

                else{
                    madeMovesField.setText(actualMoveField.getText().toString() + "\n" + madeMovesField.getText().toString());
                    actualMoveField.setText("");
                }
                redrawBoard();
            }
        }
    }

    private void moveBlackComputer(){
        if(!BlackPawn.getInstance().checkBlackWin()) {
            computer.move(2, Board.getInstance().getBoard());
            if (BlackPawn.getInstance().checkBlackWin())
                madeMovesField.setText("BLACK WINS!!!!" + "\n" + madeMovesField.getText().toString());
            redrawBoard();
        }
    }

    private void moveWhiteComputer(){
        if(!WhitePawn.getInstance().checkWhiteWin()){
            computer.move(1, Board.getInstance().getBoard());
            if(WhitePawn.getInstance().checkWhiteWin())
                madeMovesField.setText("WHITE WINS!!!!" + "\n" + madeMovesField.getText().toString());
            redrawBoard();
        }
    }

    private void prepareBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                drawField(col, row);
                if(Board.getInstance().getBoard()[row][col] == 1)
                    drawPawn(Color.WHITE, row, col);
                else if(Board.getInstance().getBoard()[row][col] == 2)
                    drawPawn(Color.BLACK, row, col);
            }
        }
    }

    private void redrawBoard(){
        prepareBoard();
    }

    private void drawField(int col, int row) {
        GraphicsContext gc = board.getGraphicsContext2D();
        int position = row + col;
        if (position%2 == 0) {
            gc.setFill(Color.SANDYBROWN);
        }
        else if (position%2 == 1){
            gc.setFill(Color.BROWN);
        }

        gc.fillRect(canvasSize/size * col, canvasSize/size * row, canvasSize/size, canvasSize/size);

    }

    private void drawPawn(Color color, int row, int col){
        int space = 10;
        GraphicsContext gc = board.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillOval(canvasSize/size * col + space/2, canvasSize/size * row + space/2, fieldSize - space, fieldSize - space);
    }

    private void preparePawns(){
        for(int row = 0; row < 2; row++)
            for(int col = 0; col < size; col++)
                drawPawn(Color.BLACK, row, col);

        for(int row = 6; row < size; row++)
            for(int col = 0; col < size; col++)
                drawPawn(Color.WHITE, row, col);
    }

    public static List<Coordinates> getCoordinates(String string){
        List<Coordinates> numericalCoordinates = new ArrayList<>(2);
        int rowActualPosition;
        int colActualPosition;
        int rowNextPosition;
        int colNextPosition;
        String actualMove = Helper.getInstance().getValue();
        String parts[] = actualMove.split("->");
        if(parts[0] == "" || parts[1] == "" || parts[0].length() != 2 || parts[1].length() != 2)
            return null;

        rowActualPosition = 8 - Integer.valueOf(String.valueOf(parts[0].charAt(1)));
        rowNextPosition = 8 - Integer.valueOf(String.valueOf(parts[1].charAt(1)));

        switch (parts[0].charAt(0)) {
            case 'a':  colActualPosition = 0;
                break;
            case 'b':  colActualPosition = 1;
                break;
            case 'c':  colActualPosition = 2;
                break;
            case 'd':  colActualPosition = 3;
                break;
            case 'e':  colActualPosition = 4;
                break;
            case 'f':  colActualPosition = 5;
                break;
            case 'g':  colActualPosition = 6;
                break;
            case 'h':  colActualPosition = 7;
                break;
            default:   colActualPosition = 8;
                break;
        }

        switch (parts[1].charAt(0)) {
            case 'a':  colNextPosition = 0;
                break;
            case 'b':  colNextPosition = 1;
                break;
            case 'c':  colNextPosition = 2;
                break;
            case 'd':  colNextPosition = 3;
                break;
            case 'e':  colNextPosition = 4;
                break;
            case 'f':  colNextPosition = 5;
                break;
            case 'g':  colNextPosition = 6;
                break;
            case 'h':  colNextPosition = 7;
                break;
            default:   colNextPosition = 8;
                break;
        }

        numericalCoordinates.add(new Coordinates(colActualPosition, rowActualPosition));
        numericalCoordinates.add(new Coordinates(colNextPosition, rowNextPosition));

        return numericalCoordinates;
    }

}