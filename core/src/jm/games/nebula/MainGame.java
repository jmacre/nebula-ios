package jm.games.nebula;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.FloatArray;

import com.badlogic.gdx.utils.Array;

import java.util.Random;

import static jm.games.nebula.Assets.bomb_sound;
import static jm.games.nebula.Assets.bullet_sound;
import static jm.games.nebula.Assets.gem_sound;
import static jm.games.nebula.Assets.hit_sound;
import static jm.games.nebula.Assets.main_theme;
import static jm.games.nebula.Assets.missile_sound;
import static jm.games.nebula.Assets.pause_sound;
import static jm.games.nebula.Assets.play_sound;
import static jm.games.nebula.EnemyBullet.ENEMY_BULLET_HEIGHT;
import static jm.games.nebula.EnemyBullet.blueShipBulletSpeed;
import static jm.games.nebula.EnemyBullet.greenShipBulletSpeed;
import static jm.games.nebula.EnemyBullet.purpleShipBulletSpeed;
import static jm.games.nebula.EnemyBullet.redShipBulletSpeed;
import static jm.games.nebula.EnemyBullet.whiteShipBulletSpeed;
import static jm.games.nebula.ItemDrop.GEM_ID;
import static jm.games.nebula.ItemDrop.HOURGLASS_TIMER;
import static jm.games.nebula.ItemDrop.MAX_GEM_SPAWN_TIME;
import static jm.games.nebula.ItemDrop.MIN_GEM_SPAWN_TIME;
import static jm.games.nebula.ItemDrop.MISSILE_TIMER;
import static jm.games.nebula.ItemDrop.RAPID_FIRE_TIMER;
import static jm.games.nebula.Anim.DEFAULT_FRAME_DURATION;
import static jm.games.nebula.Enemy.ENEMY_SHIP_ID;
import static jm.games.nebula.Enemy.EYEBAT_ID;
import static jm.games.nebula.Enemy.LASER_TRAP_ID;
import static jm.games.nebula.EnemyBullet.blueShipBulletThreshold;
import static jm.games.nebula.EnemyBullet.greenShipBulletThreshold;
import static jm.games.nebula.EnemyBullet.purpleShipBulletThreshold;
import static jm.games.nebula.EnemyBullet.redShipBulletThreshold;
import static jm.games.nebula.EnemyBullet.whiteShipBulletThreshold;
import static jm.games.nebula.ItemDrop.BOMB_ID;
import static jm.games.nebula.ItemDrop.HOURGLASS_ID;
import static jm.games.nebula.ItemDrop.RAPID_FIRE_ID;
import static jm.games.nebula.ItemDrop.HEART_ID;
import static jm.games.nebula.ItemDrop.MAX_ITEM_SPAWN_TIME;
import static jm.games.nebula.ItemDrop.MIN_ITEM_SPAWN_TIME;
import static jm.games.nebula.ItemDrop.MISSILE_ID;
import static jm.games.nebula.ItemDrop.SPREAD_FIRE_TIMER;
import static jm.games.nebula.ItemDrop.SPREAD_ID;
import static jm.games.nebula.ShopElement.BLACK_SHIP_ID;
import static jm.games.nebula.ShopElement.BLUE_SHIP_ID;
import static jm.games.nebula.ShopElement.BRED_SHIP_ID;
import static jm.games.nebula.ShopElement.CYAN_SHIP_ID;
import static jm.games.nebula.ShopElement.GREEN_SHIP_ID;
import static jm.games.nebula.ShopElement.ORANGE_SHIP_ID;
import static jm.games.nebula.ShopElement.PURPLE_SHIP_ID;
import static jm.games.nebula.ShopElement.RED_SHIP_ID;
import static jm.games.nebula.ShopElement.SHIP_ID;
import static jm.games.nebula.ShopElement.YELLOW_SHIP_ID;


public class MainGame extends GameElements implements Screen {
    public Prefs prefs = new Prefs();
    Assets assets;
    Background background;
    GlyphLayout gl;
    Array<Integer> shipPositions;
    int position;

    float maxGemSpawnTime = MAX_GEM_SPAWN_TIME;
    float minGemSpawnTime = MIN_GEM_SPAWN_TIME;

    float gemCountTimerDelay = GEM_COUNT_TIMER_DELAY;

    float shipMovementVal;

    int gemCount = 0;
    int gemsFromScore = 0;
    int gemsCaught = 0;
    int finalGemCount, finalGemCountTemp = 0;

    int replayScreenGemCount = prefs.getGemCount();
    boolean gemCountUpdated;

    BulletPool bp = new BulletPool();
    EnemyBulletPool ebp = new EnemyBulletPool();
    EnemyPool ep = new EnemyPool();
    ExplosionPool exp = new ExplosionPool();
    ItemDropPool idp = new ItemDropPool();

    boolean isAdLoaded;

    Vector2 center;
    FloatArray vertices;

    ShaderProgram invertedShader;

    float fadeInOpacity = 1;
    float fadeOutOpacity = 0;

    float totalTransitionDist = SHIP_Y + Math.abs(SHIP_START_Y);
    float transitionDistTraveled = 0f;

    float stateTime = 0f;

    float bulletTimer = -2f;
    float enemyBulletDelay = -0.5f;

    float missileTimer = MISSILE_TIMER;
    float rapidFireTimer = RAPID_FIRE_TIMER;
    float spreadFireTimer = SPREAD_FIRE_TIMER;
    float hourglassTimer = HOURGLASS_TIMER;

    float moveSpeed = 27f;

    Boolean hasConnection;

    float shipHitTimer = -2f;
    float shipBlinkingTimer = -0.2f;

    float hurtTimer = -0.15f;
    float hurtDelayTimer = -.03f;

    float explosionDelayTimer = -.025f;

    float countDownTimer = 0f;
    float resumeCountdownTimer = -1.5f;

    PowerupTimer powerUpTimer = new PowerupTimer();

    float bulletThreshold = 0f;
    float musicVolume = 0.35f;
    float musicVolumeTemp;

    float deltaP; // delta that doesn't update when isPaused/resume countdown

    float eyebatSpawnTimer;

    float enemyShipSpawnTimer;
    float laserTrapSpawnTimer;

    float itemSpawnTimer, gemSpawnTimer;

    boolean isUsingTextViewport;

    boolean isShipLeaving = false;
    boolean isTransitionedIn = false;

    boolean isTransitionedOut = false;
    boolean isTransitioningOut = false;
    boolean isRunningResumeCountdown = true;
    boolean runResumeCountdown = false;

    boolean eyebatsSpawning = false;
    boolean laserTrapsSpawning = false;
    boolean enemyShipsSpawning = false;

    float gemCountUpdateTimer = GEM_COUNT_UPDATE_TIMER;

    boolean gemCountStarted = false;
    boolean scoreCountStarted = false;
    boolean recapStarted = false;

    boolean gemCountComplete = false;
    boolean scoreCountComplete = false;
    boolean recapComplete = false;
    boolean recapSkipped = false;

    int gemSkipTapCount = 0, tapToContinueScreenTapCount = 0;

    boolean scoreUpdated = false;
    float scoreTickerTimer = SCORE_TICKER_TIMER;
    int finalScore = 0;

    boolean playHitSound = false;
    float hitSoundTimer = -.08f;

    boolean bombUsed = false;
    float bombUsedTimer = -.05f;

    boolean missileUsed = false;
    float missileUsedTimer = -.1f;

    boolean rapidFireUsed = false;
    float rapidFireUsedTimer = -.1f;

    boolean isFadingOut, isFadingIn = false;
    boolean justHit = false;
    boolean soundEnabled;
    boolean playerHitSoundPlayed = false;
    boolean isMainMusicPlaying = false;

    boolean isMissile = false;
    boolean isRapidFire = false;
    boolean isSpreadFire = false;

    boolean isHourglass = false;
    boolean soundLoaded;
    boolean isEnemyHurt;
    boolean isTransitioningIn = true;
    boolean newHighscore = false;

    public float minEyebatSpawnTime, maxEyebatSpawnTime, minEnemyShipSpawnTime, maxEnemyShipSpawnTime;
    public float minLaserSpawnTime, maxLaserSpawnTime;

    Random random;
    Main game;

    int score, prevScore;
    int randomDrop, randomEnemy, randomSpawnLocation;
    int lastItemDrop;
    int playButtonTapVal, yesButtonTapVal = 0, transitionInTapVal = 0;

    float musicPosition;

    GameInterface gameInterface;

    boolean isPaused = false;
    boolean isAlive = true;
    boolean isResettingScreen = false;
    boolean updatingScore;

    ShapeRenderer sr = new ShapeRenderer();

    MyInputProcessor inputProcessor = new MyInputProcessor();

    Array<Bullet> bullets = new Array<>();
    Array<Bullet> bulletsToRemove = new Array<>();

    Array<EnemyBullet> enemyBullets = new Array<>();
    Array<EnemyBullet> enemyBulletsToRemove = new Array<>();

    Array<Enemy> enemies = new Array<>();
    Array<Enemy> hurtEnemies = new Array<>();
    Array<Enemy> enemiesToRemove = new Array<>();
    Array<PowerupTimer> powerUpTimers = new Array<>();

    Array<Explosion> explosions = new Array<>();
    Array<Explosion> explosionsToDelay = new Array<>();
    Array<Explosion> explosionsToRemove = new Array<>();

    Array<ItemDrop> itemDrops = new Array<>();
    Array<ItemDrop> itemsToRemove = new Array<>();

    Array<Float> deltaList = new Array<>();
    float deltaSum;
    float speedIncrease;

    float hourglassMultiplier = 1;
    Sprite powerupTimerSheet;

    Player player;
    int health = 3;
    int selectedShip = 0;
    float refreshRate;

    Bullet bullet1, bullet2, bullet3, bullet4;

    public MainGame(Main game, GameInterface gameInterface, Assets assets, Background background) {
        this.game = game;
        this.gameInterface = gameInterface;
        this.background = background;
        this.assets = assets;
    }

    @Override
    public void show() {
        Enemy.createEnemySprites(assets);
        Explosion.createExplosionSprite(assets);
        PowerupTimer.createPowerupSprite(assets);

        minEnemyShipSpawnTime = MIN_ENEMY_SHIP_SPAWN_TIME;
        maxEnemyShipSpawnTime = MAX_ENEMY_SHIP_SPAWN_TIME;

        minEyebatSpawnTime = MIN_EYEBAT_SPAWN_TIME;
        maxEyebatSpawnTime = MAX_EYEBAT_SPAWN_TIME;

        minLaserSpawnTime = MIN_LASER_TRAP_SPAWN_TIME;
        maxLaserSpawnTime = MAX_LASER_TRAP_SPAWN_TIME;

        shipPositions = new Array<>();

        random = new Random();
        eyebatSpawnTimer = random.nextFloat() * (MAX_EYEBAT_SPAWN_TIME - MIN_EYEBAT_SPAWN_TIME) + MIN_EYEBAT_SPAWN_TIME;
        enemyShipSpawnTimer = random.nextFloat() * (MAX_ENEMY_SHIP_SPAWN_TIME - MIN_ENEMY_SHIP_SPAWN_TIME) + MIN_ENEMY_SHIP_SPAWN_TIME;
        laserTrapSpawnTimer = random.nextFloat() * (MAX_LASER_TRAP_SPAWN_TIME - MIN_LASER_TRAP_SPAWN_TIME) + MIN_LASER_TRAP_SPAWN_TIME;
        itemSpawnTimer = random.nextFloat() * (MAX_ITEM_SPAWN_TIME - MIN_ITEM_SPAWN_TIME) + MIN_ITEM_SPAWN_TIME;
        gemSpawnTimer = random.nextFloat() * (maxGemSpawnTime - minGemSpawnTime) + minGemSpawnTime;

        playSound = game.miniAudio.createSound(play_sound);
        playSound.setVolume(0.2f);

        pauseSound = game.miniAudio.createSound(pause_sound);
        pauseSound.setVolume(0.2f);

        mainMusic = game.miniAudio.createSound(main_theme);

        hitSound = game.miniAudio.createSound(hit_sound);
        hitSound.setVolume(0.3f);

        hitSound1 = game.miniAudio.createSound(hit_sound);
        hitSound1.setVolume(0.3f);

        bulletSound = game.miniAudio.createSound(bullet_sound);
        bulletSound.setVolume(0.05f);

        bulletSound1 = game.miniAudio.createSound(bullet_sound);
        bulletSound1.setVolume(0.05f);

        missileSound = game.miniAudio.createSound(missile_sound);
        missileSound.setVolume(0.1f);

        missileSound1 = game.miniAudio.createSound(missile_sound);
        missileSound1.setVolume(0.1f);

        itemSound = game.miniAudio.createSound(play_sound);
        itemSound.setVolume(0.075f);

        gemSound = game.miniAudio.createSound(gem_sound);
        gemSound.setVolume(0.1f);

        bombSound = game.miniAudio.createSound(bomb_sound);
        bombSound.setVolume(0.1f);

        soundEnabled = prefs.isSoundEnabled();

        blackTransition = new Sprite(assets.assetManager.get(Assets.black_transition, Texture.class));
        blackTransition.setSize(SCREEN_WIDTH * 1.5f, SCREEN_HEIGHT * 1.5f);

        whiteFlash = new Sprite(assets.assetManager.get(Assets.white_flash, Texture.class));
        whiteFlash.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        selectedShip = prefs.getShip();
        powerupTimerSheet = new Sprite(assets.assetManager.get(Assets.powerup_timer, Texture.class));

        getSelectedShip();

        shipAnimation = Anim.createAnimation(shipSS, 4, DEFAULT_FRAME_DURATION * 1.5f);

        textParameter.size = SCREEN_WIDTH / 40;
        menuScoreFont = generator.generateFont(textParameter);
        menuScoreFont.setColor(Color.valueOf(PURPLE_COLOR_HEX));

        gemCountFont = generator.generateFont(textParameter);
        gemCountFont.setColor(1, 1, 1, 0.8f);

        textParameter.size = SCREEN_WIDTH / 22;
        confirmScreenFont = generator.generateFont(textParameter);
        confirmScreenFont.setColor(Color.valueOf(PURPLE_COLOR_HEX));

        textParameter.size = SCREEN_WIDTH / 10;
        gameOverFont = generator.generateFont(textParameter);
        gameOverFont.setColor(Color.WHITE);

        textParameter.size = SCREEN_WIDTH / 6;
        countdownFont = generator.generateFont(textParameter);
        countdownFont.setColor(Color.WHITE);

        gl = new GlyphLayout();
        Gdx.input.setInputProcessor(inputProcessor);
        player = new Player();

        refreshRate = Gdx.graphics.getDisplayMode().refreshRate;

        ShaderProgram.pedantic = false;
        invertedShader = new ShaderProgram(Gdx.files.internal("shader/invert.vsh"), Gdx.files.internal("shader/invert.fsh"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);

        game.batch.enableBlending();
        game.batch.begin();

        deltaList.add(delta);

        if (isPaused || isRunningResumeCountdown) {
            deltaP = 0;
        }

        for (int i = 0; i < deltaList.size; i++) {
            deltaSum += deltaList.get(i);
        }
        delta = deltaSum / deltaList.size;
        if (isPaused || isRunningResumeCountdown) {
            deltaP = 0;
        } else {
            deltaP = delta;
        }
        deltaList.removeIndex(0);
        deltaSum = 0;


        if (fadeOutOpacity > 0 || fadeInOpacity > 0)
            blackTransition.draw(game.batch);
        if (!isPaused && !isShipLeaving) {
            updateSpawnRates(score);
        }

        if (!isMainMusicPlaying && soundEnabled && fadeInOpacity < 1)
            playMusic();

        if (!soundEnabled || isPaused || !isAlive || isRunningResumeCountdown) {
            musicVolume = 0;
            isMainMusicPlaying = false;
        }
        isAlive = health > 0;


        if (fadeInOpacity < 1) {
            musicPosition = mainMusic.getCursorPosition();
            background.updateAndRender(deltaP, isAlive, isHourglass, score, starsAnimFront, starsAnimBack, game.batch, false, isResettingScreen, false, false);
            if (!isRunningResumeCountdown) {
                bulletTimer += deltaP;
                for (Enemy enemy : enemies) {
                    if (enemy.getId() == ENEMY_SHIP_ID) {
                        enemy.setEnemyBulletTimer(enemy.getEnemyBulletTimer() + deltaP * hourglassMultiplier);
                    }
                }

                if (isAlive) {
                    enemyDamaged();
                    runBulletTimers(); //Adds bullets/bullet sounds
                }
            }
        }

        if (!isShipLeaving && isAlive) {
            updateBullets(game);
            updateEnemyBullets(game);
            updateEnemies();
            updateExplosions();
            updateItems();
        }

        if (!soundLoaded) {
            soundLoaded = true;
        }

        if (!isShipLeaving) {
            drawMainElements();
        }
        if (fadeInOpacity > 0) {
            fadeIn();
        } else if (fadeInOpacity <= 0) {
            isFadingIn = false;
        }
        if (!isRunningResumeCountdown) {
            if (isTransitionedIn && isAlive) {

                runScoreTickerTimer();

                addEyebats();
                eyebatsSpawning = true;

                if (score >= 100) {
                    addEnemyShips();
                    enemyShipsSpawning = true;
                }


                if (score >= 2000) {
                    addLaserTraps();
                    laserTrapsSpawning = true;
                }

            }

            if (isAlive) {
                if (score >= 100 && !isTransitioningIn) {
                    addGemDrops();
                    addItemDrops();
                    playerItemCollision();
                }

                playerEnemyCollision();

                if (enemyShipsSpawning && enemyBullets.size > 0)
                    playerBulletCollision();

                if (!isRunningResumeCountdown)
                    player.update();
            }

            if (isAlive && !isTransitioningOut && !isTransitionedOut) {
                transitionIn();
            }

            if (justHit) {
                runShipHitTimer();
            }

            if (missileUsed) {
                runMissileUsedTimer();
            }

            if (isMissile) {
                runMissileTimer();
            } else if (isRapidFire) {
                runRapidFireTimer();
            } else if (isHourglass) {
                runHourglassTimer();
            } else if (rapidFireUsed) {
                runRapidFireUsedTimer();
            } else if (isSpreadFire) {
                runSpreadFireTimer();
            } else if (bombUsed) {
                runBombUsedTimer();
            }

            if (!hurtEnemies.isEmpty()) {
                for (Enemy enemy : hurtEnemies) {
                    runEnemyHurtDelay(enemy);
                }
            }
            if (!explosionsToDelay.isEmpty()) {
                for (Explosion explosion : explosionsToDelay) {
                    runExplosionDelay(explosion);
                }
            }

            if (isEnemyHurt) {
                enemyHurt();
            }

            //Enables ship movement
            if (!isShipLeaving && isAlive && inputProcessor.getTapCount() != playButtonTapVal && (inputProcessor.getTapCount() != yesButtonTapVal) && (inputProcessor.getTapCount() > transitionInTapVal || Gdx.input.isTouched())) {
                movePlayer();
            }
        }
        if ((!isAlive || isPaused || gameInterface.isReplayScreenOpen) && inputProcessor.getTapCount() > tapToContinueScreenTapCount && gameInterface.checkForSoundButtonTap(soundEnabled, isAlive) && !gameInterface.isConfirmLeaveScreenOpen()) {
            if (prefs.isSoundEnabled()) {
                prefs.setSound(false);
                soundEnabled = false;
            } else {
                prefs.setSound(true);
                soundEnabled = true;
            }
        }

        //Pausing (pauses music/freezes delta)
        if (isTransitionedIn && !isPaused && !isTransitioningOut && !isRunningResumeCountdown && gameInterface.checkForPauseButtonTap()) {
            if (soundEnabled) {
                pauseSound.play();
            }

            isPaused = true;
        }

        if (isPaused && !isRunningResumeCountdown || gameInterface.isReplayScreenOpen) {
            resetShader();

            if (!gameInterface.isReplayScreenOpen && !gameInterface.isConfirmLeaveScreenOpen()) {
                if (!isAdLoaded) {
                    gameInterface.drawPauseScreen(game, menuScoreFont, gemCountFont, prefs);
                }

                if (gameInterface.checkForPlayButtonTap(soundEnabled) && !isTransitioningOut) {
                    playButtonTapVal = inputProcessor.getTapCount();

                    if (!isFadingIn && !isTransitioningIn) {
                        runResumeCountdown = true;
                    }
                    isPaused = false;

                }
            }
            if (gameInterface.checkForHomeButtonTap(soundEnabled) || gameInterface.isConfirmLeaveScreenOpen()) {
                if (!gameInterface.isConfirmLeaveScreenOpen()) {
                    gameInterface.setConfirmLeaveScreenOpen(true);

                }
                if (!isFadingOut) {
                    gameInterface.drawConfirmLeave(game, confirmScreenFont);
                }

                if (gameInterface.checkForYesButtonTap()) {
                    yesButtonTapVal = inputProcessor.getTapCount();

                    if (soundEnabled) {
                        playSound.play();

                    }
                    isShipLeaving = true;
                }
                if (gameInterface.checkForNoButtonTap(soundEnabled)) {
                    gameInterface.setConfirmLeaveScreenOpen(false);
                }

            }
            if (isHourglass) {
                game.batch.setShader(invertedShader);
            }
        }
        if (runResumeCountdown) {
            runResumeCountdown(delta);
            isRunningResumeCountdown = true;
        } else {
            isRunningResumeCountdown = false;
            resumeCountdownTimer = -1.5f;
        }

        if (!isAlive) {
            resetShader();

            if (inputProcessor.getTapCount() == 0) {
                transitionOut(SHIP_X);
            } else {
                transitionOut(CURRENT_SHIP_X);
            }
            if (isTransitionedOut && !isFadingOut) {

                if (!recapComplete) {

                    if (Gdx.input.justTouched()) {
                        gemCountStarted = true;
                        scoreCountStarted = true;
                        recapStarted = true;

                        scoreCountComplete = true;
                        gemCountComplete = true;
                        recapComplete = true;

                        gemSkipTapCount = inputProcessor.getTapCount();
                        replayScreenGemCount = prefs.getGemCount();
                        gemsFromScore = (int) Math.floor(finalScore / 100f);
                        gemsCaught = finalGemCountTemp - gemsFromScore;

                        gemCount = 0;
                        finalGemCount = 0;
                        score = 0;

                    } else {
                        runGemCountUpdateTimer();
                    }
                }
                if (!gameInterface.isConfirmLeaveScreenOpen() && !gameInterface.isContinueScreenOpen() && !isAdLoaded) {
                    gameInterface.drawReplayScreen(game, menuScoreFont, gameOverFont, gemCountFont, newHighscore, replayScreenGemCount, gemsFromScore, gemsCaught, gemCountStarted, finalScore, prefs.getHighScore(), scoreCountStarted, recapStarted, recapComplete, deltaP);
                }
                if (recapComplete && gameInterface.isRecapScreenOpen) {
                    if (Gdx.input.justTouched() && inputProcessor.getTapCount() != gemSkipTapCount) {
                        tapToContinueScreenTapCount = inputProcessor.getTapCount();
                    }

                    if (tapToContinueScreenTapCount != 0) {
                        gameInterface.isRecapScreenOpen = false;
                        gameInterface.isReplayScreenOpen = true;

                        if (soundEnabled) {
                            playSound.play();
                        }
                    }
                }
            }

            if (gameInterface.isReplayScreenOpen && inputProcessor.getTapCount() > tapToContinueScreenTapCount && isTransitionedOut) {
                if (gameInterface.checkForReplayButtonTap()) {
                    resetScreen();
                }
            } else if (isTransitionedOut) {
                if (!gameInterface.isRecapScreenOpen && inputProcessor.getTapCount() > tapToContinueScreenTapCount || gameInterface.isConfirmLeaveScreenOpen()) {
                    if (!gameInterface.isConfirmLeaveScreenOpen()) {

                        gameInterface.setConfirmLeaveScreenOpen(true);
                        if (soundEnabled) {
                            pauseSound.play();
                        }
                    }

                    if (!isFadingOut) {

                        gameInterface.drawConfirmLeave(game, confirmScreenFont);

                        if (gameInterface.checkForYesButtonTap()) {
                            yesButtonTapVal = inputProcessor.getTapCount();

                            if (soundEnabled) {
                                playSound.play();
                            }
                            isShipLeaving = true;
                        }
                        if (gameInterface.checkForNoButtonTap(soundEnabled)) {
                            gameInterface.setConfirmLeaveScreenOpen(false);
                        }

                    }
                }
            }
        }

        if (isShipLeaving) {
            resetShader();

            isPaused = false;
            transitionOut(CURRENT_SHIP_X);
            fadeOut();
        }
        if (isMissile || isRapidFire || isHourglass || isSpreadFire) {
            updatePowerUpTimer();
        }

        game.batch.end();
    }

    public void updateSpawnRates(int score) {
        if (score != prevScore) {
            updateGemSpawnTimes();

            float scoreMultiplier = score - prevScore;
            if (score < 3000 || score > 5000 && score < 6000) {
                if (eyebatsSpawning) {
                    maxEyebatSpawnTime -= (.0003 * scoreMultiplier) / 5f;
                    minEyebatSpawnTime -= (.00015 * scoreMultiplier) / 5f;
                }
                if (enemyShipsSpawning) {
                    maxEnemyShipSpawnTime -= (.0003 * scoreMultiplier) / 5f;
                    minEnemyShipSpawnTime -= (.00015 * scoreMultiplier) / 5f;
                }
                if (laserTrapsSpawning) {
                    maxLaserSpawnTime -= (.001 * scoreMultiplier) / 5f;
                    minLaserSpawnTime -= (.0005 * scoreMultiplier) / 5f;
                }
                if (speedIncrease < 1) {
                    speedIncrease += (0.0015 * scoreMultiplier) / 5f;
                }
            } else if (score >= 6000 && score < 20000) {
                if (eyebatsSpawning) {
                    maxEyebatSpawnTime -= (.0001 * scoreMultiplier) / 5f;
                    minEyebatSpawnTime -= (.00005 * scoreMultiplier) / 5f;
                }
                if (enemyShipsSpawning) {
                    maxEnemyShipSpawnTime -= (.0001 * scoreMultiplier) / 5f;
                    minEnemyShipSpawnTime -= (.00005 * scoreMultiplier) / 5f;
                }
                if (laserTrapsSpawning) {
                    maxLaserSpawnTime -= (.0003 * scoreMultiplier) / 5f;
                    minLaserSpawnTime -= (.00015 * scoreMultiplier) / 5f;
                }
                if (speedIncrease < 1) {
                    speedIncrease += (0.0005 * scoreMultiplier) / 5f;
                }
            } else if (score < 10000) {
                if (eyebatsSpawning) {
                    maxEyebatSpawnTime -= (.0002 * scoreMultiplier) / 5f;
                    minEyebatSpawnTime -= (.0001 * scoreMultiplier) / 5f;
                }
                if (enemyShipsSpawning) {
                    maxEnemyShipSpawnTime -= (.0002 * scoreMultiplier) / 5f;
                    minEnemyShipSpawnTime -= (.0001 * scoreMultiplier) / 5f;
                }
                if (laserTrapsSpawning) {
                    maxLaserSpawnTime -= (.00075 * scoreMultiplier) / 5f;
                    minLaserSpawnTime -= (.00035 * scoreMultiplier) / 5f;
                }
                if (speedIncrease < 1) {
                    speedIncrease += (0.001 * scoreMultiplier) / 5f;
                }
            }
            prevScore = score;
        }
    }

    public void enemyHurt() {
        for (Enemy enemy : enemies) {
            if (enemy.isHurt()) {
                if (enemy.getHurtTimer() <= 0) {
                    enemy.setHurtTimer(enemy.getHurtTimer() + deltaP);
                } else {
                    enemy.setEnemyHurt(false);
                    enemy.setHurtTimer(hurtTimer);
                }
            }
        }
    }


    public void resetGemCounts() {
        finalGemCount = 0;
        finalGemCountTemp = 0;
        gemCount = 0;
        gemsFromScore = 0;
        gemsCaught = 0;
    }

    public void stopSounds() {
        isMainMusicPlaying = false;

        hitSound.stop();
        hitSound1.stop();
        bulletSound.stop();
        bulletSound1.stop();
        missileSound.stop();
        missileSound1.stop();
        playSound.stop();
        pauseSound.stop();
        gemSound.stop();
    }

    public void resetShader() {
        if (game.batch.getShader() != null)
            game.batch.setShader(null);
    }

    public void resetRecapVariables() {
        gemCountStarted = false;
        scoreCountStarted = false;
        recapStarted = false;

        gemCountComplete = false;
        scoreCountComplete = false;
        recapComplete = false;
        recapSkipped = false;

        gameInterface.isRecapScreenOpen = true;
        gameInterface.isReplayScreenOpen = false;
    }

    public void resetSpawnTimes() {
        minEnemyShipSpawnTime = MIN_ENEMY_SHIP_SPAWN_TIME;
        maxEnemyShipSpawnTime = MAX_ENEMY_SHIP_SPAWN_TIME;

        minEyebatSpawnTime = MIN_EYEBAT_SPAWN_TIME;
        maxEyebatSpawnTime = MAX_EYEBAT_SPAWN_TIME;

        minLaserSpawnTime = MIN_LASER_TRAP_SPAWN_TIME;
        maxLaserSpawnTime = MAX_LASER_TRAP_SPAWN_TIME;

        minGemSpawnTime = MIN_GEM_SPAWN_TIME;
        maxGemSpawnTime = MAX_GEM_SPAWN_TIME;
    }

    public void resetItems() {
        itemSpawnTimer = random.nextFloat() * (MAX_ITEM_SPAWN_TIME - MIN_ITEM_SPAWN_TIME) + MIN_ITEM_SPAWN_TIME;
        gemSpawnTimer = random.nextFloat() * (MAX_GEM_SPAWN_TIME - MIN_GEM_SPAWN_TIME) + MIN_GEM_SPAWN_TIME;

        bombUsed = false;
        missileUsed = false;
        rapidFireUsed = false;

        rapidFireTimer = RAPID_FIRE_TIMER;
        missileTimer = MISSILE_TIMER;
        spreadFireTimer = SPREAD_FIRE_TIMER;
        hourglassTimer = HOURGLASS_TIMER;
    }

    public void resetBulletTimers() {
        bulletTimer = -2f;
        bulletThreshold = 0;
    }

    public void resetPlayer(int hp) {
        SHIP_X = (int) (SCREEN_WIDTH / 2 - SHIP_WIDTH / 2);
        CURRENT_SHIP_X = SHIP_X;
        health = hp;

        isAlive = true;
        justHit = false;
        isTransitionedIn = false;
        isTransitionedOut = false;
        isTransitioningOut = false;
        isTransitioningIn = true;
        isMissile = false;
        isRapidFire = false;
        isSpreadFire = false;
        isHourglass = false;
        transitionDistTraveled = 0f;
        isFadingOut = false;
        isFadingIn = false;

    }

    public void resetPools() {
        bp.clear();
        ep.clear();
        exp.clear();
        ebp.clear();
        idp.clear();
    }

    public void resetEnemySpawnVariables() {
        speedIncrease = 0f;

        laserTrapsSpawning = false;
        eyebatsSpawning = false;
        enemyShipsSpawning = false;
    }

    public void resetMainLists() {
        if (bullets.size > 0) {
            bullets.clear();
        }

        if (bulletsToRemove.size > 0) {
            bulletsToRemove.clear();
        }
        if (explosions.size > 0)
            explosions.clear();

        if (enemies.size > 0)
            enemies.clear();

        if (enemiesToRemove.size > 0)
            enemiesToRemove.clear();

        if (powerUpTimers.size > 0)
            powerUpTimers.clear();

        if (itemDrops.size > 0)
            itemDrops.clear();

        if (enemyBullets.size > 0)
            enemyBullets.clear();

        if (enemyBulletsToRemove.size > 0) {
            enemyBulletsToRemove.clear();
        }

        if (shipPositions.size > 0)
            shipPositions.clear();

    }

    public void resetHourglassMultiplier() {
        hourglassMultiplier = 1;
    }

    public void resetScoreVariables() {
        score = 0;
        finalScore = 0;
        prevScore = 0;
        newHighscore = false;
    }

    public void resetInterface() {
        replayScreenGemCount = prefs.getGemCount();
        tapToContinueScreenTapCount = 0;
        gemCountUpdated = false;
        countDownTimer = 0f;

        gameInterface.tapToContinueBlinkingTimer = TAP_TO_CONTINUE_BLINKING_TIMER * 2;
        gemCountTimerDelay = GEM_COUNT_TIMER_DELAY;

        isResettingScreen = false;
    }

    public void resetMusicAndSoundPitches() {
        mainMusic.setPitch(1);
        bulletSound.setPitch(1);
    }

    public void resetScreen() {
        resetMusicAndSoundPitches();
        stopSounds();
        resetGemCounts();
        resetShader();
        resetRecapVariables();
        resetSpawnTimes();
        resetItems();
        resetPlayer(3);
        resetEnemySpawnVariables();
        resetPools();
        resetMainLists();
        resetHourglassMultiplier();
        resetScoreVariables();
        resetBulletTimers();
        resetInterface();
    }

    public void playMusic() {
        if (soundEnabled && !isPaused && isAlive && !isTransitioningOut && !isRunningResumeCountdown) {
            musicVolume = 0.35f;
            mainMusic.play();

        } else if (soundEnabled && !isAlive && !isPaused && isTransitioningOut) {
            musicVolume = 0f;
        }
        if (!mainMusic.isLooping()) {
            mainMusic.setLooping(true);
        }

        if (!isPaused && !isTransitioningOut && !isMainMusicPlaying && !isRunningResumeCountdown) {
            mainMusic.play();
        }
        if (musicVolume != musicVolumeTemp) {
            mainMusic.setVolume(musicVolume);
            musicVolumeTemp = musicVolume;
        }

        isMainMusicPlaying = true;
    }

    public void movePlayer() {
        if (!isPaused && isTransitionedIn && !isShipLeaving && !isFadingOut && !isTransitioningOut) {
            if (!(Gdx.input.getY() < (int) (SCREEN_HEIGHT / 5f)) && SHIP_START_Y > SHIP_Y) {
                shipMovementVal = (deltaP * moveSpeed * (Gdx.input.getX() - SHIP_X - SHIP_WIDTH / 2f));
                if (SHIP_X + shipMovementVal > SCREEN_WIDTH - SHIP_WIDTH) {
                    SHIP_X = SCREEN_WIDTH - SHIP_WIDTH;
                } else if (SHIP_X + shipMovementVal < 0) {
                    SHIP_X = 0;
                } else {
                    SHIP_X += (int) shipMovementVal;
                }

                GameElements.CURRENT_SHIP_X = SHIP_X;
            }
        }
    }

    public void runResumeCountdown(float delta) {
        if (!gameInterface.isReplayScreenOpen && !gameInterface.isPauseScreenOpen) {
            resumeCountdownTimer += delta;
            if (resumeCountdownTimer >= -1) {
                if (resumeCountdownTimer < -0.66f) {
                    gl.setText(countdownFont, "3");
                    countdownFont.draw(game.batch, "3", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2f + gl.height);

                } else if (resumeCountdownTimer <= -0.33) {
                    gl.setText(countdownFont, "2", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                    countdownFont.draw(game.batch, "2", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2f + gl.height);

                } else if (resumeCountdownTimer < 0) {
                    gl.setText(countdownFont, "1", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                    countdownFont.draw(game.batch, "1", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2f + gl.height);

                } else {
                    if (resumeCountdownTimer < 0.25f) {
                        gl.setText(countdownFont, "GO", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                        countdownFont.draw(game.batch, "GO", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2f + gl.height);

                    } else {
                        runResumeCountdown = false;
                        resumeCountdownTimer = -1.5f;
                    }
                }
            }
        }
    }

    public void resetInputProcessorTaps() {
        gemSkipTapCount = 0;
        tapToContinueScreenTapCount = 0;
    }

    public void transitionIn() {
        hasConnection = null;
        resetInputProcessorTaps();
        if (!isTransitionedIn) {
            stopSounds();
        }

        if (SHIP_START_Y < -3 * SHIP_HEIGHT) {
            SHIP_START_Y = -3 * SHIP_HEIGHT;
        }

        if (SHIP_START_Y <= SHIP_Y) {
            SHIP_X = (int) (SCREEN_WIDTH / 2 - SHIP_WIDTH / 2);
            SHIP_START_Y += (int) (1.6 * SHIP_Y * deltaP);
            transitionDistTraveled += 1.6 * SHIP_Y * deltaP;
            transitionInTapVal = inputProcessor.getTapCount();
        }

        if (fadeInOpacity < 1 && !gameInterface.isReplayScreenOpen && !gameInterface.isPauseScreenOpen) {
            if (transitionDistTraveled <= totalTransitionDist / 3) {
                gl.setText(countdownFont, "3", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                countdownFont.draw(game.batch, "3", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2f + gl.height / 2);
            } else if (transitionDistTraveled <= 2 * totalTransitionDist / 3) {
                gl.setText(countdownFont, "2", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                countdownFont.draw(game.batch, "2", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2f + gl.height / 2);
            } else if (transitionDistTraveled < totalTransitionDist) {
                gl.setText(countdownFont, "1", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                countdownFont.draw(game.batch, "1", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2f + gl.height / 2);
            } else if (transitionDistTraveled >= totalTransitionDist && SHIP_START_Y >= SHIP_Y) {

                if (countDownTimer < 0.25f) {
                    countDownTimer += deltaP;
                    gl.setText(countdownFont, "GO", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                    countdownFont.draw(game.batch, "GO", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2f + gl.height / 2);
                }
                isTransitionedIn = true;
                isTransitioningIn = false;
            } else {
                isTransitionedIn = true;
                isTransitioningIn = false;
            }
        }
    }

    public void transitionOut(float SHIP_X_TRANSITION_OUT) {
        mainMusic.stop();
        resetHourglassMultiplier();

        if (SHIP_START_Y > -3 * SHIP_HEIGHT) {
            isTransitionedOut = false;
            isTransitioningOut = true;
        }
        if (!isTransitionedOut && !gameInterface.isContinueScreenOpen()) {
            SHIP_START_Y -= 1.6 * SHIP_Y * deltaP;
        }

        shipAnim.drawAnim(shipAnimation, stateTime, SHIP_X_TRANSITION_OUT, SHIP_START_Y, SHIP_WIDTH, SHIP_HEIGHT, true, game.batch, true);

        if (SHIP_START_Y <= -3 * SHIP_HEIGHT) {
            SHIP_START_Y = -3 * SHIP_HEIGHT;

            if (!isTransitionedOut) {
                Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
                request.setUrl("https://www.google.com/");

                Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
                    @Override
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        if (httpResponse.getResult() != null) {
                            hasConnection = true;
                        }
                    }

                    @Override
                    public void failed(Throwable t) {
                        hasConnection = false;
                    }

                    @Override
                    public void cancelled() {
                        hasConnection = false;
                    }
                });
                if (hasConnection != null && hasConnection && !isAdLoaded) {
                    gameInterface.drawContinueScreen(game, confirmScreenFont);
                } else if (hasConnection != null) {
                    gameInterface.setContinueScreenOpen(false);
                    isTransitionedOut = true;

                    if (finalScore == 0) {
                        finalScore = score;
                    }
                    isResettingScreen = true;
                    finalGemCount = gemCount;
                    if (score >= 100) {
                        finalGemCount = gemCount + (score / 100);
                    }
                    if (!gemCountUpdated) {
                        prefs.setGemCount(prefs.getGemCount() + finalGemCount);
                        finalGemCountTemp = finalGemCount;
                        gemCountUpdated = true;
                    }
                }
            }
            if (gameInterface.isContinueScreenOpen() && gameInterface.checkForNoButtonTap(soundEnabled)) {
                gameInterface.setContinueScreenOpen(false);
                isTransitionedOut = true;
                hasConnection = null;

                if (finalScore == 0) {
                    finalScore = score;
                }
                isResettingScreen = true;
                finalGemCount = gemCount;
                if (score >= 100) {
                    finalGemCount = gemCount + (score / 100);
                }
                if (!gemCountUpdated) {
                    prefs.setGemCount(prefs.getGemCount() + finalGemCount);
                    finalGemCountTemp = finalGemCount;
                    gemCountUpdated = true;
                }

            } else if (gameInterface.isContinueScreenOpen() && gameInterface.checkForYesButtonTap()) {
                gameInterface.setContinueScreenOpen(false);

                if (Gdx.app.getType() == Application.ApplicationType.Android) {
                    isAdLoaded = game.requestHandlerAndroid.isAdLoaded();
                    game.requestHandlerAndroid.showAd(true, soundEnabled, prefs, gemSound);
                } else if (Gdx.app.getType() == Application.ApplicationType.iOS) {
                    isAdLoaded = game.requestHandlerIOS.isAdLoaded();
                    game.requestHandlerIOS.showAd(true, soundEnabled, prefs, gemSound);
                }

                if (isAdLoaded) {
                    isPaused = true;
                }
            }

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                if (game.requestHandlerAndroid.isAdFinished()) {
                    isPaused = false;
                    gameInterface.setContinueScreenOpen(false);
                    game.requestHandlerAndroid.setAdFinished(false);
                    isAdLoaded = false;

                    resetPlayer(1);
                    resetPools();
                    resetMainLists();
                    resetBulletTimers();
                    resetItems();
                    resetMusicAndSoundPitches();
                }
            } else if (Gdx.app.getType() == Application.ApplicationType.iOS) {
                if (game.requestHandlerIOS.isAdFinished()) {
                    isPaused = false;
                    gameInterface.setContinueScreenOpen(false);
                    game.requestHandlerIOS.setAdFinished(false);
                    isAdLoaded = false;


                    resetPlayer(1);
                    resetPools();
                    resetMainLists();
                    resetBulletTimers();
                    resetItems();
                    resetMusicAndSoundPitches();
                }
            }
        }

        if (prefs.getHighScore() < score) {
            prefs.setHighScore(score);
            newHighscore = true;
        }
    }

    public void fadeOut() {
        isFadingOut = true;
        fadeOutOpacity += 0.8f * deltaP;
        blackTransition.setColor(0, 0, 0, fadeOutOpacity);

        if (fadeOutOpacity >= 1f) {
            dispose();
        }

        blackTransition.draw(game.batch);
    }

    public void fadeIn() {
        isFadingIn = true;
        fadeInOpacity -= 0.6f * deltaP;
        blackTransition.setColor(0, 0, 0, fadeInOpacity);
        blackTransition.draw(game.batch);
    }

    public void drawMainElements() {
        if (!isRunningResumeCountdown)
            stateTime += deltaP / 6;

        if (isTransitioningIn) {
            shipAnim.drawAnim(shipAnimation, stateTime, SHIP_X, SHIP_START_Y, SHIP_WIDTH, SHIP_HEIGHT, true, game.batch, true);
        } else if (!justHit && health != 0) {
            shipAnim.drawAnim(shipAnimation, stateTime, SHIP_X, SHIP_START_Y, SHIP_WIDTH, SHIP_HEIGHT, true, game.batch);
        } else if (!isTransitioningOut) {
            runShipBlinking();
        }

        if (isTransitionedIn) {
            gameInterface.drawTopUI(game, isPaused || isRunningResumeCountdown, health, isAlive, true);


            gl.setText(Main.scoreFont, String.valueOf(score));


            Main.scoreFont.draw(game.batch, gl, SCORE_X, TOP_ELEM_Y + gl.height - (int) (0.6f * Gdx.graphics.getSafeInsetTop()));
            gemCountFont.draw(game.batch, " x " + gemCount, GEM_COUNT_X, GEM_COUNT_Y - (int) (0.6f * Gdx.graphics.getSafeInsetTop()));


            updatingScore = false;
        }
    }

    public void runShipBlinking() {
        shipBlinkingTimer += deltaP;

        if (shipBlinkingTimer < 0) {
            shipAnim.drawAnim(shipAnimation, stateTime, SHIP_X, SHIP_START_Y, SHIP_WIDTH, SHIP_HEIGHT, true, game.batch, true);
        } else if (shipBlinkingTimer < 0.2) {
            shipAnim.drawAnim(shipAnimation, stateTime, SHIP_X, SHIP_START_Y, SHIP_WIDTH, SHIP_HEIGHT, true, game.batch);
        } else {
            shipBlinkingTimer = -0.2f;
        }
    }

    public void runBulletTimers() {
        if (isMissile) {
            if (missileUsed) {
                bulletThreshold = 0.3f;
            } else {
                bulletThreshold = 0.65f;
            }
        } else if (isRapidFire) {
            bulletThreshold = 0.1f;
        } else if (!bullets.isEmpty()) {
            bulletThreshold = 0.4f;
        }

        if (bulletTimer > bulletThreshold) {
            if (score < 99999 && !isShipLeaving) {

                if (soundEnabled) {
                    if (isTransitionedIn) {
                        if (isMissile) {
                            if (missileSound.isPlaying()) {
                                missileSound1.play();
                            } else {
                                missileSound.play();
                            }
                        } else {
                            if (bulletSound.isPlaying()) {
                                bulletSound1.play();
                            } else {
                                bulletSound.play();

                            }
                        }
                    }
                }
            }
            if (!isShipLeaving && SHIP_START_Y >= SHIP_Y && isAlive && !gameInterface.isContinueScreenOpen()) {
                addBullets();
            }
            if (bullets.isEmpty() && !isMissile && !isRapidFire) { // allows bullets to fire immediately after transitioning in
                bulletThreshold = 0;
            }
            bulletTimer -= bulletThreshold;
        }

        if (enemyShipsSpawning) {
            for (Enemy enemy : enemies) {
                if (enemy.getId() == (ENEMY_SHIP_ID)) {
                    if (enemy.getEnemyY() < SCREEN_HEIGHT && enemy.getEnemyBulletTimer() > enemy.getEnemyBulletThreshold()) {
                        if (!isShipLeaving && SHIP_START_Y >= SHIP_Y) {
                            addEnemyBullets(enemy);
                            enemy.setEnemyBulletTimer(enemy.getEnemyBulletTimer() - enemy.getEnemyBulletThreshold());
                        }
                    }
                }
            }
        }
    }

    public void addBullets() {
        if (isMissile) {
            bullet1 = bp.obtain();
            bullet1.create((int) SHIP_X, true, false, false, false, false, false, assets);
            bullets.add(bullet1);
        } else if (isSpreadFire) {
            bullet1 = bp.obtain();
            bullet2 = bp.obtain();
            bullet3 = bp.obtain();
            bullet4 = bp.obtain();

            //left diagonal spread
            bullet1.create((int) (SHIP_X + SHIP_WIDTH * (4 / 27f)), false, isRapidFire, isSpreadFire, true, false, isHourglass, assets);
            bullets.add(bullet1);

            //right diagonal spread
            bullet2.create((int) (SHIP_X + SHIP_WIDTH - SHIP_WIDTH * (6 / 27f)), false, isRapidFire, isSpreadFire, false, true, isHourglass, assets);
            bullets.add(bullet2);

            //left bullet
            bullet3.create((int) (SHIP_X + SHIP_WIDTH * (4 / 27f)), false, isRapidFire, isSpreadFire, false, false, isHourglass, assets);
            bullets.add(bullet3);

            //right bullet
            bullet4.create((int) (SHIP_X + SHIP_WIDTH - SHIP_WIDTH * (6 / 27f)), false, isRapidFire, isSpreadFire, false, false, isHourglass, assets);
            bullets.add(bullet4);

        } else {
            bullet1 = bp.obtain();
            bullet2 = bp.obtain();

            bullet1.create((int) (SHIP_X + SHIP_WIDTH * (4 / 27f)), isMissile, isRapidFire, isSpreadFire, false, false, isHourglass, assets);
            bullets.add(bullet1);

            bullet2.create((int) (SHIP_X + SHIP_WIDTH - SHIP_WIDTH * (6 / 27f)), isMissile, isRapidFire, isSpreadFire, false, false, isHourglass, assets);
            bullets.add(bullet2);

            missileTimer = MISSILE_TIMER;
        }
    }

    public void updateBullets(Main game) {
        bp.freeAll(bulletsToRemove);
        bullets.removeAll(bulletsToRemove, true);
        bulletsToRemove.clear();

        for (Bullet bullet : bullets) {
            bullet.update(deltaP, isHourglass, bullet.isSpreadFire(), bullet.isLeftSpread(), bullet.isRightSpread());

            if (bullet.getBulletY() - Bullet.getBulletHeight() > SCREEN_HEIGHT && !bulletsToRemove.contains(bullet, true)) {
                bulletsToRemove.add(bullet);
            }

            if (bullet.isMissile())
                bullet.render(missileAnim, deltaP, Bullet.MISSILE_WIDTH, Bullet.MISSILE_HEIGHT, game.batch);
            else
                bullet.render(game.batch);
        }
    }

    public void addEnemyBullets(Enemy enemy) {
        if (enemy.HP > 0) {
            EnemyBullet enemyBullet1 = ebp.obtain();
            EnemyBullet enemyBullet2 = ebp.obtain();
            if (enemy.getId() == (ENEMY_SHIP_ID) && enemy.getEnemyBulletTimer() > enemy.getEnemyBulletThreshold()) {
                enemyBullet1.create((int) (enemy.ENEMY_X + ENEMY_SHIP_WIDTH * (5 / 31f)), (int) (enemy.ENEMY_Y + enemy.ENEMY_HEIGHT * (3 / 27f)), enemy.getBulletSpeed(), RED_ID, assets);
                enemyBullets.add(enemyBullet1);

                enemyBullet2.create((int) (enemy.ENEMY_X + ENEMY_SHIP_WIDTH - ENEMY_SHIP_WIDTH * (7 / 31f)), (int) (enemy.ENEMY_Y + enemy.ENEMY_HEIGHT * (3 / 27f)), enemy.getBulletSpeed(), RED_ID, assets);
                enemyBullets.add(enemyBullet2);
            }
        }
    }

    public void updateEnemyBullets(Main game) {
        ebp.freeAll(enemyBulletsToRemove);
        enemyBullets.removeAll(enemyBulletsToRemove, true);
        enemyBulletsToRemove.clear();

        for (EnemyBullet enemyBullet : enemyBullets) {
            enemyBullet.update(deltaP * hourglassMultiplier);
            enemyBullet.render(game.batch, deltaP, enemyBulletAnim);

            if (enemyBullet.getY() + ENEMY_BULLET_HEIGHT < 0 && !enemyBulletsToRemove.contains(enemyBullet, true)) {
                enemyBulletsToRemove.add(enemyBullet);
            }
        }
    }

    public void addEyebats() {
        eyebatSpawnTimer -= deltaP * hourglassMultiplier;
        if (eyebatSpawnTimer <= 0) {

            Enemy enemy = ep.obtain();

            randomSpawnLocation = random.nextInt((int) (SCREEN_WIDTH - BLUE_EYEBAT_WIDTH));

            if (score < 5000) {

                if (score <= 1000) {
                    enemy.create(EYEBAT_ID, BLUE_ID, 1, randomSpawnLocation, BLUE_EYEBAT_WIDTH, BLUE_EYEBAT_HEIGHT, 0.3f + speedIncrease / 1.5f, 0.7f + speedIncrease / 1.5f, (float) (DEFAULT_FRAME_DURATION * (1.25 - speedIncrease / 2)), false, hurtTimer);
                    eyebatSpawnTimer = random.nextFloat() * (maxEyebatSpawnTime - minEyebatSpawnTime) + minEyebatSpawnTime;

                }
                if (score > 1000 && score <= 2000) {
                    enemy.create(EYEBAT_ID, GREEN_ID, 2, randomSpawnLocation, GREEN_EYEBAT_WIDTH, GREEN_EYEBAT_HEIGHT, 0.65f + speedIncrease / 1.5f, 0.45f + speedIncrease / 1.5f, (float) (DEFAULT_FRAME_DURATION * (1.5 - speedIncrease / 2)), false, hurtTimer);
                    eyebatSpawnTimer = random.nextFloat() * (1.2f * maxEyebatSpawnTime - 1.2f * minEyebatSpawnTime) + 1.2f * minEyebatSpawnTime;

                }
                if (score > 2000 && score <= 3000) {
                    enemy.create(EYEBAT_ID, RED_ID, 2, randomSpawnLocation, RED_EYEBAT_WIDTH, RED_EYEBAT_HEIGHT, 0.60f + speedIncrease / 1.5f, 0.35f + speedIncrease / 1.5f, (float) (DEFAULT_FRAME_DURATION * (1.5 - speedIncrease / 2)), false, hurtTimer);
                    eyebatSpawnTimer = random.nextFloat() * (1.3f * maxEyebatSpawnTime - 1.3f * minEyebatSpawnTime) + 1.3f * minEyebatSpawnTime;

                }
                if (score > 3000 && score <= 4000) {
                    enemy.create(EYEBAT_ID, PURPLE_ID, 3, randomSpawnLocation, PURPLE_EYEBAT_WIDTH, PURPLE_EYEBAT_HEIGHT, 0.45f + speedIncrease / 1.75f, 0.20f + speedIncrease / 1.75f, (float) (DEFAULT_FRAME_DURATION * (1.75 - speedIncrease / 2)), false, hurtTimer);
                    eyebatSpawnTimer = random.nextFloat() * (1.6f * maxEyebatSpawnTime - 1.6f * minEyebatSpawnTime) + 1.6f * minEyebatSpawnTime;

                }
                if (score > 4000) {
                    enemy.create(EYEBAT_ID, WHITE_ID, 4, randomSpawnLocation, WHITE_EYEBAT_WIDTH, WHITE_EYEBAT_HEIGHT, 0.25f + speedIncrease / 1.75f, 0.15f + speedIncrease / 1.75f, (float) (DEFAULT_FRAME_DURATION * (2.0 - speedIncrease / 2)), false, hurtTimer);
                    eyebatSpawnTimer = random.nextFloat() * (2f * maxEyebatSpawnTime - 2f * minEyebatSpawnTime) + 2f * minEyebatSpawnTime;

                }
            } else {
                randomEnemy = random.nextInt(5);
                if (randomEnemy == 0) {
                    enemy.create(EYEBAT_ID, BLUE_ID, 1, randomSpawnLocation, BLUE_EYEBAT_WIDTH, BLUE_EYEBAT_HEIGHT, 0.3f + speedIncrease / 1.5f, .3f + speedIncrease / 1.5f, (float) (DEFAULT_FRAME_DURATION * (1.25 - speedIncrease / 3)), false, hurtTimer);
                } else if (randomEnemy == 1) {
                    enemy.create(EYEBAT_ID, GREEN_ID, 2, randomSpawnLocation, GREEN_EYEBAT_WIDTH, GREEN_EYEBAT_HEIGHT, 0.65f + speedIncrease / 1.5f, .25f + speedIncrease / 1.5f, (float) (DEFAULT_FRAME_DURATION * (1.5 - speedIncrease / 3)), false, hurtTimer);
                } else if (randomEnemy == 2) {
                    enemy.create(EYEBAT_ID, RED_ID, 2, randomSpawnLocation, RED_EYEBAT_WIDTH, RED_EYEBAT_HEIGHT, 0.60f + speedIncrease / 1.5f, .2f + speedIncrease / 1.5f, (float) (DEFAULT_FRAME_DURATION * (1.5 - speedIncrease / 3)), false, hurtTimer);
                } else if (randomEnemy == 3) {
                    enemy.create(EYEBAT_ID, PURPLE_ID, 3, randomSpawnLocation, PURPLE_EYEBAT_WIDTH, PURPLE_EYEBAT_HEIGHT, 0.45f + speedIncrease / 1.75f, 0.20f + speedIncrease / 1.75f, (float) (DEFAULT_FRAME_DURATION * (1.75 - speedIncrease / 2)), false, hurtTimer);
                } else {
                    enemy.create(EYEBAT_ID, WHITE_ID, 4, randomSpawnLocation, WHITE_EYEBAT_WIDTH, WHITE_EYEBAT_HEIGHT, 0.25f + speedIncrease / 1.75f, 0.15f + speedIncrease / 1.75f, (float) (DEFAULT_FRAME_DURATION * (2.0 - speedIncrease / 2)), false, hurtTimer);
                }
                eyebatSpawnTimer = random.nextFloat() * (1.6f * maxEyebatSpawnTime - 1.6f * minEyebatSpawnTime) + 1.6f * minEyebatSpawnTime;
            }
            enemies.add(enemy);
        }
    }

    public void addEnemyShips() {
        enemyShipSpawnTimer -= deltaP * hourglassMultiplier;

        shipPositions.clear();
        int enemyShipCount = 0;

        if (enemyShipSpawnTimer <= 0) {

            for (Enemy e : enemies) {
                if (e.getId() == ENEMY_SHIP_ID && e.getEnemyY() < SCREEN_HEIGHT) {
                    enemyShipCount++;
                    shipPositions.add(e.position);
                }
            }

            if (!shipPositions.contains(0, true)) {
                position = 0;
            } else if (!shipPositions.contains(1, true)) {
                position = 1;
            }
            shipPositions.add(position);

            if (enemyShipCount < 2) {
                Enemy enemy = ep.obtain();

                if (score < 5000) {

                    if (score <= 1000) {
                        enemy.create(ENEMY_SHIP_ID, BLUE_ID, 2, random.nextInt((SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                                1.3f + speedIncrease, 1.05f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, blueShipBulletThreshold,
                                false, hurtTimer, position, blueShipBulletSpeed);
                        enemyShipSpawnTimer = random.nextFloat() * (maxEnemyShipSpawnTime - minEnemyShipSpawnTime) + minEnemyShipSpawnTime;

                    } else if (score <= 2000) {
                        enemy.create(ENEMY_SHIP_ID, GREEN_ID, 3, random.nextInt((SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                                1.2f + speedIncrease, 1f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, greenShipBulletThreshold,
                                false, hurtTimer, position, greenShipBulletSpeed);
                        enemyShipSpawnTimer = random.nextFloat() * (1.2f * maxEnemyShipSpawnTime - 1.2f * minEnemyShipSpawnTime) + 1.2f * minEnemyShipSpawnTime;

                    } else if (score <= 3000) {
                        enemy.create(ENEMY_SHIP_ID, RED_ID, 3, random.nextInt((SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                                1f + speedIncrease, 0.95f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, redShipBulletThreshold,
                                false, hurtTimer, position, redShipBulletSpeed);
                        enemyShipSpawnTimer = random.nextFloat() * (1.2f * maxEnemyShipSpawnTime - 1.2f * minEnemyShipSpawnTime) + 1.2f * minEnemyShipSpawnTime;

                    } else if (score <= 4000) {
                        enemy.create(ENEMY_SHIP_ID, PURPLE_ID, 4, random.nextInt((SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                                0.9f + speedIncrease, 0.85f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, purpleShipBulletThreshold,
                                false, hurtTimer, position, purpleShipBulletSpeed);
                        enemyShipSpawnTimer = random.nextFloat() * (1.3f * maxEnemyShipSpawnTime - 1.3f * minEnemyShipSpawnTime) + 1.3f * minEnemyShipSpawnTime;

                    } else {
                        enemy.create(ENEMY_SHIP_ID, WHITE_ID, 4, random.nextInt((SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                                0.8f + speedIncrease, 0.75f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, whiteShipBulletThreshold,
                                false, hurtTimer, position, whiteShipBulletSpeed);
                        enemyShipSpawnTimer = random.nextFloat() * (1.5f * maxEnemyShipSpawnTime - 1.5f * minEnemyShipSpawnTime) + 1.5f * minEnemyShipSpawnTime;
                    }
                } else {

                    randomEnemy = random.nextInt(5);
                    if (randomEnemy == 0) {
                        enemy.create(ENEMY_SHIP_ID, BLUE_ID, 2, random.nextInt((SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                                1.3f + speedIncrease, 1.05f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, blueShipBulletThreshold,
                                false, hurtTimer, position, blueShipBulletSpeed);
                    } else if (randomEnemy == 1) {
                        enemy.create(ENEMY_SHIP_ID, GREEN_ID, 3, random.nextInt((SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                                1.2f + speedIncrease, 1f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, greenShipBulletThreshold,
                                false, hurtTimer, position, greenShipBulletSpeed);
                    } else if (randomEnemy == 2) {
                        enemy.create(ENEMY_SHIP_ID, RED_ID, 3, random.nextInt((SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                                1f + speedIncrease, 0.95f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, redShipBulletThreshold,
                                false, hurtTimer, position, redShipBulletSpeed);
                    } else if (randomEnemy == 3) {
                        enemy.create(ENEMY_SHIP_ID, PURPLE_ID, 4, random.nextInt((SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                                0.9f + speedIncrease, 0.85f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, purpleShipBulletThreshold,
                                false, hurtTimer, position, purpleShipBulletSpeed);
                    } else {
                        enemy.create(ENEMY_SHIP_ID, WHITE_ID, 4, random.nextInt((SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                                0.8f + speedIncrease, 0.75f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, whiteShipBulletThreshold,
                                false, hurtTimer, position, whiteShipBulletSpeed);
                    }
                    enemyShipSpawnTimer = random.nextFloat() * (1.2f * maxEnemyShipSpawnTime - 1.2f * minEnemyShipSpawnTime) + 1.2f * minEnemyShipSpawnTime;
                }
                enemies.add(enemy);
            }
        }
    }

    public void addLaserTraps() {
        laserTrapSpawnTimer -= deltaP * hourglassMultiplier;

        if (laserTrapSpawnTimer <= 0) {
            Enemy enemy = ep.obtain();
            laserTrapSpawnTimer = random.nextFloat() * (maxLaserSpawnTime - minLaserSpawnTime) + minLaserSpawnTime;

            enemy.create(LASER_TRAP_ID, random.nextInt((SCREEN_WIDTH - (int) LASER_TRAP_H_WIDTH)), LASER_TRAP_H_WIDTH, LASER_TRAP_H_HEIGHT,
                    0, 0.65f + speedIncrease / 1.5f, DEFAULT_FRAME_DURATION * 1.5f);
            enemies.add(enemy);

        }
    }

    public void updateEnemies() {
        ep.freeAll(enemiesToRemove);
        enemies.removeAll(enemiesToRemove, true);
        enemiesToRemove.clear();

        for (Enemy enemy : enemies) {
            if (isAlive) {
                enemy.update(deltaP, enemy, isHourglass);

                switch (enemy.getId()) {
                    case EYEBAT_ID:
                        enemy.render(eyebatAnim, enemy, deltaP, isPaused, game.batch);
                        break;
                    case ENEMY_SHIP_ID:
                        enemy.render(enemyShipAnim, enemy, deltaP, isPaused, game.batch);
                        break;
                    case LASER_TRAP_ID:
                        enemy.render(laserTrapHAnim, enemy, deltaP, isPaused, game.batch);
                        break;
                }
            }
        }
    }

    public void addGemDrops() {
        gemSpawnTimer -= deltaP;
        if (gemSpawnTimer <= 0) {
            ItemDrop itemDrop = idp.obtain();
            itemDrop.create(random.nextInt((int) (SCREEN_WIDTH - ItemDrop.GEM_WIDTH)), (int) ItemDrop.GEM_HEIGHT, ItemDrop.GEM_WIDTH, GEM_ID, assets);
            itemDrops.add(itemDrop);
            gemSpawnTimer = random.nextFloat() * (maxGemSpawnTime - minGemSpawnTime) + minGemSpawnTime;
        }
    }

    public void updateGemSpawnTimes() {
        if (score > 1000 && score < 2000 && maxGemSpawnTime != MAX_GEM_SPAWN_TIME - 3f) {
            maxGemSpawnTime -= 3f;
            minGemSpawnTime -= 2f;
        } else if (score >= 2000 && score < 3000 && maxGemSpawnTime != MAX_GEM_SPAWN_TIME - 6f) {
            maxGemSpawnTime -= 3f;
            minGemSpawnTime -= 2f;
        } else if (score >= 3000 && score < 4000 && maxGemSpawnTime != MAX_GEM_SPAWN_TIME - 9f) {
            maxGemSpawnTime -= 3f;
            minGemSpawnTime -= 2f;
        } else if (score >= 4000 && maxGemSpawnTime != MAX_GEM_SPAWN_TIME - 12f) {
            maxGemSpawnTime -= 3f;
            minGemSpawnTime -= 2f;
        }
    }

    public void addItemDrops() {
        itemSpawnTimer -= deltaP;
        if (itemSpawnTimer <= 0) {

            randomDrop = random.nextInt(6);

            if (randomDrop == lastItemDrop) {
                addItemDrops();
            } else {
                itemSpawnTimer = random.nextFloat() * (MAX_ITEM_SPAWN_TIME - MIN_ITEM_SPAWN_TIME) + MIN_ITEM_SPAWN_TIME;
                if (randomDrop == 0) {
                    if (health == 3)
                        addItemDrops();
                    else {
                        ItemDrop itemDrop = idp.obtain();
                        itemDrop.create(random.nextInt((int) (SCREEN_WIDTH - ItemDrop.HEART_ITEM_WIDTH)), (int) ItemDrop.HEART_ITEM_HEIGHT, ItemDrop.HEART_WIDTH, HEART_ID, assets);
                        itemDrops.add(itemDrop);
                    }
                } else if (randomDrop == 1) {
                    ItemDrop itemDrop = idp.obtain();
                    itemDrop.create(random.nextInt((int) (SCREEN_WIDTH - ItemDrop.BOMB_WIDTH)), (int) ItemDrop.BOMB_HEIGHT, ItemDrop.BOMB_WIDTH, BOMB_ID, assets);
                    itemDrops.add(itemDrop);
                } else if (randomDrop == 2) {
                    ItemDrop itemDrop = idp.obtain();
                    itemDrop.create(random.nextInt((int) (SCREEN_WIDTH - ItemDrop.MISSILE_ITEM_WIDTH)), (int) ItemDrop.MISSILE_ITEM_HEIGHT, ItemDrop.MISSILE_ITEM_WIDTH, MISSILE_ID, assets);
                    itemDrops.add(itemDrop);

                } else if (randomDrop == 3) {
                    ItemDrop itemDrop = idp.obtain();
                    itemDrop.create(random.nextInt((int) (SCREEN_WIDTH - ItemDrop.RAPID_FIRE_WIDTH)), (int) ItemDrop.RAPID_FIRE_HEIGHT, ItemDrop.RAPID_FIRE_WIDTH, RAPID_FIRE_ID, assets);
                    itemDrops.add(itemDrop);

                } else if (randomDrop == 4) {
                    ItemDrop itemDrop = idp.obtain();
                    itemDrop.create(random.nextInt((int) (SCREEN_WIDTH - ItemDrop.SPREAD_WIDTH)), (int) ItemDrop.SPREAD_HEIGHT, ItemDrop.SPREAD_WIDTH, SPREAD_ID, assets);
                    itemDrops.add(itemDrop);

                } else {
                    ItemDrop itemDrop = idp.obtain();
                    itemDrop.create(random.nextInt((int) (SCREEN_WIDTH - ItemDrop.HOURGLASS_WIDTH)), (int) ItemDrop.HOURGLASS_HEIGHT, ItemDrop.HOURGLASS_WIDTH, HOURGLASS_ID, assets);
                    itemDrops.add(itemDrop);

                }
            }
            lastItemDrop = randomDrop;
        }
    }

    public void updateItems() {
        idp.freeAll(itemsToRemove);
        itemDrops.removeAll(itemsToRemove, true);
        itemsToRemove.clear();
        for (ItemDrop itemDrop : itemDrops) {
            itemDrop.update(deltaP * hourglassMultiplier);
            switch (itemDrop.getItemId()) {
                case HEART_ID:
                    itemDrop.render(ItemDrop.HEART_ITEM_WIDTH, ItemDrop.HEART_ITEM_HEIGHT, deltaP * hourglassMultiplier, game.batch);
                    break;
                case BOMB_ID:
                    itemDrop.render(ItemDrop.BOMB_WIDTH, ItemDrop.BOMB_HEIGHT, deltaP * hourglassMultiplier, game.batch);
                    break;
                case MISSILE_ID:
                    itemDrop.render(ItemDrop.MISSILE_ITEM_WIDTH, ItemDrop.MISSILE_ITEM_HEIGHT, deltaP * hourglassMultiplier, game.batch);
                    break;
                case RAPID_FIRE_ID:
                    itemDrop.render(ItemDrop.RAPID_FIRE_WIDTH, ItemDrop.RAPID_FIRE_HEIGHT, deltaP * hourglassMultiplier, game.batch);
                    break;
                case GEM_ID:
                    itemDrop.render(ItemDrop.GEM_WIDTH, ItemDrop.GEM_HEIGHT, deltaP * hourglassMultiplier, game.batch);
                    break;
                case SPREAD_ID:
                    itemDrop.render(ItemDrop.SPREAD_WIDTH, ItemDrop.SPREAD_HEIGHT, deltaP * hourglassMultiplier, game.batch);
                    break;
                case HOURGLASS_ID:
                    itemDrop.render(ItemDrop.HOURGLASS_WIDTH, ItemDrop.HOURGLASS_HEIGHT, deltaP * hourglassMultiplier, game.batch);
                    break;
            }

            if (itemDrop.getY() + itemDrop.getHeight() < 0) {
                itemsToRemove.add(itemDrop);
            }
        }
    }

    public void enemyDamaged() {
        for (Enemy enemy : enemies) {
            boolean pointsEarned = false;

            if (bombUsed && enemy.getId() != LASER_TRAP_ID) {
                enemy.HP = 0;
            }
            if (bombUsed) {
                if (enemy.getEnemyY() < SCREEN_HEIGHT) {
                    addPointsOnEnemyDeath(enemy);
                    pointsEarned = true;
                }
                if (!enemiesToRemove.contains(enemy, true)) {
                    enemiesToRemove.add(enemy);
                }
            }

            for (Bullet bullet : bullets) {

//            game.batch.end();
//            sr.begin((ShapeRenderer.ShapeType.Line));
//            sr.polygon(bullet.getCollision().getPolygon().getVertices());
//            sr.end();
//            game.batch.begin();

                if (enemy.getEnemyY() < SCREEN_HEIGHT) {
                    if (enemy.getEnemyY() + enemy.getHeight() < 0) {
                        if (!enemiesToRemove.contains(enemy, true)) {
                            enemiesToRemove.add(enemy);
                        }
                    }

                    if ((Collision.isNearby(bullet.getCollision(), enemy.getCollision())
                            && Collision.isColliding(bullet.getCollision(), enemy.getCollision()))) {


                        if (!bullet.isMissile() && enemy.getId() != LASER_TRAP_ID && !bulletsToRemove.contains(bullet, true)) {
                            bulletsToRemove.add(bullet);
                        }

                        if (bullet.isMissile() && bullet.getBulletY() < SCREEN_HEIGHT && enemy.getId() != LASER_TRAP_ID) {
                            enemy.HP = 0;
                            if (soundEnabled) {
                                playHitSound = true;
                            }
                        }

                        if (!bullet.isMissile() && enemy.getId() != LASER_TRAP_ID) {
                            if (bullet.isRapidFire()) {
                                enemy.HP -= 0.5f;
                            } else {
                                enemy.HP -= 1;
                            }
                            if (soundEnabled && enemy.getHP() <= 0) {
                                playHitSound = true;
                            }
                            if (enemy.HP > 0) {
                                isEnemyHurt = true;
                                hurtEnemies.add(enemy);
                            }
                        }

                        if (enemy.HP <= 0 && enemy.getId() != LASER_TRAP_ID) {

                            if (!enemiesToRemove.contains(enemy, true)) {
                                enemiesToRemove.add(enemy);
                            }

                            Explosion explosion = exp.obtain();
                            explosion.create((int) (enemy.getEnemyX() - (SMALL_EXPLOSION_WIDTH - enemy.getWidth()) / 2), (int) (enemy.getEnemyY() - (SMALL_EXPLOSION_HEIGHT - enemy.getHeight()) / 2), SMALL_EXPLOSION_WIDTH);
                            explosions.add(explosion);

                            if (!pointsEarned && !bombUsed) {
                                addPointsOnEnemyDeath(enemy);
                                pointsEarned = true;

                            }
                        }
                    }
                }
            }
        }

        if (playHitSound) {
            runEnemyHitSoundTimer();
        }
    }

    public void addPointsOnEnemyDeath(Enemy enemy) {
        if (enemy.getId() == (EYEBAT_ID)) {
            switch (enemy.getColorId()) {
                case BLUE_ID:
                case RED_ID:
                case GREEN_ID:
                    score += 15;
                    break;

                case PURPLE_ID:
                case WHITE_ID:
                    score += 20;
                    break;
            }
        } else if (enemy.getId() == (ENEMY_SHIP_ID) && enemy.getEnemyY() < SCREEN_HEIGHT) {
            score += 15;
        }
    }

    public void updateExplosions() {
        exp.freeAll(explosionsToRemove);
        explosions.removeAll(explosionsToRemove, true);
        explosionsToRemove.clear();

        for (Explosion explosion : explosions) {
            if (explosion.y < SCREEN_HEIGHT && explosion.y > 0) {
                explosion.update(deltaP);
                explosion.render(explosionAnim, game.batch);
            }

            if (explosion.explosionAnimation.isAnimationFinished(explosion.getStateTime()) && !explosionsToRemove.contains(explosion, true)) {
                explosionsToRemove.add(explosion);
            }
        }
    }

    public void playerEnemyCollision() {
//
//            game.batch.end();
//            sr.begin((ShapeRenderer.ShapeType.Line));
//
//            sr.polygon(player.getCollision().getPolygon().getVertices());
//            sr.end();
//            game.batch.begin();

        for (Enemy enemy : enemies) {
//            game.batch.end();
//            sr.begin((ShapeRenderer.ShapeType.Line));
//            sr.setColor(0,1,0,1);
//            sr.polygon(enemy.getCollision().getPolygon().getVertices());
//            sr.end();
//            game.batch.begin();
            if (Collision.isNearby(enemy.getCollision(), player.getCollision())
                    && (Collision.isColliding(enemy.getCollision(), player.getCollision()))) {

                if (soundEnabled && !playerHitSoundPlayed) {
                    hitSound.play();
                    playerHitSoundPlayed = true;
                }

                if (enemy.getId() != LASER_TRAP_ID) {
                    ep.free(enemy);
                    enemies.removeValue(enemy, true);

                    Explosion explosion = exp.obtain();
                    explosion.create((int) (enemy.getEnemyX() - (SMALL_EXPLOSION_WIDTH - enemy.getWidth()) / 2), (int) (enemy.getEnemyY() - (SMALL_EXPLOSION_HEIGHT - enemy.getHeight()) / 2), SMALL_EXPLOSION_WIDTH);
                    explosionsToDelay.add(explosion);
                }
                if (health > 0 && !justHit) {
                    justHit = true;
                    health -= 1;
                    Gdx.input.vibrate(50);
                }

                if (health == 0) {
                    isAlive = false;
                }
            }
        }
    }

    public void playerBulletCollision() {
        for (EnemyBullet enemyBullet : enemyBullets) {
            if (Collision.isNearby(enemyBullet.getCollision(), player.getCollision())
                    && Collision.isColliding(enemyBullet.getCollision(), player.getCollision())) {

                if (soundEnabled) {
                    hitSound.play();
                }

                if (!enemyBulletsToRemove.contains(enemyBullet, true)) {
                    enemyBulletsToRemove.add(enemyBullet);
                }

                if (health > 0 && !justHit) {
                    justHit = true;
                    health -= 1;
                    Gdx.input.vibrate(50);
                }

                if (health == 0) {
                    isAlive = false;
                }
            }
        }
    }

    public void playerItemCollision() {
        for (ItemDrop itemDrop : itemDrops) {
            if (Collision.isNearby(itemDrop.getCollision(), player.getCollision())
                    && Collision.isColliding(itemDrop.getCollision(), player.getCollision())) {
                itemsToRemove.add(itemDrop);

                switch (itemDrop.getItemId()) {
                    case HEART_ID:
                        score += 25;

                        if (soundEnabled) {
                            itemSound.play();
                        }

                        if (health > 0 && health < 3) {
                            health += 1;
                        }
                        break;

                    case BOMB_ID:
                        bombUsed = true;
                        Gdx.input.vibrate(200);

                        for (Enemy enemy : enemies) {

                            Explosion explosion = exp.obtain();
                            explosion.create((int) (enemy.getEnemyX() - (SMALL_EXPLOSION_WIDTH - enemy.getWidth()) / 2), (int) (enemy.getEnemyY() - (SMALL_EXPLOSION_HEIGHT - enemy.getHeight()) / 2), SMALL_EXPLOSION_WIDTH);

                            explosions.add(explosion);
                        }
                        if (soundEnabled) {
                            bombSound.play();
                        }
                        enemyBullets.clear();

                        break;

                    case MISSILE_ID:
                        isMissile = true;

                        if (soundEnabled) {
                            itemSound.play();
                        }

                        if (!missileUsed) {
                            missileUsed = true;
                            bulletTimer = .2f;
                        }
                        break;

                    case GEM_ID:
                        gemCount++;

                        if (soundEnabled) {
                            gemSound.play();

                        }
                        break;

                    case RAPID_FIRE_ID:
                        isRapidFire = true;

                        if (soundEnabled) {
                            itemSound.play();
                        }
                        if (!rapidFireUsed) {
                            bulletTimer = .05f;
                            rapidFireUsed = true;
                        }
                        break;

                    case SPREAD_ID:
                        isSpreadFire = true;

                        if (soundEnabled) {
                            itemSound.play();
                        }

                        break;

                    case HOURGLASS_ID:
                        isHourglass = true;
                        if (soundEnabled) {
                            itemSound.play();
                        }

                        break;
                }
            }
            itemDrops.removeAll(itemsToRemove, true);
        }
    }

    public void getSelectedShip() {
        if (selectedShip == SHIP_ID) {
            shipSS = new Sprite(assets.assetManager.get(Assets.ship_ss, Texture.class));
        } else if (selectedShip == RED_SHIP_ID) {
            shipSS = new Sprite(assets.assetManager.get(Assets.ship_red_ss, Texture.class));
        } else if (selectedShip == BLACK_SHIP_ID) {
            shipSS = new Sprite(assets.assetManager.get(Assets.ship_black_ss, Texture.class));
        } else if (selectedShip == PURPLE_SHIP_ID) {
            shipSS = new Sprite(assets.assetManager.get(Assets.ship_purple_ss, Texture.class));
        } else if (selectedShip == YELLOW_SHIP_ID) {
            shipSS = new Sprite(assets.assetManager.get(Assets.ship_yellow_ss, Texture.class));
        } else if (selectedShip == CYAN_SHIP_ID) {
            shipSS = new Sprite(assets.assetManager.get(Assets.ship_cyan_ss, Texture.class));
        } else if (selectedShip == BLUE_SHIP_ID) {
            shipSS = new Sprite(assets.assetManager.get(Assets.ship_blue_ss, Texture.class));
        } else if (selectedShip == BRED_SHIP_ID) {
            shipSS = new Sprite(assets.assetManager.get(Assets.ship_bred_ss, Texture.class));
        } else if (selectedShip == GREEN_SHIP_ID) {
            shipSS = new Sprite(assets.assetManager.get(Assets.ship_green_ss, Texture.class));
        } else if (selectedShip == ORANGE_SHIP_ID) {
            shipSS = new Sprite(assets.assetManager.get(Assets.ship_orange_ss, Texture.class));
        }
    }

    public void addPowerUpTimer(float totalTimerLength) {
        if (powerUpTimers.isEmpty()) {
            powerUpTimer = new PowerupTimer();
            powerUpTimer.create(POWERUP_TIMER_X, POWERUP_TIMER_Y - (int) (0.6f * Gdx.graphics.getSafeInsetTop()), POWERUP_TIMER_HEIGHT, totalTimerLength);


            powerUpTimers.add(powerUpTimer);
        }
    }

    public void updatePowerUpTimer() {
        if (powerUpTimer != null && !isTransitioningOut) {
            powerUpTimer.update(deltaP);
            powerUpTimer.render(powerupAnim, game.batch);
        }
        if (powerUpTimer != null && powerUpTimer.powerUpAnimation.isAnimationFinished(powerUpTimer.getStateTime())) {
            powerUpTimers.clear();
            powerUpTimer = null;

        }
    }

    public void runGemCountUpdateTimer() {
        if (gemCountUpdateTimer < 0) {
            gemCountUpdateTimer += deltaP;

            if (gemCountTimerDelay < 0) {
                if (gemCountComplete) {
                    gemCountTimerDelay = 0f;
                } else {
                    gemCountTimerDelay += deltaP;
                }
            }

        } else {
            if (gemCount >= 0 && !gemCountComplete && gemCountTimerDelay >= 0) {
                gemCountStarted = true;

                if (soundEnabled) {
                    itemSound.play();
                }

                if (gemCount > 0) {
                    gemCount--;
                    finalGemCount--;
                    gemsCaught++;
                }
                if (gemCount == 0) {
                    gemCountTimerDelay = -0.5f;
                    gemCountComplete = true;
                }
            }

            if (score >= 0 && gemCountTimerDelay == 0 && gemCountComplete && !scoreCountComplete) {
                scoreCountStarted = true;
                if (score >= 100) {
                    score -= 100;
                    gemsFromScore++;
                }
                if (score < 100) {
                    score = 0;
                    gemCountTimerDelay = -0.5f;
                    scoreCountComplete = true;
                }
                if (soundEnabled) {
                    itemSound.play();
                }
            }


            if (scoreCountComplete && gemCountComplete && gemCountTimerDelay == 0 && !recapComplete) {
                recapStarted = true;
                if (replayScreenGemCount < prefs.getGemCount()) {
                    replayScreenGemCount++;

                    if (soundEnabled) {
                        itemSound.play();
                    }
                } else if (gemsCaught + gemsFromScore == 0 && !recapComplete) {
                    if (soundEnabled) {
                        itemSound.play();
                    }
                    recapComplete = true;
                } else {
                    recapComplete = true;
                }
            }

            gemCountUpdateTimer = GEM_COUNT_UPDATE_TIMER + gemCountTimerDelay;
        }
    }

    public void runScoreTickerTimer() {
        if (scoreTickerTimer < 0) {
            scoreTickerTimer += deltaP;
        } else {
            score += 1;
            scoreUpdated = false;
            scoreTickerTimer = SCORE_TICKER_TIMER;
        }
    }

    public void runEnemyHitSoundTimer() {
        if (hitSoundTimer < 0) {
            hitSoundTimer += deltaP;
        } else {
            if (hitSound.isPlaying()) {
                hitSound1.play();
            } else {
                hitSound.play();
            }
            playHitSound = false;
            hitSoundTimer = -.08f;
        }
    }

    public void runBombUsedTimer() {
        if (bombUsedTimer < 0) {
            bombUsedTimer += deltaP;
            whiteFlash.draw(game.batch);
        } else {
            bombUsed = false;
            bombUsedTimer = -.05f;
        }
    }

    public void runMissileUsedTimer() { //missile item drop
        if (missileUsedTimer < 0) {
            missileUsedTimer += deltaP;
        } else {
            missileUsed = false;
            missileUsedTimer = -.1f;
        }
    }

    public void runRapidFireUsedTimer() {
        if (rapidFireUsedTimer < 0) {
            rapidFireUsedTimer += deltaP;
        } else {
            rapidFireUsed = false;
            rapidFireUsedTimer = -.1f;
        }
    }

    public void runMissileTimer() { //missile weapon
        if (missileTimer < 0) {
            addPowerUpTimer(MISSILE_TIMER);

            missileTimer += deltaP;

        } else {
            isMissile = false;
            missileTimer = MISSILE_TIMER;
        }
    }

    public void runRapidFireTimer() {
        if (rapidFireTimer < 0) {
            addPowerUpTimer(RAPID_FIRE_TIMER);

            rapidFireTimer += deltaP;
        } else {
            isRapidFire = false;
            rapidFireTimer = RAPID_FIRE_TIMER;

        }
    }

    public void runSpreadFireTimer() {
        if (spreadFireTimer < 0) {
            addPowerUpTimer(SPREAD_FIRE_TIMER);

            spreadFireTimer += deltaP;
        } else {
            isSpreadFire = false;
            spreadFireTimer = SPREAD_FIRE_TIMER;

        }
    }

    public void runHourglassTimer() {
        if (soundEnabled && mainMusic.isPlaying()) {
            mainMusic.setPitch(0.5f);
            bulletSound.setPitch(0.5f);
        }
        game.batch.setShader(invertedShader);

        if (hourglassTimer < 0) {
            addPowerUpTimer(HOURGLASS_TIMER * hourglassMultiplier);

            hourglassTimer += deltaP;
            hourglassMultiplier = .25f;
        } else {
            if (soundEnabled) {
                mainMusic.setPitch(1);
                bulletSound.setPitch(1);
                mainMusic.play();
            }

            isHourglass = false;
            resetShader();
            hourglassMultiplier = 1;
            hourglassTimer = HOURGLASS_TIMER;
        }
    }

    public void runShipHitTimer() {
        if (shipHitTimer < 0) {
            shipHitTimer += deltaP;
        } else {
            justHit = false;
            playerHitSoundPlayed = false;
            shipHitTimer = -2f;
        }
    }

    public void runEnemyHurtDelay(Enemy enemy) {
        if (hurtDelayTimer < 0) {
            hurtDelayTimer += deltaP;
        } else {
            enemy.setEnemyHurt(true);
            hurtEnemies.removeValue(enemy, true);
            hurtDelayTimer = -.03f;
        }
    }

    public void runExplosionDelay(Explosion explosion) {
        if (explosionDelayTimer < 0) {
            explosionDelayTimer += deltaP;
        } else {
            explosions.add(explosion);
            explosionsToDelay.removeValue(explosion, true);
            explosionDelayTimer = -.025f;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        deltaP = 0;
        runResumeCountdown = false;

        if (!isTransitioningOut && !isFadingOut) {
            isPaused = true;
        }
    }

    @Override
    public void resume() {
        if (Gdx.graphics.getDisplayMode().refreshRate != refreshRate) {
            refreshRate = Gdx.graphics.getDisplayMode().refreshRate;
            deltaList.clear();
        }
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        mainMusic.stop();
        game.setScreen(new MainMenu(game, assets));
    }
}
