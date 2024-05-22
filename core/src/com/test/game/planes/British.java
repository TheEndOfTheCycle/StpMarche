package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.test.game.shoots.Bullets;
import com.test.game.shoots.Projectile;

public class British extends Plane {
    private float speed;
    private static int SCORE_VALUE = 1;
    private float timeSinceLastShot = 0;
    private float shootingInterval = 2.0f; 
    private static final Texture TEXTURE_BRITISH = new Texture(Gdx.files.internal("Planes/british.png"));

    private final float Yspeed = 50; // Vitesse verticale (arbitraire, peut être ajustée)
    private final float OriginalAltitude; // Altitude de départ de l'avion
    private final float VariableAltitude = 50; // Variation d'altitude pour le zigzag
    private final float MAX; // Altitude maximale
    private final float MIN; // Altitude minimale
    private boolean movingUp; // Indicateur de direction

    // Constructeur de la classe
    public British(int x, int y, int height, int width, float speed) {
        super(x, y, height, width, TEXTURE_BRITISH);
        this.speed = speed;
        setHp(1);
        OriginalAltitude = y;
        MAX = OriginalAltitude + VariableAltitude;
        MIN = OriginalAltitude - VariableAltitude;
        movingUp = false; // Initialement, l'avion descend
    }

    // Méthode pour mettre à jour la position de l'objet British
    @Override
    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Déplacement vertical en zigzag
        if (movingUp) {
            setY(getY() + Yspeed * deltaTime);
            if (getY() >= MAX) {
                movingUp = false; // Changer de direction pour descendre
            }
        } else {
            setY(getY() - Yspeed * deltaTime);
            if (getY() <= MIN ) {
                movingUp = true; // Changer de direction pour monter
            }
        }

        // Déplacement horizontal vers la gauche
        if(getSpeed() > 0){
            setX(getX() - getSpeed());
        }else{
            setX(getX() + getSpeed());
        }
    }

    // Overload update method to include ground collision avoidance and shooting
    public void update(float delta, Array<Projectile> projectiles) {
        // Mise à jour du temps depuis le dernier tir
        timeSinceLastShot += delta;
        if (timeSinceLastShot >= shootingInterval) {
            shoot(projectiles);
            timeSinceLastShot = 0;
        }

        // Mise à jour de la position de l'avion en zigzag
        update();

        // Mise à jour des projectiles tirés par l'avion British
        for (Projectile projectile : projectiles) {
            if (projectile instanceof Bullets) {
                ((Bullets) projectile).updateForEnemy(delta);
            }
        }

        // Check and avoid ground collision
        // Vous pouvez ajouter le code pour éviter les collisions avec le sol ici
    }

    // Getters et setters
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public static int getScoreValue() {
        return SCORE_VALUE;
    }

    // Méthode pour tirer des projectiles en ligne droite (vers la gauche)
    public void shoot(Array<Projectile> projectiles) {
        Bullets bullet = new Bullets(getX(), getY(), 200f);
        projectiles.add(bullet);
    }
}
