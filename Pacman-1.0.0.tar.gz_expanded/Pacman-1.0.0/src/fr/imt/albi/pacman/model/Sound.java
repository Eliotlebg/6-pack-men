package fr.imt.albi.pacman.model;


import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;




public class Sound {
	
	JFrame window;
	String clickSound;
	public SoundEffect se = new SoundEffect();

	
	
	public Sound(){
		
		System.out.println("execution du constructeur qui ne fait r");
		window = new JFrame();
		window.setVisible(true);
	}

	
	public void startSong(String path) {
		clickSound = path;
		se.setFile(clickSound);
		se.play();
		System.out.println("ah y est!");
	}
	
	public class SoundEffect {
		
		Clip clip;
		
		public void setFile(String soundFileName){
			
			try{
				File file = new File(soundFileName);
				AudioInputStream sound = AudioSystem.getAudioInputStream(file);	
				clip = AudioSystem.getClip();
				clip.open(sound);
			}
			catch(Exception e){
				
			}
		}
		
		public void play(){
			
			clip.setFramePosition(0);
			clip.start();
		}

	}
}
