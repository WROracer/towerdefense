package de.wroracer.towerdefensegame;

public enum GameStates {
    MENU,
    PLAYING,
    SETTINGS,
    EDIT,
    GAME_OVER;
    public static GameStates gameStates = MENU;

    public static void setGameStates(GameStates gameStates) {
        GameStates.gameStates = gameStates;
    }
}
