package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Scrollable extends Actor {

    protected Vector2 position;
    protected Vector2 velocity;
    protected boolean isScrolledLeft;
    protected Sprite sprite;


    public Scrollable(Texture texture, final String actorName, float x, float y, int width, int
            height, float scrollSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(scrollSpeed, 0);
        sprite = new Sprite(texture);
        spritePos(position.x, position.y);
        isScrolledLeft = false;
        sprite.setSize(width, height);
    }

    public void spritePos(float x, float y) {
        sprite.setPosition(x, y);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
        spritePos(position.x, position.y);
// If the Scrollable object is no longer visible:
        if (position.x + sprite.getWidth() < 0) {
            isScrolledLeft = true;
        }
    }

    public void reset(float newX) {
        position.x = newX;
        isScrolledLeft = false;
    }

    public void stop() {
        velocity.x = 0;
    }

    // Getters for instance variables
    public boolean isScrolledLeft() {
        return isScrolledLeft;
    }


    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

}
