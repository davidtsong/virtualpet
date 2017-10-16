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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.Timer;
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
        timer = new Timer(400, this);
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

    public void setImage(String mood) {
        timer.stop();
        pics.clear();
        getImages(mood);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        loopslot++;

        if (loopslot >= pics.size()) {
            loopslot = 0;
        }

        imagePanel.repaint();

        if (loopslot == pics.size()) {
            timer.restart();
        }
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

        for (int i = 0; i < files.length; i++) {
            if (files[i].contains(mood)) {
                pics.add(allPics[i]);
            }
        }
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
        public ImagePanel() {
            super();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (pics.size() > 0) {
                g.drawImage(pics.get(loopslot), 0, 0, this.getWidth(), this.getHeight(), null);
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




