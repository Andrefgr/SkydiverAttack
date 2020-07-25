package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Upgrades {
    // Variaveis
    Vector2 location;
    int rand, size;

    //------------------Construtor-----------------------------//
    public Upgrades(Vector2 location, int size) {
        this.location= location;
        this.size = size;
        this.rand = (int)Math.random()*4+1;
    }
    //----------------Move o bonus----------------------------//
    public void move() {
        location.y += 2;
    }
}
