package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import sun.rmi.runtime.Log;

public class LoadingScreen implements Screen {
    private OrthographicCamera cam;
    private int numberOfLevel;
    private MyGame myGame;
    private Level level;
    private Viewport viewport;
    private Stage stage;
    private int plain1Width = 600, plain1Height = 220, plain2Width = 300, plain2Height = 110;
    private Scrollable scrollable, scrollable2, scrollable3;
    float elapsedTime = 0;
    private Label labelLevel, labelGet;
    private float WIDTH, HEIGHT;

    public LoadingScreen(MyGame myGame, Level level) {
        this.myGame = myGame;
        Gdx.app.log("sad",""+numberOfLevel);
        this.level = level;
        viewport = new ExtendViewport(myGame.getScreenWidth(), myGame.getScreenHeight());
        viewport.apply();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        WIDTH = stage.getWidth();
        HEIGHT = stage.getHeight();


        scrollable = new Scrollable(new Texture("plain.png"), "plain", WIDTH, HEIGHT -
                plain1Height, plain1Width, plain1Height, -200f);
        scrollable2 = new Scrollable(new Texture("plain.png"), "plain", WIDTH, HEIGHT -
                plain2Height - plain2Height * 2, plain2Width, plain2Height, -155f);
        scrollable3 = new Scrollable(new Texture("plain.png"), "plain", WIDTH, HEIGHT -
                plain1Height*2, plain2Width, plain2Height, -125f);
        stage.addActor(scrollable);

        stage.addActor(scrollable2);

        stage.addActor(scrollable3);

        AssetLoader.planeSound.play();

        labelLevel = new Label("LEVEL " + level.getNumberOfLevel(), AssetLoader.loadingLabelStyle);
        labelLevel.setSize(WIDTH, 200);
        labelLevel.setPosition(0, HEIGHT / 2);
        labelLevel.setAlignment(Align.center);

        labelGet = new Label("GET READY ", AssetLoader.readylabelStyle);
        labelGet.setSize(WIDTH, 200);
        labelGet.setPosition(0, HEIGHT / 2 - 300);
        labelGet.setAlignment(Align.center);

        stage.addActor(labelLevel);
        stage.addActor(labelGet);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        scrollable.update(Gdx.graphics.getDeltaTime());
        scrollable2.update(Gdx.graphics.getDeltaTime());
        scrollable3.update(Gdx.graphics.getDeltaTime());

        if (scrollable3.isScrolledLeft) {
            scrollable.remove();
            scrollable2.remove();
            scrollable3.remove();
            AssetLoader.planeSound.pause();
            myGame.setScreen(new GameScreen(myGame, level));
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();
        stage.getBatch().draw(level.getBackground(), 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
