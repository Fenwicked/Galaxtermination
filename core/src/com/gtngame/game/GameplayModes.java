package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Timer;

public class GameplayModes {
    AssetLoader al;
    VarLoader vl;
    Gameobject2D playerShip;

    public GameplayModes(AssetLoader al, VarLoader vl){
        this.al = al;
        this.vl = vl;
    }

    public void modeGameplay () {
        if (!vl.gameplayInitialized) {
            gameplayInit();
        }
        if (Timer.instance().isEmpty()) {
            //System.out.println("Scheduling!");
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //vl.gameMode = vl.gameModeMap.get("Paused");
                    //System.out.println("Clearing timer");
                    //Timer.instance().clear();
                    //gameplayCleanup();
                }
                /*public void run() {
                    al.modelInstances.removeValue(al.cubeInstance,true);
                    //System.out.println("Clearing timer");
                    Timer.instance().clear();
                }*/
            }, 25);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            playerShip.accelZ(playerShip.accelRate);
            //al.cam.translate(0,0,0.1f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            playerShip.accelZ(-playerShip.accelRate);
            //al.cam.translate(0,0,-0.1f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            playerShip.accelX(-playerShip.accelRate);
            //al.cam.translate(-0.1f,0,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            playerShip.accelX(playerShip.accelRate);
            //al.cam.translate(0.1f,0,0);
        }
        al.cam.position.x = playerShip.posX + 2;
        al.cam.position.z = playerShip.posZ;
        al.cam.update();
        //al.camController.update();
        al.modelBatch.begin(al.cam);
        al.modelBatch.render(al.modelInstances, al.env);
        al.modelBatch.end();
        for (Decal dec : al.Decals){
            al.decalBatch.add(dec);
        }
        playerShip.update();
        al.decalBatch.flush();
        al.batch.begin();
        if (vl.debugMode) al.font.draw(al.batch, "GAMEPLAY",100,vl.windowHeight - 100);
        //al.batch.draw(al.dbgGameplay,10,10);
        al.batch.end();
    }

    public void gameplayInit(){
        al.init3D();
        al.buildModel();
        playerShip = new Gameobject2D(al.shipDecal);
        playerShip.update();
        vl.gameplayInitialized = true;
    }

    public void gameplayCleanup(){
        al.cubeInstance.model.dispose();
        al.planeInstance.model.dispose();
        al.modelInstances.clear();
        vl.gameplayInitialized = false;
    }

    public void modePaused () {
        al.batch.begin();
        if (Timer.instance().isEmpty()) {
            //System.out.println("Scheduling!");
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    vl.gameMode = vl.gameModeMap.get("GameOver");
                    //System.out.println("Clearing timer");
                    Timer.instance().clear();
                }

            }, 1);
        }
        if (vl.debugMode) al.font.draw(al.batch, "PAUSED",100,vl.windowHeight - 100);
        //al.batch.draw(al.dbgPaused,10,10);
        al.batch.end();
    }

    public void modeGameOver () {
        al.batch.begin();
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
        if (vl.debugMode) al.font.draw(al.batch, "GAME OVER",100,vl.windowHeight - 100);
        //al.batch.draw(al.dbgGameOver,10,10);
        al.batch.end();
    }
}
