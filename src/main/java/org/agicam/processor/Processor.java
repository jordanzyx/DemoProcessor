package org.agicam.processor;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.RGBStackSplitter;
import ij.process.ImageProcessor;
import org.agicam.processor.util.Plot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Created By: Jordan M
 * Description:
 */
public class Processor {

    /**
     * Calculates the NDVI for the plots within an image
     * @param image to process
     * @param plots to process ndbi within
     * @return Average NDVI value for image.
     */
    public double calculateNDVI(File image, List<Plot> plots){
        ImagePlus imPlus = IJ.openImage(image.getPath());

        // Split the channels on the image
        RGBStackSplitter splitter = new RGBStackSplitter();
        splitter.split(imPlus.getStack(), true);

        ImagePlus redChannel = new ImagePlus("Red", splitter.red);
        ImagePlus noirChannel = new ImagePlus("NIR", splitter.green);
        noirChannel.show();
        redChannel.show();

        double sum = plots.stream().mapToDouble(plot -> processPlot(redChannel, noirChannel, plot)).sum();
        return sum / plots.size();
    }


    public double processPlot(ImagePlus redImage, ImagePlus nirImage, Plot plot){
        ImageProcessor redProcessor = redImage.getProcessor();
        ImageProcessor nirProcessor = nirImage.getProcessor();

        double sumNDVI = 0D;
        double pixels = 0D;
        for (int x = plot.getxPoints().getItemOne(); x <= plot.getxPoints().getItemTwo(); x++) {
            for (int y = plot.getyPoints().getItemOne(); y <= plot.getyPoints().getItemTwo(); y++) {
                float red = redProcessor.getPixelValue(x,y) / 255.0F;
                float nir = nirProcessor.getPixelValue(x,y) / 255.0F;
                float ndvi = (nir - red) / (nir + red);
                sumNDVI += ndvi;
                pixels++;
            }
        }
        System.out.println("Plot with NDVI = " + (sumNDVI / pixels));
        return sumNDVI/pixels;
    }
}
