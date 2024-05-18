package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
    final Test game;
    OrthographicCamera camera;
    final int VIEW_PORT_WIDTH = 800;
    final int VIEW_PORT_HEIGHT = 480;
    final int OFFSET_TITLE = 200;
    private final Texture BackgroundTexture = new Texture("BackGroundImages/Game_Over.jpeg");
    static Sound deathSound = Gdx.audio.newSound(Gdx.files.internal("Music/You-died.mp3"));

    public GameOverScreen(final Test game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEW_PORT_WIDTH, VIEW_PORT_HEIGHT);
    }

    public void draw(SpriteBatch batch) {
        // Dessinez le mur à l'écran en utilisant les coordonnées du rectangle et la
        // texture
        batch.draw(BackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        // game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();// on dessine l image grace au batch de la classe Test
        draw(game.batch);
        game.batch.end();
        if (Gdx.input.isTouched()) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
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
