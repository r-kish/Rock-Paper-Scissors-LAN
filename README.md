# Rock Paper Scissors LAN ☕️ Java Edition 
This was the capstone project for my computer science course at SUNY Finger Lakes, and the goal was to re-create a well-known game into a network-based computer game using Java and JavaFX. 

The game chosen was ✊ Rock ✋ Paper ✌️ Scissors. 

Below is the Unified Modeling Language chart for the design of the game, from client to server:

![Picture](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/RPS%20ClientServer%20-%20UML.png)

GameServer.Java is launched initially, and is responsible for communicating and waiting for responses with multiple instances of GameClient.java. The server will always stay open, and will constantly be looking for two clients to create a match for.

The job of the client is to create a connection with the server, sending inputs for the server to calculate and return values to interpret.





The game in action
- 
- When the player starts up the client and joins the server, they are greeted with this screen displaying the rules, and their choice of weapons.

![Welcome Screen](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/photos/Startup.png)




- Once the player has joined the server, the player will be told to wait for another player, or will be assigned Player 1 or 2.  

![Player 1 Wait...](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/photos/Wait%201.png)  ![Player 2 Wait...](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/photos/Wait%202.png)



- Once the game is played, the game can end in... 

...a win: ![Win](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/photos/Win.png)  

...a loss: ![Lose](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/photos/Lose.png)  

...or a draw: ![Player 1 Draw](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/photos/Draw%201.png)  ![Player 2 Draw](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/photos/Draw%202.png)

In order to replay, the player must exit and restart the client. The server will keep of track of how many matches have been made.
