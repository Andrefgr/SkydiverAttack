package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HelpScreen implements Screen {
    Viewport viewport;
    private OrthographicCamera cam;
    Stage stage;
    Label label;
    Texture buttonMenu;
    MyActor actorButtonMenu;
    MyGame myGame;
    float auxX, auxY;
    Texture background;
    SpriteBatch batch;

    public HelpScreen(MyGame myGame) {
        this.myGame = myGame;
        batch = new SpriteBatch();
        viewport = new ExtendViewport(myGame.getScreenWidth(), myGame.getScreenHeight());
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        background = new Texture("helpscreen.png");
        buttonMenu = new Texture("ButtonMenu.png");

        actorButtonMenu = new MyActor(buttonMenu, "buttonMenu");
        actorButtonMenu.setSize(350,200);

    }

    @Override
    public void show() {
        actorButtonMenu.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Sprite sprite = new Sprite(new Texture("ButtonMenu2.png"));
                sprite.setPosition(actorButtonMenu.getX(), actorButtonMenu.getY());
                sprite.setSize(350,200);
                actorButtonMenu.sprite = sprite;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                myGame.setScreen(new GameScreen(myGame, level));
//                myGame.setScreen(new LoadingScreen(myGame, numOfLevel));
                AssetLoader.gameOverSound.pause();
                dispose();
                myGame.setScreen(new MainMenu(myGame));
            }
        });
        auxX = myGame.getScreenWidth() / 2 - actorButtonMenu.sprite.getWidth() / 2;
        actorButtonMenu.spritePos(auxX, 0);

        stage.addActor(actorButtonMenu);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, myGame.getScreenWidth(), myGame.getScreenHeight());
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
