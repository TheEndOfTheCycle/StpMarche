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
    private final Texture BackgroundTexture = new Texture("BackGroundImages/youdied.jpg");
    static Sound sonDeath = Gdx.audio.newSound(Gdx.files.internal("Music/You-died.mp3"));

    public GameOverScreen(final Test game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEW_PORT_WIDTH, VIEW_PORT_HEIGHT);
    }

    public void draw(SpriteBatch batch) {
        // Draw the background texture to cover the entire screen
        batch.draw(BackgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin(); // Start drawing with the game's batch
        draw(game.batch);
        game.batch.end();
        if (Gdx.input.isTouched()) {
            game.menu = new MenuScreen(game);
            dispose();
            game.setScreen(new MenuScreen(game));
            game.menu.sonMenu.loop();
        }
    }

    @Override
    public void show() {
        sonDeath.play();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
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
        BackgroundTexture.dispose();
        sonDeath.dispose();
    }
}
