package jose.mokeni.mycallerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add extends AppCompatActivity {
    Button btn_save, btn_cancel, btn_back;

    EditText edfirstname, edlastname, edphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ProfileManager pm = new ProfileManager(Add.this);


        btn_save = findViewById(R.id.btn_save_add);
        btn_cancel = findViewById(R.id.btn_cancel_add);
        btn_back = findViewById(R.id.btn_back_add);

        edfirstname = findViewById(R.id.edfirstname_add);
        edlastname = findViewById(R.id.ed_lastname_add);
        edphone = findViewById(R.id.ed_phone_add);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Add.this, "Save button pressed", Toast.LENGTH_SHORT).show();
                String firstName = edfirstname.getText().toString();
                String lastName = edlastname.getText().toString();
                String phone = edphone.getText().toString();

                Profile p = new Profile(firstName, lastName, phone);
                //Home.data.add(p);
                // TODO: 31/01/2024 Rendre l'enregistrement dans la base SQLITE
                pm.open();
                if (pm.add(p.firstName, p.lastName, p.phone) > 0)
                    Toast.makeText(Add.this, "Profile added successfully", Toast.LENGTH_SHORT).show();
                else
                    // Toast.makeText(Add.this, "An error occured while adding the profile", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Add.this, "An error occured while adding the profile", Toast.LENGTH_SHORT).show();

                try {
                    pm.close();
                } catch (Throwable e) {
                    System.out.println("An error occured while closing db connection");
                }


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Add.this, "Cancel button pressed", Toast.LENGTH_SHORT).show();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(Add.this, Home.class);

                startActivity(i);*/
                finish();
            }
        });

    }
}