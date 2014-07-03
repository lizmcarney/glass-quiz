/**
 * @author elizabeth.carney
 * @version 1.0.0
 */

package com.lizmcarney.googleglassquiz;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;

import android.os.Bundle;
import android.view.View;

public class TutorialActivity extends QuizActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.status_bar).setVisibility(View.GONE);
    }

    @Override
    protected void createQuestions() {
        QuizModel.setQuestions(QuestionAdapter.getTutorial());
    }

	@Override
    protected void handleGesture(Gesture gesture) {
        int index = QuizModel.getCurrentQuestionIndex();
        switch (gesture) {
         case TAP:
                if (index==2) {
	                 score();
	                 QuizModel.resetModel();
	                 finish();
                } else {
                	playSoundEffect(Sounds.ERROR);
                }
                break;
            case SWIPE_LEFT:
                if (index==1) {
                	score();
                } else {
                    playSoundEffect(Sounds.ERROR);
                }
                break;
            case SWIPE_RIGHT:
                if (index==0) {
                	score();
                } else {
                    playSoundEffect(Sounds.ERROR);
                }
                break;
            default:
            	break;
        }
    }
}
