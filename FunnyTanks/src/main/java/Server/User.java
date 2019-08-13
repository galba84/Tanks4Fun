package Server;

import java.util.Random;

public class User {
    public Integer id;

    public User() {
        Random r = new Random(1000);
        id = r.nextInt(1000) + 10;
    }
}
