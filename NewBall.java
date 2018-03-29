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

    //instance variables
    private int countX; //count that continues to move ball on x axis
    private int countY; //count that continues to move ball on y axis
    private boolean xGoBackwards = false; //to reverse x direction of ball
    private boolean yGoBackwards = false; //to reverse y direction of ball
    private int speed; //to scale the speed of the ball
    private static int currScore=0; //to keep track of score count for each ball
    private static int dropNum=0; //accounts for the ball if dropped
    private int numX; //x position of ball
    private int numY; //y position of ball


    /**
     * Constructor to set initial location and speed factor of ball
     */
    public NewBall() {
        countX = (int) (Math.random()*100+1);
        countY = 10;
        speed = 15;
    }

    /**
     * Draw the ball and manage x and y coordinate of ball (including direction)
     * Have it act appropriately if it hits an edge of the screen or the paddle
     *
     * @param g the graphics object on which to draw
     */
    public void drawBall( Canvas g, int paddleX ) {

        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);

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
        numX = (countX*speed);
        numY = (countY*speed);

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

        /*
            if the ball hits a part of the bottom wall that is not the paddle:
            - let the ball "fall" past the screen and reappear randomly at the top at a
                random speed
            - subtract 5 from the total score
            - add one to the drop count
         */
        if( numY>g.getHeight() && (numX<(paddleX-450) || numX>(paddleX+450)) ) {
            countX = (int) (Math.random()*(g.getWidth()/speed)+1);
            countY = 10;
            currScore-=5;
            dropNum++;

        }

        //if the ball hits the paddle, make it bounce off the paddle by reversing y-direction
        //also increase the total score by 1
        if( numX>(paddleX-450) && numX<(paddleX+450) && numY>1292 ) {
            yGoBackwards = true;
            currScore+=1;
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

    /**
     * To reverse the y direction and have the ball bounce off a brick if it hits one
     */
    public void reverseY() {
        yGoBackwards = false;
    }

    public int getCurrScore() {return currScore;}
    public int getDropNum() {return dropNum;}

    public int getNumX() {return numX;}
    public int getNumY() {return numY;}

}
