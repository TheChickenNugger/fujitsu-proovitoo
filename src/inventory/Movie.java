package inventory;

import enums.MovieType;

public class Movie {

    private String movieName;
    private MovieType movieType;
    private int daysRentedFor = 0;

    public Movie(String movieName, MovieType movieType) {
        this.movieName = movieName;
        this.movieType = movieType;
    }

    public void returnMovie() {
        daysRentedFor = 0;
    }

    public void rentMovie(int days ) {
        this.daysRentedFor = days;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public MovieType getMovieType() {
        return movieType;
    }

    public void setMovieType(MovieType movieType) {
        this.movieType = movieType;
    }

    public int getDaysRentedFor() {
        return daysRentedFor;
    }
}
