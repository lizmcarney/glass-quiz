/**
 * @author elizabeth.carney
 * @version 1.0.0
 */

package com.lizmcarney.googleglassquiz;

import com.google.android.glass.media.Sounds;
import com.lizmcarney.googleglassquiz.R;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

public class ResultsActivity extends Activity {

    private final Handler mHandler = new Handler();
    private AudioManager mAudioManager;
    private SoundPool mSoundPool;
    private CardScrollView mCardScroller;
    //private int mCardMargin;
    
    /** Listener that displays the options menu when the card scroller is tapped. */
    private final AdapterView.OnItemClickListener mOnClickListener =
            new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            mAudioManager.playSoundEffect(Sounds.TAP);
            openOptionsMenu();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

       // mCardMargin = (int) getResources().getDimension(R.dimen.card_margin);

        mCardScroller = new CardScrollView(this);
        mCardScroller.setHorizontalScrollBarEnabled(true);
        mCardScroller.setAdapter(
                new ResultsAdapter(getLayoutInflater(), getResources()));
        mCardScroller.setOnItemClickListener(mOnClickListener);
        mCardScroller.activate();
        setContentView(mCardScroller);

        // Initialize the sound pool and play the losing or winning sound immediately once it has
        // been loaded.
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 1.0f, 1.0f, 0, 0, 1.0f);
            }
        });
        //int soundResId = model.areAllQuestionsAnswered() ?
        //        R.raw.triumph : R.raw.sad_trombone;
        mSoundPool.load(this, R.raw.triumph, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.new_game:
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    startTraditionalQuiz();
                }
            });
            return true;
        case R.id.new_timed_quiz:
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                	startTimedQuiz();
                }
            });
            return true;
        case R.id.exit:
        	finish();
            return true;  
        default:
            return false;
        }
    }


    private void startTraditionalQuiz() {
    	QuizModel.resetModel();
        startActivity(new Intent(this, TraditionalQuizActivity.class));
        finish();
    }
    
    private void startTimedQuiz() {
    	QuizModel.resetModel();
        startActivity(new Intent(this, TimedQuizActivity.class));
        finish();
    }
}
