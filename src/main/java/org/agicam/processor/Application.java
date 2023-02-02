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

        File image = new File("./sensorNDVI.png");
        List<Plot> plots = new ArrayList<>();

        Plot inWheat = new Plot(56, 164, 73, 179);
        Plot inLane = new Plot(261 , 321, 62, 177);
        Plot dark = new Plot(615 , 612, 5, 12);
        Plot bigPlot = new Plot(413, 535, 53, 256);

        // Plots for sensor image
        Plot healthiest = new Plot(1953, 2079, 282 ,339 );
        Plot healthy = new Plot(1495, 1857, 569, 933 );
        Plot unhealthy = new Plot(2033, 2297, 511, 865 );
        Plot road = new Plot(1939, 2015, 1118,1220 );

//        plots.add(inWheat);
//        plots.add(inLane);
//        plots.add(dark);
//        plots.add(bigPlot);
        plots.add(healthiest);
        plots.add(healthy);
        plots.add(unhealthy);
        plots.add(road);

        NDVIProcessor ndviProcessor = new NDVIProcessor();
        ndviProcessor.calculateNDVI(image, plots);
    }

}
