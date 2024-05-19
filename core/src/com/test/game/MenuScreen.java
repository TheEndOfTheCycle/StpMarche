package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen implements Screen {

    final Test game;

    OrthographicCamera camera;

    final int VIEW_PORT_WIDTH = 800;
    final int VIEW_PORT_HEIGHT = 480;

    public BitmapFont test;
    public BitmapFont Tfont;

    private final Texture BackgroundTexture;

    final int OFFSET_TITLE = 200;

    Sound sonMenu;
    Sound sonJeu;

    // Constructeur de la classe
    public MenuScreen(final Test game) {
        this.game = game;
        this.test = new BitmapFont();
        this.Tfont = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEW_PORT_WIDTH, VIEW_PORT_HEIGHT);
        BackgroundTexture = new Texture("BackGroundImages/red-wings.jpg");
        sonMenu = Gdx.audio.newSound(Gdx.files.internal("Music/Unlimited-Blade-Works.mp3"));
    }

    // Méthode pour dessiner l'écran du menu
    public void draw(SpriteBatch batch) {
        batch.draw(BackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();

        game.batch.begin();

        draw(game.batch);

        // Configuration de la police pour le titre du jeu
        Tfont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        Tfont.getData().setScale(3);
        Tfont.setColor(Color.RED);

        // Dessiner le titre du jeu
        Tfont.draw(game.batch, "Time To fly Red Baron ", (Gdx.graphics.getWidth() / 2) - OFFSET_TITLE,
                Gdx.graphics.getHeight() - 10);

        game.batch.end();

        // Si l'utilisateur touche l'écran, démarrer le jeu
        if (Gdx.input.isTouched()) {
            game.jeuScreen = new GameScreen(game);
            game.setScreen(game.jeuScreen);
            this.dispose();
            game.menu.sonMenu.dispose();
            game.jeuScreen.sonJeu.loop();
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
