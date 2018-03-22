package moviestore;

import enums.MovieType;
import exception.NoSuchMovieException;
import inventory.Customer;
import inventory.Inventory;
import inventory.Movie;

import java.util.HashMap;

public class MovieRental {

    private Inventory inventory;
    private HashMap<Customer, Basket> baskets = new HashMap<>();

    public MovieRental(Inventory inventory) {
        this.inventory = inventory;
    }


    /**
     * Movies need to be added to basket before renting. Customer rents the whole basket at once.
     * Method to add movies to basket. A new basket will be made if current customer does not have a basket yet.
     */
    public void addMovieToBasket(Customer customer, Movie movie, int daysRentedFor) {
        try {
            if (!inventory.isMovieRentedOut(movie)) {
                if (baskets.containsKey(customer)) {
                    baskets.get(customer).addMovie(movie, daysRentedFor);
                } else {
                    Basket basket = new Basket(customer);
                    basket.addMovie(movie, daysRentedFor);
                    baskets.put(customer, basket);
                }
            } else {
                throw new NoSuchMovieException("Movie " + movie.getMovieName() + " is currently rented out");
            }
        } catch (NoSuchMovieException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Method to remove movie from customer's basket.
     */
    public void removeMovieFromBasket(Customer customer, Movie movie) {
        if (!baskets.containsKey(customer)) {
            try {
                throw new Exception("Basket for customer " + customer.getCustomerId() + " does not exist");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (!baskets.get(customer).getMovies().containsKey(movie)) {
            try {
                throw new Exception("Basket does not contain " + movie.getMovieName());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            baskets.get(customer).removeMovie(movie);
        }
    }

    /**
     * Rent out the movies in customer's basket.
     * @return Basket total price.
     */
    public int checkout(Customer customer) {
        Basket basket = baskets.get(customer);
        if (basket == null) {
            try {
                throw new Exception("Basket for customer " + customer.getCustomerId() + " does not exist");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return 0;
        } else {
            StringBuilder orderString = new StringBuilder();
            int totalPrice = 0;
            HashMap<Movie, Integer> movies = basket.getMovies();
            for (Movie movie : movies.keySet()) {
                int daysRentedFor = movies.get(movie);
                int moviePrice = movie.getMovieType().calculatePrice(daysRentedFor);
                movie.rentMovie(daysRentedFor);
                inventory.rentOutMovie(movie, customer);
                addBonusPoints(customer, movie);

                int bonusPointsPayed = 0;
                int daysWillPayWithBonusPoints = 0;
                if (movie.getMovieType().getType().equals(MovieType.NEW_RELEASE.getType())) {
                    int amountOfDaysCouldPayWithBonusPoints = customer.getBonusPoints() / 25;
                    if (amountOfDaysCouldPayWithBonusPoints > 0) {
                        daysWillPayWithBonusPoints = amountOfDaysCouldPayWithBonusPoints;
                        if (amountOfDaysCouldPayWithBonusPoints > daysRentedFor) {
                            daysWillPayWithBonusPoints = daysRentedFor;
                        }
                        bonusPointsPayed = daysWillPayWithBonusPoints * 25;
                        customer.removeBonusPoints(bonusPointsPayed);
                    }
                }

                orderString.append(movie.getMovieName())
                        .append(" (")
                        .append(movie.getMovieType().getType())
                        .append(") ")
                        .append(daysRentedFor)
                        .append(" days ");
                if (daysWillPayWithBonusPoints > 0) {
                    moviePrice = movie.getMovieType().calculatePrice(daysRentedFor-daysWillPayWithBonusPoints);
                    orderString.append(" (Paid with ")
                            .append(bonusPointsPayed)
                            .append(" Bonus points) Final price: ");
                }
                totalPrice += moviePrice;
                orderString.append(moviePrice)
                        .append(" EUR\n");
            }
            orderString.append("Total price: ")
                    .append(totalPrice)
                    .append(" EUR\n")
                    .append("Remaining Bonus points: ")
                    .append(customer.getBonusPoints())
                    .append("\n");
            System.out.println(orderString.toString());
            baskets.remove(customer);
            return totalPrice;
        }
    }

    /**
     * Add bonus points to customer's account for each movie.
     */
    private void addBonusPoints(Customer customer, Movie movie) {
        switch (movie.getMovieType()) {
            case NEW_RELEASE:
                customer.addBonusPoints(2);
                break;
            default:
                customer.addBonusPoints(1);
        }
    }
}
