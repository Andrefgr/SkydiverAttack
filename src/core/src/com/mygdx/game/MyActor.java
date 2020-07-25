package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class MyActor extends Actor {
    Sprite sprite;

    public MyActor(Texture texture, final String actorName) {
        sprite = new Sprite(texture);
        spritePos(sprite.getX(), sprite.getY());
        setTouchable(Touchable.enabled);

    }

    public void setSize(float width, float height) {
        sprite.setSize(width, height);
    }

    public void spritePos(float x, float y) {
        sprite.setPosition(x, y);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void move(int x1, int y1) {
        sprite.setPosition(sprite.getX() - x1, sprite.getY() - y1);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void spriteSize(float width, float height) {
        sprite.setSize(width, height);
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
