package de.wroracer.towerdefensegame.enemis;

import de.wroracer.towerdefensegame.managers.EnemyManager;

import static de.wroracer.towerdefensegame.util.Constants.Enemies.WOLF;

public class Wolf extends Enemy{
    public Wolf(float x, float y, int id, EnemyManager enemyManager) {
        super(x, y, id, WOLF,enemyManager);
    }
}
