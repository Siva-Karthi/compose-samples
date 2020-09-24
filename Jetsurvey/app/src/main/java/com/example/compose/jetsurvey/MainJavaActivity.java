package com.example.compose.jetsurvey;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class MainJavaActivity extends AppCompatActivity {

    ImageView img;

    private Bitmap bitmap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_main);
        img = (ImageView) findViewById(R.id.imageView);
        img.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        abrirCamera();
                    }
                });
    }

    public void abrirCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        InputStream stream = null;

        if (requestCode == 0 && resultCode == RESULT_OK) {
            try {
                if (bitmap != null) {
                    bitmap.recycle();
                }

                stream = getContentResolver().openInputStream(data.getData());

                bitmap = BitmapFactory.decodeStream(stream);

                img.setImageBitmap(resizeImage(this, bitmap, 700, 600));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (stream != null)
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    private static Bitmap resizeImage(Context context, Bitmap bmpOriginal, float newWidth, float newHeight) {

        Bitmap nuevoBmp = null;

        float densityFactor = context.getResources().getDisplayMetrics().density;

        float nuevoW = newWidth * densityFactor;
        float nuevoH = newHeight * densityFactor;

        int w = bmpOriginal.getWidth();
        int h = bmpOriginal.getHeight();

        float scalaW = nuevoW / w;
        float scalaH = nuevoH / h;

        Matrix matrix = new Matrix();
        matrix.postScale(scalaW, scalaH);

        nuevoBmp = Bitmap.createBitmap(bmpOriginal, 0, 0, w, h, matrix, true);

        return nuevoBmp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}