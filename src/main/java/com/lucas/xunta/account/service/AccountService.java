package com.lucas.xunta.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lucas.xunta.account.entity.Account;
import com.lucas.xunta.common.result.Result;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网址账密信息服务类
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Service
public interface AccountService extends IService<Account> {
    /**
     * 通过收藏ID查询网址账密信息
     *
     * @param collectId 收藏ID
     * @return 账密信息
     */
    Result getAccountForCollectId(Long collectId);

    /**
     * 修改网址账密信息
     *
     * @param account 账密信息实体
     * @return 修改结果
     */
    Result updateAccount(Account account);
}
