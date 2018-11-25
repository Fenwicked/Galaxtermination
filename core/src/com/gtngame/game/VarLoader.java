package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class VarLoader {
    public HashMap<String, Integer> gameModeMap;
    public Integer gameMode;
    public Boolean debugMode;
    public Boolean gameplayInitialized;
    public Integer windowWidth, windowHeight;
    public Array<String> victoryText;
    public Array<String> gameOverText;

    public void load () {
        gameModeMap = new HashMap<String, Integer>();
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
        debugMode = false;
        gameplayInitialized = false;

        victoryText = new Array<String>();
        victoryText.add("YOU HAVE SUCCEEDED WHERE COUNTLESS OTHERS HAVE NOT.");
        victoryText.add("YOUR PARENTS ARE PROUD.");
        victoryText.add("YOU BRING GLORY TO YOURSELF!");
        victoryText.add("YOUR NAME WILL BE ECHOED UNTO ETERNITY.");
        victoryText.add("THEY WILL SING SONGS OF THIS DAY.");
        victoryText.add("COOL!");
        victoryText.add("GRATS!");
        victoryText.add("YOU HAVE PROVEN YOUR SKILLS.");
        victoryText.add("YOUR ENEMIES WOULD COWER, IF ANY REMAINED.");
        victoryText.add("GEEZ, THEY HAD FAMILIES, YOU KNOW?");
        victoryText.add("TRY FOR A HIGHER SCORE!");
        victoryText.add("BETCHA CAN'T DO THAT AGAIN.");
        victoryText.add("YOUR CONQUEST IS LEGENDARY.");
        victoryText.add("NONE REMAIN.");
        victoryText.add("THE BLOOD OF YOUR SLAIN FOES PAINTS YOUR SHIP'S HULL.");
        victoryText.add("THE STARS ARE RED TONIGHT.");
        victoryText.add("SURVIVAL IS ONLY FOR THE FITTEST.");
        victoryText.add("TRY AGAIN?");
        victoryText.add("YOU LAUGH AT THOSE WHO TRY TO DEFEAT YOU.");
        victoryText.add("DAAAAAMN...");
        victoryText.add("OKAY, OKAY. YOU WIN.");

        gameOverText = new Array<String>();
        gameOverText.add("SIMPLY WASTEFUL.");
        gameOverText.add("YOUR ENEMIES LAUGH AT YOU.");
        gameOverText.add("THE VOID CALLS.");
        gameOverText.add("OOPSIE WOOPSIE!");
        gameOverText.add("OWWIE WOWWIE!");
        gameOverText.add(" IMPROVE YOURSELF.");
        gameOverText.add("YOU RETURN TO NOTHINGNESS.");
        gameOverText.add("PITIFUL.");
        gameOverText.add("AN INSUFFICIENT EFFORT.");
        gameOverText.add("DARKNESS GREETS YOU.");
        gameOverText.add(" IF I HAD A NICKEL FOR EVERY TIME THIS HAPPENED TO ME...");
        gameOverText.add("YOUR OPPONENTS CLAIM VICTORY THIS DAY.");
        gameOverText.add("DRIFT FOREVER.");
        gameOverText.add("THAT WASN'T SUPPOSED TO HAPPEN.");
        gameOverText.add("TRY AGAIN?");
        gameOverText.add("HAHAHAHAHAHAHA");
        gameOverText.add("DEATH IS YOUR ONLY COMPANION.");
        gameOverText.add("YOUR SCREAMS ARE SILENCED BY THE VACUUM OF SPACE.");
        gameOverText.add("PICK YOURSELF UP AND TRY AGAIN.");
        gameOverText.add(" I HAD HOPED FOR BETTER.");
        gameOverText.add(" IS THAT THE BEST YOU CAN DO?");
    }
}
