package moviestore;

import inventory.Customer;
import inventory.Movie;

import java.util.HashMap;

public class Basket {

    private Customer customer;
    private HashMap<Movie, Integer> movies;

    public Basket(Customer customer) {
        this.customer = customer;
        this.movies = new HashMap<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public HashMap<Movie, Integer> getMovies() {
        return movies;
    }

    public void addMovie(Movie movie, int days) {
        movies.put(movie, days);
    }

    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }
}
