package com.lucas.xunta.setting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lucas.xunta.setting.entity.Setting;
import com.lucas.xunta.common.result.Result;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设置服务类
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Service
public interface SettingService extends IService<Setting> {
    /**
     * 获取系统信息
     *
     * @return 系统信息
     */
    Result getInfo();

}
