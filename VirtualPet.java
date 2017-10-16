/* Virtual Pet version 1.0.1
 *
 * @author Jack Rosetti
 * @author Still Jack Rosetti
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;

import com.nathanruff.baseconverter.Converter;
import com.nathanruff.baseconverter.Formater;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class VirtualPet
{
    VirtualPetFace face;
    double money = 100;
    private int charisma = 1;
    private int confidence = 1;
    private int felonyProb = 0;
    private int happiness = 15;
    private int yearsRemaining = 20;
    private int tiredness = 1;
    private int stress = 1;
    private int hunger = 0;
    private Timer timer;
    private boolean gameover = false;
    
    private String petName = "";
    
    private boolean forceKill;
    
    private Leaderboard l;
	
    // constructor
    public VirtualPet()
    {
        l = new Leaderboard();
        face = new VirtualPetFace();  //Every virtual pet needs a VirtualPetFace, most the implementation of this VirtualPet Face you do not need to understand.
        resetGame();

        timer = face.getTimer();
        face.getContinueButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                initialize();
            }
        });
        
        face.setPetType("cat");
        
        initializeButtons();
        face.hideAllButtons();
    }
    
    public void tick() throws InterruptedException {
        boolean isAlive = true;
    	while (isAlive && !forceKill && !gameover) {
            checkCommands();
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
    	return isAlive;
	}


    public void initialize()
    {

        face.setMessage("Let's do this!");
        face.setImage("ready");


        face.getContinueButton().setVisible(false);
        face.showAllButtons();
    }

    public void resetGame() // really just resets the stats here
    {
        money = 100;
        charisma = 1;
        confidence = 1;
        felonyProb = 0;
        happiness = 15;
        tiredness = 1;
        stress = 1;
        hunger = 0;
        yearsRemaining = 20;

        face.wipeText(); //supposed to clear the face.setMessage box

        face.setStats(money,charisma,confidence,felonyProb,happiness,yearsRemaining,tiredness,stress);
        face.getContinueButton().setVisible(true);
        face.hideAllButtons();
        face.setImage("suit");
        face.setMessage("Congratulations! You have just inherited 100 bucks. You decide to " +
                "go to New York to be the next big billionare. Can you do it?");
        updateScoreboard();

    }
    public void updateScoreboard()
    {
        face.setScoreboard(l.listEntries());
    }

    public void printStats() {
        System.out.println("Money: " + money + "\n" +
                "Charisma: " + charisma + "\n" +
                "Confidence " + confidence + "\n" +
                "Felony Prob: " + felonyProb + "\n" +
                "Happiness: " + happiness + "\n" +
                "Years Remaining:  " + yearsRemaining + "\n" +
                "Tiredness: " + tiredness + "\n" +
                "Stress: " + stress + "\n");
    }

    
    private void checkCommands() throws InterruptedException { // checks the commands sent by the user input line and executes them
		String input;
    	if (true) {//this.scan.hasNextLine()) {
    		input = getGUICommand().toLowerCase();
    		boolean isRecognizedCommand = false;
    		
    		read(input);
    		switch (input) {
    		case "exercise":
    		case "jump":
    			exercise(3);
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
    			isRecognizedCommand = true;
    			magicTrick();
    			face.endPicLoop();
    			break;
    		case "play dead":
    			isRecognizedCommand = true;
    			forceKill = true;
    		}
    		if (input.contains(":") && !isRecognizedCommand) {
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
    			case "player":
    			case "set player":
    			case "setplayer":
    				isRecognizedCommand = true;
    				face.playerName = ((char) (args.charAt(0)&223))+args.substring(1);
    				
    				break;
    			case "set name":
    			case "setname":
    			case "name":
    				isRecognizedCommand = true;
    				face.petName = ((char) (args.charAt(0)&223))+args.substring(1);
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
    	face.setMessage("   owner: \""+input+"\"");
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

    public void feed() {
        hunger = (hunger > 10)? hunger-10 : 0;
        tiredness+=2;
        face.setMessage("Yum, thanks");
        face.setImage("normal", 1);
        
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
        else face.setImage("asleep");

    }

    public void invest() {
        checkIfDone(); //sees if the pet has died from any number of crazy things
        if (!gameover) {
            String companies[] = {"Slack", "Pied Piper", "Hound Sniffer", "Spencer Floyd's startup", "Wafeeq's startup"}; //You need variety in your investments
            face.setMessage("You decided to invest in " + companies[(int) (Math.random() * companies.length)] //just makes sure you have a diverse portfolio
                    + "!" + " Great choice!");
            face.setImage("phone"); //cute lil' photo of a cat acting like a human
            money *= 1.2; //money increased by 20% because that's pretty fair. most of my investments end up like this
            stress += 3;  // the possibility of losing money is always stressful, right?
            happiness += 2; // the pet knows it's making money ergo it's happy
            if (stress >= 10) // if it's too stressed, it becomes tired, too
            {
                tiredness += 3; // see: above
            }
            if (money >= 150) // if the pet is a baller, it gains some other atributes as well
            {
                confidence += 3; //see: above
                charisma += 2; //see: above the above
                happiness += 2; //see: above the above the above
                stress -= 1; //see: above the above the above the above
            }
            yearsRemaining--; //justs takes one off yearsRemaining
            face.setStats(money, charisma, confidence, felonyProb, happiness, yearsRemaining, tiredness, stress);
        }
    }

    public void relax() {
        checkIfDone(); //checks to see if the pet has perished
        if (!gameover) {
            if (stress - 4 < 0) {
                stress = 0;
            } else {
                stress -= 4;
            }
            if (tiredness - 2 < 0) {
                tiredness = 0;
            } else {
                tiredness -= 2;
            }
            // I really hope this one is common sense. But the more you relax with some honnies, the less stress you carry
            happiness += 5; //generally speaking, the more you relax, the more content you are with life
            money -= 10; // This is capitalist America! Can't loaf around *all* day now. Fellow worker, today is a working day! What will we work on today?
            face.setMessage("You just slept and feel like a million bucks!"); //just toying with the users' emotions at this point
            face.setImage("sleep"); // also to toy with user emotions
            if (happiness >= 20) // I don't want happiness to exceed 20, for sake of keeping the game fair. I could easily beat it if happiness went over 20
            {
                happiness = 20; // acts as a ceiling (did I spell that correctly?)
            }
            yearsRemaining--; //decreases years left
            face.setStats(money, charisma, confidence, felonyProb, happiness, yearsRemaining, tiredness, stress);
        }
    }

    private void checkIfDone() //this one just keeps the user fair and makes sure a dead pet isn't walking around
    {
        if(yearsRemaining < 1) //just to catch any bugs. I think this is fair and really allows for me to be efficient
        {
            double score = money; //I said earlier that money is equal to the player's score
            if (money > 200) //I mean, doubled their money (at least). Not too bad considering I made this game to be pretty tough
            {
                face.setImage("pet_joyful_1"); //rich cat because it is rich
                face.wipeText();
                face.setMessage("You did well! You made " + score + " dollars"); //just a nice, cute message to toy with the user's emotions. maybe they'll buy the full version
                gameover = true;
            }
            else if  (money < 200 && money >100) // for those who might not have done as well
            {
                face.wipeText();
                face.setMessage("Not bad. But not great. Your total money: " + score); // just toying again!
                timer.setInitialDelay(3000); // delays again
                gameover = true;

            }
            else
            {
                face.setImage("pet_verysad_1"); //presumably, the user lost money here. Bad gameplay
                face.wipeText();
                face.setMessage("You lost money! But, hey, being rich isn't for everyone. Your score: " + score); // No better way to gain fans than to be smug and hostile
                gameover = true;

            }
        }
        else if (stress > 20 || tiredness > 20) //this will kill you if your pet is too stressed or tired. Again, the emotion ceiling is 20!
        {
            face.setImage("tired"); // sad cat
            face.wipeText();
            face.setMessage("You are too depressed to move on. You quit! Your score: " + money + ". Play again!"); // informs the user of their failure
            gameover = true;
        }
        if(gameover)
        {

            face.hideAllButtons();
            face.getContinueButton().setVisible(false);
            l.postEntry(face.playerName, (int) money); // TODO: ADD PLAYER NAME FROM TEXT INPUT
            updateScoreboard();
            face.setMessage("Your score has been submitted! Check out the recent scores or start again");


            face.getResetButton().setVisible(true);
        }
    }

    public void buyNewCar() //cool method to allow the user to live a life of luxury
    {
        checkIfDone(); //checks to see if you have killed the pet yet
        if(!gameover)
        {
            if (money >= 110) //can't go broke just buying cars. checks to see if this purchase is smart fiscally
            {
                face.setImage("car");
                face.setMessage("Wow, look at you. Going to get all the hunnies!"); // I threw this in there just to let the user know they are popular
                charisma += 5; // with a nice car comes great arrogance
                confidence += 4; // it feels better to drive a lambo than it does to drive a Prius (sorry Prius drivers, wish I got your milage, though)
                money -= 20; // when you buy something, your money decreases
                happiness += 6; // more nice cars = happier
                yearsRemaining--; //decreases a year
                face.setStats(money, charisma, confidence, felonyProb, happiness, yearsRemaining, tiredness, stress);
            } else //this just tells the user they need more money. doesn't count as a year of work though
            {
                face.setMessage("You're too poor!"); //great encouragement!
                face.setImage("sad"); // see: above

            }
        }
    }

    public void buyStocks() // this is something super cool in my opinion
    {
        checkIfDone(); //has the user just killed the cat?
        if (!gameover)
        {
            String stocks[] = {"Micron technology", "Square cash", "Activision", "DXC technology"}; // stores multiple thriving stocks in an array for later user
            face.setMessage("You decided to invest in " + stocks[(int) (Math.random() * stocks.length)] //calls a random string for the user to invest in
                    + "!" + " Great choice!");
            face.setImage("cool"); //who doesn't feel cool after making $10 on a $400 investment?
            money *= 1.1; //10% increase is pretty fair in my opinion. Have to make this game fun somehow
            stress += 1; //i know how stressful it is to invest � trust me
            confidence += 2; // I also felt super cool
            charisma += 3; //hey, ladies, guess who owns three shares of Micron Technology and just made $7?
            yearsRemaining--; //lose a year
            face.setStats(money, charisma, confidence, felonyProb, happiness, yearsRemaining, tiredness, stress);
        }
    }

    public void workOvertime() // i know a lot of people who do this
    {
        checkIfDone(); //has the user really messed up yet?
        if (!gameover) {
            face.setImage("tired"); // more work = less sleep = tiredness
            face.setMessage("The grind never stops"); //this is just a ploy for the user to overwork the pet and play more, thus boosting my ad revenue
            tiredness += 3; // more work = less sleep
            money *= 1.2; // time and a half would be ridiculous in this scenario
            stress += 2; // more work != more fun
            confidence += 1; //this comes with the pay boost
            yearsRemaining--; //decreases by a year
            face.setStats(money, charisma, confidence, felonyProb, happiness, yearsRemaining, tiredness, stress);
        }
    }

    public void makeSale() //this uses the confidence and charisma
    {
        checkIfDone(); // what damage has the user done now?
        if(!gameover)
        {
            if (charisma > 10 && confidence > 10) //pretty high stats!
            {
                money += 50; //more money from a bigger sale because you're cool now and that makes your opinion valuable
                face.setImage("cool"); // cool cat = bigger sales
                face.setMessage("Wow! You just closed a huge sale."); // let the user know how well they did
                yearsRemaining--; //minus un ano
                face.setStats(money, charisma, confidence, felonyProb, happiness, yearsRemaining, tiredness, stress);
            } else if (charisma > 5 && charisma < 10 && confidence > 5 && confidence < 10) // not bad but not great. if their confidence and charisma are in the middle
            {
                money += 25; // not too much money but a good amount
                charisma += 1; // boost those traits
                confidence += 2; // more experience = bigger ego
                face.setImage("suit"); // looks presidential
                face.setMessage("Not too bad. But you can improve!");  // Lets the user know that they're not as hot as they might think
                yearsRemaining--; //minus one year
                face.setStats(money, charisma, confidence, felonyProb, happiness, yearsRemaining, tiredness, stress);
            } else {
                money -= 25; // the user blew a sale
                face.setMessage("You weren't prepared to make the sale. You need more confidence"); // lack of experience != good salesman
                face.setImage("tired"); //sad user
                yearsRemaining--; //minus one year
                face.setStats(money, charisma, confidence, felonyProb, happiness, yearsRemaining, tiredness, stress);
            }
        }
    }

    public void moneyLaunder() // hahaha this was just a fun lil' feature that makes me giggle
    {
        checkIfDone(); // is the pet dead?
        if(!gameover) {
            stress += 3; // when you break the law like this, you are bound to carry some stress, lol
            if (felonyProb >= 15) // yeah the user has been too greedy. they were caught
            {
                face.setImage("jail"); //cat's now in jail
                face.setMessage("You have been caught! Five years in jail for you!"); // this is a light sentence
                yearsRemaining -= 5; // five wasted years
                felonyProb = 0; //well you probably won't be caught now
                confidence = 0; //good luck after this LOL
                charisma = 0; // see: above

                if (money > 0) // they probably do have a positive amount of money, but i always underestimate these bugs
                {
                    money /= 2; //repo the money
                } else {
                    face.setMessage("You're broke! You lose!"); //nice encouragement
                    face.setImage("sad"); // sad cat lost the game
                    resetGame();
                }
                face.setStats(money, charisma, confidence, felonyProb, happiness, yearsRemaining, tiredness, stress);
            } else if (felonyProb > 0 && felonyProb < 15) //a lot more stressed
            {
                face.setImage("greed"); // like the Wolf of Wallstreet, they get evil
                face.setMessage("Can't say I condone this, but whatever makes you happy. Just be careful."); // give the user a fair warning
                stress *= 1.5; // more money laundered = way more stress
                felonyProb += 5; // increase odds of a felony
                yearsRemaining--; // minus one year
                face.setStats(money, charisma, confidence, felonyProb, happiness, yearsRemaining, tiredness, stress);
            } else {
                face.setImage("stress"); // a cat could be stressed after this
                face.setMessage("Yeah, you're saving money. But you're stressed now!"); // pretty snarky message to warn the user
                money *= 2; // I have never money laundered, so I can't give an accurate figure on how much it saves you. High risk, high yield, though
                charisma += 2; // this comes with the more money
                confidence -= 2; // who knows if the feds'll get ya!?
                felonyProb += 5; //probability of you getting a felony
                yearsRemaining--; // minus one year
                face.setStats(money, charisma, confidence, felonyProb, happiness, yearsRemaining, tiredness, stress);
            }
        }
    }
    private void initializeButtons()
    {
        face.getResetButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                resetGame();
            }
        });
        face.getInvestButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                invest();
            }
        });
        face.getRelaxbutton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                relax();
            }
        });
        face.getBuyNewCarButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                buyNewCar();
            }
        });
        face.getBuyStocksButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                buyStocks();
            }
        });
        face.getWorkOvertimeButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                workOvertime();
            }
        });
        face.getMoneyLaunderButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                moneyLaunder();
            }
        });
    }
    
    protected void chooseRabbit() {
		
		face.setPetType("host");
	}


	protected void chooseDog() {
		
		face.setPetType("dog");
	}


	protected void chooseCat() {
		
		face.setPetType("cat");
	}


} // end Virtual Pet
