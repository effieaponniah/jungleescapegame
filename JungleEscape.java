/*
   Written by Effiea Ponniah (effiea.com)
   Created on May 14, 2019
   Last Updated on February 7, 2021
   This game is based off the board game, snakes and ladders. 
   There are two levels and if the user passes the first level he or she can move onto the next level. 
   The user will verse the computer. User's game piece will be a light blue (cyan) circle while, a 
   computer's game piece is magenta. There will be a randomly generated dice which will give the 
   number of tiles to move. The first roll of the user and computer will determind who can start the game. 
   The winner will be determined by who rolls the higher number. If the computer or user rolls 6, 
   they will be asked to roll again. The sum of all the rolls will give how much the computer or user will move. 
   The user or computer will go until 25. The winner will rech 25 or a number higher than 25. 
   If computer wins level one will be played again or user will have the option to exit the game.
*/

import hsa.Console;
import java.awt.*;
import java.io.*;
import sun.audio.*;

public class JungleEscape
{
    static Console c;
    static Console e;
    static int MOVE_DELAY = 1000;
    static Font startGameFt = new Font ("Monospaced", Font.BOLD + Font.ITALIC, 42);
    static Color lightBlue = new Color (103, 186, 255);
    static Color lavender = new Color (218, 137, 255);
    static Color skyBlue = new Color (119, 212, 253);
    static Color royalBlue = new Color (26, 26, 230);
    static Font instructionFont = new Font ("Monospaced", Font.BOLD + Font.ITALIC, 24);

    public static void main (String[] args) throws Exception
    {
        c = new Console (35, 75);
        e = new Console ();

        // Array to store values of the x and y coordinates of the middle of the tiles as a reference point
        int[] xCoordinateMiddle = {100, 200, 300, 400, 500, 500, 400, 300, 200, 100, 100, 200, 300, 400, 500, 500, 400, 300, 200, 100, 100, 200, 300, 400, 500};    // center of one square X cordinates of game board
        int[] yCoordinateMiddle = {500, 500, 500, 500, 500, 400, 400, 400, 400, 400, 300, 300, 300, 300, 300, 200, 200, 200, 200, 200, 100, 100, 100, 100, 100};    // center of one square Y cordinates of game board

        // Tile number of ladder top and bottom
        int[] ladderBottom = {6, 9, 18, 16};
        int[] ladderTop = {15, 19, 23, 25};

        int[] ladderBottomSlant = {9, 16, 4};
        int[] ladderTopSlant = {18, 25, 6};

        // Tile number of snake head and snake tail
        int[] snakeHead = {13, 21, 24, 10};
        int[] snakeTail = {3, 11, 7, 1};

        boolean userStartPlay;
        int userCounter = 0;
        int level;
        String name;
        String repeatGame;

        do
        {
            InputStream is = new FileInputStream ("Background.wav");
            AudioStream as = new AudioStream (is);
            AudioPlayer.player.start (as);

            c.clear ();
            e.clear ();
            instructionPanel ();
            name = getUserName ();
            level = getGameLevel (name);

            if (level == 1)
            {
                gameBoard (xCoordinateMiddle, yCoordinateMiddle, ladderTop, ladderBottom, snakeHead, snakeTail, ladderBottomSlant, ladderTopSlant, level, name);

                userStartPlay = userStartGame (name);

                if (userStartPlay)
                {
                    e.println (name + " will start the game.");
                }
                else
                {
                    e.println ("Computer: I will start the game.");
                }

                playGame (name, userStartPlay, xCoordinateMiddle, yCoordinateMiddle, ladderTop, ladderBottom, snakeHead, snakeTail, ladderBottomSlant, ladderTopSlant, level);
            }
            else
            {
                gameBoard (xCoordinateMiddle, yCoordinateMiddle, ladderTop, ladderBottom, snakeHead, snakeTail, ladderBottomSlant, ladderTopSlant, level, name);

                userStartPlay = userStartGame (name);

                if (userStartPlay)
                {
                    e.println ("The user will start the game.");
                }
                else
                {
                    e.println ("The computer will start the game.");
                }

                playGame (name, userStartPlay, xCoordinateMiddle, yCoordinateMiddle, ladderTop, ladderBottom, snakeHead, snakeTail, ladderBottomSlant, ladderTopSlant, level);
            }
            AudioPlayer.player.stop (as);

            c.setFont (startGameFt);
            c.setColor (royalBlue);
            c.drawString ("Play Again?", 25, 410);

            e.println ("Would you like to play again? Enter [y]. \nTo exit press any other key.");
            repeatGame = e.readString ();
        }
        while (repeatGame.equalsIgnoreCase ("y"));
        e.println ("END OF GAME.");
        c.clear();
        c.setColor (skyBlue);
        c.fillRect (0, 0, c.maxx (), c.maxy ());
        c.setColor (royalBlue);
        c.setFont (startGameFt);
        c.drawString ("END OF GAME. . .", 50, 370);
    }


    // Display game instructions before start the game
    public static void instructionPanel ()
    {

        c.setColor (skyBlue);
        c.fillRect (0, 0, c.maxx (), c.maxy ());
        c.setColor (royalBlue);

        Font welcomeFont = new Font ("Serif", Font.BOLD + Font.ITALIC, 72);
        c.setFont (welcomeFont);
        c.drawString ("  Welcome to", 100, 150);
        c.drawString ("Jungle Escape ", 100, 450);
        delay (MOVE_DELAY * 2);
        c.clear ();

        c.setColor (skyBlue);
        c.fillRect (0, 0, c.maxx (), c.maxy ());

        c.setColor (royalBlue);

        c.setFont (instructionFont);
        String userReturn;

        do
        {
            c.drawString ("------------INSTRUCTIONS-----------", 20, 50);
            c.drawString ("- You will be playing against the", 20, 100);
            c.drawString ("  computer", 20, 130);
            c.drawString ("- There will be a randomly generated", 20, 180);
            c.drawString ("  dice which will give the number of", 20, 210);
            c.drawString ("  tiles to move.", 20, 240);
            c.drawString ("- The winner will be determined by", 20, 290);
            c.drawString ("  the higher value first die roll", 20, 320);
            c.drawString ("- If the computer or user rolls 6,", 20, 370);
            c.drawString ("  they will be asked to roll again.", 20, 400);
            c.drawString ("  When rolled 6, the sum of all the", 20, 430);
            c.drawString ("  rolls will determine how many tiles", 20, 460);
            c.drawString ("  to move", 20, 490);
            c.drawString ("- The winner will be the first to reach ", 20, 540);
            c.drawString ("  tile 25 or higher.", 20, 560);
            c.drawString ("Enter [y] to begin.", 150, 620);
            e.println ("Enter [y] to start.");
            userReturn = e.readString ();
        }
        while (!userReturn.equalsIgnoreCase ("y"));
        c.clear ();
    }


    // Ask the player to choose the game level and return game level#
    public static int getGameLevel (String playerName)
    {
        int gameLevel = 0;

        c.setColor (skyBlue);
        c.fillRect (0, 0, c.maxx (), c.maxy ());
        c.setColor (royalBlue);
        c.setFont (startGameFt);
        c.drawString ("CHOOSE A LEVEL ...", 50, 370);

        do
        {
            e.println ("What level would you like to try? \nEnter 1: Level [1]: Basic Straight Ladders and Snakes \nEnter 2: Level [2]: Slanted Ladders and Snakes");
            gameLevel = e.readInt ();
        }
        while (gameLevel != 1 && gameLevel != 2);


        c.setColor (skyBlue);
        c.fillRect (0, 0, c.maxx (), c.maxy ());

        c.setColor (royalBlue);
        c.setFont (startGameFt);
        c.drawString ("Welcome " + playerName, 30, 370);
        delay (MOVE_DELAY * 3);
        return gameLevel;
    }


    // Read player name and return player name
    public static String getUserName ()
    {
        String userName;
        c.setColor (skyBlue);
        c.fillRect (0, 0, c.maxx (), c.maxy ());
        c.setColor (royalBlue);
        c.setFont (startGameFt);
        c.drawString ("GETTING PLAYER NAME ...", 20, 370);
        e.print ("Enter your name: ");
        userName = e.readString ();

        return userName;
    }


    // Delay method which is used to help set up delay times
    public static void delay (int time)
    {
        try
        {
            Thread.sleep (time);
        }

        catch (InterruptedException e)
        {
        }
    }


    // Game board display / graphics
    public static void gameBoard (int[] xCoordinate1, int[] yCoordinate1, int[] ladderTop1, int[] ladderBottom1,
            int[] snakeHead1, int[] snakeTail1, int[] ladderBottomSlant1, int[] ladderTopSlant1, int gameLevel, String nameOfUser)
    {
        Color green = new Color (144, 232, 163);
        c.setColor (green);
        c.fillRect (0, 0, c.maxx (), c.maxy ());
        c.setColor (lightBlue);
        c.fillRect (50, 50, 500, 500);
        for (int i = 50 ; i <= 500 ; i += 200)
        {
            for (int j = 50 ; j <= 500 ; j += 200)
            {
                c.setColor (lavender);
                c.fillRect (i, j, 100, 100);
            }
        }

        for (int i = 150 ; i <= 500 ; i += 200)
        {
            for (int j = 150 ; j <= 500 ; j += 200)
            {
                c.setColor (lavender);
                c.fillRect (i, j, 100, 100);
            }
        }

        c.setColor (Color.black);
        c.setFont (instructionFont);
        c.drawString ("-\t" + nameOfUser + "", 70, 620);
        c.drawString ("-\tComputer", 70, 660);
        c.setColor (Color.cyan);
        c.fillOval (50, 600, 20, 20);
        c.setColor (Color.black);
        c.setColor (Color.magenta);
        c.fillOval (50, 640, 20, 20);

        setGameBoardNumbers (xCoordinate1, yCoordinate1);
        drawLaddersAndSnakes (xCoordinate1, yCoordinate1, ladderTop1, ladderBottom1, snakeHead1, snakeTail1, ladderBottomSlant1, ladderTopSlant1, gameLevel);
    }


    // In this method the computer and user rolls dies to move from one place to another
    // Computer or user move up the ladder if they land on the bottom of the ladder
    // or come down the snake if they land on the snake head
    // If computer or user rolls number 6, they are allowed to roll again before they move
    public static void playGame (String playerName, boolean userTurn, int[] xCoordinate2, int[] yCoordinate2,
            int[] ladderTop2, int[] ladderBottom2, int[] snakeHead2, int[] snakeTail2, int[] ladderBottomSlant2,
            int[] ladderTopSlant2, int levelGame)
    {
        String userRollDiceCheck;
        int userDiceValue;
        int compDiceValue;
        int userCurLanding = 0;
        int compCurLanding = 0;
        int userPreLanding = 1;
        int compPreLanding = 1;

        do
        {
            if (userTurn)
                e.setColor (Color.cyan);
            else
                e.setColor (Color.magenta);

            if (userTurn)
            {
                // If user rolls 6, they will roll again until a number that is not 6 is rolled
                do
                {
                    e.println (playerName + ", press [r] to roll the die.");
                    userRollDiceCheck = e.readString ();


                    while (!userRollDiceCheck.equalsIgnoreCase ("r"))
                    {
                        e.println ("Press enter valid input. Press r to roll dice.");
                        userRollDiceCheck = e.readString ();
                    }
                    userDiceValue = (int) (6 * Math.random () + 1);
                    userCurLanding += userDiceValue;
                    e.println (playerName + " has rolled " + userDiceValue);
                    rollDice (userDiceValue);

                    if (userDiceValue == 6)
                    {
                        e.println (playerName + " rolled the value 6. You may roll again.");
                    }

                }
                while (userDiceValue == 6);

                e.println (playerName + " moving to " + userCurLanding);

                if (userCurLanding >= 25)
                    break;

                movePosition (xCoordinate2, yCoordinate2, userCurLanding, userPreLanding, false, userTurn);
                userPreLanding = userCurLanding;

                if (levelGame == 1)
                {
                    for (int i = 0 ; i < ladderBottom2.length ; i++)
                    {
                        if (ladderBottom2 [i] == userCurLanding)
                        {
                            userCurLanding = ladderTop2 [i];
                            e.println (playerName + " has landed on a ladder, moving up to " + userCurLanding);
                            movePosition (xCoordinate2, yCoordinate2, userCurLanding, userPreLanding, true, userTurn);
                            userPreLanding = userCurLanding;
                            break;
                        }
                    }
                }

                else
                {
                    for (int i = 0 ; i < ladderBottomSlant2.length ; i++)
                    {
                        if (ladderBottomSlant2 [i] == userCurLanding)
                        {
                            userCurLanding = ladderTopSlant2 [i];
                            e.println (playerName + " has landed on a ladder, moving up to " + userCurLanding);
                            movePosition (xCoordinate2, yCoordinate2, userCurLanding, userPreLanding, true, userTurn);
                            userPreLanding = userCurLanding;
                            break;
                        }
                    }
                }

                for (int i = 0 ; i < snakeHead2.length ; i++)
                {
                    if (snakeHead2 [i] == userCurLanding)
                    {
                        userCurLanding = snakeTail2 [i];
                        e.println ("User has landed on a snake, moving down to " + userCurLanding);
                        movePosition (xCoordinate2, yCoordinate2, userCurLanding, userPreLanding, true, userTurn);
                        userPreLanding = userCurLanding;
                        break;
                    }
                }
                userTurn = false;
            }
            else
            {
                // If 6 is rolled the computer can roll again until a number that is not 6 is rolled
                do
                {
                    e.println ("It's my time to roll the dice.");
                    compDiceValue = (int) (6 * Math.random () + 1);
                    compCurLanding += compDiceValue;
                    e.println ("Computer: I rolled " + compDiceValue);
                    rollDice (compDiceValue);

                    if (compDiceValue == 6)
                    {
                        e.println ("Computer: I have rolled the value 6. I will roll again.");
                    }
                }
                while (compDiceValue == 6);

                e.println ("Computer: I am moving to " + compCurLanding);

                if (compCurLanding >= 25)
                    break;

                movePosition (xCoordinate2, yCoordinate2, compCurLanding, compPreLanding, false, false);
                compPreLanding = compCurLanding;

                if (levelGame == 1)
                {
                    for (int i = 0 ; i < ladderBottom2.length ; i++)
                    {
                        if (ladderBottom2 [i] == compCurLanding)
                        {
                            compCurLanding = ladderTop2 [i];
                            e.println ("Compuer: I have landed on a ladder, moving up to " + compCurLanding);
                            movePosition (xCoordinate2, yCoordinate2, compCurLanding, compPreLanding, true, false);
                            compPreLanding = compCurLanding;
                            break;
                        }
                    }
                }
                else
                {
                    for (int i = 0 ; i < ladderBottomSlant2.length ; i++)
                    {
                        if (ladderBottomSlant2 [i] == compCurLanding)
                        {
                            compCurLanding = ladderTopSlant2 [i];
                            e.println ("Computer: I have landed on a ladder, moving up to " + compCurLanding);
                            movePosition (xCoordinate2, yCoordinate2, compCurLanding, compPreLanding, true, false);
                            compPreLanding = compCurLanding;
                            break;
                        }
                    }
                }

                for (int i = 0 ; i < snakeHead2.length ; i++)
                {
                    if (snakeHead2 [i] == compCurLanding)
                    {
                        compCurLanding = snakeTail2 [i];
                        e.println ("Computer: I have landed on a snake, moving down to " + compCurLanding);
                        movePosition (xCoordinate2, yCoordinate2, compCurLanding, compPreLanding, true, false);
                        compPreLanding = compCurLanding;
                        break;
                    }
                }
                userTurn = true;
            }
            delay (1000);
        }
        while (compCurLanding < 25 && userCurLanding < 25);

        // Winner displays after icon lands on 25 or a number greater than 25
        if (compCurLanding >= 25)
        {
            movePosition (xCoordinate2, yCoordinate2, 25, compPreLanding, false, false);
            winnerDisplay (false, playerName);
            e.println ("I WON THE GAME");
        }
        else
        {
            movePosition (xCoordinate2, yCoordinate2, 25, userPreLanding, false, true);
            winnerDisplay (true, playerName);
            e.println ("USER WON THE GAME");
        }
    }


    // Draws and show the dice value face in the console
    // Dice value is passed as a parameter
    public static void rollDice (int diceValue2)
    {
        c.setColor (Color.red);
        c.fillRect (500, 600, 50, 50);
        c.setColor (Color.white);

        switch (diceValue2)
        {
            case 1:
                c.fillOval (520, 620, 10, 10);
                break;
            case 2:
                c.fillOval (505, 605, 10, 10);
                c.fillOval (535, 635, 10, 10);
                break;
            case 3:
                c.fillOval (520, 620, 10, 10);
                c.fillOval (505, 605, 10, 10);
                c.fillOval (535, 635, 10, 10);
                break;
            case 4:
                c.fillOval (505, 605, 10, 10);
                c.fillOval (535, 635, 10, 10);
                c.fillOval (505, 635, 10, 10);
                c.fillOval (535, 605, 10, 10);
                break;
            case 5:
                c.fillOval (505, 605, 10, 10);
                c.fillOval (535, 635, 10, 10);
                c.fillOval (505, 635, 10, 10);
                c.fillOval (535, 605, 10, 10);
                c.fillOval (520, 620, 10, 10);
                break;
            case 6:
                c.fillOval (505, 620, 10, 10);
                c.fillOval (535, 620, 10, 10);
                c.fillOval (505, 605, 10, 10);
                c.fillOval (535, 635, 10, 10);
                c.fillOval (505, 635, 10, 10);
                c.fillOval (535, 605, 10, 10);
                break;
        }
        delay (MOVE_DELAY * 3);

    }


    // Display the Winner detail
    // userWinner parameter is true if the user is the winner and name of the user
    public static void winnerDisplay (boolean userWinner, String playerName1)
    {
        delay (MOVE_DELAY * 2);
        c.clear ();
        c.setColor (skyBlue);
        c.fillRect (0, 0, c.maxx (), c.maxy ());

        c.setColor (royalBlue);

        for (int i = 0 ; i <= 600 ; i += 50)
        {
            c.fillStar (i, 200, 50, 50);
        }

        if (userWinner)
        {
            c.setFont (startGameFt);

            playerName1 = playerName1.toUpperCase ();
            c.drawString (playerName1 + " WINS GAME", 50, 300);
        }

        else
        {
            c.setFont (startGameFt);
            c.drawString ("- COMPUTER WINS GAME -", 25, 300);
            c.drawString ("Better luck next time...", 25, 360);
        }

        delay (MOVE_DELAY * 2);
    }


    // Draw Ladders and Snakes on the game board
    public static void drawLaddersAndSnakes (int[] xCoordinate3, int[] yCoordinate3, int[] ladderTop, int[] ladderBottom, int[] snakeHead, int[] snakeTail, int[] ladderBottomSlant, int[] ladderTopSlant, int gameLevel)
    {
        c.setColor (Color.black);

        if (gameLevel == 1)
        {
            for (int i = 0 ; i < ladderTop.length ; i++)
            {
                c.fillRect (xCoordinate3 [ladderTop [i] - 1], yCoordinate3 [ladderTop [i] - 1], 10, yCoordinate3 [ladderBottom [i] - 1] - yCoordinate3 [ladderTop [i] - 1]);
            }
        }

        else
        {
            for (int i = 0 ; i < ladderTopSlant.length ; i++)
            {
                int[] ladderX = {xCoordinate3 [ladderTopSlant [i] - 1], xCoordinate3 [ladderTopSlant [i] - 1] + 3, xCoordinate3 [ladderBottomSlant [i] - 1] + 3, xCoordinate3 [ladderBottomSlant [i] - 1] };
                int[] ladderY = {yCoordinate3 [ladderTopSlant [i] - 1], yCoordinate3 [ladderTopSlant [i] - 1] + 3, yCoordinate3 [ladderBottomSlant [i] - 1] + 3, yCoordinate3 [ladderBottomSlant [i] - 1] };

                c.fillPolygon (ladderX, ladderY, 4);
            }
        }

        // Draw snakes drawArc method
        for (int i = 0 ; i < snakeHead.length ; i++)
        {
            // Making three layers to emphasize thickness of snakes
            for (int j = 0 ; j < 3 ; j++)
            {
                drawSnake (xCoordinate3 [snakeHead [i] - 1] + j, yCoordinate3 [snakeHead [i] - 1] + j, xCoordinate3 [snakeTail [i] - 1] + j, yCoordinate3 [snakeTail [i] - 1] + j, 15);
            }
        }
    }


    // Draw Snake with multiple curves
    public static void drawSnake (int headX, int headY, int tailX, int tailY, int numOfCurve)
    {
        int snakeLength = tailY - headY;
        int curveHeight = snakeLength / numOfCurve;
        int curveWidth = snakeLength / numOfCurve;
        Color green = new Color (23, 141, 68);
        c.setColor (green);
        c.fillOval (headX, headY - 15, 10, 15);

        for (int i = 0 ; i < numOfCurve ; i++)
        {
            if (i % 2 == 0)
                c.drawArc (headX, headY + i * curveWidth, curveHeight, curveWidth, 90, 180);
            else
                c.drawArc (headX, headY + i * curveWidth, curveHeight, curveWidth, -90, 180);
        }
    }


    // Set numbers on the game board according to their specific locations
    public static void setGameBoardNumbers (int[] xCoordinate4, int[] yCoordinate4)
    {
        c.setColor (Color.black);
        Font ft = new Font ("ariel", Font.BOLD + Font.ITALIC, 24);
        c.setFont (ft);

        for (int i = 0 ; i < 25 ; i++)
        {
            c.drawString ((i + 1) + "", xCoordinate4 [i] + 20, yCoordinate4 [i] + 40);
        }
    }


    // Determine whether or not user can start the game.
    // Winner is determined by who rolls the higher number
    public static boolean userStartGame (String nameOfPlayer)
    {
        String userRollDice;
        int userDiceValue;
        int compDiceValue;

        boolean userWinner = false;

        // Determine who will start first
        do
        {
            e.println ("Press r to roll dice.");
            userRollDice = e.readString ();
            while (!userRollDice.equalsIgnoreCase ("r"))
            {
                e.println ("Press enter valid input. Press r to roll dice.");
                userRollDice = e.readString ();
            }

            userDiceValue = (int) (6 * Math.random () + 1);
            compDiceValue = (int) (6 * Math.random () + 1);
            e.println (nameOfPlayer + " rolled: " + userDiceValue);
            e.println ("Computer: I have rolled: " + compDiceValue);

            if (userDiceValue > compDiceValue)
            {
                userWinner = true;
            }
            else if (userDiceValue == compDiceValue)
            {
                e.println ("Tie. Please roll again.");
            }
        }
        while (userDiceValue == compDiceValue);
        return userWinner;
    }


    // Animating computer and user icons
    public static void movePosition (int[] xCoordinate5, int[] yCoordinate5, int curPosition, int prePosition,
            boolean snakeOrLadder, boolean userMove)
    {
        int displacement;
        int color;
        if (userMove)
            displacement = -35;
        else
            displacement = 15;

        // When the first dice roll is 1 for user or computer, move to #1
        if (curPosition == 1)
        {
            if (userMove)
                c.setColor (Color.cyan);
            else
                c.setColor (Color.magenta);

            c.fillOval (xCoordinate5 [0] + displacement, yCoordinate5 [0] - 10, 20, 20);
        }

        // If snake and ladder is reached the icon will move directly to either the top of the ladder or the snake tail
        if (snakeOrLadder)
        {
            if (prePosition % 2 == 0)
            {
                c.setColor (lightBlue);
                c.fillOval (xCoordinate5 [prePosition - 1] + displacement, yCoordinate5 [prePosition - 1] - 10, 20, 20);
            }
            else
            {
                c.setColor (lavender);
                c.fillOval (xCoordinate5 [prePosition - 1] + displacement, yCoordinate5 [prePosition - 1] - 10, 20, 20);

            }
            if (userMove)
                c.setColor (Color.cyan);
            else
                c.setColor (Color.magenta);

            c.fillOval (xCoordinate5 [curPosition - 1] + displacement, yCoordinate5 [curPosition - 1] - 10, 20, 20);
            delay (MOVE_DELAY);
        }
        
        //  The icon will move from 1 until 25 and will clear the previous position to show animation
        else    
        {
            for (int i = prePosition ; i < curPosition ; i++)
            {
                if (i % 2 == 0)
                {
                    c.setColor (lightBlue);
                    c.fillOval (xCoordinate5 [i - 1] + displacement, yCoordinate5 [i - 1] - 10, 20, 20);
                }
                else
                {
                    c.setColor (lavender);
                    c.fillOval (xCoordinate5 [i - 1] + displacement, yCoordinate5 [i - 1] - 10, 20, 20);
                }

                if (userMove)
                    c.setColor (Color.cyan);
                else
                    c.setColor (Color.magenta);

                c.fillOval (xCoordinate5 [i] + displacement, yCoordinate5 [i] - 10, 20, 20);
                delay (MOVE_DELAY);
            }
        }
    }
}


