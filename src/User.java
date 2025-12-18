package milestone2.src;

import java.time.LocalDate;
import java.util.Map;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class User {
    private ArrayList<Review> reviews;
    private ArrayList<Movie> reviewedMovies;

    public User(ArrayList<Review> reviews, ArrayList<Movie> reviewedMovies) {
        this.reviews = reviews;
        this.reviewedMovies = reviewedMovies;
    }

    // Returns a list of movies that are highly rated by User
    public ArrayList<Movie> getHighlyRatedMovies() {
        ArrayList<Movie> tempMovies = new ArrayList<Movie>();
        for (Movie movie : reviewedMovies) {
            if (Helper.findReview(reviews, movie).getRating() >= 4) {
                tempMovies.add(movie);
            }
        }
        return tempMovies;
    }

    // Creates a review object based on user input and writes it to my_ratings.txt
    public static Review createReview(Scanner input) {
        System.out.println("What movie would you like to review?");
        String movieName = input.nextLine();

        // Checks if user input is a valid movie
        Movie movie = Helper.findMovie(movieName);

        if (movie.getID() == -1) {
            System.out.println("Not a valid movie");
            System.out.println("Returning to menu...");
            return null;
        }

        System.out.println("What would you like to rate " + movieName + "?");
        // Checks if rating is a valid rating
        float rating = -1;
        try {
            rating = input.nextFloat();
        } catch (InputMismatchException e) {
            System.out.println("Input was not a valid float.");
            System.out.println("Returning to menu...");
            input.next();
            return null;
        }

        if (rating > 5 || rating < 0) {
            System.out.println("Not a valid rating(must be 0-5)");
            System.out.println("Returning to menu...");
            return null;
        }

        // Sets local date
        LocalDate today = LocalDate.now();

        // Creates Review object
        Review review = new Review(movie, rating, today);
        // Writes it to my_ratings.txt
        review.toCSV();
        System.out.println("Review added!");
        return review;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public ArrayList<Movie> getReviewedMovies() {
        return reviewedMovies;
    }
}
