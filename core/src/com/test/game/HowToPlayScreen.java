package com.test.game;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;

public class HowToPlayScreen implements Screen {
    final Test game;
    OrthographicCamera camera;
    final int VIEW_PORT_WIDTH = 800;
    final int VIEW_PORT_HEIGHT = 480;
    final int OFFSET_TITLE = 200;
    private final Texture BackgroundTexture = new Texture("BackGroundImages/lore.jpg");
    private BitmapFont Back;
    final int BACK_X = 100;
    final int BACK_Y = 100;
    final int BACK_SIZE = 2;
    final Color BACK_COLOR = Color.RED;
    final Color TEXTE_COLOR = Color.RED;
    final float TEXTE_SIZE = 1.3f;
    BufferedReader reader;// objet qui nous permet de lire le fichier
    BitmapFont FontTexte;
    BitmapFont FontTitle;
    final float TITLE_X = 190;
    final float TITLE_Y = Gdx.graphics.getHeight();
    String texte = "\n";

    public HowToPlayScreen(final Test game) {
        try {
            reader = new BufferedReader(new FileReader("HowTo/instructions.txt"));
        } catch (IOException ee) {
            System.out.println("fichier text non lu");
        }
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEW_PORT_WIDTH, VIEW_PORT_HEIGHT);
        Back = new BitmapFont();
        FontTexte = new BitmapFont();
        FontTitle = new BitmapFont();
        Gdx.input.setInputProcessor(new InputAdapter() {

            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.B) {
                    game.setScreen(game.menu);
                }
                return true;
            }

        });

    }

    public void TextRender() {
        try {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                System.out.println(ligne);
                if (ligne.length() != 0) {// on verifie que la ligne n est pas vode sinon parseInt renvoie une erreur
                    texte += ligne + "\n";
                }
            }
            // System.out.println("plus haut entier" + PlusHautScore);
            reader.close();
            // ScoreFont.getData().setScale(1);
        } catch (IOException ee) {

        }
    }

    public void draw(SpriteBatch batch) {
        // Dessinez le mur à l'écran en utilisant les coordonnées du rectangle et la
        // texture
        batch.begin();
        batch.draw(BackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Back.setColor(BACK_COLOR);
        Back.getData().setScale(BACK_SIZE);
        Back.draw(batch, "Back: Press B to get back to Menu", BACK_X, BACK_Y);
        FontTexte.setColor(TEXTE_COLOR);
        FontTexte.getData().setScale(TEXTE_SIZE);
        FontTexte.draw(batch, texte, Gdx.graphics.getWidth() - 460, Gdx.graphics.getHeight() - 70);
        FontTitle.setColor(Color.WHITE);
        FontTitle.getData().setScale(3);
        FontTitle.draw(batch, "Instructions", TITLE_X, TITLE_Y);
        batch.end();
    }

    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        draw(game.batch);

        TextRender();

        // game.batch.setProjectionMatrix(camera.combined);

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
