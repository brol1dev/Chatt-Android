package qcodemx.com.chatt.model;

/**
 * Created by Eric Vargas on 8/9/14.
 *
 * Represents the data for an item in the navigation drawer.
 */
public class DrawerItemModel {

    private String title;
    private int imageResourceId;

    public DrawerItemModel(String title, int imageResourceId) {
        this.title = title;
        this.imageResourceId = imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
