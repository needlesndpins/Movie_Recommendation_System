# Movie Recommendation System  

### Authors: Alex Horton, David Ho, Marko Kosoric



The Movie Recommendation System (MRS) is a Java command line interface (CLI) system that reads movie and rating data from CSV files. It allows users to create or update movie reviews. It also creates movie recommendations based on genre similarity and past ratings. The system uses a content-based recommendation algorithm, comparing a user’s highly rated movies (4-5 stars) with movies in the database.

# Directory Purposes

- data: Stores CSV text files.
- docs: Holds all important documentation such as the software requirements specification (SRS) and system design document (SDD).
- src: Contains all Java source code for the Movie Recommendation System.
- testing: Holds all java test files

# Requirements



### Java Version

- Java 17 or later

- The system uses only standard Java libraries (java.io, java.util, java.time)

- No external libraries are required



### Operating System

- Windows, macOS, or Linux



### Required Data Files

These must be placed in the project root directory:



movies.txt: Main movie database, which is required for the system to run.

my_ratings.txt: User rating history, which is created automatically if missing.



# How to Compile and Run


Clone repository

Ensure in root directory movie_recommendation_system/

Run javac src/*.java

Run java src.Main from root directory
