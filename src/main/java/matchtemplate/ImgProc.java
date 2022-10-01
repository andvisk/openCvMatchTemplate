package matchtemplate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImgProc {

    private static Logger log = LogManager.getLogger(ImgProc.class);

    public static Map<Integer, Integer> matchTemplate() {

        Map<Integer, Integer> mapRet = new HashMap<>();

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat mainImg = Imgcodecs.imread(System.getProperty("user.dir") + File.separator
                + "src/test/resources/imgIn1.png");

        mainImg.convertTo(mainImg, CvType.CV_8U);
        Imgproc.cvtColor(mainImg, mainImg, Imgproc.COLOR_BGR2BGRA);

        Mat srcForOutput = mainImg.clone();

        for (int i = 10; i >= 0; i--) {
            int matches = 0;
            Mat templateImg = Imgcodecs
                    .imread(System.getProperty("user.dir") + File.separator + "src/main/resources/" + i + ".png");

            templateImg.convertTo(templateImg, CvType.CV_8U);

            Imgproc.cvtColor(templateImg, templateImg, Imgproc.COLOR_BGR2BGRA);

            Mat contoursMat = new Mat();
            Imgproc.matchTemplate(mainImg, templateImg, contoursMat, Imgproc.TM_CCOEFF_NORMED);

            Core.MinMaxLocResult mmr = null;

            while (true) {
                mmr = Core.minMaxLoc(contoursMat);
                if (mmr.maxVal >= 0.80) {
                    ++matches;
                    Rect rect = new Rect(
                            new Point(mmr.maxLoc.x, mmr.maxLoc.y),
                            new Point(mmr.maxLoc.x + templateImg.cols(), mmr.maxLoc.y + templateImg.rows()));

                    Imgproc.rectangle(srcForOutput, rect, new Scalar(0, 255, 0, 255));

                    Imgproc.circle(contoursMat, new Point(mmr.maxLoc.x, mmr.maxLoc.y),
                            (templateImg.cols() + templateImg.rows()) / 3,
                            new Scalar(0, 0, 0), -1);

                } else {
                    break;
                }
            }

            mapRet.put(i, matches);

            log.info(i + " found - " + matches);

            matches = 0;
        }

        return mapRet;
    }
}
