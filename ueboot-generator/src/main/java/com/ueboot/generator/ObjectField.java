package com.ueboot.generator;

/**
 * Created by 阳葵 on 15/11/1.
 * 对象字段名称
 * @author yangkui
 */
public class ObjectField {
    private String name;
    private String upperName;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpperName() {
        return upperName;
    }

    public void setUpperName(String upperName) {
        this.upperName = upperName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if(type.startsWith("class java.lang")){
            type = type.substring(type.indexOf("java.lang.")+10);
        }

        if(type.startsWith("class")){
            type = type.substring(type.indexOf("class")+5);
        }
        this.type = type;
    }
}
