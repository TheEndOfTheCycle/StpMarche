package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Red extends Plane {

    private final static int RED_WIDTH = 78;
    private final static int RED_HEIGHT = 30;
    private final static Texture TEXTURE_RED = new Texture(Gdx.files.internal("Planes/red_baron.png"));

    // Constructeur de la classe
    public Red(int x, int y) {
        super(x, y, RED_WIDTH, RED_HEIGHT, TEXTURE_RED);
        setHp(3);
    }

    public void update() {
        // Mise à jour de la position
        setX(Gdx.input.getX() - (getWidth() / 2));
        setY((Gdx.graphics.getHeight() - Gdx.input.getY()) - (getHeight() / 2));

        // Pour ne pas sortir de l'écran sur l'axe des x
        if (Gdx.graphics.getWidth() < Gdx.input.getX() + (getWidth() / 2)) {
            setX(Gdx.graphics.getWidth() - getWidth());
        }
        if (Gdx.input.getX() - (getWidth() / 2) < 0) {
            setX(0);
        }
        // Pour ne pas sortir de l'écran sur l'axe des y
        if ((Gdx.graphics.getHeight() - Gdx.input.getY()) < getHeight() / 2) {
            setY(0);
        }
        if ((Gdx.graphics.getHeight() - Gdx.input.getY()) + (getHeight() / 2) > Gdx.graphics.getHeight()) {
            setY(Gdx.graphics.getHeight() - getHeight());
        }
    }
}
