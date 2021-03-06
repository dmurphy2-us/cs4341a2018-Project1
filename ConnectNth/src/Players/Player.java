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
     *Name: calculateHeuristic(StateTree board, int playerNumber)
     *Type: private method
     *Description: Analyzes a given board and computes a heuristic based on its current state
     *Parameters:
     *    - board: the StateTree that represents the board
     *Returns: integer representing the column for next best move
     */

    public int calculateHeuristic(StateTree board, int playerNumber) {
        int h = -10000;
        int c = 0;
        StateTree tempBoard;
        int opponentNumber;

        if (playerNumber == 2){
            playerNumber = 2;
            opponentNumber = 1;
        }
        else {
            playerNumber = 1;
            opponentNumber = 2;
        }


        for (int i = 0; i < board.columns; i++) {
            tempBoard = board;
            if (board.validMove(new Move(false, i))) {
                tempBoard.makeMove(new Move(false, i));
                if (getPlayValue(tempBoard, playerNumber) - getPlayValue(tempBoard, opponentNumber) > h){
                    h = getPlayValue(tempBoard, playerNumber);
                    c = i;
                }
                if (getPlayValue(tempBoard, playerNumber)  == -1 || getPlayValue(tempBoard, opponentNumber) == -1){
                    c = i;
                    return c;
                }
            }
            tempBoard = board;
            if (board.validMove(new Move(true, i))) {
                tempBoard.makeMove(new Move(true, i));
                if (getPlayValue(tempBoard, playerNumber) - getPlayValue(tempBoard, opponentNumber) > h){
                    h = getPlayValue(tempBoard, playerNumber);
                    c = i * -1;
                }
                if (getPlayValue(tempBoard, playerNumber)  == -1 || getPlayValue(tempBoard, opponentNumber) == -1){
                    c = i * -1;
                    return c;
                }
            }
        }
        return c;
    }

    /*
     *Name: getHueristic(StateTree board, int playerNumber)
     *Type: private method
     *Description: Analyzes a given board and computes a heuristic based on its current state
     *Parameters:
     *    - board: the StateTree that represents the board
     *Returns: integer representing the calculated heuristic for next best move
     */
    public int getHueristic(StateTree board, int playerNumber) {
        int h = -10000;
        int c = 0;
        StateTree tempBoard;
        int opponentNumber;

        if (playerNumber == 2){
            opponentNumber = 1;
        }
        else {
            playerNumber = 1;
            opponentNumber = 2;
        }


        for (int i = 0; i < board.columns; i++) {
            tempBoard = board;
            if (board.validMove(new Move(false, i))) {
                tempBoard.makeMove(new Move(false, i));
                if (getPlayValue(tempBoard, playerNumber) - getPlayValue(tempBoard, opponentNumber) > h){
                    h = getPlayValue(tempBoard, playerNumber);
                    c = i;
                }
                if (getPlayValue(tempBoard, playerNumber)  == -1 || getPlayValue(tempBoard, opponentNumber) == -1){
                    h = -1;
                    return h;
                }
            }
            tempBoard = board;
            if (board.validMove(new Move(true, i))) {
                tempBoard.makeMove(new Move(true, i));
                if (getPlayValue(tempBoard, playerNumber) - getPlayValue(tempBoard, opponentNumber) > h){
                    h = getPlayValue(tempBoard, playerNumber);
                    c = i * -1;
                }
                if (getPlayValue(tempBoard, playerNumber)  == -1 || getPlayValue(tempBoard, opponentNumber) == -1){
                    h = -1;
                    return 10000;
                }
            }
        }
        return h;
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

        if (horizontalValue == -1 || verticalValue == -1 || diagonal1Value == -1 || diagonal2Value == -1){
            return -1;
        }
        //h = horizontalValue;
        return h;
    }


    /*
     * Name: checkHorizontally(StateTree board, int playerNumber)
     * Type: public method
     * Description: gets value of all horizontal connections
     * Parameters:
     *  - board: The state tree of the current board move being examined
     * 	- playerNumber: The number of the player whose moves to check for
     * Returns: An integer that is the heuristic value of the current board state for horizontal pieces
     */
    public int checkHorizontally(StateTree board, int playerNumber){
        int h = 0;
        int max;
        int concurrent;
        int[][] boardMatrix;
        boardMatrix = board.getBoardMatrix();

        for (int j = 0; j < board.rows - 1; j++) {
            max = 0;
            concurrent = 0;
            for (int i = 0; i < board.columns - 1; i++){
                try{
                    if (boardMatrix[i][j] == playerNumber){
                        max++;
                        concurrent++;
                        if (i == board.columns-1){
                            h = h+max*max;
                        }
                        if (concurrent == N){
                            return -1;
                        }
                    }
                    if (boardMatrix[i][j] == 0){
                        concurrent = 0;
                    }
                    else if (boardMatrix[i][j] != playerNumber && boardMatrix[i][j] != 0){
                        h = h+max*max;
                        max = 0;
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    h = h+max*max;
                    max = 0;
                    break;
                }
            }
        }
        return h;
    }

    /*
     * Name: checkVertically(StateTree board, int playerNumber)
     * Type: public method
     * Description: gets value of all Vertical connections
     * Parameters:
     *  - board: The state tree of the current board move being examined
     * 	- playerNumber: The number of the player whose moves to check for
     * Returns: An integer that is the heuristic value of the current board state for Vertical pieces
     */
    public int checkVertically(StateTree board, int playerNumber){
        int h = 0;
        int max;
        int concurrent;
        int[][] boardMatrix;
        boardMatrix = board.getBoardMatrix();

        for (int i = 0; i < board.columns - 1; i++) {
            max = 0;
            concurrent = 0;
            for (int j = 0; j<board.rows - 1; j++){
                try {
                    if (boardMatrix[i][j] == playerNumber){
                        max++;
                        concurrent++;
                        if (j == board.rows-1){
                            h = h+max*max;
                        }
                        if (concurrent == N){
                            return -1;
                        }
                    }
                    if (boardMatrix[i][j] == 0){
                        concurrent = 0;
                    }
                    else if (boardMatrix[i][j] != playerNumber && boardMatrix[i][j] != 0){
                        h = h+max*max;
                        max = 0;
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    h = h+max*max;
                    max = 0;
                    break;
                }
            }
        }
        return h;
    }

    /*
     * Name: checkDiagonally2(StateTree board, int playerNumber)
     * Type: public method
     * Description: gets value of all Diagonal2 connections (up and to the right)
     * Parameters:
     *  - board: The state tree of the current board move being examined
     * 	- playerNumber: The number of the player whose moves to check for
     * Returns: An integer that is the heuristic value of the current board state for Diagonal2 pieces (up and to the right)
     */
    public int checkDiagonally2(StateTree board, int playerNumber){
        int h = 0;
        int max;
        int concurrent;
        int[][] boardMatrix;
        boardMatrix = board.getBoardMatrix();
        int x = 0;
        int y = 0;

        for (int i = 0; i < board.columns - 1; i++){
            x = i;
            max = 0;
            concurrent = 0;
            for (int j = 0; j<board.rows - 1; j++){
                try {
                    if (boardMatrix[x][j] == playerNumber){
                        max++;
                        concurrent++;
                        if (x == board.columns-2){
                            h = h+max*max;
                            break;
                        }
                        if (concurrent == N){
                            return -1;
                        }
                    }
                    if (boardMatrix[i][j] == 0){
                        concurrent = 0;
                    }
                    else if (boardMatrix[i][j] != playerNumber && boardMatrix[i][j] != 0){
                        h = h+max*max;
                        max = 0;
                    }
                    if (x == board.columns-2){
                        break;
                    }
                    x++;
                }
                catch (IndexOutOfBoundsException e) {
                    h = h+max*max;
                    max = 0;
                    break;
                }
            }
        }

        for (int j = 1; j < board.rows - 1; j++){
            y = j;
            max = 0;
            concurrent = 0;
            for (int i = 0; i<board.columns - 1; i++){
                try {
                    if (boardMatrix[i][y] == playerNumber){
                        max++;
                        concurrent++;
                        if (y == board.rows-2){
                            h = h+max*max;
                            break;
                        }
                        if (concurrent == N){
                            return -1;
                        }
                    }
                    if (boardMatrix[i][j] == 0){
                        concurrent = 0;
                    }
                    else if (boardMatrix[i][j] != playerNumber && boardMatrix[i][j] != 0){
                        h = h+max*max;
                        max = 0;
                    }
                    if (y == board.rows-2){
                        break;
                    }
                    y++;
                }
                catch (IndexOutOfBoundsException e) {
                    h = h+max*max;
                    max = 0;
                    break;
                }

            }
        }

        return h;
    }

    /*
     * Name: checkDiagonally1(StateTree board, int playerNumber)
     * Type: public method
     * Description: gets value of all Diagonal1 connections (down and to the right)
     * Parameters:
     *  - board: The state tree of the current board move being examined
     * 	- playerNumber: The number of the player whose moves to check for
     * Returns: An integer that is the heuristic value of the current board state for Diagonal1 pieces (down and to the right)
     */
    public int checkDiagonally1(StateTree board, int playerNumber){
        int h = 0;
        int max = 0;
        int concurrent;
        int[][] boardMatrix;
        boardMatrix = board.getBoardMatrix();
        int x = 0;
        int y = 0;


        for (int i = 0; i < board.columns - 1; i++){
            x = i;
            max = 0;
            concurrent = 0;
            for (int j = board.rows-1; j>0; j--){
                try {
                    if (boardMatrix[x][j] == playerNumber){
                        max++;
                        concurrent++;
                        if (x == board.columns-2){
                            h = h+max*max;
                            break;
                        }
                        if (concurrent == N){
                            return -1;
                        }
                    }
                    if (boardMatrix[i][j] == 0){
                        concurrent = 0;
                    }
                    else if (boardMatrix[i][j] != playerNumber && boardMatrix[i][j] != 0){
                        h = h+max*max;
                        max = 0;
                    }
                    if (x == board.columns-2){
                        break;
                    }
                    x++;
                }
                catch (IndexOutOfBoundsException e) {
                    h = h+max*max;
                    max = 0;
                    break;
                }

            }
        }

        for (int j = board.rows -2; j > 1 ; j--){
            y = j;
            max = 0;
            concurrent = 0;
            for (int i = 0; i<board.columns - 1; i++){
                try {
                    if (boardMatrix[i][y] == playerNumber){
                        max++;
                        concurrent++;
                        if (y == 1){
                            h = h+max*max;
                            break;
                        }
                        if (concurrent == N){
                            return -1;
                        }
                    }
                    if (boardMatrix[i][j] == 0){
                        concurrent = 0;
                    }
                    else if (boardMatrix[i][j] != playerNumber && boardMatrix[i][j] != 0){
                        h = h+max*max;
                        max = 0;
                    }
                    if (y == 1){
                        break;
                    }
                    y--;
                }
                catch (IndexOutOfBoundsException e) {
                    h = h+max*max;
                    max = 0;
                    break;
                }

            }
        }

        return h;
    }
}