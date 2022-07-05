package de.wroracer.towerdefensegame;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import de.wroracer.towerdefensegame.inputs.KeyboardListener;
import de.wroracer.towerdefensegame.inputs.MyMouseListener;

public class GameScreen extends JPanel {

    private Dimension size;
    private Game game;

    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;

    public GameScreen(Game game) {
        this.game = game;
        size = new Dimension(640, 800);
        setPanelSize();
    }

    public void initInputs() {
        myMouseListener = new MyMouseListener(game);
        keyboardListener = new KeyboardListener(game);
        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);

        requestFocus();
    }

    private void setPanelSize() {
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.getRender().render(g);
    }

}
