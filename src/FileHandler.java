package src;

import java.io.*;            // For Buffer and other file reading functions
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;  // For LocalDate type
import java.time.format.DateTimeParseException; 
import java.util.*; // For ArrayList and Map


public class FileHandler {

    /** 
     * Attempt to read movies.txt return store the properly formatted movies
     * inside an ArrayList<Movie>
     * movies.txt format: 
     * movie_id: int , title: String , director: String , year: int , genres: String ; String... , 
     * avg_rating: float , num_ratings: int
     * 
     * Error handling: 
     *   File not found: display error message and terminate
     *   Malformed line: Log a warning with line number and continue parsing remaining lines
     *   Field not expected type: Skip line and log the error
     * 
     * Acceptance criteria: 
     *   movie_id: int 
     *   title: String (Fine even if title is a number type converted to Sring)
     *   director: String (Hard requirement, will not accept type converted numbers)
     *   year: int 
     *   genres: String or sequence of Strings separated by ";". Does not accept type converted numbers
     *   avg_rating: float
     *   num_ratings: int
     * Note: Skips lines with duplicate movie_id, first valid line is saved
     */
    public static ArrayList<Movie> readMoviesFile() {

        ArrayList<Movie> result = new ArrayList(); // Store the movies from movies.txt

        String file = "data/movies.txt"; // File path
        // Note: file path is dependent on where you open the project from.
        // Open in VSCODE or other IDE from root 2ME3-GROUP-PROJECT-GROUP-9 folder
        // Do not open from milestone2, or src directly. Otherwise it won't work
        // Can edit file path if we want to run directly from milestone2 directory
        // But for now will leave it assuming it will be run from root 2me3 directory.

        BufferedReader reader = null;  // Instantialize reader, but don't assign it yet

        String line = ""; // Store each line of file
        int lineCount = 1; // Store what line you are reading, used for error handling

        try {
            reader = new BufferedReader(new FileReader(file)); // Assign reader 
            line = reader.readLine(); // Remove first line (headers)

            Set<Integer> movieIDs = new HashSet(); // Store all MovieID's read from file

            // While line is not null
            WhileLoop: while((line = reader.readLine()) != null){ 
                line = line.trim(); // Remove whitespace
                lineCount ++;

                String[] row = line.split(","); // Split the line at commas
                // Index: 0 -> movieID , 1 -> title , 2 -> director, 3 -> year , 4 -> genres 
                //        5 -> avg_rating , 6 -> num_ratings

                // Missing some fields, or more fields, malformed line
                if(row.length != 7){ 
                    System.out.println("(movies)Line number " + lineCount + " is malformed");
                    System.out.println("(movies)Could not store line: " + lineCount);
                    continue;
                }

                try { // Try to convert String fields into fields for Movie object
                    int movieID = Helper.parseInteger(row[0].trim(),"movie_id",lineCount);

                    if(movieIDs.contains(movieID)){ 
                        System.out.println("(movies)Could not store line: " + lineCount);
                        continue;
                    }
                    movieIDs.add(movieID); // Add to set 
                    

                    String title = row[1].trim();
                    String director = row[2].trim(); 

                    // Skip this line if director is a float or integer
                    if(Helper.isFloat(director) || Helper.isInt(director)){ 
                        System.out.println("(movies)Could not store line: " + lineCount);
                        continue;
                    }
                    int year = Helper.parseInteger(row[3].trim(),"year",lineCount);

                    // Split genres at `;`
                    String[] tempGenre = row[4].split(";");
                    ArrayList<String> genres = new ArrayList();

                    // Check if genres are valid
                    for(String i : tempGenre){ 
                        if(!Helper.isFloat(i) && !Helper.isInt(i)){ 
                            // i is not a float or an int (valid genre) 

                            genres.add(i.trim()); // Add to array list
                        }
                        else{ 
                            System.out.println("(movies)Could not store line: " + lineCount);
                            continue WhileLoop; // Continue to next iteration of while loop, invalid genre
                        }
                    }

                    float avg = Helper.parseFloat(row[5].trim(),"avg_rating",lineCount);
                    int numRatings = Helper.parseInteger(row[6].trim(),"num_ratings",lineCount);

                    // Create movie object with all fields
                    Movie tempMovie = new Movie(movieID,title,director,year,genres,avg,numRatings);
                    result.add(tempMovie); // Add to ArrayList 
                    
                } catch (NumberFormatException e) { 
                    System.out.println("(movies)Could not store line: " + lineCount);
                    // If any Integer or Float fields fail to convert continue to next loop
                    continue; 
                }
            }
            
        } catch (FileNotFoundException e) { // movies.txt not accessible
            System.out.println("movies.txt file not found");
            // e.printStackTrace();

        } catch(IOException e){ 
            // e.printStackTrace(); // Display what went wrong
        }
        finally{ 
            try {
                // Close if reader was opened
                if(reader != null){ 
                    reader.close(); // close reader
                }
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        return result; // Return ArrayList housing Movie objects
                       // Note: is Empty if program fails to open movies.txt
    }


    /**
     * Attempt to read my_ratings.txt and parse any reviews
     *  Note: Can not run if movies.txt fails to load properly 
     *         Dependent on MovieDB class being instantiated properly
     * 
     * my_ratings.txt: 
     * movie_id: int , rating: float , timestamp: LocalDate
     * 
     * Error handling: 
     *   File not found: display error message and terminate
     *   Malformed line: Log a warning with line number and continue parsing remaining lines
     *   Field not expected type: Skip line and log the error
     * 
     * Acceptance criteria: 
     *   movie_id: int 
     *   rating: float 
     *   timestamp: LocalDate
     * 
     * Note: Will skip reviews that have same MovieID
     */
    public static ArrayList<Review> readRatingsFile(){

        // Map<Movie,Review> result = new HashMap<Movie,Review>(); // Store the Reviews from my_ratings.txt
        ArrayList<Review> result = new ArrayList();

        String file = "data/my_ratings.txt"; // File path
        // Note: file path is dependent on where you open the project from.
        // Open in VSCODE or other IDE from root 2ME3-GROUP-PROJECT-GROUP-9 folder
        // Do not open from milestone2, or src directly. Otherwise it won't work
        // Can edit file path if we want to run directly from milestone2 directory
        // But for now will leave it assuming it will be run from root 2me3 directory.

        BufferedReader reader = null;  // Instantialize reader, but don't assign it yet

        String line = ""; // Store each line of file
        int lineCount = 1; // Store what line you are reading, used for error handling

        try {
            reader = new BufferedReader(new FileReader(file)); // Assign reader 
            line = reader.readLine(); // Remove first line (headers)

            Set<Integer> movieIDs = new HashSet(); // Store movieId's for users reviewed movies

            // While line is not null
            while((line = reader.readLine()) != null){ 
                line = line.trim(); // Remove whitespace
                lineCount ++;

                String[] row = line.split(","); // Split the line at commas
                // Index: 0 -> movieID , 1 -> rating , 2 -> timestamp 

                // Missing some fields, or more fields, malformed line
                if(row.length != 3){ 
                    System.out.println("(ratings)Line number " + lineCount + " is malformed");
                    continue;
                }

                try { // Try to convert String fields into fields for Movie object
                    int movieID = Helper.parseInteger(row[0].trim(),"movie_id",lineCount);
                    
                    // Already exists a review for this movie
                    if(movieIDs.contains(movieID)){ 
                        System.out.println("(ratings)Could not process line: " + lineCount);
                        continue;
                    }
                    movieIDs.add(movieID);

                    float rating = Helper.parseFloat(row[1].trim(),"rating",lineCount);
                    LocalDate timestamp;
                    
                    try {
                        timestamp = LocalDate.parse(row[2].trim());

                    } catch (DateTimeParseException e) {
                        System.out.println("(ratings)Error parsing timestamp on line: " + lineCount);
                        continue;
                    }

                    Movie tempMovie = Helper.findMovie(movieID); // Movie with empty fields if not in MovieDB 
                    if(tempMovie.getTitle() != null){ 
                        Review tempReview = new Review(tempMovie,rating,timestamp); // Create temp review
                        result.add(tempReview);  // Add to result
                    }
                    
                } catch (NumberFormatException e) { 
                    // If any Integer or Float fields fail to convert continue to next loop
                    continue; 
                }
            }
            
        } catch (FileNotFoundException e) { // my_ratings.txt not accessible
            System.out.println("my_ratings.txt file not found");
            // e.printStackTrace();

        } catch(IOException e){ 
            // e.printStackTrace(); // Display what went wrong
        }
        finally{ 
            try {
                // Close if reader was opened
                if(reader != null){ 
                    reader.close(); // close reader
                }
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        return result; // Return ArrayList housing Movie objects
                       // Note: is Empty if program fails to open my_ratings.txt

    }

    /** 
     * Update my_ratings.txt with review 
     * Cases to consider: 
     *  1) Movie has not been reviewed by user: Create new line in my_ratings.txt
     *  2) Movie has been reviewed by user: Update corresponding line in my_ratings.txt 
     * 
     * Approach: Rewrite entire file, updating specific line if review already exists
     *           else append to end of file 
     * Note: Assumes given review is well-formatted with non-null fields
     */
    public static void updateRatingsFile(Review review){

        int tempID = Helper.findMovie(review.getMovie().getID()).getID();

        // Invalid rating or movie doesn't exist in MovieDB
        if(review.getRating() > 5 || review.getRating() < 0 || tempID == -1){ 
            System.out.println("Invalid rating");
            return;
        }

        String file = "data/my_ratings.txt"; // File path
        String tempFile = "data/my_tempratings.txt"; /// temp file

        boolean reviewExists = false; // Flag used to mark if updating review (true) or creating new review (false)

        // Note: file path is dependent on where you open the project from.
        // Open in VSCODE or other IDE from root 2ME3-GROUP-PROJECT-GROUP-9 folder
        // Do not open from milestone2, or src directly. Otherwise it won't work
        // Can edit file path if we want to run directly from milestone2 directory
        // But for now will leave it assuming it will be run from root 2me3 directory.

        BufferedReader reader = null;  // Instantialize reader, but don't assign it yet
        BufferedWriter writer = null;  // Instantialize writer

        String line = ""; // Store each line of file
        int lineCount = 0; // Store what line you are reading, used for error handling

        // Construct new line to be added to file
        String newLine = review.getMovie().getID() + "," + review.getRating() + "," + review.getDate();

        try {
            reader = new BufferedReader(new FileReader(file)); // Assign reader 
            writer = new BufferedWriter(new FileWriter(tempFile)); // Assign writer

            // While line is not null
            while((line = reader.readLine()) != null){ 
                line = line.trim(); // Remove whitespace
                lineCount ++;

                String[] row = line.split(","); // Split the line at commas
                // Index: 0 -> movieID , 1 -> rating , 2 -> timestamp 

                try {   
                    int movieID = Integer.parseInt(row[0]); // Don't need to use Helper version, since updating file.
                                                            // User will already be aware of any issues in reading portion

                    // Line in my_ratings.txt for reviewed movie exists
                    // and line is valid -> update it
                    if(movieID == review.getMovie().getID() && Helper.validLine(row[1],row[2])){ 
                        writer.write(newLine); // write the new line 
                        reviewExists = true; // Flag set to true 
                    }
                    else{ 
                        writer.write(line);
                    }

                } catch (NumberFormatException e) { // Gets hit if movieID fails to convert (malformed line)
                    writer.write(line); // Still write malformed lines
                }


                writer.newLine();
               
            }
            if(!reviewExists){  // Went through whole file and couldn't find the review (new review) 
                writer.write(newLine);
                // writer.newLine();
            }
        
            
        } catch (FileNotFoundException e) { // my_ratings.txt not accessible
            System.out.println("my_ratings.txt file not found");
            // e.printStackTrace();

        } catch(IOException e){ 
            // e.printStackTrace(); // Display what went wrong
        }
        finally{ 
            try {
                // Close if reader and writer was opened
                if(reader != null){ reader.close();} // close reader
                if(writer != null){ writer.close();} // close writer

                // Rename and delete files
                Path oldPath = Paths.get(file);
                Path newPath = Paths.get(tempFile);

                try {
                    Files.move(newPath, oldPath,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE);

                } catch (AtomicMoveNotSupportedException e) {
                    Files.move(newPath, oldPath, StandardCopyOption.REPLACE_EXISTING);

                } catch (IOException e) {
                    // e.printStackTrace();
                    System.out.println("Could not replace my_ratings.txt");
                }
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
    }

    /** 
     * Checks if my_ratings.txt exists, creates it if it doesn't
     */
    public static void ratingsFileExists(){ 
        String file = "data/my_ratings.txt"; // File path
        File ratingFile = new File(file);

        if(!ratingFile.exists()){ 
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file));
                writer.write("movie_id,rating,timestamp");

            } catch (Exception e) {
                System.out.println("Unable to create my_ratings.txt file");
            }
            finally{ 
                try {
                    if(writer != null){writer.close();}

                } catch (Exception e) {

                }
            }
        }
    }

}