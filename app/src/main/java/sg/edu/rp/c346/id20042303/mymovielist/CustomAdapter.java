package sg.edu.rp.c346.id20042303.mymovielist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Movies> versionList;

    public CustomAdapter(Context context, int resource, ArrayList<Movies> object) {
        super(context, resource, object);

        parent_context = context;
        layout_id = resource;
        versionList = object;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvTitle = rowView.findViewById(R.id.textViewTitle);
        TextView tvYear = rowView.findViewById(R.id.textViewYear);
        TextView tvRating = rowView.findViewById(R.id.textViewRating);

        // Obtain the Android Version information based on the position
        Movies currentVersion = versionList.get(position);

        // Set values to the TextView to display the corresponding information

        tvTitle.setText(currentVersion.getTitle());
        tvYear.setText(currentVersion.getYear() + "");
        tvRating.setText(currentVersion.toString());


        return rowView;
    }
}
