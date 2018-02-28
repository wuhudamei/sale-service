package com.rocoinfo.entity;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 统一定义id的entity基类.
 * <p/>
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 *
 * @author calvin
 */
// JPA 基类的标识
public abstract class IdEntity implements Serializable {

    protected static final long serialVersionUID = -2716222356509348153L;
    protected Long id;
    public static final String ID_FIELD_NAME = "id";

    public IdEntity() {
    }

    public IdEntity(Long id) {
        this.id = id;
    }

    protected final Class entityClass = getClass();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //覆盖toString方法
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            Field[] fields = entityClass.getDeclaredFields();
            Object fieldValue = null;
            for (Field f : fields) {
                if (!f.getName().equals("serialVersionUID")) {
                    f.setAccessible(true);
                    fieldValue = f.get(this);
                    if (fieldValue != null) {
                        sb.append(f.getName()).append("=").append(fieldValue).append(",");
                    }
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
