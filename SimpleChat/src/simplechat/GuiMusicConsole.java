/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplechat;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.net.URL;

public class GuiMusicConsole extends JFrame implements KeyListener {
    
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
    
    String[] insturments = {"Piano", "Guitar", "Drum","chipDrum"};
    final JComboBox<String> cb = new JComboBox<String>(insturments);
    
    public GuiMusicConsole() {
        
        super("Simple Music GUI");
        setSize(300, 400);
        
        setLayout(new BorderLayout(5, 5));
        Panel bottom = new Panel();
        Panel center = new Panel();
        Panel top = new Panel();
        
        add("North", top);
        add("Center", center);
        add("South", bottom);
        
        top.add(cb);
        
        bottom.setLayout(new GridLayout(3, 3, 5, 5));
        bottom.add(noteE);
        bottom.add(noteF);
        bottom.add(noteG);
        bottom.add(note9);
        bottom.add(noteC);
        bottom.add(noteD);
        bottom.add(noteA);
        bottom.add(noteB);
        bottom.add(noteC2);
        
        setFocusable(true);
        setVisible(true);
        addKeyListener(this);
        setAlwaysOnTop(true);
        
        bottom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseReleased(e);
                setFocus();
            }
        });
        top.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseReleased(e);
                setFocus();
            }
        });
        center.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseReleased(e);
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
                String notePlayed = buildNote(noteC.getActionCommand());
                playTheSound(notePlayed);
                
            }
        });
        note9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String notePlayed = buildNote(note9.getActionCommand());
                playTheSound(notePlayed);
            }
        });
        
        noteC2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String notePlayed = buildNote(noteC2.getActionCommand());
                playTheSound(notePlayed);
            }
        });
        
        noteA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String notePlayed = buildNote(noteA.getActionCommand());
                playTheSound(notePlayed);
            }
        });
        
        noteE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String notePlayed = buildNote(noteE.getActionCommand());
                playTheSound(notePlayed);
            }
        });
        
        noteF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String notePlayed = buildNote(noteF.getActionCommand());
                playTheSound(notePlayed);
            }
        });
        
        noteG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String notePlayed = buildNote(noteG.getActionCommand());
                playTheSound(notePlayed);
            }
        });
        
        noteD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String notePlayed = buildNote(noteD.getActionCommand());
                playTheSound(notePlayed);
            }
        });
        
        noteB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String notePlayed = buildNote(noteB.getActionCommand());
                playTheSound(notePlayed);
            }
        });
        
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
    
    public void keyReleased(KeyEvent e) {
        
    }
    
    public void keyTyped(KeyEvent e) {
        
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
    
    public String buildNote(String noteNumber) {
        String notePlayed = String.valueOf(cb.getSelectedItem());
        notePlayed += noteNumber;
        return notePlayed;
    }
    
    public void setFocus() {
        this.requestFocus();
    }
    
    public void playTheSound(String notePlayed) {
        setFocus();
        URL url = getClass().getResource(notePlayed + ".wav");//You can change this to whatever other sound you have
        SoundEffect(url);//this method will load the sound

//        if (clip.isRunning()) {
//            clip.stop();   // Stop the player if it is still running
//        }
        clip.setFramePosition(0); // rewind to the beginning
        clip.start();     // Start playing

    }
    
    public static void main(String[] args) {
        
        GuiMusicConsole chat = new GuiMusicConsole();
        //chat.accept();  //Wait for console data
    }
    
}
