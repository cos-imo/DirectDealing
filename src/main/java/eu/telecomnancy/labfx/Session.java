package eu.telecomnancy.labfx;

public class Session {
    private static Session instance ;
    private User currentUser;

    private Session(){} //On ne veut pas l'instancier
    public static synchronized Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
