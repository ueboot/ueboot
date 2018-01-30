package com.ueboot.generator;

import jodd.util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

/**
 * @author yangkui
 * 代码生成器界面
 */
public class GeneratorDialog extends JDialog {
    private static String separator = System.getProperty("file.separator");

    private JPanel contentPane;
    private JTextField entityPackageName;
    private JTextField repositoryPackageName;
    private JTextField servicePackageName;
    private JLabel service;
    private JTextField controllerPackageName;
    private JTextField vueFilePath;
    private JButton btnSave;
    private JButton btnCancel;
    private JTextField entityModuleName;
    private JTextField repositoryModuleName;
    private JTextField controllerModuleName;
    private JTextField vuePageModuleName;
    private JTextField serviceModuleName;
    private JButton btnclean;
    private JTextField requestPath;

    public GeneratorDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSave);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        entityPackageName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            //失去焦点事件
            @Override
            public void focusLost(FocusEvent e) {
                String entityPackageNameValue = entityPackageName.getText();
                if (StringUtil.isNotBlank(entityPackageNameValue)) {
                    entityPackageNameValue = entityPackageNameValue.substring(0, entityPackageNameValue.lastIndexOf("."));
                    if (StringUtil.isBlank(repositoryPackageName.getText())) {
                        repositoryPackageName.setText(entityPackageNameValue.replace("entity", "repository"));
                    }
                    if (StringUtil.isBlank(servicePackageName.getText())) {
                        servicePackageName.setText(entityPackageNameValue.replace("entity", "service"));
                    }
                    if (StringUtil.isBlank(controllerPackageName.getText())) {
                        controllerPackageName.setText(entityPackageNameValue.replace("entity", controllerModuleName.getText() + ".controller"));
                    }
                    if (StringUtil.isBlank(vueFilePath.getText())) {
                        vueFilePath.setText("src" + separator + "views");
                    }
                }
            }
        });
        btnclean.addActionListener((ActionEvent e) -> {
            //清空输入框内容
            repositoryPackageName.setText("");
            servicePackageName.setText("");
            controllerPackageName.setText("");
            vueFilePath.setText("");
            entityPackageName.setText("");
        });
        //读取本机配置文件
        readDefaultSetting();
    }

    private void readDefaultSetting() {
        String userHome = System.getProperty("user.home");
        String path = userHome + separator + "ueboot.properties";
        File file = new File(path);
        if (file.exists()) {
            Properties pro = new Properties();
            try {
                FileInputStream in = new FileInputStream(path);
                pro.load(in);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            String entityModuleNameValue = (String) pro.get("entityModuleName");
            String repositoryModuleNameValue = (String) pro.get("repositoryModuleName");
            String controllerModuleNameValue = (String) pro.get("controllerModuleName");
            String vuePageModuleNameValue = (String) pro.get("vuePageModuleName");
            String serviceModuleNameValue = (String) pro.get("serviceModuleName");

            String entityPackageNameValue = (String) pro.get("entityPackageName");
            String repositoryPackageNameValue = (String) pro.get("repositoryPackageName");
            String servicePackageNameValue = (String) pro.get("servicePackageName");
            String controllerPackageNameValue = (String) pro.get("controllerPackageName");
            String vueFilePathValue = (String) pro.get("vueFilePathName");
            String requestPathValue = (String) pro.get("requestPath");

            if (StringUtil.isNotBlank(entityModuleNameValue)) {
                entityModuleName.setText(entityModuleNameValue);
            }
            if (StringUtil.isNotBlank(repositoryModuleNameValue)) {
                repositoryModuleName.setText(repositoryModuleNameValue);
            }
            if (StringUtil.isNotBlank(controllerModuleNameValue)) {
                controllerModuleName.setText(controllerModuleNameValue);
            }
            if (StringUtil.isNotBlank(vuePageModuleNameValue)) {
                vuePageModuleName.setText(vuePageModuleNameValue);
            }
            if (StringUtil.isNotBlank(serviceModuleNameValue)) {
                serviceModuleName.setText(serviceModuleNameValue);
            }

            if (StringUtil.isNotBlank(entityPackageNameValue)) {
                entityPackageName.setText(entityPackageNameValue);
            }
            if (StringUtil.isNotBlank(repositoryPackageNameValue)) {
                repositoryPackageName.setText(repositoryPackageNameValue);
            }
            if (StringUtil.isNotBlank(servicePackageNameValue)) {
                servicePackageName.setText(servicePackageNameValue);
            }
            if (StringUtil.isNotBlank(controllerPackageNameValue)) {
                controllerPackageName.setText(controllerPackageNameValue);
            }
            if (StringUtil.isNotBlank(vueFilePathValue)) {
                vueFilePath.setText(vueFilePathValue);
            }
            if (StringUtil.isNotBlank(requestPathValue)) {
                requestPath.setText(requestPathValue);
            }
        }
    }

    /**
     * 保存当前用户输入的内容
     */
    private void writeDefaultSetting() {
        String userHome = System.getProperty("user.home");
        String path = userHome + separator + "ueboot.properties";

        Properties pro = new Properties();
        try {
            FileOutputStream oFile = new FileOutputStream(path);
            pro.setProperty("entityModuleName", entityModuleName.getText());
            pro.setProperty("repositoryModuleName", repositoryModuleName.getText());
            pro.setProperty("controllerModuleName", controllerModuleName.getText());
            pro.setProperty("vuePageModuleName", vuePageModuleName.getText());
            pro.setProperty("serviceModuleName", serviceModuleName.getText());

            pro.setProperty("entityPackageName", entityPackageName.getText());
            pro.setProperty("repositoryPackageName", repositoryPackageName.getText());
            pro.setProperty("servicePackageName", servicePackageName.getText());
            pro.setProperty("controllerPackageName", controllerPackageName.getText());
            pro.setProperty("vueFilePath", vueFilePath.getText());
            pro.setProperty("requestPath", requestPath.getText());
            pro.store(oFile, "Comment");
            oFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onOK() {
        //保存当前设置
        writeDefaultSetting();
        com.ueboot.generator.CodeGenerator ac = new com.ueboot.generator.CodeGenerator();
        ac.initProperties();
        String clazzName = entityPackageName.getText();
        Class<?> clz = null;
        try {
            if (clazzName == null) {
                System.out.println("类名为空！");
                JOptionPane.showMessageDialog(null, " 类名不能为空 ", " 提示 ", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String clazzNameFilePath = clazzName.replaceAll(".", separator);
            String entityFilePath = ac.getProjectPah() + entityModuleName.getText() + separator + "target" + separator + "classes" + separator + clazzNameFilePath;
            URL[] urls = {new URL("file:" + entityFilePath)};
            URLClassLoader cl = new URLClassLoader(urls);
            clz = Class.forName(clazzName, true, cl);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " 未找到该类 ", " 提示 ", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String classPath = clz.getClassLoader().getResource("").getPath();
        System.out.println("ClassPath:" + classPath);

        System.out.println("类名：" + clz.getSimpleName());
        //ac.initProperties(basePackageName.getText(), adminTextField.getText(),classPath);

        //createRepository(clz,moduleName);
        //createService(clz,moduleName);
        if (StringUtil.isNotEmpty(repositoryPackageName.getText())) {
            if (StringUtil.isEmpty(repositoryModuleName.getText())) {
                JOptionPane.showMessageDialog(null, " repositoryModuleName不能为空 ", " 提示 ", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ac.createRepository(clz, repositoryPackageName.getText(), repositoryModuleName.getText());
        }
        if (StringUtil.isNotEmpty(servicePackageName.getText())) {
            if (StringUtil.isEmpty(serviceModuleName.getText())) {
                JOptionPane.showMessageDialog(null, " serviceModuleName不能为空 ", " 提示 ", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ac.createService(clz, servicePackageName.getText(), serviceModuleName.getText(), repositoryPackageName.getText());
        }
        if (StringUtil.isNotEmpty(controllerPackageName.getText())) {
            if (StringUtil.isEmpty(controllerModuleName.getText())) {
                JOptionPane.showMessageDialog(null, " controllerModuleName不能为空 ", " 提示 ", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ac.createController(clz, controllerPackageName.getText(), servicePackageName.getText(), controllerModuleName.getText());
        }
        if (StringUtil.isNotEmpty(vueFilePath.getText())) {
            if (StringUtil.isEmpty(vuePageModuleName.getText())) {
                JOptionPane.showMessageDialog(null, " vuePageModuleName不能为空 ", " 提示 ", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ac.createPages(clz, vuePageModuleName.getText(), vueFilePath.getText(), requestPath.getText());
        }
        JOptionPane.showMessageDialog(null, "完成！ ", " 提示 ", JOptionPane.OK_OPTION);
        // dispose();
    }

    private void onCancel() {
// add your code here if necessary
        int n = JOptionPane.showConfirmDialog(null, "确定退出吗?", "提示", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        com.ueboot.generator.GeneratorDialog dialog = new com.ueboot.generator.GeneratorDialog();
        dialog.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension compSize = dialog.getSize();
        if (compSize.height > screenSize.height) {
            compSize.height = screenSize.height;
        }
        if (compSize.width > screenSize.width) {
            compSize.width = screenSize.width;
        }
        dialog.setLocation((screenSize.width - compSize.width) / 2,
                (screenSize.height - compSize.height) / 2);
        dialog.setVisible(true);

        //System.exit(0);
    }


    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
