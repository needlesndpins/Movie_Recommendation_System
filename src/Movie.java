package milestone2.src;


import java.util.ArrayList;

public class Movie {
    private int id;
    private String title;
    private String director;
    private int year;
    private ArrayList<String> genres;
    private float avgRating;
    private int numRatings;


    // Empty constructor
    public Movie(){ 
        this.id = -1;
    }

    // Main constructor
    public Movie(int id, String title, String director, int year, ArrayList<String> genres, float avgRating, int numRatings){
        this.id = id;
        this.title = title;
        this.director = director;
        this.year = year;
        this.genres = genres;
        this.avgRating = avgRating;
        this.numRatings = numRatings;
    }


    
    public int getID(){ 
        return id;
    }

    public String getTitle(){ 
        return title;
    }
    public String getDirector(){ 
        return director;
    }
    public int getYear(){ 
        return year;
    }
    
    public float getAvgRating(){
        return avgRating;
    }

    public int getNumRatings(){
        return numRatings;
    }

    public ArrayList<String> getGenres(){
        return genres;
    }

    public void printMovie(){
        System.out.println(title + "; " + director + "; " + year + ";" + genres);
    }
}
