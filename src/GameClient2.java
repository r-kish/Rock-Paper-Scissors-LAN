/*
 * Richard Kish
 * 13 December 2022
 * GameClient.java - CSC 200 Final Project
 * This java file is the client for a two-player rock, paper, scissors game
 * This client will display instructions to the player, and will communicate with the server
 * to allow two clients to play a match of rock, paper, scissors
 */

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Exercise33_10Client extends Application {
    // Text field for chat
    private TextField tf = new TextField();

    // Text field for name
    private TextField tfInput = new TextField("Enter a name");

    // Create three buttons for UI
    private Button one = new Button("Rock");
    private Button two = new Button("Paper");
    private Button three = new Button("Scissors");

    // Text area to display contents
    private TextArea ta = new TextArea();

    // Indicate if game is still in progress
    private boolean continueToPlay = true;

    // Indicate if client is paused
    private boolean waiting = true;

    // Indicate player choice
    private int choice = 0;

    // Socket
    private Socket socket;

    // IO streams
    private DataOutputStream toServer;
    private DataInputStream fromServer;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        ta.setWrapText(true);

        // Set stage
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Choose your weapon:"), 0, 0);
        gridPane.add(one, 1, 0);
        gridPane.add(two, 2, 0);
        gridPane.add(three, 3, 0);
        BorderPane pane = new BorderPane();
        pane.setTop(gridPane);
        pane.setCenter(new ScrollPane(ta));

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 300, 200);
        primaryStage.setTitle("Rock, Paper, Scissors Client"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        // Register button listeners
        one.setOnAction(e -> sendOne());
        two.setOnAction(e -> sendTwo());
        three.setOnAction(e -> sendThree());

        try {
            ta.appendText("Welcome to ROCK PAPER SCISSORS!\nRemember...\n-Rock beats Scissors\n-Paper beats Rock\n" +
                    "-Scissors beats Paper\n\nWaiting for another player to join...\n");

            // Create a socket to connect to the server
            socket = new Socket("localhost", 8000);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());

            // Start a new thread for receiving messages
            new Thread(() -> run()).start();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Run a thread
    public void run(){
        try{
            //
            int outcome = 0;
            while(continueToPlay){
                int player = fromServer.readInt();
                if (player == 1) {
                    ta.appendText("All set!     [You are Player 1]\n");
                } else if (player == 2) {
                    ta.appendText("All set!     [You are Player 2]\n");
                }

                ta.appendText("\nPlayer " + player + ", choose your weapon: ");
                waitForPlayer(); // wait for first move
                fromServer.readInt();
                outcome = fromServer.readInt();
                //Win = 1, Lose = 2, Draw = 3
                // Choices: Rock = 1, Paper = 2, Scissors = 3
                if (outcome == 1) {
                    ta.appendText("\nYou win!");
                }   else if (outcome == 2) {
                    if (player == 1) {
                        ta.appendText("\nYou lose!");
                        if (choice == 1) {
                            ta.appendText(" Player 2 chose Paper.");
                        } else if (choice == 2) {
                            ta.appendText(" Player 2 chose Scissors.");
                        } else if (choice == 3) {
                            ta.appendText(" Player 2 chose Rock.");
                        }
                    } else if (player == 2) {
                        ta.appendText("\nYou lose!");
                        if (choice == 1) {
                            ta.appendText(" Player 1 chose Paper.");
                        } else if (choice == 2) {
                            ta.appendText(" Player 1 chose Scissors.");
                        } else if (choice == 3) {
                            ta.appendText(" Player 1 chose Rock.");
                        }
                    }
                }   else if (outcome == 3) {
                    ta.appendText("\nDraw game! You both selected the same weapon.");
                }

                // Clients end here, must rejoin to restart game
                continueToPlay = false;
                ta.appendText("\nPlease leave and rejoin the server to play again.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Client will wait until server has received all moves
    private void waitForPlayer() throws InterruptedException, IOException {
        while (waiting) {
            Thread.sleep(100);
        }
        waiting = true;
    }

    // Send the server "1"
    private void sendOne() {
        try {
            ta.appendText("Rock\nPlease wait for the other player...\n");
            this.choice = 1; // For result message
            toServer.writeInt(1);
            toServer.flush();
            waiting = fromServer.readBoolean(); // Stop waiting
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Send the server "2"
    private void sendTwo() {
        try {
            ta.appendText("Paper\nPlease wait for the other player...\n");
            this.choice = 2; // For result message
            toServer.writeInt(2);
            toServer.flush();
            waiting = fromServer.readBoolean(); // Stop waiting
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Send the server "3"
    private void sendThree() {
        try {
            ta.appendText("Scissors\nPlease wait for the other player...\n");
            this.choice = 3; // For result message
            toServer.writeInt(3);
            toServer.flush();
            waiting = fromServer.readBoolean(); // Stop waiting
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
