import enums.MovieType;
import inventory.Customer;
import inventory.Inventory;
import inventory.Movie;
import moviestore.MovieRental;
import moviestore.MovieReturn;

/**
 * Created by rasmus on 19-Mar-18.
 */
public class Main {

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Movie lastJedi = new Movie("Star Wars: Last Jedi", MovieType.NEW_RELEASE);
        Movie hours = new Movie("127 hours", MovieType.NEW_RELEASE);
        Movie whatDreamsMayCome = new Movie("What Dreams May Come", MovieType.REGULAR_FILM);
        Movie shaunOfTheDead = new Movie("Shaun of the Dead", MovieType.REGULAR_FILM);
        Movie backToTheFuture = new Movie("Back to the Future", MovieType.OLD_FILM);
        Movie jurassicPark = new Movie("Jurassic Park", MovieType.OLD_FILM);
        MovieRental movieRental = new MovieRental(inventory);
        MovieReturn movieReturn = new MovieReturn(inventory);
        Customer customer = new Customer();

        inventory.addMovie(lastJedi);
        inventory.addMovie(hours);
        inventory.addMovie(whatDreamsMayCome);
        inventory.addMovie(shaunOfTheDead);
        inventory.addMovie(backToTheFuture);
        inventory.addMovie(jurassicPark);

        inventory.listAllMoviesCurrentlyInStore();

        movieRental.addMovieToBasket(customer, lastJedi, 5);
        movieRental.addMovieToBasket(customer, whatDreamsMayCome, 10);
        movieRental.addMovieToBasket(customer, jurassicPark, 3);
        movieRental.removeMovieFromBasket(customer, lastJedi);
        movieRental.addMovieToBasket(customer, lastJedi, 8);
        movieRental.checkout(customer);

        inventory.listAllMoviesCurrentlyInStore();

        customer.addBonusPoints(100);

        movieRental.addMovieToBasket(customer, hours, 12);
        movieRental.addMovieToBasket(customer, shaunOfTheDead, 7);
        movieRental.addMovieToBasket(customer, backToTheFuture, 10);
        movieRental.checkout(customer);

        inventory.listAllMoviesCurrentlyInStore();

        movieRental.addMovieToBasket(customer, whatDreamsMayCome, 12);

        movieReturn.addMovieToReturnList(customer, lastJedi, 10);
        movieReturn.addMovieToReturnList(customer, whatDreamsMayCome, 12);
        movieReturn.addMovieToReturnList(customer, shaunOfTheDead, 5);
        movieReturn.removeMovieFromReturnList(customer, lastJedi);
        movieReturn.addMovieToReturnList(customer, lastJedi, 12);
        movieReturn.returnMovies(customer);

        inventory.listAllMoviesCurrentlyInStore();

        movieReturn.addMovieToReturnList(customer, hours, 7);
        movieReturn.addMovieToReturnList(customer, jurassicPark, 4);
        movieReturn.addMovieToReturnList(customer, backToTheFuture, 4);
        movieReturn.returnMovies(customer);

        inventory.listAllMoviesCurrentlyInStore();
        inventory.listAllMovies();

        movieReturn.addMovieToReturnList(customer, backToTheFuture, 12);

        inventory.changeMovieType(hours, MovieType.REGULAR_FILM.getType());

        inventory.listAllMovies();
    }
}
