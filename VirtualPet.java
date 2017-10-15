/* Virtual Pet version 1.0.1
 * 
 * @author Jack Rosetti, Nate Ruff
 * @author Still Jack Rosetti
 */

import com.nathanruff.baseconverter.*;

public class VirtualPet 
{
    
	/*
	 * "private" added by nate ruff
	 * removed values as this is not the object initializer
	 */
    private VirtualPetFace face;
    private double money;   
    private int charisma;
    private int confidence;
    private int felonyProb;
    private int happiness;
    private double yearsRemaining;
    private int tiredness;
    private int hunger;
    private String petName;
    
    private boolean forceKill;
    
    
    // constructor
    public VirtualPet() 
    {
        face = new VirtualPetFace("pet");
        face.setImage("normal");
        face.setMessage("Hello.");
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
        petName = "";
        forceKill = false;
    }
    //Introduction
    public void start() 
    {
//        gui.setImage(fh.getImage("penguinWalking"));
//        gui.setText("Congratulations, you have just inherited 100 smackeroos!"
//        		+ "\nYou choose to move to New York to be the next big mogul. Can you do it?");
    }
    
    public void useGUIButtons() {
//    	if (gui.) {
//    		
//    	}
    }

    public void tick() throws InterruptedException {
        boolean isAlive = true;
    	while (isAlive && !forceKill) {
            checkCommands();
            isAlive = checkVitals();
        }
    	deathMessage();
    }
    
    private void deathMessage() {
		face.petDie();
		
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
    public void exercise(int loops) {
        hunger+=3;
        tiredness+=5;
        face.setMessage("1, 2, 3, jump.  Whew.");
        face.setImage("tired", loops);
    }
    
    public void sleep(int loops) {
        hunger+=1;
        tiredness=0;
        face.setMessage("zzzzzzzzzz");
        if (face.getPetType().equals("host"))
        	face.setImageGroupCycle("sleep", loops);
        else face.setImage("asleep", loops);
    }
    public void sleep()
    {
        hunger = hunger + 1;
        tiredness = 0;
        face.setMessage("zzzzzzzzzz");
        if (face.getPetType().equals("host"))
        	face.setImage("sleep");
        else face.setImage("asleep");
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
	
	private void checkCommands() throws InterruptedException {
		String input;
    	if (true) {//this.scan.hasNextLine()) {
    		input = getGUICommand().toLowerCase();
    		boolean isRecognizedCommand = false;
    		
    		read(input);
    		switch (input) {
    		case "exercise":
    		case "jump":
    			exercise();
    			face.endPicLoop();
    			isRecognizedCommand = true;
    			break;
    		case "feed":
    		case "eat":
    			feed();
    			face.endPicLoop();
    			Thread.sleep(2000);
    			isRecognizedCommand = true;
    			break;
    		case "sleep":
    		case "go to sleep":
    			sleep(3);
    			face.endPicLoop();
    			isRecognizedCommand = true;
    			break;
    		case "do a magic trick":
    		case "magic trick":
    			magicTrick();
    			face.endPicLoop();
    			break;
    		case "play dead":
    			forceKill = true;
    		}
    		if (input.contains(":")) {
    			int colonIndex = input.indexOf(':');
    			String cmnd = input.substring(0, colonIndex).trim();
    			String args = input.substring(colonIndex+1).trim();
    			switch (cmnd) {
    			case "solve":
    				isRecognizedCommand = true;
        			face.setImageGroup("deep-thought");
        			String eq = args;
        			String output;
        			output = Converter.main(Formater.main(eq));
        			//Thread.sleep(2000);
        			face.endImageGroup();
        			say(output);
        			//sendUserOutput(output);
    				break;
    			case "set name":
    			case "setname":
    			case "name":
    				isRecognizedCommand = true;
    				this.petName = ((char) (args.charAt(0)&223))+args.substring(1);
    				say("My name is now "+this.petName);
    				break;
    			case "type":
    			case "settype":
    			case "set type":
    				isRecognizedCommand = true;
    				args = (args.equals("rabbit"))? "host":args;
    				face.setPetType(args);
    				face.setImage("normal");
    				break;
    			}
    		}
    		
			if (!isRecognizedCommand) {
    			say("Could you repeat that");
    			
//    			listCommands();
    		}
    	}
		
	}
    
    private void say(String message) {
    	face.setMessage(message);
    }
    private void read(String input) {
    	face.setMessage("    owner: \""+input+"\"");
    }
    private void wrongCommand() {
    	say("I don't know that one.");
    }
    
    private void magicTrick() {
    	if (face.getPetType().equals("host")) {
	    	hunger+=4;
	    	tiredness+=6;
	    	face.setMessage("And now, watch as I disapear.");
			face.setImageGroupCycle("disapear", 4);
    	} else {
    		wrongCommand();
    	}
		
	}
    
    private String getGUICommand() {
    	String input = "";
    	while (input.equals("")) {
    		input = face.getInput();
    		System.out.print("");
    	}
//    	System.out.println("input: "+input);
    	return input;
    	
    }

	public void listCommands() {
		say("Recognized commands are the following:");
    	say(" exercise\n eat\n sleep\n magic trick");
    }


} // end Virtual Pet
