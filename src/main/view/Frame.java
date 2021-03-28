package main.view;

import main.cryptogram.Cryptogram;
import main.game.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * This class if the main frame for the game itself
 * this class handles the display of the UI, which
 * the user will see most of the time.
 */
public class Frame {

    public static final int FRAME_WIDTH = 852;
    public static final int FRAME_HEIGHT = 480;
    public static final Color QUEENBLUE = new Color(69,95,126);
    public static final Color GUNMETAL = new Color(38,52,69);

    public final String FRAME_TITLE = "Cryptogrammer";

    private JFrame frame;
    private FrequenciesPanel frequenciesPanel;
    private WordHolder wordHolder;
    private ButtonHolder buttonHolder;
    private final Game gameController;

    public Frame(String name, Game gameController){
        this.gameController = gameController;

        initFrame(name);

        MenuBarHolder menuHolder = new MenuBarHolder(gameController, frame);
        frame.add(menuHolder.getHolder());

        frame.setVisible(false);
        frame.setLocationRelativeTo(null);
    }

    /**
     * We initializes the frame itself
     * @param name      username
     */
    private void initFrame(String name) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        frame = new JFrame(gs[0].getDefaultConfiguration());

        frame.setTitle(FRAME_TITLE);
        try {
            frame.setIconImage(ImageIO.read(new File("resources/assets/joystick.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.setResizable(false);

        // Here we add the buttons on the bottom of the frame
        buttonHolder = new ButtonHolder(name, gameController);
        frame.add(buttonHolder.getHolder(), BorderLayout.PAGE_END);
    }


    /**
     * Here we display a new set of encrypted
     * sentences, first by removing the old sentence,
     * and then adding the new set.
     * @param cryptogram    Cryptogram to be displayed
     */
    public void displayNewGame(Cryptogram cryptogram){

        // Resetting the components to display new sentences
        try{
            frame.remove(frequenciesPanel.getHolder());
            frame.remove(wordHolder.getHolder());
        }
        catch (NullPointerException e){
            System.err.println("Components were already null, cannot remove them from Frame.");
        }

        wordHolder = null;
        frequenciesPanel = null;

        // Adding new encrypted sentence to the frame
        wordHolder = new WordHolder(frame, this);
        wordHolder.displayNewSentence(cryptogram.getPhrase());

        if(!gameController.viewFrequencies().isEmpty()){
            frequenciesPanel = new FrequenciesPanel(gameController.viewFrequencies());
            frame.add(frequenciesPanel.getHolder(), BorderLayout.LINE_START);
        }

        frame.add(wordHolder.getHolder(), BorderLayout.CENTER);

        buttonHolder.enableHintButton();
        buttonHolder.enableSaveGameButton();
        buttonHolder.enableSolutionButton();
        buttonHolder.enableResetButton();

        if(!frame.isVisible()){
            frame.setVisible(true);
        }

        frame.setBackground(QUEENBLUE);

        frame.requestFocus();
        frame.toFront();
        frame.pack();
        frame.revalidate();

        frame.setLocationRelativeTo(null);

    }

    public void displayEmptyScreen(){
        // Resetting the components to display new sentences
        try{
            frame.remove(frequenciesPanel.getHolder());
            frame.remove(wordHolder.getHolder());
        }
        catch (NullPointerException e){
            System.err.println("Components were already null, cannot remove them from Frame.");
        }

        wordHolder = null;
        frequenciesPanel = null;

        // Adding new encrypted sentence to the frame
        wordHolder = new WordHolder(frame, this);
        wordHolder.displayEmptyPanel();

        frequenciesPanel = new FrequenciesPanel("");

        frame.add(wordHolder.getHolder(), BorderLayout.CENTER);

        if(!frame.isVisible()){
            frame.setVisible(true);
        }

        frame.requestFocus();
        frame.toFront();
        frame.pack();
        frame.revalidate();

        frame.setLocationRelativeTo(null);

        frame.requestFocus();
        frame.toFront();
    }

    public WordHolder getWordHolder() {
        return wordHolder;
    }

    public Game getGameController() {
        return gameController;
    }

    public JFrame getFrame() {
        return frame;
    }

    public ButtonHolder getButtonHolder() {
        return buttonHolder;
    }
}
