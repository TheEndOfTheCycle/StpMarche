package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.game.explosion.Explosion;
import com.test.game.graphics.Wall;
import com.test.game.graphics.WallParser;
import com.test.game.graphics.Zeppelin;
import com.test.game.planes.French;
import com.test.game.planes.Plane;
import com.test.game.planes.Red;
import com.test.game.shoots.Bomb;
import com.test.game.shoots.Bullets;
import com.test.game.shoots.Projectile;

public class GameScreen implements Screen {
    // Caméra et viewport
    private final OrthographicCamera camera;
    private final Viewport viewport;

    // Graphismes
    private final SpriteBatch batch;
    private final Texture background;

    // Défilement du fond
    private final int backgroundOffset;

    private final int WORLD_WIDTH = 800;
    private final int WORLD_HEIGHT = 480;

    private final int ENEMY_SPAWN_LEVEL_X = Gdx.graphics.getWidth();
    private final int ENEMY_SPAWN_LEVEL_Y = Gdx.graphics.getHeight();
    private final float ENEMY_SPAWN_TIME = 0;

    private final Test game;
    private final Array<Plane> FlyingEnemies;

    Array<Object> objects; // Liste des objects
    float scrollSpeed = 100.f;
    ShapeRenderer shape;

    Plane player;
    private final Array<Projectile> projectiles;
    final int PLAYER_START_LINE_Y = 200;
    final int PLAYER_START_LINE_X = 0;

    // Animation
    private TextureAtlas explosionAtlas;
    private Animation<TextureRegion> explosionAnimation;
    private Array<Explosion> explosions;
    private float explosionElapsedTime = 0f;
    private boolean explosionStarted = false;
    private float explosionX, explosionY;
    

    // Constructeur de la classe
    public GameScreen(final Test game) {
        this.game = game;
        this.game.addScreen(this);

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        background = new Texture("BackGroundImages/montains03.jpg"); // image de fond
        backgroundOffset = 0;

        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        batch = new SpriteBatch();
        shape = new ShapeRenderer();

        objects = WallParser.parseWalls("Map/test.txt");
        player = new Red(PLAYER_START_LINE_X, PLAYER_START_LINE_Y);
        FlyingEnemies = new Array<>();

        // Charger l'animation d'explosion
        explosionAtlas = new TextureAtlas(Gdx.files.internal("atlas/kisspng-sprite-explosion_pack.atlas"));
        explosionAnimation = new Animation<>(0.1f, explosionAtlas.getRegions());

        projectiles = new Array<>();
        explosions = new Array<>();

        // Ajouter un listener d'entrée pour détecter les clics de la souris et la barre d'espace
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Buttons.LEFT) {
                    createBullet();
                }
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    createBomb();
                }
                return true;
            }
        });
    }

    public void MapGeneration() {
        float delta = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        updateObjects(delta);
        drawObjects();
        checkCollisions();

        batch.end();
    }

    private void updateObjects(float delta) {
        // Calculer la largeur totale de la carte
        int mapWidth = WallParser.calculateMapWidth("Map/test.txt");

        // Mettre à jour les objets
        for (Object object : objects) {
            if (!player.getGameOver()) {
                if (object instanceof Wall) {
                    Wall wall = (Wall) object;
                    // Mettre à jour les coordonnées X pour faire défiler de gauche à droite
                    float newX = wall.getX() - scrollSpeed * delta;

                    // Réinitialiser la position lorsque la carte entière a défilé hors de l'écran
                    if (newX < -Wall.WIDTH) {
                        newX += mapWidth;
                    }

                    // Mettre à jour la position du mur
                    wall.setX(newX);
                } else if (object instanceof Zeppelin) {
                    Zeppelin zep = (Zeppelin) object;
                    // Mettre à jour les coordonnées X du Zeppelin pour le faire défiler de gauche à droite
                    zep.setPosition(zep.getPosition().x - scrollSpeed * delta, zep.getPosition().y);

                    if (zep.getPosition().x < -70) {
                        zep.setPosition(mapWidth, zep.getPosition().y);
                    }
                }
            }
        }
    }

    private void checkCollisions() {
        // Vérifier les collisions entre le joueur et les murs
        for (Object object : objects) {
            if (!player.getGameOver()) {
                if (object instanceof Wall) {
                    Wall wall = (Wall) object;
                    if (player.checkCollision(wall)) {
                        handlePlayerCollision();
                    }
                } else if (object instanceof Zeppelin) {
                    Zeppelin zeppelins = (Zeppelin) object;
                    if (player.checkCollision(zeppelins)) {
                        handlePlayerCollision();
                    }
                } else if (object instanceof Bomb) {
                    Bomb bomb = (Bomb) object;
                    // Vérifier la collision entre la bombe et les éléments de la carte
                    if (bombCollision(bomb)) {
                        // Produire l'animation d'explosion
                        explode(bomb.getX(), bomb.getY());
                        // Retirer la bombe de la liste
                        objects.removeValue(bomb, true);
                    }
                }
            }
        }
    }

    private void handlePlayerCollision() {
        // Produire l'animation d'explosion pour le joueur
        explode(player.getX(), player.getY());
        player.setGameOver(true); // Marquer le jeu comme terminé
    }

    private boolean bombCollision(Bomb bomb) {
        // Parcourir tous les murs de la carte pour vérifier la collision avec la bombe
        for (Object object : objects) {
            if (object instanceof Wall) {
                Wall wall = (Wall) object;
                // Vérifier si la bombe entre en collision avec le mur
                if (bomb.getX() + Bomb.WIDTH >= wall.getX() && bomb.getX() <= wall.getX() + Wall.WIDTH
                    && bomb.getY() + Bomb.HEIGHT >= wall.getY() && bomb.getY() <= wall.getY() + Wall.HEIGHT) {
                    return true; // Collision détectée avec un mur
                }
            }
        }
        return false; // Aucune collision détectée avec un mur
    }

    private void explode(float x, float y) {
        explosions.add(new Explosion(x, y, explosionAnimation));
    }

    private void drawObjects() {
        // Dessiner tous les objets, même si le jeu est terminé
        for (Object object : objects) {
            if (object instanceof Wall) {
                Wall wall = (Wall) object;
                wall.draw(batch);
            } else if (object instanceof Zeppelin) {
                Zeppelin zep = (Zeppelin) object;
                zep.draw(batch);
            }
        }
    }

    private void createBullet() {
        Bullets bullet = new Bullets(player.getX() + player.getWidth(), player.getY() + player.getHeight() / 2, 300f);
        projectiles.add(bullet);
    }

    private void createBomb() {
        Bomb bomb = new Bomb(player.getX() + player.getWidth() / 2, player.getY(), 200f);
        projectiles.add(bomb);
    }

    private void drawProjectiles() {
        batch.begin();
        for (Projectile projectile : projectiles) {
            projectile.draw(batch);
        }
        batch.end();
    }

    private void updateProjectiles(float delta) {
        for (int i = 0; i < projectiles.size; i++) {
            Projectile projectile = projectiles.get(i);
            projectile.update(delta);
            if (projectile.isOutOfScreen()) {
                projectiles.removeIndex(i);
            } else {
                // Vérifier les collisions avec les murs
                if (projectile instanceof Bomb) {
                    Bomb bomb = (Bomb) projectile;
                    if (bombCollision(bomb)) {
                        // Collision détectée avec un mur, retirer la bombe
                        explode(bomb.getX() - scrollSpeed * delta, bomb.getY());
                        projectiles.removeIndex(i);
                        // Ne pas dessiner l'explosion ici, laissez-le à la méthode render
                        continue; // Passer à la prochaine boucle car cette bombe est retirée
                    }
                }
                // Si ce n'est pas une bombe ou s'il n'y a pas de collision, dessiner la projectile normalement
                // Pas besoin de vérifier les collisions pour les balles normales car elles sont censées traverser les murs
            }
        }
    }

    public void createFlyingEnemy() {
        // Cette fonction permet de créer un ennemi et l'ajouter à la liste des ennemis
        French frenchPlane = new French(Gdx.graphics.getWidth() - 50, ENEMY_SPAWN_LEVEL_Y - 400, 40, 40, 10);
        FlyingEnemies.add(frenchPlane);
    }

    public void spawnFlyingEnemy(Plane avion) { // Fonction permettant de dessiner un ennemi
        shape.begin(ShapeRenderer.ShapeType.Filled);
        avion.update();
        avion.draw(shape);
        shape.end();
    }

    private void renderExplosions(float delta) {
        batch.begin();
        for (int i = 0; i < explosions.size; i++) {
            Explosion explosion = explosions.get(i);
            explosion.update(delta);
            explosion.draw(batch);
            if (explosion.isFinished()) {
                explosions.removeIndex(i);
                i--; // Réajuster l'index après suppression
            }
        }
        batch.end();
    }

    @Override
    public void render(float delta) {
        // Effacer l'écran
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dessiner la carte
        MapGeneration();

        // Si le jeu n'est pas terminé
        if (!player.getGameOver()) {
            renderGamePlay();
        } else {
            renderGameOver();
        }

        // Mettre à jour et dessiner les projectiles
        updateProjectiles(delta);
        drawProjectiles();

        // Dessiner les explosions
        renderExplosions(delta);
    }

    private void renderGamePlay() {
        // Mettre à jour et dessiner le joueur
        batch.begin();
        player.update();
        player.draw(batch);
        batch.end();
    
        // Créer et dessiner les ennemis
        createFlyingEnemy();
        if (FlyingEnemies.size > 0) {
            spawnFlyingEnemy(FlyingEnemies.get(0));
        }
    }    

    private void renderGameOver() {
        // Afficher l'animation d'explosion
        if (!explosionStarted) {
            explosionX = player.getX();
            explosionY = player.getY();
            explosionStarted = true;
        }
    
        batch.begin();
        explosionElapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = explosionAnimation.getKeyFrame(explosionElapsedTime, false);
        batch.draw(currentFrame, explosionX, explosionY, player.getWidth(), player.getHeight());
        batch.end();
    
        if (explosionAnimation.isAnimationFinished(explosionElapsedTime)) {
            // Animation terminée, ne rien dessiner
        }
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        explosionAtlas.dispose();
        for (Projectile projectile : projectiles) {
            projectile.dispose();
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {
        // start the playback of the background music when the screen is shown
        // rainMusic.play();
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}
}

