/**
 * @author elizabeth.carney
 * @version 1.0.0
 */

package com.lizmcarney.googleglassquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.glass.touchpad.Gesture;

import java.util.Locale;

public class TraditionalQuizActivity extends QuizActivity {
	
    private final Handler mHandler = new Handler();
    private int mSecondsElapsed;
    private TextView mTimer;

    private final Runnable mTick = new Runnable() {
        @Override
        public void run() {
            mSecondsElapsed++;
            updateTimer();
            nextTick();
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
        mSecondsElapsed = 0;
        updateTimer();
        nextTick();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mTick);
    }

    /** Overridden to select ten random questions from the application's resources. */
    @Override
    protected void createQuestions() {
        QuizModel.setQuestions(QuestionAdapter.getRandomizedQuestions(getResources().getInteger(R.integer.num_questions)));
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
                if (QuizModel.areAllQuestionsAnswered()) {
                    endGame();
                }
                break;
            case SWIPE_RIGHT:
            	if(QuizModel.isAnswerCorrect(true)){
            		score();
            	} else {
            		fail();
            	}
                if (QuizModel.areAllQuestionsAnswered()) {
                    endGame();
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
        String timeString = String.format(Locale.US, "%d\uee01%02d", mSecondsElapsed / 60, mSecondsElapsed % 60);
        mTimer.setText(timeString);
    }
    
    private void endGame() {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
        finish();
    }
}
