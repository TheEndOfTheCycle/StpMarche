package com.test.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.test.game.planes.Plane;
import com.test.game.shoots.Projectile;
import com.test.game.shoots.SolAir;


public class AntiAir extends MapObjects  {
    private float shootingInterval = 1.0f; // Intervalle de tir en secondes
    private float timeSinceLastShot = 0.0f;
    public final static int RED_WIDTH = 100;
    private final static int RED_HEIGHT = 76;
    private final static Texture TEXTURE_RED = new Texture(Gdx.files.internal("BackGroundImages/anti.png"));
    
     // Constructeur de la classe
     public AntiAir(int x, int y) {
        super(x, y,  RED_WIDTH, RED_HEIGHT, TEXTURE_RED);
    }

    // Méthode pour tirer vers l'avion
   // Méthode pour tirer vers l'avion
public void shootAt(Plane target, Array<Projectile> projectiles) {
    float angle = calculateAngle(getX(), getY(), target.getX(), target.getY());
    SolAir bullet = new SolAir(getX(), getY(), 400f, angle);
    projectiles.add(bullet);
}


    private float calculateAngle(float startX, float startY, float targetX, float targetY) {
        return (float) Math.atan2(targetY - startY, targetX - startX);
    }   

    public void update(float delta, Plane target, Array<Projectile> projectiles) {
        timeSinceLastShot += delta;
        if (timeSinceLastShot >= shootingInterval) {
            shootAt(target, projectiles);
            timeSinceLastShot = 0;
        }
    }
}