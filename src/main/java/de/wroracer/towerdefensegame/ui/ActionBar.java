package de.wroracer.towerdefensegame.ui;

import static de.wroracer.towerdefensegame.GameStates.GAME_OVER;
import static de.wroracer.towerdefensegame.GameStates.MENU;
import static de.wroracer.towerdefensegame.GameStates.setGameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import de.wroracer.towerdefensegame.objects.Tower;
import de.wroracer.towerdefensegame.scenes.Playing;
import de.wroracer.towerdefensegame.util.Constants;

public class ActionBar extends Bar {
    private Playing playing;

    private MyButton[] towerButtons;
    private MyButton sellTower, upgradeTower;
    private MyButton bMenu, bpause;

    private Tower selectedTower;
    private Tower displayedTower;

    private DecimalFormat formatter;

    private int gold = 100;
    private boolean showTowerCost;
    private int towerCostType;

    private int lives = 25;

    public ActionBar(int x, int y, int with, int height, Playing playing) {
        super(x, y, with, height);
        this.playing = playing;
        formatter = new DecimalFormat("0.0");
        initButtons();
    }

    public void resetEverything() {
        lives = 25;
        towerCostType = 0;
        showTowerCost = false;
        gold = 100;
        selectedTower = null;
        displayedTower = null;
    }

    private void initButtons() {
        bMenu = new MyButton(2, 642, 100, 30, "Menu");
        bpause = new MyButton(2, 682, 100, 30, "Pause");

        towerButtons = new MyButton[3];
        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w * 1.1f);

        for (int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new MyButton(xStart + xOffset * i, yStart, w, h, "", i);
        }

        sellTower = new MyButton(420, 702, 80, 25, "Sell");
        upgradeTower = new MyButton(545, 702, 80, 25, "Upgrade");
    }

    public void render(Graphics g) {
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, with, height);

        renderButtons(g);
        renderDisplayTower(g);

        g.setFont(new Font("LucidaSans", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        renderWaveInfos(g);

        renderGoldAmount(g);

        renderTowerCost(g);

        if (playing.isGamePaused()) {
            g.setColor(Color.black);
            g.drawString("Game is Paused!", 110, 790);
        }

        g.setColor(Color.black);
        g.drawString("Lives: " + lives, 110, 750);
    }

    private void renderTowerCost(Graphics g) {
        if (showTowerCost) {
            if (isGoldEnoughForTower(towerCostType)) {
                g.setColor(Color.GRAY);
            } else {
                g.setColor(Color.RED);
            }
            g.fillRect(280, 650, 120, 50);
            g.setColor(Color.BLACK);
            g.drawRect(280, 650, 120, 50);

            g.drawString("" + getCostTowerName(towerCostType), 285, 670);
            g.drawString("Cost: " + getTowerCostCost(towerCostType) + "g", 285, 695);
        }
    }

    private int getTowerCostCost(int type) {
        return Constants.Towers.getTowerCost(type);
    }

    private String getCostTowerName(int type) {
        return Constants.Towers.getName(type);
    }

    private void renderGoldAmount(Graphics g) {
        g.drawString("Gold: " + gold, 110, 725);
    }

    private void renderWaveInfos(Graphics g) {
        renderWaveTimerInfo(g);
        renderEnemiesLeftInfo(g);
        renderWavesLeftInfo(g);
    }

    private void renderWavesLeftInfo(Graphics g) {
        int current = playing.getWaveManager().getWaveIndex() + 1;
        int size = playing.getWaveManager().getWaves().size();
        g.drawString("Wave " + current + " / " + size, 425, 770);
    }

    private void renderEnemiesLeftInfo(Graphics g) {
        int remaining = playing.getEnemyManager().getAmountOfAliveEnemies();
        g.drawString("Enemies Left: " + remaining, 425, 790);
    }

    private void renderWaveTimerInfo(Graphics g) {
        if (playing.getWaveManager().isWaveStartTimer()) {
            float timeLeft = playing.getWaveManager().getTimeLeft();
            String formattedText = formatter.format(timeLeft);
            g.drawString("Time Left: " + formattedText, 425, 750);
        }
    }

    private void renderDisplayTower(Graphics g) {
        if (displayedTower != null) {
            g.setColor(Color.GRAY);
            g.fillRect(410, 645, 220, 85);
            g.setColor(Color.BLACK);
            g.drawRect(410, 645, 220, 85);
            g.drawRect(420, 650, 50, 50);
            g.drawImage(getTowerImg(displayedTower.getTowerType()), 420, 650, 50, 50, null);
            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
            g.drawString("" + Constants.Towers.getName(displayedTower.getTowerType()), 480, 660);
            g.drawString("ID: " + displayedTower.getId(), 480, 675);
            g.drawString("Tier: " + displayedTower.getTier(), 560, 660);

            renderDisplayTowerBorder(g);
            renderDisplayTowerRange(g);

            sellTower.render(g);
            renderButtonFeedback(g, sellTower);
            if (displayedTower.getTier() < 3) {
                upgradeTower.render(g);
                renderButtonFeedback(g, upgradeTower);
            }

            if (sellTower.isMouseOver()) {
                g.setColor(Color.RED);
                g.drawString("Sell for " + getSellAmount(displayedTower) + "g", 480, 695);
            } else if (upgradeTower.isMouseOver() && gold >= getUpgradeAmount(selectedTower)) {
                g.setColor(Color.BLUE);
                g.drawString("Upgrade for " + getUpgradeAmount(displayedTower) + "g", 480, 695);
            } else if (upgradeTower.isMouseOver() && gold < getUpgradeAmount(selectedTower)) {
                g.setColor(Color.RED);
                g.drawString(getUpgradeAmount(displayedTower) + "g Gold Needed", 480, 695);
            }

        }
    }

    private int getUpgradeAmount(Tower displayedTower) {
        return (int) (Constants.Towers.getTowerCost(displayedTower.getTowerType()) * 0.3f);
    }

    private int getSellAmount(Tower tower) {
        int towerSell = getTowerCostCost(tower.getTowerType()) / 2;
        int upgradeSell = (tower.getTier() - 1) * getUpgradeAmount(tower) / 2;
        return towerSell + upgradeSell;
    }

    private void renderDisplayTowerRange(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawOval(displayedTower.getX() - (((int) displayedTower.getRange() * 2) / 2) + 16,
                displayedTower.getY() - ((int) (displayedTower.getRange() * 2) / 2) + 16,
                (int) displayedTower.getRange() * 2,
                (int) displayedTower.getRange() * 2);
    }

    private void renderDisplayTowerBorder(Graphics g) {
        g.setColor(Color.CYAN);
        g.drawRect(displayedTower.getX(), displayedTower.getY(), 32, 32);
    }

    public void displayTower(Tower t) {
        this.displayedTower = t;
    }

    private void renderButtons(Graphics g) {
        bMenu.render(g);
        bpause.render(g);
        for (MyButton tb : towerButtons) {
            if (isGoldEnoughForTower(tb.getId())) {
                g.setColor(Color.GRAY);
            } else {
                g.setColor(Color.RED);
            }
            g.fillRect(tb.getX(), tb.getY(), tb.getWidth(), tb.getHeight());
            g.drawImage(getTowerImg(tb.getId()), tb.getX(), tb.getY(), tb.getWidth(), tb.getHeight(), null);

            renderButtonFeedback(g, tb);
        }
    }

    public BufferedImage getTowerImg(int id) {
        return playing.getTowerManager().getTowerImgs()[id];
    }

    private void sellTowerClicked() {
        playing.removeTower(displayedTower);
        gold += getSellAmount(displayedTower);
        displayedTower = null;
    }

    private void upgradeTowerClicked() {
        playing.upgradeTower(displayedTower);
        gold -= getUpgradeAmount(displayedTower);
    }

    private void togglePause() {
        playing.setGamePaused(!playing.isGamePaused());
        if (playing.isGamePaused()) {
            bpause.setText("Unpause");
        } else {
            bpause.setText("Pause");
        }
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            setGameStates(MENU);
        } else if (bpause.getBounds().contains(x, y)) {
            togglePause();
        } else {
            if (displayedTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTowerClicked();
                } else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3
                        && gold >= getUpgradeAmount(selectedTower)) {
                    upgradeTowerClicked();
                }
            }
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    if (isGoldEnoughForTower(b.getId())) {
                        selectedTower = new Tower(0, 0, -1, b.getId());
                        playing.setSelectedTower(selectedTower);
                    }
                }
            }
        }
    }

    private boolean isGoldEnoughForTower(int type) {
        return gold >= Constants.Towers.getTowerCost(type);
    }

    public void mouseMoved(int x, int y) {
        showTowerCost = false;
        bMenu.setMouseOver(bMenu.getBounds().contains(x, y));
        bpause.setMouseOver(bpause.getBounds().contains(x, y));
        sellTower.setMouseOver(sellTower.getBounds().contains(x, y));
        upgradeTower.setMouseOver(upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3);
        for (MyButton b : towerButtons) {
            b.setMouseOver(b.getBounds().contains(x, y));
            if (b.getBounds().contains(x, y)) {
                showTowerCost = true;
                towerCostType = b.getId();
            }
        }
    }

    public void mousePressed(int x, int y) {
        bMenu.setMousePressed(bMenu.getBounds().contains(x, y));
        bpause.setMousePressed(bpause.getBounds().contains(x, y));
        sellTower.setMousePressed(sellTower.getBounds().contains(x, y));
        upgradeTower.setMousePressed(upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3);
        for (MyButton b : towerButtons) {
            b.setMousePressed(b.getBounds().contains(x, y));
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bpause.resetBooleans();
        sellTower.resetBooleans();
        upgradeTower.resetBooleans();
        for (MyButton b : towerButtons) {
            b.resetBooleans();
        }
    }

    public void playForTower(int towerType) {
        this.gold -= Constants.Towers.getTowerCost(towerType);
    }

    public void addGold(int reward) {
        this.gold += reward;
    }

    public void removeOneLive() {
        lives--;
        if (lives <= 0) {
            setGameStates(GAME_OVER);
        }
    }

    public int getLives() {
        return lives;
    }

}
