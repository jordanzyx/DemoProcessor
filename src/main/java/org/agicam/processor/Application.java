package org.agicam.processor;

import org.agicam.processor.util.Plot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By: Jordan M
 * Description:
 */
public class Application {

    public static void main(String[] args) {
        System.out.println("Starting Image Processing");

        Processor processor = new Processor();

        File image = new File("./NDVI_Image.png");
        List<Plot> plots = new ArrayList<>();

        Plot inWheat = new Plot(56, 164, 73, 179);
        Plot inLane = new Plot(261 , 321, 62, 177);
        Plot dark = new Plot(615 , 612, 5, 12);
        Plot bigPlot = new Plot(413, 535, 53, 256);

        plots.add(inWheat);
        plots.add(inLane);
        plots.add(dark);
        plots.add(bigPlot);

        NDVIProcessor ndviProcessor = new NDVIProcessor();
        ndviProcessor.calculateNDVI(image, plots);
    }

}
