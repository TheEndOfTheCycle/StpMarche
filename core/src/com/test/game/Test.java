// Test.java
package com.test.game;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Test extends Game {
    Sound sonJeu;
    SpriteBatch batch;
    public MenuScreen menu;
    public GameScreen jeuScreen;
    public GameOverScreen overScreen;
    public WinLevelScreen WinScreen;
    // le score du jeu
    public int ScoreTotale = 0;
    // Lire le score
    private final Array<Screen> screens = new Array<>();
    private int CurrentMap = 1;// cette variable determine la map a cree lors du lancment du jeux
    // Son des musiques
    public static float MUSIC_VOLUME = 0.2f;

    public int getCurrentMap() {
        return this.CurrentMap;
    }

    public void setCurrentMap(int CarteCourante) {

        this.CurrentMap = CarteCourante;
    }

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
