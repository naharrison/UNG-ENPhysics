package edu.ung.phys.plot;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import edu.ung.phys.graphics.ComponentImageCapture;
import edu.ung.phys.graphics.ImageConverter;
import processing.core.PImage;

public abstract class Plot {
	
	abstract JFrame getJFrame(int width, int height);

	public JFrame getJFrame() {
		return this.getJFrame(400, 260);
	}
	
	
	public BufferedImage getBufferedImage() {
		return this.getBufferedImage(400, 260);
	}

	public BufferedImage getBufferedImage(int width, int height) {
		JFrame frame = this.getJFrame(width, height);
		frame.setVisible(true);
		BufferedImage result = ComponentImageCapture.getScreenShot(frame);
		frame.setVisible(false);
		frame.dispose();
        return result;
	}
	
	
	public String getBase64ImageString() {
		return this.getBase64ImageString(400, 260);
	}

	public String getBase64ImageString(int width, int height) {
        return ImageConverter.imgToBase64String(this.getBufferedImage(width, height), "png");
        //return "data:image/png;base64," + ImageConverter.imgToBase64String(this.getBufferedImage(width, height), "png");
	}
	
	
	public PImage getPImage() {
		return this.getPImage(400, 260);
	}
	
	public PImage getPImage(int width, int height) {
		return ImageConverter.bufferedImageToPImage(this.getBufferedImage(width, height));
	}


}
