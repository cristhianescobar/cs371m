package com.whosupnext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.SystemClock;
import android.view.View;

import java.io.InputStream;


/**
 * Created by escobarcristhian18 on 3/27/14.
 */
public class Animation extends View {


    private Movie mMovie;
    private InputStream mStream;
    private long mMoviestart;

    public Animation(Context context, InputStream in){
        super(context);

        mStream = in;
        mMovie = Movie.decodeStream(mStream);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);

        final long now = SystemClock.uptimeMillis();

        if(mMoviestart == 0){
            mMoviestart = now;
        }

        final int realTime = (int) (now - mMoviestart) % mMovie.duration();

        mMovie.setTime(realTime);
        mMovie.draw(canvas, 10, 10);
        this.invalidate();


    }
}
