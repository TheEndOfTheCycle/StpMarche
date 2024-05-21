package com.test.game.buffs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Heart extends Buff {
    public Heart(int Xlevel, int Ylevel) {
        super(Xlevel, Ylevel);
        texture = new Texture(Gdx.files.internal("Ui/health_shape.png"));

    }
}
