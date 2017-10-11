
/**
 * Write a description of class VirtualPetGUI here.
 *
 * @author Jack R
 * @version 1.0.1
 */
import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


public class VirtualPetGUI extends JFrame
{
	private ImagePanel imagePanel; 
    private JTextPane textPane;
    private JScrollPane scrollPane; 
    private Image image;
    
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
    private ArrayList<Image> pics;
	public int loopslot = 0;
    
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    
    private static final int PICTURE_HEIGHT = 300;
    private static final int PICTURE_WIDTH = 300;
    
    public VirtualPetGUI()
    {
        setTitle("The Real Life of Pets");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        
        imagePanel = new ImagePanel(this);
        
        textPane = new JTextPane();
        scrollPane = new JScrollPane(textPane);
        textPane.setEditable(false);
        textPane.setFocusable(false);
        scrollPane.setBounds(0, PICTURE_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT - PICTURE_HEIGHT - 23);
        add(scrollPane);
        scrollPane.setVisible(true);
        
        continueButton = new JButton("Continue");
        continueButton.setBounds(PICTURE_WIDTH + 45, 400, 100, 20);
        add(continueButton);
        
        catButton = new JButton("Cat");
        catButton.setBounds(PICTURE_WIDTH + 60, 400, 100, 20);
        
        dogButton = new JButton("Dog");
        dogButton.setBounds(PICTURE_WIDTH + 45, 300, 100, 20);

        resetButton = new JButton("Reset");
        resetButton.setBounds (PICTURE_WIDTH + 45, 400, 100, 20);

        continueButton = new JButton("Continue");
        continueButton.setBounds(PICTURE_WIDTH + 60, 400, 100, 20);

        investButton = new JButton("Invest in a company");
        investButton.setBounds(PICTURE_WIDTH + 60, 100, 100, 20);

        relaxButton = new JButton("Take the day off");
        relaxButton.setBounds(PICTURE_WIDTH + 60, 150, 100, 20);

        buyNewCarButton = new JButton("Buy a new car");
        buyNewCarButton.setBounds(PICTURE_WIDTH + 60, 200, 100, 20);

        buyStocksButton = new JButton("Buy stocks");
        buyStocksButton.setBounds(PICTURE_WIDTH + 60, 250, 100, 20);

        moneyLaunderButton = new JButton("Launder your money");
        moneyLaunderButton.setBounds(PICTURE_WIDTH + 60, 300, 100, 20);

        workOvertimeButton = new JButton("Work overtime");
        workOvertimeButton.setBounds(PICTURE_WIDTH + 60, 350, 100, 20);



    }
   
    public void setImage(Image _image) 
    {
        //Scales the picture, so it fills up entire picturePanel
        image = _image.getScaledInstance(PICTURE_WIDTH, PICTURE_HEIGHT, Image.SCALE_DEFAULT);
        imagePanel.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);
        repaint();
    }

    public void hideAllButtons()
    {
        continueButton.setVisible(false);
        catButton.setVisible(false);
        dogButton.setVisible(false);
        investButton.setVisible(false);
        relaxButton.setVisible(false);
        buyNewCarButton.setVisible(false);
        buyStocksButton.setVisible(false);
        moneyLaunderButton.setVisible(false);
        workOvertimeButton.setVisible(false);
        resetButton.setVisible(false);
    }

    public JButton getContinueButton() 
    {
     return continueButton; 
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
        return moneyLaunderButton;
    }

    public JButton getWorkOvertimeButton()
    {
        return workOvertimeButton;
    }

    public JButton getResetButton()
    {
        return resetButton;
    }

	public void setText(String string) {
		
		
	}
	
	public class ImagePanel extends JPanel {
        public ImagePanel( ) {
            super();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (pics.size() > 0) {
                g.drawImage((pics).get(loopslot), 0, 0, this.getWidth(), this.getHeight(), null);
                
                
            }
        }

    }




    
    
}
