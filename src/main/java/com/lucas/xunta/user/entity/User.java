package com.lucas.xunta.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户实体
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private static final long serialVersionUID = 1L;


    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码md5
     */
    @TableField(select = false)
    private String password;

    /**
     * 用户手机
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户QQ
     */
    private String qqUid;


    /**
     * 用户标题
     */
    private String title;
    /**
     * 用户域
     */
    private String domain;

    /**
     * 用户查看域是否需要权限
     */
    private Integer domainRole;

    /**
     * 用户身份 ROLE_MEMBER-普通用户 ROLE_ADMIN-管理员
     */
    private String roles;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
