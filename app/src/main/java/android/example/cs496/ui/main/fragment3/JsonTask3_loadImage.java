package android.example.cs496.ui.main.fragment3;

import android.app.Activity;
import android.content.Context;
import android.example.cs496.R;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JsonTask3_loadImage extends AsyncTask<String, String, String> {
    private String id;
    private String gender;
    private String style;
    private String type;
    private String temp;

    public JsonTask3_loadImage(String _id, String _type, String _gender, String _style, String _temp){
        id = _id;
        gender = _gender;
        style = _style;
        type = _type;
        temp = _temp;

    }


    @Override
    protected String doInBackground(String... urls) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService_3.API_URL).build();

        ApiService_3 apiService = retrofit.create(ApiService_3.class);

        Call<ResponseBody> load = (Call<ResponseBody>) apiService.getname(id, type,gender,style,temp);
        load.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        return null;
    }

}
