package com.test.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.game.buffs.Buff;
import com.test.game.buffs.Heart;
import com.test.game.explosion.Explosion;
import com.test.game.graphics.Wall;
import com.test.game.graphics.WallParser;
import com.test.game.graphics.Zeppelin;
import com.test.game.planes.Artillery;
import com.test.game.planes.British;
import com.test.game.planes.French;
import com.test.game.planes.Plane;
import com.test.game.planes.Red;
import com.test.game.shoots.Bomb;
import com.test.game.shoots.Bullets;
import com.test.game.shoots.Projectile;

public class GameScreen implements Screen {
    // ecriture de fichier
    BufferedWriter writer;
    // Son
    private final float SOUND_EFFECTS_VOLUME = 0.01f;// (on caste en float sinon ca le lit comme un double),la valeur
                                                     // est un pourcentage donc 1=100% volume du son
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
    int mapWidth;
    private final String CARET1 = "Map/carte1.txt";// ces variables representent les niveaux a charger,pas les images de
                                                   // fond
    private final String CARET2 = "Map/carte2.txt";// a changer pour generer une nouvelle map
    private final String CARET3 = "Map/carte1.txt";
    // Défilement du fond
    private final int backgroundOffset;

    private final int WORLD_WIDTH = 800;
    private final int WORLD_HEIGHT = 480;

    private final float ENEMY_SPAWN_TIME = 0;
    private final float FRENCH_ENEMY_SPEED = 3;
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
    float lastEnemySpawnTime;
    float lastShellSpawnTime;
    long startTime;
    long elapsedTime;
    French BasicEnemy;
    British AdvancedEnemy;
    private final int ENEMY_SPAWN_LEVEL_X = Gdx.graphics.getWidth();
    private int ENEMY_SPAWN_LEVEL_Y = Gdx.graphics.getHeight() - 50;
    private final int FLYING_ENEMY_SPAWN_INTERVAL = 3000;
    private final int FIRE_SUPPORT_SPAWN = 4000;
    private final int SCORE_FOR_STAGE2 = 1;
    private final int SCORE_FOR_STAGE3 = 2;
    // Buff
    private final int BUFF_SPAWN_TIME = 5000;
    Buff buffitem;
    long lastBuffSpawnTime;
    // Constructeur de la classe

    public GameScreen(final Test game, int niveauCourrant) {
        // sonJeu = Gdx.audio.newSound(Gdx.files.internal(MUSIQUE1));
        if (game.getCurrentMap() == 1) {
            sonJeu = Gdx.audio.newSound(Gdx.files.internal(MUSIQUE1));
        }
        if (game.getCurrentMap() == 2) {
            sonJeu = Gdx.audio.newSound(Gdx.files.internal(MUSIQUE3));
        }
        if (game.getCurrentMap() == 3) {
            sonJeu = Gdx.audio.newSound(Gdx.files.internal(MUSIQUE2));
        }
        sonJeu.loop(SOUND_EFFECTS_VOLUME);// on lance la musique des que le jeu commence
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
        // objects = WallParser.parseWalls("Map/test.txt");
        player = new Red(PLAYER_START_LINE_X, PLAYER_START_LINE_Y);
        FlyingEnemies = new Array<>();
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
                if (button == Buttons.LEFT) {
                    createBullet();
                    // player.sonTire.play(SOUND_EFFECTS_VOLUME);
                    if (game.getScreen().getClass() == GameScreen.class) {// pour declencher l effet sonor on verifie si
                                                                          // on est bien dans un objet de class
                                                                          // GameScreen c a d on est bien entrain de
                                                                          // jouer et pas dans un menu
                        player.sonTire.play(SOUND_EFFECTS_VOLUME);
                    }
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

    public void update() {
        if (elapsedTime - lastEnemySpawnTime >= FLYING_ENEMY_SPAWN_INTERVAL) {// si la difference entre le temps
                                                                              // courrant et le temps du dernier
                                                                              // spawn est superieur au temps de
                                                                              // génération des enemies
            createFlyingEnemy();
            // System.err.println("elapsed time" + elapsedTime + "derneri spawn" +
            // lastEnemySpawnTime);

            // System.err.println("yes");
        }
        if (elapsedTime - lastShellSpawnTime >= FIRE_SUPPORT_SPAWN) {
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
            background = new Texture("BackGroundImages/montains03.jpg");
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

    public boolean checkEnemyCollision(Plane avion) {// on gere la collisoin des enemies seulment
        for (Object object : objects) {
            if (object instanceof Wall) {
                Wall mur = (Wall) object;
                if (avion.checkCollision(mur)) {
                    return true;
                }
            }

            if (object instanceof Zeppelin) {
                Zeppelin zepp = (Zeppelin) object;
                if (avion.checkCollision(zepp)) {
                    return true;
                }
            }

        }
        if (player.checkCollision(avion)) {
            return true;
        }
        return false;
    }

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
            if (player.collidesWithBuff(buffitem)) {
                if (buffitem instanceof Heart) {
                    player.setHp(player.getHp() + 1);
                    buffitem = null;
                }
            }
        }
        if (elapsedTime - lastBuffSpawnTime > BUFF_SPAWN_TIME && buffitem == null) {// il ne doit pas y avoir plus d un
                                                                                    // buff sur la map
            buffitem = new Heart(MathUtils.random(100, Gdx.graphics.getHeight() - 100),
                    MathUtils.random(100, Gdx.graphics.getHeight()) - 100);
            while (checkBuffSpawn(buffitem)) {
                buffitem = new Heart(MathUtils.random(100, Gdx.graphics.getHeight() - 100),
                        MathUtils.random(100, Gdx.graphics.getHeight()) - 100);
            }
            // System.err.println("haha");
            lastBuffSpawnTime = elapsedTime;
        }

    }

    // cette fonction va gerer les avoins lorsque ils se prennent des degats
    public void handleDamagedFlyingEnemy() {
        for (Projectile tire : projectiles) {
            for (Plane avion : FlyingEnemies) {
                if (avion.collidesWith(tire)) {
                    avion.setHp(avion.getHp() - 1);// on diminue la vie
                    tire.setHit(true);
                    if (avion.getHp() == 0) {
                        avion.setIsDeadByFireHit(true);
                        // System.err.println("mort par tire");
                        if (avion instanceof French) {
                            score += French.getScoreValue();
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
        for (Projectile tire : enemyProjectiles) {
            tire.draw(batch);
        }
        batch.end();

    }

    private void updateProjectiles(float delta) {
        for (int i = 0; i < projectiles.size; i++) {
            Projectile projectile = projectiles.get(i);
            projectile.update(delta);// on gere les projectiles du joueur
            if (projectile.isOutOfScreen() || projectile.getHit() == true) {
                projectiles.removeIndex(i);
            }
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

        }
        // on gere les projectiles enemies
        for (int i = 0; i < enemyProjectiles.size; i++) {
            Projectile tire = enemyProjectiles.get(i);
            if (tire instanceof Bullets) {
                Bullets balle = (Bullets) tire;
                balle.updateForEnemy(delta);
            }
            if (tire.isOutOfScreen()) {// le tire sort de l ecran
                enemyProjectiles.removeIndex(i);
                break;// on a fini le traitment de la balle donc on sort
            }
            if (player.collidesWith(tire)) {
                player.setHp(player.getHp() - 1);
                enemyProjectiles.removeIndex(i);
            }

        }

    }

    public void createFlyingEnemy()

    {

        ENEMY_SPAWN_LEVEL_Y = MathUtils.random(50, Gdx.graphics.getHeight() - 50);
        Plane avion = new French(Gdx.graphics.getWidth() - 30, ENEMY_SPAWN_LEVEL_Y, 40, 40, FRENCH_ENEMY_SPEED);
        while (checkEnemyCollision(avion)) {
            ENEMY_SPAWN_LEVEL_Y = MathUtils.random(100, Gdx.graphics.getHeight());
            avion = new French(Gdx.graphics.getWidth() - 30, ENEMY_SPAWN_LEVEL_Y, 40, 40, FRENCH_ENEMY_SPEED);
        }
        FlyingEnemies.add(avion);
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
        avion.draw(shape);
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
        renderExplosions(delta);
        if (score == 3) {
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
        scoreFont.draw(batch, "Score :" + score, 20, Gdx.graphics.getHeight() - 30);
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
                writer.write(String.valueOf(score + "\n"));// on convertit le score en chaine

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
        game.ScoreTotale += score;
        game.setScreen(game.WinScreen);
        // score = 0;// temporairment on met le score a 0

        try {
            // game.writer.write(score);
            writer.write(String.valueOf(game.ScoreTotale + "\n"));// on convertit le score en chaine

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