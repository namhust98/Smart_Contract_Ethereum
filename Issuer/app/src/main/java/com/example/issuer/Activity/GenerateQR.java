package com.example.issuer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.issuer.R;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateQR extends AppCompatActivity {

    private ImageView qrcode;
    private String userAddress, couponAddress;
    private String dataToGenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generateqr);

        qrcode = findViewById(R.id.qrCode);

        dataToGenerate = getIntent().getStringExtra("qr");

        generateQrCode(dataToGenerate);
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
