package com.ueboot.generator;

import jodd.datetime.JDateTime;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 代码自动生成工具类
 *
 * @author yangkui
 * @version 2.0
 */
@Data
public class CodeGenerator {
    private static String separator = System.getProperty("file.separator");

    private final String USER = System.getenv("USER");
    private String projectPah = "";
    private final boolean SAVA_FILE = true;//是否生成文件


    /**
     * 初始化一些属性
     */
    public void initProperties() {
        String classPath = com.ueboot.generator.CodeGenerator.class.getClassLoader().getResource("").getPath();
        classPath = classPath.substring(0, classPath.indexOf(separator + "target" + separator + "classes"));
        classPath = classPath.substring(0, classPath.lastIndexOf(separator));
        if (!classPath.endsWith(separator)) {
            classPath += separator;
        }
        this.projectPah = classPath;
    }

    private void log(String message) {
        System.out.println(message);
    }

    /**
     * 生成repository及repositoryImpl类
     * @param clz 实体类
     * @param repositoryPackageName repository包名路径
     * @param repositoryModuleName repository模块名称
     */
    public void createRepository(Class<?> clz, String repositoryPackageName, String repositoryModuleName) {
        log("repository类生成包名:" + repositoryPackageName);
        VelocityContext context = getBaseContext(clz);
        String entityClassName = (String) context.get("entityName");
        context.put("repositoryPackageName", repositoryPackageName);
        String java = getTemplateString("repository.vm", context);
        log("repository文件内容：" + java);
        String filepath = repositoryPackageName.replace(".", separator);
        String filePath = projectPah + repositoryModuleName + separator + "src" + separator + "main" + separator + "java" + separator + filepath + separator + entityClassName + "Repository.java";
        log("repository文件保存路径：" + filePath);
        saveFile(filePath, java);
    }


    /**
     * 生成service及serviceImpl类
     * @param clz 实体类
     * @param servicePackageName service类包名路径
     * @param serviceModuleName  service类模块名称
     * @param repositoryPackageName repository类包名路径
     */
    public void createService(Class<?> clz, String servicePackageName, String serviceModuleName, String repositoryPackageName) {
        log("service类生成包名:" + servicePackageName);
        VelocityContext context = getBaseContext(clz);
        String entityClassName = (String) context.get("entityName");
        context.put("servicePackageName", servicePackageName);
        String java = getTemplateString("service.vm", context);
        log("service文件内容：" + java);
        context.put("repositoryPackageName", repositoryPackageName);

        String java2 = getTemplateString("serviceImpl.vm", context);
        log("serviceImpl文件内容：" + java2);

        String filepath = servicePackageName.replace(".", separator);

        String interfacefilepath = projectPah + serviceModuleName + separator + "src" + separator + "main" + separator + "java" + separator + filepath + separator + entityClassName + "Service.java";
        String implfilepath = projectPah + serviceModuleName + separator + "src" + separator + "main" + separator + "java" + separator + filepath + separator + "impl" + separator + entityClassName + "ServiceImpl.java";

        log("service文件保存路径：" + interfacefilepath);
        log("serviceImpl文件保存路径：" + implfilepath);

        saveFile(interfacefilepath, java);
        saveFile(implfilepath, java2);
    }

    /**
     * 生成controller类
     * @param clz 实体类
     * @param controllerPackageName controller类包名路径
     * @param servicePackageName service类包名路径
     * @param controllerModuleName controller模块名称
     */
    public void createController(Class<?> clz, String controllerPackageName, String servicePackageName, String controllerModuleName) {
        createVO(clz, controllerModuleName, controllerPackageName);

        log("controller类生成包名:" + controllerPackageName);

        VelocityContext context = getBaseContext(clz);
        String entityClassName = (String) context.get("entityName");

        context.put("controllerPackageName", controllerPackageName);
        context.put("servicePackageName", servicePackageName);

        String java = getTemplateString("controller.vm", context);
        log("controller文件内容：" + java);
        String filepath = controllerPackageName.replace(".", separator);

        String controllerfilepath = projectPah + controllerModuleName + separator + "src" + separator + "main" + separator + "java" + separator + filepath + separator + entityClassName + "Controller.java";
        log("controller文件保存路径：" + controllerfilepath);
        saveFile(controllerfilepath, java);
    }

    private void createVO(Class<?> clz, String controllerModuleName, String controllerPackageName) {

        String voPackageName = controllerPackageName + ".vo";
        String filepath = voPackageName.replace(".", "/");

        VelocityContext context = getBaseContext(clz);
        context.put("controllerPackageName", controllerPackageName);

        try {
            List<ObjectField> fields = getObjectValue(clz);
            context.put("fields", fields);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> transCodes = new HashMap<String, String>();
        transCodes.put("FindReq", "");
        transCodes.put("Resp", "");
        transCodes.put("Req", "");

        for (Map.Entry<String, String> entry : transCodes.entrySet()) {
            String key = entry.getKey();
            String java = getTemplateString(key + ".vm", context);
            log(key + "文件内容：" + java);
            String reqFilePath = projectPah + controllerModuleName + separator + "src" + separator + "main" + separator + "java" + separator + filepath + separator + context.get("entityName") + key + ".java";
            log(key + "文件保存路径：" + reqFilePath);
            saveFile(reqFilePath, java);

        }
    }

    //生成VUE页面
    public void createPages(Class<?> clz, String vuePageModuleName, String vueFilePath, String requestPath) {
        log("pagePath:" + vueFilePath);

        VelocityContext context = getBaseContext(clz);
        String entityClassName = (String) context.get("entityName");

        try {
            List<ObjectField> fields = getObjectValue(clz);
            context.put("fields", fields);
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.put("requestPath", requestPath);

        String js = getTemplateString("vue.vm", context);
        log("vue 页面文件内容：" + js);
        String jsFilePath = projectPah + vuePageModuleName + separator + vueFilePath + separator + entityClassName + ".vue";
        log("vueFilePath文件保存路径：" + jsFilePath);
        saveFile(jsFilePath, js);

    }


    private void saveFile(String fileName, String fileContent) {
        if (!SAVA_FILE) {
            return;
        }
        try {
            File file = new File(fileName);
            if (file.exists()) {
                FileUtils.forceDelete(file);
            }
            FileUtils.write(file, fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private VelocityContext getBaseContext(Class<?> clz) {
        VelocityContext context = new VelocityContext();
        String entity_package_name = clz.getPackage().getName();
        context.put("entityPackageName", entity_package_name);
        context.put("YEAR", Calendar.getInstance().get(Calendar.YEAR));
        context.put("entityFullName", clz.getSimpleName());
        String simpleName = clz.getSimpleName();
        if (simpleName.endsWith("Entity")) {
            simpleName = simpleName.replace("Entity", "");
        }
        context.put("entityName", simpleName);
        context.put("lowEntityName", getFirstLow(simpleName));
        context.put("USER", USER);
        JDateTime dateTime = new JDateTime();
        context.put("DATE", dateTime.toString("YYYY-MM-DD hh:mm:ss"));
        return context;
    }

    private List<ObjectField> getObjectValue(Class<?> clz) throws Exception {
        clz.getPackage().getName();
        Field[] fields = clz.getDeclaredFields();
        List<ObjectField> list = new ArrayList<ObjectField>();
        for (Field field : fields) {
            Class type = field.getType();
            ObjectField field1 = new ObjectField();
            field1.setName(field.getName());
            String typeName = field.getType().toString();
            if (!typeName.contains("java")) {
                continue;
            }
            field1.setType(field.getType().toString());
            field1.setUpperName(getMethodName(field.getName()));
            log(field.getName() + ":" + type.toString());
            list.add(field1);
        }
        return list;
    }

    /**
     * 把一个字符串的第一个字母大写、效率是最高的、
     * @param fieldName 字段名称
     * @return
     */
    private String getMethodName(String fieldName) {
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    /**
     * 获取第一个字符并小写
     * @param fieldName 字段名称
     * @return
     */
    private String getFirstLow(String fieldName) {
        String f = fieldName.substring(0, 1);
        String l = fieldName.substring(1);
        return new String(f.toLowerCase() + l);
    }

    private String getTemplateString(String templateName, VelocityContext context) {
        Properties p = new Properties();
        String path = Thread.currentThread().getContextClassLoader().getResource("velocity").getFile();
        p.setProperty(Velocity.RESOURCE_LOADER, "class");
        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
        p.setProperty("class.resource.loader.path", path);
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        Velocity.init(p);

        Template template = Velocity.getTemplate("velocity" + separator + "ueboot" + separator + templateName, "UTF-8");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

}
