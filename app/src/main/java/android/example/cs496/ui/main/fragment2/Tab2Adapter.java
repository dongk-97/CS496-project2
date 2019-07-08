package android.example.cs496.ui.main.fragment2;

import android.content.Context;
import android.example.cs496.R;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.ArrayList;

public class Tab2Adapter extends BaseAdapter {
    private static ArrayList<BitmapClass> picArr;
    private LayoutInflater inf;
    private Context context;
    GridViewHolder viewHolder;
    public static ArrayList<Bitmap> bitmapArr;


    public Tab2Adapter(Context context, LayoutInflater inflater, ArrayList<BitmapClass> picArr) {
        this.picArr = picArr;
        this.inf = inflater;
        this.context = context;
        bitmapArr = parseBitmap(picArr);
    }

    <def> ArrayList<Bitmap> parseBitmap(ArrayList<BitmapClass> picArr){
        ArrayList<Bitmap> bitmapArrayList = new ArrayList<Bitmap>();

        Bitmap add_camera = getBitmapFromVectorDrawable(context, R.drawable.add_camera);
        bitmapArrayList.add(add_camera);

        for (int j = 0; j<picArr.size(); j++){
            bitmapArrayList.add(picArr.get(j).getImg());
        }

        return bitmapArrayList;
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static ArrayList<Bitmap> getBitmapArr() {return bitmapArr;}

    public static ArrayList<BitmapClass> getpicArr() {return picArr;}

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
//            int colorPrimaryLight = context.getResources().getColor(R.color.colorPrimaryLight);
//            OvalShape ovalShape = new OvalShape();
//            ovalShape.resize(30, 30);
//            ShapeDrawable bgShape = new ShapeDrawable(ovalShape);
//            viewHolder.img.setBackground(bgShape);
//            bgShape.setTint(colorPrimaryLight);
//            viewHolder.img.setClipToOutline(true);
//            viewHolder.img.setScaleType(ImageView.ScaleType.CENTER);
        }
        if (position!=0) {viewHolder.img.setImageBitmap(bitmapArr.get(position));}
        return convertView;
    }
}
