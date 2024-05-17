// Test.java
package com.test.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Test extends Game {
    SpriteBatch batch;
    

    private final Array<Screen> screens = new Array<>();

    public void addScreen(Screen screen) {
        this.screens.add(screen);
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        Screen mm = new MenuScreen(this);
        addScreen(mm);
        setScreen(mm);
    }

    @Override
    public void render() {
        super.render();
    }
    

    @Override
    public void dispose() {
        batch.dispose();
        for (Screen s : screens) {
            s.dispose();
        }
    }
}
