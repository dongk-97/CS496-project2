package android.example.cs496.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.example.cs496.MainActivity;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment1.phonebook.ProfileActivity;
import android.example.cs496.ui.main.fragment3.JasonTask_result;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Resultpage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultpage);

        Intent intent = getIntent();
        String Top_id = intent.getStringExtra("Top");
        System.out.println(Top_id);
        String Pants_id = intent.getStringExtra("Pants");
        System.out.println(Pants_id);
        String Shoes_id = intent.getStringExtra("Shoes");
        System.out.println(Shoes_id);
        String temperature = intent.getStringExtra("temperature");
        String humidity = intent.getStringExtra("humidity");
        String wind = intent.getStringExtra("wind");
        String clouds = intent.getStringExtra("clouds");
        String precipitation = intent.getStringExtra("precipitation");

        TextView info1 = (TextView) findViewById(R.id.tv_WeatherInfo1);
        TextView info2 = (TextView) findViewById(R.id.tv_WeatherInfo2);
        TextView info3 = (TextView) findViewById(R.id.tv_WeatherInfo3);
        TextView info4 = (TextView) findViewById(R.id.tv_WeatherInfo4);
        TextView info5 = (TextView) findViewById(R.id.tv_WeatherInfo5);
        TextView date = (TextView) findViewById(R.id.TimeDateInfo);
        info1.setText(temperature);
        info2.setText(humidity);
        info3.setText(wind);
        info4.setText(clouds);
        info5.setText(precipitation);
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        date.setText(today);

        JasonTask_result task_top = new JasonTask_result(this, R.id.image_top, Top_id);
        task_top.execute();
        JasonTask_result task_pants = new JasonTask_result(this, R.id.image_pants, Pants_id);
        task_pants.execute();
        JasonTask_result task_shoes = new JasonTask_result(this, R.id.image_shoes, Shoes_id);
        task_shoes.execute();


        TextView share = (TextView) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeScreenshot();


            }
        });

    }
    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + "screenshot" + ".JPEG";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File storageDir =  new File(Environment.getExternalStorageDirectory() + "/Pictures", "tab3");
            if(!storageDir.exists()){
                storageDir.mkdirs();
            }
            File imageFile = new File(storageDir,System.currentTimeMillis()+".jpg");
            Uri contentUri = Uri.fromFile(imageFile);
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
            sendBroadcast(mediaScanIntent);

            Toast.makeText(this,"캡쳐가 저장되었습니다",Toast.LENGTH_SHORT).show();


            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }
    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName(), imageFile);
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}
