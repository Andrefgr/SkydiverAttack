package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu implements Screen {
    Texture background, buttonPlay, buttonOptions, buttonHelp, buttonExit;
    Stage stage;
    Viewport viewport;
    MyActor actorButtonPlay, actorButtonOptions, actorButtonHelp, actorButtonExit;
    private float auxX, auxY;
    MyGame myGame;
    Label label, label2;


    public MainMenu(MyGame myGame) {
        this.myGame = myGame;
        background = new Texture("back.png");
        viewport = new ExtendViewport(myGame.getScreenWidth(), myGame.getScreenHeight());
        viewport.apply();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);


        label = new Label("SKYDIVER", AssetLoader.menulabelStyle);
        label.setSize(stage.getWidth(), 200);
        label.setPosition(0, myGame.getScreenHeight() - 200);
        label.setAlignment(Align.center);
        stage.addActor(label);

        label2 = new Label("ATTACK", AssetLoader.menulabelStyle);
        label2.setSize(stage.getWidth(), 200);
        label2.setPosition(0, myGame.getScreenHeight() - 400);
        label2.setAlignment(Align.center);
        stage.addActor(label2);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, myGame.getScreenWidth(),myGame.getScreenHeight());
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


    @Override
    public void show() {

        buttonPlay = new Texture("ButtonPlay.png");
        actorButtonPlay = new MyActor(buttonPlay, "buttonPlay");
        auxX = stage.getWidth() / 2 - actorButtonPlay.sprite.getWidth() / 2;
        auxY = stage.getHeight() / 2 - actorButtonPlay.sprite.getHeight() / 2;
        actorButtonPlay.spritePos(auxX, auxY);
        actorButtonPlay.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AssetLoader.introSound.pause();
                Sprite sprite = new Sprite(new Texture("ButtonPlay2.png"));
                sprite.setPosition(actorButtonPlay.getX(), actorButtonPlay.getY());
                actorButtonPlay.sprite = sprite;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Level level = new Level();
//                myGame.setScreen(new VictoryScreen(myGame, level));
//                myGame.setScreen(new GameScreen(myGame, level));
                myGame.setScreen(new LoadingScreen(myGame, level));
//                myGame.setScreen(new GameOverScreen(myGame));
            }
        });


        auxY -= actorButtonPlay.sprite.getHeight() / 2;

        buttonOptions = new Texture("ButtonOptions.png");
        actorButtonOptions = new MyActor(buttonOptions, "buttonOptions");
        actorButtonOptions.spritePos(auxX, auxY);
        actorButtonOptions.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Sprite sprite = new Sprite(new Texture("ButtonOptions2.png"));
                sprite.setPosition(actorButtonOptions.getX(), actorButtonOptions.getY());
                actorButtonOptions.sprite = sprite;
                AssetLoader.clickSound.play();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        auxY -= actorButtonPlay.sprite.getHeight() / 2;

        buttonHelp = new Texture("ButtonHelp.png");
        actorButtonHelp = new MyActor(buttonHelp, "buttonHelp");
        actorButtonHelp.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Sprite sprite = new Sprite(new Texture("ButtonHelp2.png"));
                sprite.setPosition(actorButtonHelp.getX(), actorButtonHelp.getY());
                actorButtonHelp.sprite = sprite;
                AssetLoader.clickSound.play();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                myGame.setScreen(new HelpScreen(myGame));
            }
        });
        actorButtonHelp.spritePos(auxX, auxY);
        auxY -= actorButtonPlay.getHeight() / 2;

        buttonExit = new Texture("ButtonExit.png");
        actorButtonExit = new MyActor(buttonExit, "buttonExit");
        actorButtonExit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AssetLoader.clickSound.play();
                Sprite sprite = new Sprite(new Texture("ButtonExit2.png"));
                sprite.setPosition(actorButtonExit.getX(), actorButtonExit.getY());
                actorButtonExit.sprite = sprite;
                Gdx.app.exit();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        actorButtonExit.spritePos(auxX, auxY);
        auxY -= actorButtonPlay.sprite.getHeight() / 2;

        stage.addActor(actorButtonPlay);
        stage.addActor(actorButtonOptions);
        stage.addActor(actorButtonHelp);
        stage.addActor(actorButtonExit);

        AssetLoader.introSound.play();
    }

}
