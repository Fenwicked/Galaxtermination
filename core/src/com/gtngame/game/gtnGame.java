package com.gtngame.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;

public class gtnGame extends ApplicationAdapter {
	AssetLoader al;
	VarLoader vl;
	MenuModes mm;
	GameplayModes gm;

	@Override
	public void create () {
		vl = new VarLoader();
		vl.load();
		al = new AssetLoader(vl);
		al.load();
		mm = new MenuModes(al,vl);
		gm = new GameplayModes(al,vl);

		Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode();
		vl.windowWidth = mode.width - 100;
		vl.windowHeight = mode.height - 100;
		Gdx.graphics.setWindowedMode(vl.windowWidth, vl.windowHeight);
	}

	@Override
	public void render () {
		//called each frame, use as main loop
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		switch (vl.gameMode) {
			case 1: //SplashLoading
				mm.modeSplash();
				break;
			case 2: //Credits
				mm.modeCredits();
				break;
			case 3: //MainMenu
				mm.modeMainMenu();
				break;
			case 4: //Settings
				mm.modeSettings();
				break;
			case 5: //Gameplay
				gm.modeGameplay();
				break;
			case 6: //Paused
				gm.modePaused();
				break;
			case 7: //GameOver
				gm.modeGameOver();
				break;
			default:
				System.out.println("Invalid game mode");
				break;
		}
	}

	@Override
	public void dispose () {
		al.dispose();
	}
}
