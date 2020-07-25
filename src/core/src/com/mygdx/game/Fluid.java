package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Fluid extends Actor {
    protected Vector2 location, dimension;
    protected Sprite sprite;
    private float viscosity, lifetime;
    float lastTime = 0;

    public Fluid(Vector2 loc, Vector2 dim, float viscosity, float lifetime) {
        location = loc.cpy();
        dimension = dim.cpy();
        sprite = new Sprite(AssetLoader.fluidTexture);
        spritePos(location.x, location.y);
        sprite.setSize(dimension.x, dimension.y);
        this.viscosity = 0.02f;
        this.lifetime = lifetime;
    }

    public Vector2 dragForce(Enemy enemy) {
        float area = (float) (Math.pow(enemy.radius, 2) * Math.PI);
//        Vector2 v = enemy.velocity.cpy();
        float speed = (float) Math.sqrt(Math.pow(enemy.velocity.x, 2) + Math.pow(enemy.velocity.y, 2));
//        v.nor();
        float aux = (float)(-0.5 * area * speed* viscosity);
        return enemy.velocity.scl(aux);
    }

    public boolean isInside(Enemy enemy) {
        if (enemy.sprite.getX() >= sprite.getX() && enemy.sprite.getX() <= sprite.getX() +
                dimension.x && enemy.sprite.getY() >= sprite.getY() && enemy.sprite.getY() <=
                sprite.getY() + dimension.y) {
            Gdx.app.log("asdasd", "inside");
            return true;
        }
        return false;
    }

    public void spritePos(float x, float y) {
        sprite.setPosition(x, y);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }


    @Override
    public void act(float delta) {
        lastTime += 1;
        logg(""+lastTime);
        super.act(delta);
        if(lastTime > 360.f){
            remove();
            GameScreen.fluids.remove(this);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }
    public void logg(String s){
        Gdx.app.log("fluid log",s);
    }

}
