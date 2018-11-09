package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

import java.util.HashMap;

public class VarLoader {
    public HashMap<String, Integer> gameModeMap = new HashMap<String, Integer>();
    public Integer gameMode;
    public Boolean debugMode;
    public Boolean gameplayInitialized;
    public Integer windowWidth, windowHeight;

    public void load () {
        Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode();
        windowWidth = mode.width - 100;
        windowHeight = mode.height - 100;

        gameModeMap.put("Splash",1);
        gameModeMap.put("Credits",2);
        gameModeMap.put("MainMenu",3);
        gameModeMap.put("Settings",4);
        gameModeMap.put("Gameplay",5);
        gameModeMap.put("Paused",6);
        gameModeMap.put("GameOver",7);
        /*	System.out.println("Size of map is:- " + gameModeMap.size());
		if (gameModeMap.containsKey("Gameplay"))
		{
			Integer a = gameModeMap.get("Gameplay");
			System.out.println("value for key Gameplay is:- " + a);
		} */
        gameMode = gameModeMap.get("Splash");
        debugMode = true;
        gameplayInitialized = false;
    }
}
