package de.wroracer.towerdefensegame.managers;

import de.wroracer.towerdefensegame.events.Wave;
import de.wroracer.towerdefensegame.scenes.Playing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WaveManager {
    private Playing playing;

    private List<Wave> waves = new ArrayList<>();
    private int enemyIndex,waveIndex;

    private int enemySpawnTickLimit = 60;
    private int enemySpawnTick = enemySpawnTickLimit;

    private int waveTickLimit = 60*5;
    private int waveTick = 0;

    private boolean waveStartTimer,waveTickTimerOver;

    public WaveManager(Playing playing) {
        this.playing = playing;
        createWaves();
    }

    private void createWaves() {
        waves.add(new Wave(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,1))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(2,0,0,0,0,0,0,0,0,1))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(2,2,2,0,1,1,0,0,0,1))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(3,2,2,0,0,0,2,0,1,1))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(3,2,2,1,1,1,2,0,1,1))));

        Random r = new Random();
        for (int w = 0;w!=5;w++){
            int e = r.nextInt(20)+10;
            Wave wave = new Wave();
            for (int i = 0;i!=e;i++){
                wave.addEnemy(r.nextInt(3));
            }
            waves.add(wave);
        }
    }

    public void update(){
        if (enemySpawnTick < enemySpawnTickLimit){
            enemySpawnTick++;
        }
        if (waveStartTimer){
            waveTick++;
            if (waveTick>=waveTickLimit){
                waveTickTimerOver = true;
            }
        }
    }

    public boolean isWaveTimerOver() {
        return waveTickTimerOver;
    }

    public void startWaveTimer() {
        waveStartTimer = true;
    }

    public void increaseWaveIndex(){
        waveIndex++;
        waveTick = 0;
        waveTickTimerOver = false;
        waveStartTimer = false;
    }

    public int getNextEnemy(){
        enemySpawnTick = 0;
        return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }

    public List<Wave> getWaves() {
        return waves;
    }

    public boolean isTimeForNewEnemy() {
        return enemySpawnTick >= enemySpawnTickLimit;
    }

    public boolean isThereMoreEnemiesInWave(){
        return enemyIndex < waves.get(waveIndex).getEnemyList().size();
    }

    public boolean isThereMoreWaves() {
        return waveIndex + 1 < waves.size();
    }

    public void resetEnemyIndex() {
        enemyIndex = 0;
    }

    public int getWaveIndex() {
        return waveIndex;
    }

    public float getTimeLeft(){
        float ticksLeft = waveTickLimit - waveTick;
        return ticksLeft /60.0f;
    }

    public boolean isWaveStartTimer() {
        return waveStartTimer;
    }

    public void reset() {
        waves.clear();
        createWaves();
        enemyIndex = 0;
        waveIndex = 0;
        waveStartTimer = false;
        waveTickTimerOver = false;
        waveTick = 0;
        enemySpawnTick = enemySpawnTickLimit;
    }
}
