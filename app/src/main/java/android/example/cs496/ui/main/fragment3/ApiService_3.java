package android.example.cs496.ui.main.fragment3;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService_3 {

    public static final String API_URL = "http://143.248.36.219:8080/";
    @GET("loadClothes")
    Call<ResponseBody> getname(@Query("type") String type, @Query("gender") String gender,
                               @Query("style") String style, @Query("temperature") String temp);

    @GET("loadwithId")
    Call<ResponseBody> getname(@Query("id") String id);

    @GET("uploadClothesInfo")
    Call<ResponseBody> getname(@Query("id") String id, @Query("type") String type,@Query("gender") String gender,@Query("style") String style, @Query("temperature") String temperature);



}