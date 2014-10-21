package qcodemx.com.chatt.model;

/**
 * Created by Eric Vargas on 10/18/14.
 *
 * User entity in the app.
 */
public class User {

    private String id;
    private String deviceId;
    private String name;
    private String email;
    private String imageUrl;
    private String token;
    private Long issuedAt;
    private Long expiration;

    public User(String id, String deviceId, String name, String email, String imageUrl, String token) {
        this.id = id;
        this.deviceId = deviceId;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.token = token;
    }

    public String getDeviceId() {
        return deviceId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getToken() {
        return token;
    }

    public Long getIssuedAt() {
        return issuedAt;
    }

    public Long getExpiration() {
        return expiration;
    }
}
