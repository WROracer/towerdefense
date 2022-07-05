package de.wroracer.towerdefensegame;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import de.wroracer.towerdefensegame.managers.TileManager;
import de.wroracer.towerdefensegame.scenes.Editing;
import de.wroracer.towerdefensegame.scenes.GameOver;
import de.wroracer.towerdefensegame.scenes.Menu;
import de.wroracer.towerdefensegame.scenes.Playing;
import de.wroracer.towerdefensegame.scenes.Settings;
import de.wroracer.towerdefensegame.util.LoadSave;

public class Game extends JFrame implements Runnable {

    public static void main(String[] args) {
        LoadSave.createLevelFile();
        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();
    }

    private static final double FPS_SET = 120;
    private static final int UPS_SET = 60;

    private GameScreen gameScreen;

    private Thread gameThread;

    //Classes
    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;
    private Editing editing;
    private GameOver gameOver;

    private TileManager tileManager;

    public Game() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Tower Defense");

        initClasses();
        add(gameScreen);
        createDefaultLevel();

        openInMiddleOfScreen();
        pack();
        setVisible(true);
    }

    private void openInMiddleOfScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension game = gameScreen.getMaximumSize();
        int x = (int) (screen.getWidth() / 2);
        int y = (int) (screen.getHeight() / 2);
        x -= game.getWidth() / 2;
        y -= game.getHeight() / 2;

        setLocation(x, y);
    }

    private void createDefaultLevel() {
        int[] arr = new int[400];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
        LoadSave.createLevel(arr);

    }

    private void initClasses() {
        tileManager = new TileManager();
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
        render = new Render(this);
        editing = new Editing(this);
        gameOver = new GameOver(this);

        gameScreen = new GameScreen(this);
    }

    public void updateGame() {
        switch (GameStates.gameStates) {
            case MENU -> menu.update();
            case PLAYING -> playing.update();
            case SETTINGS -> settings.update();
            case EDIT -> editing.update();
        }
    }

    private void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();

        double timePerUpdate = 1000000000.0 / UPS_SET;
        long lastUpdate = System.nanoTime();

        int updates = 0;
        int frames = 0;
        long lastTimeCheck = System.currentTimeMillis();

        long now;

        //checking fps and ups

        while (true) {

            now = System.nanoTime();

            //Render
            if (now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            } else {
                //Wo do nothing
            }

            //Update
            if (now - lastUpdate >= timePerUpdate) {
                updateGame();
                lastUpdate = now;
                updates++;
            }

            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                lastTimeCheck = System.currentTimeMillis();
                updates = 0;
                frames = 0;
            }

        }
    }

    //Getters and Setters
    public Render getRender() {
        return render;
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Settings getSettings() {
        return settings;
    }

    public Editing getEditing() {
        return editing;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public GameOver getGameOver() {
        return gameOver;
    }
}
