//package android.example.cs496.ui.main.fragment2;
//
//import android.example.cs496.R;
//import android.graphics.Bitmap;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.viewpager.widget.PagerAdapter;
//
//import com.github.chrisbanes.photoview.PhotoView;
//
//import java.util.ArrayList;
//
//public abstract class SubFragment2Adapter extends PagerAdapter {
//    LayoutInflater inflater;
//    String path;
//    Bitmap bitmap;
//
//    public SubFragment2Adapter(LayoutInflater inflater, String path) {
//        this.inflater=inflater;
//    }
//
//
//
//    @Override
//    public Object instantiateItem(ViewGroup viewGroup, int position) {
//        inflater = LayoutInflater.from(viewGroup.getContext());
//        JsonTask jsonTask = new JsonTask("Download", path, inflater);
//        View view = inflater.inflate(R.layout.tab_fragment2_zoominout, viewGroup, false);
//        PhotoView photoView = view.findViewById(R.id.photoView);
//        photoView.setImageBitmap(picArr.get(position));
//
//        viewGroup.addView(view);
//        return view;
//
//    }
//
//    public static Bitmap send()
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View)object);
//    }
//
//    @Override
//    public boolean isViewFromObject(View v, Object obj) {
//        return v == obj;
//    }
//}