package com.test.game.shoots;

public class Bomb extends Projectile {
    public Bomb(float x, float y, float speed) {
        super(x, y, speed, "Amo/bomb.png");  // Assuming you have a different texture for bombs
    }
    public void update(float delta) {
        y -= speed * delta;
    }
}
