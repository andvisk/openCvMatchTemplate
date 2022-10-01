package matchtemplate;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.opencv.core.Core;

public class App {

    private static Logger log = LogManager.getLogger(App.class);

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Configurator.initialize(null, System.getProperty("user.dir") + File.separator
                + "src/main/resources/log4j2.properties");

        ImgProc.matchTemplate();
    }

}