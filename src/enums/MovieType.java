package enums;


public enum MovieType {
    NEW_RELEASE("NEW_RELEASE"),
    REGULAR_FILM("REGULAR_FILM"),
    OLD_FILM("OLD_FILM");
    private String type;

    MovieType(String type) { this.type = type; }

    public String getType() {
        return type;
    }

    public int calculatePrice(int numberOfDays) {
        switch (this) {
            case NEW_RELEASE:
                return MoviePrices.PREMIUM_PRICE.getPrice() * numberOfDays;
            case REGULAR_FILM:
                if (numberOfDays > 3) {
                    return MoviePrices.BASIC_PRICE.getPrice() * (numberOfDays - 2);
                } else {
                    return MoviePrices.BASIC_PRICE.getPrice();
                }
            case OLD_FILM:
                if (numberOfDays > 5) {
                    return MoviePrices.BASIC_PRICE.getPrice() * (numberOfDays - 4);
                } else {
                    return MoviePrices.BASIC_PRICE.getPrice();
                }
            default:
                throw new AssertionError("Incorrect operation");
        }
    }

    public int calculateLateFee(int numberOfDays) {
        switch (this) {
            case NEW_RELEASE:
                return MoviePrices.PREMIUM_PRICE.getPrice() * numberOfDays;
            case REGULAR_FILM:
                return MoviePrices.BASIC_PRICE.getPrice() * numberOfDays;
            case OLD_FILM:
                return MoviePrices.BASIC_PRICE.getPrice() * numberOfDays;
            default:
                throw new AssertionError("Incorrect operation");
        }
    }
}
