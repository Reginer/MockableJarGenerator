import java.io.File;
import java.io.IOException;

/**
 * @author :Reginer in  2020/11/19 17:42.
 * 联系方式:QQ:282921012
 * 功能描述:MockableJarGenerator
 */
public class Test {
    public static void main(String[] args) {
        File input = new File("D:\\Personal\\Desktop\\android.jar");
        File outputFile = new File("D:\\Personal\\Desktop\\mockable_android.jar");

        MockableJarGenerator generator = new MockableJarGenerator(true);
        try {
            generator.createMockableJar(input, outputFile);
        } catch (IOException e) {
            throw new RuntimeException("Cannot create mockable android.jar", e);
        }
    }
}
