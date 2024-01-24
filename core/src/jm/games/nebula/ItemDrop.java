package jm.games.nebula;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemDrop extends GameElements {
    Assets assets;
    Animation<TextureRegion> itemAnimation;
    public static final int DEFAULT_BULLET_ID = -1;
    public static final int BOMB_ID = 0;
    public static final int MISSILE_ID = 1;
    public static final int HEART_ID = 2;
    public static final int RAPID_FIRE_ID = 3;
    public static final int GEM_ID = 4;
    public static final int HOURGLASS_ID = 5;
    public static final int SPREAD_ID = 6;
    public static final int BEAM_ID = 7;

    public static final float MIN_ITEM_SPAWN_TIME = 15f;
    public static final float MAX_ITEM_SPAWN_TIME = 20f;

    public static final float MAX_GEM_SPAWN_TIME = 5f;
    public static final float MIN_GEM_SPAWN_TIME = 2f;

    public static float MISSILE_TIMER = -9f;
    public static float RAPID_FIRE_TIMER = -10f;
    public static float SPREAD_FIRE_TIMER = -10f;
    public static float BEAM_TIMER = -10f;
    public static float HOURGLASS_TIMER = -6f;
    public static float HOURGLASS_SPEED_MULTIPLIER = 0.25f;


    public Sprite itemSheet;
    Anim itemAnim = new Anim();


    public static final float SPEED = SCREEN_HEIGHT / 2f;

    int ITEM_X, ITEM_Y;

    public float ITEM_HEIGHT;
    public float ITEM_WIDTH;

    float stateTime = 0f;
    int itemId;

    Collision rect;

    public void create(int ITEM_X, int ITEM_HEIGHT, float ITEM_WIDTH, int itemId, Assets assets) {
        this.ITEM_X = ITEM_X;
        this.ITEM_Y = (int) (1.2f * SCREEN_HEIGHT);
        this.ITEM_HEIGHT = ITEM_HEIGHT;
        this.ITEM_WIDTH = ITEM_WIDTH;

        this.assets = assets;
        this.itemId = itemId;
        this.rect = new Collision(ITEM_X, ITEM_Y, ITEM_WIDTH, ITEM_HEIGHT);

        switch (itemId) {
            case HEART_ID:
                itemSheet = new Sprite(assets.assetManager.get(Assets.heart_item_ss, Texture.class));
                break;
            case BOMB_ID:
                itemSheet = new Sprite(assets.assetManager.get(Assets.bomb_ss, Texture.class));
                break;
            case MISSILE_ID:
                itemSheet = new Sprite(assets.assetManager.get(Assets.missile_item_ss, Texture.class));
                break;
            case RAPID_FIRE_ID:
                itemSheet = new Sprite(assets.assetManager.get(Assets.rapid_fire_ss, Texture.class));
                break;
            case GEM_ID:
                itemSheet = new Sprite(assets.assetManager.get(Assets.gem_ss, Texture.class));
                break;
            case HOURGLASS_ID:
                itemSheet = new Sprite(assets.assetManager.get(Assets.hourglass_ss, Texture.class));
                break;
            case SPREAD_ID:
                itemSheet = new Sprite(assets.assetManager.get(Assets.spread_ss, Texture.class));
                break;
            case BEAM_ID:
                itemSheet = new Sprite(assets.assetManager.get(Assets.beam_ss, Texture.class));
        }

        itemAnimation = Anim.createAnimation(itemSheet, 2, 0.02f);
    }


    public void update(float deltaTime) {
        if(getItemId() == GEM_ID){
            ITEM_Y -= (1.25f *SPEED) * deltaTime;
        }
        else{
            ITEM_Y -= SPEED * deltaTime;
        }

        rect.move(ITEM_X, ITEM_Y);
    }

    public void render(float width, float height, float delta, SpriteBatch batch) {
        stateTime += delta / 10;

        if(ITEM_Y < SCREEN_HEIGHT)
            itemAnim.drawAnim(itemAnimation, stateTime, ITEM_X, ITEM_Y, width, height, true, batch);
    }

    public float getHeight(){
        return ITEM_HEIGHT;
    }

    public float getY() {
        return ITEM_Y;
    }

    public float getX() {
        return ITEM_X;
    }

    public Collision getCollision() {
        return rect;
    }

    public int getItemId() {
        return itemId;
    }

}
