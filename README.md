# Rock Paper Scissors LAN Java Edition
This was the capstone project for my computer science course at SUNY Finger Lakes, and the goal was to re-create a well-known game into a network-based computer game using Java and JavaFX. 

The game chosen was Rock Paper Scissors. 

Below is the Unified Modeling Language chart for the design of the game, from client to server:

![Picture](https://github.com/r-kish/Rock-Paper-Scissors-LAN/blob/main/RPS%20ClientServer%20-%20UML.png)

Input	Result
Client 1	Client 2	Client 1	Client 2
R	R	Draw	Draw
S	S	Draw	Draw
P	P	Draw	Draw
R	S	You Win	You Lose
S	R	You Lose	You Win
R	P	You Lose	You Win
P	R	You Win	You Lose
S	P	You Win	You Lose
P	S	You Lose	You Win
