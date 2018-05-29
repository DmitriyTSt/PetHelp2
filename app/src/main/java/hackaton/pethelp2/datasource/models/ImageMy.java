package hackaton.pethelp2.datasource.models;

/**
 * Created by dmitriyt on 24.03.18.
 */

public class ImageMy {
    private String path;

    public ImageMy(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ImageMy{" +
                "path='" + path + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }
}
