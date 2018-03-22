package inventory;

import enums.MovieType;
import exception.NoSuchMovieException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Inventory {

    private List<Movie> listOfAllMovies = new ArrayList<>();
    private List<Movie> listOfMoviesCurrentlyInStore = new ArrayList<>();
    private HashMap<Customer, List<Movie>> rentedOutMovies = new HashMap<>();

    public Inventory() {
    }

    /**
     * Add new movie to store shelf
     */
    public void addMovie(Movie movie) {
        listOfAllMovies.add(movie);
        listOfMoviesCurrentlyInStore.add(movie);
    }

    /**
     * Remove given movie from store. Throws exception if movie is currently not available (rented out)
     * or is not in the store at all.
     */
    public void removeMovie(Movie movie) {
        if (listOfAllMovies.contains(movie) && listOfMoviesCurrentlyInStore.contains(movie)) {
            listOfAllMovies.remove(movie);
            listOfMoviesCurrentlyInStore.remove(movie);
        } else {
            try {
                throw new NoSuchMovieException("Movie " + movie.getMovieName() + " not found in database");
            } catch (NoSuchMovieException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Changes movie type. Throws exception if movie is currently not available (rented out)
     * or is not in the store at all.
     */
    public void changeMovieType(Movie movie, String movieType) {
        if (listOfAllMovies.contains(movie) && listOfMoviesCurrentlyInStore.contains(movie)) {
            movie.setMovieType(MovieType.valueOf(movieType));
        } else {
            try {
                throw new NoSuchMovieException("Movie " + movie.getMovieName() + " not found in database");
            } catch (NoSuchMovieException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Returns whether movie is already rented out.
     * @return Boolean, whether movie is rented out
     * @throws NoSuchMovieException If the movie does not exist in the database
     */
    public boolean isMovieRentedOut(Movie movie) throws NoSuchMovieException {
        if (listOfAllMovies.contains(movie)) {
            if (listOfMoviesCurrentlyInStore.contains(movie)) {
                return false;
            } else {
                return true;
            }
        }
        throw new NoSuchMovieException("Movie " + movie.getMovieName() + " not found in database");
    }

    /**
     * Checks whether the correct customer returned the movie.
     * @return Boolean, whether the correct customer returned the asked movie
     */
    public boolean isCorrectCustomerReturning(Movie movie, Customer customer) {
        if (rentedOutMovies.containsKey(customer)) {
            if (rentedOutMovies.get(customer).contains(movie)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Puts movie to rented movies list. Throws exception if movie is currently not available (rented out)
     * or is not in the store at all.
     */
    public void rentOutMovie(Movie movie, Customer customer) {
        if (!listOfAllMovies.contains(movie)) {
            try {
                throw new NoSuchMovieException("Movie " + movie.getMovieName() + " not in store");
            } catch (NoSuchMovieException e) {
                System.out.println(e.getMessage());
            }
        } else if (!listOfMoviesCurrentlyInStore.contains(movie)) {
            try {
                throw new NoSuchMovieException("Movie " + movie.getMovieName() + " is currently out of stock");
            } catch (NoSuchMovieException e) {
                System.out.println(e.getMessage());
            }
        } else {
            listOfMoviesCurrentlyInStore.remove(movie);
            if (rentedOutMovies.containsKey(customer)) {
                rentedOutMovies.get(customer).add(movie);
            } else {
                List<Movie> movies = new ArrayList<>();
                movies.add(movie);
                rentedOutMovies.put(customer, movies);
            }
        }
    }

    /**
     * Moves movie back to store list
     */
    public void returnMovie(Movie movie, Customer customer) {
        rentedOutMovies.get(customer).remove(movie);
        listOfMoviesCurrentlyInStore.add(movie);
    }

    public List<Movie> listAllMovies() {
        System.out.println("Listing all movies: ");
        listMovies(listOfAllMovies);
        return listOfAllMovies;
    }

    public List<Movie> listAllMoviesCurrentlyInStore() {
        System.out.println("Listing all movies currently in store: ");
        listMovies(listOfMoviesCurrentlyInStore);
        return listOfMoviesCurrentlyInStore;
    }

    private void listMovies(List<Movie> movies) {
        for (Movie movie : movies) {
            System.out.println(movie.getMovieName() + " (" + movie.getMovieType().getType() + ")");
        }
        System.out.println();
    }
}
