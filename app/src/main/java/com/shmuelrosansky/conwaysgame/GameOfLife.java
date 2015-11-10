package com.shmuelrosansky.conwaysgame;

import android.graphics.Point;
import android.support.v4.util.Pair;

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

        Point point = new Point();

        boolean[][] newBoard = new boolean[currentBoard.length][currentBoard[0].length];

        for (int i = 0; i < newBoard.length; i++){
            for (int j = 0; j < newBoard.length; j++){
                point.set(i,j);
                switch (getAmtOfNeighbors(point, currentBoard)){
                    case 0:
                    case 1:
                        //die
                        newBoard[i][j] = false;
                        break;
                    case 2:
                        //keep the same value
                        newBoard[i][j] = currentBoard[i][j];
                        break;
                    case 3:
                        // Bring alive (or leave alive if already alive)
                        newBoard[i][j] = true;
                        break;
                    default:
                        newBoard[i][j] = false;
                }
            }
        }

        return newBoard;
    }

    private static int getAmtOfNeighbors(Point point, boolean[][] currentBoard) {

        int x;
        int y;

        int count = 0;

        for( int i = point.x-1; i <= point.x+1; i++){
            for( int j = point.y-1; j <= point.y+1; j++){

                x = i;
                y = j;

                if(x == point.x && y == point.y){
                    continue;
                }

                // Wrap around the board
                if(x == currentBoard.length){
                    x = 0;
                } else if(x == -1){
                    x = currentBoard.length-1;
                }

                if(y == currentBoard.length){
                    y = 0;
                } else if(y == -1){
                    y = currentBoard.length-1;
                }

                if (currentBoard[x][y]){
                    count++;
                }
            }
        }

        return count;
    }

}
