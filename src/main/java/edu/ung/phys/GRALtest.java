package edu.ung.phys;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import edu.ung.phys.hist.ComponentImageCapture;
import edu.ung.phys.hist.ImageConverter;

public class GRALtest extends JFrame {
    public GRALtest() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);

        DataTable data = new DataTable(Double.class, Double.class);
        for (double x = -5.0; x <= 5.0; x+=0.25) {
            double y = 5.0*Math.sin(x);
            data.add(x, y);
        }
        XYPlot plot = new XYPlot(data);
        getContentPane().add(new InteractivePanel(plot));
        LineRenderer lines = new DefaultLineRenderer2D();
        plot.setLineRenderers(data, lines);
        Color color = new Color(0.0f, 0.3f, 1.0f);
        plot.getPointRenderers(data).get(0).setColor(color);
        plot.getLineRenderers(data).get(0).setColor(color);
    }

    public static void main(String[] args) {
        GRALtest frame = new GRALtest();
        frame.setVisible(true);
        
        BufferedImage bi = ComponentImageCapture.getScreenShot(frame);
        String b64s = ImageConverter.imgToBase64String(bi, "png");
        System.out.println(b64s);
    }
}