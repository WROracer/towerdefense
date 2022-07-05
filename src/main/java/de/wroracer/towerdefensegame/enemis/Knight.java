package de.wroracer.towerdefensegame.enemis;

import de.wroracer.towerdefensegame.managers.EnemyManager;

import static de.wroracer.towerdefensegame.util.Constants.Enemies.KNIGHT;

public class Knight extends Enemy{
    public Knight(float x, float y, int id, EnemyManager enemyManager) {
        super(x, y, id, KNIGHT,enemyManager);
    }
}
