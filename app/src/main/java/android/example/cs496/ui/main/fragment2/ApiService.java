package android.example.cs496.ui.main.fragment2;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    public static final String API_URL = "http://143.248.36.219:8080/";

    @GET("downloadallphoto")
    Call<ResponseBody> getpath();

    @GET("deletephoto")
    Call<ResponseBody> getpath(@Query("path") String path);

//    @Multipart
//    @POST("uploadphoto")
//    Call<ResponseBody> upload(
//            @Part("description")RequestBody description,
//            @Part MultipartBody.Part file
//            );
    @GET("testupload")
    Call<ResponseBody> getdata (@Query("data")String data);


    @GET("downloadphoto")
    Call<ResponseBody> getname(@Query("path") String path);
}
