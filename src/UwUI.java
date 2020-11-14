import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	private JButton generateSound;
	private JCheckBox applynoise;
	
	private JCheckBox advanced;

	private JCheckBox lightnoise;
    private MrMoneyMaker mmm=new MrMoneyMaker();
	
	private GraphApp a=new GraphApp(false,false);
	
	public UwUI(Frame frame) {
	
		generateSound = new JButton("Generate Sine Wave");
		advanced = new JCheckBox("Advanced Option");
		applynoise = new JCheckBox("Apply noise cancellation");
		lightnoise=new JCheckBox("Light noise cancellation");
		
		sinenoise = new JButton("Sine wave");
		negsinenoise = new JButton("Negative Sine wave");
		playsametime = new JButton("Demo (Legacy)");
		say[0] = new JLabel("Terminal");
		say[1] = new JLabel("Noise cancellation is not active");
		say[2] = new JLabel("Visual representation (just for illustration purpose only)");
		for (int i =0; i<say.length; panel.add(say[i++]));
		 
		

		panel.add(sinenoise);
		panel.add(negsinenoise);
		panel.add(playsametime);
        panel.add(advanced);
		panel.add(generateSound);
		panel.add(applynoise);
		panel.add(lightnoise);
		
		panel.add(a);
		
		panel.setLayout(null);
		
		generateSound.setBounds(300,500,200,50);
		applynoise.setBounds(200,550,200,50);
		lightnoise.setBounds(400,550,200,50);
		
        say[2].setBounds(225, 30, 400, 50);
        say[2].setForeground(Color.BLUE);

		a.setBounds(250,100,300,300);
		say[0].setBounds(375, 420, 100, 50);
		say[1].setBounds(310, 450, 200, 50);
		
        say[1].setForeground(Color.RED);
	
		advanced.setBounds(320,60,200,50);
		
		sinenoise.setBounds(350, 600, 100, 50);
        negsinenoise.setBounds(325,650,150,50);
		playsametime.setBounds(325,700,150,50);
		sinenoise.setVisible(false);
        negsinenoise.setVisible(false);
		playsametime.setVisible(false);
        advanced.addActionListener(this);
		generateSound.addActionListener(this);
		applynoise.addActionListener(this);
		lightnoise.addActionListener(this);
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

	
	public void resetVisual(boolean sine, boolean neg) {
		a.sine=sine;
		a.negsine=neg;
		a.reset();
		a.repaint();
		a.timer.start();
	}

	public static void changeTerminal(boolean activeq) {
		if(activeq) {
		     UwUI.say[1].setForeground(new Color(0,102,0));
             UwUI.say[1].setText("Noise cancellation is active");
		}else {
		    UwUI.say[1].setForeground(Color.RED);
            UwUI.say[1].setText("Noise cancellation is not active");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand(); 
		
		if(s.equals("Generate Sine Wave")) {
			resetVisual(true,false);
			
			  new Thread(new Runnable() {
	   				public void run() {
	   					try {
	   						for (int i=10; i >0; i--) {
	   							Thread.sleep(1000);
	   							generateSound.setText("Wait for " +i+" second(s)");
	   						}
	   						generateSound.setText("Generate Sine Wave");
	   						
	   					} catch (InterruptedException e2) {
	   						e2.printStackTrace();
	   					}
	   					
	   				}
				  }).start();
				MrMoneyMaker.howheavy =1;
				MrMoneyMaker.danumber=-1;

				mmm.makeNoisePlz(false,10,10, true);
			
		}
		
		if(advanced.isSelected()) {
			sinenoise.setVisible(true);
	        negsinenoise.setVisible(true);
			playsametime.setVisible(true);
		}else {
			sinenoise.setVisible(false);
	        negsinenoise.setVisible(false);
			playsametime.setVisible(false);
		}
		
		if(lightnoise.isSelected()) {
			//OkThisIsEmbarrassing.howheavy=2;
			MrMoneyMaker.howheavy =2;

		}else {
			//OkThisIsEmbarrassing.howheavy=1;
			MrMoneyMaker.howheavy =1;

		}
		
		if (applynoise.isSelected()) {
			//OkThisIsEmbarrassing.danumber=-1;
			MrMoneyMaker.danumber=-1;

            changeTerminal(true);
		    resetVisual(true,true);
		
		}else {
			//OkThisIsEmbarrassing.danumber=1;
			MrMoneyMaker.danumber=1;
            changeTerminal(false);

			resetVisual(true,false);
		}
		
		if (s.equals("Sine wave") ) {

			//posandnegsinewaveeh(1);	
			MrMoneyMaker.danumber=1;
			mmm.makeNoisePlz(false,1,1, false);
			
			resetVisual(true,false);

		}
		if (s.equals("Negative Sine wave") ) {
			
			//posandnegsinewaveeh(-1);
			MrMoneyMaker.danumber=-1;
			mmm.makeNoisePlz(false,1,1, false);
			resetVisual(false,true);


		}
		if (s.equals("Demo (Legacy)") ) {
			MrMoneyMaker.danumber=1;
			
			mmm.makeNoisePlz(true, 2,3, true);
			
			resetVisual(true,true);


		}
	}



}
