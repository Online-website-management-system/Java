package com.lucas.xunta.file.service;

import com.lucas.xunta.common.result.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 文件服务
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020/8/11/011 12:08
 */
public interface FileService {
    /**
     * 上传文件
     *
     * @param fileList 文件列表
     * @param request  请求详情
     * @return 上传结果
     */
    Result upload(List<MultipartFile> fileList, HttpServletRequest request);
}
