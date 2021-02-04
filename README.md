# Jungle Escape Game

This game is based off the board game, snakes and ladders.     There are two levels and if the user passes the first level he or she can move onto the next level.     The user will verse the computer.     User's game piece will be a light blue (cyan) circle while, a computer's game piece is magenta.      There will be a randomly generated dice which will give the number of tiles to move.     The first roll of the user and computer will determind who can start the game. The winner will be determined by who rolls the higher number.     If the computer or user rolls 6, they will be asked to roll again. The sum of all the rolls will give how much the computer or user will move.     The user or computer will go until 25. The winner will rech 25 or a number higher than 25.  If computer wins level one will be played again or user will have the option to exit the game.
        
        Methods:
        instructionPanel: Display game instructions before start the game
        getUserName: Get player name and return string value of name of the player
        getGameLevel: Ask the player to choose the game level and return game level#
        gameBoard: Setup Snake and Ladder Game Board
        delay: Setup delay time to wait for sometime between operations
        setGameBoardNumbers: Draws the game board numerical numbers and doesn't return any values
        drawLaddersAndSnakes: Draws ladders and snakes on the game board and doesn't return any values
        drawSnake: Draws snakes by drawing multiple arcs on the game board and doesn't return any values
        movePosition: Animating computer and user icon move and doesn't return any values
        userStartGame: Determine whether or not user can start the game and return true if the user start the game
        winnerDisplay: Display the winner display mesage and doesn't return any values
        rollDice: Draws dice face value and doesn't return any values
        playGame: Continuously plays the game until computer or user wins the game and doesn't return any values

    Author: Effiea Ponniah

    Date Created: December 21st 2019

    Date Last Modified: January 16th 2019
