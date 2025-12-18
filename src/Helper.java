package milestone2.src;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Class that houses Helper functions which other classes can use
 */

public class Helper {

    /**
     * Safe parseInteger function to help track which field fails to parse and on
     * what line it occurs
     */
    public static int parseInteger(String s, String fieldName, int line) throws NumberFormatException {
        try {
            return Integer.parseInt(s);

        } catch (NumberFormatException e) {
            System.out.println("Error parsing " + fieldName + " on line: " + line);
            System.out.println("Expected input of type Integer. Input received: " + s);
            throw e;
        }

    }

    /**
     * Safe parseFloat function to help track which field fails to parse and on what
     * line it occurs
     */
    public static float parseFloat(String s, String fieldName, int line) throws NumberFormatException {
        try {
            return Float.parseFloat(s);

        } catch (NumberFormatException e) {
            System.out.println("Error parsing " + fieldName + " on line: " + line);
            System.out.println("Expected input of type float. Input received: " + s);
            throw e;
        }

    }

    /**
     * Check if a given String s can be converted to a float
     */
    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true; // String was able to be converted to a float

        } catch (NumberFormatException e) {
            return false; // String could not be converted to a float
        }
    }

    /**
     * Check if a given string s can be converted to an int
     */
    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true; // String was able to be converted to an int

        } catch (NumberFormatException e) {
            return false; // String could not be converted to an int
        }
    }

    /**
     * Takes a Movie Title as input and searches through the MovieDB to find
     * the corresponding Movie Object
     */
    public static Movie findMovie(String title) {
        try {
            for (Movie m : MovieDB.getMovies()) {
                if (title.compareTo(m.getTitle()) == 0) {
                    return m;

                }
            }
        } catch (Exception e) {
            System.out.println("Movies not loaded properly");
        }
        return new Movie(); // Return empty movie if it couldn't find it

    }

    /**
     * Overloaded function that takes a Movie ID as input and searches through the
     * MovieDB to find
     * the corresponding Movie Object
     */
    public static Movie findMovie(int ID) {
        try {
            for (Movie m : MovieDB.getMovies()) {
                if (ID == m.getID()) {
                    return m;
                }
            }
        } catch (Exception e) {
            System.out.println("Movies not loaded properly");
        }
        return new Movie(); // Return empty movie if it couldn't find it

    }

    /**
     * Function that takes a Review arraylist and Movie as input and searches
     * through the list
     * to find
     * the corresponding Review Object
     */
    public static Review findReview(ArrayList<Review> reviews, Movie movie) {
        try {
            for (Review r : reviews) {
                if (r.getMovie().getID() == movie.getID()) {
                    return r;
                }
            }
        } catch (Exception e) {
            System.out.println("Review not found");
        }
        return new Review(); // Return empty movie if it couldn't find it

    }

    /**
     * Function that takes a Movie arraylist and Movie as input and searches
     * through the list to see if it contains said movie
     */
    
    public static Boolean contains(ArrayList<Movie> movieList, Movie movie) {
        try {
            for (Movie m : movieList) {
                if (m.getID() == movie.getID()) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Review not found");
        }
        return false; // Return false if movie not found
    }

    /** 
     * Takes a String representing a rating and date
     * Checks if they are of types float and LocalDate respectively
     */
    public static Boolean validLine(String rating, String date){ 
        try {
            LocalDate.parse(date);
  
        } catch (DateTimeParseException e) {
            return false; // date is not of type LocalDate
        }

        return Helper.isFloat(rating); // date is of type LocalDate so only dependent on this
    }


}


