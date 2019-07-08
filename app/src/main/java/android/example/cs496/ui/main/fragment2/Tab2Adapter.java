package android.example.cs496.ui.main.fragment2;

import android.content.Context;
import android.example.cs496.R;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class Tab2Adapter extends BaseAdapter {
    private static ArrayList<Bitmap> bitmapArr;
    private LayoutInflater inf;
    private Context context;
    GridViewHolder viewHolder;



    public Tab2Adapter(Context context, LayoutInflater inflater, ArrayList<Bitmap> picArr) {
        this.bitmapArr = picArr;
        this.inf = inflater;
        this.context = context;
    }

    @Override
    public int getCount() {return bitmapArr.size();}

    @Override
    public Object getItem(int position) {return bitmapArr.get(position);}

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다.
        if (convertView==null) {
            convertView = inf.inflate(R.layout.griditem, parent, false);
            viewHolder = new GridViewHolder();
            viewHolder.img = convertView.findViewById(R.id.imageView1);
            convertView.setTag(viewHolder);
        }
        // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다.
        else{
//            convertView.setLayoutParams(new GridView.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
            viewHolder = (GridViewHolder) convertView.getTag();
        }

        if (position==0) {
            viewHolder.img.setImageBitmap(bitmapArr.get(position));
        }
        if (position!=0) {viewHolder.img.setImageBitmap(bitmapArr.get(position));}
        return convertView;
    }
}
