package com.test.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.BSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.game.buffs.Buff;
import com.test.game.buffs.Heart;
import com.test.game.explosion.Explosion;
import com.test.game.graphics.AntiAir;
import com.test.game.graphics.Arbre;
import com.test.game.graphics.Bush;
import com.test.game.graphics.Panzer;
import com.test.game.graphics.Rock;
import com.test.game.graphics.Wall;
import com.test.game.graphics.WallParser;
import com.test.game.graphics.Zeppelin;
import com.test.game.planes.Artillery;
import com.test.game.planes.BossFinale;
import com.test.game.planes.British;
import com.test.game.planes.French;
import com.test.game.planes.Plane;
import com.test.game.planes.Red;
import com.test.game.shoots.AmungUs;
import com.test.game.shoots.Bomb;
import com.test.game.shoots.Bullets;
import com.test.game.shoots.Projectile;
import com.test.game.shoots.Snake;
import com.test.game.shoots.SolAir;
import com.test.game.shoots.Tate;
import com.test.game.shoots.Trump;

public class GameScreen implements Screen {
    // ecriture de fichier
    BufferedWriter writer;
    // Son
    private final float SOUND_VOLUME = 0.1f;// (on caste en float sinon ca le lit comme un double),la valeur
                                            // est un pourcentage donc 1=100% volume du son
    private final float SHOOTING_VOULME = 0.2f;
    final String MUSIQUE1 = "Music/Red-B.mp3";
    final String MUSIQUE2 = "Music/Berserk.mp3";
    final String MUSIQUE3 = "Music/Evangelion.mp3";
    Sound sonJeu;
    // Caméra et viewport
    private final OrthographicCamera camera;
    private final Viewport viewport;
    // Graphismes
    private final SpriteBatch batch;
    private Texture background;
    private BitmapFont scoreFont;
    private BitmapFont FontObjective;
    int mapWidth;
    private final String CARET1 = "Map/LatestMap.txt";// ces variables representent les niveaux a charger,pas les images
                                                      // de
                                                      // fond
    private final String CARET2 = "Map/carte3.txt";// a changer pour generer une nouvelle map
    private final String CARET3 = "Map/MapBoss.txt";
    private TextureRegion backgroundRegion;
    // Défilement du fond
    private final int backgroundOffset;

    private final int WORLD_WIDTH = 800;
    private final int WORLD_HEIGHT = 480;

    private final float ENEMY_SPAWN_TIME = 0;
    private final float FRENCH_ENEMY_SPEED = 3;
    private final float BRITISH_ENEMY_SPEED = 1;
    private final Test game;

    Array<Object> objects; // Liste des objects
    float scrollSpeed = 100.f;
    ShapeRenderer shape;
    private int score = 0;
    // Parametres de jeu
    Red player;
    private final Array<Projectile> projectiles;
    private final Array<Projectile> enemyProjectiles;
    final int PLAYER_START_LINE_Y = 200;
    final int PLAYER_START_LINE_X = 0;
    private String objective;// objective pour pouvoir passer au prochain niveau
    // Animation
    private TextureAtlas explosionAtlas;
    private Animation<TextureRegion> explosionAnimation;
    private Array<Explosion> explosions;
    private float explosionElapsedTime = 0f;
    private boolean explosionStarted = false;
    private float explosionX, explosionY;
    // Enemies
    private final Array<Plane> FlyingEnemies;
    private final Array<Artillery> Fire_Support;
    private final float FIRE_SUPPORT_SPEED = 300;
    private final Texture TEXTURE_FRENCH;
    private final Texture TEXTURE_BRITISH;
    float lastEnemySpawnTime;
    float lastShellSpawnTime;
    long startTime;
    long elapsedTime;
    French BasicEnemy;
    British AdvancedEnemy;
    private BossFinale boss;
    private boolean BossSlevel = false;
    private final int ENEMY_SPAWN_LEVEL_X = Gdx.graphics.getWidth();
    private int ENEMY_SPAWN_LEVEL_Y = Gdx.graphics.getHeight() - 50;
    private final int FLYING_ENEMY_SPAWN_INTERVAL = 3000;
    private final int FIRE_SUPPORT_SPAWN = 4000;
    private final int SCORE_FOR_STAGE2 = 1;
    private final int SCORE_FOR_STAGE3 = 2;
    private final int ENEMY_SPAWN_RATIO = 50;// ratio du spawn entre les ennemies French et British
    private int spawnType = 1;// si 1 on spawn french,si 2 on spawn british
    private final int BASIC_DEATH_REQ = 2;// combien d enemies basics il faut tuer pour passer au niveau suivant
    private final int ANTI_AIR_REQ = 2;
    // Buff
    private Plane enemyspawn;
    private final int BUFF_SPAWN_TIME = 5000;
    Buff buffitem;
    long lastBuffSpawnTime;
    // Constructeur de la classe

    public GameScreen(final Test game, int niveauCourrant) {
        // sonJeu = Gdx.audio.newSound(Gdx.files.internal(MUSIQUE1));
        if (game.getCurrentMap() == 1) {
            sonJeu = Gdx.audio.newSound(Gdx.files.internal(MUSIQUE1));
            BossSlevel = false;
        }
        if (game.getCurrentMap() == 2) {
            sonJeu = Gdx.audio.newSound(Gdx.files.internal(MUSIQUE3));
            BossSlevel = false;
        }
        if (game.getCurrentMap() == 3) {
            sonJeu = Gdx.audio.newSound(Gdx.files.internal(MUSIQUE2));
            BossSlevel = true;

        }
        if (BossSlevel) {
            boss = new BossFinale(Gdx.graphics.getWidth() - BossFinale.FINAL_WIDTH, // on spawn le boss si on est au
                    // niveau 3
                    Gdx.graphics.getHeight() / 2 - BossFinale.FINAL_HEIGHT / 2);
        }
        sonJeu.loop(SOUND_VOLUME);// on lance la musique des que le jeu commence
        this.game = game;
        this.game.addScreen(this);
        try {
            writer = new BufferedWriter(new FileWriter("Score/score.txt", true));// append=true permet de ne pas écraser
                                                                                 // le fichier
        } catch (IOException e) {

        }
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        startTime = TimeUtils.millis();
        backgroundOffset = 0;
        Fire_Support = new Array<>();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        scoreFont = new BitmapFont();
        FontObjective = new BitmapFont();
        // objects = WallParser.parseWalls("Map/test.txt");
        player = new Red(PLAYER_START_LINE_X, PLAYER_START_LINE_Y);
        FlyingEnemies = new Array<>();
        TEXTURE_FRENCH = new Texture(Gdx.files.internal("Planes/french.png"));
        TEXTURE_BRITISH = new Texture(Gdx.files.internal("Planes/british.png"));
        // Charger l'animation d'explosion
        explosionAtlas = new TextureAtlas(Gdx.files.internal("atlas/kisspng-sprite-explosion_pack.atlas"));
        explosionAnimation = new Animation<>(0.1f, explosionAtlas.getRegions());
        projectiles = new Array<>();
        enemyProjectiles = new Array<>();
        explosions = new Array<>();
        // le niveau qu on va charger suivant le numero de la map
        if (game.getCurrentMap() == 1) {
            objects = WallParser.parseWalls(CARET1);
            mapWidth = WallParser.calculateMapWidth(CARET1);
        }
        if (game.getCurrentMap() == 2) {
            objects = WallParser.parseWalls(CARET2);
            mapWidth = WallParser.calculateMapWidth(CARET2);
        }
        if (game.getCurrentMap() == 3) {
            objects = WallParser.parseWalls(CARET3);
            mapWidth = WallParser.calculateMapWidth(CARET3);
        }
        // Ajouter un listener d'entrée pour détecter les clics de la souris et la barre
        // d'espace
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Buttons.LEFT && !player.getGameOver()) {// on check que la partie n est pas finie
                    createBullet();
                    // player.sonTire.play(SOUND_EFFECTS_VOLUME);
                    if (game.getScreen().getClass() == GameScreen.class) {// pour declencher l
                                                                          // effet sonor on
                                                                          // verifie si
                        // on est bien dans un objet de class
                        // GameScreen c a d on est bien entrain de
                        // jouer et pas dans un menu
                        player.sonTire.play(SHOOTING_VOULME);
                    }
                }
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.SPACE && !player.getGameOver()) {
                    createBomb();
                }
                if (game.getScreen().getClass() == GameScreen.class) {// pour declencher l

                    // player.sonBomb.play(SHOOTING_VOULME);
                }
                return true;
            }
        });
    }

    public void update() {
        if (elapsedTime - lastEnemySpawnTime >= FLYING_ENEMY_SPAWN_INTERVAL) {// si la difference entre le temps
                                                                              // courrant et le temps du dernier
                                                                              // spawn est superieur au temps de
            if (BossSlevel == false) {
                createFlyingEnemy();
            } // génération des enemies
              // System.err.println("elapsed time" + elapsedTime + "derneri spawn" +
              // lastEnemySpawnTime);

            // System.err.println("yes");
        }
        if (elapsedTime - lastShellSpawnTime >= FIRE_SUPPORT_SPAWN && BossSlevel == false) {
            createSupportEnemy();
        }
    }

    public void MapGeneration() {
        float delta = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        if (game.getCurrentMap() == 1) {
            background = new Texture("BackGroundImages/montains01.jpg"); // image de fond
        }
        if (game.getCurrentMap() == 2) {
            background = new Texture("BackGroundImages/montains02.png");
        }
        if (game.getCurrentMap() == 3) {
            background = new Texture("BackGroundImages/mapBoss.png");

        }
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        updateObjects(delta);
        drawObjects();

        batch.end();
    }

    private void updateObjects(float delta) {
        // Calculer la largeur totale de la carte

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
                    // Mettre à jour les coordonnées X du Zeppelin pour le faire défiler de gauche à
                    // droite
                    zep.setPosition(zep.getPosition().x - scrollSpeed * delta, zep.getPosition().y);

                    if (zep.getPosition().x < -70) {
                        zep.setPosition(mapWidth, zep.getPosition().y);
                    }
                } else if (object instanceof AntiAir) {
                    AntiAir groundEnemy = (AntiAir) object;
                    // Mettre à jour les coordonnées X pour faire défiler de gauche à droite
                    float newX = groundEnemy.getX() - scrollSpeed * delta;

                    // Réinitialiser la position lorsque la carte entière a défilé hors de l'écran
                    if (newX < -AntiAir.RED_WIDTH) {
                        newX += mapWidth;
                    }

                    // Mettre à jour la position du mur
                    groundEnemy.setX(newX);

                    // Tirer vers l'avion si à portée
                    groundEnemy.update(delta, player, enemyProjectiles);// on fait tirer l enemie qui suit la position
                                                                        // du
                    // joueur
                } else if (object instanceof Bush) {
                    Bush bush = (Bush) object;
                    // Mettre à jour les coordonnées X pour faire défiler de gauche à droite
                    float newX = bush.getX() - scrollSpeed * delta;

                    // Réinitialiser la position lorsque la carte entière a défilé hors de l'écran
                    if (newX < -AntiAir.RED_WIDTH) {
                        newX += mapWidth;
                    }

                    // Mettre à jour la position du mur
                    bush.setX(newX);
                } else if (object instanceof Arbre) {
                    Arbre arbre = (Arbre) object;
                    // Mettre à jour les coordonnées X pour faire défiler de gauche à droite
                    float newX = arbre.getX() - scrollSpeed * delta;

                    // Réinitialiser la position lorsque la carte entière a défilé hors de l'écran
                    if (newX < -AntiAir.RED_WIDTH) {
                        newX += mapWidth;
                    }

                    // Mettre à jour la position du mur
                    arbre.setX(newX);

                } else if (object instanceof Panzer) {
                    Panzer panzer = (Panzer) object;
                    // Mettre à jour les coordonnées X pour faire défiler de gauche à droite
                    float newX = panzer.getX() - scrollSpeed * delta;

                    // Réinitialiser la position lorsque la carte entière a défilé hors de l'écran
                    if (newX < -AntiAir.RED_WIDTH) {
                        newX += mapWidth;
                    }

                    // Mettre à jour la position du mur
                    panzer.setX(newX);

                } else if (object instanceof Rock) {
                    Rock rock = (Rock) object;
                    // Mettre à jour les coordonnées X pour faire défiler de gauche à droite
                    float newX = rock.getX() - scrollSpeed * delta;

                    // Réinitialiser la position lorsque la carte entière a défilé hors de l'écran
                    if (newX < -AntiAir.RED_WIDTH) {
                        newX += mapWidth;
                    }

                    // Mettre à jour la position du mur
                    rock.setX(newX);

                }
            }
        }
    }

    private void checkPlayerCollisions() {
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

                }
                for (Plane avion : FlyingEnemies) {// on check si on rentre dans un enemie,en effet mort instantanée en
                                                   // cas de collision
                    if (player.checkCollision(avion)) {
                        handlePlayerCollision();
                    }
                }
                // on gere la collision avec l artillerie
                for (int i = 0; i < Fire_Support.size; i++) {
                    if (player.collidesWith(Fire_Support.get(i))) {
                        player.setHp(player.getHp() - 1);
                        explode(Fire_Support.get(i).getX(), Fire_Support.get(i).getY());
                        Fire_Support.removeIndex(i);
                    }
                }

            }
        }

    }

    public boolean checkCollisionSpawn(Plane avion) {// on gere la collisoin des enemies seulment lors du spawn
        for (Object object : objects) {
            if (object instanceof Wall) {
                Wall mur = (Wall) object;
                if (avion.checkCollision(mur)) {
                    return true;
                }
            }

        }
        if (player.checkCollision(avion)) {
            return true;
        }
        return false;
    }

    public boolean checkEnemyCollision(Plane avion) {// on gere la collisoin des enemies seulment
        for (Object object : objects) {
            if (object instanceof Wall) {
                Wall mur = (Wall) object;
                if (avion.checkCollision(mur)) {
                    if (avion instanceof British) {// en effet le british est invunérable au terrain,il ne le percute
                                                   // jamais
                        avion.setY(avion.getY() + 20);
                        return false;
                    }
                    if (avion instanceof French) {// le French meurt lorsque il rentre dans le terrain
                        return true;
                    }
                }
            }

        }
        if (player.checkCollision(avion)) {
            return true;
        }
        return false;
    }

    /*
     * public boolean checkEnemyCollision(Plane avion) {// on gere la collisoin des
     * enemies seulment
     * for (Object object : objects) {
     * if (object instanceof Wall) {
     * Wall mur = (Wall) object;
     * if (avion.checkCollision(mur)) {
     * return true;
     * }
     * }
     * 
     * }
     * if (player.checkCollision(avion)) {
     * return true;
     * }
     * return false;
     * }
     */
    public boolean checkBuffSpawn(Buff buff) {
        for (Object objet : objects) {
            if (objet instanceof Wall) {
                Wall tempo = (Wall) objet;
                if (buff.collidesWith(tempo)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void handleBuffSpawn() {

        if (buffitem != null) {
            buffitem.draw(batch);
            buffitem.update();
            if (player.collidesWithBuff(buffitem)) {
                if (buffitem instanceof Heart) {
                    player.setHp(player.getHp() + 1);
                    buffitem = null;
                }
            }

        }
        if (buffitem != null) {
            if (buffitem.getX() < 10) {
                buffitem = null;
            }
        }
        if (elapsedTime - lastBuffSpawnTime > BUFF_SPAWN_TIME && buffitem == null) {// il ne doit pas y avoir plus d un
                                                                                    // buff sur la map
            buffitem = new Heart(MathUtils.random(100, 400), MathUtils.random(100, 400));
            while (checkBuffSpawn(buffitem) == false) {
                buffitem = new Heart(MathUtils.random(100, 400), MathUtils.random(100, 400));
            }
            lastBuffSpawnTime = elapsedTime;
        }

    }

    // cette fonction va gerer les avoins lorsque ils se prennent des degats
    public void handleDamagedFlyingEnemy() {
        for (Projectile tire : projectiles) {
            for (Plane avion : FlyingEnemies) {
                if (avion.collidesWith(tire) && (tire instanceof SolAir) == false) {// on verifie que l enemeie est
                                                                                    // touche mais pas par une defense
                                                                                    // aerienne
                    avion.setHp(avion.getHp() - 1);// on diminue la vie
                    tire.setHit(true);
                    if (avion.getHp() == 0) {
                        // avion.setIsDeadByFireHit(true);
                        // System.err.println("mort par tire");
                        if (avion instanceof French) {
                            game.ScoreTotale += French.getScoreValue();
                            game.BasicEnemyDeath++;
                        }
                        if (avion instanceof British) {
                            game.ScoreTotale += British.getScoreValue();
                        }
                    }
                }
            }
        }
    }

    private void handlePlayerHealth() {
        if (player.getHp() == 0) {
            explode(player.getX(), player.getY());
            player.setGameOver(true);
        }
    }

    private void handlePlayerCollision() {
        // Produire l'animation d'explosion pour le joueur
        explode(player.getX(), player.getY());
        player.setGameOver(true); // Marquer le jeu comme terminé
    }

    public void handleEnemyCollision() {// cette fonction gere la mort d un enemie
        for (Plane avion : FlyingEnemies) {
            if (checkEnemyCollision(avion)) {
                explode(avion.getX(), avion.getY());
                avion.setHp(0);
            }
        }
        handleAntiAirCollisions();
    }

    private void handleAntiAirCollisions() {// on gere la collison des anti air avec les bombes largues par l enemie
        for (int i = 0; i < projectiles.size; i++) {
            Projectile projectile = projectiles.get(i);
            if (projectile instanceof Bomb) {
                for (Object object : objects) {
                    if (object instanceof AntiAir) {
                        AntiAir antiAir = (AntiAir) object;
                        if (antiAir.collidesWith(projectile)) {
                            // Créer une explosion à la position de l'anti-air
                            explode(antiAir.getX(), antiAir.getY());
                            game.ScoreTotale += AntiAir.getScoreValue();// on modifie le score
                            if (game.getCurrentMap() == 2) {
                                game.AntiAirDeath++;
                            }
                            // Retirer l'anti-air et le projectile de leurs listes respectives
                            objects.removeValue(antiAir, true);
                            projectiles.removeIndex(i);
                            i--; // Réajuster l'index après suppression
                            break; // Sortir de la boucle pour ce projectile
                        }
                    }
                }
            }
        }
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
            if (object instanceof AntiAir) {
                AntiAir air = (AntiAir) object;
                air.draw(batch);
            }
            if (object instanceof Bush) {
                Bush bush = (Bush) object;
                bush.draw(batch);
            }
            if (object instanceof Panzer) {
                Panzer panzer = (Panzer) object;
                panzer.draw(batch);
            }
            if (object instanceof Arbre) {
                Arbre arbre = (Arbre) object;
                arbre.draw(batch);
            }
            if (object instanceof Rock) {
                Rock rock = (Rock) object;
                rock.draw(batch);
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
        for (Projectile tire : enemyProjectiles) {
            tire.draw(batch);
        }
        batch.end();

    }

    private void updateProjectiles(float delta) {
        // Parcourir les projectiles du joueur
        for (int i = projectiles.size - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            projectile.update(delta);
            if (projectile.isOutOfScreen() || projectile.getHit()) {
                projectiles.removeIndex(i);
                continue;
            }
            // on gere la collisoin des projectiles avec le boss
            // Vérifier les collisions avec le boss
            if (boss != null && boss.collidesWithProj(projectile)) {
                // Réduire la santé du boss en fonction des dégâts du projectile
                boss.setHp(boss.getHp() - 1);
                System.out.println("hpppp : " + boss.getHp());
                // Marquer le projectile comme touché pour le retirer plus tard
                projectiles.removeIndex(i);
                if (boss.getHp() == 0) {
                    explode(boss.getX() + (boss.getWidth() / 2), boss.getY() + (boss.getHeight() / 2));

                }
                continue;
            }
            // Vérifier les collisions avec les murs pour les bombes
            if (projectile instanceof Bomb) {
                Bomb bomb = (Bomb) projectile;
                if (bombCollision(bomb)) {
                    explode(bomb.getX() - scrollSpeed * delta, bomb.getY());
                    projectiles.removeIndex(i);
                    continue;
                }
            }
        }

        // Parcourir les projectiles des ennemis
        for (int i = enemyProjectiles.size - 1; i >= 0; i--) {
            Projectile tire = enemyProjectiles.get(i);

            if (tire instanceof Bullets) {
                Bullets balle = (Bullets) tire;
                balle.updateForEnemy(delta);
            } else if (tire instanceof SolAir) {
                SolAir tireAir = (SolAir) tire;
                tireAir.update(delta);
            } else if (tire instanceof Trump) {
                Trump tempo = (Trump) tire;
                tempo.update(delta);
            } else if (tire instanceof AmungUs) {
                AmungUs tempo = (AmungUs) tire;
                tempo.update(delta);
            } else if (tire instanceof Tate) {
                Tate tempo = (Tate) tire;
                tempo.update(delta);
            } else if (tire instanceof Snake) {
                Snake tempo = (Snake) tire;
                tempo.update(delta);
            }

            if (tire.isOutOfScreen()) {
                enemyProjectiles.removeIndex(i);
                continue;
            }

            // Vérifier les collisions avec le joueur
            if (player.collidesWith(tire)) {
                player.setHp(player.getHp() - 1);
                enemyProjectiles.removeIndex(i);
                continue;
            }
            // on gerer les collisons avec le boss
            if (tire instanceof AmungUs && player.collidesWithProj((AmungUs) tire)) {
                player.setHp(player.getHp() - 1);
                enemyProjectiles.removeIndex(i);
                continue;
            }

            if (tire instanceof Trump && player.collidesWithProj((Trump) tire)) {
                player.setHp(player.getHp() - 1);
                enemyProjectiles.removeIndex(i);
                continue;
            }

            if (tire instanceof Tate && player.collidesWithProj((Tate) tire)) {
                player.setHp(player.getHp() - 1);
                enemyProjectiles.removeIndex(i);
                continue;
            }

            if (tire instanceof SolAir && player.collidesWithProj((SolAir) tire)) {
                player.setHp(player.getHp() - 1);
                enemyProjectiles.removeIndex(i);
                continue;
            }
        }
    }

    public void createFlyingEnemy()

    {

        ENEMY_SPAWN_LEVEL_Y = MathUtils.random(50, Gdx.graphics.getHeight() - 50);
        if (game.getCurrentMap() == 1) {
            enemyspawn = new French(Gdx.graphics.getWidth() - 30, ENEMY_SPAWN_LEVEL_Y, 40, 40,
                    FRENCH_ENEMY_SPEED, TEXTURE_FRENCH);
        }
        if (game.getCurrentMap() == 2) {// le 2eme type d enemies apprait uniquement sur la 2eme carte et aleatoirment
            if (MathUtils.random(1, 100) > ENEMY_SPAWN_RATIO) {
                spawnType = 1;
                enemyspawn = new French(Gdx.graphics.getWidth() - 30, ENEMY_SPAWN_LEVEL_Y, 40, 40,
                        FRENCH_ENEMY_SPEED, TEXTURE_FRENCH);
            } else {

                spawnType = 2;
                enemyspawn = new British(Gdx.graphics.getWidth() - 30, ENEMY_SPAWN_LEVEL_Y, 40, 40,
                        BRITISH_ENEMY_SPEED, TEXTURE_BRITISH);

            }
        }
        while (checkCollisionSpawn(enemyspawn)) {
            ENEMY_SPAWN_LEVEL_Y = MathUtils.random(100, Gdx.graphics.getHeight());
            if (spawnType == 1) {
                enemyspawn = new French(Gdx.graphics.getWidth() - 30, ENEMY_SPAWN_LEVEL_Y, 40, 40,
                        FRENCH_ENEMY_SPEED, TEXTURE_FRENCH);
            }
            if (spawnType == 2) {// on ne spawn le type d enemies british ssi on est sur la
                                 // 2eme map
                enemyspawn = new British(Gdx.graphics.getWidth() - 30, ENEMY_SPAWN_LEVEL_Y, 40, 40,
                        BRITISH_ENEMY_SPEED, TEXTURE_BRITISH);
            }
        }
        FlyingEnemies.add(enemyspawn);
        lastEnemySpawnTime = elapsedTime;
    }

    public void createSupportEnemy() {
        Fire_Support.add(new Artillery(MathUtils.random(30, Gdx.graphics.getWidth() - 30), Gdx.graphics.getHeight(), 50,
                20, FIRE_SUPPORT_SPEED));
        lastShellSpawnTime = elapsedTime;
    }

    public void spawnFlyingEnemy(Plane avion) { // Fonction permettant de dessiner un ennemi
        shape.begin(ShapeRenderer.ShapeType.Filled);
        avion.update();
        avion.draw(batch);
        shape.end();
    }

    public void UpdateSupportEnemy() {
        Iterator<Artillery> iter = Fire_Support.iterator();
        while (iter.hasNext()) {
            Artillery fire = iter.next();
            fire.update();
            if (fire.getY() < 0) {
                iter.remove();
            }
        }
        for (Artillery fire : Fire_Support) {
            SpawnFlyingEnemy(fire);
        }
    }

    public void SpawnFlyingEnemy(Plane avion) {// fonction permet de dessiner un enemi
        // avion.setStartY(ENEMY_SPAWN_LEVEL_X);
        // sshape.begin(ShapeRenderer.ShapeType.Filled);
        batch.begin();
        avion.draw(batch);
        batch.end();
        // on obtient le temps courrent durant le jeu
    }

    public void UpdateFlyingEnemy() {

        // cette fonction gere les mouvemnts et l'état des enemies
        handleDamagedFlyingEnemy();
        Iterator<Plane> iter = FlyingEnemies.iterator();
        while (iter.hasNext()) {
            Plane avion = iter.next();
            // avion.Xspeed = 300;
            // avion.update();// on met a jour la position de l enemie a chaque fois
            if (avion instanceof French) {
                BasicEnemy = (French) avion;
                BasicEnemy.update();
                BasicEnemy.updateFire(Gdx.graphics.getDeltaTime(), enemyProjectiles);
            }
            if (avion instanceof British) {
                AdvancedEnemy = (British) avion;
                AdvancedEnemy.update();
                AdvancedEnemy.updateFire(Gdx.graphics.getDeltaTime(), enemyProjectiles);
            }
            if (avion.getX() < 20) {
                // createFlyingEnemy();
                iter.remove();
            }
            if (avion.getHp() == 0) {
                explode(avion.getX(), avion.getY());
                iter.remove();

            }
            // on va gerer le score

        }
        // cette boucle permet d afficher les enemies
        for (Plane avion : FlyingEnemies) {
            // SpawnFlyingEnemy(avion);
            if (avion instanceof French) {
                BasicEnemy = (French) avion;
                SpawnFlyingEnemy(BasicEnemy);
            }
            if (avion instanceof British) {
                AdvancedEnemy = (British) avion;
                SpawnFlyingEnemy(avion);
            }
        }

    }

    private void renderExplosions(float delta) {
        batch.begin();
        for (int i = 0; i < explosions.size; i++) {
            Explosion explosion = explosions.get(i);
            explosion.update(delta);// on met a jour la position de l explosion
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
        // buffitem = new Heart(200, 200);
        // Dessiner la carte
        MapGeneration();

        // Si le jeu n'est pas terminé
        if (player.getGameOver() == false) {
            elapsedTime = TimeUtils.timeSinceMillis(startTime);// on recuperer le temps depuis le début de la partie
            // System.err.println("temps passe" + elapsedTime);
            // System.out.println("dernier spawn" + lastShellSpawnTime);
            this.update();
            // createFlyingEnemy();
            // System.err.println("taille" + FlyingEnemies.size);
            renderGamePlay();
            UpdateFlyingEnemy();
            UpdateSupportEnemy();
            handleEnemyCollision();
        }

        if (player.getGameOver() == true) {
            renderGameOver();
        }

        // Mettre à jour et dessiner les projectiles
        updateProjectiles(delta);
        drawProjectiles();
        // System.out.println(enemyProjectiles.size + "taille");
        // Dessiner les explosions
        if (!player.getGameOver()) {
            renderExplosions(delta);
        }
        if (game.getCurrentMap() == 1 && game.BasicEnemyDeath == BASIC_DEATH_REQ) {
            renderWin();
        }
        if (game.getCurrentMap() == 2 && game.AntiAirDeath == ANTI_AIR_REQ) {
            renderWin();
        }
    }

    private void renderGamePlay() {
        // Mettre à jour et dessiner le joueur
        // Heart coeur = new Heart(200, 200);
        batch.begin();
        player.update();
        handleBuffSpawn();
        player.draw(batch);
        player.drawHealth(batch);
        objective = game.getCurrentMap() != 2 ? ("French killed :" + game.BasicEnemyDeath)
                : ("Anti-Air destroyed :" + game.AntiAirDeath);
        if (game.getCurrentMap() == 3) {
            objective = "Boss Heakth :" + boss.getHp();
        }
        FontObjective.draw(batch, objective, 20, Gdx.graphics.getHeight() - 50);
        scoreFont.draw(batch, "Score :" + game.ScoreTotale, 20, Gdx.graphics.getHeight() - 30);
        if (BossSlevel && boss.getHp() > 0) {
            boss.update(Gdx.graphics.getDeltaTime(), player, enemyProjectiles);
            boss.draw(batch);
        }
        if (BossSlevel && boss.getHp() == 0) {
            game.jeuScreen.sonJeu.dispose();
            game.menu = new MenuScreen(game);
            game.menu.sonMenu.loop();
            game.setScreen(game.menu);
        }
        batch.end();

        checkPlayerCollisions();
        handlePlayerHealth();

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
            game.jeuScreen.sonJeu.dispose();
            game.overScreen = new GameOverScreen(game);// on va afficher l'ecran du gameover
            game.setScreen(game.overScreen);
            game.overScreen.sonDeath.play();
            try {
                // game.writer.write(score);
                writer.write(String.valueOf("\n" + score));// on convertit le score en chaine

                // writer.write("hah");
                writer.close();

            } catch (IOException e) {
                // System.out.println("e");
            }
            dispose();
        }

    }

    public void renderWin() {
        // game.jeuScreen.sonJeu.dispose();
        WinLevelScreen ecran = new WinLevelScreen(game);
        game.WinScreen = ecran;
        // score = score;
        game.setScreen(game.WinScreen);
        // score = 0;// temporairment on met le score a 0

        try {
            // game.writer.write(score);
            writer.write(String.valueOf("\n" + game.ScoreTotale));// on convertit le score en chaine

            // writer.write("hah");
            writer.close();

        } catch (IOException e) {
            // System.out.println("e");
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
    public void resize(int width, int height) {

        // Mettre à jour la région de texture pour l'image de fond

    }

    @Override
    public void show() {
        // start the playback of the background music when the screen is shown
        // rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}