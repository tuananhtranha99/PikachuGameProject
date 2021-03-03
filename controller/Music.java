package controller;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
	public static void playMusic() throws Exception {
		AudioInputStream BGM = AudioSystem.getAudioInputStream(new File("D:\\eclipse\\eclipse\\PikachuGameProject\\src\\music\\Intentions-JustinBieberQuavo-6217997.wav"));
		Clip clip = AudioSystem.getClip();
		
		clip.open(BGM);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();
	}
}
