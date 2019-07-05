package android.example.cs496.ui.main;

import android.content.Intent;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment2.SubFragment2Adapter;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.github.chrisbanes.photoview.PhotoView;
import static android.app.PendingIntent.getActivity;

public class Fragment2SubActivity extends AppCompatActivity {
    ViewPager pager;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sub_fragment2);
        pager= findViewById(R.id.sub_fragment2_view_pager);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        //ViewPager에 설정할 Adapter 객체 생성
        //ListView에서 사용하는 Adapter와 같은 역할.
        //다만. ViewPager로 스크롤 될 수 있도록 되어 있다는 것이 다름
        //PagerAdapter를 상속받은 CustomAdapter 객체 생성
        //CustomAdapter에게 LayoutInflater 객체 전달

        SubFragment2Adapter adapter= new SubFragment2Adapter(getLayoutInflater());
        //ViewPager에 Adapter 설정
        pager.setAdapter(adapter);
        pager.setCurrentItem(position, true);
//        setContentView(R.layout.tab_fragment2_zoominout);
//        PhotoView photoView = findViewById(R.id.photoView);
//        photoView.setImageResource(picArr[position]);
    }
}
