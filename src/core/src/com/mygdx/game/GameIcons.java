package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class GameIcons {
    Vector2 loc;
    float mass;
    Texture img;
    //------------------Construtor------------------------------//
    GameIcons(Vector2 loc, float mass, Texture img) {
        this.loc = loc;
        this.mass = mass;
        this.img = img;
    }
}
