package testing;

import java.util.*;
import milestone2.src.*;

class FileHandleTest { 

    public static void main(String[] args) {
        // FP-T1 (FR-FP1)
        // testLoadMovies(100);
        // testLoadReviews(8);

        // FP-T2 (FR-FP1)
        // testLoadMovies(101);
        // testLoadReviews(9);
    }

    /** 
     * Parameter represents number of expected lines for given file
     */
    public static void testLoadMovies(int lines){ 

        try {
            MovieDB.loadMovies();
            System.out.println(MovieDB.getMovies().size());
            
            if(!MovieDB.getMovies().isEmpty() && MovieDB.getMovies().size() == lines){ 
                System.out.println("Loaded all movies");
            }else{ 
                System.out.println("Failed to load movies");
            }
        } catch (Exception e) {
            System.out.println("Failed to load movies");
        }

    }

    /** 
     * Parameter represents number of expected lines for given file
     */
    public static void testLoadReviews(int lines){ 
        ArrayList<Review> reviews = new ArrayList();

        reviews = FileHandler.readRatingsFile();

        if(!reviews.isEmpty() && reviews.size() == lines){ 
            System.out.println("Loaded reviews");
        }
        else { 
            System.out.println("Failed to load reviews");
        }

    }


}