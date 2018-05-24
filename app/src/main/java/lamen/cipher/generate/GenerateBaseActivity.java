package lamen.cipher.generate;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lamen.cipher.R;
import lamen.cipher.utility.Encrypter;
import lamen.cipher.utility.StoreHandler;

public class GenerateBaseActivity extends Activity {


    RelativeLayout generate_main;
    RelativeLayout generate_fun_panel_set;

    TextView type_textview;
    TextView fun_digit_btn;
    TextView panel_digit_text;
    TextView pw_generate_btn;
    TextView pw_password_text;

    String input;
    StoreHandler storeHandler;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        Load_find_public_view();
        Load_data_loading();
        Load_typeface_set();

        pw_generate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generate_onClick();
            }
        });

        fun_digit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout bit_relative = (RelativeLayout) findViewById(R.id.panel_digit_relative);
                if (bit_relative.getVisibility() == View.GONE)
                    bit_relative.setVisibility(View.VISIBLE);
                else bit_relative.setVisibility(View.GONE);
            }
        });
        set_digit_fun();

        TextView fun_pattern_btn = (TextView) findViewById(R.id.fun_panel_pattern_textview);
        fun_pattern_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout pattern_relative = (RelativeLayout) findViewById(R.id.pattern_relative);
                if (pattern_relative.getVisibility() == View.GONE)
                    pattern_relative.setVisibility(View.VISIBLE);
                else pattern_relative.setVisibility(View.GONE);
            }
        });
        set_pattern_fun();

        TextView fun_copy_btn = (TextView) findViewById(R.id.fun_panel_copy_textview);
        fun_copy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData textCd = ClipData.newPlainText("password", pw_password_text.getText());
                clipboard.setPrimaryClip(textCd);
                pw_password_text.setText(getString(R.string.copyed));
            }
        });

        TextView fun_info_btn = (TextView) findViewById(R.id.fun_panel_info_textview);
        fun_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_info_dialog();
            }
        });

        LOAD_Generate();

    }

    public void LOAD_Generate() {

    }

    public void type_set(int TypeCode) {
        type_textview.setText(getString(TypeCode));
    }

    public void view_set(int ViewCode) {
        generate_main.addView(LayoutInflater.from(this).inflate(ViewCode, (RelativeLayout) findViewById(R.id.generate_relative), false));

    }

    public void generate_onClick() {
        if (input != null) {
            String text = new Encrypter().encrypt(input + storeHandler.getDoublePW(), storeHandler.getDigit(), storeHandler.getPattern());
            pw_password_text.setText(text);
        } else {
            pw_password_text.setText(getString(R.string.data_input_empty));
        }
    }

    private void Load_find_public_view() {
        //find the public view
        generate_fun_panel_set = (RelativeLayout) findViewById(R.id.generate_fun_panel_set);
        generate_main = (RelativeLayout) findViewById(R.id.generate_main_relative);
        pw_generate_btn = (TextView) findViewById(R.id.pw_panel_generate_textview);
        pw_password_text = (TextView) findViewById(R.id.pw_panel_pw_textview);
        fun_digit_btn = (TextView) findViewById(R.id.fun_panel_digit_textview);
        panel_digit_text = (TextView) findViewById(R.id.panel_digit_text);
    }

    private void Load_data_loading() {
        // load data
        storeHandler = new StoreHandler();
        storeHandler.setShared(getApplicationContext());
        //set the ui data
        panel_digit_text.setText(storeHandler.getDigit() + getString(R.string.digit));
        fun_digit_btn.setText(storeHandler.getDigit() + getString(R.string.digit));
    }

    private void Load_typeface_set() {
        //set text typeface
        Typeface larabi = Typeface.createFromAsset(getAssets(), "fonts/larabiefont.ttf");
        Typeface mplus = Typeface.createFromAsset(getAssets(), "fonts/mplus.ttf");

        LinearLayout pw_panel = (LinearLayout) findViewById(R.id.generate_pw_panel);
        LinearLayout fun_panel = (LinearLayout) findViewById(R.id.generate_fun_panel);
        TextView title_textview = (TextView) findViewById(R.id.generate_title_textview);
        type_textview = (TextView) findViewById(R.id.generate_title_type_textview);

        title_textview.setTypeface(larabi);
        type_textview.setTypeface(larabi);

        for (int i = 0; i < fun_panel.getChildCount(); i++) {
            ((TextView) fun_panel.getChildAt(i)).setTypeface(mplus);
        }
        for (int i = 0; i < pw_panel.getChildCount(); i++) {
            ((TextView) pw_panel.getChildAt(i)).setTypeface(mplus);
        }

    }

    private void set_pattern_fun() {

        TextView set_pattern_num = (TextView) findViewById(R.id.pattern_num);
        set_pattern_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeHandler.setPattern(Encrypter.number);
                RelativeLayout pattern_relative = (RelativeLayout) findViewById(R.id.pattern_relative);
                pattern_relative.setVisibility(View.GONE);
            }
        });
        TextView set_pattern_numletter = (TextView) findViewById(R.id.pattern_numletter);
        set_pattern_numletter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeHandler.setPattern(Encrypter.numberletter);
                RelativeLayout pattern_relative = (RelativeLayout) findViewById(R.id.pattern_relative);
                pattern_relative.setVisibility(View.GONE);
            }
        });
        TextView set_pattern_all = (TextView) findViewById(R.id.pattern_all);
        set_pattern_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeHandler.setPattern(Encrypter.all);
                RelativeLayout pattern_relative = (RelativeLayout) findViewById(R.id.pattern_relative);
                pattern_relative.setVisibility(View.GONE);
            }
        });
    }

    private void set_digit_fun() {

        TextView set_digit_add = (TextView) findViewById(R.id.panel_digit_set_add);
        set_digit_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storeHandler.getDigit() < 16)
                    storeHandler.setDigit(storeHandler.getDigit() + 1);
                panel_digit_text.setText(storeHandler.getDigit() + getString(R.string.digit));
                fun_digit_btn.setText(storeHandler.getDigit() + getString(R.string.digit));
            }
        });
        TextView set_digit_sub = (TextView) findViewById(R.id.panel_digit_set_sub);
        set_digit_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storeHandler.getDigit() >= 1)
                    storeHandler.setDigit(storeHandler.getDigit() - 1);
                panel_digit_text.setText(storeHandler.getDigit() + getString(R.string.digit));
                fun_digit_btn.setText(storeHandler.getDigit() + getString(R.string.digit));
            }
        });
    }

    private void show_info_dialog() {
        Dialog dialog = new Dialog(GenerateBaseActivity.this, R.style.dialog);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.info_layout, (ViewGroup) findViewById(R.id.setting_linear));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = getWindowManager().getDefaultDisplay().getWidth() * 9 / 10;
        lp.height = getWindowManager().getDefaultDisplay().getHeight() * 6 / 10;
        dialog.setContentView(layout);
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
