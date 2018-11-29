package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Timer;

public class MenuModes {
    AssetLoader al;
    VarLoader vl;
    float mmWait;
    float lettersInEnterText, lettersInCreditsText;

    public MenuModes(AssetLoader al, VarLoader vl){
        this.al = al;
        this.vl = vl;
        //mmWait = 0;
        lettersInEnterText = 0;
        lettersInCreditsText = 0;
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

                }, 2);
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

        Gdx.gl.glClearColor(0.03f, 0.03f, 0.08f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        al.batch.begin();
        al.batch.draw(al.titleBG,0f, 0f, vl.windowWidth, vl.windowHeight);
        float logoWidth = al.logoImg.getWidth() * 0.8f;
        float logoHeight = al.logoImg.getHeight() * 0.8f;
        al.batch.draw(al.logoImg,(vl.windowWidth /2) - (logoWidth/2), ((vl.windowHeight /2) - (logoHeight/2)) + 100, logoWidth, logoHeight);
        al.font.draw(al.batch, "ALL WORK BY MATT FENWICK",(vl.windowWidth /2) - (logoWidth/2),((vl.windowHeight /2) - (logoHeight/2)) + 110);
        al.font.draw(al.batch, "CREATED IN JAVA USING LIBGDX, INTELLIJ IDEA, AND GRADLE",(vl.windowWidth /2) - (logoWidth/2),((vl.windowHeight /2) - (logoHeight/2)) + 75);
        al.font.draw(al.batch, "SOUND EFFECTS CREATED IN SFXR",(vl.windowWidth /2) - (logoWidth/2),((vl.windowHeight /2) - (logoHeight/2)) + 40);
        al.font.draw(al.batch, "3D MODELING IN BLENDER",(vl.windowWidth /2) - (logoWidth/2),((vl.windowHeight /2) - (logoHeight/2)) + 5);
        al.font.draw(al.batch, "SUBMISSION FOR ITCH.IO GAME OFF 2018",(vl.windowWidth /2) - (logoWidth/2),((vl.windowHeight /2) - (logoHeight/2)) - 30);

        if (mmWait < 2){
            mmWait += Gdx.graphics.getDeltaTime();
        }
        else
        {
            mmWait += Gdx.graphics.getDeltaTime();
            al.font.draw(al.batch, "PRESS ENTER TO RETURN TO TITLE",(vl.windowWidth /2) - (logoWidth/2),100);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                mmWait = 0;
                vl.gameMode = vl.gameModeMap.get("MainMenu");
            }
        }
        //al.batch.draw(al.dbgMM,10,10);
        al.batch.end();
        if (vl.debugMode) al.font.draw(al.batch, "CREDITS",100,vl.windowHeight - 100);
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
        al.batch.draw(al.titleBG,0f, 0f, vl.windowWidth, vl.windowHeight);
        float logoWidth = al.logoImg.getWidth() * 0.8f;
        float logoHeight = al.logoImg.getHeight() * 0.8f;
        al.batch.draw(al.logoImg,(vl.windowWidth /2) - (logoWidth/2), ((vl.windowHeight /2) - (logoHeight/2)) + 100, logoWidth, logoHeight);
        al.font.draw(al.batch, "WASD OR ARROWS TO MOVE",(vl.windowWidth /2) - (logoWidth/2),((vl.windowHeight /2) - (logoHeight/2)) + 110);
        al.font.draw(al.batch, "SPACE TO SHOOT",(vl.windowWidth /2) - (logoWidth/2),((vl.windowHeight /2) - (logoHeight/2)) + 75);
        al.font.draw(al.batch, "ENTER TO PAUSE",(vl.windowWidth /2) - (logoWidth/2),((vl.windowHeight /2) - (logoHeight/2)) + 40);


        if (mmWait < 2){
            mmWait += Gdx.graphics.getDeltaTime();
        }
        else
        {
            mmWait += Gdx.graphics.getDeltaTime();
            if (mmWait > 4) mmWait = 4;
            lettersInEnterText = (mmWait - 2) / 0.05f;
            if (lettersInEnterText > 20) lettersInEnterText = 20;
            lettersInCreditsText = (mmWait - 2) / 0.05f;
            if (lettersInCreditsText > 21) lettersInCreditsText = 21;
            //System.out.println(lettersInEnterText);
            String toDraw = "PRESS ENTER TO START".substring(0,(int)lettersInEnterText);
            String toDrawCreds = "PRESS 'C' FOR CREDITS".substring(0,(int)lettersInCreditsText);
            al.font.draw(al.batch, toDraw,(vl.windowWidth / 2) + 100, 100);
            al.font.draw(al.batch, toDrawCreds,(vl.windowWidth /2) - (logoWidth/2),100);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                mmWait = 0;
                vl.gameMode = vl.gameModeMap.get("Gameplay");
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
                mmWait = 0;
                vl.gameMode = vl.gameModeMap.get("Credits");
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
