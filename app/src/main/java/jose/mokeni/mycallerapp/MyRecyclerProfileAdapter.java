package jose.mokeni.mycallerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerProfileAdapter extends RecyclerView.Adapter<MyRecyclerProfileAdapter.MyViewHolder> {
    Context context;

    ArrayList<Profile> data;

    public MyRecyclerProfileAdapter(Context context, ArrayList<Profile> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyRecyclerProfileAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // View creation
        // Création d'un prototype
        // Conversion code XML
        LayoutInflater inf = LayoutInflater.from(context);
        View v = inf.inflate(R.layout.view_profile, null);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerProfileAdapter.MyViewHolder holder, int position) {
        // Affectation des views

        // Récuperation de la donnée
        Profile p = data.get(position);

        // Affecter la view/HOLDER
        holder.tvfirstname.setText(p.firstName);
        holder.tvlastname.setText(p.lastName);
        holder.tvphone.setText(p.phone);
    }

    @Override
    public int getItemCount() {
        // Nombre total des views
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvfirstname, tvlastname, tvphone;

        ImageView imgDelete, imgEdit, imgCall;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Recuperation des sous views / HOLDERS
            tvfirstname = itemView.findViewById(R.id.tvfirstname_profile);
            tvlastname = itemView.findViewById(R.id.tvlastname_profile);
            tvphone = itemView.findViewById(R.id.tvphone_profile);

            imgDelete = itemView.findViewById(R.id.imageViewdelete_profile);
            imgEdit = itemView.findViewById(R.id.imageViewedit_profile);
            imgCall = itemView.findViewById(R.id.imageViewcall_profile);

            ProfileManager pm = new ProfileManager(context);

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

                            // get the position of selected element
                            int position = getAdapterPosition();

                            pm.open();
                            
                            long rowsDeleted = pm.delete(data.get(position).id);

                            try {
                                pm.close();
                            } catch (Throwable e) {
                                Toast.makeText(context, "An error occured while deleting the profile", Toast.LENGTH_SHORT).show();
                            }
                            if (rowsDeleted > 0)
                            {
                                data.remove(position);
                                Toast.makeText(context, "Profile deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(context, "An error occured while deleting the profile", Toast.LENGTH_SHORT).show();
                                
                            }
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

            imgEdit.setOnClickListener(new View.OnClickListener() {
                AlertDialog alertDialog;

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Editing");
                    alert.setMessage("Update infos");

                    LayoutInflater inf = LayoutInflater.from(context);
                    View editDialog = inf.inflate(R.layout.view_dialog, null);

                    EditText ed_firstname_dialog = editDialog.findViewById(R.id.edfirstname_edit_dialog);
                    EditText ed_lastname_dialog = editDialog.findViewById(R.id.edlastname_edit_dialog);
                    EditText ed_phone_dialog = editDialog.findViewById(R.id.edphone_edit_dialog);

                    ed_firstname_dialog.setText(data.get(position).firstName);
                    ed_lastname_dialog.setText(data.get(position).lastName);
                    ed_phone_dialog.setText(data.get(position).phone);

                    Button btn_save = editDialog.findViewById(R.id.btn_save_edit_dialog);
                    Button btn_cancel = editDialog.findViewById(R.id.btn_cancel_edit_dialog);
                    


                    btn_save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String firstName = ed_firstname_dialog.getText().toString();
                            String lastName = ed_lastname_dialog.getText().toString();
                            String phone = ed_phone_dialog.getText().toString();
                            int id = data.get(position).id;
                            
                            pm.open();

                            long rowsUpdated = pm.update(id, firstName, lastName, phone);

                            try {
                                pm.close();
                            } catch (Throwable e) {
                                Toast.makeText(context, "An error occured while closing db", Toast.LENGTH_SHORT).show();
                            }

                            if (rowsUpdated > 0)
                            {
                                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                data.set(position, new Profile(id, firstName, lastName, phone));
                                notifyDataSetChanged();
                                alertDialog.dismiss();
                            } 
                            else
                            {
                                Toast.makeText(context, "An error occured while updating the profile", Toast.LENGTH_SHORT).show();
                            }
                            
                        }
                    });
                    
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(context, "Cancel button pressed", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();

                        }
                    });


                    alert.setView(editDialog);

                    alertDialog = alert.create();
                    alertDialog.show();


                }
            });

            imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get the position of selected element
                    int position = getAdapterPosition();
                    // Numerotation
                    if (MainActivity.PERMISSION){
                        Intent i =new Intent();
                        i.setAction(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel:" + data.get(position).phone));

                        context.startActivity(i);

                    } else {
                        Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
