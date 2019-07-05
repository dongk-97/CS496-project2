package android.example.cs496.ui.main.fragment1.phonebook;

import android.app.Activity;
import android.content.Context;
import android.example.cs496.R;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JsonTask extends AsyncTask<String, String, String> {
    public ArrayList<Contact> ArrayListOfcontact;
    private String mstate;
    private String mid;
    private Contact mcontact;
    public Contact loadcontact;
    private Activity mactivity;
    private Context mcontext;
    private Fragment mfragment;
    private View v;
    private String url2 = "http://143.248.36.219:8080/";
    private int Position;
    private ArrayList<Contact> clone = new ArrayList<>();
    public RecyclerViewDataAdapter adapter;




    //LoadAll constructor
    public JsonTask(ArrayList<Contact> ArrayListOfContact, String state, Activity activity, Context context, Fragment fragment, View view){
        this.ArrayListOfcontact = ArrayListOfContact;
        mstate = state;
        mactivity = activity;
        mcontext = context;
        mfragment = fragment;
        v=view;
        this.adapter = null;


    }
    //Add, Edit constructor
    public JsonTask(ArrayList<Contact> ArrayListOfContact, String state, Contact contact, Activity activity, Context context, Fragment fragment, View view, int position){
        this.ArrayListOfcontact = ArrayListOfContact;
        mstate = state;
        mcontact = contact;
        mactivity = activity;
        mcontext = context;
        mfragment = fragment;
        v=view;
        Position = position;
        this.adapter = null;
    }
    //Delete constructor
    public JsonTask(ArrayList<Contact> ArrayListOfContact, String state, Activity activity, Context context, Fragment fragment, View view, String id, int position){
        this.ArrayListOfcontact = ArrayListOfContact;
        mstate = state;
        mid = id;
        this.loadcontact = null;
        Position = position;
        mactivity = activity;
        mcontext = context;
        mfragment = fragment;
        v=view;
        this.adapter = null;
    }
    //ProfileEdit constructor
    public JsonTask(String state, Contact contact){
        mstate = state;
        mcontact = contact;
    }



    @Override
    protected String doInBackground(String... urls) {
        String result =null;
        switch (mstate) {
            case "ProfileEdit":
                Retrofit retrofit_profileedit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();

                ApiService apiService_profileedit = retrofit_profileedit.create(ApiService.class);

                Call<ResponseBody> profileedituser = (Call<ResponseBody>) apiService_profileedit.getname(mcontact.getID(),mcontact.getName(),mcontact.getPhoneNumber(), mcontact.getEmail(),mcontact.getAddress(), mcontact.getBirthday(), mcontact.getMemo());
                profileedituser.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            System.out.println(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                break;

            case "Edit":

                Retrofit retrofit_edit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();

                ApiService apiService_edit = retrofit_edit.create(ApiService.class);

                Call<ResponseBody> edituser = (Call<ResponseBody>) apiService_edit.getname(mcontact.getID(),mcontact.getName(),mcontact.getPhoneNumber(), mcontact.getEmail(),mcontact.getAddress(), mcontact.getBirthday(), mcontact.getMemo());
                edituser.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            System.out.println(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                break;
            case "Add":
                Retrofit retrofit_add = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();

                ApiService apiService_add = retrofit_add.create(ApiService.class);

                Call<ResponseBody> adduser = (Call<ResponseBody>) apiService_add.getname(mcontact.getName(),mcontact.getPhoneNumber(), mcontact.getEmail(),mcontact.getAddress(), mcontact.getBirthday(), mcontact.getMemo());
                adduser.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            try {
                                JSONObject obj = new JSONObject(response.body().string());
                                ArrayListOfcontact.add(contactcreater(obj));
                                clone.addAll(ArrayListOfcontact);
                                adapter = new RecyclerViewDataAdapter(mactivity,mcontext, mfragment, ArrayListOfcontact,v,clone);
                                RecyclerView recyclerView_add = (RecyclerView) v.findViewById(R.id.recyclerView);
                                recyclerView_add.setAdapter(adapter);
                                recyclerView_add.setLayoutManager(new LinearLayoutManager(mactivity));
                                final EditText search = (EditText) v.findViewById(R.id.searchEdit);
                                search.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {
                                        String text = search.getText().toString()
                                                .toLowerCase(Locale.getDefault());
                                        adapter.filter(true,text);

                                    }
                                });
                                System.out.println("hi2");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                break;

            case "LoadAll":
                try {

                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
                        URL url = new URL(urls[0]);//url을 가져온다.
                        con = (HttpURLConnection) url.openConnection();
                        con.connect();//연결 수행
                        InputStream stream = con.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(stream));
                        StringBuffer buffer = new StringBuffer();
                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            buffer.append(line);
                        }
                        System.out.println(buffer.toString());
                        return buffer.toString();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (con != null) {
                            con.disconnect();
                        }
                        try {
                            if (reader != null) {
                                reader.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }//finally 부분
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case "Delete":
                Retrofit retrofit_delete = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();

                ApiService apiService_delete = retrofit_delete.create(ApiService.class);

                Call<ResponseBody> deleteuser = (Call<ResponseBody>) apiService_delete.getname(mid);
                deleteuser.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            System.out.println(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                break;
        }
        return result;
    }
    //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        switch (mstate) {
            case "ProfileEdit":
                break;
            case "Edit":
                System.out.println(ArrayListOfcontact.size());
                ArrayListOfcontact.get(Position).setName(mcontact.getName());
                ArrayListOfcontact.get(Position).setPhoneNumber(mcontact.getPhoneNumber());
                ArrayListOfcontact.get(Position).setAddress(mcontact.getAddress());
                ArrayListOfcontact.get(Position).setEmail(mcontact.getEmail());
                ArrayListOfcontact.get(Position).setBirthday(mcontact.getBirthday());
                ArrayListOfcontact.get(Position).setMemo(mcontact.getMemo());
                System.out.println(ArrayListOfcontact.size());
                clone.addAll(ArrayListOfcontact);
                adapter = new RecyclerViewDataAdapter(mactivity,mcontext, mfragment, ArrayListOfcontact,v,clone);
                RecyclerView recyclerView_edit = (RecyclerView) v.findViewById(R.id.recyclerView);
                recyclerView_edit.setAdapter(adapter);
                recyclerView_edit.setLayoutManager(new LinearLayoutManager(mactivity));
                final EditText search_edit = (EditText) v.findViewById(R.id.searchEdit);
                search_edit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String text = search_edit.getText().toString()
                                .toLowerCase(Locale.getDefault());
                        adapter.filter(true,text);

                    }
                });
                break;

            case "Add":
                System.out.println("mission clear!!!!");
                break;

            case "LoadAll":
                System.out.println("here");
                ArrayListOfcontact.clear();
                jsontocontacttoArray(result, ArrayListOfcontact);
                System.out.println("mission clear");
                clone.addAll(ArrayListOfcontact);
                adapter = new RecyclerViewDataAdapter(mactivity,mcontext, mfragment, ArrayListOfcontact,v,clone);
                RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(mactivity));
                final EditText search = (EditText) v.findViewById(R.id.searchEdit);
                search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String text = search.getText().toString()
                                .toLowerCase(Locale.getDefault());
                        adapter.filter(true,text);

                    }
                });
                break;

            case "Delete" :
                ArrayListOfcontact.remove(Position);
                clone.addAll(ArrayListOfcontact);
                adapter = new RecyclerViewDataAdapter(mactivity,mcontext, mfragment, ArrayListOfcontact,v,clone);
                RecyclerView recyclerView_delete = (RecyclerView) v.findViewById(R.id.recyclerView);
                recyclerView_delete.setAdapter(adapter);
                recyclerView_delete.setLayoutManager(new LinearLayoutManager(mactivity));
                final EditText search_delete = (EditText) v.findViewById(R.id.searchEdit);
                search_delete.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String text = search_delete.getText().toString()
                                .toLowerCase(Locale.getDefault());
                        adapter.filter(true,text);

                    }
                });
                break;
        }

    }

    public void jsontocontacttoArray(String jsonstring, ArrayList<Contact> arrayListOfContact){

        try {
            JSONArray array = new JSONArray(jsonstring);
            for(int i = 0; i<array.length(); i++){
                JSONObject jObject = array.getJSONObject(i);
                arrayListOfContact.add(contactcreater(jObject));
                System.out.println("contactadd");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Contact contactcreater(JSONObject jObject){
        Contact contact = new Contact();
        ArrayList<String> keys = new ArrayList<>();
        String temp;
        Iterator i = jObject.keys();
        while(i.hasNext()){
            temp = i.next().toString();
            keys.add(temp);
        }
        for(int j = 0; j<keys.size(); j++){
            switch (keys.get(j)){
                case "_id":
                    try {
                        System.out.println(jObject.getString("_id"));
                        contact.setID(jObject.getString("_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                case "name":
                    try {
                        contact.setName(jObject.getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                case "phoneNumber":
                    try {
                        contact.setPhoneNumber(jObject.getString("phoneNumber"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                case "address":
                    try {
                        contact.setAddress(jObject.getString("address"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                case "email":
                    try {
                        contact.setEmail(jObject.getString("email"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                case "birthday":
                    try {
                        contact.setBirthday(jObject.getString("birthday"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                case "memo":
                    try {
                        contact.setMemo(jObject.getString("memo"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }

        return contact;
    }

    public String stringmaker(String state,Contact contact){
        String result="";
        if(state.equals("Edit")){
            result = "edituser?id="+contact.getID()+"&";
        }else if(state.equals("Add")){
            result = "adduser?";
        }

        if(!contact.getName().equals("")){
            result+="name="+contact.getName()+"&";
        }
        if(!contact.getPhoneNumber().equals("")){
            result+="phoneNumber="+contact.getPhoneNumber()+"&";
        }
        if(!contact.getAddress().equals("")){
            result+="address="+contact.getAddress()+"&";
        }
        if(!contact.getEmail().equals("")){
            System.out.println("email:"+contact.getEmail());
            result+="email="+contact.getEmail()+"&";
        }
        if(!contact.getBirthday().equals("")){
            result+="birthday="+contact.getBirthday()+"&";
        }
        if(!contact.getMemo().equals("")){
            result+="memo="+contact.getMemo();
        }
        if(result.charAt(result.length()-1)=='&'){
            result= result.substring(0, result.length()-1);
        }
        return result;
    }




}