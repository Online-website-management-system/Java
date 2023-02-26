package com.lucas.xunta.collect.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.collect.controller.param.CollectSaveParam;
import com.lucas.xunta.collect.controller.param.CollectSaveSortParam;
import com.lucas.xunta.collect.controller.param.UrlInfoParam;
import com.lucas.xunta.collect.entity.Collect;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 收藏服务类
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Service
public interface CollectService extends IService<Collect> {

    /**
     * 增加用户收藏
     *
     * @param param 收藏实体
     * @return 增加结果
     */
    Result insertCollect(CollectSaveParam param);

    /**
     * 修改用户收藏
     *
     * @param param 收藏实体
     * @return 修改结果
     */
    Result updateCollect(CollectSaveParam param);

    /**
     * 排序用户收藏
     *
     * @param list 收藏集合
     * @return 排序结果
     */
    Result updateCollectSort(List<CollectSaveSortParam> list);

    /**
     * 增加收藏访问
     *
     * @param collectId 收藏id
     * @return 增加结果
     */
    Result addVisit(Long collectId);

    /**
     * 修改收藏首页显示
     *
     * @param collectId 收藏id
     * @param home      是否首页显示
     * @return 修改结果
     */
    Result updateCollectHome(Long collectId, Integer home);

    /**
     * 删除用户收藏
     *
     * @param collectId 收藏id
     * @return 删除结果
     */
    Result removeCollect(Long collectId);

    /**
     * 通过收藏ID查看收藏
     *
     * @param collectId 收藏id
     * @return 收藏详情
     */
    Result getCollect(Long collectId);

    /**
     * 获取目标网站相关信息
     *
     * @param param 网址参数
     * @return 目标网站相关信息
     */
    Result getUrlInfo(UrlInfoParam param);
}
