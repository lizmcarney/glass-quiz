/**
 * @author elizabeth.carney
 * @version 1.0.0
 */

package com.lizmcarney.googleglassquiz;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public abstract class QuizActivity extends Activity {
    private static final long DELAY_MILLISECONDS = 500;

    private AudioManager mAudioManager;
    private GestureDetector mGestureDetector;
    private boolean mGesturesEnabled;
    private ViewFlipper mQuestionFlipper;
    private TextView mGameState;
    private final Handler mHandler = new Handler();
    private static final char HOLLOW_CIRCLE = '\u25cb';
    private static final char FILLED_CIRCLE = '\u25cf';
    
    private final GestureDetector.BaseListener mBaseListener = new GestureDetector.BaseListener() {
        @Override
        public boolean onGesture(Gesture gesture) {
            if (areGesturesEnabled()) {
                switch (gesture) {
                    case TAP:
                    case SWIPE_LEFT:
                    case SWIPE_RIGHT:
                        handleGesture(gesture);
                        return true;
                    default:
                        return false;
                }
            }
            return false;
        }
    };

    protected abstract void createQuestions();
    protected abstract void handleGesture(Gesture gesture);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);
        setGesturesEnabled(true);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mGestureDetector = new GestureDetector(this).setBaseListener(mBaseListener);
        mQuestionFlipper = (ViewFlipper) findViewById(R.id.phrase_flipper);
        mGameState = (TextView) findViewById(R.id.game_state);
        
        createQuestions();
        updateDisplay();
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector.onMotionEvent(event);
    }
    
    protected void playSoundEffect(int effectType) {
        mAudioManager.playSoundEffect(effectType);
    }

    protected void score() {
        setGesturesEnabled(false);

        QuizModel.markAnswered(true);
        playSoundEffect(Sounds.SUCCESS);

        getCurrentTextView().setTextColor(getResources().getColor(R.color.scored_question));
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!QuizModel.areAllQuestionsAnswered()) {
                    mQuestionFlipper.showNext();
                    updateDisplay();
                    setGesturesEnabled(true);
                }
            }
        }, DELAY_MILLISECONDS);
    }
    
    protected void fail() {
        setGesturesEnabled(false);

        QuizModel.markAnswered(false);
        playSoundEffect(Sounds.ERROR);

        getCurrentTextView().setTextColor(getResources().getColor(R.color.failed_question));
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!QuizModel.areAllQuestionsAnswered()) {
                    mQuestionFlipper.showNext();
                    updateDisplay();
                    setGesturesEnabled(true);
                }
            }
        }, DELAY_MILLISECONDS);
    }

    /** Passes on the current question and advances to the next one. */
    protected void pass() {
        QuizModel.pass();
        playSoundEffect(Sounds.SELECTED);
        mQuestionFlipper.showNext();
        updateDisplay();
    }

    private void updateDisplay() {
        getCurrentTextView().setText(QuizModel.getCurrentQuestion().getText());
        getCurrentTextView().setTextColor(Color.WHITE);
        mGameState.setText(buildScoreBar());
    }
    
	protected CharSequence buildScoreBar() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (int i = 0; i < QuizModel.getNumberOfQuestions(); i++) {
            if (i > 0) {
                builder.append(' ');
            }

            if (i == QuizModel.getCurrentQuestionIndex()) {
                builder.append(HOLLOW_CIRCLE);
                builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.unanswered_question)),
                        builder.length() - 1, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (QuizModel.isQuestionAnswered(i)) {
                builder.append(FILLED_CIRCLE);
            } else {
                builder.append(HOLLOW_CIRCLE);
            }
        }
        return builder;
    }

    private TextView getCurrentTextView() {
        return (TextView) mQuestionFlipper.getCurrentView();
    }

    private boolean areGesturesEnabled() {
        return mGesturesEnabled;
    }

    private void setGesturesEnabled(boolean enabled) {
        mGesturesEnabled = enabled;
    }
}
