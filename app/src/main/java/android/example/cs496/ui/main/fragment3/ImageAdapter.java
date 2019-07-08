package android.example.cs496.ui.main.fragment3;

import android.content.Context;
import android.example.cs496.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Imageclass> arrayList;
    private LayoutInflater mLiInflater ;
    private int mCellLayout;

    public ImageAdapter(Context c, int cellLayout, ArrayList<Imageclass> _arrayList) {
        mContext = c;
        arrayList = _arrayList;
        mLiInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCellLayout = cellLayout;
    }

    public int getCount() {
        return arrayList.size();
    }

    public Imageclass getItem(int position) {
        return arrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mLiInflater.inflate(mCellLayout, parent, false);
            ImageViewHolder holder = new ImageViewHolder();
            holder.setIvImage((ImageView)convertView.findViewById(R.id.loadedImage));
            holder.getIvImage().setImageBitmap(arrayList.get(position).getBitmap());
            //holder.setChkImage((CheckBox) convertView.findViewById(R.id.selectbox));
            convertView.setTag(holder);
        }

        final ImageViewHolder holder = (ImageViewHolder) convertView.getTag();


//        if (arrayList.get(position).getCheck()) {
//            holder.getChkImage().setChecked(true);
//        } else{
//            holder.getChkImage().setChecked(false);
//        }
        return convertView;
    }
}