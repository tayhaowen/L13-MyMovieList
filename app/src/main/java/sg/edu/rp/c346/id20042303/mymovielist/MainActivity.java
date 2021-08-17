package sg.edu.rp.c346.id20042303.mymovielist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnSort;
    ListView lv;
    ArrayList<Movies> movieList;
    int sort = 0;

    //ArrayAdapter adapter;
    int requestCode = 9;
    CustomAdapter caMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.buttonAdd);
        btnSort = findViewById(R.id.buttonSort);
        lv = findViewById(R.id.ListView);

        DBHelper dbh = new DBHelper(this);
        movieList = dbh.getAllMovies();
        dbh.close();

        caMovie = new CustomAdapter(MainActivity.this, R.layout.row, movieList);
        lv.setAdapter(caMovie);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("movie", movieList.get(position));
                startActivityForResult(i, requestCode);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the input.xml layout file
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.insert_movie, null);
                // Obtain the UI component in the input.xml layout
                // It needs to be defined as "final", so that it can used in the onClick() method later

                final EditText etTitle = viewDialog.findViewById(R.id.editTextTitle);
                final EditText etYear = viewDialog.findViewById(R.id.editTextYear);
                final EditText etDes = viewDialog.findViewById(R.id.editTextDescription);
                final RatingBar rgStar = viewDialog.findViewById(R.id.ratingBar);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Insert new Movie");
                myBuilder.setPositiveButton("INSERT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(!etTitle.getText().toString().isEmpty() && !etYear.getText().toString().isEmpty() && !etDes.getText().toString().isEmpty()){
                            String title = etTitle.getText().toString().trim();
                            int year = Integer.parseInt(etYear.getText().toString().trim());
                            String description = etDes.getText().toString().trim();
                            int stars = (int) rgStar.getRating();

                            DBHelper dbh = new DBHelper(MainActivity.this);
                            long result = dbh.insertMovie(title, year, description, stars);

                            if (result != -1) {
                                Toast.makeText(MainActivity.this, "Movie inserted", Toast.LENGTH_LONG).show();

                                lv.setAdapter(null);
                                dbh = new DBHelper(MainActivity.this);
                                movieList = dbh.getAllMovies();
                                dbh.close();

                                caMovie = new CustomAdapter(MainActivity.this, R.layout.row, movieList);
                                lv.setAdapter(caMovie);
                            } else {
                                Toast.makeText(MainActivity.this, "Insert failed", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Insert failed", Toast.LENGTH_LONG).show();
                        }


                    }
                });
                myBuilder.setNegativeButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sort % 2 == 0){
                    lv.setAdapter(null);
                    DBHelper dbh = new DBHelper(MainActivity.this);
                    movieList = dbh.sortMovieByRatingDESC();
                    dbh.close();

                    caMovie = new CustomAdapter(MainActivity.this, R.layout.row, movieList);
                    lv.setAdapter(caMovie);
                    sort++;
                }else{
                    lv.setAdapter(null);
                    DBHelper dbh = new DBHelper(MainActivity.this);
                    movieList = dbh.sortMovieByRatingASC();
                    dbh.close();

                    caMovie = new CustomAdapter(MainActivity.this, R.layout.row, movieList);
                    lv.setAdapter(caMovie);
                    sort++;
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == this.requestCode && resultCode == RESULT_OK){
            DBHelper dbh = new DBHelper(this);
            movieList.clear();
            movieList.addAll(dbh.getAllMovies());
            dbh.close();
            //adapter.notifyDataSetChanged();
            caMovie.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}