package com.lucas.xunta.collect.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lucas.xunta.collect.controller.param.CollectSaveParam;
import com.lucas.xunta.collect.controller.param.CollectSaveSortParam;
import com.lucas.xunta.collect.entity.Collect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 收藏 Mapper 接口
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Repository
public interface CollectMapper extends BaseMapper<Collect> {
    /**
     * 增加收藏
     *
     * @param param 收藏参数实体
     * @return 受影响的行
     */
    Long insertCollect(@Param("param") CollectSaveParam param);

    /**
     * 修改收藏
     *
     * @param param 收藏参数实体
     * @return 受影响的行
     */
    Integer updateCollect(@Param("param") CollectSaveParam param);

    /**
     * 通过分类id删除收藏
     *
     * @param ids 分类id列表
     * @return 受影响的行
     */
    Integer removeForCategoryId(@Param("ids") List<Long> ids);

    /**
     * 通过分类id修改收藏
     *
     * @param ids 分类id列表
     * @return 受影响的行
     */
    Integer updateForCategoryId(@Param("ids") List<Long> ids);

    /**
     * 增加收藏访问量
     *
     * @param collectId 收藏id
     * @return 受影响的行
     */
    Integer addVisit(@Param("collectId") Long collectId);

    /**
     * 通过收藏id和用户id清除收藏
     *
     * @param collectId 收藏id
     * @param userId    用户id
     * @return 受影响的行
     */
    Integer removeForCollectIdAndUserId(Long collectId, Long userId);

    /**
     * 修改收藏排序
     *
     * @param list   排序的列表
     * @param userId 用户id
     * @return 受影响的行
     */
    Integer updateCollectSort(@Param("list") List<CollectSaveSortParam> list, @Param("userId") Long userId);


}
