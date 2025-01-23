package com.moviecachesystem;

public class MovieCacheSystem {
    public static void main(String[] args) {
        MovieContentManager manager = new MovieContentManager();

        manager.addMovie(1, "Inception", "Sci-Fi", 2010, 9.5);
        manager.addMovie(2, "The Dark Knight", "Action", 2008, 9.0);
        manager.addMovie(1, "", "Sci-Fi", 2010, 9.5);         // Empty title
        manager.addMovie(2, "Future Movie", "Action", -100, 8.5);  // Invalid year
        manager.addMovie(3, "Another Movie", "Sci-Fi", 2025, 10);
        manager.addMovie(3, "Another Movie", "Sci-Fi", 2027, 10); // Future year
        manager.addMovie(4, "Movie with rating", "Drama", 1999, 0);  // Rating = 0
        manager.addMovie(5, "Extreme Rating", "Horror", 2000, 11);

        manager.addUser(1, "John", "Action");
        manager.addUser(2, "Alice", "Sci-Fi");
        manager.addUser(3, "Bob", "Drama");
        manager.addUser(4, "Charlie", "Comedy");
        manager.addUser(1, "Eve", "Horror");
        manager.addUser(5, "", "Action");
        manager.addUser(6, "David", "");
        manager.addUser(7, null, "Action");
        manager.addUser(8, "Eve", null);

        manager.search(1, "title", "Inception");
        manager.search(2, "genre", "Action");
        manager.search(3, "year", "2008");
        manager.search(999, "title", "Inception");
        manager.search(1, "", "Inception");
        manager.search(1, null, "Inception");
        manager.search(1, "title", "");
        manager.search(1, "genre", null);
        manager.search(1, "genre", "Romance");

        manager.searchMulti(1, "Action", 2008, 8.0);
        manager.searchMulti(2, "Sci-Fi", 2010, 7.5);
        manager.searchMulti(3, "Drama", 2015, 6.0);

        manager.searchMulti(999, "Action", 2008, 8.0);
        manager.searchMulti(1, "", 2008, 8.0);
        manager.searchMulti(1, null, 2008, 8.0);
        manager.searchMulti(1, "Action", 1899, 8.0);
        manager.searchMulti(1, "Action", 2200, 8.0);
        manager.searchMulti(1, "Action", 2008, -1.0);
        manager.searchMulti(1, "Action", 2008, 11.0);
        manager.searchMulti(1, "Horror", 2008, 8.0);

        manager.getCacheName(1, "MULTI:Action:2008:8.0");
        manager.getCacheName(999, "MULTI:Action:2008:8.0");
        manager.getCacheName(1, null);
        manager.getCacheName(1, "");

        manager.searchPrimaryStore("title", "Inception");
        manager.searchPrimaryStore("genre", "Action");
        manager.searchPrimaryStore("year", "2008");
        manager.searchPrimaryStore("year", "InvalidYear");
        manager.searchPrimaryStore("invalid", "Inception");
        manager.searchPrimaryStore("title", "");
        manager.searchPrimaryStore("", "Action");
        manager.searchPrimaryStore(null, "Action");

        manager.clearCache("l1");
        manager.clearCache("l2");
        manager.clearCache("l1");
        manager.clearCache("l2");
        manager.clearCache("l3");
//        manager.clearCache("");
//        manager.clearCache(null);
        manager.getCacheName(1,"inception");


    }
}

