package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.test.game.GameOverScreen;
import com.test.game.Test;
import com.test.game.shoots.AmungUs;
import com.test.game.shoots.Projectile;
import com.test.game.shoots.Snake;
import com.test.game.shoots.Tate;
import com.test.game.shoots.Trump;

/**
 * Cette classe représente le boss final dans le jeu.
 */
public class BossFinale extends Plane {
    // Textures pour les différents états du boss
    private static final Texture TEXTURE_BOSS1 = new Texture(Gdx.files.internal("Planes/drake.png"));
    private static final Texture TEXTURE_BOSS2 = new Texture(Gdx.files.internal("Planes/Andrew.png"));
    private static final Texture TEXTURE_BOSS3 = new Texture(Gdx.files.internal("Planes/tower.png"));
    // son
    public Sound sonTate = Gdx.audio.newSound(Gdx.files.internal("Music/Tate-theme.mp3"));
    public boolean TatePlaying = false;
    public Sound sonTour = Gdx.audio.newSound(Gdx.files.internal("Music/America.mp3"));
    public boolean TourPlaying = false;
    // Dimensions finales du boss
    public static final int FINAL_WIDTH = 200;
    public static final int FINAL_HEIGHT = Gdx.graphics.getHeight();

    private boolean buff = false; // Indique si le boss a été buffé

    private int hp; // Points de vie du boss
    private float timeSinceLastTrumpShot = 0.0f;
    private float timeSinceLastAmungUsShot = 0.0f;
    private float timeSinceLastTateShot = 0.0f;
    private float totalTime = 0.0f;
    public static final int LIFE = 200;
    // Variable de controle
    Test game;
    // Intervalles de tir pour chaque type de projectile
    private float trumpShootingInterval = 1.0f;
    private float amungUsShootingInterval = 1.5f;
    private float tateShootingInterval = 2.0f;

    /**
     * Construit un objet BossFinale avec les coordonnées spécifiées.
     *
     * @param x La coordonnée x du boss.
     * @param y La coordonnée y du boss.
     */
    public BossFinale(int x, int y, Test game) {
        super(x, y, FINAL_WIDTH, FINAL_HEIGHT, TEXTURE_BOSS1);
        this.hp = LIFE;
        this.game = game;
    }

    /**
     * Tire sur la cible en fonction du temps écoulé et des intervalles de tir.
     *
     * @param target      La cible à viser.
     * @param projectiles La liste des projectiles où ajouter les nouveaux tirs.
     */
    public void shootAt(Plane target, Array<Projectile> projectiles) {
        // Calcule l'angle entre le boss et la cible
        float angle = calculateAngle(getX(), Gdx.graphics.getHeight() / 2, target.getX(), target.getY());

        // Vérifie le temps écoulé et effectue les tirs en conséquence
        if (totalTime < 5.0f) {
            // Tire uniquement si le temps écoulé est inférieur à 5 secondes
            if (timeSinceLastTrumpShot >= trumpShootingInterval) {
                // Vérifie si le type de boss est le premier et ajoute un projectile en
                // conséquence
                if (getTexture() == TEXTURE_BOSS1) {
                    Snake snake = new Snake(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(snake);
                } else {
                    Trump trump = new Trump(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(trump);
                }
                timeSinceLastTrumpShot = 0; // Réinitialise le temps écoulé depuis le dernier tir de Trump
            }
        } else if (totalTime < 15.0f) {
            // Tire également des AmungUs si le temps écoulé est entre 5 et 15 secondes
            if (timeSinceLastTrumpShot >= trumpShootingInterval) {
                // Vérifie si le type de boss est le premier et ajoute un projectile en
                // conséquence
                if (getTexture() == TEXTURE_BOSS1) {
                    Snake snake = new Snake(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(snake);
                } else {
                    Trump trump = new Trump(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(trump);
                }
                timeSinceLastTrumpShot = 0; // Réinitialise le temps écoulé depuis le dernier tir de Trump
            }
            if (timeSinceLastAmungUsShot >= amungUsShootingInterval) {
                // Ajoute deux projectiles AmungUs à des hauteurs différentes
                AmungUs amungUs1 = new AmungUs(getX(), Gdx.graphics.getHeight() / 4, 200f);
                AmungUs amungUs2 = new AmungUs(getX(), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 4), 200f);
                projectiles.add(amungUs1);
                projectiles.add(amungUs2);
                timeSinceLastAmungUsShot = 0; // Réinitialise le temps écoulé depuis le dernier tir d'AmungUs
            }
        } else if (totalTime < 25.0f) {
            // Ajoute également des tirs de Tate si le temps écoulé est entre 15 et 25
            // secondes
            if (timeSinceLastTrumpShot >= trumpShootingInterval) {
                // Vérifie si le type de boss est le premier et ajoute un projectile en
                // conséquence
                if (getTexture() == TEXTURE_BOSS1) {
                    Snake snake = new Snake(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(snake);
                } else {
                    Trump trump = new Trump(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(trump);
                }
                timeSinceLastTrumpShot = 0; // Réinitialise le temps écoulé depuis le dernier tir de Trump
            }
            if (timeSinceLastTateShot >= tateShootingInterval) {
                // Ajoute deux projectiles Tate à des hauteurs différentes
                Tate tate1 = new Tate(getX(), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 4), 200f);
                Tate tate2 = new Tate(getX(), Gdx.graphics.getHeight() / 4, 200f);
                projectiles.add(tate1);
                projectiles.add(tate2);
                timeSinceLastTateShot = 0; // Réinitialise le temps écoulé depuis le dernier tir de Tate
            }
        } else {
            // Ajoute des tirs de Trump, AmungUs et Tate si le temps écoulé est supérieur à
            // 25 secondes
            if (timeSinceLastTrumpShot >= trumpShootingInterval) {
                // Vérifie si le type de boss est le premier et ajoute un projectile en
                // conséquence
                if (getTexture() == TEXTURE_BOSS1) {
                    Snake snake = new Snake(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(snake);
                } else {
                    Trump trump = new Trump(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(trump);
                }
                timeSinceLastTrumpShot = 0; // Réinitialise le temps écoulé depuis le dernier tir de Trump
            }
            if (timeSinceLastAmungUsShot >= amungUsShootingInterval) {
                // Ajoute deux projectiles AmungUs à des hauteurs différentes
                AmungUs amungUs1 = new AmungUs(getX(), Gdx.graphics.getHeight() / 4, 200f);
                AmungUs amungUs2 = new AmungUs(getX(), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 4), 200f);
                projectiles.add(amungUs1);
                projectiles.add(amungUs2);
                timeSinceLastAmungUsShot = 0; // Réinitialise le temps écoulé depuis le dernier tir d'AmungUs
            }
            if (timeSinceLastTateShot >= tateShootingInterval) {
                Tate tate1 = new Tate(getX(), Gdx.graphics.getHeight() -
                        (Gdx.graphics.getHeight() / 4), 200f);
                Tate tate2 = new Tate(getX(), Gdx.graphics.getHeight() / 4, 200f);
                projectiles.add(tate1);
                projectiles.add(tate2);
                timeSinceLastTateShot = 0;
            }
        }
    }

    private float calculateAngle(float startX, float startY, float targetX, float targetY) {
        return (float) Math.atan2(targetY - startY, targetX - startX);
    }

    public void update(float delta, Plane target, Array<Projectile> projectiles) {
        timeSinceLastTrumpShot += delta;
        timeSinceLastAmungUsShot += delta;
        timeSinceLastTateShot += delta;
        totalTime += delta;

        shootAt(target, projectiles);

        // Réinitialise le temps total après 40 secondes
        if (totalTime >= 40.0f) {
            totalTime = 0.0f;
        }

    }

    @Override
    public int getHp() {
        return hp;
    }

    /**
     * Cette méthode permet de définir les points de vie du boss.
     * Si les points de vie descendent en dessous de 25 et que le boss n'a pas
     * encore bénéficié d'un buff,
     * le boss récupère 100 points de vie.
     * 
     * @param hp les points de vie à définir pour le boss
     */
    @Override
    public void setHp(int hp) {
        if (hp < 10 && !buff) {
            this.hp = 100;
            buff = true;
        } else {
            this.hp = hp;
        }

        // Mettre à jour l'image du boss en fonction du niveau de santé
        if (this.hp > 100 && !buff) {
            setTexture(TEXTURE_BOSS1);
        } else if (this.hp <= 100 && this.hp > 10 && !buff) {
            setTexture(TEXTURE_BOSS2);
            if (TatePlaying == false) {
                game.jeuScreen.sonJeu.dispose();
                sonTate.loop();
                TatePlaying = true;
            }
        } else if (buff) {
            if (TourPlaying == false) {
                sonTate.dispose();
                sonTour.loop();
                TatePlaying = false;
                TourPlaying = true;
            }
            setTexture(TEXTURE_BOSS3);
        }
    }

    /**
     * Cette méthode permet de définir l'intervalle de tir pour le boss Trump.
     * 
     * @param interval l'intervalle de tir à définir pour le boss Trump
     */
    public void setTrumpShootingInterval(float interval) {
        this.trumpShootingInterval = interval;
    }

    /**
     * Cette méthode permet de définir l'intervalle de tir pour le boss AmungUs.
     * 
     * @param interval l'intervalle de tir à définir pour le boss AmungUs
     */
    public void setAmungUsShootingInterval(float interval) {
        this.amungUsShootingInterval = interval;
    }

    /**
     * Cette méthode permet de définir l'intervalle de tir pour le boss Tate.
     * 
     * @param interval l'intervalle de tir à définir pour le boss Tate
     */
    public void setTateShootingInterval(float interval) {
        this.tateShootingInterval = interval;
    }

    /**
     * Cette méthode vérifie si le projectile donné entre en collision avec le boss.
     * 
     * @param projectile le projectile à vérifier
     * @return true si le projectile entre en collision avec le boss, sinon false
     */
    public boolean collidesWithProj(Projectile projectile) {
        return (getX() + getWidth() >= projectile.getX()) && (getX() <= (projectile.getX() + projectile.getWidth()))
                && (getY() + getHeight() >= projectile.getY())
                && (getY() <= (projectile.getY() + projectile.getHeight()));
    }
}
