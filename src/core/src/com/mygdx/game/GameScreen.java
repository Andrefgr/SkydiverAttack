package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class GameScreen implements Screen {
    public enum State {
        PAUSE, RUN, RESUME, STOPPED
    }

    private OrthographicCamera cam;
    SpriteBatch batch;
    MyGame myGame;
    Texture background, gun, icon1, icon2;
    Viewport viewport;
    Stage stage;
    ArrayList<MyActor> lifes;
    static ArrayList<MyActor> bullets;
    static ArrayList<Enemy> enemys = new ArrayList<Enemy>();
    ArrayList<MyActor> upgrades;
    static ArrayList<Fluid> fluids = new ArrayList<Fluid>();
    static ArrayList<ParticleEffectActor> particles = new ArrayList<ParticleEffectActor>();
    private Air air = new Air();

    private int enemySize;
    long timestampNextEnemy = 0, timestampNextUpgrade = 0;
    static boolean tocou, canReload;
    float elapsedTime = 0, elapsedTimeReload = 0;
    float pointX;
    private Level level;
    private static int score;
    MyActor scoreIcon;
    Label label, labelLevel, labelReload;
    float scoreIconWidth = 200, scoreIconHeight = 200;
    MyActor reload;
    int bulletWidth = 70, bulletHeight = 100, lifeWidth = 85, lifeHeight = 75;
    private State state;
    Hole hole;
    private float WIDTH, HEIGHT;

    public GameScreen(MyGame myGame, Level level) {
        this.myGame = myGame;
        viewport = new FillViewport(myGame.getScreenWidth(), myGame.getScreenHeight());
        viewport.apply();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        WIDTH = stage.getWidth();
        HEIGHT = stage.getHeight();

        state = State.RUN;
        batch = new SpriteBatch();
        scoreIcon = new MyActor(AssetLoader.points, "scoreIcon");
        this.level = level;
        gun = new Texture("gun2.png");
        icon1 = new Texture("12.png");
        lifes = new ArrayList<MyActor>();
        enemySize = 150;
        score = 0;
        pointX = WIDTH / 2;
        bullets = new ArrayList<MyActor>();
        upgrades = new ArrayList<MyActor>();

        tocou = false;

        label = new Label("" + level.getScoreTotal(), AssetLoader.labelStyle);
        label.setSize(WIDTH, 200);
        label.setPosition(scoreIconWidth, 0);
        label.setAlignment(Align.left);
        stage.addActor(label);

        scoreIcon.setSize(200, 200);
        scoreIcon.spritePos(0, 0);
        stage.addActor(scoreIcon);

        loadLifes();
        loadAmmo();

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tocou = true;
                if (bullets.size() == 0) return false;
                if (x >= WIDTH - 100 && y <= 200) ;
                else AssetLoader.music.play();
                if (x >= WIDTH - AssetLoader.gunfIXED.getRegionWidth())
                    pointX = WIDTH - AssetLoader.gunfIXED.getRegionWidth();
                else pointX = x;

//                AssetLoader.music.setLooping(true);
                if (bullets.size() > 0) {
                    bullets.get(bullets.size() - 1).remove();
                    bullets.remove(bullets.size() - 1);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                AssetLoader.music.pause();
                tocou = false;
            }
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        switch (state) {
            case RUN:
                AssetLoader.ambience.play();
                update();
                break;
            case PAUSE:
                AssetLoader.ambience.pause();
                stage.act(Gdx.graphics.getDeltaTime());
                stage.getBatch().begin();
                stage.getBatch().draw(AssetLoader.resumeTexture, WIDTH / 2 - AssetLoader
                        .resumeTexture.getWidth() / 2, HEIGHT / 2 - AssetLoader.resumeTexture
                        .getHeight() / 2);
                stage.getBatch().end();
                stage.draw();
                break;
            case RESUME:
                state = State.RUN;
                break;

            default:
                break;
        }

    }

    public void update() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();

        if (level.getNumberOfLifes() <= 0) {
            myGame.setScreen(new GameOverScreen(myGame));
        }
        if (level.getScoreTotal() >= level.getNextLevel()) {
            level.nextLevel();
            if (AssetLoader.music.isPlaying()) AssetLoader.music.pause();
            logg("" + level.getNumberOfLevel());
            enemys.clear();
            particles.clear();
            myGame.setScreen(new LoadingScreen(myGame, level));
        }

        stage.getBatch().draw(level.getBackground(), 0, 0, myGame.getScreenWidth(), myGame
                .getScreenHeight());
        MyActor pause = new MyActor(AssetLoader.pauseTexture, "pause");
        pause.sprite.setSize(100, 200);
        pause.spritePos(WIDTH - pause.sprite.getWidth(), 0);
        pause.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (state == State.RUN) pause();
                else resume();
                return true;
            }
        });
        stage.addActor(pause);

        if (bullets.size() == 0 && !canReload) {
            canReload = true;
            reload = new MyActor(AssetLoader.reload, "reload");
            reload.spritePos(WIDTH / 2 - reload.sprite.getWidth() / 2, HEIGHT / 2 - reload.sprite
                    .getHeight() / 2);
            stage.addActor(reload);
            reload.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int
                        button) {
                    canReload = false;
                    reload.remove();
                    loadAmmo();
                    return true;

                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                }
            });
        }
        if (TimeUtils.timeSinceMillis(timestampNextEnemy) > level.getNextEnemy()) {
            timestampNextEnemy = TimeUtils.millis();
            final Enemy enemy = new Enemy(new Vector2(getRandomIntInclusive(enemySize, (int)
                    WIDTH - enemySize), HEIGHT), new Vector2(0, 0), 80f, level.speedUp,
                    enemySize, level.getPoints(), level);
            stage.addActor(enemy);
            enemys.add(enemy);
        }

        if (level.getHaveUpgrade() && TimeUtils.timeSinceMillis(timestampNextUpgrade) > level
                .getNextUpdate()) {
            timestampNextUpgrade = TimeUtils.millis();
            final int random = 1;
            final MyActor upgrade = new MyActor(new Texture("upgrade" + random + ".png"),
                    "upgrade");
            upgrade.sprite.setSize(200, 100);
            upgrade.spritePos((int) (Math.random() * WIDTH - upgrade.sprite.getWidth()), HEIGHT);
            upgrade.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int
                        button) {
                    upgrade.remove();
                    upgrades.remove(upgrades);
                    switch (random) {
                        case 1:
                            level.setNumOfAmmo(level.getNumOfMaxAmmo());
                            if (level.getNumberOfLifes() < level.getNumOfMaxLifes())
                                level.setNumberOfLifes(level.getNumberOfLifes() + 1);
                            break;
                        case 2:
                            hole = new Hole(new Vector2(300, 300), 1000);
                            stage.addActor(hole);
                            break;
                        case 3:
                            Fluid fluid = new Fluid(new Vector2(upgrade.sprite.getX(), upgrade
                                    .sprite.getY()), new Vector2(500, 300), 0.02f, 100);
                            fluids.add(fluid);
                            stage.addActor(fluid);
                            break;
                    }
                    return true;
                }
            });
            upgrades.add(upgrade);
            stage.addActor(upgrade);
//            control = true;
        }
        // actualiza todos os upgrades em jogo
        for (int up = upgrades.size() - 1; up >= 0; --up) {
            MyActor upgrade = upgrades.get(up);
            if (upgrade.sprite.getY() < 0) {
                upgrade.remove();
                upgrades.remove(upgrade);
            } else upgrade.move(0, 2);
        }
        // actualiza todos os inimigos em jogo
        for (int en = enemys.size() - 1; en >= 0; --en) {
            Enemy enemy = enemys.get(en);
            // ver se foi atingido
            if (enemy.hit) {
                AssetLoader.music.play();

                ParticleEffectActor particleActor = new ParticleEffectActor(AssetLoader
                        .particleEffect, enemy.sprite.getX() + enemy.sprite.getWidth() / 2, enemy
                        .sprite.getY() + enemy.sprite.getHeight() / 2);

                stage.addActor(particleActor);
                particles.add(particleActor);
                particleActor.start();
                enemys.remove(enemy);
                enemy.removeEnemy();
                if (level.getNumberOfLevel() == level.getLastLevel() && level.getNextLevel() ==
                        level.getScoreTotal()) {
                    myGame.setScreen(new VictoryScreen(myGame, level));
                }
                continue;
            }
            // ver se chegou ao fundo da janela
            if (enemy.sprite.getY() <= 0) {
                logg("remove life");
                level.setNumberOfLifes(level.getNumberOfLifes() - 1);
                lifes.get(lifes.size() - 1).remove();
                lifes.remove(lifes.get(lifes.size() - 1));
                if (lifes.size() == 0) {
                    myGame.setScreen(new GameOverScreen(myGame));
                }
                enemys.remove(en);
                enemy.remove();
                continue;
            }
            if (!enemy.open && enemy.sprite.getY() <= HEIGHT / 2) {
                enemy.openParachute();
//                air.density = 0.5f;
            }
            if (hole != null) {
                Vector2 force = hole.dragForce(enemy);
                if (enemy.location.x >= WIDTH / 2 - hole.dimension.x / 2 && enemy.location.x <=
                        WIDTH / 2 + hole.dimension.x / 2 && enemy.location.y >= HEIGHT / 2 - hole
                        .dimension.y / 2 && enemy.location.y <= HEIGHT / 2 + hole.dimension.y / 2) {
                    enemys.remove(enemy);
                    enemy.removeEnemy();
                    if (level.getNumberOfLevel() == level.getLastLevel() && level.getNextLevel()
                            == level.getScoreTotal()) {
                        myGame.setScreen(new VictoryScreen(myGame, level));
                    }
                }
            } else if (fluids.size() > 0) {
                for (int i = fluids.size() - 1; i >= 0; --i) {
                    Fluid fluid = fluids.get(i);
                    if (fluid.isInside(enemy)) {
                        Vector2 drag = fluid.dragForce(enemy);
                        enemy.applyForce(drag);
                    } else {
                        float weight = enemy.mass * air.g;
                        enemy.applyForce(new Vector2(0, weight));
                        Vector2 drag = air.drag(enemy);
                        enemy.applyForce(drag);
                    }
                }
            } else {
                float weight = enemy.mass * air.g;
                enemy.applyForce(new Vector2(0, weight));
                Vector2 drag = air.drag(enemy);
                enemy.applyForce(drag);
            }

            enemy.move();

        }
        // actualiza todas as particulas em jogo
        for (int en = particles.size() - 1; en >= 0; --en) {
            ParticleEffectActor particleEffectActor = particles.get(en);
            if (particleEffectActor.isComplet()) {
                particleEffectActor.remove();
                particles.remove(particleEffectActor);

            }
        }
        // desenha animação da arma
        if (tocou && bullets.size() > 0) {
            elapsedTime += Gdx.graphics.getDeltaTime();
            stage.getBatch().draw(AssetLoader.gunAnimation.getKeyFrame(elapsedTime, true),
                    pointX, 0, 400, 300);
//        stage.getBatch().draw(gun, WIDTH / 2, 0, 450, 350);

        } else stage.getBatch().draw(AssetLoader.gunfIXED, pointX, 0, 400, 300);
        stage.getBatch().end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        this.state = State.PAUSE;
    }

    @Override
    public void resume() {
        this.state = State.RESUME;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }


    public int getRandomIntInclusive(int min, int max) {
        min = (int) Math.ceil(min);
        max = (int) Math.floor(max);
        return (int) Math.floor(Math.random() * (max - min + 1)) + min; //The maximum is
        // inclusive and the minimum is inclusive
    }

    public void loadAmmo() {
        float alignBulletsX = WIDTH - 20;
        for (int i = 0; i < level.getNumOfAmmo(); ++i) {
            MyActor bullet = new MyActor(new Texture("bullet.png"), "bullet");
            bullet.spriteSize(bulletWidth, bulletHeight);
            bullet.spritePos(alignBulletsX - bullet.sprite.getWidth() / 2 - 10, HEIGHT - bullet
                    .sprite.getHeight());
            bullets.add(bullet);
            stage.addActor(bullet);
            alignBulletsX -= bullet.sprite.getWidth() / 2;
        }
    }

    public void loadLifes() {
        float alignLifesX = 20;
        for (int i = 0; i < level.getNumberOfLifes(); ++i) {
            MyActor loadLife = new MyActor(new Texture("life.png"), "life");
            loadLife.spriteSize(lifeWidth, lifeHeight);
            loadLife.spritePos(alignLifesX, HEIGHT - loadLife.sprite.getHeight() - 10);
            lifes.add(loadLife);
            stage.addActor(loadLife);
            alignLifesX += loadLife.sprite.getWidth() + 10;
        }
    }

    public void logg(String s) {
        Gdx.app.log("game screen log ", s);
    }

}
