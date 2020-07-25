package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class VictoryScreen implements Screen {
    Viewport viewport;
    Stage stage;
    Label label;
    Texture buttonMenu;
    MyActor actorButtonMenu;
    MyGame myGame;
    float auxX, auxY;
    Texture background;

    public VictoryScreen(MyGame myGame, Level level) {
        this.myGame = myGame;
        viewport = new FillViewport(myGame.getScreenWidth(), myGame.getScreenHeight());
        viewport.apply();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        background = new Texture("vic.png");


        label = new Label("Kills: " + level.getScoreTotal(), AssetLoader.scoresLabelStyle);
        label.setSize(myGame.getScreenWidth(), 200);
        label.setPosition(0, myGame.getScreenHeight() / 2);
        label.setAlignment(Align.center);
        stage.addActor(label);

        buttonMenu = new Texture("ButtonMenu.png");
        actorButtonMenu = new MyActor(buttonMenu, "buttonMenu");


    }

    @Override
    public void show() {
        actorButtonMenu.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Sprite sprite = new Sprite(new Texture("ButtonMenu2.png"));
                sprite.setPosition(actorButtonMenu.getX(), actorButtonMenu.getY());
                actorButtonMenu.sprite = sprite;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                AssetLoader.gameOverSound.pause();
                myGame.setScreen(new MainMenu(myGame));
            }
        });
        auxX = myGame.getScreenWidth() / 2 - actorButtonMenu.sprite.getWidth() / 2;
        auxY = 100;
        actorButtonMenu.spritePos(auxX, auxY);

        stage.addActor(actorButtonMenu);
        AssetLoader.winSound.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0);
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
