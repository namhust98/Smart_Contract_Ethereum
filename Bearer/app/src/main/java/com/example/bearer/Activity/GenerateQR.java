package com.example.bearer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bearer.R;
import com.example.bearer.Web3jEthereum.Roles.Bearer;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateQR extends AppCompatActivity {

    private ImageView qrcode;
    private String userAddress, couponAddress;
    private String dataToGenerate;
    private LinearLayout view, progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generateqr);

        String campaign_address = getIntent().getStringExtra("address");

        qrcode = findViewById(R.id.qrCode);
        view = findViewById(R.id.activity_generateqr_view);
        progress = findViewById(R.id.activity_generateqr_progress);

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Bearer br = new Bearer("fdd865dc00505e217a4327c1f9a7ec94dc705a85da85a702ea4af93ac0acdcad");
                try {
                    dataToGenerate = br.generateQRToConfirmUsingCoupon(campaign_address);
                } catch (Exception e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                generateQrCode(dataToGenerate);
                view.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }
        }.execute();
    }

    private void generateQrCode(String dataToGenerate) {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;

        QRGEncoder qrgEncoder = new QRGEncoder(dataToGenerate, null, QRGContents.Type.TEXT, smallerDimension);
        try {
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            qrcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
        }
    }
}
