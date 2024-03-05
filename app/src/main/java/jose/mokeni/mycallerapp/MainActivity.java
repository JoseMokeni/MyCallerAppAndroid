package jose.mokeni.mycallerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static boolean PERMISSION = false;

    Button btnexit, btnlogin;

    EditText edemail, edpwd;

    CheckBox checkBox_rememberme;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // permissions
        // test if permissions are already granted
        // it's the call permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED){
            // ask for permissions
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CALL_PHONE
            }, 1);

        } else {
            PERMISSION = true;
        }

        // Getting shared preferences
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        // check if there's already a username saved
        String username = sharedPreferences.getString(getString(R.string.saved_username_key), null);

        if (username != null){
            Intent i = new Intent(MainActivity.this, Home.class);

            startActivity(i);
        }

        // Recuperation
        btnexit = findViewById(R.id.btn_exit_auth);
        btnlogin = findViewById(R.id.btn_login_auth);
        edemail = findViewById(R.id.edemail_auth);
        edpwd = findViewById(R.id.edpwd_auth);

        checkBox_rememberme = findViewById(R.id.checkBox_rememberme_auth);
        // TODO: 16/02/2024 Implement remember me

        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // close current activity
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get strings
                    String email = edemail.getText().toString();
                    String pwd = edpwd.getText().toString();


                        if (email.equals("azer") && pwd.equals("111")){
                            // Passage vers une autre activite
                            // on doit créer une intent: intention de lancer quelque chose
                            // param: activite courante, activite cible
                            if (checkBox_rememberme.isChecked()){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(getString(R.string.saved_username_key), email);
                                editor.apply();

                            }

                            Intent i = new Intent(MainActivity.this, Home.class);

                            startActivity(i);
                        }
                    else {
                        // Message d'erreur
                        Toast.makeText(MainActivity.this, "Email or password not valid", Toast.LENGTH_SHORT).show();
                    }


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1)
        {
            if (grantResults.length > 0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    PERMISSION = true;
                } else {
                    PERMISSION = false;
                }
            }
        }
    }
}