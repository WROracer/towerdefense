package de.wroracer.towerdefensegame.enemis;

import de.wroracer.towerdefensegame.managers.EnemyManager;

import static de.wroracer.towerdefensegame.util.Constants.Enemies.BAT;

public class Bat extends Enemy {
    public Bat(float x, float y, int id, EnemyManager enemyManager) {
        super(x, y, id, BAT, enemyManager);
    }
}
