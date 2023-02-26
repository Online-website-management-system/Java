package com.lucas.xunta.account.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lucas.xunta.account.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 网址账密信息 Mapper 接口
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Repository
public interface AccountMapper extends BaseMapper<Account> {
    /**
     * 通过收藏ID查询网址账密信息
     *
     * @param userId    用户ID防止非法查询
     * @param collectId 收藏ID
     * @return 账密信息
     */
    Account getAccountForCollectId(Long userId, Long collectId);

    /**
     * 修改网址账密信息
     *
     * @param account 账号信息
     * @param userId  用户ID防止非法修改
     * @return 受影响行数
     */
    Integer updateAccount(@Param("account") Account account, @Param("userId") Long userId);
}
