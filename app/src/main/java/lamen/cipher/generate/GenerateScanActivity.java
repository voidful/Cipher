package lamen.cipher.generate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;
import lamen.cipher.R;

public class GenerateScanActivity extends GenerateBaseActivity implements QRCodeView.Delegate {

    private QRCodeView mQRCodeView;
    @Override
    public void LOAD_Generate() {
        super.LOAD_Generate();

        type_set(R.string.cipher_scan);
        view_set(R.layout.generate_scan);

        mQRCodeView = (ZBarView) findViewById(R.id.scan_camera_view);
        mQRCodeView.setDelegate(this);
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
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GenerateScanActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GenerateScanActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
        }
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
