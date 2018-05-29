package hackaton.pethelp2.datasource.models;

/**
 * Created by dmitriyt on 24.03.18.
 */

public class Pet {
    private int id;
    private String name;
    private ImageMy image;

    public Pet() {
    }

    public Pet(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name=" + name +
                ", image=" + image +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageMy getImage() {
        return image;
    }

    public void setImage(ImageMy image) {
        this.image = image;
    }
}
