package android.example.cs496.ui.main;

import android.content.Context;
import android.content.Intent;
import android.example.cs496.ui.main.fragment2.Tab2Adapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.example.cs496.R;

public class TabFragment2 extends Fragment{

    GridView gridView;

    int[] picArr = {R.drawable.add_camera, R.drawable.cat, R.drawable.tree, R.drawable.sunflower, R.drawable.rose, R.drawable.panda,
            R.drawable.heart, R.drawable.google, R.drawable.tiger, R.drawable.dog, R.drawable.chiba3, R.drawable.chiba,
            R.drawable.girl, R.drawable.fruit, R.drawable.beach, R.drawable.bird, R.drawable.chiba2, R.drawable.yun2,
            R.drawable.yun3, R.drawable.yun4, R.drawable.yun5, R.drawable.iu, R.drawable.view, R.drawable.goeun_img1,
            R.drawable.goeun_img2};

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final Context context = getActivity();

        final View view = inflater.inflate(R.layout.tab_fragment2, container, false);
        gridView = view.findViewById(R.id.gridView1);
        gridView.setAdapter(new Tab2Adapter(context, inflater, picArr));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parentView, View clickView, int position, long id){
                // 이미지 터치했을 때 동작하는 곳
                if (position == 0) {
                    Intent intent = new Intent(context, Fragment2Camera.class);
                    intent.putExtra("picArr", picArr);
                    startActivityForResult(intent, 0);
                }
                else {
                    Intent intent = new Intent(context, Fragment2SubActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1){
            System.out.println("got new picArr");
            int[] npicArr = (int[]) data.getSerializableExtra("npicArr");
//            onCreateView(inflater, )
        }

    }


}