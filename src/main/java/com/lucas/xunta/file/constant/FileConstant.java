package com.lucas.xunta.file.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 文件配置常量类
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:37
 **/

@Configuration
@Data
public class FileConstant {
    /**
     * 文件上传路径
     */
    @Value("${xunta.file.upload-url}")
    private String uploadUrl;
}
