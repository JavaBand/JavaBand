/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplechat;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.sound.midi.Instrument;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

//import static simplechat.ClientConsole.DEFAULT_PORT;
/**
 *
 * @author fairbrother8338
 */
public class GuiClientConsole extends JFrame implements KeyListener, ChatIF, MusicIF {

    private Clip clip;
    private JButton noteA = new JButton("1");
    private JButton noteB = new JButton("2");
    private JButton noteC2 = new JButton("3");
    private JButton note9 = new JButton("4");
    private JButton noteC = new JButton("5");
    private JButton noteD = new JButton("6");
    private JButton noteE = new JButton("7");
    private JButton noteF = new JButton("8");
    private JButton noteG = new JButton("9");
    private JButton focus = new JButton("Click here to play music");

    String[] instruments = {"Piano", "Guitar", "Drum", "chipDrum"};
    final JComboBox<String> cb = new JComboBox<String>(instruments);

    private JButton closeB = new JButton("Close");
    private JButton openB = new JButton("Open");
    private JButton sendB = new JButton("Send");
    private JButton quitB = new JButton("Quit");

    private JTextField portTxF = new JTextField("5555");
    private JTextField hostTxF = new JTextField("127.0.0.1");
    private JTextField messageTxF = new JTextField("");
    private JTextField userTxF = new JTextField("");

    private JLabel portLB = new JLabel("Port: ", JLabel.RIGHT);
    private JLabel hostLB = new JLabel("Host: ", JLabel.RIGHT);
    private JLabel messageLB = new JLabel("Message: ", JLabel.RIGHT);
    private JLabel userLB = new JLabel("User: ", JLabel.RIGHT);

    private JTextArea messageList = new JTextArea();

    private static int DEFAULT_PORT = 5555;
    ChatClient client;

    private String host;
    private int port;
    private String user;

    public GuiClientConsole(String host, int port, String user) {

        super("Simple Chat GUI");
        setSize(400, 400);

        setLayout(new BorderLayout(5, 5));
        Panel bottom = new Panel();
        add("Center", messageList);
        add("South", bottom);
        Panel east = new Panel();
        add("East", east);
        Panel top = new Panel();
        add("North", top);

        top.add(cb);
        top.add(focus);

        bottom.setLayout(new GridLayout(6, 2, 5, 5));
        bottom.add(hostLB);
        bottom.add(hostTxF);
        bottom.add(portLB);
        bottom.add(portTxF);
        bottom.add(userLB);
        bottom.add(userTxF);
        bottom.add(messageLB);
        bottom.add(messageTxF);
        bottom.add(openB);
        bottom.add(sendB);
        bottom.add(closeB);
        bottom.add(quitB);

        east.setLayout(new GridLayout(3, 3, 5, 5));
        east.add(noteE);
        east.add(noteF);
        east.add(noteG);
        east.add(note9);
        east.add(noteC);
        east.add(noteD);
        east.add(noteA);
        east.add(noteB);
        east.add(noteC2);

        setVisible(true);
        addKeyListener(this);
        setFocusable(true);

        east.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseReleased(e);
                setFocus();
            }
        });

        sendB.setEnabled(false);
        closeB.setEnabled(false);

        focus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setFocus();
            }
        });

        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFocus();
            }
        });
        noteC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object notePlayed = buildNote(noteC.getActionCommand());
                sendNote(notePlayed);

            }
        });
        note9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object notePlayed = buildNote(note9.getActionCommand());
                sendNote(notePlayed);
            }
        });

        noteC2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object notePlayed = buildNote(noteC2.getActionCommand());
                sendNote(notePlayed);
            }
        });

        noteA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object notePlayed = buildNote(noteA.getActionCommand());
                sendNote(notePlayed);
            }
        });

        noteE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object notePlayed = buildNote(noteE.getActionCommand());
                sendNote(notePlayed);
            }
        });

        noteF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object notePlayed = buildNote(noteF.getActionCommand());
                sendNote(notePlayed);
            }
        });

        noteG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object notePlayed = buildNote(noteG.getActionCommand());
                sendNote(notePlayed);
            }
        });

        noteD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object notePlayed = buildNote(noteD.getActionCommand());
                sendNote(notePlayed);
            }
        });

        noteB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object notePlayed = buildNote(noteB.getActionCommand());
                sendNote(notePlayed);
            }
        });

        sendB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                send(messageTxF.getText());
                messageTxF.setText("");
                // display(messageTxF.getText() + "\n");
            }
        });

        closeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                send("#logOff");

                sendB.setEnabled(false);
                closeB.setEnabled(false);
                openB.setEnabled(true);

                // display(messageTxF.getText() + "\n");
            }
        });

        quitB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                send("#quit");
            }
        });

        openB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                open();
                userTxF.setText("");
                // host = hostTxF.getText();
                // port = Integer.parseInt(portTxF.getText());

                // display(messageTxF.getText() + "\n");
            }
        });

        //should have setters...
        this.host = host;
        this.port = port;
        this.user = user;

        try {

        } catch (Exception exception) {
            System.out.println("Error: Can't setup connection!"
                    + " Terminating client.");
            System.exit(1);
        }

    }

    public void send(String msg) {
        client.handleMessageFromClientUI(msg);
    }

    public void sendNote(Object notePlayed) {
        client.handleMessageFromClientUI(notePlayed);
    }

    public void display(String message) {

        messageList.append(message + "\n");

    }

    public void open() {
        user = userTxF.getText();
        host = hostTxF.getText();
        sendB.setEnabled(true);
        closeB.setEnabled(true);
        openB.setEnabled(false);

        try {
            client = new ChatClient(host, port, user, this, this);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
            noteA.doClick();
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
            noteB.doClick();
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
            noteC2.doClick();
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
            note9.doClick();
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
            noteC.doClick();
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
            noteD.doClick();
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
            noteE.doClick();
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
            noteF.doClick();
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
            noteG.doClick();
        }
    }

    public void setFocus() {
        this.requestFocus();
    }

    public static void main(String[] args) {
        String host = "";
        String user = "";
        int port = 0;  //The port number

        try {
            // host = args[0];

        } catch (ArrayIndexOutOfBoundsException e) {
            host = "localhost";
        }

        try {
            port = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            port = DEFAULT_PORT;
        }

        try {
            user = args[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            user = "ANON";
        }
        GuiClientConsole chat = new GuiClientConsole(host, port, user);
        //chat.accept();  //Wait for console data
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void SoundEffect(URL url) {
        try {
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sing(String notePlayed) {
        
        URL url = getClass().getResource(notePlayed + ".wav");//You can change this to whatever other sound you have
        SoundEffect(url);//this method will load the sound

//        if (clip.isRunning()) {
//            clip.stop();   // Stop the player if it is still running
//        }
        clip.setFramePosition(0); // rewind to the beginning
        clip.start();     // Start playing
    }

    @Override
    public Object buildNote(String noteNumber) {

        String instrument = String.valueOf(cb.getSelectedItem());
        String number = noteNumber;
        musicEnvelope me = new musicEnvelope(instrument, number);
        return me;
    }
}
