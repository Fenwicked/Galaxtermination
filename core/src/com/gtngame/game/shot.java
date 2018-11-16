package com.gtngame.game;

public class shot extends Gameobject2D {
    AssetLoader al;
    float startX, startZ, totalDist, startYaw;
    public shot (float startX, float startZ, float totalDist, float startYaw, AssetLoader al){
        this.al = al;
        this.startX = startX;
        this.startZ = startZ;
        this.totalDist = totalDist;
        this.startYaw = startYaw;
    }

    public void updateShot(){

    }
}
