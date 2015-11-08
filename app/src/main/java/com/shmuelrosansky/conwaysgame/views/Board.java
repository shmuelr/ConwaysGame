package com.shmuelrosansky.conwaysgame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by User on 11/7/2015.
 */
public class Board extends View {

    private static final String TAG = Board.class.getSimpleName();
    private boolean[][] board;
    private int amtOfRows;
    private int cellWidth;

    private final Paint paint = new Paint();

    public Board(Context context) {
        super(context);
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Board(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initBoard(int size){
        this.amtOfRows = size;
        board = new boolean[size][size];
        paint.setColor(Color.BLACK);
    }

    public void updateBoard(boolean[][] newBoard){
        this.board = newBoard;
    }

    public boolean[][] getBoard() {
        return board;
    }

    // http://blog.sqisland.com/2012/01/android-square-view.html
    @Override
    public void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");

        cellWidth = getWidth() / amtOfRows;

        // Clear canvas
        canvas.drawColor(Color.WHITE);

        drawLines(canvas);

        drawSquares(canvas);
    }

    private void drawSquares(Canvas canvas) {

        for (int i = 0 ; i < amtOfRows; i++){
            for (int j = 0; j <amtOfRows; j++){

                if(board[i][j]){

                    canvas.drawRect(
                            i * cellWidth,
                            j * cellWidth,
                            (i * cellWidth) + cellWidth,
                            (j * cellWidth) + cellWidth
                            ,paint);
                }
            }
        }

    }

    private void drawLines(Canvas canvas) {
        paint.setColor(Color.LTGRAY);
        for(int i = 1 ; i < amtOfRows; i ++){
            int offset = (i * cellWidth);
            // Draw the vertical lines
            canvas.drawLine(offset, 0f, offset, getHeight(), paint  );

            // Draw the horizontal lines
            canvas.drawLine(0f,offset , getWidth(), offset, paint  );
        }
        paint.setColor(Color.BLACK);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d(TAG, "onTouch X:" + event.getX() + " Y:" + event.getY());

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                int x = (int) (event.getX() / cellWidth);
                int y = (int) (event.getY() / cellWidth);

                board[x][y] = !board[x][y];

                // redraw the board
                invalidate();
                break;
        }

        return super.onTouchEvent(event);
    }


}
