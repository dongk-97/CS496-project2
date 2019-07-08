package android.example.cs496.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment3.ImageAdapter;
import android.example.cs496.ui.main.fragment3.ImageViewHolder;
import android.example.cs496.ui.main.fragment3.Imageclass;
import android.example.cs496.ui.main.fragment3.JsonTask3;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import javax.xml.transform.Result;

public class ClothesSelect extends Activity {

    ArrayList<Imageclass> arrayListOfImage_top = new ArrayList<>();
    ArrayList<Imageclass> arrayListOfImage_pants = new ArrayList<>();
    ArrayList<Imageclass> arrayListOfImage_shoes = new ArrayList<>();
    int pos_top = -1;
    int pos_pants = -1;
    int pos_shoes = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clothes_select);

        Intent intent = getIntent();
        String gender = intent.getStringExtra("gender");
        String style = intent.getStringExtra("style");
        String temp = intent.getStringExtra("temp");
        final String temperature = intent.getStringExtra("temperature");
        final String humidity = intent.getStringExtra("humidity");
        final String wind = intent.getStringExtra("wind");
        final String clouds = intent.getStringExtra("clouds");
        final String precipitation = intent.getStringExtra("precipitation");

        final JsonTask3 task_top = new JsonTask3(arrayListOfImage_top, ClothesSelect.this, getApplicationContext(),R.id.gridView_top, pos_top,"top", gender, style, temp);
        task_top.execute();

        final JsonTask3 task_pants = new JsonTask3(arrayListOfImage_pants, ClothesSelect.this,getApplicationContext(), R.id.gridView_pants ,pos_pants,"pants", gender, style, temp);
        task_pants.execute();

        final JsonTask3 task_shoes = new JsonTask3(arrayListOfImage_shoes, ClothesSelect.this,getApplicationContext(), R.id.gridView_shoes ,pos_shoes,"shoes", gender, style, temp);
        task_shoes.execute();

        Button submit = (Button) findViewById(R.id.button_dialog_result);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(task_top.getPosition());
                if(task_top.getPosition()!=-1 && task_pants.getPosition()!=-1 && task_shoes.getPosition()!=-1){
                    Intent intent = new Intent(ClothesSelect.this, Resultpage.class);
                    intent.putExtra("Top", arrayListOfImage_top.get(task_top.getPosition()).getId());
                    intent.putExtra("Pants", arrayListOfImage_pants.get(task_pants.getPosition()).getId());
                    intent.putExtra("Shoes", arrayListOfImage_shoes.get(task_shoes.getPosition()).getId());
                    intent.putExtra("temperature", temperature);
                    intent.putExtra("humidity", humidity);
                    intent.putExtra("wind", wind);
                    intent.putExtra("clouds", clouds);
                    intent.putExtra("precipitation", precipitation);
                    startActivity(intent);

                }else{

                    Toast.makeText(ClothesSelect.this, "select all items", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}
