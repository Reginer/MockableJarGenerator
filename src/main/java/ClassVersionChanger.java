import org.objectweb.asm.*;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;


/**
 * 运行这个main方法可以将android.jar转成jdk8的jar包
 */
public class ClassVersionChanger {
    public static void main(String[] args) {
        String inputJarPath = "D:\\Personal\\Desktop\\android.jar";
        String outputJarPath = "D:\\Personal\\Desktop\\mockable_android.jar";
        int targetMajorVersion = 52;

        try (JarFile jarFile = new JarFile(inputJarPath);
             JarOutputStream jos = new JarOutputStream(new FileOutputStream(outputJarPath))) {

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    ClassReader classReader = new ClassReader(jarFile.getInputStream(entry));
                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                    classReader.accept(new ClassVisitor(Opcodes.ASM9, classWriter) {
                        @Override
                        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                            super.visit(targetMajorVersion, access, name, signature, superName, interfaces);
                        }
                    }, 0);
                    byte[] bytes = classWriter.toByteArray();

                    JarEntry newEntry = new JarEntry(entry.getName());
                    jos.putNextEntry(newEntry);
                    jos.write(bytes);
                    jos.closeEntry();
                } else {
                    copyNonClassFile(jarFile, entry, jos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyNonClassFile(JarFile jarFile, JarEntry entry, JarOutputStream jos) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        JarEntry newEntry = new JarEntry(entry.getName());
        jos.putNextEntry(newEntry);
        try (InputStream is = jarFile.getInputStream(entry)) {
            while ((bytesRead = is.read(buffer)) != -1) {
                jos.write(buffer, 0, bytesRead);
            }
        } finally {
            jos.closeEntry();
        }
    }
}