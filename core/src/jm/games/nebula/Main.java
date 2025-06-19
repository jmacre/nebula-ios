package jm.games.nebula;

import static jm.games.nebula.GameElements.SCREEN_WIDTH;
import static jm.games.nebula.GameElements.WIDTH_ADJUSTMENT;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.pay.PurchaseManager;


import games.rednblack.miniaudio.MiniAudio;
import pl.mk5.gdx.fireapp.GdxFIRCrash;


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
		GdxFIRCrash.inst().initialize();

		batch = new SpriteBatch();
		miniAudio = new MiniAudio();
		miniAudio.setupAndroid(miniAudioContextAssets);

		GameElements.defineSizesAndPositions();

		Gdx.input.setCatchKey(Input.Keys.BACK, true);
		FreeTypeFontGenerator.setMaxTextureSize(2048);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = (int) ((SCREEN_WIDTH/14) * WIDTH_ADJUSTMENT);
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
