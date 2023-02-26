package com.lucas.xunta.collect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lucas.xunta.category.entity.Category;
import com.lucas.xunta.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 收藏实体类
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Collect {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户信息
     */
    @TableField(select = false)
    private User user;

    /**
     * 分类信息
     */
    @TableField(select = false)
    private Category category;

    /**
     * 收藏地址
     */
    private String url;

    /**
     * 收藏标题
     */
    private String title;

    /**
     * 收藏logo
     */
    private String logo;

    /**
     * 收藏介绍
     */
    private String introduce;

    /**
     * 是否首页
     */
    private Integer home;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 收藏点赞数
     */
    private Integer praise;

    /**
     * 收藏访问数
     */
    private Long visit;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
