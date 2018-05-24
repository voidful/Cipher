package lamen.cipher.generate;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amnix.materiallockview.MaterialLockView;

import java.util.List;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

import lamen.cipher.R;
import lamen.cipher.utility.Encrypter;
import lamen.cipher.utility.StoreHandler;

public class GenerateActivity extends Activity implements QRCodeView.Delegate {

    private QRCodeView mQRCodeView;
    private MaterialLockView link;
    private EditText input_text;
    private RelativeLayout getsture_relative;
    private TextView gesture_clear;
    private TextView gesture_input;
    private TextView Bit;
    private TextView digit;
    private RelativeLayout generate_main;
    private RelativeLayout generate_fun_panel_set;
    private TextView Generate;
    TextView PassWord;
    private String input;
    StoreHandler storeHandler;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        Bundle bundle = this.getIntent().getExtras();
        generate_fun_panel_set = (RelativeLayout)findViewById(R.id.generate_fun_panel_set);
        generate_main = (RelativeLayout)findViewById(R.id.generate_main_relative);
        LinearLayout pw_panel = (LinearLayout)findViewById(R.id.generate_pw_panel);
        LinearLayout fun_panel = (LinearLayout)findViewById(R.id.generate_fun_panel);
        TextView title_textiview = (TextView)findViewById(R.id.generate_title_textview);
        TextView type_textiview = (TextView)findViewById(R.id.generate_title_type_textview);
        storeHandler = new StoreHandler();
        storeHandler.setShared(getApplicationContext());
        Generate = (TextView)findViewById(R.id.pw_panel_generate_textview);
        PassWord= (TextView)findViewById(R.id.pw_panel_pw_textview);

        Typeface larabi = Typeface.createFromAsset(getAssets(), "fonts/larabiefont.ttf");
        Typeface mplus = Typeface.createFromAsset(getAssets(), "fonts/mplus.ttf");
        title_textiview.setTypeface(larabi);
        type_textiview.setTypeface(larabi);
        type_textiview.setText(getString(bundle.getInt("text")));
        for(int i = 0 ; i < fun_panel.getChildCount();i++){
            ((TextView)fun_panel.getChildAt(i)).setTypeface(mplus);
        }
        for(int i = 0 ; i < pw_panel.getChildCount();i++){
            ((TextView)pw_panel.getChildAt(i)).setTypeface(mplus);
        }

        generate_main.removeAllViews();
        switch(bundle.getInt("id")){
            case R.mipmap.cipher_text:
                generate_main.addView(LayoutInflater.from(this).inflate(R.layout.generate_text, (RelativeLayout) findViewById(R.id.generate_relative),false));
                input_text = (EditText)findViewById(R.id.text_input);
                input_text.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        input = s.toString();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case  R.mipmap.cipher_gesture:
                generate_main.addView(LayoutInflater.from(this).inflate(R.layout.generate_gesture, (RelativeLayout) findViewById(R.id.generate_relative),false));
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
                break;
            case R.mipmap.cipher_link:
                generate_main.addView(LayoutInflater.from(this).inflate(R.layout.generate_link, (RelativeLayout) findViewById(R.id.generate_relative),false));
                link = (MaterialLockView)findViewById(R.id.link_custom);
                link.setOnPatternListener(new MaterialLockView.OnPatternListener() {

                    public void onPatternStart() {}

                    public void onPatternCleared() {}

                    public void onPatternCellAdded(List<MaterialLockView.Cell> pattern, String SimplePattern) {}

                    public void onPatternDetected(List<MaterialLockView.Cell> pattern, String SimplePattern) {
                        input = SimplePattern;
                    }

                });
                break;
            case R.mipmap.cipher_scan:
                generate_main.addView(LayoutInflater.from(this).inflate(R.layout.generate_scan, (RelativeLayout) findViewById(R.id.generate_relative),false));
                mQRCodeView = (ZBarView) findViewById(R.id.scan_camera_view);
                mQRCodeView.setDelegate(this);
                break;
            case R.mipmap.cipher_multi:
                break;
        }

        TextView Info = (TextView)findViewById(R.id.fun_panel_info_textview);
        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(GenerateActivity.this, R.style.dialog);
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.info_layout, (ViewGroup)findViewById(R.id.setting_linear));
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width =  getWindowManager().getDefaultDisplay().getWidth()*9/10;
                lp.height = getWindowManager().getDefaultDisplay().getHeight()*6/10;
                dialog.setContentView(layout);
                dialog.show();
                dialog.getWindow().setAttributes(lp);
            }
        });

        Generate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                generate_onClick();
            }
        });


        Bit = (TextView)findViewById(R.id.fun_panel_digit_textview);
        digit = (TextView)findViewById(R.id.panel_digit_text);
        digit.setText(storeHandler.getDigit()+getString(R.string.digit));
        Bit.setText(storeHandler.getDigit()+getString(R.string.digit));
        Bit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout digit_relative = (RelativeLayout)findViewById(R.id.panel_digit_relative);
                if(digit_relative.getVisibility() == View.GONE) digit_relative.setVisibility(View.VISIBLE);
                else digit_relative.setVisibility(View.GONE);
            }
        });
        TextView Bit_Add = (TextView)findViewById(R.id.panel_digit_set_add);
        Bit_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storeHandler.getDigit() < 16) storeHandler.setDigit(storeHandler.getDigit()+1);
                digit.setText(storeHandler.getDigit()+getString(R.string.digit));
                Bit.setText(storeHandler.getDigit()+getString(R.string.digit));
            }
        });
        TextView Bit_Sub = (TextView)findViewById(R.id.panel_digit_set_sub);
        Bit_Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storeHandler.getDigit() >= 1) storeHandler.setDigit(storeHandler.getDigit()-1);
                digit.setText(storeHandler.getDigit()+getString(R.string.digit));
                Bit.setText(storeHandler.getDigit()+getString(R.string.digit));
            }
        });
        TextView Pattern_text = (TextView)findViewById(R.id.fun_panel_pattern_textview);
        Pattern_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout pattern_relative = (RelativeLayout)findViewById(R.id.pattern_relative);
                if(pattern_relative.getVisibility() == View.GONE) pattern_relative.setVisibility(View.VISIBLE);
                else pattern_relative.setVisibility(View.GONE);
            }
        });
        TextView Pattern_Num = (TextView)findViewById(R.id.pattern_num);
        Pattern_Num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeHandler.setPattern(Encrypter.number);
                RelativeLayout pattern_relative = (RelativeLayout)findViewById(R.id.pattern_relative);
                pattern_relative.setVisibility(View.GONE);
            }
        });
        TextView Pattern_NumLetter = (TextView)findViewById(R.id.pattern_numletter);
        Pattern_NumLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeHandler.setPattern(Encrypter.numberletter);
                RelativeLayout pattern_relative = (RelativeLayout)findViewById(R.id.pattern_relative);
                pattern_relative.setVisibility(View.GONE);
            }
        });
        TextView Pattern_All = (TextView)findViewById(R.id.pattern_all);
        Pattern_All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeHandler.setPattern(Encrypter.all);
                RelativeLayout pattern_relative = (RelativeLayout)findViewById(R.id.pattern_relative);
                pattern_relative.setVisibility(View.GONE);
            }
        });

        TextView Copy = (TextView)findViewById(R.id.fun_panel_copy_textview);
        Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                ClipData textCd = ClipData.newPlainText("password", PassWord.getText());
                clipboard.setPrimaryClip(textCd);
                PassWord.setText(getString(R.string.copyed));
            }
        });
    }

    private void generate_onClick(){
        if(input != null){
            String text = new Encrypter().encrypt(input+storeHandler.getDoublePW(),storeHandler.getDigit(),storeHandler.getPattern());
            PassWord.setText(text);
        }
        else{
            PassWord.setText(getString(R.string.data_input_empty));
        }
    }
    @Override
    public void onScanQRCodeSuccess(String result) {
        mQRCodeView.stopSpot();
        mQRCodeView.hiddenScanRect();
        input = result;

        RelativeLayout scan_success = (RelativeLayout)findViewById(R.id.scan_success_relative);
        scan_success.setVisibility(View.VISIBLE);
        scan_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQRCodeView.startSpot();
                mQRCodeView.showScanRect();
                v.setVisibility(View.GONE);
            }
        });
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        scan_success.startAnimation(anim);

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mQRCodeView != null) {
            mQRCodeView.startCamera();
            mQRCodeView.startSpot();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mQRCodeView != null){
            mQRCodeView.stopSpot();
            mQRCodeView.stopCamera();
        }

    }

}
