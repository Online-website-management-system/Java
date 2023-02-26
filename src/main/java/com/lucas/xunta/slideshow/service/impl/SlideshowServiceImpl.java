package com.lucas.xunta.slideshow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lucas.xunta.slideshow.dao.SlideshowMapper;
import com.lucas.xunta.slideshow.entity.Slideshow;
import com.lucas.xunta.slideshow.service.SlideshowService;
import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.common.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 幻灯片服务实现类
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Service
public class SlideshowServiceImpl extends ServiceImpl<SlideshowMapper, Slideshow> implements SlideshowService {
    @Autowired
    private SlideshowService slideshowService;

    /**
     * 获取首页幻灯片
     *
     * @return 首页幻灯片
     */
    @Override
    public Result getList() {
        return ResultGenerator.genSuccessResult(slideshowService.list());
    }
}
