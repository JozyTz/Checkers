This is a basic implementation of checkers.

There are two heuristics implemented, the more complex one is active by default.
    To switch the heuristic, change the "return moveEvaluator(game)" lines in the minValue() and maxValue() methods
    in the AlphaBeta class to "return moveEvaluatorBasic(game)".

The search depth can be edited in the AlphaBeta method within the AlphaBeta class. The "for maxDepth..." line (currently
    line 20) can be changed to have whatever depth you wish. By default, it is set to 5.

To run the program, run the main method in the Checkers class. A GUI will pop up with a checkers game board.
The player is red by default and an alpha-beta agent will play black.
The pieces that can be moved are highlighted in blue.
The selected piece is highlighted in light blue
The available tiles to move the selected piece to are highlighted in green.
To play, click the piece you want to move, then click the space you want to move it to. The ai will then play it's turn
and pass the turn back to you. This will repeat until one player has no possible moves, then the game will close.


**Note**
There is a printout currently in AlphaBeta line 52 that displays all of the best moves the program could choose from for
that particular turn. I have left this in as it could be useful for understanding how a move is chosen.

The heuristic for choosing the best move isn't perfect. I have it weighted by friendly vs enemy pieces, defensive value
of a piece(friendly pieces near it), and distance to the double/king line. The ai is still pretty dumb and I don't know
much about checkers theory to make it better haha.
The basic heuristic just uses the friendly vs enemy piece count.

There is also a bug which I couldn't figure out regarding a nullpointer on the game.getMoves(playerTurn) call in the
minValue() and maxValue() functions in AlphaBeta class. It seems to occur close to the end of the game, so it could be
related to high-depth searches running out of moves to look for. You might not even run into it with general testing.
** ** **
