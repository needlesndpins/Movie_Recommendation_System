package src;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class RecommendationEngine {
    private User user;

    public RecommendationEngine(User user){
        this.user = user;
    }

    //counts number of matching with reviewed movies
    private int getGenreScore(Movie movie){
        ArrayList<Movie> reviews = user.getHighlyRatedMovies(); //arraylist of movies for only good reviews
        int score = 0;
        for(Movie review : reviews){
            for(int i = 0; i < movie.getGenres().size(); i++){
                for(int j = 0; j < review.getGenres().size(); j++){
                    if((movie.getGenres().get(i)).equals(review.getGenres().get(j)) ){
                        score++; //increments score if there is a matching genre
                    }
                }
            }
        }
        
        return score;
    }

    //checks if user has reviews, if no reviews exist returns a score based on average rating and number of rankings, if reviews exist returns a score based on average ratings, number of ratings and a genre matching score
    private float getScore(Movie movie){
        if( user.getReviewedMovies() == null){
            return movie.getAvgRating() * movie.getNumRatings();
        }else{
            return ((float) Math.pow((movie.getAvgRating() * getGenreScore(movie)), 2.5)) + movie.getNumRatings();
        }
    }


    public void recommendMovies(){
        ArrayList<Movie> movies = MovieDB.getMovies();
        ArrayList<Movie> reviews = user.getReviewedMovies();

        //New code which uses O(n) time n = number of movies in database

        //min-heap ordered by movie score
        PriorityQueue<Movie> topMovies = new PriorityQueue<>(
            (m1, m2) -> Float.compare(getScore(m1), getScore(m2))
        );

        for (Movie movie : movies) {
            //skip movies the user has already reviewed
            if (Helper.contains(reviews, movie)) {
                continue;
            }

            topMovies.offer(movie); //add movie

            //keep only top 5 movies
            if (topMovies.size() > 5) {
                topMovies.poll(); //remove lowest scoring movie
            }
        }

        return;

        //Old code using O(nlog(n)) time n = number of movies in database

        // //creates a temporary list of movies that have not been reviewed
        // ArrayList<Movie> tempMovies = new ArrayList<>();
        // for(Movie movie : movies){
        //     if(!Helper.contains(reviews,movie)){
        //         tempMovies.add(movie);
        //     }
        // }

        // //sorts movies based on their score from greatest to least and prints them for the user
        // tempMovies.sort((m1, m2) -> Float.compare(getScore(m2), getScore(m1))); 	     
        // for(int i = 0; i < 5 ; i++){
        //     tempMovies.get(i).printMovie(); 
        // }
        // return;

    }
}
