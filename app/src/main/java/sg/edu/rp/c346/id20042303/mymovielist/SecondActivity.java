package sg.edu.rp.c346.id20042303.mymovielist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    TextView etTitle, etYear, etDes;
    Button btnUpdate, btnDelete, btnBack;
    RatingBar editRB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        etTitle = findViewById(R.id.editTextEditTitle);
        etYear = findViewById(R.id.editTextEditYear);
        etDes = findViewById(R.id.editTextEditDescription);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);
        btnBack = findViewById(R.id.buttonBack);
        editRB = findViewById(R.id.editRatingBar);

        Intent i = getIntent();
        final Movies currentMovie = (Movies) i.getSerializableExtra("movie");
        int id = currentMovie.getId();
        etTitle.setText(currentMovie.getTitle());
        etYear.setText(currentMovie.getYear()+"");
        etDes.setText(currentMovie.getDescription());
        editRB.setRating(currentMovie.getRating());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(SecondActivity.this);
                myBuilder.setTitle("WARNING!");
                myBuilder.setMessage("Are you sure you want to delete " + currentMovie.getTitle() + "?");
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("CANCEL", null);

                myBuilder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(SecondActivity.this);
                        int result = dbh.deleteMovie(currentMovie.getId());
                        if (result>0){
                            Toast.makeText(SecondActivity.this, "Movie deleted", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(SecondActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(SecondActivity.this);
                myBuilder.setTitle("Warning!");
                myBuilder.setMessage("Are you sure you want to update the changes?");
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("CANCEL", null);

                myBuilder.setNegativeButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(SecondActivity.this);
                        currentMovie.setTitle(etTitle.getText().toString().trim());
                        currentMovie.setYear(Integer.parseInt(etYear.getText().toString().trim()));
                        currentMovie.setDescription(etDes.getText().toString().trim());

                        currentMovie.setRating((int)editRB.getRating());
                        int result = dbh.updateMovie(currentMovie);
                        if (result>0){
                            Toast.makeText(SecondActivity.this, "Movie updated", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(SecondActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}