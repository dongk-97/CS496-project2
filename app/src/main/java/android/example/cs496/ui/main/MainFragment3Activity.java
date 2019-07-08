package android.example.cs496.ui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment1.phonebook.Contact;
import android.example.cs496.ui.main.fragment1.phonebook.JsonTask;
import android.example.cs496.ui.main.fragment3.ForeCastManager;
import android.example.cs496.ui.main.fragment3.WeatherInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.health.SystemHealthManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainFragment3Activity extends AppCompatActivity {

    public static final int THREAD_HANDLER_SUCCESS_INFO = 1;
    private static final Location TODO = null;

    TextView tv_CityInfo;
    ImageView tv_WeatherCat;
    TextView tv_WeatherInfo1;
    TextView tv_WeatherInfo2;
    TextView tv_WeatherInfo3;
    TextView tv_WeatherInfo4;
    TextView tv_WeatherInfo5;
    TextView tv_WeatherInfo6;
    TextView tv_LastUpdate;
    TextView textView;
    ImageView tv_Weather;
    ImageView reset;
    TextView timeanddate;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    ForeCastManager mForeCast;
    MainFragment3Activity mThis;
    LocationManager locationManager;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean isGetLocation = false;
    Location location;
    double lat;
    double lon;

    ArrayList<ContentValues> mWeatherData;
    ArrayList<WeatherInfo> mWeatherInformation;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1000;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1분 --> 1시간간

    String gender = "gender";
    String Style = "style";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weatherinfo);
        Intent intent = getIntent();
        String time = intent.getExtras().getString("time");
        textView = findViewById(R.id.TimeDateInfo);
        textView.setText(time);
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new MyListener());
        Initialize();//필요
        TextView today_style = (TextView) findViewById(R.id.select_button);
        today_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainFragment3Activity.this);
                View view = LayoutInflater.from(MainFragment3Activity.this).inflate(R.layout.select_box, null, false);
                builder.setView(view);

                final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_select);
                RadioGroup genderGroup = (RadioGroup) view.findViewById(R.id.radio1);
                final RadioButton man = (RadioButton) view.findViewById(R.id.man);
                final RadioButton woman = (RadioButton) view.findViewById(R.id.woman);


                RadioGroup styleGroup = (RadioGroup) view.findViewById(R.id.radio2);
                final RadioButton street = (RadioButton) view.findViewById(R.id.street);
                final RadioButton dandy = (RadioButton) view.findViewById(R.id.dandy);
                final RadioButton classic = (RadioButton) view.findViewById(R.id.classic);
                final RadioButton set = (RadioButton) view.findViewById(R.id.set);
                final RadioButton sporty = (RadioButton) view.findViewById(R.id.sporty);
                final RadioButton casual = (RadioButton) view.findViewById(R.id.casual);

                genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i){
                            case R.id.man:
                                if(gender.equals("man")){
                                    man.setChecked(false);
                                    gender = "gender";
                                }else{
                                    gender = "man";
                                }
                                break;
                            case R.id.woman:
                                if(gender.equals("woman")){
                                    woman.setChecked(false);
                                    gender = "gender";
                                }else{
                                    gender = "woman";
                                }
                                break;
                        }
                    }
                });

                styleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i){
                            case R.id.street:
                                if(Style.equals("street")){
                                    street.setChecked(false);
                                    Style = "style";
                                }else{
                                    Style = "street";
                                }
                                break;
                            case R.id.casual:
                                if(Style.equals("casual")){
                                    casual.setChecked(false);
                                    Style = "style";
                                }else{
                                    Style = "casual";
                                }
                                break;
                            case R.id.dandy:
                                if(Style.equals("dandy")){
                                    dandy.setChecked(false);
                                    Style = "style";
                                }else{
                                    Style = "dandy";
                                }
                                break;
                            case R.id.set:
                                if(Style.equals("set")){
                                    set.setChecked(false);
                                    Style = "style";
                                }else{
                                    Style = "set";
                                }
                                break;
                            case R.id.classic:
                                if(Style.equals("classic")){
                                    classic.setChecked(false);
                                    Style = "style";
                                }else{
                                    Style = "classic";
                                }
                                break;
                            case R.id.sporty:
                                if(Style.equals("sporty")){
                                    sporty.setChecked(false);
                                    Style = "style";
                                }else{
                                    Style = "sporty";
                                }
                                break;
                        }
                    }
                });

                final AlertDialog dialog = builder.create();

                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!gender.equals("gender")&&!Style.equals("style")){
                            Context context = MainFragment3Activity.this;
                            Toast.makeText(context, "LOADING...", Toast.LENGTH_SHORT).show();

                            String temperature = tv_WeatherInfo1.getText().toString();
                            int length = temperature.length();
                            temperature = temperature.substring(0,length-3);
                            String humidity = tv_WeatherInfo2.getText().toString();
                            String wind = tv_WeatherInfo3.getText().toString();
                            String clouds = tv_WeatherInfo4.getText().toString();
                            String precipitation = tv_WeatherInfo5.getText().toString();
                            String temp = tempSelecter(Float.parseFloat(temperature));
                            Intent intent = new Intent(context, ClothesSelect.class);
                            intent.putExtra("gender", gender);
                            intent.putExtra("style", Style);
                            intent.putExtra("temp", temp);
                            intent.putExtra("temperature", temperature);
                            intent.putExtra("humidity", humidity);
                            intent.putExtra("wind", wind);
                            intent.putExtra("clouds", clouds);
                            intent.putExtra("precipitation", precipitation);
                            startActivity(intent);
                            dialog.dismiss();
                        }else{
                            Toast.makeText(MainFragment3Activity.this, "select both items", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });


    }


    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v){
            Toast.makeText(mThis, "LOADING...", Toast.LENGTH_SHORT).show();
            textView = findViewById(R.id.TimeDateInfo);
            textView.setText(getTime());
            Initialize();
        }
    }

    public void Initialize() {//필요-정보 불러오기

        tv_CityInfo = findViewById(R.id.CityInfo);
        tv_WeatherCat = findViewById(R.id.WeatherCat);
        tv_LastUpdate = findViewById(R.id.LastUpdate);
        tv_WeatherInfo1 = findViewById(R.id.tv_WeatherInfo1);
        tv_WeatherInfo2 = findViewById(R.id.tv_WeatherInfo2);
        tv_WeatherInfo3 = findViewById(R.id.tv_WeatherInfo3);
        tv_WeatherInfo4 = findViewById(R.id.tv_WeatherInfo4);
        tv_WeatherInfo5 = findViewById(R.id.tv_WeatherInfo5);
        tv_WeatherInfo6 = findViewById(R.id.tv_WeatherInfo6);
        tv_Weather = findViewById(R.id.Weather);

        mWeatherInformation = new ArrayList<>();
        mThis = this;
        location = getLocation();
        mForeCast = new ForeCastManager(lon, lat, mThis);
        mForeCast.run();//쓰레드로 데이터 불러옴
    }

    private LocationListener locationListener = new LocationListener() { //gps이용해서 위치 정보 리스너

        @Override
        public void onLocationChanged(Location location) {
            Toast.makeText(mThis, "Location Update", Toast.LENGTH_SHORT).show();
            mForeCast = new ForeCastManager(lon, lat, mThis);
            mForeCast.run();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Toast.makeText(mThis, "Status Changed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String s) {
//            Toast.makeText(mThis, "OnProviderEnabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String s) {
//            Toast.makeText(mThis, "OnProviderDisabled", Toast.LENGTH_SHORT).show();
        }
    };

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mThis.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                Toast.makeText(mThis, "Error: either GPS or Network should be available", Toast.LENGTH_SHORT).show();
            } else {
                this.isGetLocation = true;
                if (isNetworkEnabled) {
                    if (ContextCompat.checkSelfPermission(mThis, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(mThis, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    Activity#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for Activity#requestPermissions for more details.
                        return TODO;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                    locationManager.removeUpdates(locationListener);
                    if (locationManager != null){
                       location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                       if (location != null) {
                           lat = location.getLatitude();
                           lon = location.getLongitude();
                        }
                    }
                }
                if (isGPSEnabled){
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                               MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                        locationManager.removeUpdates(locationListener);
                        if (locationManager != null) {
                           location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                           if (location!=null) {
                               lat = location.getLatitude();
                               lon = location.getLongitude();
                           }
                       }
                   }
               }
           }
       } catch (Exception e) {e.printStackTrace();}
    return location;
    }


    public void AssignValue() {
        tv_CityInfo.setText(mWeatherInformation.get(0).getCity());
        tv_LastUpdate.setText(mWeatherInformation.get(0).getLast_update());
        tv_WeatherInfo1.setText(mWeatherInformation.get(0).getTemperature_Value()  +' '+ mWeatherInformation.get(0).getTemperature_Unit());
        tv_WeatherInfo2.setText(mWeatherInformation.get(0).getHumidity_Value() + mWeatherInformation.get(0).getHumidity_Unit());
        tv_WeatherInfo3.setText(mWeatherInformation.get(0).getWind_Name());
        tv_WeatherInfo4.setText(mWeatherInformation.get(0).getClouds_Name());
        tv_WeatherInfo5.setText(mWeatherInformation.get(0).getPrecipitation_Mode());
        tv_WeatherInfo6.setText(mWeatherInformation.get(0).getWeather_Value());

        // icon 설정해주기
        String icon = mWeatherInformation.get(0).getIcon();
        try {
            URL url = new URL("http://openweathermap.org/img/wn/"+icon+"@2x.png");
            URLConnection conn = url.openConnection();
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();
            tv_Weather.setImageBitmap(bm);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int[] catArr = {R.drawable.cat_coat, R.drawable.cat_cold, R.drawable.cat_flower, R.drawable.cat_hot,
                                R.drawable.cat_padding, R.drawable.cat_rain, R.drawable.cat_sunglass, R.drawable.cat_surprised};

        int cat_id = mWeatherInformation.get(0).getWeather_id();
        tv_WeatherCat.setImageResource(catArr[cat_id]);
    };


    public void DataToInformation()
    {
        for(int i = 0; i < mWeatherData.size(); i++)
        {
            mWeatherInformation.add(new WeatherInfo(
                    String.valueOf(mWeatherData.get(i).get("city")),  String.valueOf(mWeatherData.get(i).get("temperature_Value")), String.valueOf(mWeatherData.get(i).get("temperature_Min")),
                    String.valueOf(mWeatherData.get(i).get("temperature_Max")),  String.valueOf(mWeatherData.get(i).get("temperature_Unit")),  String.valueOf(mWeatherData.get(i).get("humidity_Value")),
                    String.valueOf(mWeatherData.get(i).get("humidity_Unit")),  String.valueOf(mWeatherData.get(i).get("wind_Name")),  String.valueOf(mWeatherData.get(i).get("clouds_Name")),
                    String.valueOf(mWeatherData.get(i).get("precipitation_Mode")),  String.valueOf(mWeatherData.get(i).get("weather_Value")),  String.valueOf(mWeatherData.get(i).get("last_update")),
                    String.valueOf(mWeatherData.get(i).get("weather_icon")), String.valueOf(mWeatherData.get(i).get("weather_id"))
            ));
        }
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case THREAD_HANDLER_SUCCESS_INFO :
                    mWeatherData = mForeCast.getmWeather();
                    if(mWeatherData.size() ==0) {
                        tv_WeatherInfo6.setText("No Data :(");
                    }
                    else {
                        DataToInformation(); // 자료 클래스로 저장,
                        AssignValue();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public String tempSelecter(float temp){
        if(temp<0){
            return "verycold";

        }else if(0<=temp && temp<10){
            return "cold";

        }else if(10<=temp && temp<20){
            return "soso";

        }else if(20<=temp && temp<30){
            return "hot";
        }else{
            return "veryhot";
        }
    }
}


