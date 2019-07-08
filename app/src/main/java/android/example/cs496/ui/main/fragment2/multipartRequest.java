package android.example.cs496.ui.main.fragment2;

import android.example.cs496.ui.main.TabFragment2;
import android.os.StrictMode;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class multipartRequest extends Thread{
    TabFragment2 context;
    String currentPhotoPath;
    String result;
    public static final int THREAD_HANDLER_SUCCESS_INFO = 1;
    private String url = "http://143.248.36.219:8080/uploadphoto";
    String mimetype = "image/jpeg";

    public multipartRequest(TabFragment2 context, String currentpathInfo){
        this.currentPhotoPath = currentpathInfo;
        this.context = context;
    }

    public String result() {return result;}

    public String _multipartRequest(String urlTo, String filepath, String filefield, String fileMimeType)  {
        HttpURLConnection conn = null;
        DataOutputStream outputStream = null;
        InputStream inputStream = null;
        String twoHyphens = "--";
        String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
        String lineEnd = "\r\n";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        String[] q = filepath.split("/");
        int idx = q.length - 1;

        try {
            File file = new File(filepath);
            FileInputStream fileInputStream = new FileInputStream(file);

            URL url = new URL(urlTo);
            conn = (HttpURLConnection) url.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            outputStream = new DataOutputStream(conn.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"" + filefield + "\"; filename=\"" + q[idx] + "\"" + lineEnd);
            outputStream.writeBytes("Content-Type: " + fileMimeType + lineEnd);
            outputStream.writeBytes(lineEnd);


            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            fileInputStream.close();
            outputStream.flush();

            int ch;
            inputStream = conn.getInputStream();
            StringBuffer b = new StringBuffer();

            while((ch = inputStream.read()) != -1){
                b.append( (char)ch );
            }
            String s=b.toString();
            Log.e("Test", "result = " + s);

//            inputStream.close();
            outputStream.close();
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @Override
    public void run() {
        super.run();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        result = _multipartRequest(url, currentPhotoPath, "myImage", mimetype);
        context.handler.sendEmptyMessage(context.THREAD_HANDLER_SUCCESS_INFO);
        //Thread 작업 종료, UI 작업을 위해 MainHandler에 Message보냄
    }
}
