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
    playerShip playerShip;
    enemyShip enme;
    asteroid ast;
    Array<enemyShip> enmes;
    int numPlayerShots;
    float playerShotTimer;
    boolean playerShotTimerStarted;

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
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerShip.accelYaw(playerShip.accelRate);
            //al.cam.translate(0,0,0.1f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerShip.accelYaw(-playerShip.accelRate);
            //al.cam.translate(0,0,-0.1f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerShip.accelForward(-playerShip.accelRate);
            //al.cam.translate(-0.1f,0,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerShip.accelForward(playerShip.accelRate);
            //al.cam.translate(0.1f,0,0);
        }
        //System.out.println(playerShotTimer);
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            playerShip.shoot();;
        }
        Gdx.gl20.glDepthMask(false);
        playerShip.update();
        //al.addCircleAt(playerShip.posX,-2, playerShip.posZ);
        Array<Decal> enmDecs = new Array<Decal>();
        for (enemyShip enm: al.enmes){
            enmDecs.add(enm.myDecal);
        }
        enmDecs.add(playerShip.myDecal);
        ///System.out.println("size2: " + enmDecs.size);
        //al.modelInstances.clear();
        for (enemyShip enm: al.enmes){
            //System.out.println(al.enmes.indexOf(enm, true)+ "|" + enm.posX + "|" + enm.posZ);
            enm.updateEnme(enmDecs);
        }
        for (shot sht : al.shots){
            sht.updateShot(al.enmes, al.asts,true);
        }
        for (asteroid ast : al.asts){
            ast.updateAst();
        }
        //System.out.println(al.modelInstances.size);
        Vector2 motionVec = new Vector2(5f, 0).rotate(-(float)playerShip.yaw + playerShip.yawSpeed);
        al.cam.position.x = playerShip.posX + motionVec.x;
        al.cam.position.z = playerShip.posZ + motionVec.y;
        al.cam.lookAt(new Vector3(playerShip.posX - motionVec.x * 2,0, playerShip.posZ - motionVec.y * 2));
        al.cam.up.set(al.cam.position.x, 5000, al.cam.position.z);

        al.cam.update();
        al.modelBatch.begin(al.cam);
        al.modelBatch.render(al.modelInstances, al.env);
        al.modelBatch.end();
        for (Decal dec : al.Decals){
            al.decalBatch.add(dec);
        }
        al.decalBatch.flush();
        al.batch.begin();
        if (vl.debugMode) al.font.draw(al.batch, "GAMEPLAY",100,vl.windowHeight - 100);
        //al.batch.draw(al.dbgGameplay,10,10);
        al.modelInstances.clear();
        al.batch.end();
    }

    public void gameplayInit(){
        al.init3D();
        al.buildModel();
        playerShip = new playerShip(al.shipDecal, al);
        playerShip.update();
        al.Decals.add(playerShip.myDecal);
        //al.addCircleAt(playerShip.posX,-2, playerShip.posZ);
        vl.gameplayInitialized = true;
        al.enmes = new Array<enemyShip>();
        /*al.enmes.add(new enemyShip(al.addEnemyAt(-25,-1.5f,25f),al));
        al.enmes.add(new enemyShip(al.addEnemyAt(-25f,-1.5f,-25),al));
        al.enmes.add(new enemyShip(al.addEnemyAt(25f,-1.5f,25f),al));
        al.enmes.add(new enemyShip(al.addEnemyAt(25f,-1.5f,-25),al));*/
        ast = new asteroid(-10,-10, al);
        ast = new asteroid(-20,-10, al);
        ast = new asteroid(-30,-10, al);
        ast = new asteroid(-10,0, al);
        ast = new asteroid(-20,0, al);
        ast = new asteroid(-30,0, al);
        ast = new asteroid(-10,10, al);
        ast = new asteroid(-20,10, al);
        ast = new asteroid(-30,10, al);
        //System.out.println("size: " + al.enmes.size);
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
