package simplechat;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 *
 */
public class ChatClient extends AbstractClient {
    //Instance variables **********************************************

    /**
     * The interface type variable. It allows the implementation of the display
     * method in the client.
     */
    ChatIF clientUI;
    MusicIF musicUI;
    //Constructors ****************************************************
    /**
     * Constructs an instance of the chat client.
     *
     * @param host The server to connect to.
     * @param port The port number to connect on.
     * @param clientUI The interface type variable.
     */
    public ChatClient(String host, int port, ChatIF clientUI)
            throws IOException {
        super(host, port); //Call the superclass constructor
        this.clientUI = clientUI;
        openConnection();
        //sendToServer("#login "+username);
    }

    public ChatClient(String host, int port, String username, ChatIF clientUI)
            throws IOException {
        super(host, port); //Call the superclass constructor
        this.clientUI = clientUI;
        openConnection();
        sendToServer("#join common");
        sendToServer("#login " + username);
        //sendToServer("join common");
    }
    
        public ChatClient(String host, int port, String username, ChatIF clientUI,MusicIF musicUI)
            throws IOException {
        super(host, port); //Call the superclass constructor
        this.clientUI = clientUI;
        this.musicUI = musicUI;
        openConnection();
        sendToServer("#join common");
        sendToServer("#login " + username);
        //sendToServer("join common");
    }

    //Instance methods ************************************************
    /**
     * This method handles all data that comes in from the server.
     *
     * @param msg The message from the server.
     */
    public void handleMessageFromServer(Object msg) {
        if (msg instanceof musicEnvelope) {
            musicEnvelope me = (musicEnvelope) msg;
            System.out.println(me.getNote());
            musicUI.sing(me.getNote());

        } else {
            clientUI.display(msg.toString());
        }

    }

    /**
     * This method handles all data coming from the UI
     *
     * @param message The message from the UI.
     */
    public void handleMessageFromClientUI(String message) {

        if (message.charAt(0) == '#') {

            handleClientCommand(message);
        } else {

            try {
                sendToServer(message);
            } catch (IOException e) {
                clientUI.display("Could not send message to server.  Terminating client.");
                quit();
            }
        }
    }

    public void handleClientCommand(String message) {

        if (message.startsWith("#getPort")) {

            clientUI.display(String.valueOf(getPort()));
        } else if (message.startsWith("#logOff")) {

            try {
                closeConnection();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else if (message.startsWith("#logOn")) {

            try {
                openConnection();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else if (message.startsWith("#quit")) {
            quit();
        } else if (message.startsWith("#setHost")) {
            setHost(message.split(" ")[1]);
        } else if (message.startsWith("#setPort")) {
            setPort(Integer.parseInt(message.split(" ")[1]));
        } else if (message.startsWith("#sendTicTacToe")) {

            try {
                String recipient = message.split(" ")[1];
                int[][] board = new int[3][3];
                Envelope e = new Envelope(recipient, board);
                sendToServer(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        else if (message.startsWith("#music")) {
//            try {
//                sendToServer(message);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        } else {
//            try {
//                sendToServer(message);
//            } catch (IOException ioe) {
//
//                clientUI.display("Could not send message to server. Terminating message");
//                ioe.printStackTrace();
//                quit();
//            }
//        }
    }

    public void handleMessageFromClientUI(Object message) {
        try {
            sendToServer(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This method terminates the client.
     */
    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
        }
        System.exit(0);
    }

    protected void connectionException(Exception exception) {

        System.out.println("Server Shutdown");

        connectionClosed();

    }
}

//End of ChatClient class
