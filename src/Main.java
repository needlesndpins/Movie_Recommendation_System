package milestone2.src;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    /**
     * IMPORTANT NOTE ABOUT COMPILING AND RUNNING
     * Run from root 2ME3-GROUP-PROJECT-GROUP-9 Directory or else file reading won't
     * work
     */
    public static void main(String[] args) {

        // Loads all movies from movies.txt to the Movie Database
        try {
            MovieDB.loadMovies();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Check if the movie databse is empty
        if (MovieDB.getMovies().isEmpty()) {
            System.out.println("Movie database is empty.");
        }

        // Check if my_ratings.txt exists. If it does not then it creates one.
        FileHandler.ratingsFileExists();

        // Reads all user ratings and stores in review
        ArrayList<Review> reviews = FileHandler.readRatingsFile();

        // Loops through all movies in reviews list. Adds the movie to moviesInReviews
        // if it is not in the list
        ArrayList<Movie> moviesInReviews = new ArrayList<>();
        for (Review review : reviews) {
            Movie movie = review.getMovie();
            if (movie != null && !moviesInReviews.contains(movie)) {
                moviesInReviews.add(movie);
            }
        }

        // User initialized
        User user = new User(reviews, moviesInReviews);

        RecommendationEngine recommendationEngine = new RecommendationEngine(user);

        Scanner s = new Scanner(System.in);
        String ascii = " __  __            _                                                              \n" + //
                "|  \\/  | _____   _(_) ___                                                         \n" + //
                "| |\\/| |/ _ \\ \\ / / |/ _ \\                                                        \n" + //
                "| |  | | (_) \\ V /| |  __/                                                        \n" + //
                "|_|__|_|\\___/ \\_/ |_|\\___|                              _       _   _             \n" + //
                "|  _ \\ ___  ___ ___  _ __ ___  _ __ ___   ___ _ __   __| | __ _| |_(_) ___  _ __  \n" + //
                "| |_) / _ \\/ __/ _ \\| '_ ` _ \\| '_ ` _ \\ / _ \\ '_ \\ / _` |/ _` | __| |/ _ \\| '_ \\ \n" + //
                "|  _ <  __/ (_| (_) | | | | | | | | | | |  __/ | | | (_| | (_| | |_| | (_) | | | |\n" + //
                "|_|_\\_\\___|\\___\\___/|_| |_| |_|_| |_| |_|\\___|_| |_|\\__,_|\\__,_|\\__|_|\\___/|_| |_|\n" + //
                "/ ___| _   _ ___| |_ ___ _ __ ___                                                 \n" + //
                "\\___ \\| | | / __| __/ _ \\ '_ ` _ \\                                                \n" + //
                " ___) | |_| \\__ \\ ||  __/ | | | | |                                               \n" + //
                "|____/ \\__, |___/\\__\\___|_| |_| |_|                                               \n" + //
                "       |___/                                                                      ";
        System.out.println(ascii);
        System.out.println("Press ENTER to continue");
        s.nextLine();
        boolean systemState = true;
        while (systemState) {
            System.out.println("");
            System.out.println("1. Get movie recommendation.");
            System.out.println("2. Make movie review.");
            System.out.println("3. Quit");
            System.out.println("Enter 1, 2, or 3:");

            String choice = "";
            while (true) {
                choice = s.nextLine();
                if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                    break;
                } else {
                    System.out.println("Choice must be 1, 2, or 3.");
                }
            }

            if (choice.equals("1")) {
                System.out.println("");
                System.out.println("You should check out:");
                recommendationEngine.recommendMovies();
            } else if (choice.equals("2")) {
                System.out.println("");
                Review newReview = User.createReview(s);
                if (newReview != null) {
                    reviews.add(newReview);
                    moviesInReviews.add(newReview.getMovie());
                }

            } else if (choice.equals("3")) {
                systemState = false;
            }
        }
        s.close();

    }

}
