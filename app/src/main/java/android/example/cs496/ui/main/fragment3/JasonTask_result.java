package android.example.cs496.ui.main.fragment3;

import android.app.Activity;
import android.example.cs496.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

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

public class JasonTask_result extends AsyncTask<String, String, String> {

    private Activity mactivity;
    private int layout;
    private String id;

    public JasonTask_result(Activity activity, int _layout, String _id){
        mactivity = activity;
        layout = _layout;
        id = _id;
    }



    @Override
    protected String doInBackground(String... urls) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService_3.API_URL).build();

        ApiService_3 apiService = retrofit.create(ApiService_3.class);

        Call<ResponseBody> load = (Call<ResponseBody>) apiService.getname(id);
        load.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    try {
                        JSONArray array = new JSONArray(response.body().string());
                        JSONObject obj = array.getJSONObject(0);
                        String _img = obj.getString("data");
                        byte[] decodedString = Base64.decode(_img, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        ImageView image = (ImageView) mactivity.findViewById(layout);
                        image.setImageBitmap(decodedByte);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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
}
