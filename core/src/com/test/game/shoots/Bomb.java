package com.test.game.shoots;

public class Bomb extends Projectile {
    public static final float WIDTH = 20; // Largeur de la bombe
    public static final float HEIGHT = 36; // Hauteur de la bombe

    public Bomb(float x, float y, float speed) {
        super(x, y, speed, "Amo/bomb.png");  // Assumant que vous avez une texture différente pour les bombes
    }

    @Override
    public void update(float delta) {
        y -= speed * delta;
        x -= 100f * delta;
    }

    public static float getWidth() {
        return WIDTH;
    }

    public static float getHeight() {
        return HEIGHT;
    }
}
