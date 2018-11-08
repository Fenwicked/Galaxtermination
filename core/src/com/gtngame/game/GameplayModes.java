package com.gtngame.game;

import com.badlogic.gdx.utils.Timer;

public class GameplayModes {
    AssetLoader al;
    VarLoader vl;

    public GameplayModes(AssetLoader al, VarLoader vl){
        this.al = al;
        this.vl = vl;
    }

    public void modeGameplay () {
        al.batch.begin();
        if (Timer.instance().isEmpty()) {
            System.out.println("Scheduling!");
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    vl.gameMode = vl.gameModeMap.get("Paused");
                    System.out.println("Clearing timer");
                    Timer.instance().clear();
                }

            }, 1);
        }
        al.batch.draw(al.dbgGameplay,10,10);
        al.batch.end();
    }

    public void modePaused () {
        al.batch.begin();
        if (Timer.instance().isEmpty()) {
            System.out.println("Scheduling!");
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    vl.gameMode = vl.gameModeMap.get("GameOver");
                    System.out.println("Clearing timer");
                    Timer.instance().clear();
                }

            }, 1);
        }
        al.batch.draw(al.dbgPaused,10,10);
        al.batch.end();
    }

    public void modeGameOver () {
        al.batch.begin();
        if (Timer.instance().isEmpty()) {
            System.out.println("Scheduling!");
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    vl.gameMode = vl.gameModeMap.get("MainMenu");
                    System.out.println("Clearing timer");
                    Timer.instance().clear();
                }

            }, 1);
        }
        al.batch.draw(al.dbgGameOver,10,10);
        al.batch.end();
    }
}
