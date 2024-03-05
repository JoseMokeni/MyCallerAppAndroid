package jose.mokeni.mycallerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    // public static ArrayList<Profile> data = new ArrayList<Profile>();

    Button btn_add, btn_list, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_add = findViewById(R.id.btn_add_home);
        btn_list = findViewById(R.id.btn_list_home);
        btn_logout = findViewById(R.id.btn_logout_home);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Add.class);

                startActivity(i);

            }
        });

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, List.class);

                startActivity(i);

            }
        });
        
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 16/02/2024 Implement logout button action
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.saved_username_key), null);
                editor.apply();

                finish();
            }
        });

        /*if (sharedPreferences.getString(getString(R.string.saved_username_key), null) == null){
            btn_logout.setVisibility(View.GONE);
        }*/
    }
}