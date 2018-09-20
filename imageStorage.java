import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Provides methods to facilitate drawing images.
 */
public class imageStorage {
	
	/**
	 * Keep track of pictures that have already been drawn so that we don't have to load them every time.
	 */
	private static Map<String, BufferedImage> cache = new HashMap<String, BufferedImage>();

	public static void draw(Graphics g, String filepath, int x, int y) {
		try {
			BufferedImage img;
			if (cache.containsKey(filepath))
				img = cache.get(filepath);
			else {
				img = ImageIO.read(new File(filepath));
				cache.put(filepath, img);
			}
			g.drawImage(img, x, y, null);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
}