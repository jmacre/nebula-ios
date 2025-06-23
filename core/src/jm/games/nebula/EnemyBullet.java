package jm.games.nebula;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static jm.games.nebula.GameElements.ENEMY_SHIP_WIDTH;
import static jm.games.nebula.GameElements.SCREEN_HEIGHT;


public class EnemyBullet {
    public Sprite enemyBulletSprite;
    Assets assets;
    float speed;
    int ENEMY_BULLET_X, ENEMY_BULLET_Y;
    Animation<TextureRegion> enemyBulletAnimation;


    public static float ENEMY_BULLET_WIDTH = ENEMY_SHIP_WIDTH * (2/31f);
    public static float ENEMY_BULLET_HEIGHT = ENEMY_BULLET_WIDTH * 4f;

    public static float blueShipBulletThreshold = 1.5f;
    public static float greenShipBulletThreshold = 1.4f;
    public static float redShipBulletThreshold = 1.35f;
    public static float purpleShipBulletThreshold = 1.4f;
    public static float whiteShipBulletThreshold = 1.5f;

    public static float blueShipBulletSpeed = SCREEN_HEIGHT/2.25f;
    public static float greenShipBulletSpeed = SCREEN_HEIGHT/2.05f;
    public static float redShipBulletSpeed = SCREEN_HEIGHT/1.85f;
    public static float purpleShipBulletSpeed = SCREEN_HEIGHT/1.7f;
    public static float whiteShipBulletSpeed = SCREEN_HEIGHT/1.55f;

    public boolean remove = false;
    int bulletColor;
    Collision rect;
    float stateTime = 0f;

    public void create (int ENEMY_BULLET_X, int ENEMY_BULLET_Y, float speed, int bulletColor, Assets assets) {
        this.assets = assets;
        this.ENEMY_BULLET_X = ENEMY_BULLET_X;
        this.ENEMY_BULLET_Y = ENEMY_BULLET_Y;
        this.speed = speed;
        this.stateTime = 0;

        this.bulletColor = bulletColor;

        this.rect = new Collision(ENEMY_BULLET_X, ENEMY_BULLET_Y, ENEMY_BULLET_WIDTH, ENEMY_BULLET_HEIGHT);
        enemyBulletSprite = new Sprite(assets.assetManager.get(Assets.enemy_bullet_ss, Texture.class));
        enemyBulletSprite.setSize(ENEMY_BULLET_WIDTH, ENEMY_BULLET_HEIGHT);
        enemyBulletAnimation = Anim.createAnimation(enemyBulletSprite, 2, Anim.DEFAULT_FRAME_DURATION*2f);

    }

    public void update(float delta){
        ENEMY_BULLET_Y -= (int)(speed * delta);
        if (ENEMY_BULLET_Y < 0){
            remove = true;
        }
        rect.move(ENEMY_BULLET_X, ENEMY_BULLET_Y);
        if(enemyBulletSprite != null) {
            enemyBulletSprite.setPosition(ENEMY_BULLET_X, ENEMY_BULLET_Y);
        }
    }

    public float getY(){
        return ENEMY_BULLET_Y;
    }

    public float getX(){
        return ENEMY_BULLET_X;
    }

    public void render (SpriteBatch batch, float delta, Anim enemyBulletAnim) {
        stateTime += delta / 4;

        if (ENEMY_BULLET_Y + ENEMY_BULLET_HEIGHT > 0) {
            enemyBulletAnim.drawAnim(enemyBulletAnimation, stateTime, ENEMY_BULLET_X, ENEMY_BULLET_Y, ENEMY_BULLET_WIDTH, ENEMY_BULLET_HEIGHT, true, batch);
        }
    }

    public Collision getCollision() {
        return rect;
    }
}
