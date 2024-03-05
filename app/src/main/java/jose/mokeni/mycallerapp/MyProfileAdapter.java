package jose.mokeni.mycallerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyProfileAdapter extends BaseAdapter {
    Context context;
    ArrayList<Profile> data;

    // Role: Créer les views

    public MyProfileAdapter(Context context, ArrayList<Profile> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public int getCount() {
        // Retourner le nombre de views à créer
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProfileManager pm = new ProfileManager(context);

        // Création d'un prototype
        // Conversion code XML
        LayoutInflater inf = LayoutInflater.from(context);
        View v = inf.inflate(R.layout.view_profile, null);

        // Recuperation des sous views / HOLDERS
        TextView tvfirstname = v.findViewById(R.id.tvfirstname_profile);
        TextView tvlastname = v.findViewById(R.id.tvlastname_profile);
        TextView tvphone = v.findViewById(R.id.tvphone_profile);

        ImageView imgDelete = v.findViewById(R.id.imageViewdelete_profile);
        ImageView imgEdit = v.findViewById(R.id.imageViewedit_profile);
        ImageView imgCall = v.findViewById(R.id.imageViewcall_profile);

        // Récuperation de la donnée
        Profile p = data.get(position);

        // Affecter la view/HOLDER
        tvfirstname.setText(p.firstName);
        tvlastname.setText(p.lastName);
        tvphone.setText(p.phone);

        // Actions sur les HOLDERS
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Alerte de demande de confirmation
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Deleting");
                alert.setMessage("Are you sure you want to delete this profile ?");

                alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        data.remove(position);
                        notifyDataSetChanged(); // Actualisation

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alert.setNeutralButton("Exit", null);

                alert.show();

            }
        });

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Numerotation
                Intent i =new Intent();
                i.setAction(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + p.phone));

                context.startActivity(i);
            }
        });



        return v;
    }
}
