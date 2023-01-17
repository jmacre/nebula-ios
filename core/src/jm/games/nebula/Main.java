package jm.games.nebula;

import static jm.games.nebula.GameElements.SCREEN_WIDTH;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.pay.PurchaseManager;
import com.sun.tools.sjavac.Log;

import games.rednblack.miniaudio.MiniAudio;
import jdk.nashorn.internal.runtime.Context;
import pl.mk5.gdx.fireapp.GdxFIRApp;


public class Main extends Game implements ApplicationListener {

	public SpriteBatch batch;
	public static BitmapFont scoreFont;
	public FreeTypeFontGenerator generator;
	public FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	Assets assets;

	public Object miniAudioContextAssets;
	MiniAudio miniAudio;
	public IActivityRequestHandler requestHandlerAndroid, requestHandlerIOS;
	public PurchaseManager purchaseManager;

	public Main(Object miniAudioContextAssets, IActivityRequestHandler requestHandler){ // android constructor
		this.miniAudioContextAssets = miniAudioContextAssets;
		this.requestHandlerAndroid = requestHandler;
	}
	public Main(IActivityRequestHandler requestHandler){ // ios constructor
		this.requestHandlerIOS = requestHandler;
	}

	@Override
	public void create () {
		GdxFIRApp.inst().configure();
		batch = new SpriteBatch();
		miniAudio = new MiniAudio();
		miniAudio.setupAndroid(miniAudioContextAssets);

		Gdx.input.setCatchKey(Input.Keys.BACK, true);
		FreeTypeFontGenerator.setMaxTextureSize(2048);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = SCREEN_WIDTH/14;
		assets = new Assets();

		assets.load(miniAudio);
		assets.assetManager.finishLoading();

		scoreFont = generator.generateFont(parameter);
		scoreFont.setColor(1,1,1, 0.8f);

		this.setScreen(new MainMenu(this, assets));

	}

	@Override
	public void dispose() {
		super.dispose();
		assets.unloadAll();
		purchaseManager.dispose();
		assets.assetManager.dispose();
		miniAudio.dispose();
	}

	@Override
	public void pause(){
		super.pause();
		miniAudio.stopEngine();
	}
	@Override
	public void resume(){
		super.resume();
		miniAudio.startEngine();
	}
}
