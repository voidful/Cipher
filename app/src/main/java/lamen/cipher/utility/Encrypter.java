package lamen.cipher.utility;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lamen on 4/17/2016.
 * flow
 * raw data = sha
 * sha = int
 * int = pw
 */

public class Encrypter {

    public static final int number = 30;
    public static final int numberletter = 82;
    public static final int all = 97;

    private int range;
    private int digit;

    public String encrypt(String input, int dig, int ran) {
        digit = dig;
        range = ran;
        try {
            return PutLineIntoPW(PWProcess(StringToInt(SHA(("CIPHER" + input).getBytes("UTF-8")))));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String SHA(byte[] input) {
        byte[] process = null;
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            sha.update(input);
            process = sha.digest();

        } catch (NoSuchAlgorithmException e) {
            Log.v("error", e.getMessage());
        }
        return Base64.encodeToString(process, Base64.DEFAULT);
    }

    private int StringToInt(String input) {
        Integer cal = 0;
        for (int times = 0; times < input.length(); times++) {
            int data = Character.codePointAt(input, times);
            cal += data * (times + 1);
        }
        return cal;
    }

    private String PWProcess(int pw) {
        String output = "";
        if (String.valueOf(pw).length() < digit) {
            char[] outputChar = new char[digit];
            char[] inputChar = ("" + pw).toCharArray();
            outputChar = inputChar;
            for (int times = 0; times < outputChar.length; times++) {
                outputChar[times] = inputChar[times % digit];
            }
            pw = Integer.parseInt(String.valueOf(outputChar));
        }
        for (int length = 0; length < digit; length++) {
            output += getStringFormNum(pw % range);
            pw = pw ^ (pw >> 1);
        }
        return output;
    }

    private String PutLineIntoPW(String pw) {
        StringBuilder str = new StringBuilder(pw);
        int idx = str.length() - 4;

        while (idx > 0) {
            str.insert(idx, "_");
            idx = idx - 4;
        }
        return str.toString();
    }

    private String getStringFormNum(int num) {
        switch (num) {
            case 1:
            case 2:
            case 3:
                return "0";

            case 4:
            case 5:
            case 6:
                return "1";

            case 7:
            case 8:
            case 9:
                return "2";
            case 10:
            case 11:
            case 12:
                return "3";

            case 13:
            case 14:
            case 15:
                return "4";

            case 16:
            case 17:
            case 18:
                return "5";

            case 19:
            case 20:
            case 21:
                return "6";

            case 22:
            case 23:
            case 24:
                return "7";

            case 25:
            case 26:
            case 27:
                return "8";

            case 28:
            case 29:
            case 30:
                return "9";

            case 31:
                return "a";

            case 32:
                return "b";

            case 33:
                return "c";

            case 34:
                return "d";

            case 35:
                return "e";

            case 36:
                return "f";

            case 37:
                return "g";

            case 38:
                return "h";

            case 39:
                return "i";

            case 40:
                return "j";

            case 41:
                return "k";

            case 42:
                return "l";

            case 43:
                return "m";

            case 44:
                return "n";

            case 45:
                return "o";

            case 46:
                return "p";

            case 47:
                return "q";

            case 48:
                return "r";

            case 49:
                return "s";

            case 50:
                return "t";

            case 51:
                return "u";

            case 52:
                return "v";

            case 53:
                return "w";

            case 54:
                return "x";

            case 55:
                return "y";

            case 56:
                return "z";

            case 57:
                return "A";

            case 58:
                return "B";

            case 59:
                return "C";

            case 60:
                return "D";

            case 61:
                return "E";

            case 62:
                return "F";

            case 63:
                return "G";

            case 64:
                return "H";

            case 65:
                return "I";

            case 66:
                return "J";

            case 67:
                return "K";

            case 68:
                return "L";

            case 69:
                return "M";

            case 70:
                return "N";

            case 71:
                return "O";

            case 72:
                return "P";

            case 73:
                return "Q";

            case 74:
                return "R";

            case 75:
                return "S";

            case 76:
                return "T";

            case 77:
                return "U";

            case 78:
                return "V";

            case 79:
                return "W";

            case 80:
                return "X";

            case 81:
                return "Y";

            case 82:
                return "Z";

            case 83:
            case 84:
            case 85:
                return "@";

            case 86:
            case 87:
            case 88:
                return "#";

            case 89:
            case 90:
            case 91:
                return "*";

            case 92:
            case 93:
            case 94:
                return "+";

            case 95:
            case 96:
            case 97:
                return "%";
        }
        return "7";
    }
}