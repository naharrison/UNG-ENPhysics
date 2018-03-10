package edu.ung.phys.plot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;

/**
 * @author naharrison
 * Graphical 1-D histogram
 */
public class Histogram1D {
	
	public ArrayList<Double> binLimits;
	public ArrayList<Integer> counts;
	

	public Histogram1D(ArrayList<Double> binLimits, ArrayList<Integer> counts) {
		this.binLimits = binLimits;
		this.counts = counts;
	}
	

	public JFrame getJFrame(int width, int height) {
		JFrame result = new JFrame();
        result.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        result.setSize(width, height);

        @SuppressWarnings("unchecked")
        DataTable data = new DataTable(Double.class, Integer.class);

		for(int k = 0; k < counts.size(); k++) {
			double binCenter = binLimits.get(k) + 0.5*(binLimits.get(k+1) - binLimits.get(k));
			data.add(binCenter, counts.get(k));
			System.out.println(binCenter + " " + counts.get(k));
		}

		BarPlot plot = new BarPlot(data);
		plot.setInsets(new Insets2D.Double(20.0, 65.0, 50.0, 40.0));
		plot.setBarWidth((binLimits.get(binLimits.size()-1) - binLimits.get(0))/counts.size());
		plot.getTitle().setText("not so crappy plot");
		int countsMin = Collections.min(counts);
		int yMin = 0;
		if(countsMin < 0) yMin = countsMin;
		plot.getAxis(BarPlot.AXIS_Y).setRange(yMin, 1.15*Collections.max(counts));
		plot.getAxis(BarPlot.AXIS_X).setRange(binLimits.get(0), binLimits.get(binLimits.size()-1));
		PointRenderer barRenderer = plot.getPointRenderers(data).get(0);
		barRenderer.setColor(GraphicsUtils.deriveWithAlpha(new Color(25, 100, 200), 128));
		barRenderer.setValueVisible(true);

        result.getContentPane().add(new InteractivePanel(plot));
        
		return result;
	}

	
	public static void main(String[] args) {
		ArrayList<Double> lims = new ArrayList<>();
		lims.add(0.0);
		lims.add(0.5);
		lims.add(1.0);
		lims.add(1.5);
		ArrayList<Integer> counts = new ArrayList<>();
		counts.add(20);
		counts.add(55);
		counts.add(45);
		
		Histogram1D hh = new Histogram1D(lims, counts);
		JFrame fr = hh.getJFrame(600, 400);
		fr.setVisible(true);
	}

}

