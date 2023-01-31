package org.agicam.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import ij.process.ImageStatistics;
import org.agicam.processor.util.Couple;
import org.agicam.processor.util.Plot;

public class NDVIProcessor {

    public void calculateNDVI(File image, List<Plot> plots) {
        ImagePlus img = IJ.openImage(image.getPath());
        int w = img.getWidth();
        int h = img.getHeight();

        ImageProcessor ip = img.getProcessor();

        // Red, Green, Blue
        ImageProcessor red = ip.convertToFloatProcessor();
        ImageProcessor green = ip.convertToFloatProcessor();
        ImageProcessor blue = ip.convertToFloatProcessor();


        // Create a new float processor for the NDVI
        FloatProcessor ndvi = new FloatProcessor(w, h);

        // Calculate the NDVI
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                float r = red.getf(x, y);
                float g = green.getf(x, y);
                float b = blue.getf(x, y);
                ndvi.setf(x, y, (1.664f * b) / (0.953f * r) - 1);
            }
        }

        // Create a new image from the NDVI data
        ImagePlus ndviImp = new ImagePlus("NDVI", ndvi);
        IJ.run(ndviImp, "Apply LUT", "red=0 green=0 blue=0"); // Mask the image

        // Calculate statistics for each plot
        for (int i = 0; i < plots.size(); i++) {
            Plot plot = plots.get(i);
            int width = plot.getxPoints().getItemTwo() - plot.getxPoints().getItemOne();
            int height = plot.getyPoints().getItemTwo() - plot.getyPoints().getItemOne();

            ndviImp.setRoi(plot.getxPoints().getItemOne(), plot.getyPoints().getItemOne(), width, height );
            ImageStatistics stats = ImageStatistics.getStatistics(ndviImp.getProcessor(), ImageStatistics.MEAN, null);
            double mean = stats.mean;



            stats = ImageStatistics.getStatistics(ndviImp.getProcessor(), ImageStatistics.MEDIAN, null);
            double median = stats.median;
            stats = ImageStatistics.getStatistics(ndviImp.getProcessor(), ImageStatistics.STD_DEV, null);
            double std = stats.stdDev;
            stats = ImageStatistics.getStatistics(ndviImp.getProcessor(), ImageStatistics.MIN_MAX, null);
            double max = stats.max;
            double min = stats.min;

            System.out.println("Plot Mean {" + i + "} = " + mean);
            System.out.println("Plot Median {" + i + "} = " + median);
            System.out.println("Plot Max {" + i + "} = " + max);
            System.out.println("Plot Min {" + i + "} = " + min);
        }
    }
}
