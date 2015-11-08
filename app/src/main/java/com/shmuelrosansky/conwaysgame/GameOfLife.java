package com.shmuelrosansky.conwaysgame;

/**
 * Created by User on 11/7/2015.
 */
public class GameOfLife {

    public static boolean[][] getNextFrame(boolean[][] currentBoard){
        //// TODO: Implement game logic

        /**
         * Rules:
         *
           Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.
           Any live cell with more than three live neighbours dies, as if by overcrowding.
           Any live cell with two or three live neighbours lives, unchanged, to the next generation.
           Any dead cell with exactly three live neighbours cells will come to life.

         *
         */


        return currentBoard;
    }

}
