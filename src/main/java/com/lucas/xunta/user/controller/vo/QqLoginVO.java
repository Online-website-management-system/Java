package com.lucas.xunta.user.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020/7/24/024 12:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QqLoginVO {
    /**
     *
     */
    private String code;
    /**
     *
     */
    private String msg;
    /**
     *
     */
    private String type;
    /**
     *
     */
    private String url;
}
