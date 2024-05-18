package com.test.game.shoots;

public class Bullets extends Projectile {
    public Bullets(float x, float y, float speed) {
        super(x, y, speed, "Amo/bullets.png");
    }
    
    @Override
    public void update(float delta) {
        x += speed * delta;
    }
}
