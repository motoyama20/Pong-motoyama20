package com.example.tawnymotoyama.pong_motoyama20;

/**
 * Created by TawnyMotoyama on 3/19/18.
 */

import android.graphics.*;
import java.util.Random;

/**
 * Class that creates a new ball and controls its coordinates/velocity vectors (x and y)
 * and determines speed at random
 *
 * @author Tawny Motoyama
 * @version March 2018
 */
public class NewBall {

    private int countX = (int) (Math.random()*100+1);
    private int countY = 0;
    private boolean xGoBackwards = false;
    private boolean yGoBackwards = false;
    private int speed=15;

    /**
     * Draw the ball and manage x and y coordinate of ball
     * Have it act appropriately if it hits an edge of the screen
     *
     * @param g the graphics object on which to draw
     */
    public void drawBall( Canvas g) {

        // bump x and y counts either up or down by one, depending on whether
        // we are in "backwards mode".
        if (xGoBackwards) {
            countX--;
        }
        else {
            countX++;
        }

        if(yGoBackwards) {
            countY--;
        }
        else{
            countY++;
        }

        // Determine the pixel position of our ball.  Multiplying by speed
        // has the effect of moving a certain amount of pixels per frame.

        int numX = (countX*speed); //current x-coordinate of ball
        int numY = (countY*speed); //current y-coordinate of ball


        //if the ball hits the left edge of screen (the x coordinate gets lower than zero)
        // then reverse x-direction to bounce off wall
        if( numX < 0 ) {
            xGoBackwards = false;
        }

        //if the ball hits the right edge of screen (x-coordinate exceeds the width of screen)
        // then bounce off wall by reversing x-direction
        if( numX > g.getWidth() ) {
            xGoBackwards = true;
        }

        //if ball hits top wall, reverse y-direction
        if( numY < 0 ) {
            yGoBackwards = false;
        }

        //if the ball hits a part of the bottom wall that is not the paddle, let the ball
        //"fall" past the screen and reappear randomly at the top at a random speed
        if( numY>g.getHeight() && (numX<565 || numX>1483) ) {
            countX = (int) (Math.random()*(g.getWidth()/speed)+1);
            countY = 0;
            speed();
        }

        //if the ball hits any part of the paddle on the bottom edge, make it bounce off the
        //paddle by reversing y-direction
        if( numX>565 && numX<1483 && numY>1292 ) {
            yGoBackwards = true;
        }


        //draw the red ball based on its current x and y coordinate
        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        g.drawCircle(numX, numY, 60,redPaint);
        redPaint.setColor(0xff0000ff);


    }


    /**
     * Determine a random speed for the ball
     *
     * @return a random speed for the ball
     */
    public int speed() {

        //pick a random speed between 10 and 39
        Random rand = new Random();
        speed = rand.nextInt(30) + 10;
        return speed;

        /**
         External Citation
         Date:     21 March 2018
         Problem:  Couldn't remember how to set min and max value for random using rand.nextInt()
         Resource:
         https://stackoverflow.com/questions/20389890/generating-a-random
         -number-between-1-and-10-java
         Solution: I used the example code from this post.
         */

    }


}
