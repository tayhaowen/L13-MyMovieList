package sg.edu.rp.c346.id20042303.mymovielist;

import java.io.Serializable;

public class Movies implements Serializable {
    private int id;
    private String title;
    private int year;
    private String description;
    private int rating;

    public Movies(int id, String title, int year, String description, int rating) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.description = description;
        this.rating = rating;
    }

    public Movies(String title, int year, String description, int rating) {
        this.title = title;
        this.year = year;
        this.description = description;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        String result = "";
        if (rating == 5) {
            result = "⭐⭐⭐⭐⭐";
        } else if (rating == 4) {
            result = "⭐⭐⭐⭐";
        } else if (rating == 3) {
            result = "⭐⭐⭐";
        } else if (rating == 2) {
            result = "⭐⭐";
        } else if (rating == 1) {
            result = "⭐";
        }
        return result;
    }
}
