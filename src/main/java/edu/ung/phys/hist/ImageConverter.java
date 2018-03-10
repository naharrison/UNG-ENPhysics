package edu.ung.phys.hist;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.imageio.ImageIO;

import processing.core.PApplet;
import processing.core.PImage;

public class ImageConverter {
	
	public static String imgToBase64String(final RenderedImage img, final String formatName) {
	    final ByteArrayOutputStream os = new ByteArrayOutputStream();
	    try {
	        ImageIO.write(img, formatName, Base64.getEncoder().wrap(os));
	        return os.toString(StandardCharsets.ISO_8859_1.name());
	    } catch (final IOException ioe) {
	        throw new UncheckedIOException(ioe);
	    }
	}


	public static BufferedImage base64StringToBufferedImage(final String base64String) {
	    try {
	        return ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(base64String)));
	    } catch (final IOException ioe) {
	        throw new UncheckedIOException(ioe);
	    }
	}
	
	
	public static PImage bufferedImageToPImage(final BufferedImage bimage) {
		PImage pimg;
		PApplet papp = new PApplet();
		pimg = papp.createImage(bimage.getWidth(), bimage.getHeight(), PApplet.RGB);
		pimg.loadPixels();
		for(int x = 0; x < pimg.width; x++) {
			for(int y = 0; y < pimg.height; y++) {
				pimg.set(x, y, bimage.getRGB(x, y));
			}
		}
		return pimg;
	}
	
	
	public static PImage base64StringToPImage(final String base64String) {
		BufferedImage bimage = ImageConverter.base64StringToBufferedImage(base64String);
		return ImageConverter.bufferedImageToPImage(bimage);
	}


}

