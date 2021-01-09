package com.bytesmyth.lifegame;

import com.bytesmyth.application.GameContext;
import com.bytesmyth.application.GameWindow;

public class Main {

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
        GameContext context = gameWindow.init();

        LifeGame lifeGame = new LifeGame(context);
        gameWindow.run(lifeGame);
    }

}
