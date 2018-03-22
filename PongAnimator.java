package com.example.tawnymotoyama.pong_motoyama20;

/**
 * Created by TawnyMotoyama on 3/19/18.
 */


import android.graphics.*;
import android.view.MotionEvent;
import java.util.ArrayList;


/**
 * Class that animates a ball that bounces off three edges of a rectangular screen.
 * On the fourth edge, the ball may bounce off the stationary paddle or "fall off" the screen
 * if it does not hit the paddle.
 * Once a ball falls off the ball will reappear by falling from a random position at the top
 * off the screen.
 * More balls may be added by tapping the screen.
 *
 * @author Steve Vegdahl
 * @author Andrew Nuxoll
 * @version February 2016
 *
 * @author Tawny Motoyama
 * @version March 2018
 */
public class PongAnimator implements Animator {

    // instance variables
    private boolean xGoBackwards = false;
    private boolean yGoBackwards = false;
    private ArrayList<NewBall> balls = new ArrayList<NewBall>(); //to hold the balls
    private boolean firstBall = true; //to trigger first ball automatically when run

    /**
     External Citation
     Date:     21 March 2018
     Problem:  Couldn't figure out how to efficiently store the balls
     Resource: suggestion by classmate, Jason Twigg
     Solution: I used an array list so that the balls could be easily added and looped through
     to be redrawn each tick.
     */

    /**
     * Interval between animation frames: .03 seconds (i.e., about 33 times
     * per second).
     *
     * @return the time interval between frames, in milliseconds.
     */
    public int interval() {
        return 30;
    }

    /**
     * The background color: a light blue.
     *
     * @return the background color onto which we will draw the image.
     */
    public int backgroundColor() {
        // create/return the background color
        return Color.rgb(180, 200, 255);
    }

    /**
     * Tells the animation whether to go backwards.
     *
     * @param x true iff animation is to go backwards.
     */
    public void goBackwards(boolean x, boolean y) {
        // set our instance variables for backwards/bounce movement
        xGoBackwards = x;
        yGoBackwards = y;
    }


    /**
     * Action to perform on clock tick
     *
     * @param g the graphics object on which to draw
     */
    public void tick(Canvas g) {

        /*
        triggers the first ball to automatically fall when game is run
        - creates a new ball (the first ball)
        - adds it to the ball array list
         */
        if(firstBall) {
            NewBall first = new NewBall();
            balls.add(first);
            firstBall = false;
        }

        //draws a magenta colored paddle in the center of the bottom edge of screen
        Paint magentaPaint = new Paint();
        magentaPaint.setColor(Color.MAGENTA);
        g.drawRect(574, 1352, 1474, 1392, magentaPaint);

        //runs through the array list of balls and draws each
        for( NewBall b : balls ) {

            b.drawBall(g);
        }
    }

    /**
     * Tells that we never pause.
     *
     * @return indication of whether to pause
     */
    public boolean doPause() {
        return false;
    }

    /**
     * Tells that we never stop the animation.
     *
     * @return indication of whether to quit.
     */
    public boolean doQuit() {
        return false;
    }

    /**
     * reverse the ball's direction when the screen is tapped
     * create new ball when screen is tapped and add it to the ball array list
     */
    public void onTouch(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            xGoBackwards = !xGoBackwards; //reverse x velocity/direction
            yGoBackwards = !yGoBackwards; //reverse y velocity/direction

            //create new instance of a ball and add it to the array list of balls
            NewBall makeBall = new NewBall();
            balls.add(makeBall);
        }

    }



}//class PongAnimator
