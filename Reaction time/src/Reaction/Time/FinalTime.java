package Reaction.Time;

import java.util.Timer;

import javax.swing.JButton;

public class FinalTime{
	
	// creates initial time
	Timer timer;
	
	// creates the the textFeild changing time and button clicked time
	long startTime;
	long EndTime;
	
	FinalTime(){
		
		timer = new Timer();
		// gets the textFeild changing time
		startTime = System.currentTimeMillis();
		
	}
	
	public void Time(JButton button) {
		// gets the end time by calculating the time that has passed
		EndTime = System.currentTimeMillis() - startTime;
		// prevents the user from pressing the button
		button.setEnabled(false);
	}
	
	
}
