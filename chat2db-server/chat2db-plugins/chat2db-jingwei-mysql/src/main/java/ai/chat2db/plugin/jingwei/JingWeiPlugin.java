package ai.chat2db.plugin.jingwei;

import ai.chat2db.spi.DBManage;
import ai.chat2db.spi.MetaData;
import ai.chat2db.spi.Plugin;
import ai.chat2db.spi.config.DBConfig;
import ai.chat2db.spi.util.FileUtils;

/**
 * @author duanfuqiang
 * @date 2025/9/30 15:18
 */
public class JingWeiPlugin implements Plugin {
    @Override
    public DBConfig getDBConfig() {
        return FileUtils.readJsonValue(this.getClass(),"jingwei.json", DBConfig.class);
    }

    @Override
    public MetaData getMetaData() {
        return new JingWeiMetaData();
    }

    @Override
    public DBManage getDBManage() {
        return new JingWeiDBManage();
    }
}
