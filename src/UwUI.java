import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.nio.ByteBuffer;


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UwUI extends Component implements ActionListener{
  
	private static final long serialVersionUID = 2568371618200928057L;
    JFrame frame;
	private  JButton sinenoise;
	private  JButton negsinenoise;
	private  JButton playsametime;
	public static JLabel say[] = new JLabel[3];
	private  JPanel panel = new JPanel();
	
	private GraphApp a=new GraphApp(false,false);
	
	public UwUI(Frame frame) {
	
		sinenoise = new JButton("Sine wave");
		negsinenoise = new JButton("Negative Sine wave");
		playsametime = new JButton("Cancel da noize eh");
		say[0] = new JLabel("Terminal");
		say[1] = new JLabel("Noise cancellation is not active");
		say[2] = new JLabel("Visual representation (just for illustration purpose only)");
		for (int i =0; i<say.length; panel.add(say[i++]));
		panel.add(sinenoise);
		panel.add(negsinenoise);
		panel.add(playsametime);

		panel.add(a);
		
		panel.setLayout(null);
		
        say[2].setBounds(225, 40, 400, 50);
        say[2].setForeground(Color.BLUE);

		a.setBounds(250,100,300,300);
		say[0].setBounds(375, 420, 100, 50);
		say[1].setBounds(310, 450, 200, 50);
		
        say[1].setForeground(Color.RED);
	
		
		sinenoise.setBounds(350, 600, 100, 50);
        negsinenoise.setBounds(325,650,150,50);
		playsametime.setBounds(325,700,150,50);
		

		sinenoise.addActionListener(this);
		negsinenoise.addActionListener(this);
		playsametime.addActionListener(this);
		frame.add(panel);
		frame.setSize(800, 800); 
		frame.setResizable(false);
		frame.setFocusable(true); 
		frame.setLocationRelativeTo(null);
		frame.setVisible(true); 
		((JFrame) frame).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	
		  
	
	public void posandnegsinewaveeh(int a2) {

		new Thread(new Runnable() {
			public void run() {
				try {
					final int SAMPLING_RATE = 44100;            // Audio sampling rate
					final int SAMPLE_SIZE = 2;                  // Audio sample size in bytes

					SourceDataLine line;
					double fFreq = 440;                         // Frequency of sine wave in hz

					//Position through the sine wave as a percentage (i.e. 0 to 1 is 0 to 2*PI)
					double fCyclePosition = 0;        

					//Open up audio output, using 44100hz sampling rate, 16 bit samples, mono, and big 
					// endian byte ordering
					AudioFormat format = new AudioFormat(SAMPLING_RATE, 16, 1, true, true);
					DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

					if (!AudioSystem.isLineSupported(info)){
						System.out.println("Line matching " + info + " is not supported.");
						throw new LineUnavailableException();
					}

					line = (SourceDataLine)AudioSystem.getLine(info);
					line.open(format);  
					line.start();

					// Make our buffer size match audio system's buffer
					ByteBuffer cBuf = ByteBuffer.allocate(line.getBufferSize());   

					int ctSamplesTotal = SAMPLING_RATE*1;         // Output for roughly 5 seconds


					//On each pass main loop fills the available free space in the audio buffer
					//Main loop creates audio samples for sine wave, runs until we tell the thread to exit
					//Each sample is spaced 1/SAMPLING_RATE apart in time
					while (ctSamplesTotal>0) {
						double fCycleInc = fFreq/SAMPLING_RATE;  // Fraction of cycle between samples

						cBuf.clear();                            // Discard samples from previous pass

						// Figure out how many samples we can add
						int ctSamplesThisPass = line.available()/SAMPLE_SIZE;   
						for (int i=0; i < ctSamplesThisPass; i++) {
							cBuf.putShort((short)(Short.MAX_VALUE * Math.sin(a2*2*Math.PI * fCyclePosition)));

							fCyclePosition += fCycleInc;
							if (fCyclePosition > 1)
								fCyclePosition -= 1;
						}

						//Write sine samples to the line buffer.  If the audio buffer is full, this will 
						// block until there is room (we never write more samples than buffer will hold)
						line.write(cBuf.array(), 0, cBuf.position());            
						ctSamplesTotal -= ctSamplesThisPass;     // Update total number of samples written 

						//Wait until the buffer is at least half empty  before we add more
						while (line.getBufferSize()/2 < line.available())   
							Thread.sleep(1);                                             
					}


					//Done playing the whole waveform, now wait until the queued samples finish 
					//playing, then clean up and exit
					line.drain();                                         
					line.close();
				}catch(Exception e) {
					e.printStackTrace();
				}

			}}).start();
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand(); 
		if (s.equals("Sine wave") ) {

			posandnegsinewaveeh(1);			
			a.sine=true;
			a.negsine=false;
			a.reset();
			a.repaint();
			a.timer.start();;
		}
		if (s.equals("Negative Sine wave") ) {
			posandnegsinewaveeh(-1);
			a.negsine=true;
			a.sine=false;
			a.reset();
			a.repaint();
			a.timer.start();

		}
		if (s.equals("Cancel da noize eh") ) {
			MrMoneyMaker.danumber=1;
			new MrMoneyMaker().makeNoisePlz();
			a.negsine=true;
			a.sine=true;
			a.reset();
			a.repaint();
			a.timer.start();

		}
	}



}
