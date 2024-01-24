package jm.games.nebula;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static jm.games.nebula.ItemDrop.BEAM_ID;
import static jm.games.nebula.ItemDrop.HOURGLASS_ID;
import static jm.games.nebula.ItemDrop.HOURGLASS_SPEED_MULTIPLIER;
import static jm.games.nebula.ItemDrop.MISSILE_ID;
import static jm.games.nebula.ItemDrop.RAPID_FIRE_ID;
import static jm.games.nebula.ItemDrop.SPREAD_ID;

import java.util.Objects;

public class Bullet extends GameElements {
    public Sprite missileSprite = new Sprite();
    public Sprite bulletSprite = new Sprite();

    Animation<TextureRegion> missileAnimation;
    Animation<TextureRegion> beamAnimation;

    public static final float SPEED_VERTICAL = SCREEN_HEIGHT/1.4f;
    public static final float SPEED_HORIZONTAL = (SPEED_VERTICAL * ((float)SCREEN_WIDTH / SCREEN_HEIGHT))/3;

    int BULLET_X, BULLET_Y;
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
    public void create(int BULLET_X, int powerUp, Assets assets, boolean isHourglass){
        create(BULLET_X, powerUp, "", assets, isHourglass);
    }
    public void create(int BULLET_X, int powerUp, String bulletType, Assets assets, boolean isHourglass){
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

        if(!isMissile) {
            this.rect = new Collision(BULLET_X, BULLET_Y, BULLET_WIDTH, BULLET_HEIGHT);
            bulletSprite.setSize(BULLET_WIDTH, BULLET_HEIGHT);
        }

        if(isMissile) {
            this.rect = new Collision(BULLET_X - MISSILE_WIDTH, BULLET_Y, MISSILE_WIDTH, MISSILE_HEIGHT);
            missileSprite.setTexture(assets.assetManager.get(Assets.missile_ss,Texture.class));
            missileSprite.setSize(MISSILE_WIDTH, MISSILE_HEIGHT);
            missileAnimation = Anim.createAnimation(missileSprite, 4, Anim.DEFAULT_FRAME_DURATION*1.5f);
        }

        else if(isRapidFire){
            bulletSprite.setTexture(assets.assetManager.get(Assets.bullet_blue,Texture.class));
        }

        else if(isHourglass){
            this.rect = new Collision(BULLET_X, BULLET_Y, BULLET_WIDTH, BULLET_HEIGHT);
            bulletSprite.setTexture(assets.assetManager.get(Assets.bullet_purple,Texture.class));
        }

        else if (isSpreadFire){
            bulletSprite.setTexture(assets.assetManager.get(Assets.bullet_green,Texture.class));
        }

        else if(isBeam){
            this.rect = new Collision(BULLET_X, BULLET_Y, BULLET_WIDTH, SCREEN_HEIGHT);
            bulletSprite.setTexture(assets.assetManager.get(Assets.enemy_bullet_ss, Texture.class));
            bulletSprite.setSize(BULLET_WIDTH, SCREEN_HEIGHT);
            beamAnimation = Anim.createAnimation(bulletSprite, 2, Anim.DEFAULT_FRAME_DURATION*2f);
        }

        else{
            bulletSprite.setTexture(assets.assetManager.get(Assets.bullet_yellow,Texture.class));
        }
    }


    public void update(float delta, boolean isHourglass, boolean isSpreadFire, boolean isLeftSpread, boolean isRightSpread, int leftBeamPos, int rightBeamPos){
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

    public int getBulletY(){
        return BULLET_Y;
    }

    public int getBulletX(){
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
            bulletSprite.draw(batch);
    }

    public void render (Anim bulletAnim, float delta, float width, float height, SpriteBatch batch) {
        stateTime += delta / 6;
        if(BULLET_Y < SCREEN_HEIGHT && isMissile) {
            bulletAnim.drawAnim(missileAnimation, stateTime, BULLET_X, BULLET_Y, width, height, true, batch);
        }
        if(isBeam){
            bulletAnim.drawAnim(beamAnimation, stateTime, BULLET_X, BULLET_Y, BULLET_WIDTH, SCREEN_HEIGHT, true, batch);
        }
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

