package com.lucas.xunta.file.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lucas.xunta.captcha.constant.CaptchaConstant;
import com.lucas.xunta.file.dto.UploadDTO;
import com.lucas.xunta.file.service.FileService;
import com.lucas.xunta.common.utils.EntityUtils;
import com.lucas.xunta.common.utils.HttpUtils;
import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.common.result.ResultGenerator;
import com.lucas.xunta.common.constant.ErrorConstant;
import com.lucas.xunta.file.constant.FileConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 文件服务实现类
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020/8/11/011 12:08
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private FileConstant fileConstant;
    @Autowired
    private CaptchaConstant captchaConstant;

    @Override
    public Result upload(List<MultipartFile> fileList, HttpServletRequest request) {
        UploadDTO dto = UploadDTO.builder()
                .fileList(fileList)
                .moduleName(captchaConstant.getModuleName())
                .sourceIp(HttpUtils.getRealIP(request))
                .build();
        log.info("上传文件请求URL {}", fileConstant.getUploadUrl());
        ResponseEntity<String> responseEntity = null;
        try {
            MultiValueMap<String, Object> fileEntity = EntityUtils.prepareFileEntity(dto);
            responseEntity = restTemplate.postForEntity(fileConstant.getUploadUrl(), fileEntity, String.class);
        } catch (Exception e) {
            log.info("上传文件接口请求失败 {}", e.getMessage());
            return ResultGenerator.genFailResultMsg(e.getMessage());
        }
        Result vo = JSONObject.parseObject(responseEntity.getBody(), Result.class);
        if (vo.getCode() == 200) {
            return ResultGenerator.genSuccessResult(vo.getData());
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.FILE_UPLOAD_ERROR);

    }

}
