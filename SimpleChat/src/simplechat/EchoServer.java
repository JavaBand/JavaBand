package simplechat;

import java.io.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 */
public class EchoServer extends AbstractServer {
    //Class variables *************************************************

    /**
     * The default port to listen on.
     */
    final public static int DEFAULT_PORT = 5555;

    //Constructors ****************************************************
    /**
     * Constructs an instance of the echo server.
     *
     * @param port The port number to connect on.
     */
    public EchoServer(int port) {
        super(port);
    }

    //Instance methods ************************************************
    /**
     * This method handles any messages received from the client.
     *
     * @param msg The message received from the client.
     * @param client The connection from which the message originated.
     */
    public void handleMessageFromClient(Object msg, ConnectionToClient client) {
        String message = msg.toString();
        if (msg instanceof musicEnvelope) {
            
            System.out.println(msg);
            playMusicInRoom(msg, client);
            
        }
        else if (msg instanceof Envelope) {
            Envelope e = (Envelope) msg;
            String recipient = e.getRecipient();
            int[][] board = (int[][]) e.getContents();
            sendToAClient("You are playing tic tac toe!", recipient);
        } else if (message.charAt(0) == '#') {

            handleServerCommand(msg, client);
        } else {
            System.out.println("Message received: " + msg + " from " + client);
            this.sendToAllClientsInRoom(msg, client);
        }
    }

    public void handleServerCommand(Object msg, ConnectionToClient client) {
        String message = msg.toString();

        if (message.startsWith("#login")) {

            String username = message.split(" ")[1];
            client.setInfo("userName", username);
            //client.setInfo("room" , "common");
            this.sendToAllClientsInRoom(username + " has arrived!", client);

        } else if (message.startsWith("#music")) {

            String notePlayed = message.split(" ")[1];
            System.out.println(notePlayed);
            playMusicInRoom(notePlayed, client);

        } else if (message.startsWith("#w")) {

            String target = message.split(" ")[1];

            String pm = "";
            pm = message.substring(message.indexOf(" "), message.length());
            pm = pm.substring(pm.indexOf(" "), pm.length());

            sendToAClient(pm, target);

        } else if (message.startsWith("#join")) {
            String room = message.split(" ")[1];
            client.setInfo("room", room);

        } else if (message.startsWith("#intercom")) {

            String room = message.split(" ")[1];
            String[] tokens = message.split(" ");
            String intercomMsg = "";
            for (int i = 2; i < tokens.length; i++) {
                intercomMsg += tokens[i] + " ";
            }
            intercomMsg = intercomMsg.trim();

            sendIntercomMessage(room, intercomMsg);

        } else if (message.startsWith("#locate")) {

            String user = message.split(" ")[1];
            findUserInRoom(user);

        }
    }

    public void findUserInRoom(String user) {
        String room = "";
        Thread[] clientThreadList = getClientConnections();

        for (int i = 0; i < clientThreadList.length; i++) {

            ConnectionToClient clientProxy = ((ConnectionToClient) clientThreadList[i]);

            try {

                if (user.equals(clientProxy.getInfo("userName"))) {

                    room.equals(clientProxy.getInfo("room"));
                    System.out.println(user + " was found in room " + room);

                } else if (!user.equals(clientProxy.getInfo("userName"))) {
                    System.out.println(user + " was not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void sendIntercomMessage(String room, String intercomMsg) {

        Thread[] clientThreadList = getClientConnections();

        for (int i = 0; i < clientThreadList.length; i++) {

            ConnectionToClient clientProxy = ((ConnectionToClient) clientThreadList[i]);

            if (clientProxy.getInfo("room").equals(room)) {
                try {
                    clientProxy.sendToClient(intercomMsg);
                } catch (Exception ex) {
                    System.out.println("Failed to send pm");
                    ex.printStackTrace();
                }
            }
        }
    }

    public void playMusicInRoom(Object msg, ConnectionToClient client) {
        String room = client.getInfo("room").toString();
        musicEnvelope me = (musicEnvelope)msg;
        //String pm = message.substring(message.indexOf(" "), message.length());
        // pm = pm.substring(pm.indexOf(" "), pm.length());

        Thread[] clientThreadList = getClientConnections();

        for (int i = 0; i < clientThreadList.length; i++) {

            ConnectionToClient clientProxy = ((ConnectionToClient) clientThreadList[i]);

            if (clientProxy.getInfo("room").equals(room)) {
                try {
                    clientProxy.sendToClient(me);
                } catch (Exception ex) {
                    System.out.println("Failed to send pm");
                    ex.printStackTrace();
                }
            }
        }
    }

    public void sendToAClient(Object msg, String target) {
        String message = msg.toString();

        Thread[] clientThreadList = getClientConnections();

        for (int i = 0; i < clientThreadList.length; i++) {

            ConnectionToClient clientProxy = ((ConnectionToClient) clientThreadList[i]);

            if (clientProxy.getInfo("userName").equals(target)) {
                try {
                    clientProxy.sendToClient(message);
                } catch (Exception ex) {
                    System.out.println("Failed to send pm");
                    ex.printStackTrace();
                }
            }
        }
    }

    public void sendToAllClientsInRoom(Object msg, ConnectionToClient client) {
        String room = client.getInfo("room").toString();
        String message = msg.toString();
        //String pm = message.substring(message.indexOf(" "), message.length());
        // pm = pm.substring(pm.indexOf(" "), pm.length());

        Thread[] clientThreadList = getClientConnections();

        for (int i = 0; i < clientThreadList.length; i++) {

            ConnectionToClient clientProxy = ((ConnectionToClient) clientThreadList[i]);

            if (clientProxy.getInfo("room").equals(room)) {
                try {
                    clientProxy.sendToClient(message);
                } catch (Exception ex) {
                    System.out.println("Failed to send pm");
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * This method overrides the one in the superclass. Called when the server
     * starts listening for connections.
     */
    protected void serverStarted() {
        System.out.println("Server listening for connections on port " + getPort());
    }

    /**
     * This method overrides the one in the superclass. Called when the server
     * stops listening for connections.
     */
    protected void serverStopped() {
        System.out.println("Server has stopped listening for connections.");
    }

    //Class methods ***************************************************
    /**
     * This method is responsible for the creation of the server instance (there
     * is no UI in this phase).
     *
     * @param args[0] The port number to listen on. Defaults to 5555 if no
     * argument is entered.
     */
    public static void main(String[] args) {
        int port = 0; //Port to listen on

        try {
            port = Integer.parseInt(args[0]); //Get port from command line
        } catch (Throwable t) {
            port = DEFAULT_PORT; //Set port to 5555
            
        }

        EchoServer sv = new EchoServer(port);

        try {
            sv.listen(); //Start listening for connections
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
        }
    }

    protected void clientConnected(ConnectionToClient client) {

        System.out.println("Client is here" + client.toString());
    }

    synchronized protected void clientDisconnected(
            ConnectionToClient client) {

        System.out.println("Client disconnected");
    }

    synchronized protected void clientException(
            ConnectionToClient client, Throwable exception) {
        clientDisconnected(client);
    }
}
//End of EchoServer class
