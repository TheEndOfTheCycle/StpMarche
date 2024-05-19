package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class French extends Plane {

    float speed;
    private static int SCORE_VALUE = 1;
    private final static Texture TEXTURE_FRENCH = new Texture(Gdx.files.internal("Planes/french.png"));

    // Constructeur de la classe
    public French(int x, int y, int height, int width, float speed) {
        super(x, y, height, width, TEXTURE_FRENCH);
        setSpeed(speed);
        setHp(1);
    }

    // Méthode pour mettre à jour la position de l'objet French
    @Override
    public void update() {
        setX(getX() - getSpeed());
    }

    // getters et setters
    private float getSpeed() {
        return speed;
    }

    private void setSpeed(float speed) {
        this.speed = speed;
    }

    public static int getScoreValue() {
        return SCORE_VALUE;
    }
}
