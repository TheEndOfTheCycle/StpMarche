package com.test.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class MenuScreen implements Screen {

    final Test game;

    OrthographicCamera camera;
    long elapsedTime;
    long startTime;
    long lastDrawTime;
    final int VIEW_PORT_WIDTH = 800;
    final int VIEW_PORT_HEIGHT = 480;
    BufferedReader reader;
    int PlusHautScore = -1;
    private BitmapFont Tfont;
    private BitmapFont ScoreFont;
    private BitmapFont Start;
    private BitmapFont HowToPlay;
    private Texture BackgroundTexture;
    final int HIGH_SCORE_X = (Gdx.graphics.getWidth() / 2) - 150;
    final int HIGH_SCORE_Y = 200;
    final int HOW_TO_PLAY_X = (Gdx.graphics.getWidth() / 2) - 200;
    final int HOW_TO_PLAY_Y = 90;
    final int OPTIONS_FONT_SIZE = 2;
    final int START_X = (Gdx.graphics.getWidth() / 2) - 180;
    final int START_Y = 160;
    final Color SCORE_COLOR = Color.RED;
    final int OFFSET_TITLE = 200;
    private boolean drawn = true;
    private float dissapearTime;
    private final float APPEAR_DISSAPEAR = 1.0f;

    final String MSG_ABSENCE_SCORE = "pas encore de score";
    Sound sonMenu;// son du menu

    // Constructeur de la classe
    public MenuScreen(final Test game) {
        this.game = game;
        this.Tfont = new BitmapFont();
        this.ScoreFont = new BitmapFont();
        this.Start = new BitmapFont();
        this.HowToPlay = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEW_PORT_WIDTH, VIEW_PORT_HEIGHT);
        BackgroundTexture = new Texture("BackGroundImages/red-wings.jpg");
        try {
            sonMenu = Gdx.audio.newSound(Gdx.files.internal("Music/Unlimited-Blade-Works.mp3"));
            reader = new BufferedReader(new FileReader("Score/score.txt"));
        } catch (IOException ee) {

        }

    }

    public void handleMenu() {// on gere les autres sreens selon la touch saisie
        Gdx.input.setInputProcessor(new InputAdapter() {

            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    game.jeuScreen = new GameScreen(game, game.getCurrentMap());
                    game.setScreen(game.jeuScreen);
                    game.menu.sonMenu.dispose();
                    game.menu.dispose();
                }
                if (keycode == Input.Keys.H) {
                    game.HowToPlayScreen = new HowToPlayScreen(game);
                    game.setScreen(game.HowToPlayScreen);
                    game.menu.dispose();
                }
                return true;
            }

        });
    }

    public void PlusHautScore() {// cette fonction calcule le plus haut score dans le fichier score.txt
        try {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
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
        if (drawn) {// si c est vrai on dessine on attend que dissapear>appear_dissapear
            Start.draw(game.batch, "Start: Press enter to Start", START_X, START_Y);
        }

        HowToPlay.draw(batch, "How to play : Press H to learn how to play", HOW_TO_PLAY_X, HOW_TO_PLAY_Y);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        startTime = TimeUtils.millis();
        elapsedTime = TimeUtils.timeSinceMillis(startTime);
        camera.update();
        game.batch.begin();
        handleMenu();
        // Configuration de la police pour le titre du jeu
        Tfont.getData().setScale(3);
        Tfont.setColor(Color.RED);
        PlusHautScore();
        // Dessiner le titre du jeu
        ScoreFont.setColor(SCORE_COLOR);
        ScoreFont.getData().setScale(OPTIONS_FONT_SIZE);
        Start.getData().setScale(OPTIONS_FONT_SIZE);
        HowToPlay.getData().setScale(OPTIONS_FONT_SIZE - 0.5f);
        draw(game.batch);
        dissapearTime += delta;// delta represent le temps passe depuis le dernier frame
        if (dissapearTime >= APPEAR_DISSAPEAR) {
            drawn = !drawn;
            dissapearTime = 0;
        }

        game.batch.end();

        // Si l'utilisateur touche l'écran, démarrer le jeu

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
