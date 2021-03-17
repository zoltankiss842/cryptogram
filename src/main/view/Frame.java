package main.view;

import main.cryptogram.Cryptogram;
import main.game.Game;

import javax.swing.*;
import java.awt.*;

/**
 * This class if the main frame for the game itself
 * this class handles the display of the UI, which
 * the user will see most of the time.
 */
public class Frame {

    public static final int FRAME_WIDTH = 852;
    public static final int FRAME_HEIGHT = 480;

    public final String FRAME_TITLE = "Cryptogrammer";

    private JFrame frame;
    private SolutionPanel solutionPanel;
    private WordHolder wordHolder;
    private ButtonHolder buttonHolder;
    private MenuBarHolder menuHolder;

    private Game gameController;

    public Frame(String name, Game gameController){
        this.gameController = gameController;

        initFrame(name);

        centerFrame();
        frame.pack();
        frame.setVisible(true);
        menuHolder = new MenuBarHolder(gameController, frame);
        frame.add(menuHolder.getHolder());

    }

    /**
     * We initializes the frame itself
     * @param name      username
     */
    private void initFrame(String name) {
        frame = new JFrame(FRAME_TITLE);

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        centerFrame();

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
            frame.remove(solutionPanel.getHolder());
            frame.remove(wordHolder.getHolder());
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        }
        catch (NullPointerException e){
            System.err.println("Components were already null, cannot remove them from Frame.");
        }

        wordHolder = null;
        solutionPanel = null;

        // Adding new encrypted sentence to the frame
        wordHolder = new WordHolder(frame, this);
        wordHolder.displayNewSentence(cryptogram.getPhrase());

        // Adding the solution sentence to the frame
        solutionPanel = new SolutionPanel(cryptogram.getSolution());

        frame.add(solutionPanel.getHolder(), BorderLayout.PAGE_START);
        frame.add(wordHolder.getHolder(), BorderLayout.CENTER);

        centerFrame();
        frame.pack();
    }

    private void centerFrame(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
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


}
