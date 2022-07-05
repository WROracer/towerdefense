package de.wroracer.towerdefensegame.objects;


import de.wroracer.towerdefensegame.util.Constants;

import static  de.wroracer.towerdefensegame.util.Constants.Towers.*;

public class Tower {

    private int x,y,id,towerType,cdTick,damage;
    private float range,coolDown;
    private int tier;

    public Tower(int x,int y,int id,int towerType){
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType =towerType;
        tier = 1;
        setDefaultDamage();
        setDefaultRange();
        setDefaultCoolDown();
    }

    public void upgradeTower(){
        this.tier++;
        switch (towerType){
            case ARCHER:
                damage+=2;
                range+=20;
                coolDown-=5;
                break;
            case CANON:
                damage+=5;
                range+=20;
                coolDown-=15;
                break;
            case WIZARD:
                range+=20;
                coolDown-=10;
                break;
        }
    }

    public void update(){
        cdTick++;
    }

    public void resetCoolDown() {
        cdTick = 0;
    }

    public boolean isCooldownOver() {
        return cdTick>=coolDown;
    }

    private void setDefaultCoolDown() {
        coolDown = getStartCoolDown(towerType);
    }

    private void setDefaultRange() {
        range = getStartRange(towerType);
    }

    private void setDefaultDamage() {
        damage = getStartDamage(towerType);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public int getTowerType() {
        return towerType;
    }

    public float getDamage() {
        return damage;
    }

    public float getRange() {
        return range;
    }

    public float getCoolDown() {
        return coolDown;
    }

    public int getDmg() {
        return damage;
    }

    public int getTier() {
        return tier;
    }
}
