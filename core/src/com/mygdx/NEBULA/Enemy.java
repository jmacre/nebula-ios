package com.mygdx.NEBULA;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import static com.mygdx.NEBULA.ItemDrop.HOURGLASS_SPEED_MULTIPLIER;

public class Enemy extends GameElements implements Pool.Poolable{
    public static final int EYEBAT_ID = 0;
    public static final int ENEMY_SHIP_ID = 1;
    public static final int LASER_TRAP_ID = 2;

    Animation<TextureRegion> enemyAnimation;
    ShaderProgram shader = new ShaderProgram(Gdx.files.internal("shaders/enemy_blink.vsh"), Gdx.files.internal("shaders/enemy_blink.fsh"));

    private Sprite enemySheet;
    public static final float SPEED = SCREEN_HEIGHT/1.56f;
    public float ENEMY_WIDTH;
    public float ENEMY_HEIGHT;

    Random random = new Random();
    SpriteBatch hurtBatch = new SpriteBatch();

    float ENEMY_X;
    float ENEMY_Y;
    float ENEMY_X_SPEED_MULTIPLIER;
    float ENEMY_Y_SPEED_MULTIPLIER;
    float ENEMY_FRAME_DURATION;
    float ENEMY_BULLET_DELAY;
    float ENEMY_BULLET_THRESHOLD;

    Integer position;
    float bulletSpeed;

    float stateTime = 0f;

    boolean shipMovingToPos = false;
    boolean movingRight = false;
    boolean movingLeft = false;
    boolean hurt;

    float hurtTimer;
    float hourglassMultiplier = 1;

    float HP;
    private int id;
    private int colorId;

    float randomNumber;

    Collision collision;
    public boolean remove = false;

    static Sprite blueBat, greenBat, redBat, purpleBat, whiteBat;
    static Sprite blueShip, greenShip, redShip, purpleShip, whiteShip;
    static Sprite laserTrap;


    public Enemy(){

    }

    public static void createEnemySprites(Assets assets){
        blueBat = new Sprite(assets.assetManager.get(Assets.eyebat_blue_ss, Texture.class));
        greenBat = new Sprite(assets.assetManager.get(Assets.eyebat_green_ss, Texture.class));
        redBat = new Sprite(assets.assetManager.get(Assets.eyebat_red_ss, Texture.class));
        purpleBat = new Sprite(assets.assetManager.get(Assets.eyebat_purple_ss, Texture.class));
        whiteBat = new Sprite(assets.assetManager.get(Assets.eyebat_white_ss, Texture.class));
        blueShip = new Sprite(assets.assetManager.get(Assets.enemy_ship_blue_ss, Texture.class));
        greenShip = new Sprite(assets.assetManager.get(Assets.enemy_ship_green_ss, Texture.class));
        redShip = new Sprite(assets.assetManager.get(Assets.enemy_ship_red_ss, Texture.class));
        purpleShip = new Sprite(assets.assetManager.get(Assets.enemy_ship_purple_ss, Texture.class));
        whiteShip = new Sprite(assets.assetManager.get(Assets.enemy_ship_white_ss, Texture.class));
        laserTrap = new Sprite(assets.assetManager.get(Assets.laser_trap_h_ss, Texture.class));
    }

    //EYEBAT CONSTRUCTOR
    public void create(int id, int colorId, float HP, float ENEMY_X, float ENEMY_WIDTH, float ENEMY_HEIGHT, float xSpeedMult, float ySpeedMult, float frameDuration, boolean hurt, float hurtTimer) {
        this.id = id;
        this.colorId = colorId;
        this.HP = HP;
        this.hurt = hurt;
        this.hurtTimer = hurtTimer;

        this.ENEMY_X = ENEMY_X;
        this.ENEMY_Y = 1.5f * MainGame.SCREEN_HEIGHT;
        this.ENEMY_HEIGHT= ENEMY_HEIGHT;
        this.ENEMY_WIDTH = ENEMY_WIDTH;
        this.ENEMY_X_SPEED_MULTIPLIER = xSpeedMult;
        this.ENEMY_Y_SPEED_MULTIPLIER= ySpeedMult;
        this.ENEMY_FRAME_DURATION = frameDuration;

        if(id == EYEBAT_ID) {
            this.collision = new Collision(this,ENEMY_X, ENEMY_Y, ENEMY_WIDTH, ENEMY_HEIGHT);

            switch (colorId) {

                case BLUE_ID:
                    enemySheet = blueBat;
                    break;
                case GREEN_ID:
                    enemySheet = greenBat;
                    break;
                case RED_ID:
                    enemySheet = redBat;
                    break;
                case PURPLE_ID:
                    enemySheet = purpleBat;
                    break;
                case WHITE_ID:
                    enemySheet = whiteBat;
                    break;

            }

        }
        enemyAnimation = Anim.createAnimation(enemySheet, 4, ENEMY_FRAME_DURATION);
        enemyAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }
    //SHIP CONSTRUCTOR
    public void create (int id, int colorId, float HP, float ENEMY_X, float ENEMY_WIDTH, float ENEMY_HEIGHT, float xSpeedMult, float ySpeedMult, float frameDuration, float bulletDelay, float bulletThreshold, boolean hurt, float hurtTimer, int position, float bulletSpeed) {
        this.id = id;
        this.colorId = colorId;
        this.HP = HP;
        this.hurt = hurt;
        this.hurtTimer = hurtTimer;

        this.ENEMY_X = ENEMY_X;
        this.ENEMY_Y = 1.2f * MainGame.SCREEN_HEIGHT;
        this.ENEMY_HEIGHT= ENEMY_HEIGHT;
        this.ENEMY_WIDTH = ENEMY_WIDTH;
        this.ENEMY_X_SPEED_MULTIPLIER = xSpeedMult;
        this.ENEMY_Y_SPEED_MULTIPLIER= ySpeedMult;
        this.ENEMY_FRAME_DURATION = frameDuration;
        this.ENEMY_BULLET_DELAY = bulletDelay;
        this.ENEMY_BULLET_THRESHOLD = bulletThreshold;
        this.position = position;
        this.bulletSpeed = bulletSpeed;

        this.collision = new Collision(ENEMY_X, ENEMY_Y, ENEMY_WIDTH, ENEMY_HEIGHT);

        if(id == ENEMY_SHIP_ID) {
            switch (colorId) {
                case BLUE_ID:
                    enemySheet = blueShip;
                    break;
                case GREEN_ID:
                    enemySheet = greenShip;
                    break;
                case RED_ID:
                    enemySheet = redShip;
                    break;
                case PURPLE_ID:
                    enemySheet = purpleShip;
                    break;
                case WHITE_ID:
                    enemySheet = whiteShip;
                    break;
            }
        }
        enemyAnimation = Anim.createAnimation(enemySheet, 4, ENEMY_FRAME_DURATION);
        enemyAnimation.setPlayMode(Animation.PlayMode.LOOP);

    }

    //LASER TRAP CONSTRUCTOR
    public void create (int id, float ENEMY_X, float ENEMY_WIDTH, float ENEMY_HEIGHT, float xSpeedMult, float ySpeedMult, float frameDuration) {
        this.id = id;

        this.ENEMY_X = ENEMY_X;
        this.ENEMY_Y = 1.2f * MainGame.SCREEN_HEIGHT;
        this.ENEMY_HEIGHT= ENEMY_HEIGHT;
        this.ENEMY_WIDTH = ENEMY_WIDTH;
        this.ENEMY_X_SPEED_MULTIPLIER = xSpeedMult;
        this.ENEMY_Y_SPEED_MULTIPLIER= ySpeedMult;
        this.ENEMY_FRAME_DURATION = frameDuration;

        this.collision = new Collision(ENEMY_X, ENEMY_Y, ENEMY_WIDTH, ENEMY_HEIGHT);

        if(id == LASER_TRAP_ID) {
            enemySheet = laserTrap;
        }

        enemyAnimation = Anim.createAnimation(enemySheet, 4, ENEMY_FRAME_DURATION);
        enemyAnimation.setPlayMode(Animation.PlayMode.LOOP);

    }

    public void update(float delta, Enemy enemy, boolean isHourglass){
        if(isHourglass){
            hourglassMultiplier = HOURGLASS_SPEED_MULTIPLIER;
        }
        else{
            hourglassMultiplier = 1;
        }

        if(id == EYEBAT_ID) {
            if(!isHurt())
                ENEMY_Y -= SPEED * delta * ENEMY_Y_SPEED_MULTIPLIER * hourglassMultiplier;
        }

        else if(id == ENEMY_SHIP_ID && !shipMovingToPos) {
            ENEMY_Y -= SPEED * .6f * delta * ENEMY_Y_SPEED_MULTIPLIER * hourglassMultiplier;
        }

        else if(id == LASER_TRAP_ID) {
            ENEMY_Y -= SPEED * delta * ENEMY_Y_SPEED_MULTIPLIER * hourglassMultiplier;
        }

        if (ENEMY_Y < - ENEMY_HEIGHT){
            remove = true;
        }

        if(id == LASER_TRAP_ID) {
            collision.move(ENEMY_X, ENEMY_Y);
        }
        else{
            collision.move(enemy, ENEMY_X, ENEMY_Y, delta);
        }
    }

    public void render (Anim enemyAnim, Enemy enemy, float delta, boolean isPaused, SpriteBatch batch) {
        switch (id) {
            case EYEBAT_ID:
                moveEyebat(enemyAnim, enemy, ENEMY_WIDTH, ENEMY_HEIGHT, delta, isPaused, batch);
                break;
            case ENEMY_SHIP_ID:
                moveEnemyShip(enemyAnim, enemy, ENEMY_WIDTH, ENEMY_HEIGHT, delta, isPaused, batch);
                break;
            case LASER_TRAP_ID:
                moveLaserTrap(enemyAnim, ENEMY_WIDTH, ENEMY_HEIGHT, delta, batch);
                break;
        }
    }

    public void moveEyebat(Anim enemyAnim, Enemy enemy, float width, float height, float delta, boolean isPaused, SpriteBatch batch){
        if(!isHurt()) {
            stateTime += delta / 6 * hourglassMultiplier;
        }

            if (enemy.getEnemyY() > SCREEN_HEIGHT) {
                movingRight = random.nextBoolean();
                movingLeft = !movingRight;
            }

            if (enemy.getEnemyX() <= 0) {
                movingRight = true;
                movingLeft = false;
            }

            if (enemy.getEnemyX() >= SCREEN_WIDTH - ENEMY_WIDTH) {
                movingLeft = true;
                movingRight = false;
            }

            if (!isPaused && !movingRight && (movingLeft || (randomNumber == 0))) {
                ENEMY_X -= (SCREEN_WIDTH / 2f) * delta * ENEMY_X_SPEED_MULTIPLIER * hourglassMultiplier;
            }

            if (!isPaused && !movingLeft && (movingRight || (randomNumber == 1))) {
                ENEMY_X += (SCREEN_WIDTH / 2f) * delta * ENEMY_X_SPEED_MULTIPLIER * hourglassMultiplier;
            }

        if(ENEMY_Y <= SCREEN_HEIGHT && enemyAnimation != null) {
            if (!isHurt()) {
                enemyAnim.drawAnim(enemyAnimation, stateTime, ENEMY_X, ENEMY_Y, width, height, true, batch);
            }
            else {
                enemyAnim.drawAnim(enemyAnimation, stateTime, ENEMY_X, ENEMY_Y, width, height, true, batch);
                batch.end();

                hurtBatch.setShader(shader);
                hurtBatch.begin();

                enemyAnim.drawAnim(enemyAnimation, stateTime, ENEMY_X, ENEMY_Y, width, height, true, hurtBatch);
                hurtBatch.end();
                batch.begin();
            }
        }
    }


    public void moveLaserTrap(Anim enemyAnim, float width, float height, float delta, SpriteBatch batch){
        stateTime += (delta / 4.5f) * hourglassMultiplier;

        if(ENEMY_Y <= SCREEN_HEIGHT && enemyAnimation != null)
            enemyAnim.drawAnim(enemyAnimation, stateTime, ENEMY_X, ENEMY_Y, width, height,   true, batch, hurt);
    }


    public void moveEnemyShip(Anim enemyAnim, Enemy enemy, float width, float height, float delta, boolean isPaused,  SpriteBatch batch){
        stateTime += (delta / 3) * hourglassMultiplier;

        if(enemy.getEnemyY() > SCREEN_HEIGHT) {
            movingRight = random.nextBoolean();
            movingLeft = !movingRight;
        }

        if(enemy.getEnemyX() <= 0) {
            movingRight = true;
            movingLeft = false;
        }

        else if(enemy.getEnemyX() >= SCREEN_WIDTH - ENEMY_WIDTH) {
            movingLeft = true;
            movingRight = false;
        }
        if(position != null) {

            if (ENEMY_Y <= SCREEN_HEIGHT - 6.5f * (ENEMY_HEIGHT) + (1.15f * ENEMY_HEIGHT * position - 1)) {
                shipMovingToPos = true;

                if (!isPaused && !movingRight && (movingLeft || (randomNumber == 0))) {
                    ENEMY_X -= (SCREEN_WIDTH / 4.5) * delta * ENEMY_X_SPEED_MULTIPLIER * hourglassMultiplier;
                } else if (!isPaused && !movingLeft && (movingRight || (randomNumber == 1))) {
                    ENEMY_X += (SCREEN_WIDTH / 4.5) * delta * ENEMY_X_SPEED_MULTIPLIER * hourglassMultiplier;
                }
            } else {
                shipMovingToPos = false;
            }
        }

        if(ENEMY_Y <= SCREEN_HEIGHT && enemyAnimation != null) {
            enemyAnim.drawAnim(enemyAnimation, stateTime/1.5f, ENEMY_X, ENEMY_Y, width, height, true, batch, hurt);
            if(hurt){
                batch.end();
                hurtBatch.setShader(shader);
                hurtBatch.begin();
                enemyAnim.drawAnim(enemyAnimation, stateTime/1.5f, ENEMY_X, ENEMY_Y, width, height, true, hurtBatch);
                hurtBatch.end();
                batch.begin();

            }
        }
    }

    public float getEnemyX(){
        return ENEMY_X;
    }

    public Float getEnemyY(){
        return ENEMY_Y;
    }

    public void setX(float ENEMY_X) {
        this.ENEMY_X = ENEMY_X;
    }

    public void setY(float ENEMY_Y) {
        this.ENEMY_Y = ENEMY_Y;
    }

    public float getWidth() {
        return ENEMY_WIDTH;
    }

    public void setWidth(float ENEMY_WIDTH) {
        this.ENEMY_WIDTH = ENEMY_WIDTH;
    }

    public float getHeight() {
        return ENEMY_HEIGHT;
    }

    public void setHeight(float ENEMY_HEIGHT) {
        this.ENEMY_HEIGHT = ENEMY_HEIGHT;
    }

    public int getId(){
        return id;
    }
    public float getHP(){
        return HP;
    }

    public void setHP(int HP){
        this.HP = HP;
    }

    public Collision getCollision() {
        return collision;
    }
    public int getColorId(){
        return colorId;
    }

    public float getEnemyBulletTimer(){
        return ENEMY_BULLET_DELAY;
    }
    public void setEnemyBulletTimer(float ENEMY_BULLET_TIMER){
        this.ENEMY_BULLET_DELAY = ENEMY_BULLET_TIMER;
    }

    public float getEnemyBulletThreshold() {
        return ENEMY_BULLET_THRESHOLD;
    }
    public boolean isHurt(){
        return hurt;
    }
    public void setEnemyHurt(boolean hurt){
        this.hurt = hurt;
    }
    public float getHurtTimer(){
        return hurtTimer;
    }
    public void setHurtTimer(float hurtTimer){
        this.hurtTimer = hurtTimer;
    }
    public int getPosition(){
        return position;
    }
    public float getBulletSpeed(){
        return bulletSpeed;
    }



    public Animation<TextureRegion> getEnemyAnimation() {
        return enemyAnimation;
    }


    @Override
    public void reset() {
        position = null;
    }
}
