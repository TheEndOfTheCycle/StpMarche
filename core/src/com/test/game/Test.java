// Test.java
package com.test.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Test extends Game {

    SpriteBatch batch;
    public MenuScreen menu;
    public GameScreen jeuScreen;
    public GameOverScreen overScreen;
    private final Array<Screen> screens = new Array<>();
    BufferedWriter writer;

    public void addScreen(Screen screen) {
        this.screens.add(screen);
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        MenuScreen mm = new MenuScreen(this);
        this.menu = mm;
        addScreen(mm);
        setScreen(mm);
        mm.sonMenu.loop();
        try {
            writer = new BufferedWriter(new FileWriter("Score/score.txt"));
        } catch (IOException e) {

        }
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
