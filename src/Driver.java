import main.players.Player;
import main.view.NewPlayerFrame;

import java.io.IOException;

/**
 * Starting point for our program. The View model will be instantiated
 * from here.
 *
 * @author Zoltan Kiss
 * @since 22/02/2021
 */

public class Driver {

    public static void main(String[] args){

        try {
            NewPlayerFrame frame = new NewPlayerFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
