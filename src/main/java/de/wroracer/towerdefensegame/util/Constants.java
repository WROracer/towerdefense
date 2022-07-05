package de.wroracer.towerdefensegame.util;

import java.util.HashMap;

public class Constants {
    public static class Direction{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class Tiles{
        public static final int WATER = 0;
        public static final int GRASS = 1;
        public static final int ROAD = 2;
    }

    public static class Enemies{
        public static final int ORC = 0;
        public static final int BAT = 1;
        public static final int KNIGHT = 2;
        public static final int WOLF = 3;

        public static int getReward(int enemyType) {
            switch (enemyType){
                case ORC: return 5;
                case BAT: return 5;
                case KNIGHT: return 25;
                case WOLF: return 10;
                default: return 0;

            }
        }

        public static final float getSpeed(int enemyType){
            switch (enemyType){
                case ORC: return 0.5f;
                case BAT: return 0.65f;
                case KNIGHT: return 0.3f;
                case WOLF: return 0.75f;
                default: return 0f;

            }
        }

        public static int getStartHealth(int enemyType) {
            switch (enemyType){
                case ORC: return 100;
                case BAT: return 60;
                case KNIGHT: return 250;
                case WOLF: return 85;
                default: return 0;

            }
        }
    }

    public static class Towers{
        public static final int CANON = 0;
        public static final int ARCHER = 1;
        public static final int WIZARD = 2;

        public static final String getName(int type){
            switch (type){
                case CANON: return "Cannon";
                case ARCHER: return "Archer";
                case WIZARD: return "Wizard";
                default:return "Undefined";
            }
        }

        public static final int getTowerCost(int type){
            switch (type){
                case CANON: return 65;
                case ARCHER: return 30;
                case WIZARD: return 45;
                default:return 0;
            }
        }

        public static final int getStartDamage(int type){
            switch (type){
                case CANON: return 15;
                case ARCHER: return 5;
                case WIZARD: return 0;
                default:return 0;
            }
        }

        public static final float getStartRange(int type){
            switch (type){
                case CANON: return 100;
                case ARCHER: return 100;
                case WIZARD: return 100;
                default:return 0;
            }
        }

        public static final float getStartCoolDown(int type){
            switch (type){
                case CANON: return 50;
                case ARCHER: return 25;
                case WIZARD: return 40;
                default:return 0;
            }
        }
    }

    public static class Projectiles {
        public static final int ARROW = 0;
        public static final int BOMB = 2;
        public static final int CHAINS = 1;


        public static final float getSpeed(int type) {
            switch (type) {
                case ARROW: return 8f;
                case BOMB: return 4f;
                case CHAINS: return 6f;
                default:return 0f;
            }
        }
    }
}
