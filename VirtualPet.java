/* Virtual Pet version 1.0.1
 * 
 * @author Jack Rosetti, Nate Ruff
 * @author Still Jack Rosetti
 */
import java.util.Scanner;

import com.nathanruff.baseconverter.*;

public class VirtualPet 
{
    
	/*
	 * "private" added by nate ruff
	 * removed values as this is not the object initializer
	 */
    private VirtualPetFace face;
    private VirtualPetGUI gui;
    private double money;   
    private int charisma;
    private int confidence;
    private int felonyProb;
    private int happiness;
    private double yearsRemaining;
    private int tiredness;
    private int hunger;
    
    private boolean forceKill;
    
    private Scanner scan;// added by nate ruff, for use of textfeild in gui for commands
    
    // constructor
    public VirtualPet() 
    {
        face = new VirtualPetFace();
        face.setImage("normal");
        face.setMessage("Hello.");
        gui = new VirtualPetGUI();
        resetGame();
    }

    public void resetGame()
    {
    	setMoney(100);
        setCharisma(1); // percent out of 100?
        setConfidence(1); // percent out of 100?
        setFelonyProb(0); // percent out of 100?
        setHappiness(15); // percent out of 100? 
        tiredness = 1; // number out of 15
        hunger = 0;  // number out of 12
        setYearsRemaining(20);
        forceKill = false;
    }
    //Introduction
    public void start() 
    {
        gui.setImage(fh.getImage("penguinWalking"));
        gui.setText("Congratulations, you have just inherited 100 smackeroos!"
        		+ "\nYou choose to move to New York to be the next big mogul. Can you do it?");
    }
    
    public void useGUIButtons() {
//    	if (gui.) {
//    		
//    	}
    }

    public void tick() throws InterruptedException {
        boolean isAlive = true;
    	while (isAlive && !forceKill) {
//            checkCommands();
            isAlive = checkVitals();
        }
    }
    
    public boolean checkVitals() { // returns the boolean true if VP is still alive
    	boolean isAlive = true;
    	if (tiredness > 15 && hunger > 12) 
        {
            face.setMessage("Darn!");
            face.setImage("dead");
            isAlive = false;
        }
    	else if (tiredness > 15) 
        {
            face.setMessage("I'm... too... sleepy...");
            sleep(4);
        }
    	else if (hunger > 12)
    	{
    		face.setMessage("Urk... I'm too hungry.");
    		face.setImage("dead");
    		isAlive = false;
    	}
    	return isAlive;
    }
    
    public void feed() 
    {
        if (hunger > 10) 
        {
            hunger = hunger - 10;
        } 
        else 
        {
            hunger = 0;
        }
        tiredness = tiredness + 2;
        face.setMessage("Yum, thanks");
        face.setImage("normal");
        
    }
    
    public void exercise() 
    {
        hunger = hunger + 3;
        tiredness = tiredness + 5;
        face.setMessage("1, 2, 3, jump.  Whew.");
        face.setImage("tired");
    }
    
    public void sleep(int loops)
    {
        hunger = hunger + 1;
        tiredness = 0;
        face.setMessage("zzzzzzzzzz");
        face.setImage("asleep", loops);
    }

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getCharisma() {
		return charisma;
	}

	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}

	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}

	public int getHappiness() {
		return happiness;
	}

	public void setHappiness(int happiness) {
		this.happiness = happiness;
	}

	public int getFelonyProb() {
		return felonyProb;
	}

	public void setFelonyProb(int felonyProb) {
		this.felonyProb = felonyProb;
	}

	public double getYearsRemaining() {
		return yearsRemaining;
	}

	public void setYearsRemaining(double yearsRemaining) {
		this.yearsRemaining = yearsRemaining;
	}


} // end Virtual Pet
