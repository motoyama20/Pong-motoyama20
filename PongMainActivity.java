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
 * On the fourth edge, the ball may bounce off the stationary paddle or "fall off" the screen
 * if it does not hit the paddle.
 * Once a ball falls off, the ball will reappear by falling from a random position at the top
 * off the screen.
 * The first ball is dropped automatically when the program is ran.
 * More balls may be added by tapping the screen.
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
