package lamen.cipher.generate;

import com.amnix.materiallockview.MaterialLockView;

import java.util.List;

import lamen.cipher.R;

public class GenerateLinkActivity extends GenerateBaseActivity {

    private MaterialLockView link;
    @Override
    public void LOAD_Generate() {
        super.LOAD_Generate();

        type_set(R.string.cipher_link);
        view_set(R.layout.generate_link);

        link = (MaterialLockView)findViewById(R.id.link_custom);
        link.setOnPatternListener(new MaterialLockView.OnPatternListener() {

            public void onPatternStart() {}

            public void onPatternCleared() {}

            public void onPatternCellAdded(List<MaterialLockView.Cell> pattern, String SimplePattern) {}

            public void onPatternDetected(List<MaterialLockView.Cell> pattern, String SimplePattern) {
                input = SimplePattern;
            }

        });
    }

}
