package com.shmuelrosansky.conwaysgame;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.shmuelrosansky.conwaysgame.views.Board;

public class MainActivity extends AppCompatActivity {

    private Board gameBoard;
    private EditText sizeInput;
    private AsyncTask gameLoop;
    int boardSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpGUI();
    }

    private void setUpGUI() {

        sizeInput = (EditText)findViewById(R.id.boardSizeInput);
        boardSize = Integer.parseInt(sizeInput.getText().toString());

        gameBoard = (Board)findViewById(R.id.board);
        gameBoard.initBoard(boardSize);

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

        findViewById(R.id.changeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newSize = Integer.parseInt(sizeInput.getText().toString());
                if(newSize != boardSize){
                    boardSize = newSize;
                    gameBoard.initBoard(boardSize);
                    gameBoard.invalidate();
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
