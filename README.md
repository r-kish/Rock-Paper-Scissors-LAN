# Rock Paper Scissors LAN Java Edition
This was the capstone project for my computer science course at SUNY Finger Lakes, and the goal was to re-create a well-known game into a network-based computer game using Java and JavaFX. 

The game chosen was Rock Paper Scissors. 

Below is the Unified Modeling Language chart for the design of the game, from client to server:

![Picture](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/RPS%20ClientServer%20-%20UML.png)

GameServer.Java is launched initially, and is responsible for communicating with multiple instances of GameClient.java.

The job of the client is to connect with the server, sending inputs for the server to calculate and return values to interpret.

![Welcome Screen](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/photos/Startup.png)   When you startup the client and join the server, you are greeted with this screen displaying the rules, and your choice of weapons.


Once you've joined the server, you will be told to wait for another player, or will be assigned Player 1 or 2.  

![Player 1 Wait...](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/photos/Wait%201.png)  ![Player 2 Wait...](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/photos/Wait%202.png)


Once the game is played, the game can end in... 
...a win: ![Win](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/photos/Win.png)  

...a loss: ![Lose](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/photos/Lose.png)  

...or a draw: ![Player 1 Draw](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/photos/Draw%201.png)  ![Player 2 Draw](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/photos/Draw%202.png)
