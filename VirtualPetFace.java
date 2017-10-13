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


import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.border.Border;


public class VirtualPetFace extends JFrame implements ActionListener{

    private final int WIDTH = 400;
    private final int HEIGHT = 400;
    private ImagePanel imagePanel;
    private JTextPane textArea;
    private String base;
    private int loopslot = 0;
    private String[] files;
    private Image[] allPics;
    private ArrayList<Image> pics;
    private Timer timer;
    
    private static final int TIMERSPEED = 60;// added by nate
    
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
	private JButton startButton;
	
    
    private String currentImageGroup;//	added by nate, the current group of images ex: petType+"_"+mood+"_"+("intro", "cycle", or "outro")
    private int currentFrame;//			added by nate, what frame the current pic is set to
    private int numberOfFrames;//		added by nate, how many total frames there are in current mood
    private boolean isEndOfLoop;//		added by nate, is at the last frame of a loop
    private int numberOfLoops;//		added by nate, how many total loops there should be before changing mood
    private int currentLoop;//			added by nate, the current loop in my image cycling method
    private String currentMood;//		added by nate, stores the current mood
    private String petType;//			added by nate, word prier to the mood in the picture

    private static final String imageBase = "./pet_images/";
    
    public static void main(String args[]) {
//        VirtualPet newPet = new VirtualPet();   
    }
    
    public VirtualPetFace(String petType) {
        try {
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't successfully complete");
        }
        
        init(petType);
    }
    
    public void init(String petType) {
        String curDir = System.getProperty("user.dir");
        
        base = curDir + "/" + imageBase;
        //System.out.println(base);
        pics = new ArrayList<Image>();
        timer = new Timer(TIMERSPEED, this);
        //timer.setInitialDelay(1000);

        getAllImages();
        
        startButton = new JButton ("Start");
        startButton.setBounds(WIDTH + 50, 300, 100, 20);
        add(startButton);
        
        setBackground();
        setPetType(petType);
        setImage("normal");      
        setMessage("Hello, and Welcome!");
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
        c.gridx = 1;
        c.gridy = 1;
        contentPane.add(imagePanel,c);
        
        textArea = new JTextPane();
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(width, height/2));
        scroll.setSize(new Dimension(width, height/2));
        textArea.setPreferredSize(new Dimension(width, height/2));
        textArea.setSize(new Dimension(width, height/2));


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        c.ipady = 20;
        contentPane.add(scroll, c);
    
        setLocationRelativeTo(null);
        setVisible(true);
        //toFront();
        setAlwaysOnTop(true);
        setAlwaysOnTop(false);
    }
    
    private void setBackground() {
        Image backImage = createImage(base+"background.png", "");
        Border bkgrnd = new CentredBackgroundBorder(backImage);
        ((JComponent) getContentPane()).setBorder(bkgrnd);
    }
    
    protected Image createImage(String path, String description) {
           return new ImageIcon(path, description).getImage();
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
    		System.out.println("test-3"); // --------------------------------------
    		int var;
    		for (currentLoop = 0; currentLoop<numberOfLoops; currentLoop++) {
    			var = 0;
    			while (!isEndOfLoop) {
    				var++;
    				System.out.print("");
    			}
    			System.out.println("test-1");
    			isEndOfLoop = false;
    		}
    		System.out.println("test-2");
    		setImage(newMood);
    	}
    }
    public void endPicLoop(String newMood , int newLoopVal) {
    	if (!currentMood.equals(newMood)) {
    		int var;
    		for (currentLoop = 0; currentLoop<numberOfLoops; currentLoop++) {
    			var = 0;
    			while (!isEndOfLoop) {
    				var++;
    				System.out.print("");
    			}
    			isEndOfLoop = false;
    		}
    		setImage(newMood, newLoopVal);
    	}
    }
    
    
    
    public void setPetType(String petType) {
        this.petType = petType;
    }

    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
    	if (source.equals(catButton)) {
    		
    	} else if (source.equals(dogButton)) {
    		
    	} else if (source.equals(buyNewCarButton)) {
    		
    	} else if (source.equals(continueButton)) {
    		
    	} else if (source.equals(startButton)) {
    		
    	} else if (source.equals(investButton)) {
    		
    	} else if (source.equals(moneyLaunderButton)) {
    		
    	} else if (source.equals(buyStocksButton)) {
    		
    	} else if (source.equals(relaxButton)) {
    		
    	} else if (source.equals(workOvertimeButton)) {
    		
    	} else if (source.equals(resetButton)) {
    		
    	} else {
    		loopslot++;
		    
		    if (loopslot >= pics.size()) {
		        loopslot = 0;
		    }
		
		    imagePanel.repaint();
		
		    if (loopslot == pics.size()) {
		        timer.restart();
		    }
    	}
    }
    
    public void getAllImages() {
        File dir = new File(base);   
        files  = dir.list();
        allPics = new Image[files.length];
        
        for (int i = 0; i < files.length; i++) {
            //System.err.println(files[i]);
            allPics[i]=createImage(base + files[i],"");
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
                
            }
        }
        //System.err.println(pics.size());
    }
    
    public void setMessage(String message) {
        String current = textArea.getText();
        textArea.setText(current + "\n" + message);
        textArea.select(current.length(), (current.length() + message.length() + 1));
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
            g. drawImage(image, 0, 0, VirtualPetFace.this.getWidth(), VirtualPetFace.this.getHeight(),null);
        }
     
        public Insets getBorderInsets(Component c) {
            return new Insets(0,0,0,0);
        }
     
        public boolean isBorderOpaque() {
            return true;
        }
    }
    

    public JButton getContinueButton() 
    {
        return continueButton; 
    }
   
    public JButton getStartButton()
    {
        return startButton;
    }
    
    public JButton getCatButton()
    {
        return catButton;
    }

    public JButton getDogButton()
    {
        return dogButton;
    }

    public JButton getInvestButton()
    {
        return investButton;
    }

    public JButton getRelaxbutton()
    {
        return relaxButton;
    }

    public JButton getBuyNewCarButton()
    {
        return buyNewCarButton;
    }

    public JButton getBuyStocksButton()
    {
        return buyStocksButton;
    }

    public JButton getWorkOvertimeButton()
    {
        return workOvertimeButton;
    }

    public JButton getResetButton()
    {
        return resetButton;
    }
    
    public JButton getMoneyLaunderButton()
    {
        return moneyLaunderButton;
    }
    
    /*public void hideAllButtons()
    {
        startButton.setVisible(false);
        investButton.setVisible(false);
        relaxButton.setVisible(false);
        buyNewCarButton.setVisible(false);
        buyStocksButton.setVisible(false);
        moneyLaunderButton.setVisible(false);
        workOvertimeButton.setVisible(false);
        resetButton.setVisible(false);
    }
    */
    
    
    
    
    
    
    
    
    
    
}

 


