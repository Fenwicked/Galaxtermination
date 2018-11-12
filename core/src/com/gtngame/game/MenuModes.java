package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;

public class MenuModes {
    AssetLoader al;
    VarLoader vl;

    public MenuModes(AssetLoader al, VarLoader vl){
        this.al = al;
        this.vl = vl;
    }

    public void modeSplash () {
        //System.out.println(splashTimer);
        al.batch.begin();
        al.splashSprite.draw(al.batch, al.splashTimer /255.0f);
        al.splashTimer += 100* Gdx.graphics.getDeltaTime(); //coefficient changes splash screen fade in time
        if (al.splashTimer > 255f)
        {
            al.splashTimer = 255f;
            //gameMode = gameModeMap.get("Splash");
            al.splashSprite.draw(al.batch, 1);
            if (Timer.instance().isEmpty()) {
                //System.out.println("Scheduling!");
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        vl.gameMode = vl.gameModeMap.get("MainMenu");
                        //System.out.println("Clearing timer");
                        Timer.instance().clear();
                    }

                }, 1);
            }
        }
        if (vl.debugMode) al.font.draw(al.batch, "SPLASH",100,vl.windowHeight - 100);
        //al.batch.draw(al.dbgSplash,10,10);
        al.batch.end();
    }

    public void modeCredits () {
        al.batch.begin();
        if (Timer.instance().isEmpty()) {
            //System.out.println("Scheduling!");
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    vl.gameMode = vl.gameModeMap.get("Settings");
                    //System.out.println("Clearing timer");
                    Timer.instance().clear();
                }

            }, 1);
        }
        if (vl.debugMode) al.font.draw(al.batch, "CREDITS",100,vl.windowHeight - 100);
        //al.batch.draw(al.dbgCredits,10,10);
        al.batch.end();
    }

    public void modeMainMenu () {
        al.batch.begin();
        if (Timer.instance().isEmpty()) {
            //System.out.println("Scheduling!");
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    vl.gameMode = vl.gameModeMap.get("Credits");
                    //System.out.println("Clearing timer");
                    Timer.instance().clear();
                }

            }, 1);
        }
        if (vl.debugMode) al.font.draw(al.batch, "MAIN MENU",100,vl.windowHeight - 100);
        //al.batch.draw(al.dbgMM,10,10);
        al.batch.end();
    }

    public void modeSettings () {
        al.batch.begin();
        if (Timer.instance().isEmpty()) {
            //System.out.println("Scheduling!");
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    vl.gameMode = vl.gameModeMap.get("Gameplay");
                    //System.out.println("Clearing timer");
                    Timer.instance().clear();
                }

            }, 1);
        }
        if (vl.debugMode) al.font.draw(al.batch, "SETTINGS",100,vl.windowHeight - 100);
        //al.batch.draw(al.dbgSettings,10,10);
        al.batch.end();
    }
}
