package android.example.cs496.ui.main.fragment1.phonebook;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    public static final String API_URL = "http://143.248.36.219:8080/";
    @GET("adduser")
    Call<ResponseBody> getname(@Query("name") String name, @Query("phoneNumber") String phoneNumber,
                               @Query("email") String email, @Query("address") String address,
                               @Query("birthday") String birthday, @Query("memo") String memo);

    @GET("deleteuser")
    Call<ResponseBody> getname(@Query("id") String id);


    @GET("edituser")
    Call<ResponseBody> getname(@Query("id") String id, @Query("name") String name, @Query("phoneNumber") String phoneNumber,
                               @Query("email") String email, @Query("address") String address,
                               @Query("birthday") String birthday, @Query("memo") String memo);


}