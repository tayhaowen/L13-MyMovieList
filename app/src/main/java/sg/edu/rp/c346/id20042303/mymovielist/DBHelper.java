package sg.edu.rp.c346.id20042303.mymovielist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MOVIE = "Movie";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_RATING = "rating";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_MOVIE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_RATING + " INTEGER )";
        db.execSQL(createSongTableSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }

    public long insertMovie(String title, int year, String description, int rating) {
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_RATING, rating);
        // Insert the row into the TABLE_MOVIE
        long result = db.insert(TABLE_MOVIE, null, values);
        // Close the database connection
        db.close();

        return result;
    }

    public ArrayList<Movies> getAllMovies() {
        ArrayList<Movies> movieList = new ArrayList<Movies>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_TITLE + "," + COLUMN_YEAR + ","
                + COLUMN_DESCRIPTION + ","
                + COLUMN_RATING + " FROM " + TABLE_MOVIE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                int year = cursor.getInt(2);
                String description = cursor.getString(3);
                int rating = cursor.getInt(4);

                Movies newMovie= new Movies(id, title, year, description, rating);
                movieList.add(newMovie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movieList;
    }

    public ArrayList<Movies> sortMovieByRatingDESC() {
        ArrayList<Movies> movieList = new ArrayList<Movies>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_TITLE + "," + COLUMN_YEAR + ","
                + COLUMN_DESCRIPTION + ","
                + COLUMN_RATING + " FROM " + TABLE_MOVIE
                + " ORDER BY " + COLUMN_RATING  + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                int year = cursor.getInt(2);
                String description = cursor.getString(3);
                int rating = cursor.getInt(4);

                Movies newMovie= new Movies(id, title, year, description, rating);
                movieList.add(newMovie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movieList;
    }

    public ArrayList<Movies> sortMovieByRatingASC() {
        ArrayList<Movies> movieList = new ArrayList<Movies>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_TITLE + "," + COLUMN_YEAR + ","
                + COLUMN_DESCRIPTION + ","
                + COLUMN_RATING + " FROM " + TABLE_MOVIE
                + " ORDER BY " + COLUMN_RATING  + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                int year = cursor.getInt(2);
                String description = cursor.getString(3);
                int rating = cursor.getInt(4);

                Movies newMovie= new Movies(id, title, year, description, rating);
                movieList.add(newMovie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movieList;
    }

    public int deleteMovie(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_MOVIE, condition, args);
        db.close();
        return result;
    }

    public int updateMovie(Movies data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_RATING, data.getRating());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_MOVIE, values, condition, args);
        db.close();
        return result;
    }
}
