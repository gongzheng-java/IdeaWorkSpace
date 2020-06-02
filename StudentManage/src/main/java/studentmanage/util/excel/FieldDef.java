package studentmanage.util.excel;

/**
 * excel字段配置
 */
public class FieldDef {
    /**
     * excel的列标题
     */
    private String title = "";

    /**
     * 数据源的属性名称
     */
    private String prop = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public FieldDef(String title, String prop) {
        this.title = title;
        this.prop = prop;
    }
}
