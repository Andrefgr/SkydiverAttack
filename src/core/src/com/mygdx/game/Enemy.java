package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import org.omg.CORBA.TRANSACTION_UNAVAILABLE;

import java.util.logging.Handler;

public class Enemy extends Actor {
    // Variaveis
    Vector2 location, velocity, acceleration;
    float mass, radius, speedUp;
    int size, aux;
    public Texture img = new Texture("p3.png");
    Sprite sprite;
    boolean isKill = false;
    boolean down = false;
    Level level;
    boolean open, hit;
    float area;
    int points;

    //------------------Construtor-----------------------------//
    public Enemy(Vector2 location, Vector2 velocity, float mass, float speedUp, int size, final
    int points, Level levelAux) {
        sprite = new Sprite(img);
        this.location = location;
        this.velocity = velocity;
        acceleration = new Vector2();
        this.mass = mass;
        this.speedUp = speedUp;
        radius = 0.4f;
        area = (float) (Math.pow(radius, 2) * Math.PI);
        sprite.setSize(size, size);
        this.open = false;
        setTouchable(Touchable.enabled);
        spritePos(location.x, location.y);
        this.level = levelAux;
        hit = false;
        this.points = points;

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AssetLoader.music.play();
                if (GameScreen.bullets.size() > 0) {
                    hit = true;

                    remove();
                    GameScreen.enemys.remove(this);
                }
                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                AssetLoader.music.pause();
            }
        });


    }

    public void removeEnemy() {
        this.remove();
        level.addPoints(getPoints());

    }

    //----------------Aplicar força ao paraquedista-----------------//
    void applyForce(Vector2 f) {
        acceleration.add(new Vector2(f.x / mass, f.y / mass));
    }

    //----------------Move o paraquedista----------------------------//
    void move() {
//        float delta = Gdx.graphics.getDeltaTime() * speedUp;
        float delta = Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f) * speedUp;
        velocity.add(acceleration.scl(delta));
        location.add(velocity.scl(delta));
        spritePos(location.x, location.y);
        acceleration.scl(0);
    }

    public void spritePos(float x, float y) {
        sprite.setPosition(x, y);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void openParachute() {
        sprite.setTexture(AssetLoader.enemy2Texture);
        open = true;
        sprite.setSize(300, 300);
    }

    public void setArea(float rad) {
        radius = rad;
        area = (float) (Math.pow(rad, 2) * Math.PI);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(location.x, location.y);
        sprite.draw(batch);
    }

    public int getPoints() {
        return points;
    }
}
