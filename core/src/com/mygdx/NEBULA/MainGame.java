package com.mygdx.NEBULA;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static com.mygdx.NEBULA.ItemDrop.HOURGLASS_TIMER;
import static com.mygdx.NEBULA.ItemDrop.MISSILE_TIMER;
import static com.mygdx.NEBULA.ItemDrop.RAPID_FIRE_TIMER;
import static com.mygdx.NEBULA.Anim.DEFAULT_FRAME_DURATION;
import static com.mygdx.NEBULA.Enemy.ENEMY_SHIP_ID;
import static com.mygdx.NEBULA.Enemy.EYEBAT_ID;
import static com.mygdx.NEBULA.Enemy.LASER_TRAP_ID;
import static com.mygdx.NEBULA.EnemyBullet.blueShipBulletThreshold;
import static com.mygdx.NEBULA.EnemyBullet.greenShipBulletThreshold;
import static com.mygdx.NEBULA.EnemyBullet.purpleShipBulletThreshold;
import static com.mygdx.NEBULA.EnemyBullet.redShipBulletThreshold;
import static com.mygdx.NEBULA.EnemyBullet.whiteShipBulletThreshold;
import static com.mygdx.NEBULA.ItemDrop.BOMB_ID;
import static com.mygdx.NEBULA.ItemDrop.HOURGLASS_ID;
import static com.mygdx.NEBULA.ItemDrop.HOURGLASS_TIMER;
import static com.mygdx.NEBULA.ItemDrop.MISSILE_TIMER;
import static com.mygdx.NEBULA.ItemDrop.RAPID_FIRE_ID;
import static com.mygdx.NEBULA.ItemDrop.HEART_ID;
import static com.mygdx.NEBULA.ItemDrop.MAX_ITEM_SPAWN_TIME;
import static com.mygdx.NEBULA.ItemDrop.MIN_ITEM_SPAWN_TIME;
import static com.mygdx.NEBULA.ItemDrop.MISSILE_ID;
import static com.mygdx.NEBULA.ItemDrop.RAPID_FIRE_TIMER;


public class MainGame extends GameElements implements Screen{
    public Prefs prefs = new Prefs();
    Assets assets;
    Background background;
    GlyphLayout gl;
    ArrayList<Integer> shipPositions;

    Vector2 center;
    FloatArray vertices;

    ShaderProgram invertedShader;


    float fadeInOpacity = 1;
    float fadeOutOpacity = 0;

    float totalTransitionDist = SHIP_Y + Math.abs(SHIP_START_Y);
    float transitionDistTraveled = 0f;

    float stateTime = 0f;

    float bulletTimer = -2f;
    float enemyBulletDelay = -1f;

    float missileTimer = MISSILE_TIMER;
    float rapidFireTimer = RAPID_FIRE_TIMER;
    float hourglassTimer = HOURGLASS_TIMER;

    float shipHitTimer = -2.2f;
    float hurtTimer = -0.15f;
    float countDownTimer = 0f;
    float resumeCountdownTimer = -1.5f;
    float playerPosition = SHIP_X;

    float bulletThreshold = 0.4f;
    float musicVolume = 0.35f;
    float musicVolumeTemp;

    float deltaP; // delta that doesn't update when isPaused/resume countdown

    float eyebatSpawnTimer;

    float enemyShipSpawnTimer;
    float laserTrapSpawnTimer;

    float itemSpawnTimer;

    boolean isShipLeaving = false;
    boolean isTransitionedIn = false;

    boolean isTransitionedOut = false;
    boolean isTransitioningOut = false;
    boolean isRunningResumeCountdown = true;
    boolean runResumeCountdown = false;

    boolean eyebatsSpawning = false;
    boolean laserTrapsSpawning = false;
    boolean enemyShipsSpawning = false;

    boolean heartUsed = false;
    float heartUsedTimer = -1f;

    boolean hourglassUsed = false;
    float hourglassUsedTimer = -1f;

    boolean scoreUpdated = false;
    float scoreTickerTimer = SCORE_TICKER_TIMER;

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
    int randomDrop, randomColor, randomSpawnLocation;
    int lastItemDrop;
    int playButtonTapVal, yesButtonTapVal = 0, transitionInTapVal = 0;

    float songPausePosition, musicPosition;

    GameInterface gameInterface;

    boolean isPaused = false;
    boolean isAlive = true;
    boolean isResettingScreen = false;

    ShapeRenderer sr = new ShapeRenderer();

    MyInputProcessor inputProcessor = new MyInputProcessor();

    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Bullet> bulletsToRemove = new ArrayList<>();

    ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    ArrayList<EnemyBullet> enemyBulletsToRemove = new ArrayList<>();

    ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

    ArrayList<Explosion> explosions = new ArrayList<>();
    ArrayList<Explosion> explosionsToRemove =  new ArrayList<>();

    ArrayList<ItemDrop> itemDrops = new ArrayList<>();
    ArrayList<ItemDrop> itemsToRemove = new ArrayList<>();

    ArrayList<Float> deltaList = new ArrayList<>();
    float deltaSum;
    float speedIncrease;

    float hourglassMultiplier = 1;

    Player player;
    int health = 3;

    public MainGame(Main game, Assets assets, Background background) {
        this.game = game;
        this.assets = assets;
        gameInterface = new GameInterface(assets);
        this.background = background;


    }

    @Override
    public void show() {
        minEnemyShipSpawnTime = MIN_ENEMY_SHIP_SPAWN_TIME;
        maxEnemyShipSpawnTime = MAX_ENEMY_SHIP_SPAWN_TIME;

        minEyebatSpawnTime = MIN_EYEBAT_SPAWN_TIME;
        maxEyebatSpawnTime = MAX_EYEBAT_SPAWN_TIME;

        minLaserSpawnTime = MIN_LASER_TRAP_SPAWN_TIME;
        maxLaserSpawnTime = MAX_LASER_TRAP_SPAWN_TIME;

        shipPositions = new ArrayList<>();

        random = new Random();
        eyebatSpawnTimer = random.nextFloat() * (MAX_EYEBAT_SPAWN_TIME - MIN_EYEBAT_SPAWN_TIME) + MIN_EYEBAT_SPAWN_TIME;
        enemyShipSpawnTimer = random.nextFloat() * (MAX_ENEMY_SHIP_SPAWN_TIME - MIN_ENEMY_SHIP_SPAWN_TIME) + MIN_ENEMY_SHIP_SPAWN_TIME;
        laserTrapSpawnTimer = random.nextFloat() * (MAX_LASER_TRAP_SPAWN_TIME - MIN_LASER_TRAP_SPAWN_TIME) + MIN_LASER_TRAP_SPAWN_TIME;
        itemSpawnTimer = random.nextFloat() * (MAX_ITEM_SPAWN_TIME - MIN_ITEM_SPAWN_TIME) + MIN_ITEM_SPAWN_TIME;

        mainMusic = assets.assetManager.get(Assets.main_theme, Music.class);

        pauseSound = assets.assetManager.get(Assets.pause_sound, Sound.class);
        hitSound = assets.assetManager.get(Assets.hit_sound, Sound.class);
        bulletSound = assets.assetManager.get(Assets.bullet_sound, Sound.class);
        missileSound = assets.assetManager.get(Assets.missile_sound, Sound.class);
        heartSound = assets.assetManager.get(Assets.heart_sound, Sound.class);

        bombSound = assets.assetManager.get(Assets.bomb_sound, Music.class);
        bombSound.setVolume(0.1f);
        soundEnabled = prefs.hasSound();

        blackTransition = new Sprite(assets.assetManager.get(Assets.black_transition, Texture.class));
        blackTransition.setSize(SCREEN_WIDTH*1.5f, SCREEN_HEIGHT*1.5f);

        whiteFlash = new Sprite(assets.assetManager.get(Assets.white_flash, Texture.class));
        whiteFlash.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        shipSS = new Sprite(assets.assetManager.get(Assets.ship_ss, Texture.class));
        shipBlinkingSS = new Sprite(assets.assetManager.get(Assets.ship_blinking_ss, Texture.class));

        shipAnimation = Anim.createAnimation(shipSS, 4, DEFAULT_FRAME_DURATION*1.5f);
        shipBlinkingAnimation = Anim.createAnimation(shipBlinkingSS, 4, (DEFAULT_FRAME_DURATION*1.5f));

        textParameter.size = Gdx.graphics.getWidth()/40;
        menuScoreFont = generator.generateFont(textParameter);
        menuScoreFont.setColor(Color.valueOf("6a11f6"));

        textParameter.size = Gdx.graphics.getWidth()/22;
        confirmScreenFont = generator.generateFont(textParameter);
        confirmScreenFont.setColor(Color.valueOf("6a11f6"));

        textParameter.size = Gdx.graphics.getWidth()/10;
        gameOverFont = generator.generateFont(textParameter);
        gameOverFont.setColor(Color.valueOf("FFFFFF"));

        textParameter.size = Gdx.graphics.getWidth()/6;
        countdownFont = generator.generateFont(textParameter);
        countdownFont.setColor(Color.valueOf("FFFFFF"));

        gl = new GlyphLayout();
        Gdx.input.setInputProcessor(inputProcessor);
        player = new Player();

        ShaderProgram.pedantic = false;
        invertedShader = new ShaderProgram(Gdx.files.internal("shaders/invert.vsh"), Gdx.files.internal("shaders/invert.fsh"));

        game.batch.setShader(invertedShader);
        game.batch.setShader(null);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        game.batch.enableBlending();
        game.batch.begin();

        deltaList.add(delta);

        if(deltaList.size() >= 60) {
            for (int i = 0; i < deltaList.size(); i++) {
                deltaSum += deltaList.get(i);
            }
            delta = deltaSum / deltaList.size();

            if(isPaused || isRunningResumeCountdown)
                deltaP = 0;
            else
                deltaP = delta;

            deltaList.remove(0);
            deltaSum = 0;
        }

        blackTransition.draw(game.batch);
        if(!isPaused && !isShipLeaving) {
            updateSpawnRates(score);
        }

        if(!isMainMusicPlaying && soundEnabled)
            playMusic();

        if(!soundEnabled || isPaused || !isAlive || isRunningResumeCountdown){
            musicVolume = 0;
            isMainMusicPlaying = false;
        }
        isAlive = health > 0;
        if(!isAlive){
            isResettingScreen = true;
        }

        if (fadeInOpacity < 1) {
            musicPosition = mainMusic.getPosition();
            background.updateAndRender(deltaP, isAlive, isHourglass, score, starsAnim, game.batch, false, isResettingScreen, false, false);
            if(!isRunningResumeCountdown) {
                bulletTimer += deltaP;
                for (Enemy enemy : enemies) {
                    if (enemy.getId() == ENEMY_SHIP_ID) {
                        enemy.setEnemyBulletTimer(enemy.getEnemyBulletTimer() + deltaP * hourglassMultiplier);
                    }
                }

                if (isAlive)
                    runBulletTimers(); //Adds bullets/bullet sounds
            }
        }

        if(!isShipLeaving && isAlive) {
            updateBullets(game);
            updateEnemyBullets(game);
            updateEnemies();
            updateExplosions();
            updateItems();
        }
        if(!soundLoaded) {
            soundLoaded = true;
        }

        if(!isShipLeaving) {
            drawMainElements();
        }
        if(fadeInOpacity > 0) {
            fadeIn();
        }
        else if (fadeInOpacity <= 0) {
            isFadingIn = false;
        }
        if(!isRunningResumeCountdown) {
            if (isTransitionedIn) {

                if(isAlive)
                    runScoreTickerTimer();

                addEyebats();
                eyebatsSpawning = true;

                if (score >= 100) {
                    addEnemyShips();
                    enemyShipsSpawning = true;
                }

            }
            if (score >= 1000) {
                addLaserTraps();
                laserTrapsSpawning = true;
            }


            enemyBulletCollision(bombUsed);

            if (isAlive) {
                if (!bombUsed && !heartUsed && !missileUsed && !rapidFireUsed && score >= 100) {
                    addItemDrops();
                    shipItemCollision();
                }

                shipEnemyCollision();

                if(enemyShipsSpawning && enemyBullets.size() > 0)
                    shipBulletCollision();

                if(!isRunningResumeCountdown)
                    updatePlayer();
            }

            if (isAlive && !isTransitioningOut && !isTransitionedOut)
                transitionIn();

            if (isMissile)
                runMissileTimer();

            if (justHit)
                runShipHitTimer();

            if (missileUsed)
                runMissileUsedTimer();

            if (isRapidFire)
                runRapidFireTimer();

            if(isHourglass)
                runHourglassTimer();

            else if (bombUsed)
                runBombUsedTimer();

            else if (heartUsed)
                runHeartUsedTimer();

            else if (rapidFireUsed) {
                runRapidFireUsedTimer();
            }
            else if(hourglassUsed){
                runHourglassUsedTimer();
            }

            if (isEnemyHurt) {
                enemyHurt();
            }

            //Enables ship movement
            if (!isShipLeaving && isAlive && inputProcessor.getTapCount() != playButtonTapVal && (inputProcessor.getTapCount() != yesButtonTapVal)) {
                movePlayer();
            }
        }
        if((!isAlive || isPaused) && gameInterface.checkForSoundButtonTap(soundEnabled) && !gameInterface.getConfirmLeaveScreenOpen()){
            if(prefs.hasSound()) {
                prefs.setSound(false);
                soundEnabled = false;
            }
            else{
                prefs.setSound(true);
                soundEnabled = true;
                pauseSound.play(0.3f);
            }
        }

        //Pausing (pauses music/freezes delta)
        if(isTransitionedIn && !isPaused && !isTransitioningOut && !isRunningResumeCountdown && gameInterface.checkForPauseButtonTap()){
            if(soundEnabled)
                pauseSound.play(0.3f);

            isPaused = true;
        }

        if (isPaused && !isRunningResumeCountdown) {
            songPausePosition = mainMusic.getPosition();
            gameInterface.drawPauseScreen(game, menuScoreFont, score);

            if (gameInterface.checkForPlayButtonTap() && !isTransitioningOut && !isFadingIn && !isTransitioningIn &!gameInterface.getConfirmLeaveScreenOpen()){
                playButtonTapVal = inputProcessor.getTapCount();
                runResumeCountdown = true;
                isPaused = false;

                if(soundEnabled){
                    game.playSound.play(0.3f);
                }
            }
            if(gameInterface.checkForHomeButtonTap() || gameInterface.getConfirmLeaveScreenOpen()){
                if(!gameInterface.getConfirmLeaveScreenOpen()) {
                    gameInterface.setConfirmLeaveScreenOpen(true);
                    if (soundEnabled)
                        pauseSound.play(0.3f);
                }


                gameInterface.drawConfirmLeave(game, confirmScreenFont);

                if(gameInterface.checkForYesButtonTap()) {
                    yesButtonTapVal = inputProcessor.getTapCount();

                    if (soundEnabled)
                        game.playSound.play(0.3f);
                    isShipLeaving = true;
                }
                gameInterface.checkForNoButtonTap(game, soundEnabled);

            }
        }
        if(runResumeCountdown) {
            runResumeCountdown(delta);
            isRunningResumeCountdown = true;
        }

        else{
            isRunningResumeCountdown = false;
            resumeCountdownTimer = -1.5f;
        }

        if(!isAlive){
            game.batch.setShader(null);

            if(inputProcessor.getTapCount() == 0)
                transitionOut(SHIP_X);
            else
                transitionOut(CURRENT_SHIP_X);
            if(isTransitionedOut && !isFadingOut) {
                gameInterface.drawReplayScreen(game, menuScoreFont, gameOverFont, newHighscore);
            }

            if (gameInterface.checkForReplayButtonTap() && isTransitionedOut){
                resetScreen();
            }
            else if(isTransitionedOut){
                if(gameInterface.checkForHomeButtonTap() || gameInterface.getConfirmLeaveScreenOpen()) {
                    if (!gameInterface.getConfirmLeaveScreenOpen()) {

                        gameInterface.setConfirmLeaveScreenOpen(true);
                        if (soundEnabled)
                            pauseSound.play(0.3f);
                    }


                    if (!isFadingOut) {
                        gameInterface.drawConfirmLeave(game, confirmScreenFont);

                        if (gameInterface.checkForYesButtonTap()) {
                            yesButtonTapVal = inputProcessor.getTapCount();

                            if (soundEnabled)
                                game.playSound.play(0.3f);
                            isShipLeaving = true;
                        }
                        gameInterface.checkForNoButtonTap(game, soundEnabled);

                    }

                }
            }
        }

        if(isShipLeaving){
            game.batch.setShader(null);

            isPaused = false;
            transitionOut(CURRENT_SHIP_X);
            fadeOut();
        }

        game.batch.end();
    }

    public void updateSpawnRates(int score) {
        if (score != prevScore) {
            float scoreMultiplier = score - prevScore;
            if(score < 3000) {
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
            }
            else{
                if (eyebatsSpawning) {
                    maxEyebatSpawnTime -= (.00015 * scoreMultiplier) / 5f;
                    minEyebatSpawnTime -= (.00005 * scoreMultiplier) / 5f;
                }
                if (enemyShipsSpawning) {
                    maxEnemyShipSpawnTime -= (.00015 * scoreMultiplier) / 5f;
                    minEnemyShipSpawnTime -= (.00005 * scoreMultiplier) / 5f;
                }
                if (laserTrapsSpawning) {
                    maxLaserSpawnTime -= (.0005 * scoreMultiplier) / 5f;
                    minLaserSpawnTime -= (.00025 * scoreMultiplier) / 5f;
                }
                if (speedIncrease < 1) {
                    speedIncrease += (0.00075 * scoreMultiplier) / 5f;
                }
            }
            prevScore = score;
        }
    }
    public void enemyHurt(){
        for(Enemy enemy : enemies){
            if(enemy.isHurt()){
                if(enemy.getHurtTimer() <= 0){
                    enemy.setHurtTimer(enemy.getHurtTimer() + deltaP);
                }
                else{
                    enemy.setEnemyHurt(false);
                    enemy.setHurtTimer(hurtTimer);
                }
            }
        }
    }

    public void resetScreen(){
        game.batch.setShader(null);
        hourglassMultiplier = 1;

        health = 3;
        score = 0;
        songPausePosition = 0f;
        countDownTimer = 0f;

        bulletTimer = -2f;
        speedIncrease = 0f;

        minEnemyShipSpawnTime = MIN_ENEMY_SHIP_SPAWN_TIME;
        maxEnemyShipSpawnTime = MAX_ENEMY_SHIP_SPAWN_TIME;

        minEyebatSpawnTime = MIN_EYEBAT_SPAWN_TIME;
        maxEyebatSpawnTime = MAX_EYEBAT_SPAWN_TIME;

        minLaserSpawnTime = MIN_LASER_TRAP_SPAWN_TIME;
        maxLaserSpawnTime = MAX_LASER_TRAP_SPAWN_TIME;

        prevScore = 0;

        isAlive = true;
        justHit = false;
        isTransitionedIn = false;
        isTransitionedOut = false;
        isTransitioningOut = false;
        isTransitioningIn = true;
        isMissile = false;
        isRapidFire = false;
        isHourglass = false;
        transitionDistTraveled = 0f;
        isFadingOut = false;
        isFadingIn = false;

        laserTrapsSpawning = false;
        eyebatsSpawning = false;
        enemyShipsSpawning = false;

        bombUsed = false;
        heartUsed = false;
        missileUsed = false;
        rapidFireUsed = false;

        if(explosions.size() > 0)
            explosions.clear();

        if(enemies.size() > 0)
            enemies.clear();

        if(bullets.size() > 0)
            bullets.clear();

        if(itemDrops.size() > 0)
            itemDrops.clear();

        if(enemyBullets.size() > 0)
            enemyBullets.clear();

        if(shipPositions.size() > 0)
            shipPositions.clear();

        SHIP_X = SCREEN_WIDTH / 2 - SHIP_WIDTH / 2;
        CURRENT_SHIP_X = SHIP_X;

        isResettingScreen = false;
        isMainMusicPlaying = false;
        newHighscore = false;
    }

    public void playMusic(){
        if(soundEnabled && !isPaused && isAlive && !isTransitioningOut && !isRunningResumeCountdown) {
            musicVolume = 0.35f;
            mainMusic.setPosition(songPausePosition);

        }
        else if(soundEnabled && !isAlive && !isPaused && isTransitioningOut) {
            musicVolume = 0f;
        }
        mainMusic.setLooping(true);

        if(!isPaused && !isTransitioningOut && !isMainMusicPlaying && !isRunningResumeCountdown){
            mainMusic.play();
        }
        if(musicVolume != musicVolumeTemp){
            mainMusic.setVolume(musicVolume);
            musicVolumeTemp = musicVolume;
        }

        isMainMusicPlaying = true;
    }

    public void movePlayer(){
        if (!isPaused && isTransitionedIn && !isShipLeaving && !isFadingOut) {
            if (!(Gdx.input.getY() < 6 * gameInterface.pauseButton.getHeight())) {
                if (Gdx.input.isTouched()) {
                    if(Gdx.input.getX() < SHIP_X - SHIP_WIDTH/2) {
                        playerPosition = SHIP_X - deltaP * 50 * (SHIP_X - (Gdx.input.getX() - SHIP_WIDTH / 2));

                        if(playerPosition >= 0 && playerPosition <= SCREEN_WIDTH - SHIP_WIDTH) {
                            SHIP_X -= deltaP * 50 * (SHIP_X - (Gdx.input.getX() - SHIP_WIDTH / 2));
                        }
                    }
                    if(Gdx.input.getX() > SHIP_X - SHIP_WIDTH/2) {
                        playerPosition = SHIP_X + deltaP * 30 * (Gdx.input.getX() - SHIP_X - SHIP_WIDTH / 2);

                        if (playerPosition >=0 && playerPosition <= SCREEN_WIDTH - SHIP_WIDTH) {
                            SHIP_X += deltaP * 50 * (Gdx.input.getX() - SHIP_X - SHIP_WIDTH / 2);
                        }
                    }
                }
                CURRENT_SHIP_X = SHIP_X;
            }
        }
    }

    public void runResumeCountdown(float delta){
        resumeCountdownTimer += delta;
        if (resumeCountdownTimer >= -1) {
            if (resumeCountdownTimer < -0.66f) {
                gl.setText(countdownFont, "3", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                countdownFont.draw(game.batch, "3", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2 + gl.height);

            } else if (resumeCountdownTimer <= -0.33) {
                gl.setText(countdownFont, "2", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                countdownFont.draw(game.batch, "2", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2 + gl.height);

            } else if (resumeCountdownTimer < 0) {
                gl.setText(countdownFont, "1", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                countdownFont.draw(game.batch, "1", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2 + gl.height);

            } else {
                if (resumeCountdownTimer < 0.25f) {
                    gl.setText(countdownFont, "GO", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                    countdownFont.draw(game.batch, "GO", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2 + gl.height);

                } else {
                    runResumeCountdown = false;
                    resumeCountdownTimer = -1.5f;
                }
            }
        }
    }

    public void transitionIn(){
        if(SHIP_START_Y <= SHIP_Y) {
            SHIP_X = SCREEN_WIDTH / 2 - SHIP_WIDTH / 2;
            SHIP_START_Y += 1.6 * SHIP_Y * deltaP;
            transitionDistTraveled += 1.6 * SHIP_Y * deltaP;
            transitionInTapVal = inputProcessor.getTapCount();
        }

        if (transitionDistTraveled <= totalTransitionDist/3) {
            gl.setText(countdownFont, "3", Color.WHITE, SCREEN_WIDTH, Align.center, true);
            countdownFont.draw(game.batch, "3", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2 + gl.height/2);
        }
        else if (transitionDistTraveled <= 2*totalTransitionDist/3) {
            gl.setText(countdownFont, "2", Color.WHITE, SCREEN_WIDTH, Align.center, true);
            countdownFont.draw(game.batch, "2", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2 + gl.height/2);
        }
        else if (transitionDistTraveled < totalTransitionDist) {
            gl.setText(countdownFont, "1", Color.WHITE, SCREEN_WIDTH, Align.center, true);
            countdownFont.draw(game.batch, "1", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2 + gl.height/2);
        }
        else if (transitionDistTraveled >= totalTransitionDist && SHIP_START_Y >= SHIP_Y) {
            if(countDownTimer < 0.25f) {
                countDownTimer += deltaP;
                gl.setText(countdownFont, "GO", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                countdownFont.draw(game.batch, "GO", (SCREEN_WIDTH - gl.width) / 2, SCREEN_HEIGHT / 2 + gl.height/2);
            }
            isTransitionedIn = true;
            isTransitioningIn = false;
        }
        else{
            isTransitionedIn = true;
            isTransitioningIn = false;
        }
    }

    public void transitionOut(float SHIP_X_TRANSITION_OUT){

        if(SHIP_START_Y >= -3*SHIP_HEIGHT){
            isTransitionedOut = false;
            isTransitioningOut = true;
        }
        if(!isTransitionedOut) {
            SHIP_START_Y -= 1.6 * SHIP_Y * deltaP;

        }

        shipAnim.drawAnim(shipAnimation, stateTime, SHIP_X_TRANSITION_OUT, SHIP_START_Y, SHIP_WIDTH, SHIP_HEIGHT,   true, game.batch, true);

        if(SHIP_START_Y <= -3*SHIP_HEIGHT){
            isTransitionedOut = true;
        }
        if(prefs.getHighScore() < score) {
            prefs.setHighScore(score);
            newHighscore  = true;
        }

    }

    public void fadeOut(){
        isFadingOut = true;
        fadeOutOpacity += 0.8f * deltaP;
        blackTransition.setColor(0,0,0, fadeOutOpacity);

        if(fadeOutOpacity >= 1f){
            dispose();
        }

        blackTransition.draw(game.batch);
    }

    public void fadeIn(){
        isFadingIn = true;
        fadeInOpacity -= 0.6f * deltaP;
        blackTransition.setColor(0,0,0, fadeInOpacity);
        blackTransition.draw(game.batch);
    }

    public void drawMainElements(){
        if(!isRunningResumeCountdown)
            stateTime += deltaP / 6;

        if(isTransitioningIn) {
            shipAnim.drawAnim(shipAnimation, stateTime, SHIP_X, SHIP_START_Y, SHIP_WIDTH, SHIP_HEIGHT, true, game.batch, true);
        }
        else if(!justHit && health != 0)
            shipAnim.drawAnim(shipAnimation, stateTime, SHIP_X, SHIP_START_Y, SHIP_WIDTH, SHIP_HEIGHT,   true, game.batch);
        else if(!isTransitioningOut){
            shipAnim.drawAnim(shipBlinkingAnimation, stateTime, SHIP_X, SHIP_START_Y, SHIP_WIDTH, SHIP_HEIGHT,   true, game.batch,true);
        }
        if(isTransitionedIn) {
            gl.setText(Main.scoreFont, String.valueOf(score));

            if(Gdx.app.getType() == Application.ApplicationType.iOS) {
                Main.scoreFont.draw(game.batch, gl, SCORE_X, SCREEN_HEIGHT - gl.height * 2f);
            }
            else{
                Main.scoreFont.draw(game.batch, gl, SCORE_X, SCREEN_HEIGHT - gl.height);
            }
            gameInterface.drawTopUI(game, isPaused || isRunningResumeCountdown, health, isAlive, isTransitionedIn);
        }
    }

    public void runBulletTimers(){
        if(isMissile)
            bulletThreshold = 0.55f;
        else if(isRapidFire)
            bulletThreshold = 0.225f;
        else
            bulletThreshold = 0.4f;

        if (bulletTimer > bulletThreshold) {
            if (score < 99999 && !isShipLeaving) {

               if(soundEnabled) {
                   if (isTransitionedIn) {
                       if (isMissile) {
                           missileSound.play(0.1f);
                       }
                       else {
                           bulletSound.play(0.05f);
                       }
                   }
               }
            }
            if (!isShipLeaving && SHIP_START_Y >= SHIP_Y && isAlive) {
                addBullets();
            }
            bulletTimer -= bulletThreshold;
        }

        if(enemyShipsSpawning) {
            for (Enemy enemy : enemies) {
                if (enemy.getId() == (ENEMY_SHIP_ID)) {
                    if (enemy.getEnemyBulletTimer() > enemy.getEnemyBulletThreshold()) {
                        if (!isShipLeaving && SHIP_START_Y >= SHIP_Y) {
                            addEnemyBullets(enemy);
                            enemy.setEnemyBulletTimer(enemy.getEnemyBulletTimer() - enemy.getEnemyBulletThreshold());
                        }
                    }
                }
            }
        }
    }

    public void addBullets(){
        if(isMissile) {
            bullets.add(new Bullet(SHIP_X, true, false, assets));
        }
        else if(isRapidFire) {
            bullets.add(new Bullet(SHIP_X + SHIP_WIDTH * (4/27f), false, true, assets));
            bullets.add(new Bullet(SHIP_X + SHIP_WIDTH - SHIP_WIDTH * (6/27f), false, true, assets));
        }
        else {
            bullets.add(new Bullet(SHIP_X + SHIP_WIDTH * (4/27f), false, false, assets));
            bullets.add(new Bullet(SHIP_X + SHIP_WIDTH - SHIP_WIDTH * (6/27f), false, false, assets));
            missileTimer = MISSILE_TIMER;
        }
    }

    public void updateBullets(Main game) {
        for (Bullet bullet : bullets) {
            bullet.update(deltaP, isHourglass);
            if(bullet.isMissile())
                bullet.render(missileAnim, deltaP, Bullet.MISSILE_WIDTH, Bullet.MISSILE_HEIGHT, game.batch);
            else
                bullet.render(game.batch, deltaP, Bullet.BULLET_WIDTH, Bullet.BULLET_HEIGHT);

            if (bullet.remove) {
                bulletsToRemove.add(bullet);
            }
        }
    }

    public void addEnemyBullets(Enemy enemy){
        if(enemy.getId() == (ENEMY_SHIP_ID) && enemy.getEnemyBulletTimer() > enemy.getEnemyBulletThreshold()){
            enemyBullets.add(new EnemyBullet(enemy.ENEMY_X + ENEMY_SHIP_WIDTH * (5/31f), enemy.ENEMY_Y + enemy.ENEMY_HEIGHT * (3/27f), RED_ID, assets));
            enemyBullets.add(new EnemyBullet(enemy.ENEMY_X + ENEMY_SHIP_WIDTH - ENEMY_SHIP_WIDTH * (7/31f), enemy.ENEMY_Y + enemy.ENEMY_HEIGHT * (3/27f), RED_ID, assets));
        }
    }

    public void updateEnemyBullets(Main game) {
        for (EnemyBullet enemyBullet : enemyBullets) {

            enemyBullet.update(deltaP * hourglassMultiplier);
            enemyBullet.render(game.batch, EnemyBullet.ENEMY_BULLET_WIDTH, EnemyBullet.ENEMY_BULLET_HEIGHT);

            if (enemyBullet.remove) {
                enemyBulletsToRemove.add(enemyBullet);
            }
        }
    }

    public void addEyebats() {
        eyebatSpawnTimer -= deltaP * hourglassMultiplier;
        if (eyebatSpawnTimer <= 0) {
            randomSpawnLocation = random.nextInt((int) ((int) SCREEN_WIDTH- BLUE_EYEBAT_WIDTH));

            if(score <= 1000){
                enemies.add(new Enemy(EYEBAT_ID, BLUE_ID, 1, randomSpawnLocation, BLUE_EYEBAT_WIDTH, BLUE_EYEBAT_HEIGHT, 0.35f + speedIncrease / 1.5f, 0.8f + speedIncrease / 1.5f, (float) (DEFAULT_FRAME_DURATION * (1.25 - speedIncrease / 2)), false, hurtTimer, assets));
                eyebatSpawnTimer = random.nextFloat() * (maxEyebatSpawnTime - minEyebatSpawnTime) + minEyebatSpawnTime;

            }
            else if(score <= 2000){
                enemies.add(new Enemy(EYEBAT_ID, GREEN_ID, 2, randomSpawnLocation, GREEN_EYEBAT_WIDTH,  GREEN_EYEBAT_HEIGHT, 0.8f + speedIncrease / 1.5f, 0.4f + speedIncrease / 1.5f, (float) (DEFAULT_FRAME_DURATION * (1.5 - speedIncrease / 2)), false, hurtTimer, assets));
                eyebatSpawnTimer = random.nextFloat() * (maxEyebatSpawnTime - minEyebatSpawnTime) + minEyebatSpawnTime;
            }
            else if(score <= 3000){
                enemies.add(new Enemy(EYEBAT_ID, RED_ID, 2, randomSpawnLocation, RED_EYEBAT_WIDTH, RED_EYEBAT_HEIGHT, 0.65f + speedIncrease / 1.5f, 0.2f + speedIncrease / 1.5f, (float) (DEFAULT_FRAME_DURATION * (1.5 - speedIncrease / 2)), false, hurtTimer, assets));
                eyebatSpawnTimer = random.nextFloat() * (maxEyebatSpawnTime - minEyebatSpawnTime) + minEyebatSpawnTime;
            }
            else if(score <= 4000){
                enemies.add(new Enemy(EYEBAT_ID, PURPLE_ID, 3, randomSpawnLocation, 1.4f * BLUE_EYEBAT_WIDTH, 1.4f * BLUE_EYEBAT_HEIGHT, 0.5f + speedIncrease / 1.75f, 0.2f + speedIncrease / 1.75f, (float)(DEFAULT_FRAME_DURATION * (2.5 - speedIncrease / 2)), false, hurtTimer, assets));
                eyebatSpawnTimer = random.nextFloat() * (1.45f*maxEyebatSpawnTime - 1.45f*minEyebatSpawnTime) + 1.45f*minEyebatSpawnTime;
            }
            else{
                enemies.add(new Enemy(EYEBAT_ID, WHITE_ID, 4, randomSpawnLocation, 1.6f * BLUE_EYEBAT_WIDTH, 1.6f * BLUE_EYEBAT_HEIGHT, 0.4f + speedIncrease / 1.75f, 0.18f + speedIncrease / 1.75f, (float)(DEFAULT_FRAME_DURATION * (2.5 - speedIncrease / 2)), false, hurtTimer, assets));
                eyebatSpawnTimer = random.nextFloat() * (1.65f*maxEyebatSpawnTime - 1.65f*minEyebatSpawnTime) + 1.65f*minEyebatSpawnTime;
            }
        }
    }

    public void addEnemyShips(){
        randomColor = random.nextInt(2);
        enemyShipSpawnTimer -= deltaP * hourglassMultiplier;;
        if(enemyShipSpawnTimer <= 0 && 3 - shipPositions.size() >= 0) {
            int i = 3;
            int position = 0;
            for(Enemy enemy : enemies) {
                if(enemy.getId() == (ENEMY_SHIP_ID) && !shipPositions.contains(enemy.getPosition())){
                    shipPositions.add(enemy.getPosition());
            }
                if(shipPositions.contains(i)) {
                    i--;
                }
                else {
                    position = i;
                }
            }

            if(score <= 1000){
                enemies.add(new Enemy(ENEMY_SHIP_ID, BLUE_ID, 3, random.nextInt(((int) SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                        1.25f + speedIncrease, 1.25f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, blueShipBulletThreshold,
                        false, hurtTimer, position, assets));

                enemyShipSpawnTimer = random.nextFloat() * (maxEnemyShipSpawnTime - minEnemyShipSpawnTime) + minEnemyShipSpawnTime;
            }
            else if(score <= 2000){
                enemies.add(new Enemy(ENEMY_SHIP_ID, GREEN_ID, 4, random.nextInt(((int) SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                        1.15f + speedIncrease, 1.15f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, greenShipBulletThreshold,
                        false, hurtTimer, position, assets));

                enemyShipSpawnTimer = random.nextFloat() * (1.2f*maxEnemyShipSpawnTime - 1.2f*minEnemyShipSpawnTime) + 1.2f*minEnemyShipSpawnTime;
            }
            else if(score <= 3000){
                enemies.add(new Enemy(ENEMY_SHIP_ID, RED_ID, 5, random.nextInt(((int) SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                        0.95f + speedIncrease, 0.95f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, redShipBulletThreshold,
                        false, hurtTimer, position, assets));

                enemyShipSpawnTimer = random.nextFloat() * (1.2f*maxEnemyShipSpawnTime - 1.2f*minEnemyShipSpawnTime) + 1.2f*minEnemyShipSpawnTime;
            }
            else if(score <= 4000){
                enemies.add(new Enemy(ENEMY_SHIP_ID, PURPLE_ID, 5, random.nextInt(((int) SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                        0.85f + speedIncrease, 0.85f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, purpleShipBulletThreshold,
                        false, hurtTimer, position, assets));

                enemyShipSpawnTimer = random.nextFloat() * (1.2f*maxEnemyShipSpawnTime - 1.2f*minEnemyShipSpawnTime) + 1.2f*minEnemyShipSpawnTime;
            }
            else {
                enemies.add(new Enemy(ENEMY_SHIP_ID, WHITE_ID, 5, random.nextInt(((int) SCREEN_WIDTH - (int) ENEMY_SHIP_WIDTH)), ENEMY_SHIP_WIDTH, ENEMY_SHIP_HEIGHT,
                        0.75f + speedIncrease, 0.75f + speedIncrease, DEFAULT_FRAME_DURATION * 1.5f, enemyBulletDelay, whiteShipBulletThreshold,
                        false, hurtTimer, position, assets));

                enemyShipSpawnTimer = random.nextFloat() * (1.3f*maxEnemyShipSpawnTime - 1.3f*minEnemyShipSpawnTime) + 1.3f*minEnemyShipSpawnTime;
            }
        }
    }

    public void addLaserTraps(){
        laserTrapSpawnTimer -= deltaP * hourglassMultiplier;;
        if(laserTrapSpawnTimer <= 0) {
            laserTrapSpawnTimer = random.nextFloat() * (maxLaserSpawnTime - minLaserSpawnTime) + minLaserSpawnTime;

            enemies.add(new Enemy(LASER_TRAP_ID, random.nextInt(((int)SCREEN_WIDTH - (int)LASER_TRAP_H_WIDTH)), LASER_TRAP_H_WIDTH, LASER_TRAP_H_HEIGHT,
                    0, 0.65f + speedIncrease, DEFAULT_FRAME_DURATION*1.5f, assets));

        }
    }

    public void updateEnemies(){
        for (Enemy enemy : enemies) {
            if(isAlive) {

                enemy.update(deltaP, enemy, isHourglass);

                switch (enemy.getId()) {
                    case EYEBAT_ID:
                        enemy.render(eyebatAnim, enemy, deltaP, isPaused , game.batch);
                        break;
                    case ENEMY_SHIP_ID:
                        enemy.render(enemyShipAnim, enemy, deltaP, isPaused , game.batch);
                        break;
                    case LASER_TRAP_ID:
                         enemy.render(laserTrapHAnim, enemy, deltaP, isPaused , game.batch);
                         break;
                }
            }

            if (enemy.remove) {
                enemiesToRemove.add(enemy);
            }
        }
    }

    public void addItemDrops() {
        itemSpawnTimer -= deltaP;
        if (itemSpawnTimer <= 0) {

            randomDrop = random.nextInt(5);

            if(randomDrop == lastItemDrop){
                addItemDrops();
            }
            else {
                itemSpawnTimer = random.nextFloat() * (MAX_ITEM_SPAWN_TIME - MIN_ITEM_SPAWN_TIME) + MIN_ITEM_SPAWN_TIME;
                if (randomDrop == 0) {
                    if(health == 3)
                        addItemDrops();
                    else
                        itemDrops.add(new ItemDrop(random.nextInt((int) (Gdx.graphics.getWidth() - ItemDrop.HEART_WIDTH)), ItemDrop.HEART_HEIGHT, ItemDrop.HEART_WIDTH, HEART_ID, assets));
                }
                else if (randomDrop == 1) {
                    itemDrops.add(new ItemDrop(random.nextInt((int) (Gdx.graphics.getWidth() - ItemDrop.BOMB_WIDTH)), ItemDrop.BOMB_HEIGHT, ItemDrop.BOMB_WIDTH, BOMB_ID, assets));
                }
                else if (randomDrop == 2) {
                    itemDrops.add(new ItemDrop(random.nextInt((int) (Gdx.graphics.getWidth() - ItemDrop.MISSILE_WIDTH)), ItemDrop.MISSILE_HEIGHT, ItemDrop.MISSILE_WIDTH, MISSILE_ID, assets));
                }
                else if( randomDrop ==3){
                    itemDrops.add(new ItemDrop(random.nextInt((int) (Gdx.graphics.getWidth() - ItemDrop.RAPID_FIRE_WIDTH)), ItemDrop.RAPID_FIRE_HEIGHT, ItemDrop.RAPID_FIRE_WIDTH, RAPID_FIRE_ID, assets));
                }
                else{
                    if(score < 1000)
                        addItemDrops();
                    else
                        itemDrops.add(new ItemDrop(random.nextInt((int) (Gdx.graphics.getWidth() - ItemDrop.HOURGLASS_WIDTH)), ItemDrop.HOURGLASS_HEIGHT, ItemDrop.HOURGLASS_WIDTH, HOURGLASS_ID, assets));
                }
            }
            lastItemDrop = randomDrop;
        }
    }

    public void updateItems(){
        for (ItemDrop itemDrop : itemDrops) {
                itemDrop.update(deltaP);
            switch (itemDrop.getItemId()) {
                case HEART_ID:
                    itemDrop.render(ItemDrop.HEART_WIDTH, ItemDrop.HEART_HEIGHT, deltaP, game.batch);
                    break;
                case BOMB_ID:
                    itemDrop.render(ItemDrop.BOMB_WIDTH, ItemDrop.BOMB_HEIGHT, deltaP, game.batch);
                    break;
                case MISSILE_ID:
                    itemDrop.render(ItemDrop.MISSILE_WIDTH, ItemDrop.MISSILE_HEIGHT, deltaP, game.batch);
                    break;
                case RAPID_FIRE_ID:
                    itemDrop.render(ItemDrop.RAPID_FIRE_WIDTH, ItemDrop.RAPID_FIRE_HEIGHT, deltaP, game.batch);
                case HOURGLASS_ID:
                    itemDrop.render(ItemDrop.HOURGLASS_WIDTH, ItemDrop.HOURGLASS_HEIGHT, deltaP, game.batch);
            }

            if (itemDrop.remove) {
                itemsToRemove.add(itemDrop);
            }
        }
    }

    public void enemyBulletCollision(boolean bombUsed){
        boolean pointsEarned = false;
        for(Bullet bullet : bullets) {

//            game.batch.end();
//            sr.begin((ShapeRenderer.ShapeType.Line));
//            sr.polygon(bullet.getCollision().getPolygon().getVertices());
//            sr.end();
//            game.batch.begin();

            for (Enemy enemy : enemies) {
                if (enemy.getEnemyY() < SCREEN_HEIGHT) {
                    if (bombUsed || Collision.isColliding(bullet.getCollision(), enemy.getCollision())) {

                        if (!bombUsed && !bullet.isMissile() && enemy.getId() != LASER_TRAP_ID)
                            bulletsToRemove.add(bullet);

                        if (bullet.isMissile() && bullet.getBulletY() + Bullet.MISSILE_HEIGHT < SCREEN_HEIGHT && enemy.getId() != LASER_TRAP_ID) {
                            enemy.HP = 0;
                            if (soundEnabled)
                                playHitSound = true;
                        }

                        if (!bullet.isMissile() && enemy.getId() != LASER_TRAP_ID && !Collision.isColliding(enemy.getCollision(), player.getCollision())) {
                            enemy.HP -= 1;
                            if (soundEnabled && enemy.getHP() == 0) {
                                playHitSound = true;

                            }
                            isEnemyHurt = true;
                            enemy.setEnemyHurt(true);
                        }

                        if (enemy.HP <= 0 && enemy.getId() != LASER_TRAP_ID) {
                            if (enemy.getId() == (ENEMY_SHIP_ID))
                                shipPositions.removeAll(Collections.singletonList(enemy.getPosition()));
                            enemiesToRemove.add(enemy);

                            if (enemy.getId() != ENEMY_SHIP_ID)
                                explosions.add(new Explosion(enemy.getEnemyX(), enemy.getEnemyY() - enemy.getHeight() / 4f, enemy.getWidth(), assets));
                            else
                                explosions.add(new Explosion(enemy.getEnemyX(), enemy.getEnemyY(), enemy.getWidth() * 1.5f, assets));

                            if (enemy.getId() == (EYEBAT_ID) && !pointsEarned) {
                                switch (enemy.getColorId()) {

                                    case BLUE_ID:
                                    case RED_ID:
                                    case GREEN_ID:
                                        score += 10;
                                        break;
                                    case PURPLE_ID:
                                    case WHITE_ID:
                                        score += 15;
                                        break;
                                }

                                pointsEarned = true;
                            } else if (enemy.getId() == (ENEMY_SHIP_ID) && !pointsEarned && enemy.getEnemyY() < SCREEN_HEIGHT) {
                                score += 10;
                                pointsEarned = true;

                            }
                        }
                    }
                }
            }

            if (playHitSound) {
                runEnemyHitSoundTimer();
            }
        }
        bullets.removeAll(bulletsToRemove);
        enemies.removeAll(enemiesToRemove);
        explosions.removeAll(explosionsToRemove);

    }

    public void updateExplosions(){
        for (Explosion explosion : explosions) {
            if(explosion.y < SCREEN_HEIGHT && explosion.y > 0) {
                    explosion.update(deltaP);

                    explosion.render(explosionAnim,  deltaP, game.batch);

            }
            if (explosion.remove) {
                explosionsToRemove.add(explosion);
            }
        }
    }

    public void updatePlayer(){
        player.update();
    }

    public void shipEnemyCollision(){
//
//            game.batch.end();
//            sr.begin((ShapeRenderer.ShapeType.Line));
//
//            sr.polygon(player.getCollision().getPolygon().getVertices());
//            sr.end();
//            game.batch.begin();

        for(Enemy enemy : enemies){
//            game.batch.end();
//            sr.begin((ShapeRenderer.ShapeType.Line));
//            sr.setColor(0,1,0,1);
//            sr.polygon(enemy.getCollision().getPolygon().getVertices());
//            sr.end();
//            game.batch.begin();
            if(Collision.isColliding(enemy.getCollision(), player.getCollision())){

                if(soundEnabled && !playerHitSoundPlayed){
                    hitSound.play(0.2f);
                    playerHitSoundPlayed = true;
                }

                if(enemy.getId() != LASER_TRAP_ID)
                    enemiesToRemove.add(enemy);
                if(enemy.getId() != LASER_TRAP_ID)
                    explosions.add(new Explosion(enemy.getEnemyX(), enemy.getEnemyY()- enemy.getHeight()/4f, enemy.getWidth(), assets));

                if(health > 0 && !justHit){
                    justHit = true;
                    health -= 1;
                    Gdx.input.vibrate(50);
                }

                if(health == 0){
                    isAlive = false;
                }
            }
        }
    }

    public void shipBulletCollision(){
        for(EnemyBullet enemyBullet : enemyBullets){
            if(Collision.isColliding(enemyBullet.getCollision(), player.getCollision())){

                if(soundEnabled){
                    hitSound.play(0.2f);
                }
                enemyBulletsToRemove.add(enemyBullet);

                if(health > 0 && !justHit){
                    justHit = true;
                    health -= 1;
                    Gdx.input.vibrate(50);

                }
                if(health == 0){
                    isAlive = false;
                }
            }
        }
        enemyBullets.removeAll(enemyBulletsToRemove);
    }

    public void shipItemCollision() {
        for (ItemDrop itemDrop : itemDrops) {
            if (Collision.isColliding(itemDrop.getCollision(),player.getCollision())) {
                itemsToRemove.add(itemDrop);

                switch (itemDrop.getItemId()) {
                    case HEART_ID:
                        score += 50;

                        if (soundEnabled)
                            heartSound.play(0.15f);


                        if (health > 0 && health < 3 && !heartUsed) {
                            health += 1;
                        }
                        heartUsed = true;
                        break;

                    case BOMB_ID:
                        if (!bombUsed) {
                            score += 25;
                            bombUsed = true;

                            if (soundEnabled)
                                bombSound.play();

                            for (Enemy enemy : enemies) {
                                enemy.setHP(0);

                            }
                            enemiesToRemove.addAll(enemies);
                            enemyBulletsToRemove.addAll(enemyBullets);
                        }
                        break;

                    case MISSILE_ID:
                        isMissile = true;

                        if (soundEnabled)
                            heartSound.play(0.15f);

                        if (!missileUsed) {
                            missileUsed = true;
                            bulletTimer = .2f;
                        }
                        break;

                    case RAPID_FIRE_ID:
                        isRapidFire = true;
                        if (soundEnabled)
                            heartSound.play(0.15f);

                        if (!rapidFireUsed) {
                            bulletTimer = .05f;
                            rapidFireUsed = true;
                        }
                        break;

                    case HOURGLASS_ID:
                        isHourglass = true;
                        if (soundEnabled)
                            heartSound.play(0.15f);

                        if (!hourglassUsed) {
                            hourglassUsed = true;
                        }
                        break;
                }
            }
            itemDrops.removeAll(itemsToRemove);
        }
    }

    public void runHeartUsedTimer( ){
        if(heartUsedTimer < 0){
            heartUsedTimer += deltaP;
        }
        else{
            heartUsed = false;
            heartUsedTimer = -.1f;
        }
    }

    public void runHourglassUsedTimer(){
        if(hourglassUsedTimer < 0){
            hourglassUsedTimer += deltaP;
        }
        else{
            hourglassUsed = false;
            hourglassUsedTimer = -.1f;
        }
    }
    public void runScoreTickerTimer(){
        if(scoreTickerTimer < 0){
            scoreTickerTimer += deltaP;
        }
        else{
            score += 1;
            scoreUpdated = false;
            scoreTickerTimer = SCORE_TICKER_TIMER;
        }
    }
    public void runEnemyHitSoundTimer( ){
        if(hitSoundTimer < 0){
            hitSoundTimer += deltaP;
        }
        else{
            hitSound.play(0.3f);
            playHitSound = false;
            hitSoundTimer = -.08f;
        }
    }

    public void runBombUsedTimer( ){
        if(bombUsedTimer < 0){
            bombUsedTimer += deltaP;
            whiteFlash.draw(game.batch);
        }
        else{
            bombUsed = false;
            bombUsedTimer = -.05f;
        }
    }

    public void runRapidFireUsedTimer(){
        if(rapidFireUsedTimer < 0){
            rapidFireUsedTimer += deltaP;
        }
        else{
            rapidFireUsed = false;
            rapidFireUsedTimer = -.1f;
        }
    }

    public void runMissileUsedTimer(){ //missile item drop
        if(missileUsedTimer < 0){
            missileUsedTimer += deltaP;
        }
        else{
            missileUsed = false;
            missileUsedTimer = -.1f;
        }
    }

    public void runMissileTimer(){ //missile weapon
        if(missileTimer < 0) {
            missileTimer += deltaP;
        }
        else{
            isMissile = false;
            missileTimer = MISSILE_TIMER;
        }
    }

    public void runRapidFireTimer(){
        if(rapidFireTimer < 0) {
            rapidFireTimer += deltaP;
        }
        else{
            isRapidFire = false;
            rapidFireTimer = RAPID_FIRE_TIMER;
        }
    }

    public void runHourglassTimer(){
        if(soundEnabled && mainMusic.isPlaying()){
           mainMusic.pause();
        }
        game.batch.setShader(invertedShader);

        if(hourglassTimer < 0) {
            hourglassTimer += deltaP;
            hourglassMultiplier = .25f;
        }
        else{
            if(soundEnabled){
                mainMusic.play();
            }

            isHourglass = false;
            game.batch.setShader(null);
            hourglassMultiplier = 1;
            hourglassTimer = HOURGLASS_TIMER;
        }
    }

    public void runShipHitTimer(){
        if(shipHitTimer < 0) {
            shipHitTimer += deltaP;
        }
        else{
            justHit = false;
            playerHitSoundPlayed = false;
            shipHitTimer = -2.2f;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if(!isTransitioningIn && !isTransitioningOut && !isFadingIn && !isFadingOut && !isRunningResumeCountdown)
            isPaused = true;

        songPausePosition = mainMusic.getPosition();
    }

    @Override
    public void resume() {
   }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        game.setScreen(new MainMenu(game, 0, assets));
    }
}
