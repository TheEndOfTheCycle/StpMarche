package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class WinLevelScreen implements Screen {
    final Test game;
    OrthographicCamera camera;
    final int VIEW_PORT_WIDTH = 800;
    final int VIEW_PORT_HEIGHT = 480;
    final int OFFSET_TITLE = 200;
    final int TITLE_X = 180;
    final int TITLE_Y = Gdx.graphics.getHeight();
    BitmapFont FontTitle;
    private final Texture BackgroundTexture = new Texture("BackGroundImages/victory.png");

    public WinLevelScreen(final Test game) {
        this.game = game;
        camera = new OrthographicCamera();
        FontTitle = new BitmapFont();
        camera.setToOrtho(false, VIEW_PORT_WIDTH, VIEW_PORT_HEIGHT);
        game.setCurrentMap(game.getCurrentMap() + 1);// on passe a la map suivante
        if (game.getCurrentMap() == 4) {// on atteint la derniere map
            game.setCurrentMap(1);// on repart de la 1ere map
        }
        game.jeuScreen.sonJeu.dispose();// on est pas sur le jeu donc on enleve la musique
    }

    public void draw(SpriteBatch batch) {
        // Dessinez le mur à l'écran en utilisant les coordonnées du rectangle et la
        // texture
        batch.draw(BackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        FontTitle.setColor(Color.RED);
        FontTitle.getData().setScale(3);
        FontTitle.draw(batch, "You have Won", TITLE_X, TITLE_Y - 70);
    }

    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        // game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();// on dessine l image grace au batch de la classe Test
        draw(game.batch);
        game.batch.end();
        if (Gdx.input.isTouched()) {
            game.jeuScreen = new GameScreen(game, game.getCurrentMap());
            game.setScreen(game.jeuScreen);
            // game.menu.sonMenu.loop();
        }
    }

    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
