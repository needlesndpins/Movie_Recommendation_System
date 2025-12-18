package src;

import java.time.LocalDate;

public class Review {
    // private User user; // Don't think we need this field
    private Movie movie;
    private float rating;
    private LocalDate date;

    public Review(){ 
        rating = -1;
    }

    // Maybe change Movie to movieID instead
    public Review(Movie movie, float rating, LocalDate date){ 
        this.movie = movie;
        this.rating = rating;
        this.date = date;
    }

    /** 
     * Calls fileHandler to update my_ratings.txt with new review
     */
    public void toCSV(){
        FileHandler.updateRatingsFile(this);
    }

    public Movie getMovie(){ 
        return movie;
    }

    public LocalDate getDate(){ 
        return date;
    }

    public float getRating(){
        return rating;
    }

    //add a method to get users review and check format 
}
