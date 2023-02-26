package com.lucas.xunta.setting.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lucas.xunta.setting.dao.SettingMapper;
import com.lucas.xunta.setting.entity.Setting;
import com.lucas.xunta.setting.service.SettingService;
import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.common.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设置服务实现类
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {
    @Autowired
    private SettingService settingService;

    /**
     * 获取系统信息
     *
     * @return 系统信息
     */
    @Override
    public Result getInfo() {
        return ResultGenerator.genSuccessResult(settingService.getOne(new LambdaQueryWrapper<Setting>().eq(Setting::getId, 1)));
    }
}
