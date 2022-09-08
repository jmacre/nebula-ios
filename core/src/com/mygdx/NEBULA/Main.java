package com.mygdx.NEBULA;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


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
		parameter.size = Gdx.graphics.getWidth()/14;

		scoreFont = generator.generateFont(parameter);
		playSound = assets.assetManager.get(Assets.play_sound, Sound.class);
		pauseSound = assets.assetManager.get(Assets.pause_sound, Sound.class);

		if(assets.assetManager.isFinished()) {
			this.setScreen(new MainMenu(this, 0, assets));
		}
	}
	@Override
	public void dispose() {
		assets.unloadAll();
		assets.assetManager.dispose();
	}
}
