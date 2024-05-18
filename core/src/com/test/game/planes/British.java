package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class British extends Plane {
    private final float Yspeed, Xspeed;
    private final float OriginalAltitude; // Altitude de début de l'avion
    private final float VariableAltitude; // Variation d'altitude pour le zigzag
    private final float MAX; // Altitude maximale
    private final float MIN; // Altitude minimale
    private boolean movingUp; // Indicateur de direction

    private static final Texture TEXTURE_BRITISH = new Texture(Gdx.files.internal("Planes/british.png"));

    public British(int x, int y, int height, int width, float Xvitesse, float Yvitesse) {
        super(x, y, height, width, TEXTURE_BRITISH);
        Xspeed = Xvitesse;
        Yspeed = Yvitesse;
        OriginalAltitude = y;
        VariableAltitude = 100;
        MAX = OriginalAltitude + VariableAltitude;
        MIN = OriginalAltitude - VariableAltitude;
        movingUp = false; // Initialement, l'avion descend
    }

    @Override
    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Déplacement vertical en zigzag
        if (movingUp) {
            setY(getY() +  Yspeed * deltaTime );
            if (getY() >= MAX) {
                movingUp = false; // Changer de direction pour descendre
            }
        } else {
            setY(getY() -  Yspeed * deltaTime );
            if (getY() <= MIN) {
                movingUp = true; // Changer de direction pour monter
            }
        }

        // Déplacement horizontal
        setX(getX() +  Xspeed * deltaTime );
    }
}
