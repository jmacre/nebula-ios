package com.mygdx.NEBULA;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
    public AssetManager assetManager = new AssetManager();
    public static final String bullet_sound = "sound/bullet_sound.mp3";
    public static final String hit_sound = "sound/hit_sound.mp3";
    public static final String main_theme = "sound/organ.mp3";
    public static final String pause_sound = "sound/pause.mp3";
    public static final String play_sound = "sound/play.mp3";
    public static final String heart_sound = "sound/heart_sound.mp3";
    public static final String bomb_sound = "sound/bomb_sound.mp3";
    public static final String missile_sound = "sound/missile_sound.mp3";

    public static final String default_background = "ui/space_background.png";
    public static final String blue_background = "ui/blue_background.png";
    public static final String green_background = "ui/green_background.png";
    public static final String purple_background = "ui/purple_background.png";
    public static final String red_background = "ui/red_background.png";
    public static final String black_background = "ui/black_background.png";

    public static final String stars_back = "ui/stars_back.png";
    public static final String stars_front = "ui/stars_front.png";

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

    public static final String bullet_yellow = "weapons/bullet_yellow.png";
    public static final String bullet_red = "weapons/bullet_red.png";
    public static final String bullet_blue = "weapons/bullet_blue.png";
    public static final String missile_ss = "weapons/missile_ss.png";

    public static final String black_transition = "misc/black_transition.png";
    public static final String white_flash = "misc/white_flash.png";
    public static final String laser_trap_h_ss = "misc/trap_h_ss.png";

    public static final String eyebat_blue_ss = "char/eyebat_blue_ss.png";
    public static final String eyebat_red_ss = "char/eyebat_red_ss.png";
    public static final String eyebat_purple_ss = "char/eyebat_purple_ss.png";
    public static final String eyebat_green_ss = "char/eyebat_green_ss.png";
    public static final String eyebat_white_ss = "char/eyebat_white_ss.png";

    public static final String enemy_ship_blue_ss = "char/enemy_ship_blue_ss.png";
    public static final String enemy_ship_red_ss = "char/enemy_ship_red_ss.png";
    public static final String enemy_ship_green_ss = "char/enemy_ship_green_ss.png";
    public static final String enemy_ship_purple_ss = "char/enemy_ship_purple_ss.png";
    public static final String enemy_ship_white_ss = "char/enemy_ship_white_ss.png";

    public static final String ship_ss = "char/main_ship_black_ss.png";
    public static final String explosion = "misc/explosion_ss.png";
    public static final String pause_menu_back = "ui/menu_back.png";

    public static final String shop_back = "ui/shop_back.png";
    public static final String x_button = "ui/x_button.png";
    public static final String yes_button_active = "ui/yes_button_active.png";
    public static final String yes_button_inactive = "ui/yes_button_inactive.png";
    public static final String no_button_active = "ui/no_button_active.png";
    public static final String no_button_inactive = "ui/no_button_inactive.png";

//    public static final String upgrade_button = "ui/upgrade_button.png";

    public static final String heart_item_ss = "items/heart_ss.png";
    public static final String missile_item_ss = "items/missile_ss.png";
    public static final String bomb_ss = "items/bomb_ss.png";
    public static final String rapid_fire_ss = "items/rapid_fire_ss.png";
    public static final String hourglass_ss = "items/hourglass_ss.png";


    public void load() {
        assetManager.load(main_theme, Music.class);
        assetManager.load(bomb_sound, Music.class);

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
        assetManager.load(explosion, Texture.class);

        assetManager.load(heart_item_ss, Texture.class);
        assetManager.load(bomb_ss, Texture.class);
        assetManager.load(missile_item_ss, Texture.class);
        assetManager.load(rapid_fire_ss, Texture.class);
        assetManager.load(hourglass_ss, Texture.class);

        assetManager.load(pause_menu_back, Texture.class);
        assetManager.load(shop_back, Texture.class);
        assetManager.load(x_button, Texture.class);
        assetManager.load(yes_button_active, Texture.class);
        assetManager.load(no_button_active, Texture.class);
        assetManager.load(yes_button_inactive, Texture.class);
        assetManager.load(no_button_inactive, Texture.class);
//        assetManager.load(upgrade_button, Texture.class);

        assetManager.load(hit_sound, Sound.class);
        assetManager.load(missile_sound, Sound.class);
        assetManager.load(pause_sound, Sound.class);
        assetManager.load(play_sound, Sound.class);
        assetManager.load(bullet_sound, Sound.class);
        assetManager.load(heart_sound, Sound.class);
    }

    public void unloadAll() {
        assetManager.unload(hit_sound);
        assetManager.unload(missile_sound);
        assetManager.unload(pause_sound);
        assetManager.unload(play_sound);
        assetManager.unload(bullet_sound);
        assetManager.unload(heart_sound);
        assetManager.unload(main_theme);
        assetManager.unload(bomb_sound);

        assetManager.unload(default_background);
        assetManager.unload(green_background);
        assetManager.unload(blue_background);
        assetManager.unload(purple_background);
        assetManager.unload(red_background);
        assetManager.unload(black_background);

        assetManager.unload(stars_front);
        assetManager.unload(stars_back);

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
        assetManager.unload(explosion);
        assetManager.unload(heart_item_ss);
        assetManager.unload(bomb_ss);
        assetManager.unload(missile_item_ss);
        assetManager.unload(rapid_fire_ss);
        assetManager.unload(pause_menu_back);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
