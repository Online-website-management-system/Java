package com.lucas.xunta.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lucas.xunta.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return 受影响的行
     */
    Integer deleteUser(@Param("userId") Long userId);
}
