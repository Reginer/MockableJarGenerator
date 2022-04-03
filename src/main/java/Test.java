import com.android.builder.testing.MockableJarGenerator;

import java.io.File;
import java.io.IOException;

/**
 * @author :Reginer in  2020/11/19 17:42.
 * 联系方式:QQ:282921012
 * 功能描述:MockableJarGenerator
 */
public class Test {
    //这里模拟AS的报错,当修复之后之后,用class替换
    public static void main(String[] args) {
        File input = new File("/Users/wujieyuan/Downloads/android.jar");
        File outputFile = new File("/Users/wujieyuan/Downloads/mockable_android.jar");
        outputFile.delete();

        MockableJarGenerator generator = new MockableJarGenerator(true);
        try {
            generator.createMockableJar(input, outputFile);
        } catch (IOException e) {
            throw new RuntimeException("Cannot create mockable android.jar", e);
        }
    }
}
