package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Level {
    private Texture background;
    private int numOfAmmo;
    private int nextEnemy, nextLevel;
    private int points;
    private int scoreTotal;
    private int numberOfLevel;
    private int numberOfLifes, nextUpdate;
    private boolean gameOver, haveUpgrade;
    float speedUp;
    private int numOfMaxAmmo, numOfMaxLifes;
    private int lastLevel;
    public Level() {
        scoreTotal = 0;
        background = new Texture("field1.png");
        numOfAmmo = 8;
        nextEnemy = 2000;
        points = 1;
        numberOfLifes = 4;
        nextLevel = 6;
        numberOfLevel = 1;
        gameOver = false;
        speedUp =30f;
        haveUpgrade = false;
        numOfMaxAmmo = 10;
        lastLevel = 6;
        numOfMaxLifes = 5;
    }

    public void nextLevel() {
        ++numberOfLevel;
        switch (numberOfLevel) {
            case 2:
                background = new Texture("field2.png");
                nextEnemy = 1000;
                points = 1;
                nextLevel = 16;
                nextUpdate = 2000;
                speedUp = 35f;
                break;
            case 3:
                background = new Texture("field3.png");
                nextEnemy = 800;
                points = 1;
                nextLevel = 32;
                haveUpgrade = true;
                nextUpdate = 4000;
                speedUp = 35f;
                break;
            case 4:
                background = new Texture("field4.png");
                nextEnemy = 700;
                nextLevel = 48;
                nextUpdate = 4000;
                speedUp = 35f;
                break;
            case 5:
                background = new Texture("field5.png");
                nextEnemy = 600;
                nextLevel = 60;
                nextUpdate = 4000;
                speedUp = 35f;
                points = 1;
                break;
            case 6:
                background = new Texture("field7.png");
                nextEnemy = 500;
                nextLevel = 75;
                nextUpdate = 4000;
                speedUp = 35f;
                break;

        }
    }

    public int getPoints() {
        return points;
    }

    public int getNumOfAmmo() {
        return numOfAmmo;
    }

    public int getNextEnemy() {
        return nextEnemy;
    }

    public Texture getBackground() {
        return background;
    }

    public int getNumberOfLevel() {
        return numberOfLevel;
    }

    public int getNumberOfLifes() {
        return numberOfLifes;
    }

    public int getNextLevel() {
        return nextLevel;
    }

    public void addPoints(int points) {
        scoreTotal += points;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public void setNumberOfLifes(int numberOfLifes) {
        this.numberOfLifes = numberOfLifes;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public int getNextUpdate() {
        return nextUpdate;
    }

    public boolean getHaveUpgrade() {
        return haveUpgrade;
    }

    public void setNumOfAmmo(int numOfAmmo) {
        this.numOfAmmo = numOfAmmo;
    }

    public int getNumOfMaxAmmo() {
        return numOfMaxAmmo;
    }

    public int getLastLevel() {
        return lastLevel;
    }

    public int getNumOfMaxLifes() {
        return numOfMaxLifes;
    }
}
