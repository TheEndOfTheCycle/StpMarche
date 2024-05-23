package com.test.game.shoots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tate extends Projectile {

    private final float Yspeed = 50;
    private final float OriginalAltitude;
    private final float VariableAltitude = 50;
    private final float MAX;
    private final float MIN;
    private boolean movingUp;

    public Tate(float x, float y, float speed) {
        super(x, y, speed, "Amo/Andrew.png");
        OriginalAltitude = y;
        MAX = OriginalAltitude + VariableAltitude;
        MIN = OriginalAltitude - VariableAltitude;
        movingUp = true;
    }

    @Override
    public void update(float deltaTime) {
        // Déplacement vertical en zigzag
        if (movingUp) {
            y += Yspeed * deltaTime;
            if (y >= MAX) {
                movingUp = false;
            }
        } else {
            y -= Yspeed * deltaTime;
            if (y <= MIN) {
                movingUp = true;
            }
        }

        // Déplacement horizontal vers la gauche
        x -= speed * deltaTime;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, getWidth(), getHeight());
    }
}