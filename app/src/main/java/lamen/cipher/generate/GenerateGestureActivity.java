package lamen.cipher.generate;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lamen.cipher.R;

public class GenerateGestureActivity extends GenerateBaseActivity {

    private RelativeLayout getsture_relative;
    private TextView gesture_clear;
    private TextView gesture_input;
    @Override
    public void LOAD_Generate() {
        super.LOAD_Generate();

        type_set(R.string.cipher_gesture);
        view_set(R.layout.generate_gesture);

        getsture_relative = (RelativeLayout)findViewById(R.id.getsture_relative);
        gesture_clear = (TextView)findViewById(R.id.gesture_clear);
        gesture_input = (TextView)findViewById(R.id.gesture_input);
        gesture_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gesture_input.setText("");
            }
        });
        getsture_relative.setOnTouchListener(new View.OnTouchListener() {
            float startX,startY,moveX,moveY;

            ImageView gesture_image = (ImageView)findViewById(R.id.gesture_image);

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = event.getX();
                    startY = event.getY();
                    moveX = 0;
                    moveY = 0;
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            gesture_image.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    moveX = event.getRawX();
                    moveY = event.getRawY();
                    if (moveX - startX > 170) { //left
                        gesture_image.setVisibility(View.VISIBLE);
                        gesture_image.setRotation(90);
                        gesture_input.setText(gesture_input.getText().toString()+"*");
                        input += "left";
                        gesture_image.startAnimation(anim);
                    }
                    else if (startX - moveX > 170) { // right
                        gesture_image.setVisibility(View.VISIBLE);
                        gesture_image.setRotation(270);
                        gesture_input.setText(gesture_input.getText().toString()+"*");
                        input += "right";
                        gesture_image.startAnimation(anim);
                    }
                    else if (moveY - startY > 170) { //down
                        gesture_image.setVisibility(View.VISIBLE);
                        gesture_image.setRotation(180);
                        gesture_input.setText(gesture_input.getText().toString()+"*");
                        input += "down";
                        gesture_image.startAnimation(anim);
                    }
                    else if (startY - moveY > 170) { //up
                        gesture_image.setVisibility(View.VISIBLE);
                        gesture_image.setRotation(0);
                        gesture_input.setText(gesture_input.getText().toString()+"*");
                        input += "up";
                        gesture_image.startAnimation(anim);
                    }

                }
                return false;
            }
        });

    }

}
