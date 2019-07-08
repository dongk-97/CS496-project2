package android.example.cs496.ui.main;

import android.content.Context;
import android.content.Intent;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment1.phonebook.Contact;
import android.example.cs496.ui.main.fragment1.phonebook.JsonTask;
import android.example.cs496.ui.main.fragment1.phonebook.ProfileActivity;
import android.example.cs496.ui.main.fragment2.Tab2Adapter;
import android.example.cs496.ui.main.fragment3.BitmapClass;
import android.example.cs496.ui.main.fragment3.JsonTask3;
import android.example.cs496.ui.main.fragment3.JsonTask3_loadImage;
import android.example.cs496.ui.main.fragment3.multipartRequest;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.telephony.RadioAccessSpecifier;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class TabFragment3 extends Fragment {

    ImageView imageView;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private LayoutInflater inflater;
    private String url = "http://143.248.36.219:8080/";
    public ArrayList<BitmapClass> ArrayListOfEdit = new ArrayList<>();
    String currentPhotoPath;

    File image;
    public View view;
    private String filePath;
    private static final int FROM_ALBUM = 1;
    public static final int PICK_FROM_CAMERA = 2;
    private Uri imgUri, photoURI, albumURI;
    public static final int THREAD_HANDLER_SUCCESS_INFO = 1;
    multipartRequest _multi;
    String gender = "gender";
    String Style = "style";
    String type ="type";
    String temp = "temp";


    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_fragment3, container, false);


        imageView = view.findViewById(R.id.loading);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getActivity();
                Toast.makeText(context, "LOADING...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainFragment3Activity.class);
                SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                intent.putExtra("time", mFormat.format(new Date().getTime()));
                startActivity(intent);
            }

        });

        Button camera  = (Button) view.findViewById(R.id.camera_tab3);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.camera_select_box, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_select);
                RadioGroup genderGroup = (RadioGroup) view.findViewById(R.id.gender);
                final RadioButton man = (RadioButton) view.findViewById(R.id.man);
                final RadioButton woman = (RadioButton) view.findViewById(R.id.woman);

                RadioGroup typeGroup = (RadioGroup) view.findViewById(R.id.type);
                final RadioButton top = (RadioButton) view.findViewById(R.id.top);
                final RadioButton pants = (RadioButton) view.findViewById(R.id.pants);
                final RadioButton shoes = (RadioButton) view.findViewById(R.id.shoes);


                RadioGroup styleGroup = (RadioGroup) view.findViewById(R.id.style);
                final RadioButton street = (RadioButton) view.findViewById(R.id.street);
                final RadioButton dandy = (RadioButton) view.findViewById(R.id.dandy);
                final RadioButton classic = (RadioButton) view.findViewById(R.id.classic);
                final RadioButton sporty = (RadioButton) view.findViewById(R.id.sporty);
                final RadioButton casual = (RadioButton) view.findViewById(R.id.casual);

                RadioGroup tempGroup = (RadioGroup) view.findViewById(R.id.temperature);
                final RadioButton verycold = (RadioButton) view.findViewById(R.id.verycold);
                final RadioButton cold = (RadioButton) view.findViewById(R.id.cold);
                final RadioButton soso = (RadioButton) view.findViewById(R.id.soso);
                final RadioButton hot = (RadioButton) view.findViewById(R.id.hot);
                final RadioButton veryhot = (RadioButton) view.findViewById(R.id.veryhot);


                genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i){
                            case R.id.man:

                                    gender = "man";

                                break;
                            case R.id.woman:

                                    gender = "woman";

                                break;
                        }
                    }
                });

                typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i){
                            case R.id.top:

                                    type = "top";

                                break;
                            case R.id.pants:

                                    type = "pants";

                                break;

                            case R.id.shoes:

                                    type = "shoes";

                                break;


                        }
                    }
                });

                styleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i){
                            case R.id.street:

                                    Style = "street";

                                break;
                            case R.id.casual:

                                    Style = "casual";

                                break;
                            case R.id.dandy:

                                    Style = "dandy";

                                break;
                            case R.id.classic:

                                    Style = "classic";

                                break;
                            case R.id.sporty:

                                    Style = "sporty";

                                break;
                        }
                    }
                });


                tempGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i){
                            case R.id.verycold:

                                    temp = "verycold";

                                break;
                            case R.id.cold:

                                    temp = "cold";

                                break;
                            case R.id.soso:

                                    temp = "soso";

                                break;
                            case R.id.hot:

                                    temp = "hot";

                                break;
                            case R.id.veryhot:

                                    temp = "veryhot";

                                break;
                        }
                    }
                });

                final AlertDialog dialog = builder.create();
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!gender.equals("gender")&&!Style.equals("style")&&!type.equals("type")&&!temp.equals("temp")){
                            String state = Environment.getExternalStorageState();
                            if(Environment.MEDIA_MOUNTED.equals(state)) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
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
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(), "select all items", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
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
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "dongdong");
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
        getActivity().sendBroadcast(mediaScanIntent);

        _multi = new multipartRequest(this, currentPhotoPath);
        _multi.run();

        Toast.makeText(getActivity(),"사진이 저장되었습니다",Toast.LENGTH_SHORT).show();
    }


    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case THREAD_HANDLER_SUCCESS_INFO :
                    System.out.println("SUCCESS");
                    String s = _multi.result();
                    System.out.println(s);
                    try {
                        JSONObject obj = new JSONObject(s);
                        String id = obj.getString("_id");
                        JsonTask3_loadImage task = new JsonTask3_loadImage(id, type, gender,Style, temp);
                        task.execute();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // res.json ---> id / id parsing + 나머지 정보 같이 get으로 전송
                    break;
                default:
                    break;
            }
        }
    };

}