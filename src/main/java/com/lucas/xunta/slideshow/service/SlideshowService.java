package com.lucas.xunta.slideshow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lucas.xunta.slideshow.entity.Slideshow;
import com.lucas.xunta.common.result.Result;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 幻灯片服务类
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Service
public interface SlideshowService extends IService<Slideshow> {
    /**
     * 获取首页幻灯片
     *
     * @return 首页幻灯片
     */
    Result getList();
}
