package hackaton.pethelp2.datasource.models;

/**
 * Created by dmitriyt on 24.03.18.
 */

public class Event {
    private Integer id;
    private double latitude;
    private double longitude;
    private Pet pet;
    private String description;
    private ImageMy image;

    public Event() {
    }

    public Event(double latitude, double longitude, Pet pet, String description, ImageMy image) {
        this.id = null;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pet = pet;
        this.description = description;
        this.image = image;
    }



    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", petType=" + pet +
                ", description='" + description + '\'' +
                ", image=" + image +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageMy getImage() {
        return image;
    }

    public void setImage(ImageMy image) {
        this.image = image;
    }
}
