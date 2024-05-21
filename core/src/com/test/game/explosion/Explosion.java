package com.test.game.explosion;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
    private float x, y;
    private float elapsedTime;
    private Animation<TextureRegion> animation;
    private boolean finished;
    private float mapScrollOffset; // Décalage de défilement de la carte

    public Explosion(float x, float y, Animation<TextureRegion> animation) {
        this.x = x;
        this.y = y;
        this.animation = animation;
        this.elapsedTime = 0f;
        this.finished = false;
    }

    public void update(float delta) {
        x -= 100f * delta;
        elapsedTime += delta;
        if (animation.isAnimationFinished(elapsedTime)) {
            finished = true;
        }
    }
    

    public void draw(SpriteBatch batch) {
        if (!finished) {
            // Ajouter le décalage de défilement à la position X de l'explosion
            TextureRegion currentFrame = animation.getKeyFrame(elapsedTime, false);
            batch.draw(currentFrame, x, y, 50, 30);
        }
    }

    public boolean isFinished() {
        return finished;
    }
    
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
