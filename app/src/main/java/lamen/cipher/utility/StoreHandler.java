package lamen.cipher.utility;

import android.content.Context;
import android.content.SharedPreferences;

import lamen.cipher.R;

/**
 * Created by lamen on 4/17/2016.
 */
public class StoreHandler {

    public final static int[] ways_image = new int[]{
            R.mipmap.cipher_text,
            R.mipmap.cipher_gesture,
            R.mipmap.cipher_link,
            R.mipmap.cipher_scan,
//            R.mipmap.cipher_multi,
            R.mipmap.cipher_doublepassword
    };
    public final static int[] ways_text = new int[]{
            R.string.cipher_text,
            R.string.cipher_gesture,
            R.string.cipher_link,
            R.string.cipher_scan,
//            R.string.cipher_multi,
            R.string.cipher_doublepassword
    };

    SharedPreferences UserData;

    public void setShared(Context context) {
        UserData = context.getSharedPreferences("UserData", context.MODE_PRIVATE);
    }

    public int getDigit() {
        return UserData.getInt("digit", 8);
    }

    public void setDigit(int num) {
        SharedPreferences.Editor editor = UserData.edit();
        editor.putInt("digit", num);
        editor.commit();
    }

    public int getPattern() {
        return UserData.getInt("pattern", Encrypter.all);
    }

    public void setPattern(int num) {
        SharedPreferences.Editor editor = UserData.edit();
        editor.putInt("pattern", num);
        editor.commit();
    }

    public String getDoublePW() {
        return UserData.getString("double_pw", "");
    }

    public void setDoublePW(String pw) {
        SharedPreferences.Editor editor = UserData.edit();
        editor.putString("double_pw", pw);
        editor.commit();
    }

}
