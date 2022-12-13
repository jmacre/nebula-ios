package com.mygdx.NEBULA;

import static com.mygdx.NEBULA.GameElements.SCREEN_WIDTH;
import static com.mygdx.NEBULA.GameElements.SCREEN_WIDTH;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class Main extends Game {
	public SpriteBatch batch;

	public static BitmapFont scoreFont;
	public FreeTypeFontGenerator generator;
	public FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	Assets assets;

	public Sound playSound, pauseSound;

	@Override
	public void create () {
		batch = new SpriteBatch();

		assets = new Assets();
		assets.load();
		assets.assetManager.finishLoading();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("mainfont.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = SCREEN_WIDTH/14;

		scoreFont = generator.generateFont(parameter);
		scoreFont.setColor(1,1,1, 0.8f);

		playSound = assets.assetManager.get(Assets.play_sound, Sound.class);
		pauseSound = assets.assetManager.get(Assets.pause_sound, Sound.class);

		if(assets.assetManager.isFinished()) {
			this.setScreen(new MainMenu(this, assets));
		}
	}
	@Override
	public void dispose() {
		assets.unloadAll();
		assets.assetManager.dispose();
	}
}
