package codemonkey.wingbuddy;

/**
 * Created by Deb Banerji on 09-Apr-16.
 */
public class User {
    private String userName;
    private String phoneNumber;

    private int wingPoints;

    public User(String userName, String phoneNumber) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        wingPoints = 0;
    }

    public String getName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getWingPoints() {
        return wingPoints;
    }

    public int increaseWingPoints() {
        wingPoints++;
        return wingPoints;
    }

    public int increaseWingPoints(int num) {
        wingPoints+=num;
        return wingPoints;
    }

    public int decreaseWingPoints() {
        wingPoints--;
        return wingPoints;
    }

    public int decreaseWingPoints(int num) {
        wingPoints-=num;
        return wingPoints;
    }
}

