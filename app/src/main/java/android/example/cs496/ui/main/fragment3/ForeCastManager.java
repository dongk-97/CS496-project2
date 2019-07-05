package android.example.cs496.ui.main.fragment3;

import android.content.ContentValues;
import android.example.cs496.MainActivity;
import android.example.cs496.ui.main.MainFragment3Activity;
import android.example.cs496.ui.main.TabFragment3;
import android.os.StrictMode;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;


public class ForeCastManager extends Thread{

    String lon,lat;

    ArrayList<ContentValues> mWeather;
    MainFragment3Activity mContext;
    public ArrayList<ContentValues> getmWeather()
    {
        return mWeather;
    }

    public ForeCastManager(Double lon, Double lat, MainFragment3Activity mContext)
    {
        this.lon = lon.toString() ; this.lat = lat.toString();
        this.mContext = mContext;
    }

    public ArrayList<ContentValues> GetOpenWeather(String lon,String lat)
    {
        ArrayList<ContentValues> mTotalValue = new ArrayList<ContentValues>();
        ContentValues mContent = new ContentValues();
        String key = "2c5f2be1208a6ad916814bf49934ed6b";
        try{
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?"+
                    "lat="+lat+
                    "&lon="+lon+
                    "&mode=xml"+
                    "&APPID="+key
            );

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(url.openStream(), null);
            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) { //  문서 시작
                switch (parserEvent) {
                    case XmlPullParser.START_TAG:
                        String startTag = parser.getName();
                        if (startTag.equals("city")){
                            Log.i("currentCity", parser.getAttributeValue(null, "name"));
                            mContent.put("city", parser.getAttributeValue(null, "name"));
                        }
                        else if (startTag.equals("temperature")){
                            mContent.put("temperature_Value", parser.getAttributeValue(null, "value"));
                            mContent.put("temperature_Min", parser.getAttributeValue(null, "min"));
                            mContent.put("temperature_Max", parser.getAttributeValue(null, "max"));
                            mContent.put("temperature_Unit", parser.getAttributeValue(null, "unit"));
                        }
                        else if (startTag.equals("humidity")){
                            mContent.put("humidity_Value", parser.getAttributeValue(null, "value"));
                            mContent.put("humidity_Unit", parser.getAttributeValue(null, "unit"));
                        }
                        else if(startTag.equals("wind")){
                            parserEvent = parser.next();
                            startTag = parser.getName();
                            if (startTag.equals("speed")){
                                mContent.put("wind_Name", parser.getAttributeValue(null, "name"));
                            }
                        }
                        else if(startTag.equals("clouds")){
                            mContent.put("clouds_Name", parser.getAttributeValue(null, "name"));
                        }
                        else if(startTag.equals("precipitation")){
                            mContent.put("precipitation_Mode", parser.getAttributeValue(null, "mode"));
                        }
                        else if(startTag.equals("weather")){
                            mContent.put("weather_Value", parser.getAttributeValue(null, "value"));
                            mContent.put("weather_icon", parser.getAttributeValue(null, "icon"));
                            mContent.put("weather_id", parser.getAttributeValue(null, "number"));
                        }
                        else if (startTag.equals("lastupdate")){
                            mContent.put("last_update", parser.getAttributeValue(null,"value"));
                        }

                    case XmlPullParser.END_TAG:
                        String endTag = parser.getName();
                        if (endTag.equals("current")){
                            mTotalValue.add(mContent);
                        }
                }
                parserEvent = parser.next();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mTotalValue;
    }


    @Override
    public void run() {
        super.run();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mWeather = GetOpenWeather(lon,lat);
        mContext.handler.sendEmptyMessage(mContext.THREAD_HANDLER_SUCCESS_INFO);
        //Thread 작업 종료, UI 작업을 위해 MainHandler에 Message보냄
    }
}