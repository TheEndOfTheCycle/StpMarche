
package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.game.graphics.Wall;
import com.test.game.graphics.WallParser;
import com.test.game.graphics.Zeppelin;
import com.test.game.planes.French;
import com.test.game.planes.Plane;
import com.test.game.planes.Red;

public class GameScreen implements Screen {
    // Caméra et viewport
    private final OrthographicCamera camera;
    private final Viewport viewport;

    // Graphismes
    private final SpriteBatch batch;
    private final Texture background;

    //Défilement du fond
    private final int backgroundOffset;

    private final int WORLD_WIDTH = 800;
    private final int WORLD_HEIGHT = 480;


    private final int ENEMY_SPAWN_LEVEL_X = Gdx.graphics.getWidth();
    private final int ENEMY_SPAWN_LEVEL_Y = Gdx.graphics.getHeight();
    private final float ENEMY_SPAWN_TIME = 0;

    private final Test game;
    private final Array<Plane> FlyingEnemies;

    Array<Object> objects; // Liste des objects
    float scrollSpeed = 200.f; 
    ShapeRenderer shape;

    Plane player;
    final int PLAYER_START_LINE_Y = 200;
    final int PLAYER_START_LINE_X = 0;

    // Constructeur de la classe
    public GameScreen(final Test game) {
        this.game = game;
        this.game.addScreen(this);
        
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        background = new Texture("BackGroundImages/montains03.jpg"); // image de fond
        System.out.println(background.getHeight());
        backgroundOffset = 0;
        
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        
        objects = WallParser.parseWalls("Map/test.txt");
        player = new Red(PLAYER_START_LINE_X, PLAYER_START_LINE_Y);
        FlyingEnemies = new Array<>();

    }

    // Génération de la carte
    public void MapGenration() {
        float delta = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Calculer la largeur totale de la carte
        int mapWidth = WallParser.calculateMapWidth("Map/test.txt");

        // Mettez à jour et dessinez les objets
        for (Object object : objects) {
            if (object instanceof Wall) {
                Wall wall = (Wall) object;
                // Mettez à jour les coordonnées X pour faire défiler de gauche à droite
                float newX = wall.getX() - scrollSpeed * delta;

                // Réinitialiser la position lorsque la carte entière a défilé hors de l'écran
                if (newX < -Wall.WIDTH) {
                    newX += mapWidth;
                }

                // Mettez à jour la position du mur
                wall.setX(newX);

                // Dessinez le mur avec les nouvelles coordonnées
                wall.draw(batch);
            } else if (object instanceof Zeppelin) {
                // Mettez à jour les coordonnées X du Zeppelin pour le faire défiler de gauche à droite
                Zeppelin zep = (Zeppelin) object;
                zep.setPosition(zep.getPosition().x - scrollSpeed * delta, zep.getPosition().y);

                if (zep.getPosition().x < -70) {
                    zep.setPosition(mapWidth, zep.getPosition().y);
                }

                // Dessinez le Zeppelin avec les nouvelles coordonnées
                zep.draw(batch);
            }
        }

       // Vérifiez les collisions entre le joueur et les murs
        for (Object object : objects) {
            if (object instanceof Wall) {
                Wall wall = (Wall) object;
                player.checkCollision(wall);
            } else if (object instanceof Zeppelin) {
                Zeppelin zeppelins =  (Zeppelin) object ;
                player.checkCollision(zeppelins);
            }
        }

        batch.end();
    }

    public void createFlyingEnemy(){
        /*
            cette foncition permet de creer un enemi et l'ajouter a la liste des
            enmies(plus tard grace a la generecite et en passant en parametre une classe
            on pourra genrer plusieurs types d'ennemies.
        */
        French frenchPlane = new French(Gdx.graphics.getWidth() - 50, ENEMY_SPAWN_LEVEL_Y - 400, 40, 40, 10);
        FlyingEnemies.add(frenchPlane);
    }

    public void SpawnFlyingEnemy(Plane avion) {// fonction permet de dessiner un enemi
        // avion.setStartY(ENEMY_SPAWN_LEVEL_X);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        avion.update();
        avion.draw(shape);
        shape.end();
    }

    public void PlayerGeneration() {
        batch.begin();

        // Mettez à jour et dessinez le joueur
        player.update();
        player.draw(batch);

        // Fin de ShapeRenderer
        batch.end();
    }

    @Override
    public void render(float delta) {
        if(!player.getGameOver()){
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            MapGenration(); 
            PlayerGeneration();
            createFlyingEnemy();
            SpawnFlyingEnemy(FlyingEnemies.get(0));
        }
    }

    public void create() {}

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        // rainMusic.play();
    }

    // Du fait que la classe implements Screen, on doit implémenter
    // les méthodes suivantes : même si on les laisse vides...
    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

}