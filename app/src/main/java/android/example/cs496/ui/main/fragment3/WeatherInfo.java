package android.example.cs496.ui.main.fragment3;

import java.net.PasswordAuthentication;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class WeatherInfo {
    String city;
    String temperature_Value;
    String temperature_Min;
    String temperature_Max;
    String temperature_Unit;
    String humidity_Value;
    String humidity_Unit;
    String wind_Name;
    String clouds_Name;
    String precipitation_Mode;
    String weather_Value;
    String last_update;
    String icon;
    String weather_id;

    public WeatherInfo(String city, String temperature_Value, String temperature_Min, String temperature_Max,
                       String temperature_Unit, String humidity_Value, String humidity_Unit, String wind_Name,
                       String clouds_Name, String precipitation_Mode, String weather_Value, String last_update,
                       String icon, String id)
    {
        this.city = city;
        this.temperature_Value = temperature_Value;
        this.temperature_Max = temperature_Max;
        this.temperature_Min = temperature_Min;
        this.temperature_Unit = temperature_Unit;
        this.humidity_Value = humidity_Value;
        this.humidity_Unit = humidity_Unit;
        this.wind_Name = wind_Name;
        this.clouds_Name = clouds_Name;
        this.precipitation_Mode = precipitation_Mode;
        this.weather_Value = weather_Value;
        this.last_update = last_update;
        this.icon = icon;
        this.weather_id = id;
    }

    public String getCity() {return city;}

    public String getTemperature_Value() {
        float temp = Float.parseFloat(temperature_Value);
        temp = temp - 273.15f;
        temp = Math.round((temp*100)/100.0);
        return Float.toString(temp);
    }

    public Float getTemperature_Int() {
        float temp = Float.parseFloat(temperature_Min);
        temp = temp - 273.15f;
        temp = Math.round((temp*100)/100.0);
        return temp;
    }

    public Integer getWeather_id(){
        int cat_id = 2;
        float temp = getTemperature_Int();
        int _id = Integer.parseInt(weather_id);
        if (_id == 210 || _id == 211 || _id == 212 || _id == 221) {cat_id = 7;}
        else if (_id < 600) {cat_id = 5;}
        else if(_id < 700) {cat_id = 4;}
        else if(_id < 800 || _id==803 || _id==804) {cat_id = 0;}
        else if(_id < 900) {
            if (temp > 26) cat_id = 6;
            else if(temp < 15) cat_id = 1;
            else cat_id = 2;
        }
        return cat_id;
    }

    public String getIcon(){return icon;}

    public String getTemperature_Unit() {return "'C";}

    public String getHumidity_Value() {return humidity_Value;}

    public String getHumidity_Unit() {return humidity_Unit;}

    public String getWind_Name() {return wind_Name;}

    public String getClouds_Name() {return clouds_Name;}

    public String getPrecipitation_Mode() {return precipitation_Mode;}

    public String getWeather_Value() {return weather_Value;}

    public String getLast_update() {
        return formattedDate(last_update, "yyyy-MM-dd'T'HH:mm:ss", "yyyy.MM.dd HH:mm:ss");
    }

    public static String formattedDate(String date, String fromFormatString, String toFormatString){
        SimpleDateFormat fromFormat = new SimpleDateFormat(fromFormatString);
        SimpleDateFormat toFormat = new SimpleDateFormat(toFormatString);
        Date fromDate = null;

        try {
            fromFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            fromDate = fromFormat.parse(date);
        }catch (ParseException e){
            fromDate = new Date();
        }

        return toFormat.format(fromDate);
    }
}
