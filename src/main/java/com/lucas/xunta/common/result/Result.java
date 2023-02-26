package com.lucas.xunta.common.result;

import lombok.*;

/**
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020/11/14 9:46
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Result {
    private int code;
    private String message;
    private Object data;
}