package edu.ung.phys.plot;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import edu.ung.phys.graphics.ComponentImageCapture;
import edu.ung.phys.graphics.ImageConverter;

public class Function1D {
	
	public Expression expr;
	public double min, max;
	public int nSteps;
	

	public Function1D(String exprString, double min, double max) {
		expr = new ExpressionBuilder(exprString)
				.variables("x")
				.build();
		this.min = min;
		this.max = max;
		this.nSteps = 100;
	}
	
	
	public double evaluate(double x) {
		expr.setVariable("x", x);
		return expr.evaluate();
	}
	
	
	public JFrame getJFrame() {
		return this.getJFrame(400, 260);
	}
	
	public JFrame getJFrame(int width, int height) {
		JFrame result = new JFrame();
        result.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        result.setSize(width, height);
		@SuppressWarnings("unchecked")
        DataTable data = new DataTable(Double.class, Double.class);
        for (double x = min; x <= max; x += (max-min)/nSteps) {
            double y = this.evaluate(x);
            data.add(x, y);
        }
        XYPlot plot = new XYPlot(data);
        result.getContentPane().add(new InteractivePanel(plot));
        LineRenderer lines = new DefaultLineRenderer2D();
        plot.setLineRenderers(data, lines);
        Color color = new Color(0.0f, 0.3f, 1.0f);
        plot.getPointRenderers(data).get(0).setColor(color);
        plot.getLineRenderers(data).get(0).setColor(color);
		return result;
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
	

}

