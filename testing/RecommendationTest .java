package testing;

import java.util.*;
import src.*;
import java.time.LocalDate;

class RecommendationTest {

    public static void main(String[] args) {

        // initialization of movies
        try {
            MovieDB.loadMovies();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // make reviews (test input)
        ArrayList<String> moviesToReview = new ArrayList<>();

        // FR-RE-T1 expected output: animated movies without any of the reviewed movies
        moviesToReview.add("WALL-E");
        moviesToReview.add("The Lion King");
        moviesToReview.add("Spirited Away");
        moviesToReview.add("Coco");
        moviesToReview.add("Toy Story");
        makeReviews(moviesToReview);

        // FR-RE-T2 expected output: mostly romance movies
        // moviesToReview.add("Forrest Gump");
        // moviesToReview.add("Good Will Hunting");
        // moviesToReview.add("Amélie");
        // moviesToReview.add("Vertigo");
        // moviesToReview.add("Groundhog Day");
        // makeReviews(moviesToReview);

        // FR-RE-T3 expected output: 5 movies with the highest average rating and number
        // of ratings.
        // no input

        // FR-RE-T4 expected output: 5 movies with the highest number of average
        // ratings,
        // number of ratings and genre similarity.
        // moviesToReview.add("Forrest Gump");
        // moviesToReview.add("Good Will Hunting");
        // moviesToReview.add("Amélie");
        // moviesToReview.add("Coco");
        // moviesToReview.add("Toy Story");
        // makeReviews(moviesToReview);

        // FR-RE-T5 expected output: 5 movies that are not the same as the user rated
        // movie.
        // moviesToReview.add("Forrest Gump");
        // makeReviews(moviesToReview);

        // initialize reviews
        FileHandler.ratingsFileExists();

        ArrayList<Review> reviews = FileHandler.readRatingsFile();

        ArrayList<Movie> moviesInReviews = new ArrayList<>();
        for (Review review : reviews) {
            Movie movie = review.getMovie();
            if (movie != null && !moviesInReviews.contains(movie)) {
                moviesInReviews.add(movie);
            }
        }

        User user = new User(reviews, moviesInReviews);

        RecommendationEngine recommendationEngine = new RecommendationEngine(user);

        // FR-RE1 (testing recommendations)
        recommendationEngine.recommendMovies();
    }

    public static void makeReviews(ArrayList<String> movies) {

        try {

            for (String movieName : movies) {
                Movie movie = Helper.findMovie(movieName);
                LocalDate today = LocalDate.now();

                Review review = new Review(movie, 5, today);
                review.toCSV();
            }

        } catch (Exception e) {
            System.out.println("Failed to load movies");
        }

    }

}