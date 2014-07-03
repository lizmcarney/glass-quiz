/**
 * @author elizabeth.carney
 * @version 1.0.0
 */

package com.lizmcarney.googleglassquiz;

import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.glass.touchpad.Gesture;

public class TimedQuizActivity extends QuizActivity {

    private final Handler mHandler = new Handler();
    private int mSecondsRemaining;
    private TextView mTimer;

    private final Runnable mTick = new Runnable() {
        @Override
        public void run() {
            mSecondsRemaining--;
            updateTimer();

            if (mSecondsRemaining <= 0) {
                endGame();
            } else {
                nextTick();
            }
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTimer = (TextView) findViewById(R.id.timer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSecondsRemaining = getResources().getInteger(R.integer.timed_quiz_time_seconds);
        updateTimer();
        nextTick();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mTick);
    }

    @Override
    protected void createQuestions() {
        QuizModel.setQuestions(QuestionAdapter.getRandomizedQuestions());
    }

    @Override
    protected void handleGesture(Gesture gesture) {
        switch (gesture) {
        	case TAP:
        		pass();
        		break;
            case SWIPE_LEFT:
            	if(QuizModel.isAnswerCorrect(false)){
            		score();
            	} else {
            		fail();
            	}
                break;
            case SWIPE_RIGHT:
            	if(QuizModel.isAnswerCorrect(true)){
            		score();
            	} else {
            		fail();
            	}
                break;
		default:
			break;
        }
    }

    private void nextTick() {
        mHandler.postDelayed(mTick, 1000);
    }

    private void updateTimer() {
        String timeString = String.format(Locale.US, "%d\uee01%02d", mSecondsRemaining / 60, mSecondsRemaining % 60);
        mTimer.setText(timeString);
    }

    private void endGame() {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
        finish();
    }

	@Override
	protected CharSequence buildScoreBar() {
		return String.valueOf(QuizModel.getCorrectAnswers()+ "        ");
	}
}
