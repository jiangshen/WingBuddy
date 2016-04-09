import java.util.Map;

/**
 * Created by Deb Banerji on 09-Apr-16.
 */
public class User {
    private String userName;
    private String phoneNumber;
    private String roommatePhoneNumber;

    private int wingPoints;

    public User(String userName, String phoneNumber, String roommatePhoneNumber) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.roommatePhoneNumber = roommatePhoneNumber;
        wingPoints = 0;
    }

    public User(Map map) {
        this.userName = ((String) map.get("userName"));
        this.phoneNumber = ((String) map.get("phoneNumber"));
        this.wingPoints = ((int) map.get("wingPoints"));
        this.roommatePhoneNumber = ((String) map.get("roommatePhoneNumber"));
    }

    public String getName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    publicint getRoommatePhoneNumber() {
        return roommatePhoneNumber;
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
