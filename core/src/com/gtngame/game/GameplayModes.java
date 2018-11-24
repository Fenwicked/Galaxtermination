package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;

public class GameplayModes {
    AssetLoader al;
    VarLoader vl;
    playerShip playerShip;
    enemyShip enme;
    asteroid ast;
    Array<enemyShip> enmes;
    Array<gridSpace> grid;
    int numPlayerShots;
    float playerShotTimer;
    boolean playerShotTimerStarted;
    Random r;
    int gridSpacesPerSide = 15;
    int gridSpaceSize = 40;
    int totalEnemies = 100;
    float pauseTimer = 0;

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
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            pauseTimer = 0;
            vl.gameMode = vl.gameModeMap.get("Paused");
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
//        for (AnimationController astAnim : al.astAnim){
//            astAnim.update(Gdx.graphics.getDeltaTime());
//        }
        //System.out.println(al.modelInstances.size);
        updateCam();

        al.modelBatch.begin(al.cam);
        al.modelBatch.render(al.modelInstances, al.env);
        al.modelBatch.end();
        for (Decal dec : al.Decals){
            al.decalBatch.add(dec);
        }
        al.decalBatch.flush();
        al.batch.begin();
        if (vl.debugMode) al.font.draw(al.batch, "GAMEPLAY",100,vl.windowHeight - 100);
        al.font.draw(al.batch, "NEAREST ENEMY: " + (int)playerShip.findNearest(enmDecs),100,vl.windowHeight - 100);
        al.font.draw(al.batch, "ENEMIES REMAINING: " + al.enmes.size,100,vl.windowHeight - 150);
        //al.batch.draw(al.dbgGameplay,10,10);
        al.modelInstances.clear();
        al.batch.end();
    }

    public void updateCam(){
        Vector2 motionVec = new Vector2(3f, 0).rotate(-(float)playerShip.yaw - playerShip.yawSpeed);
        al.cam.position.x = playerShip.posX + motionVec.x;
        al.cam.position.z = playerShip.posZ + motionVec.y;
        al.cam.lookAt(new Vector3(playerShip.posX - motionVec.x * 3,0, playerShip.posZ - motionVec.y * 3));
        al.cam.up.set(al.cam.position.x, 15000, al.cam.position.z);

        al.cam.update();
    }
    public void refreshCam(){
//        Vector2 motionVec = new Vector2(3f, 0).rotate(-(float)playerShip.yaw - playerShip.yawSpeed);
//        al.cam.position.x = playerShip.posX + motionVec.x;
//        al.cam.position.z = playerShip.posZ + motionVec.y;
//        al.cam.lookAt(new Vector3(playerShip.posX - motionVec.x * 3,0, playerShip.posZ - motionVec.y * 3));
//        al.cam.up.set(al.cam.position.x, 15000, al.cam.position.z);
//
//        al.cam.update();
        updateCam();
    }

    public void gameplayInit(){
        al.init3D();
        al.buildModel();
        grid = new Array<gridSpace>();
        al.enmes = new Array<enemyShip>();
        makeGrid(gridSpacesPerSide, gridSpacesPerSide, gridSpaceSize, gridSpaceSize);
        playerShip = new playerShip(al.shipDecal, al);
        playerShip.update();
        al.Decals.add(playerShip.myDecal);
        //al.addCircleAt(playerShip.posX,-2, playerShip.posZ);
        vl.gameplayInitialized = true;
        /*al.enmes.add(new enemyShip(al.addEnemyAt(-25,-1.5f,25f),al));
        al.enmes.add(new enemyShip(al.addEnemyAt(-25f,-1.5f,-25),al));
        al.enmes.add(new enemyShip(al.addEnemyAt(25f,-1.5f,25f),al));
        al.enmes.add(new enemyShip(al.addEnemyAt(25f,-1.5f,-25),al));*/
//        ast = new asteroid(-10,-10, al);
//        ast = new asteroid(-20,-10, al);
//        ast = new asteroid(-30,-10, al);
//        ast = new asteroid(-10,0, al);
//        ast = new asteroid(-20,0, al);
//        ast = new asteroid(-30,0, al);
//        ast = new asteroid(-10,10, al);
//        ast = new asteroid(-20,10, al);
//        ast = new asteroid(-30,10, al);
        //System.out.println("size: " + al.enmes.size);
    }

    public void makeGrid(int xRows, int zCols, float gridX, float gridZ){
        int cntEnemies = 0;
        int offsetX = gridSpacesPerSide * gridSpaceSize / 2, offsetZ = gridSpacesPerSide * gridSpaceSize / 2;
        r = new Random();
        for (int row = 1; row <= xRows; row++){
            for (int col = 1; col <= zCols; col++){
                gridSpace newGS = new gridSpace(row, col, gridX, gridZ);
                if ((row == 5 || row == 6) && (col == 5 || col == 6)) {
                    newGS.hasSomething = true;
                }
                grid.add(newGS);
            }
        }
        while (cntEnemies < totalEnemies) {
            for (gridSpace gs : grid) {
                //r = new Random();
                if (r.nextBoolean() && cntEnemies < totalEnemies && gs.hasSomething == false) {
                    gs.setEnemy();
                    al.enmes.add(new enemyShip(al.addEnemyAt(gs.col * gs.xDim - offsetX,-1.5f,gs.row * gs.zDim - offsetZ),al));
                    cntEnemies ++;
                }
            }
        }
        for (gridSpace gs : grid) {
            //r = new Random();
            if (r.nextBoolean() && gs.hasSomething == false) {
                gs.setAst();
                float randOffset = r.nextInt(15) - 15;
                ast = new asteroid((gs.col * gs.xDim - offsetX + randOffset),(gs.row * gs.zDim - offsetZ + randOffset), al);
            }
        }
        //System.out.println(cntEnemies);
    }
    public void gameplayCleanup(){
        al.cubeInstance.model.dispose();
        al.planeInstance.model.dispose();
        al.modelInstances.clear();
        vl.gameplayInitialized = false;
    }

    public void modePaused () {
        if (pauseTimer < 1){
            pauseTimer += Gdx.graphics.getDeltaTime();
        }
        else
        {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                pauseTimer = 0;
                vl.gameMode = vl.gameModeMap.get("Gameplay");
            }
        }

        Gdx.gl20.glDepthMask(false);
        playerShip.refresh();
        Array<Decal> enmDecs = new Array<Decal>();
        for (enemyShip enm: al.enmes){
            enmDecs.add(enm.myDecal);
        }
        enmDecs.add(playerShip.myDecal);
        for (enemyShip enm: al.enmes){
            enm.refreshEnme(enmDecs);
        }
        for (shot sht : al.shots){
            sht.refreshShot(al.enmes, al.asts,true);
        }
        for (asteroid ast : al.asts){
            ast.refreshAst();

        }
        refreshCam();

        al.modelBatch.begin(al.cam);
        al.modelBatch.render(al.modelInstances, al.env);
        al.modelBatch.end();
        for (Decal dec : al.Decals){
            al.decalBatch.add(dec);
        }
        al.decalBatch.flush();
        al.batch.begin();
        //al.font.draw(al.batch, "NEAREST ENEMY: " + (int)playerShip.findNearest(enmDecs),100,vl.windowHeight - 100);
        //al.font.draw(al.batch, "ENEMIES REMAINING: " + al.enmes.size,100,vl.windowHeight - 150);
        al.modelInstances.clear();
        al.batch.draw(al.pausedImg,(vl.windowWidth /2) - (al.pausedImg.getWidth() /2), (vl.windowHeight /2) - (al.pausedImg.getHeight() /2));
        //al.font.draw(al.batch, "PAUSED",100,vl.windowHeight - 100);
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
