package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Air {
    // guardar densidade do ar
    float density = 1.29f;
    float g = -9.8f;

    // calcular força de arrasto
    Vector2 drag(Enemy e) {
        float mag = (float) Math.sqrt(Math.pow(e.velocity.x, 2) + Math.pow(e.velocity.y, 2));
        float aux = -0.5f * density * e.area * mag;
        Vector2 vector = e.velocity.scl(aux);
        return vector;


    }
}
