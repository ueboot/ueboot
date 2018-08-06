import java.net.URL;
import java.net.URLClassLoader;

public class Test {
    private static String separator = "/";

    public static void main(String[] args) {
        String clazzName = "com.ueboot.project.entity.role.Permission";
        String b = clazzName.replaceAll("\\.", "/");
        System.out.println(b);

        try {

            String clazzNameFilePath = clazzName.replaceAll("\\.", separator);
            clazzNameFilePath = clazzNameFilePath.substring(0,clazzNameFilePath.lastIndexOf("/")+1);
            String entityFilePath = "/Users/yangkui/workspace/pddsd" + separator + "entity" + separator + "target" + separator + "classes" + separator ;
            System.out.println(entityFilePath);
            URL[] urls = {new URL("file:" + entityFilePath)};
            URLClassLoader cl = new URLClassLoader(urls);
            Class clz = Class.forName(clazzName, true, cl);
            System.out.println(clz.toGenericString());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
