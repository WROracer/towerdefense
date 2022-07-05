package de.wroracer.towerdefensegame.events;

import java.util.ArrayList;

public class Wave {

    private ArrayList<Integer> enemyLis;

    public Wave(){
        enemyLis = new ArrayList<>();
    }

    public Wave(ArrayList<Integer> enemyLis) {
        this.enemyLis = enemyLis;
    }

    public void addEnemy(int id){
        enemyLis.add(id);
    }

    public ArrayList<Integer> getEnemyList() {
        return enemyLis;
    }
}
