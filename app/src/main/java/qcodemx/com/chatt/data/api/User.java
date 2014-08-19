package qcodemx.com.chatt.data.api;

/**
 * Created by Eric Vargas on 8/6/14.
 *
 * Represents a User authenticated in the server.
 */
public class User {
    protected String id;
    protected String name;
    protected String email;
    protected Long iat;              // issued at
    protected Long exp;              // expiration

    public User(String email) {
        this.email = email;
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(String id, String name, String email, Long iat, Long exp) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.iat = iat;
        this.exp = exp;
    }

    public User(String id, String email, Long iat, Long exp) {
        this.id = id;
        this.email = email;
        this.iat = iat;
        this.exp = exp;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getIat() {
        return iat;
    }

    public Long getExp() {
        return exp;
    }

    public static class UserCredentials {
        protected String email;
        protected String password;

        public UserCredentials(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}
