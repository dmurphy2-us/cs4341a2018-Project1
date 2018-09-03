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
        return new Move(false, calculateHeuristic(state));
    }
}