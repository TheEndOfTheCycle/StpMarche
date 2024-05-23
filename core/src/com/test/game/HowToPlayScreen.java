package com.test.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Cette classe représente l'écran des instructions de jeu.
 */
public class HowToPlayScreen implements Screen {
    final Test game;
    OrthographicCamera camera;
    final int VIEW_PORT_WIDTH = 800;
    final int VIEW_PORT_HEIGHT = 480;
    private final Texture BackgroundTexture = new Texture("BackGroundImages/lore.jpg");
    private BitmapFont Back;
    final int BACK_X = 100;
    final int BACK_Y = 100;
    final int BACK_SIZE = 2;
    final Color BACK_COLOR = Color.RED;
    final Color TEXT_COLOR = Color.RED;
    final float TEXT_SIZE = 1.3f;
    BufferedReader reader;
    BitmapFont FontText;
    BitmapFont FontTitle;
    final float TITLE_X = 190;
    final float TITLE_Y = Gdx.graphics.getHeight();
    String text = "\n";

    /**
     * Constructeur de la classe HowToPlayScreen.
     *
     * @param game l'instance de la classe principale du jeu
     */
    public HowToPlayScreen(final Test game) {
        try {
            reader = new BufferedReader(new FileReader("HowTo/instructions.txt"));
        } catch (IOException ee) {
            System.out.println("Fichier texte non lu");
        }
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEW_PORT_WIDTH, VIEW_PORT_HEIGHT);
        Back = new BitmapFont();
        FontText = new BitmapFont();
        FontTitle = new BitmapFont();
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.B) {
                    game.setScreen(game.menu);
                    dispose();
                }
                return true;
            }
        });
    }

    /**
     * Méthode pour afficher le texte des instructions de jeu.
     */
    public void renderText() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (!line.isEmpty()) {
                    text += line + "\n";
                }
            }
            reader.close();
        } catch (IOException ee) {
        }
    }

    /**
     * Méthode pour dessiner les éléments de l'écran des instructions.
     *
     * @param batch le SpriteBatch utilisé pour dessiner
     */
    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(BackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Back.setColor(BACK_COLOR);
        Back.getData().setScale(BACK_SIZE);
        Back.draw(batch, "Back: Press B to get back to Menu", BACK_X, BACK_Y);
        FontText.setColor(TEXT_COLOR);
        FontText.getData().setScale(TEXT_SIZE);
        FontText.draw(batch, text, Gdx.graphics.getWidth() - 460, Gdx.graphics.getHeight() - 70);
        FontTitle.setColor(Color.WHITE);
        FontTitle.getData().setScale(3);
        FontTitle.draw(batch, "Instructions", TITLE_X, TITLE_Y);
        batch.end();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        draw(game.batch);
        renderText();
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}

}
