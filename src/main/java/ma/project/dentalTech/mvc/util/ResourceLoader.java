package ma.project.dentalTech.mvc.util;

import java.net.URL;
import javax.swing.ImageIcon;

public final class ResourceLoader {

    private ResourceLoader() {
    }

    public static URL get(String path) {
        String normalized = path.startsWith("/") ? path : "/" + path;
        return ResourceLoader.class.getResource(normalized);
    }

    public static ImageIcon icon(String path) {
        URL url = get(path);
        if (url == null) {
            throw new IllegalArgumentException("Resource not found: " + path);
        }
        return new ImageIcon(url);
    }

    public static ImageIcon iconFromStatic(String relativePath) {
        return icon("/static/" + relativePath);
    }
}
