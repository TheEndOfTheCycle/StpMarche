package com.test.game.planes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.game.shoots.Projectile;
import com.badlogic.gdx.utils.Array;
import com.test.game.shoots.Trump;
import com.test.game.shoots.AmungUs;
import com.test.game.shoots.Tate;
import java.util.Random;


public class BossFinale extends Plane {
    private static final Texture TEXTURE_BOSS = new Texture(Gdx.files.internal("Planes/Andrew.png"));
    public static final int FINAL_WIDTH = 200;
    public static final int FINAL_HEIGHT = Gdx.graphics.getHeight();
    private final float shootingInterval = 1.0f; // Intervalle de tir en secondes
    private float timeSinceLastShot = 0.0f;
    private float totalTime = 0.0f; // Nouveau compteur de temps

    public BossFinale(int x, int y) {
        super(x, y, FINAL_WIDTH, FINAL_HEIGHT, TEXTURE_BOSS);
    }

    public void shootAt(Plane target, Array<Projectile> projectiles) {
        float angle = calculateAngle(getX(), Gdx.graphics.getHeight() / 2 , target.getX(), target.getY());

        // Tire un Trump pendant les 5 premières secondes
        if (totalTime < 5.0f) {
            Trump trump = new Trump(getX(), Gdx.graphics.getHeight() / 2 , 400f, angle);
            projectiles.add(trump);
        }
        // Tire un Trump et deux AmungUs pendant les 10 secondes suivantes
        else if (totalTime < 15.0f) {
            Trump trump = new Trump(getX(), Gdx.graphics.getHeight() / 2 , 400f, angle);
            AmungUs amungUs1 = new AmungUs(getX(), Gdx.graphics.getHeight() / 4 , 200f);
            AmungUs amungUs2 = new AmungUs(getX(), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 4) , 200f);
            projectiles.add(trump);
            projectiles.add(amungUs1);
            projectiles.add(amungUs2);
        }
        // Tire un Trump et deux Tate pendant les 10 secondes suivantes
        else if (totalTime < 25.0f) {
            Trump trump = new Trump(getX(), Gdx.graphics.getHeight() / 2 , 400f, angle);
            Tate tate1 = new Tate(getX(), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 4) , 200f);
            Tate tate2 = new Tate(getX(), Gdx.graphics.getHeight() / 4 , 200f);
            projectiles.add(trump);
            projectiles.add(tate1);
            projectiles.add(tate2);
        }
        // Tire tous les projectiles après 25 secondes
        else {
            Trump trump = new Trump(getX(), Gdx.graphics.getHeight() / 2 , 400f, angle);
            AmungUs amungUs1 = new AmungUs(getX(), Gdx.graphics.getHeight() / 4 , 200f);
            AmungUs amungUs2 = new AmungUs(getX(), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 4) , 200f);
            Tate tate1 = new Tate(getX(), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 4) , 200f);
            Tate tate2 = new Tate(getX(), Gdx.graphics.getHeight() / 4 , 200f);
            projectiles.add(trump);
            projectiles.add(amungUs1);
            projectiles.add(amungUs2);
            projectiles.add(tate1);
            projectiles.add(tate2);
        }
    }

    private float calculateAngle(float startX, float startY, float targetX, float targetY) {
        return (float) Math.atan2(targetY - startY, targetX - startX);
    }

    public void update(float delta, Plane target, Array<Projectile> projectiles) {
        timeSinceLastShot += delta;
        totalTime += delta; // Incrémente le temps total écoulé

        if (timeSinceLastShot >= shootingInterval) {
            shootAt(target, projectiles);
            timeSinceLastShot = 0;
        }

        // Réinitialise le temps total après 30 secondes
        if (totalTime >= 40.0f) {
            totalTime = 0.0f;
        }
    }
}