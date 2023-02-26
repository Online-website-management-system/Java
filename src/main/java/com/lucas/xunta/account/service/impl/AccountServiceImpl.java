package com.lucas.xunta.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lucas.xunta.account.entity.Account;
import com.lucas.xunta.account.service.AccountService;
import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.common.result.ResultGenerator;
import com.lucas.xunta.account.dao.AccountMapper;
import com.lucas.xunta.common.constant.ErrorConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网址账密信息服务实现类
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    /**
     * 通过收藏ID查询网址账密信息
     *
     * @param collectId 收藏ID
     * @return 账密信息
     */
    @Override
    public Result getAccountForCollectId(Long collectId) {
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Account account = accountMapper.getAccountForCollectId(userId, collectId);
        return ResultGenerator.genSuccessResult(account);
    }

    /**
     * 修改网址账密信息
     *
     * @param account 账密信息实体
     * @return 修改结果
     */
    @Override
    public Result updateAccount(Account account) {
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // 修改账号密码
        Integer integer = accountMapper.updateAccount(account, userId);
        if (integer > 0) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
    }
}
