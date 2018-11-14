package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class GameplayModes {
    AssetLoader al;
    VarLoader vl;
    Gameobject2D playerShip;
    asteroid ast;
    Array<asteroid> asts;
    enemyShip enme;
    Array<enemyShip> enmes;

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
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
            playerShip.accelYaw(playerShip.accelRate);
            //al.cam.translate(0,0,0.1f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
            playerShip.accelYaw(-playerShip.accelRate);
            //al.cam.translate(0,0,-0.1f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
            playerShip.accelForward(-playerShip.accelRate);
            //al.cam.translate(-0.1f,0,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
            playerShip.accelForward(playerShip.accelRate);
            //al.cam.translate(0.1f,0,0);
        }
        Gdx.gl20.glDepthMask(false);
        playerShip.update();
        for (enemyShip enm: enmes){
            System.out.println(enmes.indexOf(enm, true)+ "|" + enm.posX + "|" + enm.posZ);
            enm.updateEnme();
        }
        //al.cam.position.x = playerShip.posX + 2;
        //al.cam.position.z = playerShip.posZ;
        //al.cam.position.x = returnPosArroundObj(new Vector3(playerShip.posX,-1.9f,playerShip.posZ),playerShip.yaw,2f,20f).x;
        //al.cam.position.z = returnPosArroundObj(new Vector3(playerShip.posX,-1.9f,playerShip.posZ),playerShip.yaw,2f,20f).z;
        Vector2 motionVec = new Vector2(5f, 0).rotate(-(float)playerShip.yaw + playerShip.yawSpeed);
        //this.motionX += Math.sin(this.yaw) * xMO;
        //this.motionZ += Math.cos(this.yaw) * xMO;
        //al.cam.position.x = playerShip.posX + (5f * (float)Math.sin(Math.toRadians(-Math.abs(playerShip.yaw))));
        //al.cam.position.z = playerShip.posZ + (5f * (float)Math.cos(Math.toRadians(-Math.abs(playerShip.yaw))));
        al.cam.position.x = playerShip.posX + motionVec.x;
        al.cam.position.z = playerShip.posZ + motionVec.y;
        al.cam.lookAt(new Vector3(playerShip.posX - motionVec.x * 2,0, playerShip.posZ - motionVec.y * 2));
        al.cam.up.set(al.cam.position.x, 5000, al.cam.position.z);

        al.cam.update();
        //al.camController.update();
        al.modelBatch.begin(al.cam);
        al.modelBatch.render(al.modelInstances, al.env);
        al.modelBatch.end();
        for (Decal dec : al.Decals){
            al.decalBatch.add(dec);
        }
        //playerShip.update();
        al.decalBatch.flush();
        al.batch.begin();
        if (vl.debugMode) al.font.draw(al.batch, "GAMEPLAY",100,vl.windowHeight - 100);
        //al.batch.draw(al.dbgGameplay,10,10);
        al.batch.end();
    }

    public static Vector3 returnPosArroundObj(Vector3 posObject, Float angleDegrees, Float radius, Float height) {
        Float angleRadians = angleDegrees * MathUtils.degreesToRadians;
        Vector3 position = new Vector3();
        position.set(radius * MathUtils.sin(angleRadians), height, radius * MathUtils.cos(angleRadians));
        position.add(posObject); //add the position so it would be arround object
        return position;
    }

    public void gameplayInit(){
        al.init3D();
        al.buildModel();
        playerShip = new Gameobject2D(al.shipDecal);
        playerShip.update();
        al.Decals.add(playerShip.myDecal);
        vl.gameplayInitialized = true;
        asts = new Array<asteroid>();
        enmes = new Array<enemyShip>();
        enmes.add(new enemyShip(al.addEnemyAt(5f,-1.5f,5f)));
        enmes.add(new enemyShip(al.addEnemyAt(-5f,-1.5f,5f)));
        enmes.add(new enemyShip(al.addEnemyAt(5f,-1.5f,-5f)));
        enmes.add(new enemyShip(al.addEnemyAt(-5f,-1.5f,-5f)));
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
