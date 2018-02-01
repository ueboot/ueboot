package com.ueboot.generator;

/**
 * Created by 阳葵 on 15/11/1.
 */
public class ObjectField {
    private String name;
    private String UpperName;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpperName() {
        return UpperName;
    }

    public void setUpperName(String upperName) {
        UpperName = upperName;
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
