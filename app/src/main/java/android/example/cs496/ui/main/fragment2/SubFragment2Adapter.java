package android.example.cs496.ui.main.fragment2;

import android.example.cs496.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;

public class SubFragment2Adapter extends PagerAdapter {
    LayoutInflater inflater;
    final int[] picArr = {R.drawable.add_camera, R.drawable.cat, R.drawable.tree, R.drawable.sunflower, R.drawable.rose, R.drawable.panda,
            R.drawable.heart, R.drawable.google, R.drawable.tiger, R.drawable.dog, R.drawable.chiba3, R.drawable.chiba,
            R.drawable.girl, R.drawable.fruit, R.drawable.beach, R.drawable.bird, R.drawable.chiba2, R.drawable.yun2,
            R.drawable.yun3, R.drawable.yun4, R.drawable.yun5, R.drawable.iu, R.drawable.view, R.drawable.goeun_img1,
            R.drawable.goeun_img2};
    public SubFragment2Adapter(LayoutInflater inflater) {
        this.inflater=inflater;
    }

    @Override
    public int getCount() {
        return picArr.length;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {
        inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.tab_fragment2_zoominout, viewGroup, false);
        PhotoView photoView = view.findViewById(R.id.photoView);
        photoView.setImageResource(picArr[position]);

        viewGroup.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == obj;
    }
}