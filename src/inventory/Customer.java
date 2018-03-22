package inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Customer {

    private int customerId;
    private int bonusPoints = 0;

    public Customer() {
        this.customerId = new Random().nextInt(Integer.MAX_VALUE);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void addBonusPoints(int bonusPoints) {
        this.bonusPoints += bonusPoints;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void removeBonusPoints(int points) {
        this.bonusPoints -= points;
    }
}
