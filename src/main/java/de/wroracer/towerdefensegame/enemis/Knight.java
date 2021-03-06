package de.wroracer.towerdefensegame.enemis;

import static de.wroracer.towerdefensegame.util.Constants.Enemies.KNIGHT;

import de.wroracer.towerdefensegame.managers.EnemyManager;

public class Knight extends Enemy {
    public Knight(float x, float y, int id, EnemyManager enemyManager) {
        super(x, y, id, KNIGHT, enemyManager);
    }
}
