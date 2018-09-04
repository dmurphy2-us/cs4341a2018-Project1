package Players;

import Utilities.Move;
import Utilities.StateTree;


/**
 * This is an example of how to make a player.
 * This player is extremely simple and does no tree building
 * but its good to test against at first.
 *
 * @author Ethan Prihar
 *
 */
public class SimplePlayer1 extends Player
{
    public SimplePlayer1(String n, int t, int l)
    {
        super(n, t, l);
    }

    public Move getMove(StateTree state)
    {
        StateTree tempState;
        tempState = state;

        int opponentNumber;
        int playerNumber;

        if (turn % 2 == 0){
            playerNumber = 2;
        }
        else {
            playerNumber = 1;
        }

        int h = -100000;
        int h2 = 100000;
        int h3 = -100000;
        int c = 0;

        // a set depth 3 minimax search through the possible upcoming board states
        for (int e = 0; e < state.columns; e++){
            if (tempState.validMove(new Move(false, e))) {
                tempState.makeMove(new Move(false, e));
                for (int f = 0; f < state.columns; f++){
                    if (tempState.validMove(new Move(false, f))) {
                        tempState.makeMove(new Move(false, f));
                        h3 = getHueristic(tempState, playerNumber);
                        if (h3 < h2){
                            h2 = h3;
                            if (h2 > h){
                                h = h2;
                                c = e;
                            }
                        }
                    }
                }
            }
        }

        // returns next best move
        return new Move(false, c);
    }
}