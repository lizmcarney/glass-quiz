/**
 * @author elizabeth.carney
 * @version 1.0.0
 */

package com.lizmcarney.googleglassquiz;

import java.util.Arrays;
import java.util.List;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

public class StartActivity extends Activity {
	
    private final Handler mHandler = new Handler();

    private final GestureDetector.BaseListener mBaseListener = new GestureDetector.BaseListener() {
        @Override
        public boolean onGesture(Gesture gesture) {
            if (gesture == Gesture.TAP) {
                mAudioManager.playSoundEffect(Sounds.TAP);
                openOptionsMenu();
                return true;
            } else {
                return false;
            }
        }
    };

    private AudioManager mAudioManager;

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mGestureDetector = new GestureDetector(this).setBaseListener(mBaseListener);
        
        List<String> allTrueQuestions = Arrays.asList(getResources().getStringArray(R.array.true_questions));
        List<String> allFalseQuestions = Arrays.asList(getResources().getStringArray(R.array.false_questions));
        List<String> tutorialSteps = Arrays.asList(getResources().getStringArray(R.array.tutorial_steps));
        QuestionAdapter.adapt(allTrueQuestions, true);
        QuestionAdapter.adapt(allFalseQuestions, false);
        QuestionAdapter.setTutorial(tutorialSteps);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector.onMotionEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start_game, menu);
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
            case R.id.instructions:
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        startTutorial();
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
        startActivity(new Intent(this, TraditionalQuizActivity.class));
        finish();
    }
    
    private void startTimedQuiz() {
        startActivity(new Intent(this, TimedQuizActivity.class));
        finish();
    }

    private void startTutorial() {
        startActivity(new Intent(this, TutorialActivity.class));
    }
}
