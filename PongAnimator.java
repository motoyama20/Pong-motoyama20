package com.example.tawnymotoyama.pong_motoyama20;

/**
 * Created by TawnyMotoyama on 3/19/18.
 */


import android.graphics.*;
import android.view.MotionEvent;
import java.util.ArrayList;


/**
 * Class that animates the ball that bounces off three edges of a rectangular screen.
 * Also makes bricks disappear once a ball hits one.
 * More balls may be added by tapping the screen.
 * Triggers the end screen and ends the game when the user wins/loses
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
    private ArrayList<NewBall> balls = new ArrayList<NewBall>(); //to hold the balls
    private boolean firstBall = true; //to trigger first ball automatically when run
    private int paddleTouchX; //to allow for a movable paddle (moves on an x axis)
    private int score; //to keep track of the user's score
    private int totalDrop; //to keep track of number of balls dropped
    private int endScreenTimer; //make-shift timer for lose screen
    private int x=0; //to set x position of bricks
    private int y=0; //to set y position of bricks
    private int currNumX; //x position of a particular ball
    private int currNumY; //y position of a particular ball
    private Brick[] bricks = new Brick[9]; //to hold 9 bricks
    private boolean reverse=false; //to bounce the ball off remaining bricks
    int winCount; //counts bricks destroyed to determine when the user wins

    /**
     External Citation
     Date:     21 March 2018
     Problem:  Couldn't figure out how to efficiently store the balls
     Resource: suggestion by classmate, Jason Twigg
     Solution: I used an array list so that the balls could be easily added and looped through
     to be redrawn each tick.
     */

    /**
     * Constructor to perform initial set up on bricks
     */
    public PongAnimator() {

        //set initial paddle position to bottom center of screen
        paddleTouchX = 1000;

        /*
        set the bricks at their starting position when the game is run
         */
        int i;
        for( i=0; i<bricks.length; i++ ) {
                int yellow = Color.YELLOW;
                bricks[i] = new Brick( x+20, y, yellow ); //create a new brick at proper position
                x+=220; //to increment x for the next brick
        }
    }


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
     * Action to perform on clock tick
     *
     * @param g the graphics object on which to draw
     */
    public void tick(Canvas g) {

        //loop through each existing ball and check to see if they hit a brick
        //if one did hit a brick, have it bounce off the brick (not the wall)
        for( NewBall b: balls ) {
            currNumX = b.getNumX();
            currNumY = b.getNumY();
            drawBricks(g);
            if(reverse) {
                b.reverseY();
                reverse=false;
            }
        }

        //if 5 balls are dropped, trigger lose screen
        if( totalDrop >= 5 ) {
            drawLoseFrame(g);
        }
        //if all bricks are destroyed (before 5 balls are dropped), trigger win screen!
        else if(winCount>=9) {
            drawWinFrame(g);
        }
        //if less than 5 balls are dropped do the following
        else {

            Paint magentaPaint = new Paint(); //set paint for the paddle

            /*
             triggers the first ball to automatically fall when game is run
             - creates a new ball (the first ball)
             - adds it to the ball array list
             - draw the paddle at starting position
            */
            if(firstBall) {
                NewBall first = new NewBall();
                balls.add(first);
                firstBall = false;

                //draws a magenta colored paddle in the center of the bottom edge of screen
                magentaPaint.setColor(Color.MAGENTA);
                g.drawRect(574, 1352, 1474, 1392, magentaPaint);
            }
            else {
                //draws movable paddle based on user's touch
                magentaPaint.setColor(Color.MAGENTA);
                g.drawRect(paddleTouchX-450, 1352, paddleTouchX+450, 1392, magentaPaint);
            }


            //runs through the array list of balls and draws each
            //keep track of total running score
            //keep track of total balls dropped
            for( NewBall b : balls ) {

                b.drawBall(g, paddleTouchX);
                score = b.getCurrScore();
                totalDrop = b.getDropNum();

                if( totalDrop >= 5 ) { break; } //at next "tick" lose screen will be triggered
            }

            //draw the score and numbers of balls dropped on the screen
            Paint blackPaint = new Paint();
            blackPaint.setColor(Color.BLACK);
            blackPaint.setTextSize(70.0f);
            g.drawText("Your current score is "+score, 20.0f, 150.0f, blackPaint);
            g.drawText("Balls dropped : "+totalDrop, 20.0f, 230.0f, blackPaint);
        }
    }

    /**
     * Draws the "lose screen" with a message that says "You Lose :(" when 5 balls are dropped
     *
     * @param g the graphics object on which to draw
     */
    public void drawLoseFrame( Canvas g ) {

        //set background to white
        Paint whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        g.drawRect(0.0f, 0.0f, 2048.0f, 1392.0f, whitePaint);

        //draw the lose message in red
        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setTextSize(300.0f);
        g.drawText("You Lose :(", 320.0f, 700.0f, redPaint);

        //the following acts as a make-shift timer so that the game quits after about 5sec
        endScreenTimer++;
        if( endScreenTimer >= 150 ) {
            System.exit(0);
        }
    }

    /**
     * Draws the "win screen" with a message that says "You Win (:" if all bricks are
     * destroyed before 5 balls are dropped.
     *
     * @param g the graphics object on which to draw
     */
    public void drawWinFrame( Canvas g ) {

        //set background to white
        Paint whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        g.drawRect(0.0f, 0.0f, 2048.0f, 1392.0f, whitePaint);

        //draw the win message in yellow
        Paint yellowPaint = new Paint();
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setTextSize(300.0f);
        g.drawText("You Win (:", 320.0f, 700.0f, yellowPaint);
        yellowPaint.setTextSize(90.0f);
        g.drawText("Your final score was "+score, 480.0f, 1000.0f, yellowPaint);

        //the following acts as a make-shift timer so that the game quits after about 5sec
        endScreenTimer++;
        if( endScreenTimer >= 150 ) {
            System.exit(0);
        }

        /**
         External Citation
         Date:     27 March 2018
         Problem:  Couldn't remember how to exit out of application
         Resource: https://stackoverflow.com/questions/6014028/closing-application-with-exit-button
         Solution: I used the function used in the example provided (System.exit)
         */


    }

    /**
     * Draws the bricks at the top of the screen and "destroys" bricks that have been hit by
     * a ball.
     *
     * @param g the graphics object on which to draw
     */
    public void drawBricks( Canvas g ) {

        //loop through each existing brick
        int i;
        for( i=0; i<bricks.length; i++ ) {

            /*
                if a ball hits a brick that has not been hit yet, have the ball bounce off the
                brick, and "destroy" the brick by redrawing it to the background color
             */
            if((currNumX>(x+20) && currNumX<(x+220) && currNumY<(y+100)) &&
                    (bricks[i].getColorInt()!=backgroundColor())) {
                x+=220;
                winCount++;
                reverse = true;
                int brickColor = backgroundColor();
                bricks[i]= new Brick(x+20,y, brickColor);
                bricks[i].drawBrick(g);
            }

            /*
                if the ball enters an area where an existing brick used to be, have it continue
                to the wall before bouncing off (redraw "invisible brick" to background color)
             */
            else if((currNumX>(x+20) && currNumX<(x+220) && currNumY<(y+100)) &&
                    (bricks[i].getColorInt()==backgroundColor())) {
                x+=220;
                int brickColor = backgroundColor();
                bricks[i]= new Brick(x+20,y, brickColor);
                bricks[i].drawBrick(g);
            }

            /*
                if the brick is not hit, redraw it as is (yellow), in its appropriate location
             */
            else {
                bricks[i].drawBrick(g);
            }

            x+=220; //increment x position for the next brick
        }

        x=0; //reset x position to zero after all bricks have been checked and redrawn

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
     * Cary out the following actions based on user's interaction with the screen:
     * - Create new ball when screen is tapped and add it to the ball array list
     * - Keep track of user's touch on the paddle
     */
    public void onTouch(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            //when user touches screen above the paddle
            if( event.getY() < 1140 ) {

                //create new instance of a ball and add it to the array list of balls
                NewBall makeBall = new NewBall();
                balls.add(makeBall);
            }

        }

        //when user touches paddle
        if( event.getY() > 1140 ) {
            //keep resetting paddle's x position as a user drags their finger across the screen
            paddleTouchX = (int) event.getX();
        }

    }

}//class PongAnimator
