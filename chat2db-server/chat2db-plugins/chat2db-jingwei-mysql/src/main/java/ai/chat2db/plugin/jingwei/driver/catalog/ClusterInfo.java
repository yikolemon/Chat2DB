package ai.chat2db.plugin.jingwei.driver.catalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author duanfuqiang
 * @date 2025/10/13 15:29
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClusterInfo {


    /**
     * 集群ID
     */
    private String clusterId;

    /**
     * 环境
     */
    private String environment;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 业务信息
     */
    private String businessInfo;

    /**
     * 域名
     */
    private String domain;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 标签
     */
    private String tags;

    /**
     * 描述
     */
    private String description;

    /**
     * IDC
     */
    private String idc;
    
}
