package android.example.cs496.ui.main;

import android.content.Intent;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment2.BitmapClass;
import android.example.cs496.ui.main.fragment2.JsonTask;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import static android.example.cs496.ui.main.TabFragment2.ArrayListOfEdit;
import static android.example.cs496.ui.main.TabFragment2.adapter;
import static android.example.cs496.ui.main.TabFragment2.bitmapEditArr;
import static android.example.cs496.ui.main.TabFragment2.tab2_context;

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

        ImageView iv = findViewById(R.id.trashcan);
        iv.setOnClickListener(new listener());

        JsonTask jsonTask = new JsonTask(subarray, "Download", path, photoView);
        jsonTask.execute(url);
    }


   class listener implements View.OnClickListener{

       @Override
       public void onClick(View view) {
           JsonTask jsonTask = new JsonTask("Delete", path);
           jsonTask.execute(url);
           Toast.makeText(tab2_context,"Deleted!",Toast.LENGTH_SHORT).show();
           JsonTask task = new JsonTask(ArrayListOfEdit, "LoadAll", adapter, bitmapEditArr, tab2_context);
           task.execute(url);
           finish();
       }
   }


    public static void setImage(Bitmap img, PhotoView photoView) {
        photoView.setImageBitmap(img);
    }
}


