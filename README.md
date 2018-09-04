# cs4341a2018-Project1
This is the code for Project 1 of CS4341.

This project was authored by Derek Murphy and Samuel Winter.

While writing this AI we focused on getting the heuristic value of using n^2. We created the heuristic values based off the players current board state and the most recently placed piece. We used the players concurrent items and used them to weigh the balance of other piece placements. This AI will look at the empty spaces that is needed to create a win and will look at how many spaces away the opponent's pieces are, to see if a possibility of collision based on objective decision making using the board state.

While writing this AI we ran into the issue with the referee code was not registering a turn change and the display of the board state does not seem to appear when a player has made a valid move. In contrast, it will first print out all the failed moves that are made by the players. Then the program will print out the final board state when a player has won.

Considering we were not supposed to alter the Referee or the Utilities classes, we thought this might be an issue with the referee class and left our code the way it way, considering the AI sucessfully followed our heuristic.

To test the heuristic was working we hardcoded a board display on each valid move that was made and ensured that the AI did not snake throught the columns as it did when the project framework was handed over.

Thanks for your consideration and time. 