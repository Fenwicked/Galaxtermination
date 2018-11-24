package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Timer;

public class MenuModes {
    AssetLoader al;
    VarLoader vl;
    float mmWait;

    public MenuModes(AssetLoader al, VarLoader vl){
        this.al = al;
        this.vl = vl;
        //mmWait = 0;
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
                        mmWait = 0;
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
//        if (Timer.instance().isEmpty()) {
//            //System.out.println("Scheduling!");
//            Timer.schedule(new Timer.Task() {
//                @Override
//                public void run() {
//                    vl.gameMode = vl.gameModeMap.get("Settings");
//                    //System.out.println("Clearing timer");
//                    Timer.instance().clear();
//                }
//            }, 1);
//        }

        al.batch.begin();
        if (vl.debugMode) al.font.draw(al.batch, "CREDITS",100,vl.windowHeight - 100);
        //al.batch.draw(al.dbgCredits,10,10);
        al.batch.end();
    }

    public void modeMainMenu () {
//        if (Timer.instance().isEmpty()) {
//            //System.out.println("Scheduling!");
//            Timer.schedule(new Timer.Task() {
//                @Override
//                public void run() {
//                    vl.gameMode = vl.gameModeMap.get("Credits");
//                    //System.out.println("Clearing timer");
//                    Timer.instance().clear();
//                }
//
//            }, 1);
//        }
        Gdx.gl.glClearColor(0.03f, 0.03f, 0.08f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        al.batch.begin();
        float logoWidth = al.logoImg.getWidth() * 0.8f;
        float logoHeight = al.logoImg.getHeight() * 0.8f;
        al.batch.draw(al.logoImg,(vl.windowWidth /2) - (logoWidth/2), ((vl.windowHeight /2) - (logoHeight/2)) + 100, logoWidth, logoHeight);
        if (mmWait < 3){
            mmWait += Gdx.graphics.getDeltaTime();
        }
        else
        {
            al.font.draw(al.batch, "PRESS ENTER TO START",(vl.windowWidth /2) - (logoWidth/2),((vl.windowHeight /2) - (logoHeight/2)));
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                mmWait = 0;
                vl.gameMode = vl.gameModeMap.get("Gameplay");
            }
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
