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

        for (int i = 0; i < state.columns; i++){
            if (tempState.validMove(new Move(false, i))) {
                tempState.makeMove(new Move(false, i));
                for (int j = 0; j < state.columns; j++){
                    if (tempState.validMove(new Move(false, j))) {
                        tempState.makeMove(new Move(false, j));
                        h3 = getHueristic(tempState, playerNumber);
                        if (h3 < h2){
                            h2 = h3;
                            if (h2 > h){
                                h = h2;
                                c = i;
                            }
                        }
                    }
                }
            }
        }

        return new Move(false, c);
    }
}