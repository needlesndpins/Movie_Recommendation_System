package testing;

import java.time.LocalDate;
import java.util.*;
import milestone2.src.*;

class RatingFunctionalityTest { 

    public static void main(String[] args) {

        // RF-T1 (File writing Test 1) (FR-FP2)
        // testLoadMovies(100); // Need to load movies first
        // ArrayList<Review> fw_t1 = new ArrayList<Review>();
        // fw_t1.add(createReviewObject(23,(float)3.5)); // new
        // fw_t1.add(createReviewObject(1,(float)4.3)); // new 
        // fw_t1.add(createReviewObject(99,(float)1.2)); // new 
        // testUpdateReviews(fw_t1);

        // RF-T2 (Test review updating, movieID already in my_ratings.txt) (FR-FP2)
        // testLoadMovies(100);
        // ArrayList<Review> fw_t2 = new ArrayList<Review>();
        // fw_t2.add(createReviewObject(6,(float)1)); // update
        // fw_t2.add(createReviewObject(15,5)); // update  
        // fw_t2.add(createReviewObject(88,(float)4.3)); // update 
        // fw_t2.add(createReviewObject(48, (float)1.2)); // new 
        // testUpdateReviews(fw_t2);

        // RF-T3 (Try with malformed movies.txt) (FR-FP2)
        // testLoadMovies(100);
        // ArrayList<Review> fw_t3 = new ArrayList<Review>();
        // fw_t3.add(createReviewObject(20,(float)1)); // update
        // fw_t3.add(createReviewObject(4,(float) 5)); // malformed line
        // fw_t3.add(createReviewObject(7,(float)2.1)); // update
        // fw_t3.add(createReviewObject(99, (float)3.3)); // new
        // fw_t3.add(createReviewObject(1000, (float)3.3)); // Invalid movie
        // fw_t3.add(createReviewObject(32, (float)-1)); // Invalid rating
        // testUpdateReviews(fw_t3);

        // RF-T4 ("Stress test") (FR-FP2)
        testLoadMovies(100);
        int movieID = 1; 
        ArrayList<Review> fw_t4 = new ArrayList<Review>();
        while(movieID <= 100){ 
            fw_t4.add(createReviewObject(movieID++, (float)5));
        }
        testUpdateReviews(fw_t4);


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

    /** 
     * Update my_ratings.txt with given ArrayList<Review> 
     */
    public static void testUpdateReviews(ArrayList<Review> reviews){

        // For every review in ArrayList update ratings file with them
        for(Review review : reviews){ 
            FileHandler.updateRatingsFile(review);
        }

    }

    public static Review createReviewObject(int movieID, float rating){ 

        // Get movie for corresponding movieID
        Movie temp = Helper.findMovie(movieID);

        // Create Review Object
        Review result = new Review(temp,rating,LocalDate.now());

        return result;
    }
    

}