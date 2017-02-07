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

public class GuiMusicConsole extends JFrame {
    
    private Clip clip;
   
    private JButton noteC = new JButton("C");
    private JButton noteD = new JButton("D");
    private JButton noteE = new JButton("E");
    private JButton noteF = new JButton("F");
    private JButton noteG = new JButton("G");
    private JButton noteA = new JButton("A");
    private JButton noteB = new JButton("B");
    private JButton noteC2 = new JButton("C2");
    private JButton note9 = new JButton("9");    
    
    String[] insturments = { "Piano","Guitar", "Drums"};
    final JComboBox<String> cb = new JComboBox<String>(insturments);
    
    public GuiMusicConsole ()
{
		
		super("Simple Music GUI");
		setSize(300, 400);
		
		setLayout( new BorderLayout(5,5));
		Panel bottom = new Panel();
                Panel center = new Panel();
                Panel top = new Panel();
                
                add( "North", top );
		add( "Center", center );
		add( "South" , bottom);
                
                top.add(cb);
		
		bottom.setLayout( new GridLayout(3,3,5,5));
                bottom.add(noteG);  bottom.add(noteB);  bottom.add(noteA); 
		bottom.add(noteD);  bottom.add(noteC);  bottom.add(noteC2);        
		bottom.add(noteE);  bottom.add(noteF);  bottom.add(note9);       
                 	        
		 		
				
		  	 
		
		
		setVisible(true);
       
    
    noteC.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            
            playTheSound("piano-c");
        }
    });
    
      note9.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            
            playTheSound("piano-bb");
        }
    });
      
       noteC2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            
            playTheSound("piano-eb");
        }
    });
    
     noteA.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            playTheSound("piano-a");
        }
    });
    
    
    
    noteE.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            playTheSound("piano-e");
        }
    });
    
    noteF.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            playTheSound("piano-f");
        }
    });
    
    noteG.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            playTheSound("piano-g");
        }
    });
    
    
    
     noteD.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            playTheSound("piano-d");
        }
    });
     
      noteB.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            playTheSound("piano-b");
      
        }
    });

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
    
    public void playTheSound(String notePlayed) {

    URL url = getClass().getResource(notePlayed+".wav");//You can change this to whatever other sound you have
    SoundEffect(url);//this method will load the sound

    if (clip.isRunning())
        clip.stop();   // Stop the player if it is still running
    clip.setFramePosition(0); // rewind to the beginning
    clip.start();     // Start playing

    }
    
    

public static void main(String[] args) 
  {

    GuiMusicConsole chat= new GuiMusicConsole();
    //chat.accept();  //Wait for console data
  }

    
}
