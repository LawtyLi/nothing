
# CS611-Assignment 3
## Quoridor
---------------------------------------------------------------------------
Jian Xie

jianx@bu.edu

U75516303

Guanxi Li
guanxili@bu.edu
U14373976

## Files
1. Main.java: To run this game.
2. Game.java: An abstract class that defines the standard game flow. It serves as the parent class for `Quoridor` class. 
3. GameFactory.java: The purpose of this interface is to set a foundation for a factory pattern for `Game` objects.  
4.  InteractiveGameFactory.java: The `InteractiveGameFactory` class is a factory class designed to create and return a specific game instance based on user choices. 
5.  QuoridorGameFactory.java:  The `QuoridorGameFactory` class is a specific implementation of a factory class designed to instantiate and return an instance of the `Quoridor` game. It's a concrete implementation of the `GameFactory` interface, which prescribes a structure for game-creating factories.  
6. GameManager.java:  The `GameManager` class serves as the central control unit for the game's operations. It manages the game's workflow, from selecting teams and players to initiating the game's creation and user interactions. 
7.  TeamManager.java: The `TeamManager` class serves as a controller for team-related operations in the game.  
8. PlayerManager.java:  The `PlayerManager` class is focused on assisting with player-related tasks in the game. Specifically, it offers functionalities related to selecting players from a given team. 
9. GameUIManager.java:  The `GameUIManager` class manages the user interface aspects of the game, especially pertaining to team management and player selections. 
10. Quoridor.java:  The `Quoridor` class encapsulates the game's operational logic and covers all potential scenarios that might arise during gameplay. 
11. Team.java: An abstraction for teams, including team name, team members, player indexes in the match, scores, and team color attributes. 
12. Player.java: An abstract representation of a player, including player ID, player name, the piece they are playing with, whether they are in the game, their score, the number of walls, and a collection of positions where they have placed their pieces. 
13. Board.java: An abstraction for the game board, including board size and methods for drawing the board, displaying the board, and more. 
14. Piece.java: Abstraction of a game piece, responsible for initializing the piece and containing only the "symbol" attribute. 
15. Tile.java: Abstraction of a game board tile. Each tile includes properties such as coordinates, color, and a game piece. 
16. Color.java: A class that contains the names of colors. 
17. InputValidator.java: Contains a variety of methods for validating user input for checking input validity. 
18. Wall:  The `Wall` class represents a wall object in the game. It encompasses the wall's coordinates, direction, and color. 

## Notes

1.  We incorporated the Factory Pattern to standardize our code. 

2.  We introduced a feature allowing users to exit at any time by typing "quit". 

3.  The board size is adjustable, with three available sizes: 7x7, 9x9, and 11x11. 

4.  During any stage of the game, players can press the 'r' key to return to the previous step. 

5. To enhance gameplay, we added the following features:

   - During the move phase, the board highlights potential moves with numerical indicators, allowing players to simply input the corresponding number.

   - In the "place the wall" phase, to simplify wall placement for players, we provided various coordinate perspectives. 

             1   2   3   4   5   6   7   8
         1   |   |   |   |   |   |   |   |
          ___+___+___+___+___+___+___+___+___
         2   |   |   |   | T |   |   |   |
          ___+___+___+___+___+___+___+___+___
         3   |   |   |   |   |   |   |   |
          ___+___+___+___+___+___+___+___+___
         4   |   |   |   |   |   |   |   |
          ___+___+___+___+___+___+___+___+___
         5   |   |   |   |   |   |   |   |
          ___+___+___+___+___+___+___+___+___
         6   |   |   |   |   |   |   |   |
          ___+___+___+___+___+___+___+___+___
         7   |   |   |   |   |   |   |   |
          ___+___+___+___+___+___+___+___+___
         8   |   |   |   | R |   |   |   |
          ___+___+___+___+___+___+___+___+___
         9   |   |   |   |   |   |   |   |
         

   As illustrated, players only need to input three numbers, corresponding to the three coordinates of the wall. The order of the second and third coordinates is interchangeable, meaning inputting "4 5 6" and "4 6 5" both result in successful wall placement.
    

---------------------------------------------------------------------------


## How to compile and run
1. Navigate to the directory "src" after unzipping the files

2. Run the following instructions:
    javac Main.java

​       java Main

## Input/Output Example

Here‘s an example of playing Quoridor.

```
******************************************************
*               Welcome to our playroom              *
* Choose a game that you wanna play.                 *
* We have a list of games for you to choose from.    *
* 1. Quoridor                                        *
* 2. TBD                                             *
* 3. TBD                                             *
******************************************************
Select a game:
9
No corresponding game available at the moment.
Select a game:
1
Enter the number of teams (1-5): 1
Enter the name of the team1: 1
Enter the team color (red, blue, green, yellow, or purple): red
Enter the number of players for Team 1 (2-5): 2
Enter the name of Player 1 for Team 1: 1
Enter the name of Player 2 for Team 1: 2
Select Team 1 (by team name or 'bot'): 1
Select Team 2 (by team name or 'bot'): 1
Select a player1 from Team 1:
1. 1
2. 2
Enter the number of the player you choose: 1
Select a player2 from Team 1:
1. 1
2. 2
Enter the number of the player you choose: 2
Now input your board size of this round(7 or 9 or 11)
9

Player 1, please choose a letter as your piece (or type 'quit' to exit): t
Player 2, please choose a letter as your piece (or type 'quit' to exit): p
   |   |   |   | T |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | P |   |   |   |

Player1 1 move or place a wall :
Enter 'm' to move or 'p' to place a wall (or 'quit' to exit): m
   |   |   | 1 | T | 2 |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | 3 |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | P |   |   |   |
Please choose a number corresponding to a valid move: 3
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | T |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | P |   |   |   |
Player2 2 move or place a wall :
Enter 'm' to move or 'p' to place a wall (or 'quit' to exit): m
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | T |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | 1 |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   | 2 | P | 3 |   |   |
Please choose a number corresponding to a valid move: 1
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | T |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | P |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
Player1 1 move or place a wall :
Enter 'm' to move or 'p' to place a wall (or 'quit' to exit): m
   |   |   |   | 1 |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   | 2 | T | 3 |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | 4 |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | P |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
......Omitted the middle process because it's too long.
Player2 2 move or place a wall :
Enter 'm' to move or 'p' to place a wall (or 'quit' to exit): p
Enter 1 for horizontal wall or 2 for vertical wall (You can change the choice anytime until a valid wall is placed): 1
  1   2   3   4   5   6   7   8   9
    |   |   |   |   |   |   |   |
1___+___+___+___+___+___+___+___+___
    |   |   |   | T |   |   |   |
2___+___+___+___+___+___+___+___+___
    |   |   |   |   |   |   |   |
3___+___+___+___+___+___+___+___+___
    |   |   |   |   |   |   |   |
4___+___+___+___+___+___+___+___+___
    |   |   |   |   |   |   |   |
5___+___+___+___+___+___+___+___+___
    |   |   |   |   |   |   |   |
6___+___+___+___+___+___+___+___+___
    |   |   |   |   |   |   |   |
7___+___+___+___+___+___+___+___+___
    |   |   |   | R |   |   |   |
8___+___+___+___+___+___+___+___+___
    |   |   |   |   |   |   |   |
Enter wall coordinates in the format “row column1 column2” like '4 5 6'(or 'r' to return): 4 5 6
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | T |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | R |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
Player2 2 has 9 walls left.

Player1 1 move or place a wall :
Enter 'm' to move or 'p' to place a wall (or 'quit' to exit): p
Enter 1 for horizontal wall or 2 for vertical wall (You can change the choice anytime until a valid wall is placed): 2
    1   2   3   4   5   6   7   8
1   |   |   |   |   |   |   |   |
 ___+___+___+___+___+___+___+___+___
2   |   |   |   | T |   |   |   |
 ___+___+___+___+___+___+___+___+___
3   |   |   |   |   |   |   |   |
 ___+___+___+___+___+___+___+___+___
4   |   |   |   |   |   |   |   |
 ___+___+___+___+___+___+___+___+___
5   |   |   |   |   |   |   |   |
 ___+___+___+___+___+___+___+___+___
6   |   |   |   |   |   |   |   |
 ___+___+___+___+___+___+___+___+___
7   |   |   |   |   |   |   |   |
 ___+___+___+___+___+___+___+___+___
8   |   |   |   | R |   |   |   |
 ___+___+___+___+___+___+___+___+___
9   |   |   |   |   |   |   |   |
Enter wall coordinates in the format “column row1 row2” like '4 5 6'(or 'r' to return): 6 5 6
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | T |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | R |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
Player1 1 has 9 walls left.

Player2 2 move or place a wall :
Enter 'm' to move or 'p' to place a wall (or 'quit' to exit): p
Enter 1 for horizontal wall or 2 for vertical wall (You can change the choice anytime until a valid wall is placed): 1
  1   2   3   4   5   6   7   8   9
    |   |   |   |   |   |   |   |
1___+___+___+___+___+___+___+___+___
    |   |   |   | T |   |   |   |
2___+___+___+___+___+___+___+___+___
    |   |   |   |   |   |   |   |
3___+___+___+___+___+___+___+___+___
    |   |   |   |   |   |   |   |
4___+___+___+___+___+___+___+___+___
    |   |   |   |   |   |   |   |
5___+___+___+___+___+___+___+___+___
    |   |   |   |   |   |   |   |
6___+___+___+___+___+___+___+___+___
    |   |   |   |   |   |   |   |
7___+___+___+___+___+___+___+___+___
    |   |   |   | R |   |   |   |
8___+___+___+___+___+___+___+___+___
    |   |   |   |   |   |   |   |
Enter wall coordinates in the format “row column1 column2” like '4 5 6'(or 'r' to return): 6 5 6
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | T |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | R |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
Player2 2 has 8 walls left.

Player1 1 move or place a wall :
Enter 'm' to move or 'p' to place a wall (or 'quit' to exit): p
Enter 1 for horizontal wall or 2 for vertical wall (You can change the choice anytime until a valid wall is placed): 2
    1   2   3   4   5   6   7   8
1   |   |   |   |   |   |   |   |
 ___+___+___+___+___+___+___+___+___
2   |   |   |   | T |   |   |   |
 ___+___+___+___+___+___+___+___+___
3   |   |   |   |   |   |   |   |
 ___+___+___+___+___+___+___+___+___
4   |   |   |   |   |   |   |   |
 ___+___+___+___+___+___+___+___+___
5   |   |   |   |   |   |   |   |
 ___+___+___+___+___+___+___+___+___
6   |   |   |   |   |   |   |   |
 ___+___+___+___+___+___+___+___+___
7   |   |   |   |   |   |   |   |
 ___+___+___+___+___+___+___+___+___
8   |   |   |   | R |   |   |   |
 ___+___+___+___+___+___+___+___+___
9   |   |   |   |   |   |   |   |
Enter wall coordinates in the format “column row1 row2” like '4 5 6'(or 'r' to return): 4 5 6
This wall forms an enclosed space. Choose another location.
Enter wall coordinates in the format “column row1 row2” like '4 5 6'(or 'r' to return): r
Returning to choose wall orientation.
Enter 1 for horizontal wall or 2 for vertical wall (You can change the choice anytime until a valid wall is placed): r
Enter 'm' to move or 'p' to place a wall (or 'quit' to exit): m
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | T |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | 1 |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   | 2 | R | 3 |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | 4 |   |   |   |
Please choose a number corresponding to a valid move: 4
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | T |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   |   |   |   |   |
___+___+___+___+___+___+___+___+___
   |   |   |   | R |   |   |   |
Congratulations! Player1 1 win the game!
No scoring will be done when members of the same team play against each other.

Team 1 Score: 0
Players in Team 1:
1 Score: 0
2 Score: 0
You can choose 1. play another round 2. return to the game selection 3. exit the game system
3
```