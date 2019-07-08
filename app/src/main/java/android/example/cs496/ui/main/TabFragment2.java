package android.example.cs496.ui.main;

import android.content.Context;
import android.content.Intent;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment2.BitmapClass;
import android.example.cs496.ui.main.fragment2.JsonTask;
import android.example.cs496.ui.main.fragment2.Tab2Adapter;
import android.example.cs496.ui.main.fragment2.multipartRequest;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class TabFragment2 extends Fragment{

    private String url = "http://143.248.36.219:8080/";
    public static ArrayList<BitmapClass> ArrayListOfEdit = new ArrayList<>();
    String currentPhotoPath;
    public static Context tab2_context;
    File image;
    public static View view;
    private String filePath;
    private static final int FROM_ALBUM = 1;
    public static final int PICK_FROM_CAMERA = 2;
    private Uri imgUri, photoURI, albumURI;
    public static final int THREAD_HANDLER_SUCCESS_INFO = 1;
    multipartRequest _multi;
    static Tab2Adapter adapter;
    static GridView gridView;
    public static ArrayList<Bitmap> bitmapEditArr;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final Fragment fragment = this;
        tab2_context = getActivity();
        view = inflater.inflate(R.layout.tab_fragment2, container, false);
        gridView = view.findViewById(R.id.gridView1);
        bitmapEditArr = parseBitmap(ArrayListOfEdit);
        adapter = new Tab2Adapter(tab2_context, inflater, bitmapEditArr);
        gridView.setAdapter(adapter);

        System.out.println("start loading");
        Toast.makeText(tab2_context,"loading...",Toast.LENGTH_SHORT).show();
        JsonTask task = new JsonTask(ArrayListOfEdit, "LoadAll", adapter, bitmapEditArr, tab2_context);
        task.execute(url);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parentView, View clickView, int position, long id){
                // 이미지 터치했을 때 동작하는 곳
                if (position == 0) {
                    String state = Environment.getExternalStorageState();
                    if(Environment.MEDIA_MOUNTED.equals(state)) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(tab2_context.getPackageManager()) != null) {
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (photoFile != null) {
                                Uri providerURI = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName(), photoFile);
                                imgUri = providerURI;
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
                                startActivityForResult(intent, PICK_FROM_CAMERA);
                            }
                        }
                    }

                }
                else {
                    Intent intent = new Intent(tab2_context, Fragment2SubActivity.class);
                    intent.putExtra("path", ArrayListOfEdit.get(position-1).getPath());
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    <def> ArrayList<Bitmap> parseBitmap(ArrayList<BitmapClass> picArr){
        ArrayList<Bitmap> bitmapArrayList = new ArrayList<Bitmap>();

        Bitmap add_camera = getBitmapFromVectorDrawable(tab2_context, R.drawable.add_camera);
        bitmapArrayList.add(add_camera);
        System.out.println("parseBitmap");
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PICK_FROM_CAMERA && resultCode == RESULT_OK) {
            try{
                Log.v("알림", "FROM_CAMERA 처리");
                galleryAddPic();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (requestCode == FROM_ALBUM && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                try {
                    File albumFile = null;
                    albumFile = createImageFile();
                    photoURI = data.getData();
                    albumURI = Uri.fromFile(albumFile);
                    galleryAddPic();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("알림", "앨범에서 가져오기 에러");
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        String imgFileName = System.currentTimeMillis() + ".jpg";
        File imageFile;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");
        if(!storageDir.exists()){
            storageDir.mkdirs();
        }
        imageFile = new File(storageDir,imgFileName);
        currentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;

    }


    private void galleryAddPic(){
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
        tab2_context.sendBroadcast(mediaScanIntent);

        _multi = new multipartRequest(this, currentPhotoPath);
        _multi.run();

        Toast.makeText(tab2_context,"사진이 저장되었습니다",Toast.LENGTH_SHORT).show();
    }

    public static void setnotify(Tab2Adapter adapter){
        adapter.notifyDataSetChanged();
    }


    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case THREAD_HANDLER_SUCCESS_INFO :
                    System.out.println("SUCCESS");
                    String result = _multi.result();
                    System.out.println(result);
                    System.out.println(ArrayListOfEdit.size());
//                    JsonTask task = new JsonTask(ArrayListOfEdit, "Upload", result, adapter, bitmapEditArr);
//                    task.execute();
                    JsonTask task = new JsonTask(ArrayListOfEdit, "LoadAll", adapter, bitmapEditArr, tab2_context);
                    task.execute(url);
                    break;
                default:
                    break;
            }
        }
    };


}