import enums.MovieType;
import inventory.Customer;
import inventory.Inventory;
import inventory.Movie;
import moviestore.MovieRental;
import moviestore.MovieReturn;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieRentalTest {

    private Inventory inventory;
    private Movie newReleaseMovie;
    private Movie regularRentalMovie;
    private Movie oldFilmMovie;
    private MovieRental movieRental;
    private MovieReturn movieReturn;
    private Customer customer;


    @Before
    public void runBeforeEachTest() {
        System.out.println("------------------------------");
        inventory = new Inventory();
        newReleaseMovie = new Movie("Matrix 11", MovieType.NEW_RELEASE);
        regularRentalMovie = new Movie("Spider Man", MovieType.REGULAR_FILM);
        oldFilmMovie = new Movie("Back to the Future", MovieType.OLD_FILM);
        movieRental = new MovieRental(inventory);
        movieReturn = new MovieReturn(inventory);
        customer = new Customer();
    }

    @Test
    public void testRentRegularRentalMovie() {
        inventory.addMovie(regularRentalMovie);

        movieRental.addMovieToBasket(customer, regularRentalMovie, 5);

        assertEquals(9, movieRental.checkout(customer));
    }

    @Test
    public void testRentOldFilmMovie() {
        inventory.addMovie(oldFilmMovie);

        movieRental.addMovieToBasket(customer, oldFilmMovie, 10);

        assertEquals(18, movieRental.checkout(customer));
    }

    @Test
    public void testRentNewReleaseMovie() {
        inventory.addMovie(newReleaseMovie);

        movieRental.addMovieToBasket(customer, newReleaseMovie, 5);

        assertEquals(20, movieRental.checkout(customer));
    }

    @Test
    public void testRentNewReleaseMoviePayWithBonusPoints() {
        inventory.addMovie(newReleaseMovie);

        customer.addBonusPoints(50);
        movieRental.addMovieToBasket(customer, newReleaseMovie, 5);

        assertEquals(12, movieRental.checkout(customer));
    }

    @Test
    public void testRentMultipleMovies() {
        inventory.addMovie(newReleaseMovie);
        inventory.addMovie(regularRentalMovie);
        inventory.addMovie(oldFilmMovie);

        customer.addBonusPoints(50);
        movieRental.addMovieToBasket(customer, newReleaseMovie, 5);
        movieRental.addMovieToBasket(customer, regularRentalMovie, 5);
        movieRental.addMovieToBasket(customer, oldFilmMovie, 5);

        assertEquals(24, movieRental.checkout(customer));
    }

    @Test
    public void testRemoveMovieFromBasketRent() {
        inventory.addMovie(newReleaseMovie);

        movieRental.addMovieToBasket(customer, newReleaseMovie, 5);
        movieRental.removeMovieFromBasket(customer, newReleaseMovie);
        assertEquals(0, movieRental.checkout(customer));
    }

    @Test
    public void testRentMovieNotInStore() {
        movieRental.addMovieToBasket(customer, newReleaseMovie, 5);
        assertEquals(0, movieRental.checkout(customer));
    }

    @Test
    public void testRentMovieAlreadyRentedOut() {
        inventory.addMovie(oldFilmMovie);

        movieRental.addMovieToBasket(customer, oldFilmMovie, 5);
        movieRental.checkout(customer);

        movieRental.addMovieToBasket(customer, oldFilmMovie, 5);
        assertEquals(0, movieRental.checkout(customer));
    }


    @Test
    public void testReturnMovieInRightTime() {
        inventory.addMovie(oldFilmMovie);

        movieRental.addMovieToBasket(customer, oldFilmMovie, 10);
        movieRental.checkout(customer);

        movieReturn.addMovieToReturnList(customer, oldFilmMovie, 5);
        assertEquals(0, movieReturn.returnMovies(customer));
    }

    @Test
    public void testReturnMovieLate() {
        inventory.addMovie(newReleaseMovie);

        movieRental.addMovieToBasket(customer, newReleaseMovie, 5);
        movieRental.checkout(customer);

        movieReturn.addMovieToReturnList(customer, newReleaseMovie, 10);
        assertEquals(20, movieReturn.returnMovies(customer));
    }

    @Test
    public void testReturnMultipleMovies() {
        inventory.addMovie(newReleaseMovie);
        inventory.addMovie(regularRentalMovie);
        inventory.addMovie(oldFilmMovie);

        movieRental.addMovieToBasket(customer, newReleaseMovie, 5);
        movieRental.addMovieToBasket(customer, regularRentalMovie, 5);
        movieRental.addMovieToBasket(customer, oldFilmMovie, 5);
        movieRental.checkout(customer);

        movieReturn.addMovieToReturnList(customer, newReleaseMovie, 7);
        movieReturn.addMovieToReturnList(customer, regularRentalMovie, 8);
        movieReturn.addMovieToReturnList(customer, oldFilmMovie, 4);
        assertEquals(17, movieReturn.returnMovies(customer));
    }

    @Test
    public void testRemoveMovieFromReturnList() {
        inventory.addMovie(newReleaseMovie);

        movieRental.addMovieToBasket(customer, newReleaseMovie, 5);
        movieRental.checkout(customer);

        movieReturn.addMovieToReturnList(customer, newReleaseMovie, 10);
        movieReturn.removeMovieFromReturnList(customer, newReleaseMovie);
        assertEquals(0, movieReturn.returnMovies(customer));
    }

    @Test
    public void testReturnMovieNotInStoreDatabase() {
        movieReturn.addMovieToReturnList(customer, newReleaseMovie, 5);
        assertEquals(0, movieReturn.returnMovies(customer));
    }

    @Test
    public void testReturnMovieNotRentedOut() {
        inventory.addMovie(newReleaseMovie);

        movieReturn.addMovieToReturnList(customer, newReleaseMovie, 5);
        assertEquals(0, movieReturn.returnMovies(customer));
    }

    @Test
    public void testListAllMoviesInStore() {
        inventory.addMovie(newReleaseMovie);
        inventory.addMovie(regularRentalMovie);
        inventory.addMovie(oldFilmMovie);

        assertEquals(3, inventory.listAllMovies().size());
    }

    @Test
    public void testListAllMoviesCurrentlyInStore() {
        inventory.addMovie(newReleaseMovie);
        inventory.addMovie(regularRentalMovie);
        inventory.addMovie(oldFilmMovie);

        movieRental.addMovieToBasket(customer, newReleaseMovie, 5);
        movieRental.checkout(customer);

        assertEquals(2, inventory.listAllMoviesCurrentlyInStore().size());
    }
}
