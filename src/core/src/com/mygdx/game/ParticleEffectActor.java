package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {
    ParticleEffect particleEffect;
    Vector2 acc;
    int rep, repMax;

    public ParticleEffectActor(ParticleEffect particleEffect, float x, float y) {
//        super();
        this.particleEffect = particleEffect;
        acc = new Vector2(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        particleEffect.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        localToStageCoordinates(acc);
        particleEffect.setPosition(acc.x, acc.y);
        particleEffect.update(delta);
    }


    public void start() {
        particleEffect.start();
    }

    public boolean isComplet() {
        return particleEffect.isComplete();
    }

    public void allowCompletion() {
        particleEffect.allowCompletion();
    }

}