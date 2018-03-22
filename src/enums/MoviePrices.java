package enums;


public enum MoviePrices {
    PREMIUM_PRICE(4), BASIC_PRICE(3);
    private int price;

    MoviePrices(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}