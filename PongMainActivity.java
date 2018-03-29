package com.example.tawnymotoyama.pong_motoyama20;


/**
 * Created by TawnyMotoyama on 3/19/18.
 */

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;

/**
 * PongMainActivity
 *
 * This is the activity for the Pong game. It attaches a PongAnimator to
 * an AnimationSurface.
 * This game animates a ball that bounces off three edges of a rectangular screen.
 * On the top edge, bricks are created and can be destroyed if a ball hits a brick.
 * On the bottom edge, the ball may bounce off the movable paddle or "fall off" the screen
 * if it does not hit the paddle.
 * Once a ball falls off, the ball will reappear by falling from a random position at the top
 * off the screen.
 * The first ball is dropped automatically when the program is ran.
 * More balls may be added by tapping the screen.
 * The game keeps track of the users score.
 * The user gains 1 point for each time the ball bounces off the paddle and is penalized 5 points
 * for each ball that is dropped.
 * If the user drops 5 balls, he/she loses.
 * If the user destroys all the bricks before he/she drops 5 balls, he/she WINS!
 *
 * @author Andrew Nuxoll
 * @author Steven R. Vegdahl
 * @version July 2013
 *
 * @author Tawny Motoyama
 * @version March 2018
 *
 */
public class PongMainActivity extends Activity {

    /**
     * creates an AnimationSurface containing a PongAnimator.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pong_main);

        // Connect the animation surface with the animator
        AnimationSurface mySurface = (AnimationSurface) this
                .findViewById(R.id.animationSurface);
        mySurface.setAnimator(new PongAnimator());
    }
}
