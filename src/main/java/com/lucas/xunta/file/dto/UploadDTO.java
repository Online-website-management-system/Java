package com.lucas.xunta.file.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 上传文件DTO实体
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
public class UploadDTO {
    /**
     * 文件列表
     */
    private List<MultipartFile> fileList;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 来源ip
     */
    private String sourceIp;


}
