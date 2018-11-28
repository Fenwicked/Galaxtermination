package com.gtngame.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

//import java.util.Random;

public class hundred extends Gameobject3D {
    ModelInstance myMI;
    //Random r;
    public hundred(float startX, float startZ, AssetLoader al){
        this.posX = startX;
        this.posZ = startZ;
        this.al = al;
        //r = new Random();
        //yawSpeed = r.nextFloat();
        yawSpeed = 2f;
        //pitchSpeed = r.nextFloat();
        //rollSpeed = r.nextFloat();
        al.addHundredAt(this);
    }

    public void updateHun(){
        yaw += yawSpeed;
        //pitch += pitchSpeed;
        //roll += rollSpeed;
        refreshHun();
        //al.modelInstances.add(myMI);
        /*if (collide) {
            for (enemyShip nme : enmes) {
                if (getDistForCollision(nme) <= 2) {
                    al.enmes.removeValue(nme, true);
                    al.Decals.removeValue(nme.myDecal, true);
                    al.oww.play(1.5f);
                }
            }
        }*/
    }
    public void refreshHun(){
        al.addHundredInstanceAt(posX, -1.5f, posZ, (float)yaw);
    }
}
