package com.lucas.xunta.slideshow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 幻灯片实体类
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
public class Slideshow {

    private static final long serialVersionUID = 1L;

    /**
     * 轮播图ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 轮播图链接
     */
    private String url;

    /**
     * 轮播图图片
     */
    private String img;

    /**
     * 轮播图标题
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
