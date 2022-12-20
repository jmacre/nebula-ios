package com.mygdx.NEBULA;

import static com.mygdx.NEBULA.Assets.*;
import static com.mygdx.NEBULA.GameElements.SCREEN_WIDTH;
import static com.mygdx.NEBULA.GameElements.SCREEN_WIDTH;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import games.rednblack.miniaudio.MASound;
import games.rednblack.miniaudio.MiniAudio;
import games.rednblack.miniaudio.loader.MASoundLoader;
import jdk.nashorn.internal.runtime.Context;


public class Main extends Game implements ApplicationListener {
	public SpriteBatch batch;

	public static BitmapFont scoreFont;
	public FreeTypeFontGenerator generator;
	public FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	Assets assets;
	public MiniAudio miniAudio;

	public Main(MiniAudio miniAudio){
		this.miniAudio = miniAudio;
	}
	public Main(){}

	@Override
	public void create () {
		batch = new SpriteBatch();
		miniAudio = new MiniAudio();

		generator = new FreeTypeFontGenerator(Gdx.files.internal("mainfont.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = SCREEN_WIDTH/14;
		assets = new Assets();
		assets.load();
		assets.assetManager.finishLoading();

		if(assets.assetManager.isFinished()) {
			this.setScreen(new MainMenu(this, assets));
			scoreFont = generator.generateFont(parameter);
			scoreFont.setColor(1,1,1, 0.8f);
		}
	}

	@Override
	public void dispose() {
		assets.unloadAll();
		assets.assetManager.dispose();
//		miniAudio.dispose();
	}

	@Override
	public void pause(){
		miniAudio.stopEngine();
	}
	@Override
	public void resume(){
		miniAudio.startEngine();
	}
}
