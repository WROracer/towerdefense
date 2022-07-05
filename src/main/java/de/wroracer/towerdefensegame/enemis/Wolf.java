package de.wroracer.towerdefensegame.enemis;

import static de.wroracer.towerdefensegame.util.Constants.Enemies.WOLF;

import de.wroracer.towerdefensegame.managers.EnemyManager;

public class Wolf extends Enemy {
    public Wolf(float x, float y, int id, EnemyManager enemyManager) {
        super(x, y, id, WOLF, enemyManager);
    }
}
