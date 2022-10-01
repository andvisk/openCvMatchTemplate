package matchtemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;

public class ImgProcTest {

    @BeforeEach
    void loadOpenCvLib(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    @Test
    void matchTemplateTest(){
        Map<Integer, Integer> map = ImgProc.matchTemplate();
        assertEquals(35, map.get(1));
        assertEquals(23, map.get(2));
        assertEquals(6, map.get(3));
    }
}
