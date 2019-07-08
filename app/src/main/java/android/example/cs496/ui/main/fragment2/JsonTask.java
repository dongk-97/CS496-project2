package android.example.cs496.ui.main.fragment2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.widget.GridView;

import com.github.chrisbanes.photoview.PhotoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.example.cs496.ui.main.fragment2.ApiService.API_URL;

public class JsonTask extends AsyncTask<String, String, String> {

    public ArrayList<BitmapClass> ArrayListOfPhoto;
    public ArrayList<BitmapClass> subarray;
    private String mstate;
    private String path;
    GridView gridView;
    LayoutInflater inflater;
    Context context;
    PhotoView photoView;
    String result;
    Tab2Adapter adapter;

    //loadall
    public JsonTask(ArrayList<BitmapClass> ArrayListOfPhoto, String state, Tab2Adapter _adapter){
        System.out.println("In JSONTask");
        this.ArrayListOfPhoto = ArrayListOfPhoto;
        this.mstate = state;
        this.adapter = _adapter;
    }

    public JsonTask(ArrayList<BitmapClass> subarray, String state, String path, PhotoView photoView){
        this.subarray = subarray;
        this.mstate = state;
        this.path = path;
        this.photoView = photoView;
        System.out.println("right JsonTask");
    }

    //update
    public JsonTask(ArrayList<BitmapClass> ArrayListOfPhoto, String mstate, String result, Tab2Adapter adapter){
        this.mstate = mstate;
        this.result = result;
        this.ArrayListOfPhoto = ArrayListOfPhoto;
        this.adapter = adapter;
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = null;
        switch (mstate){
            case "LoadAll":
                Retrofit retrofit_loadall = new Retrofit.Builder().baseUrl(API_URL).build();
                ApiService apiService_loadall = retrofit_loadall.create(ApiService.class);
                Call<ResponseBody> downloadallphoto = (Call<ResponseBody>) apiService_loadall.getpath();
                downloadallphoto.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            ArrayListOfPhoto.clear();
                            ArrayListOfPhoto = jsontophototoArray(response.body().string(), ArrayListOfPhoto);
                            adapter.notifyDataSetChanged();
                            //TabFragment2.setnotify(adapter);
                            System.out.println("arraylist length is "+ArrayListOfPhoto.size());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;

            case "Upload":
                try {
                    JSONArray array = new JSONArray(result);
                    JSONObject obj = array.getJSONObject(0);
                    BitmapClass bitmapClass = photocreater(obj);
                    ArrayListOfPhoto.add(bitmapClass);
                    System.out.println("arraylist length is "+ArrayListOfPhoto.size());
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case "Download":
                Retrofit retrofit_download = new Retrofit.Builder().baseUrl(API_URL).build();
                ApiService apiService_download= retrofit_download.create(ApiService.class);
                Call<ResponseBody> download = (Call<ResponseBody>) apiService_download.getname(path);
                download.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            System.out.println("hi");
                            subarray.clear();
                            subarray = jsontophototoArray(response.body().string(), subarray);
                            System.out.println("setImageBitmap");
                            ArrayListOfPhoto.remove(subarray.get(0));
                            photoView.setImageBitmap(subarray.get(0).getImg());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;

            case "Delete":
                Retrofit retrofit_delete = new Retrofit.Builder().baseUrl(API_URL).build();
                ApiService apiService_delete = retrofit_delete.create(ApiService.class);
                // 나중에 path 수정하기
                Call<ResponseBody> delete = (Call<ResponseBody>) apiService_delete.getpath("upload\\myImage-1562399223804");
                delete.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        System.out.println("deletion");
//                            System.out.println(response.body().string());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;
        }
        return null;
    }

    public ArrayList<BitmapClass> jsontophototoArray(String jsonstring, ArrayList<BitmapClass> arrayListOfPhotos){
        try {
            JSONArray array = new JSONArray(jsonstring);
            for (int i = 0; i<array.length(); i++){
                JSONObject jObject = array.getJSONObject(i);
                arrayListOfPhotos.add(photocreater(jObject));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return arrayListOfPhotos;
    }

    public BitmapClass photocreater (JSONObject jObject) throws JSONException {
        BitmapClass bitmapClass = new BitmapClass();
        ArrayList<String> keys = new ArrayList<>();
        String temp;
        Bitmap img;
        Iterator i = jObject.keys();
        while (i.hasNext()){
            temp = i.next().toString();
            keys.add(temp);
        }
        for (int j = 0; j<keys.size(); j++) {
            bitmapClass.setId(jObject.getString("_id"));
            bitmapClass.setType(jObject.getString("type"));
            bitmapClass.setPath(jObject.getString("path"));
            String _img = jObject.getString("data");
            byte[] decodedString = Base64.decode(_img, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            bitmapClass.setImg(decodedByte);
        }
    return bitmapClass;
    }

}
