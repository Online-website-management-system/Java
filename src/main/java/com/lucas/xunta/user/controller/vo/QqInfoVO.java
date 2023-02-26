package com.lucas.xunta.user.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
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
public class QqInfoVO {
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
    @JSONField(name = "access_token")
    private String accessToken;
    /**
     *
     */
    @JSONField(name = "social_uid")
    private String socialUid;
    /**
     *
     */
    private String faceimg;
    /**
     *
     */
    private String nickname;
    /**
     *
     */
    private String location;
    /**
     *
     */
    private String gender;
    /**
     *
     */
    private String ip;
}
