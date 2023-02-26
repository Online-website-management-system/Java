package com.lucas.xunta.category.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 分类实体
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
public class Category {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父类ID
     */
    @TableField(select = false)
    private Long categoryId;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 类别 parent-父类型  son-子类型
     */
    private String rank;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 子类列表
     */
    @TableField(select = false)
    private List<Category> categoryList;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
