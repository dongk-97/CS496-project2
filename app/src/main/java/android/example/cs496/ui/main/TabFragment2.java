package android.example.cs496.ui.main;

import android.content.Context;
import android.content.Intent;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment2.BitmapClass;
import android.example.cs496.ui.main.fragment2.JsonTask;
import android.example.cs496.ui.main.fragment2.Tab2Adapter;
import android.example.cs496.ui.main.fragment2.multipartRequest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class TabFragment2 extends Fragment{

    private String url = "http://143.248.36.219:8080/";
    public ArrayList<BitmapClass> ArrayListOfEdit = new ArrayList<>();
    String currentPhotoPath;
    public Context context;
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

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final Fragment fragment = this;
        context = getActivity();
        view = inflater.inflate(R.layout.tab_fragment2, container, false);
        gridView = view.findViewById(R.id.gridView1);
        //adapter = new Tab2Adapter(context, inflater, ArrayListOfEdit);
//        gridView.setAdapter(adapter);

        BitmapClass bitmapClass = new BitmapClass();
        Bitmap add_camera = BitmapFactory.decodeResource(getResources(), R.drawable.rose);
        bitmapClass.setImg(add_camera);
        bitmapClass.setPath("djksjflsjd");
        ArrayListOfEdit.add(bitmapClass);
//        adapter.notifyDataSetChanged();
        adapter = new Tab2Adapter(context, inflater, ArrayListOfEdit);
        gridView.setAdapter(adapter);


        System.out.println("start loading");
        JsonTask task = new JsonTask(ArrayListOfEdit, "LoadAll", adapter);
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
                        if (intent.resolveActivity(context.getPackageManager()) != null) {
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
                    Intent intent = new Intent(context, Fragment2SubActivity.class);
                    intent.putExtra("path", Tab2Adapter.getpicArr().get(position-1).getPath());
                    startActivity(intent);
                }
            }
        });
        return view;
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
        context.sendBroadcast(mediaScanIntent);

        _multi = new multipartRequest(this, currentPhotoPath);
        _multi.run();

        Toast.makeText(context,"사진이 저장되었습니다",Toast.LENGTH_SHORT).show();
    }

    public static void setnotify(Tab2Adapter adapter){
        adapter.notifyDataSetChanged();
//        gridView.setAdapter(adapter);
    }


    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case THREAD_HANDLER_SUCCESS_INFO :
                    System.out.println("SUCCESS");
                    String result = "["+_multi.result()+"]";
                    System.out.println(result);
                    System.out.println(ArrayListOfEdit.size());
                    JsonTask task = new JsonTask(ArrayListOfEdit, "Upload", result, adapter);
                    task.execute();
                    break;
                default:
                    break;
            }
        }
    };

}