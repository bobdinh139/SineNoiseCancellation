import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class MainDude {

	private static JFrame frame ;
	public static void main(String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		frame = new JFrame("Basic noise cancellation technique");
		
		new UwUI(frame);
	}

			
}
  
