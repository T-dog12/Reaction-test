package Reaction.Time;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;

public class StartUp extends JFrame implements ActionListener{
	
	// creates visuals
	JTextField textField;
	JButton button;
	JTable table;
	//creates the tables information
	Object[] colNames = {"test No", "Sight"};
	Object[][] data = {{1, ""},
			{2,""},
			{3,""},
			{"Mean",""}};
	long[] times = new long[2];
	
	//sets timers to start/check the button
	Timer xTimer;
	Timer yTimer;
	Random ran;
	
	// the fonts that will be used
	Font normFont = new Font("ITALIC",Font.BOLD,25);
	Font tablFont = new Font("SANS_SERIF",Font.PLAIN,12);
	
	// creates the timer class to use for the final time
	FinalTime timer;
	
	// the parts that checks what activates/happens at the certen time
	int wait;
	boolean active = false;
	int rep = 0;
	
	
	StartUp(){
		
		// creates the JFrame for the program 
		this.setTitle("Reaction time");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(550, 400);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		// creates the panel that will change colour 
		textField = new JTextField();
		textField.setBackground(Color.RED);
		textField.setBounds(90, 40, 355, 150);
		textField.setEditable(false);
		textField.setFont(normFont);
		
		// creates the button you need to click
		button = new JButton("click when green");
		button.setBounds(125, 200, 280, 100);
		button.addActionListener(this);
		button.setFocusable(false);
		button.setFont(normFont);
		button.addActionListener(this);
		
		
		// creates a table where the information is stored
		table = new JTable(data,colNames);
		table.setShowGrid(true);
		table.setFont(tablFont);
		table.setBounds(10,290, 110,65);
		table.setEnabled(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(130);
		
		// creates a random time for the textFeild to change
		ran = new Random();
		newTime();
		xTimer = new Timer(wait,this);
		
		// yTimer is to check weather the button is pressed down
		yTimer = new Timer(0,this);
		
		// adds everything to the frame
		this.add(table);
		this.add(textField);
		this.add(button);
		this.setVisible(true);
		
		// where the timers start
		xTimer.start();
		yTimer.start();
		
	}
	
	public void newTime() {
		// generates a random number for the timer
		wait = 1000 + ran.nextInt(9000);
	}

	public static void main(String[] args) {
		StartUp start = new StartUp();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == xTimer) {
			// if button is not green
			if(!active){
				// it turns it green
				textField.setBackground(Color.GREEN);
				textField.setText("");
				active = true;
				
				// calls the final time class
				timer = new FinalTime();
				xTimer.stop();
				
			}else if(rep == 3) {
				// stops the timer permanently
				xTimer.stop();
			}
			
			else {
				// changes the button back once clicked
				textField.setBackground(Color.red);
				button.setEnabled(true);
				active = false;
				textField.setText("");
				
				newTime();
				xTimer.restart();
				
			}
		}
		if(button.getModel().isPressed()) {
			// checks weather it is click able
			// prevents bad results
			if(!active) {
				
				textField.setText("                Too early!");
				// resets the time
				newTime();

			}else {
				
				// calls the Time method in final time
				timer.Time(button);
				xTimer.setDelay(200);
				
				// adds it to a list of the times
				times[rep] = timer.EndTime; 
				table.setValueAt(timer.EndTime, rep, 1);
				rep++;
				
				// once all info is gathered
				if(rep == 3) {
					// Calculates and displays the mean
					double mean = (times[0]+times[1]+times[2])/3;
					table.setValueAt(mean, rep, 1);
				}
					
			}xTimer.restart();
		}
		
	}
	
	

}
