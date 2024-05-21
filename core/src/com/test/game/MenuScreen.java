package com.test.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
import com.test.game.planes.Red;

public class MenuScreen implements Screen {

    final Test game;

    OrthographicCamera camera;

    final int VIEW_PORT_WIDTH = 800;
    final int VIEW_PORT_HEIGHT = 480;
    BufferedReader reader;
    int PlusHautScore = -1;
    public BitmapFont Tfont;
    private BitmapFont ScoreFont;
    private Texture BackgroundTexture;
    final int HIGH_SCORE_X = (Gdx.graphics.getWidth() / 2) - 100;
    final int HIGH_SCORE_Y = 200;
    final Color SCORE_COLOR = Color.WHITE;
    final int OFFSET_TITLE = 200;
    final String MSG_ABSENCE_SCORE = "pas encore de score";
    Sound sonMenu;// son du menu

    // Constructeur de la classe
    public MenuScreen(final Test game) {
        this.game = game;
        this.Tfont = new BitmapFont();
        this.ScoreFont = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEW_PORT_WIDTH, VIEW_PORT_HEIGHT);
        BackgroundTexture = new Texture("BackGroundImages/red-wings.jpg");
        try {
            sonMenu = Gdx.audio.newSound(Gdx.files.internal("Music/Unlimited-Blade-Works.mp3"));
            reader = new BufferedReader(new FileReader("Score/score.txt"));
        } catch (IOException ee) {

        }
    }

    public void PlusHautScore() {// cette fonction calcule le plus haut score dans le fichier score.txt
        try {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                System.out.println(ligne);
                if (ligne.length() != 0) {// on verifie que la ligne n est pas vode sinon parseInt renvoie une erreur
                    if (PlusHautScore < Integer.parseInt(ligne)) {// on convertit la ligne en entier
                        PlusHautScore = Integer.parseInt(ligne);
                    }
                }
            }
            // System.out.println("plus haut entier" + PlusHautScore);
            reader.close();
            // ScoreFont.getData().setScale(1);
        } catch (IOException ee) {

        }
    }

    // Méthode pour dessiner l'écran du menu
    public void draw(SpriteBatch batch) {
        batch.draw(BackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Tfont.draw(game.batch, "Time To fly Red Baron ", (Gdx.graphics.getWidth() / 2) - OFFSET_TITLE,
                Gdx.graphics.getHeight() - 10);
        if (PlusHautScore != -1) {
            ScoreFont.draw(game.batch, "Plus Haut score :" + PlusHautScore, HIGH_SCORE_X, HIGH_SCORE_Y);
        } else {
            ScoreFont.draw(game.batch, MSG_ABSENCE_SCORE, HIGH_SCORE_X, HIGH_SCORE_Y);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.begin();

        // Configuration de la police pour le titre du jeu
        Tfont.getData().setScale(3);
        Tfont.setColor(Color.RED);
        PlusHautScore();
        // Dessiner le titre du jeu
        ScoreFont.setColor(SCORE_COLOR);
        draw(game.batch);
        game.batch.end();

        // Si l'utilisateur touche l'écran, démarrer le jeu
        if (Gdx.input.isTouched()) {
            game.jeuScreen = new GameScreen(game, game.getCurrentMap());
            game.setScreen(game.jeuScreen);
            this.dispose();
            game.menu.sonMenu.dispose();
            game.jeuScreen.sonJeu.loop(Test.MUSIC_VOLUME);
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
