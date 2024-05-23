package com.test.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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

    /**
     * Met à jour l'état du jeu en générant des ennemis volants et des soutiens de feu
     * à des intervalles spécifiés.
     *
     * Cette méthode vérifie si le temps écoulé depuis le dernier spawn d'ennemi volant ou de soutien de feu
     * dépasse les intervalles de génération définis. Si les conditions sont remplies, elle génère de nouveaux ennemis.
     * La génération des ennemis est désactivée si le niveau du boss est actif.
     */
    public void update() {
        // Vérifie si le temps écoulé depuis le dernier spawn d'ennemi volant est supérieur à l'intervalle de spawn
        if (elapsedTime - lastEnemySpawnTime >= FLYING_ENEMY_SPAWN_INTERVAL) {
            // Si ce n'est pas le niveau du boss, crée un ennemi volant
            if (!BossSlevel) {
                createFlyingEnemy();
            }
        }

        // Vérifie si le temps écoulé depuis le dernier spawn de soutien de feu est supérieur à l'intervalle de spawn
        if (elapsedTime - lastShellSpawnTime >= FIRE_SUPPORT_SPAWN && !BossSlevel) {
            createSupportEnemy();
        }
    }


    /**
 * Génère et affiche la carte du jeu en fonction du niveau actuel.
 *
 * Cette méthode configure l'image de fond en fonction de la carte actuelle,
 * met à jour et dessine les objets du jeu. Elle est appelée à chaque frame pour
 * maintenir et afficher l'état courant du jeu.
 */
public void MapGeneration() {
    // Récupère le temps écoulé depuis la dernière frame
    float delta = Gdx.graphics.getDeltaTime();
    
    // Efface l'écran en le remplissant avec une couleur noire
    ScreenUtils.clear(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Commence le batch pour dessiner les textures
    batch.begin();
    
    // Sélectionne l'image de fond en fonction de la carte actuelle
    if (game.getCurrentMap() == 1) {
        background = new Texture("BackGroundImages/montains01.jpg"); // Image de fond pour la carte 1
    }
    if (game.getCurrentMap() == 2) {
        background = new Texture("BackGroundImages/montains02.png"); // Image de fond pour la carte 2
    }
    if (game.getCurrentMap() == 3) {
        background = new Texture("BackGroundImages/mapBoss.png"); // Image de fond pour la carte 3 (boss)
    }
    
    // Dessine l'image de fond sur l'écran
    batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    
    // Met à jour les objets du jeu avec le temps écoulé
    updateObjects(delta);
    
    // Dessine les objets du jeu
    drawObjects();

    // Termine le batch
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

    /**
 * Vérifie les collisions entre le joueur et les différents objets du jeu.
 *
 * Cette méthode parcourt les objets de la carte et les ennemis volants pour vérifier
 * s'il y a des collisions avec le joueur. En cas de collision, des actions spécifiques
 * sont effectuées, telles que la gestion des collisions, la réduction des points de vie
 * du joueur ou l'explosion des objets.
 */
private void checkPlayerCollisions() {
    // Vérifier les collisions entre le joueur et les murs
    for (Object object : objects) {
        if (!player.getGameOver()) {
            if (object instanceof Wall) {
                Wall wall = (Wall) object;
                // Vérifie la collision entre le joueur et un mur
                if (player.checkCollision(wall)) {
                    handlePlayerCollision();
                }
            } else if (object instanceof Zeppelin) {
                Zeppelin zeppelin = (Zeppelin) object;
                // Vérifie la collision entre le joueur et un Zeppelin
                if (player.checkCollision(zeppelin)) {
                    handlePlayerCollision();
                }
            }
            
            // Vérifie les collisions entre le joueur et les ennemis volants
            for (Plane avion : FlyingEnemies) {
                // Collision entraîne une mort instantanée
                if (player.checkCollision(avion)) {
                    handlePlayerCollision();
                }
            }
            
            // Vérifie les collisions entre le joueur et le soutien de feu
            for (int i = 0; i < Fire_Support.size; i++) {
                if (player.collidesWith(Fire_Support.get(i))) {
                    // Réduit les points de vie du joueur en cas de collision
                    player.setHp(player.getHp() - 1);
                    // Crée une explosion à la position du soutien de feu
                    explode(Fire_Support.get(i).getX(), Fire_Support.get(i).getY());
                    // Retire le soutien de feu après la collision
                    Fire_Support.removeIndex(i);
                }
            }
        }
    }
}


    /**
 * Vérifie s'il y a une collision entre un avion et les obstacles de la carte
 * lors du spawn.
 *
 * Cette méthode est utilisée pour détecter les collisions entre un avion
 * et les murs de la carte lors du processus de spawn de l'avion. Elle permet
 * de s'assurer que l'avion n'apparaît pas à l'intérieur ou en collision avec
 * des obstacles dès son apparition.
 *
 * @param avion L'avion à vérifier s'il entre en collision avec les murs lors du spawn.
 * @return true s'il y a une collision avec un mur ou avec le joueur, sinon false.
 */
public boolean checkCollisionSpawn(Plane avion) {
    // Parcours des objets de la carte pour vérifier les collisions lors du spawn
    for (Object object : objects) {
        // Vérification des collisions avec les murs de la carte
        if (object instanceof Wall) {
            Wall mur = (Wall) object;
            // Si l'avion entre en collision avec un mur, retourne true
            if (avion.checkCollision(mur)) {
                return true;
            }
        }
    }

    // Vérification de la collision avec le joueur
    if (player.checkCollision(avion)) {
        return true;
    }
    
    // Si aucune collision n'est détectée, retourne false
    return false;
}

    /**
 * Vérifie s'il y a une collision entre un avion ennemi et les obstacles de la carte.
 *
 * Cette méthode est utilisée pour détecter les collisions entre un avion ennemi
 * et les murs de la carte ainsi que le joueur. Elle prend en compte les caractéristiques
 * spécifiques des avions ennemis, tels que l'invulnérabilité des avions britanniques
 * aux obstacles de terrain.
 *
 * @param avion L'avion ennemi à vérifier s'il entre en collision avec les obstacles de la carte.
 * @return true s'il y a une collision avec un mur (pour les avions français), sinon false.
 */
public boolean checkEnemyCollision(Plane avion) {
    // Parcours des objets de la carte pour vérifier les collisions avec les murs
    for (Object object : objects) {
        // Vérification des collisions avec les murs de la carte
        if (object instanceof Wall) {
            Wall mur = (Wall) object;
            // Si l'avion entre en collision avec un mur
            if (avion.checkCollision(mur)) {
                // Pour les avions britanniques (British), ajuste la position en cas de collision avec un mur
                if (avion instanceof British) {
                    // Le British est invulnérable au terrain, ajuste sa position verticale
                    avion.setY(avion.getY() + 20);
                    // Aucune collision n'est rapportée
                    return false;
                }
                // Pour les avions français (French), rapporte la collision avec un mur
                if (avion instanceof French) {
                    return true;
                }
            }
        }
    }

    // Vérification de la collision avec le joueur
    if (player.checkCollision(avion)) {
        return true;
    }
    
    // Si aucune collision n'est détectée avec un mur ou le joueur, retourne false
    return false;
}


  
   /**
 * Vérifie s'il est possible de faire spawn un bonus à une certaine position sans collision avec les obstacles.
 *
 * Cette méthode est utilisée pour déterminer s'il est possible de faire spawn un bonus à une position donnée
 * sans qu'il y ait de collision avec les murs de la carte. Elle retourne true si la position spécifiée pour
 * le spawn du bonus est dégagée, sinon elle retourne false.
 *
 * @param buff Le bonus à vérifier pour le spawn.
 * @return true si la position spécifiée est dégagée pour le spawn du bonus, sinon false.
 */
public boolean checkBuffSpawn(Buff buff) {
    // Parcours des objets de la carte pour vérifier les collisions avec les murs
    for (Object objet : objects) {
        // Vérification des collisions avec les murs de la carte
        if (objet instanceof Wall) {
            Wall mur = (Wall) objet;
            // Si le bonus entre en collision avec un mur
            if (buff.collidesWith(mur)) {
                // La position spécifiée pour le spawn du bonus n'est pas dégagée, retourne false
                return false;
            }
        }
    }
    
    // La position spécifiée pour le spawn du bonus est dégagée, retourne true
    return true;
}

    /**
 * Gère le spawn et l'interaction des bonus (buffs) avec le joueur.
 *
 * Cette méthode est responsable du spawn aléatoire des bonus sur la carte, de leur affichage,
 * de leur mise à jour et de leur interaction avec le joueur. Elle prend également en charge la
 * vérification de la collision des bonus avec les obstacles de la carte et la gestion des effets
 * des différents types de bonus sur le joueur.
 */
public void handleBuffSpawn() {
    // Affichage et mise à jour du bonus actuel s'il existe
    if (buffitem != null) {
        buffitem.draw(batch);
        buffitem.update();
        
        // Vérification de la collision du joueur avec le bonus actuel
        if (player.collidesWithBuff(buffitem)) {
            // Si le joueur entre en collision avec le bonus actuel
            if (buffitem instanceof Heart) {
                // Si le bonus est un coeur, augmente les points de vie du joueur
                player.setHp(player.getHp() + 1);
                // Supprime le bonus après qu'il a été collecté
                buffitem = null;
            }
        }
    }
    
    // Supprime le bonus s'il sort de la carte
    if (buffitem != null && buffitem.getX() < 10) {
        buffitem = null;
    }
    
    // Vérifie si le temps écoulé depuis le dernier spawn de bonus dépasse le temps de spawn spécifié
    if (elapsedTime - lastBuffSpawnTime > BUFF_SPAWN_TIME && buffitem == null) {
        // Si le temps de spawn est écoulé et qu'il n'y a pas de bonus actuellement sur la carte
        // Génère un nouveau bonus de type "Heart" à une position aléatoire sur la carte
        buffitem = new Heart(MathUtils.random(100, 400), MathUtils.random(100, 400));
        
        // Vérifie si la position du nouveau bonus est dégagée des obstacles de la carte
        while (!checkBuffSpawn(buffitem)) {
            // Si la position n'est pas dégagée, génère une nouvelle position aléatoire pour le bonus
            buffitem = new Heart(MathUtils.random(100, 400), MathUtils.random(100, 400));
        }
        
        // Met à jour le temps du dernier spawn de bonus
        lastBuffSpawnTime = elapsedTime;
    }
}


   /**
 * Gère les dommages infligés aux avions ennemis par les projectiles.
 *
 * Cette méthode parcourt tous les projectiles présents sur la carte et vérifie s'ils entrent en collision
 * avec des avions ennemis. Si un avion ennemi est touché par un projectile, ses points de vie sont réduits.
 * Si les points de vie de l'avion ennemi atteignent 0, il est considéré comme détruit et des actions spécifiques
 * sont entreprises en fonction de son type.
 */
public void handleDamagedFlyingEnemy() {
    // Parcours de tous les projectiles sur la carte
    for (Projectile tire : projectiles) {
        // Parcours de tous les avions ennemis sur la carte
        for (Plane avion : FlyingEnemies) {
            // Vérification de la collision entre l'avion ennemi et le projectile, excluant les défenses aériennes
            if (avion.collidesWith(tire) && !(tire instanceof SolAir)) {
                // Réduction des points de vie de l'avion ennemi
                avion.setHp(avion.getHp() - 1);
                // Marquage du projectile comme touché
                tire.setHit(true);
                
                // Vérification si l'avion ennemi est détruit
                if (avion.getHp() == 0) {
                    // Actions spécifiques en fonction du type d'avion ennemi
                    if (avion instanceof French) {
                        // Si l'avion ennemi est de type French, ajoute son score à celui du jeu
                        game.ScoreTotale += French.getScoreValue();
                        // Incrémente le compteur de morts des ennemis basiques
                        game.BasicEnemyDeath++;
                    } else if (avion instanceof British) {
                        // Si l'avion ennemi est de type British, ajoute son score à celui du jeu
                        game.ScoreTotale += British.getScoreValue();
                    }
                }
            }
        }
    }
}


    /**
 * Gère la santé du joueur.
 *
 * Cette méthode vérifie si le joueur n'a plus de points de vie. Si tel est le cas,
 * elle déclenche une explosion au niveau de la position actuelle du joueur et marque
 * le jeu comme terminé.
 */
private void handlePlayerHealth() {
    // Vérification si le joueur n'a plus de points de vie
    if (player.getHp() == 0) {
        // Déclenche une explosion au niveau de la position actuelle du joueur
        explode(player.getX(), player.getY());
        // Marque le jeu comme terminé
        player.setGameOver(true);
    }
}

/**
 * Gère la collision du joueur avec les obstacles ou les ennemis.
 *
 * Cette méthode est appelée lorsqu'une collision est détectée entre le joueur et un obstacle
 * ou un ennemi. Elle déclenche une animation d'explosion au niveau de la position actuelle du joueur
 * et marque le jeu comme terminé.
 */
private void handlePlayerCollision() {
    // Produire l'animation d'explosion pour le joueur au niveau de sa position actuelle
    explode(player.getX(), player.getY());
    // Marquer le jeu comme terminé
    player.setGameOver(true);
}

/**
 * Gère la collision des avions ennemis avec les obstacles ou le joueur.
 *
 * Cette méthode parcourt tous les avions ennemis sur la carte et vérifie s'ils entrent en collision
 * avec des obstacles ou le joueur. Si une collision est détectée, elle déclenche une explosion au niveau
 * de la position de l'avion ennemi et met à jour son état pour indiquer qu'il a été détruit.
 */
public void handleEnemyCollision() {
    // Parcours de tous les avions ennemis sur la carte
    for (Plane avion : FlyingEnemies) {
        // Vérification de la collision avec les obstacles ou le joueur
        if (checkEnemyCollision(avion)) {
            // Déclenche une explosion au niveau de la position de l'avion ennemi
            explode(avion.getX(), avion.getY());
            // Met à jour l'état de l'avion ennemi pour indiquer qu'il a été détruit
            avion.setHp(0);
        }
    }
    // Gestion des collisions avec l'artillerie anti-aérienne
    handleAntiAirCollisions();
}

   /**
 * Gère les collisions entre les projectiles largués par les ennemis et les défenses anti-aériennes.
 *
 * Cette méthode parcourt tous les projectiles largués par les ennemis sur la carte et vérifie s'ils entrent
 * en collision avec les défenses anti-aériennes. Si une collision est détectée, elle déclenche une explosion
 * à la position de la défense anti-aérienne, met à jour le score du jeu en fonction de la valeur de l'anti-air,
 * et retire l'anti-air ainsi que le projectile de leurs listes respectives.
 */
private void handleAntiAirCollisions() {
    // Parcours de tous les projectiles largués par les ennemis sur la carte
    for (int i = 0; i < projectiles.size; i++) {
        Projectile projectile = projectiles.get(i);
        // Vérification si le projectile est une bombe
        if (projectile instanceof Bomb) {
            // Parcours de tous les objets sur la carte pour trouver les défenses anti-aériennes
            for (Object object : objects) {
                // Vérification si l'objet est une défense anti-aérienne
                if (object instanceof AntiAir) {
                    AntiAir antiAir = (AntiAir) object;
                    // Vérification de la collision entre l'anti-air et le projectile
                    if (antiAir.collidesWith(projectile)) {
                        // Création d'une explosion à la position de l'anti-air
                        explode(antiAir.getX(), antiAir.getY());
                        // Mise à jour du score du jeu en fonction de la valeur de l'anti-air
                        game.ScoreTotale += AntiAir.getScoreValue();
                        // Si la carte actuelle est la carte 2, incrémentation du compteur de décès des défenses anti-aériennes
                        if (game.getCurrentMap() == 2) {
                            game.AntiAirDeath++;
                        }
                        // Retrait de l'anti-air et du projectile de leurs listes respectives
                        objects.removeValue(antiAir, true);
                        projectiles.removeIndex(i);
                        i--; // Réajustement de l'index après suppression
                        break; // Sortie de la boucle pour ce projectile
                    }
                }
            }
        }
    }
}


    /**
 * Vérifie s'il y a une collision entre une bombe et un mur sur la carte.
 *
 * Cette méthode parcourt tous les murs de la carte et vérifie s'il y a une collision
 * entre la bombe spécifiée et l'un des murs. Si une collision est détectée, la méthode
 * renvoie true ; sinon, elle renvoie false.
 *
 * @param bomb La bombe pour laquelle vérifier la collision avec les murs.
 * @return true s'il y a une collision entre la bombe et un mur, sinon false.
 */
private boolean bombCollision(Bomb bomb) {
    // Parcours de tous les murs de la carte pour vérifier la collision avec la bombe
    for (Object object : objects) {
        if (object instanceof Wall) {
            Wall wall = (Wall) object;
            // Vérification si la bombe entre en collision avec le mur
            if (bomb.getX() + Bomb.WIDTH >= wall.getX() && bomb.getX() <= wall.getX() + Wall.WIDTH
                    && bomb.getY() + Bomb.HEIGHT >= wall.getY() && bomb.getY() <= wall.getY() + Wall.HEIGHT) {
                return true; // Collision détectée avec un mur
            }
        }
    }
    return false; // Aucune collision détectée avec un mur
}

/**
 * Crée une explosion à la position spécifiée sur la carte.
 *
 * Cette méthode crée une explosion à la position spécifiée avec les coordonnées
 * x et y en ajoutant une instance de l'explosion à la liste des explosions.
 *
 * @param x La coordonnée x de la position de l'explosion.
 * @param y La coordonnée y de la position de l'explosion.
 */
private void explode(float x, float y) {
    // Ajout d'une nouvelle instance d'explosion à la liste des explosions
    explosions.add(new Explosion(x, y, explosionAnimation));
}

   /**
 * Dessine tous les objets sur la carte.
 *
 * Cette méthode parcourt tous les objets sur la carte et les dessine sur le lot de rendu (batch)
 * associé. Elle prend en charge différents types d'objets tels que les murs, les zeppelins, les défenses
 * anti-aériennes, les buissons, les Panzers, les arbres et les rochers.
 */
private void drawObjects() {
    // Dessiner tous les objets, même si le jeu est terminé
    for (Object object : objects) {
        // Vérifier le type de l'objet et le dessiner en conséquence
        if (object instanceof Wall) {
            Wall wall = (Wall) object;
            wall.draw(batch); // Dessiner le mur
        } else if (object instanceof Zeppelin) {
            Zeppelin zep = (Zeppelin) object;
            zep.draw(batch); // Dessiner le zeppelin
        } else if (object instanceof AntiAir) {
            AntiAir air = (AntiAir) object;
            air.draw(batch); // Dessiner la défense anti-aérienne
        } else if (object instanceof Bush) {
            Bush bush = (Bush) object;
            bush.draw(batch); // Dessiner le buisson
        } else if (object instanceof Panzer) {
            Panzer panzer = (Panzer) object;
            panzer.draw(batch); // Dessiner le Panzer
        } else if (object instanceof Arbre) {
            Arbre arbre = (Arbre) object;
            arbre.draw(batch); // Dessiner l'arbre
        } else if (object instanceof Rock) {
            Rock rock = (Rock) object;
            rock.draw(batch); // Dessiner le rocher
        }
    }
}

   /**
 * Crée un nouveau projectile de type bullet à partir de la position actuelle du joueur.
 * 
 * Cette méthode crée un nouveau projectile de type bullet à partir de la position actuelle du joueur
 * en ajoutant une instance de la classe Bullets à la liste des projectiles.
 */
private void createBullet() {
    // Création d'un nouveau projectile bullet à partir de la position du joueur
    Bullets bullet = new Bullets(player.getX() + player.getWidth(), player.getY() + player.getHeight() / 2, 300f);
    projectiles.add(bullet); // Ajout du projectile à la liste des projectiles
}

/**
 * Crée un nouveau projectile de type bomb à partir de la position actuelle du joueur.
 * 
 * Cette méthode crée un nouveau projectile de type bomb à partir de la position actuelle du joueur
 * en ajoutant une instance de la classe Bomb à la liste des projectiles.
 */
private void createBomb() {
    // Création d'un nouveau projectile bomb à partir de la position du joueur
    Bomb bomb = new Bomb(player.getX() + player.getWidth() / 2, player.getY(), 200f);
    projectiles.add(bomb); // Ajout du projectile à la liste des projectiles
}

/**
 * Dessine tous les projectiles sur l'écran.
 * 
 * Cette méthode parcourt la liste des projectiles et dessine chacun d'eux sur le lot de rendu (batch).
 * Elle prend également en charge le dessin des projectiles ennemis.
 */
private void drawProjectiles() {
    batch.begin(); // Début du lot de rendu
    // Dessin de tous les projectiles du joueur
    for (Projectile projectile : projectiles) {
        projectile.draw(batch);
    }
    // Dessin de tous les projectiles ennemis
    for (Projectile tire : enemyProjectiles) {
        tire.draw(batch);
    }
    batch.end(); // Fin du lot de rendu
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

    /**
 * Crée un nouvel ennemi volant.
 * 
 * Cette méthode génère un nouvel ennemi volant en fonction de la carte actuelle du jeu. Sur la première carte, uniquement des ennemis de type French sont générés. Sur la deuxième carte, des ennemis de type French et British peuvent être générés en fonction d'un ratio défini. L'ennemi est créé avec des coordonnées aléatoires sur l'axe Y et ajouté à la liste des ennemis volants.
 */
public void createFlyingEnemy() {
    // Génération de coordonnées aléatoires sur l'axe Y pour le spawn de l'ennemi
    ENEMY_SPAWN_LEVEL_Y = MathUtils.random(50, Gdx.graphics.getHeight() - 50);
    
    // Génération de l'ennemi en fonction de la carte actuelle du jeu
    if (game.getCurrentMap() == 1) {
        // Sur la première carte, seulement des ennemis de type French sont générés
        enemyspawn = new French(Gdx.graphics.getWidth() - 30, ENEMY_SPAWN_LEVEL_Y, 40, 40,
                FRENCH_ENEMY_SPEED, TEXTURE_FRENCH);
    } else if (game.getCurrentMap() == 2) {
        // Sur la deuxième carte, des ennemis de type French et British peuvent être générés en fonction d'un ratio défini
        if (MathUtils.random(1, 100) > ENEMY_SPAWN_RATIO) {
            // Générer un ennemi de type French
            spawnType = 1;
            enemyspawn = new French(Gdx.graphics.getWidth() - 30, ENEMY_SPAWN_LEVEL_Y, 40, 40,
                    FRENCH_ENEMY_SPEED, TEXTURE_FRENCH);
        } else {
            // Générer un ennemi de type British
            spawnType = 2;
            enemyspawn = new British(Gdx.graphics.getWidth() - 30, ENEMY_SPAWN_LEVEL_Y, 40, 40,
                    BRITISH_ENEMY_SPEED, TEXTURE_BRITISH);
        }
    }
    
    // Vérification de collision avec d'autres ennemis ou des objets du décor
    while (checkCollisionSpawn(enemyspawn)) {
        // Regénération des coordonnées Y si une collision est détectée
        ENEMY_SPAWN_LEVEL_Y = MathUtils.random(100, Gdx.graphics.getHeight());
        if (spawnType == 1) {
            // Regénérer un ennemi de type French
            enemyspawn = new French(Gdx.graphics.getWidth() - 30, ENEMY_SPAWN_LEVEL_Y, 40, 40,
                    FRENCH_ENEMY_SPEED, TEXTURE_FRENCH);
        } else if (spawnType == 2) {
            // Regénérer un ennemi de type British
            enemyspawn = new British(Gdx.graphics.getWidth() - 30, ENEMY_SPAWN_LEVEL_Y, 40, 40,
                    BRITISH_ENEMY_SPEED, TEXTURE_BRITISH);
        }
    }
    
    // Ajout de l'ennemi à la liste des ennemis volants et mise à jour du temps de spawn
    FlyingEnemies.add(enemyspawn);
    lastEnemySpawnTime = elapsedTime;
}


    /**
 * Crée un nouvel ennemi de soutien (artillerie).
 * 
 * Cette méthode génère un nouvel ennemi de soutien (artillerie) avec des coordonnées aléatoires sur l'axe X et en haut de l'écran sur l'axe Y. L'ennemi est créé avec des dimensions spécifiées et une vitesse définie, puis ajouté à la liste des ennemis de soutien. Le temps de la dernière apparition de coquille est également mis à jour.
 */
public void createSupportEnemy() {
    Fire_Support.add(new Artillery(MathUtils.random(30, Gdx.graphics.getWidth() - 30), Gdx.graphics.getHeight(), 50,
            20, FIRE_SUPPORT_SPEED));
    lastShellSpawnTime = elapsedTime;
}

/**
 * Dessine un ennemi volant.
 * 
 * Cette méthode permet de dessiner un ennemi volant en utilisant la classe ShapeRenderer pour dessiner sa forme, puis en appelant la méthode de dessin de l'ennemi lui-même.
 * 
 * @param avion L'ennemi à dessiner.
 */
public void spawnFlyingEnemy(Plane avion) {
    shape.begin(ShapeRenderer.ShapeType.Filled);
    avion.update();
    avion.draw(batch);
    shape.end();
}

/**
 * Met à jour les ennemis de soutien.
 * 
 * Cette méthode met à jour la position de chaque ennemi de soutien dans la liste des ennemis de soutien. Si un ennemi de soutien sort de l'écran, il est supprimé de la liste. Ensuite, pour chaque ennemi de soutien restant, la méthode appelle la fonction de dessin appropriée.
 */
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

/**
 * Dessine un ennemi volant.
 * 
 * Cette méthode permet de dessiner un ennemi volant en utilisant le lot de rendu (batch). Elle appelle la méthode de dessin de l'ennemi pour chaque ennemi dans la liste des ennemis de soutien.
 * 
 * @param avion L'ennemi à dessiner.
 */
public void SpawnFlyingEnemy(Plane avion) {
    batch.begin();
    avion.draw(batch);
    batch.end();
}


    /**
 * Met à jour les ennemis volants.
 * 
 * Cette méthode gère les mouvements et l'état des ennemis volants. Elle parcourt la liste des ennemis volants et met à jour chaque ennemi. Si un ennemi est endommagé et atteint 0 points de vie, il est supprimé de la liste et une explosion est déclenchée à sa position. De plus, si un ennemi atteint la limite gauche de l'écran, il est également supprimé de la liste. Ensuite, la méthode parcourt à nouveau la liste pour afficher chaque ennemi volant à l'écran.
 */
public void UpdateFlyingEnemy() {
    // Gère les dégâts infligés aux ennemis volants
    handleDamagedFlyingEnemy();
    
    Iterator<Plane> iter = FlyingEnemies.iterator();
    while (iter.hasNext()) {
        Plane avion = iter.next();
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
        // Si l'ennemi atteint la limite gauche de l'écran, il est supprimé de la liste
        if (avion.getX() < 20) {
            iter.remove();
        }
        // Si l'ennemi est détruit (0 points de vie), il est supprimé de la liste et une explosion est déclenchée
        if (avion.getHp() == 0) {
            explode(avion.getX(), avion.getY());
            iter.remove();
        }
    }
    // Affiche chaque ennemi volant à l'écran
    for (Plane avion : FlyingEnemies) {
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


   /**
 * Affiche les explosions à l'écran.
 * 
 * Cette méthode parcourt la liste des explosions et les affiche à l'écran. Elle met également à jour la position de chaque explosion en fonction du temps écoulé depuis la dernière mise à jour. Si une explosion est terminée (c'est-à-dire si son animation est terminée), elle est supprimée de la liste.
 * 
 * @param delta Le temps écoulé depuis la dernière mise à jour, en secondes.
 */
private void renderExplosions(float delta) {
    batch.begin();
    for (int i = 0; i < explosions.size; i++) {
        Explosion explosion = explosions.get(i);
        // Met à jour la position de l'explosion en fonction du temps écoulé
        explosion.update(delta);
        // Affiche l'explosion à l'écran
        explosion.draw(batch);
        // Si l'explosion est terminée, elle est supprimée de la liste
        if (explosion.isFinished()) {
            explosions.removeIndex(i);
            i--; // Réajuster l'index après suppression
        }
    }
    batch.end();
}


    /**
 * Méthode de rendu principal appelée à chaque frame.
 * 
 * Efface l'écran, dessine la carte, met à jour le jeu en cours (si le jeu n'est pas terminé), rend le gameplay (y compris les ennemis et les objets), met à jour et dessine les projectiles, gère les collisions avec les ennemis, rend les explosions (si le jeu n'est pas terminé), et affiche l'écran de fin de partie si le joueur a perdu ou a accompli les conditions de victoire pour la carte actuelle.
 * 
 * @param delta Le temps écoulé depuis la dernière frame, en secondes.
 */
@Override
public void render(float delta) {
    // Effacer l'écran
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    // Dessiner la carte
    MapGeneration();

    // Si le jeu n'est pas terminé
    if (!player.getGameOver()) {
        elapsedTime = TimeUtils.timeSinceMillis(startTime);
        this.update();
        renderGamePlay();
        UpdateFlyingEnemy();
        UpdateSupportEnemy();
        handleEnemyCollision();
    }

    // Si le jeu est terminé
    if (player.getGameOver()) {
        renderGameOver();
    }

    // Mettre à jour et dessiner les projectiles
    updateProjectiles(delta);
    drawProjectiles();

    // Dessiner les explosions si le jeu n'est pas terminé
    if (!player.getGameOver()) {
        renderExplosions(delta);
    }

    // Afficher l'écran de victoire si les conditions sont remplies
    if (game.getCurrentMap() == 1 && game.BasicEnemyDeath == BASIC_DEATH_REQ) {
        renderWin();
    }
    if (game.getCurrentMap() == 2 && game.AntiAirDeath == ANTI_AIR_REQ) {
        renderWin();
    }
}


   /**
 * Méthode de rendu du gameplay principal.
 * 
 * Met à jour et dessine le joueur, gère la génération et l'affichage des buffs, affiche le score et l'objectif en cours (nombre d'ennemis tués ou d'anti-aériens détruits), met à jour et dessine le boss si présent sur la carte, et vérifie les collisions du joueur avec les différents éléments du jeu.
 */
private void renderGamePlay() {
    // Mettre à jour et dessiner le joueur
    batch.begin();
    player.update();
    handleBuffSpawn();
    player.draw(batch);
    player.drawHealth(batch);
    // Afficher l'objectif en cours
    objective = game.getCurrentMap() != 2 ? ("French killed :" + game.BasicEnemyDeath)
            : ("Anti-Air destroyed :" + game.AntiAirDeath);
    if (game.getCurrentMap() == 3) {
        objective = "Boss Health :" + boss.getHp();
    }
    FontObjective.draw(batch, objective, 20, Gdx.graphics.getHeight() - 50);
    scoreFont.draw(batch, "Score :" + game.ScoreTotale, 20, Gdx.graphics.getHeight() - 30);
    // Mettre à jour et dessiner le boss si présent sur la carte
    if (BossSlevel && boss.getHp() > 0) {
        boss.update(Gdx.graphics.getDeltaTime(), player, enemyProjectiles);
        boss.draw(batch);
    }
    // Si le boss est vaincu, retourner au menu principal
    if (BossSlevel && boss.getHp() == 0) {
        game.jeuScreen.sonJeu.dispose();
        game.menu = new MenuScreen(game);
        game.menu.sonMenu.loop();
        game.setScreen(game.menu);
    }
    batch.end();

    // Vérifier les collisions du joueur
    checkPlayerCollisions();
    // Vérifier la santé du joueur
    handlePlayerHealth();
}


    /**
 * Méthode de rendu de l'écran de fin de partie en cas de défaite.
 * 
 * Affiche une animation d'explosion centrée sur la position du joueur, puis affiche l'écran de fin de partie et enregistre le score dans un fichier. Une fois l'animation terminée, arrête la lecture de la musique de jeu et passe à l'écran de fin de partie.
 */
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
            writer.write(String.valueOf("\n" + score));// on convertit le score en chaine
            writer.close();
        } catch (IOException e) {
            // Gérer l'exception
        }
        dispose();
    }
}


    /**
 * Méthode de rendu de l'écran de victoire.
 * 
 * Affiche l'écran de victoire et enregistre le score dans un fichier. Passe à l'écran de victoire une fois l'enregistrement terminé.
 */
public void renderWin() {
    WinLevelScreen ecran = new WinLevelScreen(game);
    game.WinScreen = ecran;

    game.setScreen(game.WinScreen);

    try {
        writer.write(String.valueOf("\n" + game.ScoreTotale)); // on convertit le score en chaine
        writer.close();
    } catch (IOException e) {
        // Gérer l'exception
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