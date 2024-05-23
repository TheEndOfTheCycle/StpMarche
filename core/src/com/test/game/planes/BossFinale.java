package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.test.game.shoots.AmungUs;
import com.test.game.shoots.Projectile;
import com.test.game.shoots.Snake;
import com.test.game.shoots.Tate;
import com.test.game.shoots.Trump;

public class BossFinale extends Plane {
    private static final Texture TEXTURE_BOSS1 = new Texture(Gdx.files.internal("Planes/drake.png"));
    private static final Texture TEXTURE_BOSS2 = new Texture(Gdx.files.internal("Planes/Andrew.png"));
    private static final Texture TEXTURE_BOSS3 = new Texture(Gdx.files.internal("Planes/tower.png"));

    public static final int FINAL_WIDTH = 200;
    public static final int FINAL_HEIGHT = Gdx.graphics.getHeight();
    private boolean buff = false;

    private int hp;
    private float timeSinceLastTrumpShot = 0.0f;
    private float timeSinceLastAmungUsShot = 0.0f;
    private float timeSinceLastTateShot = 0.0f;
    private float totalTime = 0.0f;

    // Intervalles de tir
    private float trumpShootingInterval = 1.0f;
    private float amungUsShootingInterval = 1.5f;
    private float tateShootingInterval = 2.0f;

    public BossFinale(int x, int y) {
        super(x, y, FINAL_WIDTH, FINAL_HEIGHT, TEXTURE_BOSS1);
        setHp(101);
    }

    public void shootAt(Plane target, Array<Projectile> projectiles) {
        float angle = calculateAngle(getX(), Gdx.graphics.getHeight() / 2, target.getX(), target.getY());

        if (totalTime < 5.0f) {
            if (timeSinceLastTrumpShot >= trumpShootingInterval) {
                if (getTexture() == TEXTURE_BOSS1) {
                    Snake snake = new Snake(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(snake);
                } else {
                    Trump trump = new Trump(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(trump);
                }
                timeSinceLastTrumpShot = 0;
            }
        } else if (totalTime < 15.0f) {
            if (timeSinceLastTrumpShot >= trumpShootingInterval) {
                if (getTexture() == TEXTURE_BOSS1) {
                    Snake snake = new Snake(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(snake);
                } else {
                    Trump trump = new Trump(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(trump);
                }
                timeSinceLastTrumpShot = 0;
            }
            if (timeSinceLastAmungUsShot >= amungUsShootingInterval) {
                AmungUs amungUs1 = new AmungUs(getX(), Gdx.graphics.getHeight() / 4, 200f);
                AmungUs amungUs2 = new AmungUs(getX(), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 4), 200f);
                projectiles.add(amungUs1);
                projectiles.add(amungUs2);
                timeSinceLastAmungUsShot = 0;
            }
        } else if (totalTime < 25.0f) {
            if (timeSinceLastTrumpShot >= trumpShootingInterval) {
                if (getTexture() == TEXTURE_BOSS1) {
                    Snake snake = new Snake(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(snake);
                } else {
                    Trump trump = new Trump(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(trump);
                }
                timeSinceLastTrumpShot = 0;
            }
            if (timeSinceLastTateShot >= tateShootingInterval) {
                Tate tate1 = new Tate(getX(), Gdx.graphics.getHeight() -
                        (Gdx.graphics.getHeight() / 4), 200f);
                Tate tate2 = new Tate(getX(), Gdx.graphics.getHeight() / 4, 200f);
                projectiles.add(tate1);
                projectiles.add(tate2);
                timeSinceLastTateShot = 0;
            }
        } else {
            if (timeSinceLastTrumpShot >= trumpShootingInterval) {
                if (getTexture() == TEXTURE_BOSS1) {
                    Snake snake = new Snake(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(snake);
                } else {
                    Trump trump = new Trump(getX(), Gdx.graphics.getHeight() / 2, 200f, angle);
                    projectiles.add(trump);
                }
                timeSinceLastTrumpShot = 0;
            }
            if (timeSinceLastAmungUsShot >= amungUsShootingInterval) {
                AmungUs amungUs1 = new AmungUs(getX(), Gdx.graphics.getHeight() / 4, 200f);
                AmungUs amungUs2 = new AmungUs(getX(), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 4), 200f);
                projectiles.add(amungUs1);
                projectiles.add(amungUs2);
                timeSinceLastAmungUsShot = 0;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        // Si les points de vie descendent en dessous de 25, le boss reprend 100 points
        // de vie
        if (hp < 10 && buff == false) {
            this.hp = 100; // Reprendre 100 points de vie sans dépasser 200
            buff = true;

        } else {
            this.hp = hp;
        }
        // Mettre à jour l'image du boss en fonction du niveau de santé
        if (this.hp > 100 && buff == false) {
            setTexture(TEXTURE_BOSS1);
        } else if (this.hp <= 100 && this.hp > 10 && buff == false) {
            setTexture(TEXTURE_BOSS2);
        } else if (buff == true) {
            setTexture(TEXTURE_BOSS3);
        }
    }

    public void setTrumpShootingInterval(float interval) {
        this.trumpShootingInterval = interval;
    }

    public void setAmungUsShootingInterval(float interval) {
        this.amungUsShootingInterval = interval;
    }

    public void setTateShootingInterval(float interval) {
        this.tateShootingInterval = interval;
    }

    public boolean collidesWithProj(Projectile projectile) {

        return (getX() + getWidth() >= projectile.getX()) && (getX() <= (projectile.getX() + projectile.getWidth()))
                && (getY() + getHeight() >= projectile.getY())
                && (getY() <= (projectile.getY() + projectile.getHeight()));

    }

}