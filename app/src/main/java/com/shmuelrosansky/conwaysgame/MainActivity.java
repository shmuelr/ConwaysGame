package com.shmuelrosansky.conwaysgame;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.shmuelrosansky.conwaysgame.views.Board;

public class MainActivity extends AppCompatActivity {

    private Board gameBoard;
    private AsyncTask gameLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpGUI();
    }

    private void setUpGUI() {
        gameBoard = (Board)findViewById(R.id.board);
        gameBoard.initBoard(20);

        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameIsRunning()) {
                    stopGame();
                } else {
                    runGame();
                }
            }
        });
    }

    private boolean gameIsRunning() {
        return gameLoop != null && gameLoop.getStatus() == AsyncTask.Status.RUNNING;
    }

    private void stopGame() {
        gameLoop.cancel(true);
    }

    private void runGame(){
        gameLoop = new AsyncTask<boolean[][], boolean[][], Void>(){

            @Override
            protected Void doInBackground(boolean[][]... params) {

                boolean[][] gameBoard = params[0];

                while(!isCancelled()){
                    gameBoard = GameOfLife.getNextFrame(gameBoard);
                    publishProgress(gameBoard);

                    try {
                        Thread.sleep(750);
                    } catch (InterruptedException ignored) {}

                }


                return null;
            }

            @Override
            protected void onProgressUpdate(boolean[][]... values) {
                super.onProgressUpdate(values);
                gameBoard.updateBoard(values[0]);
                gameBoard.invalidate();
            }
        }.execute(gameBoard.getBoard());
    }


}
