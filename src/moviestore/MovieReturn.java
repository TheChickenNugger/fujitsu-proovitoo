package moviestore;

import exception.NoSuchMovieException;
import inventory.Customer;
import inventory.Inventory;
import inventory.Movie;

import java.util.HashMap;

public class MovieReturn {

    private Inventory inventory;
    private HashMap<Customer, Basket> returnBaskets = new HashMap<>();

    public MovieReturn(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Movies need to be added to basket before returning. Customer returns the whole basket at once.
     * Method to add movies to return basket. A new basket will be made is current customer does not have a basket yet.
     */
    public void addMovieToReturnList(Customer customer, Movie movie, int daysRentedFor) {
        try {
            if (inventory.isMovieRentedOut(movie)) {
                if (inventory.isCorrectCustomerReturning(movie, customer)) {
                    if (returnBaskets.containsKey(customer)) {
                        returnBaskets.get(customer).addMovie(movie, daysRentedFor);
                    } else {
                        Basket basket = new Basket(customer);
                        basket.addMovie(movie, daysRentedFor);
                        returnBaskets.put(customer, basket);
                    }
                }
            } else {
                throw new NoSuchMovieException("Movie " + movie.getMovieName() + " is not rented out");
            }
        } catch (NoSuchMovieException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to remove movie from customer's return basket.
     */
    public void removeMovieFromReturnList(Customer customer, Movie movie) {
        if (!returnBaskets.containsKey(customer)) {
            try {
                throw new Exception("Return list for customer " + customer.getCustomerId() + " does not exist");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (!returnBaskets.get(customer).getMovies().containsKey(movie)) {
            try {
                throw new Exception("Movie " + movie.getMovieName() + " not in return list");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            returnBaskets.get(customer).removeMovie(movie);
        }
    }

    /**
     * Return the movies in customer's basket.
     * @return Total late fee.
     */
    public int returnMovies(Customer customer) {
        Basket basket = returnBaskets.get(customer);
        if (basket == null) {
            try {
                throw new Exception("Return list for customer " + customer.getCustomerId() + " does not exist");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return 0;
        } else {
            StringBuilder returnString = new StringBuilder();
            int totalReturnFee = 0;
            HashMap<Movie, Integer> movies = basket.getMovies();
            for (Movie movie : movies.keySet()) {
                int daysRentedFor = movie.getDaysRentedFor();
                int daysReturnedAfter = movies.get(movie);
                int daysLate = daysReturnedAfter - daysRentedFor;
                if (daysLate > 0) {
                    int lateFee = movie.getMovieType().calculateLateFee(daysLate);
                    totalReturnFee += lateFee;
                    returnString.append(movie.getMovieName())
                            .append(" (")
                            .append(movie.getMovieType().getType())
                            .append(") ")
                            .append(daysLate)
                            .append(" extra days ")
                            .append(lateFee)
                            .append(" EUR\n");
                } else {
                    returnString.append(movie.getMovieName())
                            .append(" (")
                            .append(movie.getMovieType().getType())
                            .append(") returned on time\n");
                }
                movie.returnMovie();
                inventory.returnMovie(movie, customer);
            }
            returnString.append("Total late charge: ").append(totalReturnFee).append(" EUR\n");
            System.out.println(returnString.toString());
            returnBaskets.remove(customer);
            return totalReturnFee;
        }
    }

}
