package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MyGame extends Game {
    final float virtualWidth = 100, virtualHeight = 50;
    float screenWidth, screenHeight, gameWidth, gameHeight;
    float aspectratio;
    @Override
    public void create() {
        AssetLoader.load();
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        gameWidth = virtualWidth;
        gameHeight = screenHeight / (screenWidth / gameWidth);
        aspectratio = screenHeight/screenWidth;
        setScreen(new MainMenu(this));
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {

        AssetLoader.dispose();
        super.dispose();
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }
}
