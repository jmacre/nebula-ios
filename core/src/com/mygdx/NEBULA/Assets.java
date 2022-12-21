package com.mygdx.NEBULA;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

import org.w3c.dom.Text;

import games.rednblack.miniaudio.MASound;
import games.rednblack.miniaudio.MiniAudio;
import games.rednblack.miniaudio.loader.MASoundLoader;

public class Assets implements Disposable {
    public AssetManager assetManager = new AssetManager();

    public static final String bullet_sound = "sound/bullet_sound.mp3";
    public static final String hit_sound = "sound/hit_sound.mp3";
    public static final String main_theme = "sound/organ.mp3";
    public static final String pause_sound = "sound/pause.mp3";
    public static final String play_sound = "sound/play.mp3";
    public static final String item_sound = "sound/item_sound.mp3";
    public static final String bomb_sound = "sound/bomb_sound.mp3";
    public static final String missile_sound = "sound/missile_sound.mp3";
    public static final String gem_sound = "sound/gem.mp3";

    public static final String default_background = "background/default_bg.png";
    public static final String blue_background = "background/blue_bg.png";
    public static final String green_background = "background/green_bg.png";
    public static final String purple_background = "background/purple_bg.png";
    public static final String red_background = "background/red_bg.png";
    public static final String black_background = "background/black_bg.png";

    public static final String stars_back = "background/stars_back.png";
    public static final String stars_front = "background/stars_front.png";

    public static final String start_button_inactive = "ui/start_button_inactive.png";
    public static final String start_button_inactive_clear = "ui/start_button_inactive_clear.png";
    public static final String start_button_active = "ui/start_button_active.png";
    public static final String start_button_active_clear = "ui/start_button_active_clear.png";

    public static final String shop_button_inactive = "ui/shop_button_inactive.png";
    public static final String shop_button_inactive_clear = "ui/shop_button_inactive_clear.png";
    public static final String shop_button_active = "ui/shop_button_active.png";
    public static final String title_logo = "ui/title_logo.png";
    public static final String title_logo_clear = "ui/title_logo_clear.png";

    public static final String heart = "ui/heart.png";
    public static final String heart_missing = "ui/heart_missing.png";
    public static final String pause_button = "ui/pause.png";

    public static final String play_button_inactive = "ui/play_button_inactive.png";
    public static final String play_button_active = "ui/play_button_active.png";

    public static final String gem_button_inactive = "ui/gem_button_inactive.png";

    public static final String replay_button_inactive = "ui/replay_button_inactive.png";
    public static final String home_button_inactive = "ui/home_button_inactive.png";
    public static final String sound_off_button_inactive = "ui/sound_off_button_inactive.png";
    public static final String sound_on_button_inactive = "ui/sound_on_button_inactive.png";
    public static final String replay_button_active = "ui/replay_button_active.png";
    public static final String home_button_active = "ui/home_button_active.png";
    public static final String sound_off_button_active = "ui/sound_off_button_active.png";
    public static final String sound_on_button_active = "ui/sound_on_button_active.png";
    public static final String sound_off_button_ts = "ui/sound_off_button_ts.png";
    public static final String sound_on_button_ts = "ui/sound_on_button_ts.png";

    public static final String right_arrow_active = "ui/right_arrow_active.png";
    public static final String left_arrow_active = "ui/left_arrow_active.png";
    public static final String right_arrow_inactive = "ui/right_arrow_inactive.png";
    public static final String left_arrow_inactive = "ui/left_arrow_inactive.png";
    public static final String select_button_inactive = "ui/select_button_inactive.png";
    public static final String select_button_active = "ui/select_button_active.png";
    public static final String ad_button_inactive = "ui/ad_button_inactive.png";
    public static final String ad_button_active = "ui/ad_button_active.png";
    public static final String active_button = "ui/active_button.png";
    public static final String buy_active = "ui/buy_active.png";
    public static final String buy_inactive = "ui/buy_inactive.png";

    public static final String bullet_yellow = "weapons/bullet_yellow.png";
    public static final String bullet_red = "weapons/bullet_red.png";
    public static final String bullet_blue = "weapons/bullet_blue.png";
    public static final String missile_ss = "weapons/missile_ss.png";

    public static final String black_transition = "misc/black_transition.png";
    public static final String white_flash = "misc/white_flash.png";
    public static final String laser_trap_h_ss = "enemy/trap_h_ss.png";

    public static final String eyebat_blue_ss = "enemy/eyebat_blue_ss.png";
    public static final String eyebat_red_ss = "enemy/eyebat_red_ss.png";
    public static final String eyebat_purple_ss = "enemy/eyebat_purple_ss.png";
    public static final String eyebat_green_ss = "enemy/eyebat_green_ss.png";
    public static final String eyebat_white_ss = "enemy/eyebat_white_ss.png";

    public static final String enemy_ship_blue_ss = "enemy/enemy_ship_blue_ss.png";
    public static final String enemy_ship_red_ss = "enemy/enemy_ship_red_ss.png";
    public static final String enemy_ship_green_ss = "enemy/enemy_ship_green_ss.png";
    public static final String enemy_ship_purple_ss = "enemy/enemy_ship_purple_ss.png";
    public static final String enemy_ship_white_ss = "enemy/enemy_ship_white_ss.png";

    public static final String ship_ss = "player/ship_ss.png";
    public static final String ship_red_ss = "player/ship_red_ss.png";
    public static final String ship_black_ss = "player/ship_black_ss.png";
    public static final String ship_purple_ss = "player/ship_purple_ss.png";
    public static final String ship_yellow_ss = "player/ship_yellow_ss.png";
    public static final String ship_cyan_ss = "player/ship_cyan_ss.png";
    public static final String ship_bred_ss = "player/ship_bred_ss.png";
    public static final String ship_orange_ss = "player/ship_orange_ss.png";
    public static final String ship_green_ss = "player/ship_green_ss.png";
    public static final String ship_blue_ss = "player/ship_blue_ss.png";

    public static final String explosion = "misc/explosion_ss.png";
    public static final String pause_menu_back = "ui/menu_back.png";
    public static final String powerup_timer = "ui/powerup_timer_ss.png";

    public static final String shop_back = "ui/shop_back.png";
    public static final String x_button = "ui/x_button.png";
    public static final String yes_button_active = "ui/yes_button_active.png";
    public static final String yes_button_inactive = "ui/yes_button_inactive.png";
    public static final String no_button_active = "ui/no_button_active.png";
    public static final String no_button_inactive = "ui/no_button_inactive.png";

    public static final String heart_item_ss = "items/heart_ss.png";
    public static final String missile_item_ss = "items/missile_ss.png";
    public static final String bomb_ss = "items/bomb_ss.png";
    public static final String rapid_fire_ss = "items/rapid_fire_ss.png";
    public static final String hourglass_ss = "items/hourglass_ss.png";
    public static final String gem_ss = "items/gem_ss.png";
    public static final String error_sound = "sound/error_sound.mp3";

    public static final String gem_icon = "ui/gem.png";


    public void load(MiniAudio miniAudio) {
        assetManager.setLoader(MASound.class, new MASoundLoader(miniAudio, assetManager.getFileHandleResolver()));

        assetManager.load(main_theme, MASound.class);
        assetManager.load(bomb_sound, MASound.class);
        assetManager.load(hit_sound, MASound.class);
        assetManager.load(missile_sound, MASound.class);
        assetManager.load(pause_sound, MASound.class);
        assetManager.load(play_sound, MASound.class);
        assetManager.load(bullet_sound, MASound.class);
        assetManager.load(item_sound, MASound.class);
        assetManager.load(error_sound, MASound.class);
        assetManager.load(gem_sound, MASound.class);

        assetManager.load(powerup_timer, Texture.class);
        assetManager.load(gem_icon, Texture.class);

        assetManager.load(default_background, Texture.class);
        assetManager.load(green_background, Texture.class);
        assetManager.load(blue_background, Texture.class);
        assetManager.load(purple_background, Texture.class);
        assetManager.load(red_background, Texture.class);
        assetManager.load(black_background, Texture.class);
        assetManager.load(stars_front, Texture.class);
        assetManager.load(stars_back, Texture.class);

        assetManager.load(start_button_inactive, Texture.class);
        assetManager.load(start_button_inactive_clear, Texture.class);
        assetManager.load(start_button_active, Texture.class);
        assetManager.load(start_button_active_clear, Texture.class);
        assetManager.load(shop_button_inactive, Texture.class);
        assetManager.load(shop_button_inactive_clear, Texture.class);
        assetManager.load(gem_button_inactive, Texture.class);
        assetManager.load(ad_button_active, Texture.class);
        assetManager.load(ad_button_inactive, Texture.class);

        assetManager.load(shop_button_active, Texture.class);

        assetManager.load(title_logo, Texture.class);
        assetManager.load(title_logo_clear, Texture.class);
        assetManager.load(heart, Texture.class);
        assetManager.load(heart_missing, Texture.class);
        assetManager.load(pause_button, Texture.class);

        assetManager.load(play_button_active, Texture.class);
        assetManager.load(play_button_inactive, Texture.class);

        assetManager.load(replay_button_inactive, Texture.class);
        assetManager.load(home_button_inactive, Texture.class);
        assetManager.load(replay_button_active, Texture.class);
        assetManager.load(home_button_active, Texture.class);

        assetManager.load(bullet_yellow, Texture.class);
        assetManager.load(bullet_red, Texture.class);
        assetManager.load(bullet_blue, Texture.class);
        assetManager.load(missile_ss, Texture.class);

        assetManager.load(sound_off_button_active, Texture.class);
        assetManager.load(sound_on_button_active, Texture.class);
        assetManager.load(sound_off_button_inactive, Texture.class);
        assetManager.load(sound_on_button_inactive, Texture.class);

        assetManager.load(sound_off_button_ts, Texture.class);
        assetManager.load(sound_on_button_ts, Texture.class);

        assetManager.load(black_transition, Texture.class);
        assetManager.load(white_flash, Texture.class);

        assetManager.load(eyebat_blue_ss, Texture.class);
        assetManager.load(eyebat_green_ss, Texture.class);
        assetManager.load(eyebat_red_ss, Texture.class);
        assetManager.load(eyebat_purple_ss, Texture.class);
        assetManager.load(eyebat_white_ss, Texture.class);

        assetManager.load(enemy_ship_blue_ss, Texture.class);
        assetManager.load(enemy_ship_green_ss, Texture.class);
        assetManager.load(enemy_ship_red_ss, Texture.class);
        assetManager.load(enemy_ship_purple_ss, Texture.class);
        assetManager.load(enemy_ship_white_ss, Texture.class);

        assetManager.load(laser_trap_h_ss, Texture.class);

        assetManager.load(ship_ss, Texture.class);
        assetManager.load(ship_black_ss, Texture.class);
        assetManager.load(ship_red_ss, Texture.class);
        assetManager.load(ship_purple_ss, Texture.class);
        assetManager.load(ship_yellow_ss, Texture.class);
        assetManager.load(ship_cyan_ss, Texture.class);
        assetManager.load(ship_bred_ss, Texture.class);
        assetManager.load(ship_orange_ss, Texture.class);
        assetManager.load(ship_green_ss, Texture.class);
        assetManager.load(ship_blue_ss, Texture.class);

        assetManager.load(explosion, Texture.class);

        assetManager.load(heart_item_ss, Texture.class);
        assetManager.load(bomb_ss, Texture.class);
        assetManager.load(missile_item_ss, Texture.class);
        assetManager.load(rapid_fire_ss, Texture.class);
        assetManager.load(hourglass_ss, Texture.class);
        assetManager.load(gem_ss, Texture.class);

        assetManager.load(pause_menu_back, Texture.class);
        assetManager.load(shop_back, Texture.class);
        assetManager.load(x_button, Texture.class);
        assetManager.load(yes_button_active, Texture.class);
        assetManager.load(no_button_active, Texture.class);
        assetManager.load(yes_button_inactive, Texture.class);
        assetManager.load(no_button_inactive, Texture.class);

        assetManager.load(right_arrow_active, Texture.class);
        assetManager.load(right_arrow_inactive, Texture.class);
        assetManager.load(left_arrow_active, Texture.class);
        assetManager.load(left_arrow_inactive, Texture.class);
        assetManager.load(select_button_active, Texture.class);
        assetManager.load(select_button_inactive, Texture.class);
        assetManager.load(active_button, Texture.class);

        assetManager.load(buy_active, Texture.class);
        assetManager.load(buy_inactive, Texture.class);
    }

    public void unloadAll() {
        assetManager.unload(hit_sound);
        assetManager.unload(missile_sound);
        assetManager.unload(pause_sound);
        assetManager.unload(play_sound);
        assetManager.unload(bullet_sound);
        assetManager.unload(item_sound);
        assetManager.unload(bomb_sound);
        assetManager.unload(gem_sound);
        assetManager.unload(error_sound);
        assetManager.unload(main_theme);

        assetManager.unload(default_background);
        assetManager.unload(green_background);
        assetManager.unload(blue_background);
        assetManager.unload(purple_background);
        assetManager.unload(red_background);
        assetManager.unload(black_background);

        assetManager.unload(stars_front);
        assetManager.unload(stars_back);

        assetManager.unload(powerup_timer);
        assetManager.unload(gem_icon);

        assetManager.unload(start_button_inactive);
        assetManager.unload(start_button_active);

        assetManager.unload(title_logo);
        assetManager.unload(heart);
        assetManager.unload(heart_missing);
        assetManager.unload(pause_button);
        assetManager.unload(play_button_active);
        assetManager.unload(play_button_inactive);
        assetManager.unload(replay_button_inactive);
        assetManager.unload(home_button_inactive);

        assetManager.unload(right_arrow_active);
        assetManager.unload(right_arrow_inactive);
        assetManager.unload(left_arrow_active);
        assetManager.unload(left_arrow_inactive);
        assetManager.unload(select_button_active);
        assetManager.unload(select_button_inactive);
        assetManager.unload(active_button);

        assetManager.unload(gem_button_inactive);

        assetManager.unload(bullet_yellow);
        assetManager.unload(bullet_red);
        assetManager.unload(bullet_blue);
        assetManager.unload(missile_ss);

        assetManager.unload(sound_off_button_inactive);
        assetManager.unload(sound_on_button_inactive);
        assetManager.unload(sound_off_button_ts);
        assetManager.unload(sound_on_button_ts);

        assetManager.unload(black_transition);
        assetManager.unload(white_flash);
        assetManager.unload(eyebat_blue_ss);
        assetManager.unload(eyebat_red_ss);
        assetManager.unload(eyebat_green_ss);
        assetManager.unload(eyebat_purple_ss);
        assetManager.unload(eyebat_white_ss);

        assetManager.unload(enemy_ship_blue_ss);
        assetManager.unload(enemy_ship_red_ss);
        assetManager.unload(enemy_ship_purple_ss);
        assetManager.unload(enemy_ship_white_ss);
        assetManager.unload(enemy_ship_green_ss);
        assetManager.unload(laser_trap_h_ss);

        assetManager.unload(ship_ss);
        assetManager.unload(ship_red_ss);
        assetManager.unload(ship_black_ss);
        assetManager.unload(ship_purple_ss);
        assetManager.unload(ship_yellow_ss);
        assetManager.unload(ship_cyan_ss);
        assetManager.unload(ship_orange_ss);
        assetManager.unload(ship_green_ss);
        assetManager.unload(ship_bred_ss);
        assetManager.unload(ship_blue_ss);

        assetManager.unload(explosion);
        assetManager.unload(heart_item_ss);
        assetManager.unload(bomb_ss);
        assetManager.unload(missile_item_ss);
        assetManager.unload(rapid_fire_ss);
        assetManager.unload(gem_ss);

        assetManager.unload(pause_menu_back);

        assetManager.unload(powerup_timer);
        assetManager.unload(buy_inactive);
        assetManager.unload(buy_active);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
