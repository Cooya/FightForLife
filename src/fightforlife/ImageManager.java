package fightforlife;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import gameframework.core.DrawableImage;

public class ImageManager {
	private static final String[] ORIENTATIONS_ARRAY = {"left", "up", "right", "down"};
	private DrawableImage[] orientationImages;
	
	public ImageManager(Canvas canvas, Image originalImage, String imagesDir, String imageOrientation) throws Exception {
		generateOrientationImages(originalImage, imagesDir, imageOrientation);
		this.orientationImages = loadOrientationImages(canvas, imagesDir);
	}
	
	public void draw(Graphics g, int x, int y, String orientation) throws Exception {
		g.drawImage(this.orientationImages[getOrientationIndex(orientation)].getImage(), x, y, 32, 32, null);
	}
	
	private static void generateOrientationImages(Image originalImage, String imagesDir, String imageOrientation) throws Exception {
		File folder = new File(imagesDir);
		if(!folder.exists())
			folder.mkdir();
		
		
		int orientationIndex = getOrientationIndex(imageOrientation);
		int height = originalImage.getHeight(null);
		int width = originalImage.getWidth(null);
		for(int i = 0; i < 271; i+= 90) {
			AffineTransform trans = new AffineTransform();
			trans.translate(height / 2, width / 2);
			trans.rotate(Math.toRadians(i));
			trans.translate(-width / 2, -height / 2);
			ImageIO.write(toBufferedImage(originalImage, trans), "png", new File(imagesDir + ORIENTATIONS_ARRAY[orientationIndex] + ".png"));
			if(orientationIndex++ >= ORIENTATIONS_ARRAY.length - 1)
				orientationIndex = 0;
		}
	}
	
	private static int getOrientationIndex(String orientation) throws Exception {
		for(int i = 0; i < ORIENTATIONS_ARRAY.length; ++i)
			if(ORIENTATIONS_ARRAY[i].equals(orientation))
				return i;
		throw new Exception("Invalid image orientation : " + orientation + ".");
	}
	
	private static BufferedImage toBufferedImage(Image image, AffineTransform trans) {
	    if(image instanceof BufferedImage)
	        return (BufferedImage) image;
	    BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = bimage.createGraphics();
	    g2d.drawImage(image, trans, null);
	    g2d.dispose();
	    return bimage;
	}
	
	private static DrawableImage[] loadOrientationImages(Canvas canvas, String imagesDir) {
		DrawableImage[] orientationImages = new DrawableImage[ORIENTATIONS_ARRAY.length];
		for(int i = 0; i < orientationImages.length; ++i)
			orientationImages[i] = new DrawableImage(imagesDir + ORIENTATIONS_ARRAY[i] + ".png", canvas);
		return orientationImages;
	}
}