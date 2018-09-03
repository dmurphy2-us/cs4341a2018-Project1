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
        int h = -10000;
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
                    h = getPlayValue(tempBoard, playerNumber);
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
            
        //h = horizontalValue+verticalValue+diagonal1Value+diagonal2Value;
        h = horizontalValue;
        return h;
    }


    public int checkHorizontally(StateTree board, int playerNumber){
        int h = 0;
        int max = 0;
        int[][] boardMatrix;
        boardMatrix = board.getBoardMatrix();

        for (int j = 0; j < board.rows - 1; j++) {
            max = 0;
            for (int i = 0; i < board.columns - 1; i++){
                if (boardMatrix[i][j] == playerNumber){
                    max++;
                    if (i == board.columns-1){
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

        for (int i = 0; i < board.columns - 1; i++) {
            max = 0;
            for (int j = 0; j<board.rows - 1; j++){
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
        int x = 0;
        int y = 0;

        for (int i = 0; i < board.columns - 1; i++){
            x = i;
            for (int j = 0; j<board.rows - 1; j++){
                if (boardMatrix[x][j] == playerNumber){
                    max++;
                    if (x == board.columns-2){
                        h = h+max*max;
                        break;
                    }
                }
                else {
                    h = h+max*max;
                    max = 0;
                }
                if (x == board.columns-2){
                    break;
                }
                x++;
            }
        }

        for (int j = 1; j < board.rows - 1; j++){
            y = j;
            for (int i = 0; i<board.columns - 1; i++){
                if (boardMatrix[i][y] == playerNumber){
                    max++;
                    if (y == board.rows-1){
                        h = h+max*max;
                        break;
                    }
                }
                else {
                    h = h+max*max;
                    max = 0;
                }
                if (y == board.rows-1){
                    break;
                }
                y++;
            }
        }

        return h;
    }

    public int checkDiagonally1(StateTree board, int playerNumber){
        int h = 0;
        int max = 0;
        int[][] boardMatrix;
        boardMatrix = board.getBoardMatrix();
        int x = 0;
        int y = 0;

        for (int i = 0; i < board.columns - 1; i++){
            x = i;
            for (int j = board.rows-1; j>0; j--){
                if (boardMatrix[x][j] == playerNumber){
                    max++;
                    if (x == board.columns-2){
                        h = h+max*max;
                        break;
                    }
                }
                else {
                    h = h+max*max;
                    max = 0;
                }
                if (x == board.columns-2){
                    break;
                }
                x++;
            }
        }

        for (int j = board.rows -2; j > 0 ; j--){
            y = j;
            for (int i = 0; i<board.columns - 1; i++){
                if (boardMatrix[i][y] == playerNumber){
                    max++;
                    if (y == 1){
                        h = h+max*max;
                        break;
                    }
                }
                else {
                    h = h+max*max;
                    max = 0;
                }
                if (y == 1){
                    break;
                }
                y--;
            }
        }

        return h;
    }
}