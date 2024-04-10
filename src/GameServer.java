/*
 * Richard Kish
 * 13 December 2022
 * GameServer.java - CSC 200 Final Project
 * This java file is the server for a two-player rock, paper, scissors game
 * This server will wait for two players to connect before initiating a thread.
 * This server will determine a winner, and notify both players.
 */

import java.io.*;
import java.net.*;
import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class GameServer extends Application {
    // Text area for displaying contents
    private TextArea ta = new TextArea();

    // Session count
    private int session = 1;

    // Mapping of sockets to output streams
    private Hashtable outputStreams = new Hashtable();

    // Server socket
    private ServerSocket serverSocket;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        ta.setWrapText(true);

        // Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(ta), 400, 200);
        primaryStage.setTitle("Rock, Paper, Scissors Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        // Start a new session
        new Thread(() -> listen()).start();
    }

    private void listen() {
        try {
            // Create a server socket
            serverSocket = new ServerSocket(8000);
            ta.appendText("Rock, Paper, Scissors Server started: " + new Date() + '\n');


            while (true) {
                // Create waiting room for two players to join
                ta.appendText("Waiting for players to join session #" + session + "...\n");

                // Connect with Player 1
                Socket player1 = serverSocket.accept();
                ta.appendText("Player 1 joined session " + session + ": " + new Date() + "\n");

                // Connect with Player 2
                Socket player2 = serverSocket.accept();
                ta.appendText("Player 2 joined session " + session + ": " + new Date() + "\n");

                // Start new session
                ta.appendText("Session " + session + " started: " + new Date() + "\n");
                ServerThread task = new ServerThread(player1, player2);
                new Thread(task).start();
                this.session++;
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    // Used to get the output streams
    Enumeration getOutputStreams(){
        return outputStreams.elements();
    }

    // This class is responsible for creating a thread to host a session of two players
    class ServerThread extends Thread {
        // Initialize sockets
        private Socket player1;
        private Socket player2;

        // Construct a thread
        public ServerThread(Socket player1, Socket player2) {
            this.player1 = player1;
            this.player2 = player2;

            start();
        }

        // Run a thread
        public void run() {
            int jack = 0;
            try {
                // Player numbers
                int player1Num = 1;
                int player2Num = 2;
                int winner = -1;

                // Create data input and output streams
                DataInputStream fromPlayer1 = new DataInputStream(player1.getInputStream());
                DataOutputStream toPlayer1 = new DataOutputStream(player1.getOutputStream());
                DataInputStream fromPlayer2 = new DataInputStream(player2.getInputStream());
                DataOutputStream toPlayer2 = new DataOutputStream(player2.getOutputStream());



                // Continuously serve the client the game
                while (true) {
                    toPlayer1.writeInt(player1Num);
                    toPlayer1.flush();
                    toPlayer2.writeInt(player2Num);
                    toPlayer2.flush();

                    // Player 1 choice
                    int play1 = fromPlayer1.readInt();
                    // Player 2 choice
                    int play2 = fromPlayer2.readInt();

                    // Disengage wait loop
                    toPlayer1.writeBoolean(false);
                    toPlayer1.flush();
                    toPlayer2.writeBoolean(false);
                    toPlayer2.flush();

                    // Incoming logic: Rock = 1, Paper = 2, Scissors = 3
                    // Outgoing login: Win = 1, Lose = 2, Draw = 3
                    if (play1 == 1 && play2 == 1) { //Player 1: Rock - Player 2: Rock
                        toPlayer1.writeInt(3);
                        toPlayer1.flush();
                        toPlayer2.writeInt(3);
                        toPlayer2.flush();
                    } else if (play1 == 1 && play2 == 2) { //Player 1: Rock - Player 2: Paper
                        toPlayer1.writeInt(2);
                        toPlayer1.flush();
                        toPlayer2.writeInt(1);
                        toPlayer2.flush();
                    } else if (play1 == 1 && play2 == 3) { //Player 1: Rock - Player 2: Scissors
                        toPlayer1.writeInt(1);
                        toPlayer1.flush();
                        toPlayer2.writeInt(2);
                        toPlayer2.flush();
                    } else if (play1 == 2 && play2 == 1) { //Player 1: Paper - Player 2: Rock
                        toPlayer1.writeInt(1);
                        toPlayer1.flush();
                        toPlayer2.writeInt(2);
                        toPlayer2.flush();
                    } else if (play1 == 2 && play2 == 2) { //Player 1: Paper - Player 2: Paper
                        toPlayer1.writeInt(3);
                        toPlayer1.flush();
                        toPlayer2.writeInt(3);
                        toPlayer2.flush();
                    } else if (play1 == 2 && play2 == 3) { //Player 1: Paper - Player 2: Scissors
                        toPlayer1.writeInt(2);
                        toPlayer1.flush();
                        toPlayer2.writeInt(1);
                        toPlayer2.flush();
                    } else if (play1 == 3 && play2 == 1) { //Player 1: Scissors - Player 2: Rock
                        toPlayer1.writeInt(2);
                        toPlayer1.flush();
                        toPlayer2.writeInt(1);
                        toPlayer2.flush();
                    } else if (play1 == 3 && play2 == 2) { //Player 1: Scissors - Player 2: Paper
                        toPlayer1.writeInt(1);
                        toPlayer1.flush();
                        toPlayer2.writeInt(2);
                        toPlayer2.flush();
                    } else if (play1 == 3 && play2 == 3) { //Player 1: Scissors - Player 2: Scissors
                        toPlayer1.writeInt(3);
                        toPlayer1.flush();
                        toPlayer2.writeInt(3);
                        toPlayer2.flush();
                    }
                    break;
                }

                // Show session complete time
                ta.appendText("Session" + " completed: " + new Date() + "\n");
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Run JavaFX
    public static void main(String[] args) {
        launch(args);
    }
}