package lamen.cipher.generate;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import lamen.cipher.R;

public class GenerateTextActivity extends GenerateBaseActivity {

    @Override
    public void LOAD_Generate() {
        super.LOAD_Generate();

        type_set(R.string.cipher_text);
        view_set(R.layout.generate_text);

        EditText input_text = (EditText)findViewById(R.id.text_input);
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

    }

}
