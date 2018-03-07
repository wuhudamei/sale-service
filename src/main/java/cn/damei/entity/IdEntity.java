package cn.damei.entity;

import java.io.Serializable;
import java.lang.reflect.Field;

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
