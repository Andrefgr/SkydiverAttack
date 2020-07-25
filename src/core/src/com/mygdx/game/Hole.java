package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Hole extends Actor {
    protected Vector2 location, dimension;
    protected Sprite sprite;
    private float viscosity, lifetime;
    float lastTime = 0;
    float mass = 637500.f, g = 1.f;

    public Hole(Vector2 dim, float lifetime) {
        sprite = new Sprite(AssetLoader.holeTexture);
        dimension = dim;
        spritePos(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        sprite.setSize(dimension.x, dimension.y);
        this.lifetime = lifetime;
    }

    public Vector2 dragForce(Enemy enemy) {
        Vector2 location = new Vector2(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2, Gdx
                .graphics.getHeight() / 2 - sprite.getHeight() / 2);
        Vector2 r = location.sub(enemy.location);
        float mag = (float) Math.sqrt(Math.pow(r.x, 2) + Math.pow(r.y, 2));
        float aux = (float) ((g * mass * enemy.mass) / Math.pow(mag, 2));
        r.nor();
        return r.scl(aux);
    }


    public void spritePos(float x, float y) {
        sprite.setPosition(x, y);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }


    @Override
    public void act(float delta) {
        lifetime += 1;
        logg(""+lifetime);
//        super.act(delta);
//        if (lifetime >= 60f) {
//            remove();
//        }
//

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    public void logg(String s){
        Gdx.app.log("hole log",s);
    }

}
