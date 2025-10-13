package ai.chat2db.plugin.jingwei.driver;


/**
 * 连接类型
 */
public enum Type {
    JINGWEI_CONNECTION("jdbc:jingwei:"),
    ;
    private String schema;

    Type(String schema) {
        this.schema = schema;
    }

    public static Type figure(String schema) {
        for (Type value : Type.values()) {
            String pattern = value.schema;
            if (schema.startsWith(pattern)) {
                return value;
            }
        }
        //should not happen
        throw null;
    }
    
    public static boolean accept(String url) {
        for (Type value : Type.values()) {
            String pattern = value.schema;
            if (url.startsWith(pattern)) {
                return true;
            }
        }
        //should not happen
        return false;
    }
}