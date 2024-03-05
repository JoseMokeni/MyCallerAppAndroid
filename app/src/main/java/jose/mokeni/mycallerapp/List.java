package jose.mokeni.mycallerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class List extends AppCompatActivity {
    RecyclerView recyclerView_profiles;
    EditText ed_search;

    ArrayList<Profile> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView_profiles = findViewById(R.id.recyclerview_profiles_list);
        ed_search = findViewById(R.id.ed_search_list);

        ProfileManager pm = new ProfileManager(this);

        //ArrayAdapter ad = new ArrayAdapter(List.this, android.R.layout.simple_list_item_1, Home.data);

        // TODO: 31/01/2024 Recuperer les données depuis la base

        pm.open();

        data = new ArrayList<Profile>();
        data = pm.getAllProfiles();

        try {
            pm.close();
        } catch (Throwable e) {
            System.out.println("An error occured while closing db connection (List.java)");
        }

        ArrayList<Profile> searchedData = new ArrayList<Profile>();

        // on edsearch text change
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchedData.clear();
                for (Profile p : data){
                    if (p.firstName.toLowerCase().contains(s.toString().toLowerCase()) || p.lastName.toLowerCase().contains(s.toString().toLowerCase()) || p.phone.toLowerCase().contains(s.toString().toLowerCase())){
                        searchedData.add(p);
                    }
                }
                MyRecyclerProfileAdapter ad = new MyRecyclerProfileAdapter(List.this, searchedData);
                GridLayoutManager manager = new GridLayoutManager(List.this, 2, GridLayoutManager.VERTICAL, true);
                recyclerView_profiles.setLayoutManager(manager);
                recyclerView_profiles.setAdapter(ad);
            }
        });

        // MyProfileAdapter ad = new MyProfileAdapter(List.this, data);
        MyRecyclerProfileAdapter ad = new MyRecyclerProfileAdapter(List.this, data);
        // LinearLayoutManager manager = new LinearLayoutManager(List.this, LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager manager = new GridLayoutManager(List.this, 2, GridLayoutManager.VERTICAL, true);
        recyclerView_profiles.setLayoutManager(manager);
        recyclerView_profiles.setAdapter(ad);
    }
}