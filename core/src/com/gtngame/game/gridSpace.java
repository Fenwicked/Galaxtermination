package com.gtngame.game;

public class gridSpace {
    public boolean hasSomething, hasEnemy, hasAsteroid;
    public float xDim;
    public float zDim;
    public int row, col;

    public gridSpace(int row, int col, float xDim, float zDim){
        this.row = row;
        this.col = col;
        this.xDim = xDim;
        this.zDim = zDim;
    }
    public void setEnemy(){
        hasEnemy = true;
        hasSomething = true;
    }
    public void setAst(){
        hasAsteroid = true;
        hasSomething = true;
    }
}
