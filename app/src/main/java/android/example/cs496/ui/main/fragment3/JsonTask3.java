package android.example.cs496.ui.main.fragment3;

import android.app.Activity;
import android.content.Context;
import android.example.cs496.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JsonTask3 extends AsyncTask<String, String, String>{
    private ArrayList<Imageclass> arrayListOfImage;
    private String gender;
    private String style;
    private String type;
    private String temp;
    private Activity mactivity;
    private int pos;
    private int layout;
    private Context mContext;

    public JsonTask3(ArrayList<Imageclass> _arrayListOfImage, Activity activity, Context context, int _layout, int _pos, String _type, String _gender, String _style, String _temp){
        arrayListOfImage = _arrayListOfImage;
        gender = _gender;
        style = _style;
        type = _type;
        temp = _temp;
        mactivity = activity;
        pos = _pos;
        layout=_layout;
        mContext = context;
    }


    @Override
    protected String doInBackground(String... urls) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService_3.API_URL).build();

        ApiService_3 apiService = retrofit.create(ApiService_3.class);

        Call<ResponseBody> load = (Call<ResponseBody>) apiService.getname(type,gender,style,temp);
        load.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println("onResponse");
                    jsontoImagetoArray(response.body().string(), arrayListOfImage);
                    GridView gridView_top = (GridView) mactivity.findViewById(layout);
                    final ImageAdapter adapter = new ImageAdapter(mContext,R.layout.griditem_checkbox, arrayListOfImage);
                    gridView_top.setAdapter(adapter);
                    gridView_top.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            System.out.println("onItemClick");
//                            if(pos != -1) {
//                                if (pos == i) {
//                                    arrayListOfImage.get(pos).setCheck(false);
//                                    pos=-1;
//                                } else {
//                                    arrayListOfImage.get(pos).setCheck(false);
//                                    arrayListOfImage.get(i).setCheck(true);
//                                    pos=i;
//                                }
//                            }else{
                                arrayListOfImage.get(i).setCheck(true);
                                pos = i;
                                System.out.println("click!!!!!");

                            view.setBackgroundResource(R.drawable.background_rounding);
                            view.setClipToOutline(true);
//                            }
                            adapter.notifyDataSetChanged();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        return null;
    }


    public void jsontoImagetoArray(String jsonstring, ArrayList<Imageclass> arrayListOfImage){

        try {
            JSONArray array = new JSONArray(jsonstring);
            for(int i = 0; i<array.length(); i++){
                JSONObject jObject = array.getJSONObject(i);
                arrayListOfImage.add(Imagecreater(jObject));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Imageclass Imagecreater(JSONObject jObject){
        Imageclass imageclass = new Imageclass();
        try {
            imageclass.setId(jObject.getString("_id"));

            String _img = jObject.getString("data");

            byte[] decodedString = Base64.decode(_img, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageclass.setBitmap(decodedByte);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return imageclass;
    }

    public int getPosition(){
        return pos;
    }
}
