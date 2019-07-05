package android.example.cs496.ui.main;

import android.content.Context;
import android.content.Intent;
import android.example.cs496.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TabFragment3 extends Fragment {

    ImageView imageView;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.tab_fragment3, container, false);
        imageView = view.findViewById(R.id.loading);
        imageView.setOnClickListener(new MyListener());
        return view;
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Context context = getActivity();
            Toast.makeText(context, "LOADING...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainFragment3Activity.class);;
            SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            intent.putExtra("time", mFormat.format(new Date().getTime()));
            startActivity(intent);
        }
    }
}