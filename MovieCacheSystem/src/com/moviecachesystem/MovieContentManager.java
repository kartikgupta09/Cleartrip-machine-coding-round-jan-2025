package com.moviecachesystem;

import java.util.*;
import java.time.Year;

public class MovieContentManager {
    private Map<Integer, Movie> movieStore;
    private Map<Integer, User> userStore;
    private L1Cache l1Cache;
    private L2Cache l2Cache;

    public MovieContentManager() {
        this.movieStore = new HashMap<>();
        this.userStore = new HashMap<>();
        this.l1Cache = new L1Cache(5);
        this.l2Cache = new L2Cache(20);
    }

    public void addMovie(int id, String title, String genre, int year, double rating) {
        try {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Title cannot be empty.");
            }
            if (year <= 0) {
                throw new IllegalArgumentException("Year must be a positive integer.");
            }
            int currentYear = java.time.Year.now().getValue();
            if (year > currentYear) {
                throw new IllegalArgumentException("Year cannot be in the future.");
            }
            if (rating < 0) {
                throw new IllegalArgumentException("Rating cannot be negative.");
            }
            if (rating > 10) {
                throw new IllegalArgumentException("Rating cannot be greater than 10.");
            }

            if (movieStore.containsKey(id)) {
                throw new IllegalArgumentException("Duplicate movie ID");
            }
            movieStore.put(id, new Movie(id, title, genre, year, rating));
            System.out.println("Movie \"" + title + "\" added successfully");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addUser(int id, String name, String preferredGenre) {
        try {
            if (name == null) {
                throw new IllegalArgumentException("User name cannot be null.");
            }
            if (name.trim().isEmpty()) {
                throw new IllegalArgumentException("User name cannot be empty.");
            }
            if (preferredGenre == null || preferredGenre.trim().isEmpty()) {
                throw new IllegalArgumentException("Preferred genre cannot be empty.");
            }

            // Handle duplicate user ID
            if (userStore.containsKey(id)) {
                throw new IllegalArgumentException("Duplicate user ID");
            }

            // Add user to the store
            userStore.put(id, new User(id, name, preferredGenre));
            System.out.println("User '" + id + " " + name + "' added successfully");

        } catch (IllegalArgumentException e) {
            // Print the error message and allow the program to continue
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void search(int userId, String searchType, String searchValue) {
        try {
            // Validate the user ID
            if (!userStore.containsKey(userId)) {
                System.out.println("Error: Invalid user ID");
                return;
            }

            // Validate searchType and searchValue
            if (searchType == null || searchType.trim().isEmpty()) {
                throw new IllegalArgumentException("Search type cannot be null or empty.");
            }

            if (searchValue == null || searchValue.trim().isEmpty()) {
                throw new IllegalArgumentException("Search value cannot be null or empty.");
            }

            // Construct the search key
            String searchKey = searchType + ":" + searchValue;

            // Check the cache first
            if (getCacheName(userId, searchKey)) return;

            // Perform search in primary store
            List<Movie> result = searchPrimaryStore(searchType, searchValue);

            if (result.isEmpty()) {
                System.out.println("No results found");
            } else {
                System.out.println(result + " (Found in Primary Store)");
                // Cache the results
                l1Cache.put(userId, searchKey, result);
                l2Cache.put(searchKey, result);
            }
        } catch (IllegalArgumentException e) {
            // Handle the exception and print the error message
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other exceptions and print a generic error message
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public void searchMulti(int userId, String genre, int year, double minRating) {
        try {
            if (!userStore.containsKey(userId)) {
                System.out.println("Error: Invalid user ID");
                return;
            }

            if (genre == null || genre.trim().isEmpty()) {
                throw new IllegalArgumentException("Genre cannot be null or empty.");
            }

            int currentYear = Year.now().getValue();
            if (year <= 0 || year > 2100) {
                throw new IllegalArgumentException("Invalid year.");
            }

            if (minRating < 0 || minRating > 10) {
                throw new IllegalArgumentException("Rating must be between 0 and 10.");
            }

            String searchKey = "MULTI:" + genre + ":" + year + ":" + minRating;

            if (getCacheName(userId, searchKey)) return;

            List<Movie> result = new ArrayList<>();
            for (Movie movie : movieStore.values()) {
                if (movie.getGenre().equalsIgnoreCase(genre) && movie.getReleaseYear() == year && movie.getRating() >= minRating) {
                    result.add(movie);
                }
            }

            if (result.isEmpty()) {
                System.out.println("No results found");
            } else {
                System.out.println(result + " (Found in Primary Store)");
                l1Cache.put(userId, searchKey, result);
                l2Cache.put(searchKey, result);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    boolean getCacheName(int userId, String searchKey) {
        try {
            List<Movie> result = l1Cache.get(userId, searchKey);
            if (result != null) {
                System.out.println(result + " (Found in L1 Cache)");
                return true;
            }

            result = l2Cache.get(searchKey);
            if (result != null) {
                System.out.println(result + " (Found in L2 Cache)");
                l1Cache.put(userId, searchKey, result);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error while fetching from cache: " + e.getMessage());
        }
        return false;
    }

    List<Movie> searchPrimaryStore(String searchType, String searchValue) {
        List<Movie> result = new ArrayList<>();
        try {
            if (searchType == null || searchType.trim().isEmpty()) {
                throw new IllegalArgumentException("Search type cannot be null or empty.");
            }

            if (searchValue == null || searchValue.trim().isEmpty()) {
                throw new IllegalArgumentException("Search value cannot be null or empty.");
            }

            for (Movie movie : movieStore.values()) {
                switch (searchType.toLowerCase()) {
                    case "title":
                        if (movie.getTitle().equalsIgnoreCase(searchValue)) {
                            result.add(movie);
                        }
                        break;
                    case "genre":
                        if (movie.getGenre().equalsIgnoreCase(searchValue)) {
                            result.add(movie);
                        }
                        break;
                    case "year":
                        try {
                            int year = Integer.parseInt(searchValue);
                            if (movie.getReleaseYear() == year) {
                                result.add(movie);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Invalid year format for search value.");
                        }
                        break;
                    default:
                        System.out.println("Error: Invalid search type.");
                        break;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during the search: " + e.getMessage());
        }
        return result;
    }

    public void clearCache(String cacheLevel) {
        try {
            if (cacheLevel == null || cacheLevel.trim().isEmpty()) {
                throw new IllegalArgumentException("Cache level cannot be null or empty.");
            }

            switch (cacheLevel.toLowerCase()) {
                case "l1":
                    l1Cache.clear();
                    System.out.println("L1 cache cleared successfully");
                    break;
                case "l2":
                    l2Cache.clear();
                    System.out.println("L2 cache cleared successfully");
                    break;
                default:
                    System.out.println("Error: Invalid cache level.");
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
