import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.Border;


public class VirtualPetFace extends JFrame implements ActionListener {

    private final int WIDTH = 1000;
    private final int HEIGHT = 800;
    private ImagePanel imagePanel;
    private JTextPane textArea;
    private String base;
    private int loopslot = 0;
    private String[] files;
    private Image[] allPics;
    private ArrayList<Image> pics;
    private Timer timer;
    
    private final int TIMERSPEED = 60;
    
    private JTextField inputArea;
    private String inputText;
    private boolean newInput;

    private JButton catButton;
    private JButton dogButton;
    private JButton resetButton;
    private JButton continueButton;
    private JButton investButton;
    private JButton relaxButton;
    private JButton buyNewCarButton;
    private JButton buyStocksButton;
    private JButton moneyLaunderButton;
    private JButton workOvertimeButton;
    private JButton makeSaleButton;
    private boolean guiCreated = false;
    private JButton startButton;
    private JTextPane _textAreaStats;
    private JTextPane _textAreaScores;
    
    private boolean newImageSet;
    private String currentImageGroup;//	added by nate, the current group of images ex: petType+"_"+mood+"_"+("intro", "cycle", or "outro")
    private int currentFrame;//			added by nate, what frame the current pic is set to
    private int numberOfFrames;//		added by nate, how many total frames there are in current mood
    private boolean isEndOfLoop;//		added by nate, is at the last frame of a loop
    private int numberOfLoops;//		added by nate, how many total loops there should be before changing mood
    private int currentLoop;//			added by nate, the current loop in my image cycling method
    private String currentMood;//		added by nate, stores the current mood
    private String petType;//			added by nate, word prier to the mood in the picture


    private static final String imageBase = "./pet_images/";

    public VirtualPetFace() {
        try {
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    if (!guiCreated) {
                        guiCreated = true;
                        createGUI();

                    }
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't successfully complete");
        }
        init();

    }

    public void init() {
        String curDir = System.getProperty("user.dir");

        base = curDir + "/" + imageBase;
        pics = new ArrayList<Image>();
        timer = new Timer(TIMERSPEED, this);
        //timer.setInitialDelay(1000);

        getAllImages();

//        setBackground();
        //setImage("angel");
        //setMessage("Hello, and Welcome!");
    }

    public void createGUI() {
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(WIDTH, HEIGHT));
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBackground(Color.white);


        imagePanel = new ImagePanel();
        int width = 200;
        int height = 200;
        imagePanel.setPreferredSize(new Dimension(width, height));
        imagePanel.setMinimumSize(new Dimension(width, height));
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 1;
        contentPane.add(imagePanel, c);

        textArea = new JTextPane();
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(width, height ));
        scroll.setSize(new Dimension(width, height ));
        textArea.setPreferredSize(new Dimension(width, height ));
        textArea.setSize(new Dimension(width, height));
        
        inputArea = new JTextField();
        inputArea.setBounds(20, 20, width, height/4);
        inputArea.setToolTipText("Enter a command here.");
        inputArea.setSize(200, 200);
        inputArea.setText("Enter a command here");
        inputArea.setEditable(true);
    	inputArea.addActionListener(this);
    	add(inputArea);
    	newInput = false;

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 2;
        c.weighty = 1;
        c.gridheight = 2;
        contentPane.add(scroll, c);

        _textAreaStats = new JTextPane();
        _textAreaStats.setEditable(false);
        _textAreaStats.setPreferredSize(new Dimension(width, height ));
        _textAreaStats.setSize(new Dimension(width, height));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 4;
        c.weighty = .5;
        contentPane.add(_textAreaStats, c);

        _textAreaScores = new JTextPane();
        _textAreaScores.setEditable(false);
        JScrollPane _scrollScores = new JScrollPane(_textAreaScores);
        _scrollScores.setPreferredSize(new Dimension(width, height));
        _scrollScores.setSize(new Dimension(width, height));
        _textAreaScores.setPreferredSize(new Dimension(width, height));
        _textAreaScores.setSize(new Dimension(width, height));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 6;
        c.insets = new Insets(0,40,0,0);
        c.gridy = 0;
        contentPane.add(_scrollScores, c);

        continueButton = new JButton("Start");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 5;
        c.gridy = 5;
        contentPane.add(continueButton, c);

        resetButton = new JButton("resetButton");

        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.weighty = 0;
        c.insets = new Insets(0,0,0,30);
        contentPane.add(resetButton, c);


        investButton = new JButton("Invest");
        c.gridy = 2;
        contentPane.add(investButton, c);
        relaxButton = new JButton("Relax");
        c.gridy = 3;
        contentPane.add(relaxButton, c);
        buyNewCarButton = new JButton("Buy Car");
        c.gridy = 4;
        contentPane.add(buyNewCarButton, c);
        buyStocksButton = new JButton("Buy Stock");
        c.gridy = 5;
        contentPane.add(buyStocksButton, c);
        moneyLaunderButton = new JButton("Money Launder");
        c.gridy = 6;
        contentPane.add(moneyLaunderButton, c);
        workOvertimeButton = new JButton("Work Overtime");
        c.gridy = 7;
        contentPane.add(workOvertimeButton, c);
        makeSaleButton = new JButton("Make Sale");
        c.gridy = 8;
        contentPane.add(makeSaleButton, c);

        setLocationRelativeTo(null);
        setVisible(true);
        //toFront();
        setAlwaysOnTop(true);
        setAlwaysOnTop(false);
    }

    private void setBackground() {
        Image backImage = createImage(base + "background.png", "");
        Border bkgrnd = new CentredBackgroundBorder(backImage);
        ((JComponent) getContentPane()).setBorder(bkgrnd);
    }

    protected Image createImage(String path, String description) {
        return new ImageIcon(path, description).getImage();
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }
    
    public String getPetType() {
    	return this.petType;
    }

    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
    	//-----------------------------start built-in
    	loopslot++;
	    
    	if (loopslot >= pics.size()) {
            loopslot = 0;
        }
        
        imagePanel.repaint();
        
        if (newImageSet) {
        	loopslot = currentFrame;
        	newImageSet = false;
        }
        if (loopslot == pics.size()) {
            timer.restart();
        }
        //-------------------------------end built-in
    	
        if (source.equals(inputArea)) {
        	String input;
//        	System.out.println("test-1");
        	try {
        		input = inputArea.getText();
        		setInput(input);
//        		System.out.println(input);
        	} catch (NullPointerException exception) {
        		input = "";
        		System.err.println("nullPointerException");
        	}
        }
    }
    
    public void setImage(String mood) {
        timer.stop();
        pics.clear();
        currentLoop = 0;
        numberOfLoops = 1;
        currentFrame = 0;
        isEndOfLoop = false;
        getImages(mood);
        timer.start(); 
    }
    
    public void setImage(String mood, int loops) {
    	timer.stop();
        pics.clear();
        numberOfLoops = loops;
        currentLoop = 0;
        currentFrame = 0;
        isEndOfLoop = false;
        getImages(mood);
        timer.start(); 
    }
    
    public void setImageGroupCycle(String moodGroup, int cycles) {
    	setImage(moodGroup+"_intro");
    	endPicLoop(moodGroup+"_cycle", cycles);
    	currentImageGroup = moodGroup;
    	endPicLoop(moodGroup+"_outro");
    	endPicLoop();
    	currentImageGroup = "normal";
    	
    }
    public void setImageGroup(String moodGroup) {
    	setImage(moodGroup+"_intro", 1);
    	endPicLoop(moodGroup+"_cycle");
    	currentImageGroup = moodGroup;
    }
    public void endImageGroup() {
    	endPicLoop(currentImageGroup+"_outro", 1);
    	endPicLoop();
    	currentImageGroup = "normal";
    	
    }
    
    public void endPicLoop() {
    	if (!currentMood.equals("normal")) {
    		int var;
    		for (currentLoop = 0; currentLoop<numberOfLoops; currentLoop++) {
    			var = 0;
    			while (!isEndOfLoop) {
    				var++;
    				System.out.print("");
    			}
    			
    			isEndOfLoop = false;
    		}
    		setImage("normal");
    		
    	}
    }
    public void endPicLoop(String newMood) {
    	if (!currentMood.equals(newMood)) {
//    		System.out.println("test-3"); // --------------------------------------
    		int var;
    		for (currentLoop = 0; currentLoop<numberOfLoops; currentLoop++) {
    			var = 0;
    			while (!isEndOfLoop) {
    				var++;
    				System.out.print("");
    			}
//    			System.out.println("test-1");
    			isEndOfLoop = false;
    		}
//    		System.out.println("test-2");
    		setImage(newMood);
    	}
    }
    public void endPicLoop(String newMood , int newLoopVal) {
    	if (!currentMood.equals(newMood)) {
    		for (currentLoop = 0; currentLoop<numberOfLoops; currentLoop++) {
    			while (!isEndOfLoop) {
    				System.out.print("");
    			}
    			isEndOfLoop = false;
    		}
    		setImage(newMood, newLoopVal);
    	}
    }

    private void setInput(String input) {
		newInput = true;
		inputText = input;
	}
    public String getInput() {
    	if (newInput) {
    		newInput = false;
//    		System.out.println("getInput test: "+inputText);
    		return inputText;
    	}
//    	System.err.println("getInput test failed: "+inputText+"\n  newInput: "+newInput);
    	return "";
    }

    public void getAllImages() {
        File dir = new File(base);
        files = dir.list();
        allPics = new Image[files.length];
        for (int i = 0; i < files.length; i++) {
            //System.err.println(files[i]);
            allPics[i] = createImage(base + files[i], "");

        }
        //System.err.println(pics.size());
    }


    public void getImages(final String mood) {
        numberOfFrames = 0;
        currentMood = mood;
        for (int i = 0; i < files.length; i++) {
            if (files[i].contains(petType+"_"+mood)) {
                pics.add(allPics[i]);
                numberOfFrames++;
//                    System.out.println(files[i]+", "+numberOfFrames);
                
            }
        }
        newImageSet = true;
        //System.err.println(pics.size());
    }


    public void setMessage(String message) {
        String current = textArea.getText();
        textArea.setText(current + "\n" + message);
        textArea.select(current.length(), (current.length() + message.length() + 1));
    }

    public void setScoreboard(String message)
    {
        _textAreaScores.setText(message);
    }

    public void setStats(double money, int charisma, int confidence, int felonyProb, int happiness, int yearsRemaining, int tiredness, int stress) {

        _textAreaStats.setText
                (
                        "Money: " + money + "\n" +
                        "Charisma: " + charisma + "\n" +
                        "Confidence " + confidence + "\n" +
                        "Felony Prob: " + felonyProb + "\n" +
                        "Happiness: " + happiness + "\n" +
                        "Years Remaining:  " + yearsRemaining + "\n" +
                        "Tiredness: " + tiredness + "\n" +
                        "Stress: " + stress + "\n"
                );
    }


    public class ImagePanel extends JPanel {
        public ImagePanel( ) {
            super();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (pics.size() > 0) {
                g.drawImage(pics.get(loopslot), 0, 0, this.getWidth(), this.getHeight(), null);
                currentFrame++;
                if (currentFrame == numberOfFrames) {
                	isEndOfLoop = true;
                	currentFrame = 0;
                }
            }
        }

    }

    public class CentredBackgroundBorder implements Border {
        private final Image image;

        public CentredBackgroundBorder(Image image) {
            this.image = image;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawImage(image, 0, 0, VirtualPetFace.this.getWidth(), VirtualPetFace.this.getHeight(), null);
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(0, 0, 0, 0);
        }

        public boolean isBorderOpaque() {
            return true;
        }
    }

    public JButton getContinueButton() {
        return continueButton;
    }

    public Timer getTimer() {
        return this.timer;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getCatButton() {
        return catButton;
    }

    public JButton getDogButton() {
        return dogButton;
    }

    public JButton getInvestButton() {
        return investButton;
    }

    public JButton getRelaxbutton() {
        return relaxButton;
    }

    public JButton getBuyNewCarButton() {
        return buyNewCarButton;
    }

    public JButton getBuyStocksButton() {
        return buyStocksButton;
    }

    public JButton getWorkOvertimeButton() {
        return workOvertimeButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JButton getMoneyLaunderButton() {
        return moneyLaunderButton;
    }

    public JButton getMakeSaleButton(){return makeSaleButton;}

    public void hideAllButtons() {

        investButton.setVisible(false);
        relaxButton.setVisible(false);
        buyNewCarButton.setVisible(false);
        buyStocksButton.setVisible(false);
        moneyLaunderButton.setVisible(false);
        workOvertimeButton.setVisible(false);
        resetButton.setVisible(false);
        makeSaleButton.setVisible(false);
    }

    public void showAllButtons() {

        investButton.setVisible(true);
        relaxButton.setVisible(true);
        buyNewCarButton.setVisible(true);
        buyStocksButton.setVisible(true);
        moneyLaunderButton.setVisible(true);
        workOvertimeButton.setVisible(true);
        resetButton.setVisible(true);
        makeSaleButton.setVisible(true);
    }

    public void wipeText() {
        this.textArea.setText("");
    }

}




