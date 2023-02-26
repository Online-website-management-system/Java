package com.lucas.xunta.setting.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 设置实体
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Data
public class Setting {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 登录页logo
     */
    private String logo;

    /**
     * 网站名称
     */
    private String title;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
