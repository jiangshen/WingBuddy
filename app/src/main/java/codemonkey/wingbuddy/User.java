package codemonkey.wingbuddy;

import java.util.Map;

/**
 * Created by Deb Banerji on 09-Apr-16.
 */
public class User {
    private String userName;
    private String phoneNumber;
    private String roommatePhoneNumber;

    private long wingPoints;

    public User(String userName, String phoneNumber, String roommatePhoneNumber) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.roommatePhoneNumber = roommatePhoneNumber;
        wingPoints = 0;
    }

    public User(Map map) {
        this.userName = ((String) map.get("name"));
        this.phoneNumber = ((String) map.get("phoneNumber"));
        this.wingPoints = ((long) map.get("wingPoints"));
        this.roommatePhoneNumber = ((String) map.get("roommatePhoneNumber"));
    }

    public String getName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRoommatePhoneNumber() {
        return roommatePhoneNumber;
    }

    public long getWingPoints() {
        return wingPoints;
    }

    public long increaseWingPoints() {
        wingPoints++;
        return wingPoints;
    }

    public long increaseWingPoints(int num) {
        wingPoints+=num;
        return wingPoints;
    }

    public long decreaseWingPoints() {
        wingPoints--;
        return wingPoints;
    }

    public long decreaseWingPoints(int num) {
        wingPoints-=num;
        return wingPoints;
    }
}
