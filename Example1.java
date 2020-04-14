import java.util.*;
import java.awt.*;
import javax.swing.*;
import org.jfree.chart.*;
// import org.jfree.chart.ChartPanel;
// import org.jfree.chart.ChartFactory;
// import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.*;
import org.jfree.chart.axis.*;

public class Example1 implements Runnable {

    public static void main(String[] args) {
        Example1 example = new Example1();
        SwingUtilities.invokeLater(example);
    }

    public void run()
    {
        String playResults = "Play Results";
        DefaultCategoryDataset data = new DefaultCategoryDataset();

        TreeMap<Integer, Float> sortedMap = new TreeMap<>();
        sortedMap.putAll(playMap);
        for (Map.Entry<Integer,Float> entry: sortedMap.entrySet()) {
            data.addValue(entry.getValue(), playResults, entry.getKey());  //(y,x)
            // System.out.println("Adding key: " + entry.getKey());
        }

        JFreeChart chart = ChartFactory.createLineChart(
            "Play Results", "Distance", "Possibility",
            data, PlotOrientation.VERTICAL,
            true, true, true
        );

        // set the range for the y-axis
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setRange(0, 15);

        JFrame frame = new JFrame("Data");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setContentPane(new ChartPanel(chart));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public static final Map<Integer, Float> playMap = new HashMap<Integer, Float>() {{
        put(10,  1f);
        put(12,  2f);
        put(15,  4f);
        put(18,  6f);
        put(20,  6f);
        put(25,  7f);
        put(30,  6f);
        put(35,  5f);
        put(40,  5f);
    }};

}
