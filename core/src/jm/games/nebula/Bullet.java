package jm.games.nebula;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static jm.games.nebula.BulletElement.AQUAMARINE_BULLET_SLOT;
import static jm.games.nebula.BulletElement.BLUE_BULLET_SLOT;
import static jm.games.nebula.BulletElement.COTTON_CANDY_BULLET_SLOT;
import static jm.games.nebula.BulletElement.CHERRY_BULLET_SLOT;
import static jm.games.nebula.BulletElement.GREEN_BULLET_SLOT;
import static jm.games.nebula.BulletElement.ORANGE_BULLET_SLOT;
import static jm.games.nebula.BulletElement.PINK_BULLET_SLOT;
import static jm.games.nebula.BulletElement.PURPLE_BULLET_SLOT;
import static jm.games.nebula.BulletElement.RAINBOW_BULLET_SLOT;
import static jm.games.nebula.BulletElement.RED_BULLET_SLOT;
import static jm.games.nebula.BulletElement.ROCKETPOP_BULLET_SLOT;
import static jm.games.nebula.BulletElement.SMOKE_BULLET_SLOT;
import static jm.games.nebula.BulletElement.YELLOW_BULLET_SLOT;
import static jm.games.nebula.ItemDrop.BEAM_ID;
import static jm.games.nebula.ItemDrop.HOURGLASS_SPEED_MULTIPLIER;
import static jm.games.nebula.ItemDrop.MISSILE_ID;
import static jm.games.nebula.ItemDrop.RAPID_FIRE_ID;
import static jm.games.nebula.ItemDrop.SPREAD_ID;

import java.util.Objects;

public class Bullet extends GameElements {

    public Sprite bulletSprite = new Sprite();
    public Sprite selectedBulletSprite;
    int selectedBullet = 0;
    Animation<TextureRegion> bulletAnimation;

    public static final float SPEED_VERTICAL = SCREEN_HEIGHT/1.4f;
    public static final float SPEED_HORIZONTAL = (SPEED_VERTICAL * ((float)SCREEN_WIDTH / SCREEN_HEIGHT))/3;

    float BULLET_X, BULLET_Y;
    float stateTime = 0f;

    private boolean isMissile = false;
    private boolean isRapidFire = false;
    private boolean isSpreadFire = false;
    private boolean isLeftSpread = false;
    private boolean isRightSpread = false;
    private boolean isLeftBeam = false;
    private boolean isRightBeam = false;
    private boolean isBeam = false;

    Collision rect;

    public Bullet(){
    }
    public void create(float BULLET_X, int powerUp, Assets assets, boolean isHourglass, Prefs prefs){
        create(BULLET_X, powerUp, "", assets, isHourglass, prefs);
    }
    public void create(float BULLET_X, int powerUp, String bulletType, Assets assets, boolean isHourglass, Prefs prefs){
        this.BULLET_X = BULLET_X;
        this.BULLET_Y = (int) (SHIP_Y + SHIP_HEIGHT - BULLET_HEIGHT/2f);

        this.isMissile = false;
        this.isRapidFire = false;
        this.isSpreadFire = false;
        this.isBeam = false;
        this.stateTime = 0;

        if(powerUp == MISSILE_ID){
            this.isMissile = true;
        }
        else if(powerUp == RAPID_FIRE_ID){
            this.isRapidFire = true;
        }
        else if(powerUp == SPREAD_ID){
            this.isSpreadFire = true;
        }

        else if(powerUp == BEAM_ID){
            this.isBeam = true;
        }

        this.isLeftSpread = Objects.equals(bulletType, "isLeftSpread");
        this.isRightSpread = Objects.equals(bulletType, "isRightSpread");

        this.isLeftBeam = Objects.equals(bulletType, "isLeftBeam");
        this.isRightBeam = Objects.equals(bulletType, "isRightBeam");

        this.rect = new Collision(BULLET_X, BULLET_Y, BULLET_WIDTH, BULLET_HEIGHT);

        if(selectedBulletSprite == null){
            getSelectedBullet(assets, prefs);
        }

        bulletSprite.setSize(BULLET_WIDTH, BULLET_HEIGHT);
        bulletSprite.setTexture(selectedBulletSprite.getTexture());
        bulletAnimation = Anim.createAnimation(bulletSprite, 1, 1f);


        if(isMissile) {
            this.rect = new Collision(BULLET_X - MISSILE_WIDTH, BULLET_Y, MISSILE_WIDTH, MISSILE_HEIGHT);
            bulletSprite.setTexture(assets.assetManager.get(Assets.missile_ss,Texture.class));
            bulletSprite.setSize(MISSILE_WIDTH, MISSILE_HEIGHT);
            bulletAnimation = Anim.createAnimation(bulletSprite, 4, Anim.DEFAULT_FRAME_DURATION*1.5f);
        }

        else if(isRapidFire){
            bulletSprite.setTexture(assets.assetManager.get(Assets.bullet_blue,Texture.class));
            bulletAnimation = Anim.createAnimation(bulletSprite, 1, 1f);
        }

        else if(isHourglass){
            this.rect = new Collision(BULLET_X, BULLET_Y, BULLET_WIDTH, BULLET_HEIGHT);
            bulletSprite.setTexture(assets.assetManager.get(Assets.bullet_purple,Texture.class));
            bulletAnimation = Anim.createAnimation(bulletSprite, 1, 1f);
        }

        else if (isSpreadFire){
            bulletSprite.setTexture(assets.assetManager.get(Assets.bullet_green,Texture.class));
            bulletAnimation = Anim.createAnimation(bulletSprite, 1, 1f);
        }

        else if(isBeam){
            this.rect = new Collision(BULLET_X, BULLET_Y, BULLET_WIDTH, SCREEN_HEIGHT);
            bulletSprite.setTexture(assets.assetManager.get(Assets.enemy_bullet_ss, Texture.class));
            bulletSprite.setSize(BULLET_WIDTH, SCREEN_HEIGHT);
            bulletAnimation = Anim.createAnimation(bulletSprite, 2, Anim.DEFAULT_FRAME_DURATION*2f);
        }
    }


    public void update(float delta, boolean isHourglass, boolean isSpreadFire, boolean isLeftSpread, boolean isRightSpread, float leftBeamPos, float rightBeamPos){
        if(isSpreadFire) {
            if (isLeftSpread) {
                BULLET_X -= (int)(SPEED_HORIZONTAL * delta);
            }
            if (isRightSpread) {
                BULLET_X += (int)(SPEED_HORIZONTAL * delta);
            }
        }

        if(isMissile){
            BULLET_Y += (SPEED_VERTICAL * delta) * .75f;
        }
        else if(isHourglass) {
            BULLET_Y += (SPEED_VERTICAL * delta) * .75f * HOURGLASS_SPEED_MULTIPLIER;
        }

        if(!isHourglass && !isMissile && !isBeam) {
            BULLET_Y += SPEED_VERTICAL * delta;
        }

        if(isBeam){
            BULLET_Y = (int) MainGame.SHIP_Y + MainGame.SHIP_HEIGHT;
            if(isLeftBeam){
                BULLET_X = leftBeamPos;
            }
            if(isRightBeam){
                BULLET_X = rightBeamPos;
            }
        }

        rect.move(this.BULLET_X, this.BULLET_Y);

        if(bulletSprite != null) {
            bulletSprite.setPosition(BULLET_X, BULLET_Y);
        }
    }

    public float getBulletY(){
        return BULLET_Y;
    }

    public float getBulletX(){
        return BULLET_X;
    }

    public static float getBulletWidth() {
        return BULLET_WIDTH;
    }

    public static float getBulletHeight() {
        return BULLET_HEIGHT;
    }

    public void render (SpriteBatch batch) {
        if(BULLET_Y < SCREEN_HEIGHT)
            bulletAnim.drawAnim(bulletAnimation, 0, BULLET_X, BULLET_Y, BULLET_WIDTH, BULLET_HEIGHT, true, batch);
    }

    public void render (float delta, float width, float height, SpriteBatch batch) {
        stateTime += delta / 6;
        if(BULLET_Y < SCREEN_HEIGHT && isMissile) {
            bulletAnim.drawAnim(bulletAnimation, stateTime, BULLET_X, BULLET_Y, width, height, true, batch);
        }
        if(isBeam){
            bulletAnim.drawAnim(bulletAnimation, stateTime, BULLET_X, BULLET_Y, BULLET_WIDTH, SCREEN_HEIGHT, true, batch);
        }
        else{
            bulletAnim.drawAnim(bulletAnimation, stateTime, BULLET_X, BULLET_Y, width, height, true, batch);
        }
    }
    public void getSelectedBullet(Assets assets, Prefs prefs) {
        selectedBullet = prefs.getBullet();

        switch (selectedBullet) {
            case YELLOW_BULLET_SLOT:
                bulletSS = new Sprite(assets.assetManager.get(Assets.bullet_yellow, Texture.class));
                break;
            case RED_BULLET_SLOT:
                bulletSS = new Sprite(assets.assetManager.get(Assets.bullet_red, Texture.class));
                break;
            case GREEN_BULLET_SLOT:
                bulletSS = new Sprite(assets.assetManager.get(Assets.bullet_green, Texture.class));
                break;
            case BLUE_BULLET_SLOT:
                bulletSS = new Sprite(assets.assetManager.get(Assets.bullet_blue, Texture.class));
                break;
            case PURPLE_BULLET_SLOT:
                bulletSS = new Sprite(assets.assetManager.get(Assets.bullet_purple, Texture.class));
                break;
            case ORANGE_BULLET_SLOT:
                bulletSS = new Sprite(assets.assetManager.get(Assets.bullet_orange, Texture.class));
                break;
            case PINK_BULLET_SLOT:
                bulletSS = new Sprite(assets.assetManager.get(Assets.bullet_pink, Texture.class));
                break;
            case COTTON_CANDY_BULLET_SLOT:
                bulletSS = new Sprite(assets.assetManager.get(Assets.bullet_cotton_candy, Texture.class));
                break;
            case ROCKETPOP_BULLET_SLOT:
                bulletSS = new Sprite(assets.assetManager.get(Assets.bullet_rocketPop, Texture.class));
                break;
            case SMOKE_BULLET_SLOT:
                bulletSS = new Sprite(assets.assetManager.get(Assets.bullet_smoke, Texture.class));
                break;
            case CHERRY_BULLET_SLOT:
                bulletSS = new Sprite(assets.assetManager.get(Assets.bullet_cherry, Texture.class));
                break;
            case AQUAMARINE_BULLET_SLOT:
                bulletSS = new Sprite(assets.assetManager.get(Assets.bullet_aquamarine, Texture.class));
                break;
            case RAINBOW_BULLET_SLOT:
                bulletSS = new Sprite(assets.assetManager.get(Assets.bullet_rainbow, Texture.class));
                break;
        }
        selectedBulletSprite = bulletSS;
        bulletAnimation = Anim.createAnimation(bulletSS, 1, 1f);
    }

    public Collision getCollision() {
        return rect;
    }

    public boolean isMissile(){
        return isMissile;
    }
    public boolean isBeam(){
        return isBeam;
    }
    public boolean isRapidFire() {
        return isRapidFire;
    }
    public boolean isSpreadFire(){
        return isSpreadFire;
    }

    public boolean isLeftSpread(){
        return isLeftSpread;
    }
    public boolean isRightSpread(){
        return isRightSpread;
    }
}

