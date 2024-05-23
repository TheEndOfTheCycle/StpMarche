package com.test.game.buffs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * La classe Heart représente un buff spécifique sous la forme d'un cœur,
 * généralement utilisé pour augmenter la santé du joueur.
 */
public class Heart extends Buff {
    
    /**
     * Construit un nouveau Heart avec la position initiale spécifiée.
     * Charge également la texture du cœur depuis le fichier interne.
     *
     * @param Xlevel la coordonnée x initiale du cœur
     * @param Ylevel la coordonnée y initiale du cœur
     */
    public Heart(int Xlevel, int Ylevel) {
        super(Xlevel, Ylevel);
        texture = new Texture(Gdx.files.internal("Ui/health_shape.png"));
    }
}
