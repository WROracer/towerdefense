package de.wroracer.towerdefensegame.enemis;

import de.wroracer.towerdefensegame.managers.EnemyManager;

import static de.wroracer.towerdefensegame.util.Constants.Enemies.ORC;

public class Orc extends Enemy{
    public Orc(float x, float y, int id, EnemyManager enemyManager) {
        super(x, y, id, ORC,enemyManager);
    }
}
