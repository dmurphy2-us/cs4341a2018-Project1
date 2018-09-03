package Players;

import Utilities.Move;
import Utilities.StateTree;

/**
 * This class is what you will extend when you make your player class.
 * Each player needs to have a getMove function which the referee calls.
 * The getMove function is probably where you want to do your min-maxing
 * and alpha-beta pruning, with helper functions and such.
 *
 * @author Ethan Prihar
 *
 */


public abstract class Player {

    String name; // the name of your player
    int turn; // the number corresponding to your turn (1 or 2)
    int timeLimit; // the amount of time (in seconds) you have to make a move
    int N; // the amount of pieces to connect in order to win

    public Player(String n, int t, int l)
    {
        name = n;
        turn = t;
        timeLimit = l;
    }

    public void setN(int n) {
        N = n;
    }

    public String getName() {
        return name;
    }

    // This is the method the referee will call when it wants a move from your player
    public abstract Move getMove(StateTree state);

    // Minimax $ Heuristic

    /*
     *Name: calculateHeuristic(StateTree board)
     *Type: private method
     *Description: Analyzes a given board and computes a heuristic based on its current state
     *Parameters:
     *    - board: the StateTree that represents the board
     *Returns: integer representing the calculated heuristic
     */

    public int calculateHeuristic(StateTree board) {
        int h = 0;
        int c = 0;
        StateTree tempBoard;
        int playerNumber;

        if (turn % 2 == 0){
            playerNumber = 2;
        }
        else {
            playerNumber = 1;
        }


        for (int i = 0; i < board.columns; i++) {
            tempBoard = board;
            if (board.validMove(new Move(false, i))) {
                tempBoard.makeMove(new Move(false, i));
                if (getPlayValue(tempBoard, playerNumber) > h){
                    c = i;
                }
            }
        }
        return c;
    }

    /*
     * Name: getPlayValue(StateTree board, int playerNumber)
     * Type: public method
     * Description: gives value of the board state
     * horizontal, vertical, and diagonal checks)
     * Parameters:
     *  - board: The state tree of the current board move being examined
     * 	- playerNumber: The number of the player whose moves to check for
     * Returns: An integer that is the heuristic value of the current board state
     */
    public int getPlayValue(StateTree board, int playerNumber) {
        int h;

        int horizontalValue  = checkHorizontally(board, playerNumber);
        int verticalValue  = checkVertically(board, playerNumber);
        int diagonal1Value = checkDiagonally1(board, playerNumber);
        int diagonal2Value = checkDiagonally2(board, playerNumber);
        
        h = horizontalValue+verticalValue+diagonal1Value+diagonal2Value;
        return h;
    }


    public int checkHorizontally(StateTree board, int playerNumber){
        int h = 0;
        int max = 0;
        int[][] boardMatrix;
        boardMatrix = board.getBoardMatrix();

        for (int i = 0; i < board.rows; i++) {
            max = 0;
            for (int j = 0; j<board.columns; j++){
                if (boardMatrix[i][j] == playerNumber){
                    max++;
                    if (j == board.columns-1){
                        h = h+max*max;
                    }
                }
                else {
                    h = h+max*max;
                    max = 0;
                }
            }
        }
        return h;
    }

    public int checkVertically(StateTree board, int playerNumber){
        int h = 0;
        int max = 0;
        int[][] boardMatrix;
        boardMatrix = board.getBoardMatrix();

        for (int j = 0; j < board.columns; j++) {
            max = 0;
            for (int i = 0; i<board.rows; i++){
                if (boardMatrix[i][j] == playerNumber){
                    max++;
                    if (j == board.rows-1){
                        h = h+max*max;
                    }
                }
                else {
                    h = h+max*max;
                    max = 0;
                }
            }
        }
        return h;
    }

    public int checkDiagonally2(StateTree board, int playerNumber){
        int h = 0;
        int max = 0;
        int[][] boardMatrix;
        boardMatrix = board.getBoardMatrix();
        int i = board.rows;
        int j = board.columns;

        for (int j2 = 0; j2 < board.columns; j2++) {
            max = 0;
            for (j = 0; j<board.columns; j++){
                if (boardMatrix[i][j] == playerNumber){
                    max++;
                    if (j == board.columns-1 || i == 0){
                        h = h+max*max;
                        i = board.rows;
                        break;
                    }
                }
                else {
                    h = h+max*max;
                    max = 0;
                }
                i--;
            }
        }

        for (int i2 = 0; i2 < board.rows; i2++) {
            max = 0;
            for (i = 0; i<board.rows; i++){
                if (boardMatrix[i][j] == playerNumber){
                    max++;
                    if (j == 0 || i == board.rows-1){
                        h = h+max*max;
                        j = board.columns;
                        break;
                    }
                }
                else {
                    h = h+max*max;
                    max = 0;
                }
                j--;
            }
        }
        return h;
    }

    public int checkDiagonally1(StateTree board, int playerNumber){
        int h = 0;
        int max = 0;
        int[][] boardMatrix;
        boardMatrix = board.getBoardMatrix();
        int i = 0;
        int j = 0;

        for (int j2 = 0; j2 < board.columns; j2++) {
            max = 0;
            for (j = 0; j<board.columns; j++){
                if (boardMatrix[i][j] == playerNumber){
                    max++;
                    if (j == board.columns-1 || i == board.rows-1){
                        h = h+max*max;
                        i = 0;
                        break;
                    }
                }
                else {
                    h = h+max*max;
                    max = 0;
                }
                i++;
            }
        }

        for (int i2 = 0; i2 < board.rows; i2++) {
            max = 0;
            for (i = 0; i<board.rows; i++){
                if (boardMatrix[i][j] == playerNumber){
                    max++;
                    if (j == board.columns-1 || i == board.rows-1){
                        h = h+max*max;
                        j = 0;
                        break;
                    }
                }
                else {
                    h = h+max*max;
                    max = 0;
                }
                j++;
            }
        }
        return h;
    }
}