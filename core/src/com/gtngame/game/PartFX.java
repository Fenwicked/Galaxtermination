package com.gtngame.game;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector3;

public class PartFX extends ParticleEffect {
    float posX, posY, posZ;
    AssetLoader al;

    public PartFX(AssetLoader al){
        this.al = al;
    }

    public void SetWorldPos(float x, float y, float z){
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }

    public void updatePos(){
        float partX = al.cam.project(new Vector3(posX,posY,posZ)).x;
        float partY = al.cam.project(new Vector3(posX,posY,posZ)).y;
        this.getEmitters().first().setPosition(partX, partY);
        //System.out.println(posZ + " " + posY + " " + posZ + " " + partX + " " + partY);
    }
}
