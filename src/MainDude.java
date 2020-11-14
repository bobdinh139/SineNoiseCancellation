import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainDude {

	private static JFrame frame ;
	public static void main(String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		frame = new JFrame("Basic noise cancellation technique");
		
		new UwUI(frame);
	}

			
}
  
