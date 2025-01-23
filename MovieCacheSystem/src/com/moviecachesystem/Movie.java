package com.moviecachesystem;

public class Movie {
        private int id;
        private String title;
        private String genre;
        private int releaseYear;
        private double rating;

        public Movie(int id, String title, String genre, int releaseYear, double rating) {
            this.id = id;
            this.title = title;
            this.genre = genre;
            this.releaseYear = releaseYear;
            this.rating = rating;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getGenre() {
            return genre;
        }

        public int getReleaseYear() {
            return releaseYear;
        }

        public double getRating() {
            return rating;
        }

        @Override
        public String toString() {
            return "(" + title + ")";
        }
    }

