package src;

import java.util.ArrayList;

/**
 * Class that stores the Movies read from movies.txt
 */
public class MovieDB {
    private static ArrayList<Movie> movies;

    /**
     * Calls FileHandler to load movies from movies.txt
     * movies will be empty if:
     * a) movies.txt is inaccessible
     * b) No lines of movies.txt can be properly parsed
     */
    public static void loadMovies() throws Exception {
        movies = FileHandler.readMoviesFile(); // Load all Movies from Movies.txt and store inside ArrayList

        if (movies == null || movies.isEmpty()) {
            movies = new ArrayList<>();
            throw new Exception("Failed to load movies: movies.txt unreadable or invalid.");
        }
    }

    /**
     * Getter function that returns the movie array list
     */
    public static ArrayList<Movie> getMovies() {
        return movies;
    }

}
