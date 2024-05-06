package game;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Class for loading assets
 */
public class AssetsLoader {

    private static final HashMap<String, Image> imgCache = new HashMap<>();//hashmap to store images to avoid repeat loading
    private static final HashMap<String, Image[]> animationCache = new HashMap<>();//hashmap to store images to avoid repeat loading

    /**This class is for getting images from directory
     * @param path
     * @return image that being gotten from the path
     */
    public static Image getImage(String path) {
        if (imgCache.containsKey(path)) {//check if it already exists in the map
            return imgCache.get(path);
        }
        Image img = null;//create an image to store the image that needs to be gotten
        try {
             img = ImageIO.read(new File("res/" + path));//assign the target image to img
        } catch (IOException e) {
            System.err.println("Unable to load image from: " + path);
        }
        imgCache.put(path, img);//add it to the hashmap
        return img;
    }

    /**This class is for getting gifs from directory
     * @param path the path for the purpose gif
     * @return image[] array that stores a series of images in the gif
     */
    public static Image[] getGIF(String path) {

        //From online resources and modified by Alex
        path = "res/" + path;
        if (animationCache.containsKey(path)) {
            return animationCache.get(path);
        }
        try {
            String[] imageatt = new String[]{
                    "imageLeftPosition",
                    "imageTopPosition",
                    "imageWidth",
                    "imageHeight"
            };

            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            ImageInputStream ciis = ImageIO.createImageInputStream(new File(path));
            reader.setInput(ciis, false);

            int noi = reader.getNumImages(true);
            BufferedImage master = null;

            Image[] frames = new Image[noi];

            for (int i = 0; i < noi; i++) {
                BufferedImage image = reader.read(i);
                IIOMetadata metadata = reader.getImageMetadata(i);

                Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
                NodeList children = tree.getChildNodes();

                for (int j = 0; j < children.getLength(); j++) {
                    Node nodeItem = children.item(j);

                    if(nodeItem.getNodeName().equals("ImageDescriptor")){
                        HashMap<String, Integer> imageAttr = new HashMap<>();

                        for (String s : imageatt) {
                            NamedNodeMap attr = nodeItem.getAttributes();
                            Node attnode = attr.getNamedItem(s);
                            imageAttr.put(s, Integer.valueOf(attnode.getNodeValue()));
                        }
                        if(i==0){
                            master = new BufferedImage(imageAttr.get("imageWidth"), imageAttr.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);
                        } else {
                            master = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);
                        }

                        master.getGraphics().drawImage(image, imageAttr.get("imageLeftPosition"), imageAttr.get("imageTopPosition"), null);
                    }
                }
                frames[i] = master;
            }
            animationCache.put(path, frames);
        } catch (Exception e) {
            System.err.println("Failed to load GIF from " + path);
            e.printStackTrace();
            animationCache.put(path, null);
        }
        return animationCache.get(path);
    }
}
