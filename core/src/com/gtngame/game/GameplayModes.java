package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class GameplayModes {
    AssetLoader al;
    VarLoader vl;
    playerShip playerShip;
    enemyShip enme;
    asteroid ast;
    hundred hun;
    Array<enemyShip> enmes;
    Array<gridSpace> grid;
    boolean win;
    Random r;
    int gridSpacesPerSide = 15;
    int gridSpaceSize = 80;
    int totalEnemies = 100;
    float pauseTimer = 0;
    float lettersToDraw = 0;
    float letterTimer = 0;
    int whichToWrite;

    public GameplayModes(AssetLoader al, VarLoader vl){
        this.al = al;
        this.vl = vl;
        win = false;
        vl.gameplayInitialized = false;
    }

    public void modeGameplay () {
        if (!vl.gameplayInitialized) {
            gameplayInit();
        }
        lettersToDraw += Gdx.graphics.getDeltaTime();
        if (lettersToDraw > 2) lettersToDraw = 2;

//        if (Timer.instance().isEmpty()) {
//            //System.out.println("Scheduling!");
//            Timer.schedule(new Timer.Task() {
//                @Override
//                public void run() {
//                    //vl.gameMode = vl.gameModeMap.get("Paused");
//                    //System.out.println("Clearing timer");
//                    //Timer.instance().clear();
//                    //gameplayCleanup();
//                }
//                /*public void run() {
//                    al.modelInstances.removeValue(al.cubeInstance,true);
//                    //System.out.println("Clearing timer");
//                    Timer.instance().clear();
//                }*/
//            }, 25);
//        }
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
        if (playerShip.isImmune && playerShip.immuneTimer % 0.2f > 0.1f){
            playerShip.myDecal.setTextureRegion(al.playerShipInvisReg);
        }
        else
        {
            if (playerShip.animTimer % 0.1f > 0.05f) {
                playerShip.myDecal.setTextureRegion(al.playerShipEngineReg);
            }
            else
            {
                playerShip.myDecal.setTextureRegion(al.playerShipReg);
            }
        }
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
            sht.updateShot(al.enmes, al.asts,true, playerShip);
        }
        for (asteroid ast : al.asts){
            ast.updateAst();

        }
        for (hundred hun : al.huns){
            hun.updateHun();

        }
//        for (AnimationController astAnim : al.astAnim){
//            astAnim.update(Gdx.graphics.getDeltaTime());
//        }
        //System.out.println(al.modelInstances.size);
        updateCam();
        al.cam.project(new Vector3());
        al.modelBatch.begin(al.cam);
        al.modelBatch.render(al.modelInstances, al.env);
        al.modelBatch.end();
        for (Decal dec : al.Decals){
            al.decalBatch.add(dec);
        }
        al.decalBatch.flush();
        al.batch.begin();
        for (PartFX FX : al.PFXs){
            FX.update(Gdx.graphics.getDeltaTime());
            FX.updatePos();
            FX.draw(al.batch);
            if (FX.isComplete()) {
                al.PFXs.removeValue(FX, true);
            }
        }
        if (vl.debugMode) al.font.draw(al.batch, "GAMEPLAY",100,vl.windowHeight - 100);

        /*

        lettersInEnterText = (mmWait - 2) / 0.05f;
        String toDraw = "PRESS ENTER TO START".substring(0,(int)lettersInEnterText);
        al.font.draw(al.batch, toDraw,(vl.windowWidth /2) - (logoWidth/2),((vl.windowHeight /2) - (logoHeight/2)));
        */
        int numToDrawNearest = (int)(lettersToDraw / 0.08f);
        int nearestEnme = (int)playerShip.findNearest(enmDecs);
        int nearestLen = ("NEAREST ENEMY: " + nearestEnme).length();
        if (numToDrawNearest >= nearestLen){
            numToDrawNearest = nearestLen;
        }
        int numToDrawRemaining = (int)(lettersToDraw / 0.08f);
        int enmesRemain = al.enmes.size;
        int remainLen = ("ENEMIES REMAINING: " + enmesRemain).length();
        if (numToDrawRemaining >= remainLen){
            numToDrawRemaining = remainLen;
        }
        int numToDrawScore = (int)(lettersToDraw / 0.08f);
        int score = playerShip.score;
        int scoreLen = ("SCORE: " + score).length();
        if (numToDrawScore >= scoreLen){
            numToDrawScore = scoreLen;
        }
        String toDrawNearest = ("NEAREST ENEMY: " + nearestEnme).substring(0, numToDrawNearest);
        String toDrawRemaining = ("ENEMIES REMAINING: " + enmesRemain).substring(0, numToDrawRemaining);
        String toDrawScore = ("SCORE: " + score).substring(0, numToDrawScore);
        al.font.draw(al.batch, toDrawNearest,50,vl.windowHeight - 40);
        al.font.draw(al.batch, toDrawRemaining,50,vl.windowHeight - 75);
        al.font.draw(al.batch, toDrawScore,50,vl.windowHeight - 110);
//(vl.windowWidth /2) - (al.shieldReg.getRegionWidth() * 2) - 9
        al.font.draw(al.batch, "SHIELDS: ",(vl.windowWidth /2) - 200,70);
        if (playerShip.shields >= 1) al.batch.draw(al.shieldReg,(vl.windowWidth /2) - al.shieldReg.getRegionWidth() - 9, 20);
        if (playerShip.shields >= 2) al.batch.draw(al.shieldReg,(vl.windowWidth /2) + 2, 20);

        al.batch.end();
        al.modelInstances.clear();
        if (enmesRemain <= 0) {
            win = true;
            pauseTimer = 0;
            r = new Random();
            whichToWrite = r.nextInt(21);
            vl.finalScore = playerShip.score;
            vl.gameMode = vl.gameModeMap.get("GameOver");
        }
        if (playerShip.shields <= -1) {
            win = false;
            pauseTimer = 0;
            r = new Random();
            whichToWrite = r.nextInt(21);
            vl.finalScore = playerShip.score;
            vl.gameMode = vl.gameModeMap.get("GameOver");
        }
    }
    public float getCameraCurrentXYAngle(PerspectiveCamera cam)
    {
        return (float)Math.atan2(cam.direction.x, cam.direction.z)* MathUtils.radiansToDegrees;
    }
    public void updateCam(){
        Vector2 motionVec = new Vector2(4f, 0f).rotate(-(float)playerShip.yaw);
        al.cam.position.x = playerShip.posX + motionVec.x;
        al.cam.position.z = playerShip.posZ + motionVec.y;
        float camAngle = -getCameraCurrentXYAngle(al.cam) + 180f;
        al.cam.rotate(new Vector3(0f,1f,0f),(float)(camAngle+playerShip.yaw+playerShip.yawSpeed)+90f);

        //        Vector3 priorRot = al.cam.direction;
//        al.cam.direction.set
        //al.cam.lookAt(new Vector3(playerShip.posX - motionVec.x * 3,-1.9f, playerShip.posZ - motionVec.y * 3));
//        //al.cam.direction.set(priorDir.x, al.cam.direction.y, priorDir.z);
//        //al.cam.direction.set(al.cam.position.x - playerShip.posX, al.cam.direction.y, al.cam.position.z - playerShip.posZ);
////        al.cam.up.set(alreadyUp);
//        //al.cam.up.set(playerShip.posX, al.cam.position.y + 1000, playerShip.posZ);
//        al.cam.up.set(al.cam.position.x, 15000, al.cam.position.z);

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
        vl.gameplayInitialized = true;
        //System.out.println("size: " + al.enmes.size);
    }

    public void makeGrid(int xRows, int zCols, float gridX, float gridZ){
        //hun = new hundred(0,5, al);
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
                    al.enmes.add(new enemyShip(al.addEnemyAt(gs.col * gs.xDim - offsetX,-1.5f,gs.row * gs.zDim - offsetZ),al, playerShip));
                    cntEnemies ++;
                }
            }
        }
        for (gridSpace gs : grid) {
            //r = new Random();
            if (r.nextBoolean() && gs.hasSomething == false) {
                gs.setAst();
                float randOffset = r.nextInt(15) - 15;
                if (randOffset >= 0 && randOffset <= 1) randOffset += 1;
                if (randOffset < 0 && randOffset >= -1) randOffset -= 1;
                ast = new asteroid((gs.col * gs.xDim - offsetX + randOffset),(gs.row * gs.zDim - offsetZ + randOffset), al);
                ast = new asteroid((gs.col * gs.xDim - offsetX - randOffset),(gs.row * gs.zDim - offsetZ + randOffset), al);
                ast = new asteroid((gs.col * gs.xDim - offsetX - randOffset),(gs.row * gs.zDim - offsetZ - randOffset), al);
            }
        }
        for (int astI = 0; astI < 200; astI ++){
            ast = new asteroid(astI + 5,0, al);
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
//        if (Timer.instance().isEmpty()) {
//            //System.out.println("Scheduling!");
//            Timer.schedule(new Timer.Task() {
//                @Override
//                public void run() {
//                    vl.gameMode = vl.gameModeMap.get("MainMenu");
//                    //System.out.println("Clearing timer");
//                    Timer.instance().clear();
//                }
//
//            }, 1);
//        }
        if (pauseTimer < 4){
            pauseTimer += Gdx.graphics.getDeltaTime();
        }
        else
        {
//            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
//                pauseTimer = 0;
//                vl.load();
//                for (gridSpace gs : grid){
//                    gs.hasSomething = false;
//                    gs.hasAsteroid = false;
//                    gs.hasEnemy = false;
//                }
//                grid.clear();
//                al.asts.clear();
//                al.enmes.clear();
//                vl.gameMode = vl.gameModeMap.get("MainMenu");
//            }
        }
        Texture toDraw;
        String toWrite;
        if (win) {
            toDraw = al.victoryImg;
            Gdx.gl.glClearColor(0.03f, 0.08f, 0.1f, 0);
            toWrite = vl.victoryText.get(whichToWrite);
        }
        else
        {
            toDraw = al.gameoverImg;
            Gdx.gl.glClearColor(0.1f, 0.03f, 0.03f, 0);
            toWrite = vl.gameOverText.get(whichToWrite);
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        al.batch.begin();
        al.batch.draw(toDraw, (vl.windowWidth / 2) - (toDraw.getWidth() / 2), (vl.windowHeight / 2) - (toDraw.getHeight() / 2) + 100);
        if (vl.debugMode) al.font.draw(al.batch, "GAME OVER",100,vl.windowHeight - 100);
        float lettersInFlavorText = pauseTimer / 0.05f;
        if (lettersInFlavorText > toWrite.length()) lettersInFlavorText = toWrite.length();
        String flavorText = toWrite.substring(0, (int) lettersInFlavorText);
        al.font.draw(al.batch, flavorText, 100, (vl.windowHeight / 2) - (toDraw.getHeight() / 2));
        if (pauseTimer >= 2) {
            float textTimer = pauseTimer - 2;
            if (textTimer >= 2 ) textTimer = 2;
            float lettersInEnterText = textTimer / 0.08f;
            if (lettersInEnterText >= 23) lettersInEnterText = 23;
            float lettersInScoreText = textTimer / 0.08f;
            String scoreString = "FINAL SCORE: " + vl.finalScore;
            if (lettersInScoreText >= scoreString.length()) lettersInScoreText = scoreString.length();
            String scoreToWrite = scoreString.substring(0, (int) lettersInScoreText);
            String enterText = "PRESS ENTER TO CONTINUE".substring(0, (int) lettersInEnterText);
            al.font.draw(al.batch, scoreToWrite, (vl.windowWidth / 2) + 100, 135);
            al.font.draw(al.batch, enterText, (vl.windowWidth / 2) + 100, 100);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                pauseTimer = 0;
                vl.load();
                for (gridSpace gs : grid){
                    gs.hasSomething = false;
                    gs.hasAsteroid = false;
                    gs.hasEnemy = false;
                }
                grid.clear();
                al.asts.clear();
                al.enmes.clear();
                vl.gameMode = vl.gameModeMap.get("MainMenu");
            }
        }

        al.batch.end();
    }
}
