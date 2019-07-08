package android.example.cs496.ui.main;

import android.content.Intent;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment2.BitmapClass;
import android.example.cs496.ui.main.fragment2.JsonTask;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

// 이미지 전체화면으로
public class Fragment2SubActivity extends AppCompatActivity {
    ViewPager pager;
    String path;
    private String url = "http://143.248.36.219:8080/";
    ArrayList<BitmapClass> subarray = new ArrayList<BitmapClass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Fragment2SubActivity");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        System.out.println("path is "+path);
        setContentView(R.layout.tab_fragment2_zoominout);
        PhotoView photoView = findViewById(R.id.photoView);
        JsonTask jsonTask = new JsonTask(subarray, "Download", path, photoView);
        jsonTask.execute(url);
    }

    public static void setImage(Bitmap img, PhotoView photoView) {
        photoView.setImageBitmap(img);
    }
}


