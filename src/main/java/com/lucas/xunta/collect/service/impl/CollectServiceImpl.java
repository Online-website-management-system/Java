package com.lucas.xunta.collect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lucas.xunta.collect.service.CollectService;
import com.lucas.xunta.common.result.Result;
import com.lucas.xunta.common.result.ResultGenerator;
import com.lucas.xunta.account.entity.Account;
import com.lucas.xunta.account.service.AccountService;
import com.lucas.xunta.collect.controller.param.CollectSaveParam;
import com.lucas.xunta.collect.controller.param.CollectSaveSortParam;
import com.lucas.xunta.collect.controller.param.UrlInfoParam;
import com.lucas.xunta.collect.dao.CollectMapper;
import com.lucas.xunta.collect.entity.Collect;
import com.lucas.xunta.common.constant.ErrorConstant;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 收藏服务实现类
 * </p>
 *
 * @author LucasMeng Email:a@wk2.cn
 * @since 2020-10-23
 */
@Service
@Slf4j
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {
    @Autowired
    private CollectService collectService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CollectMapper collectMapper;

    /**
     * 增加收藏访问
     *
     * @param collectId 收藏id
     * @return 增加结果
     */
    @Override
    public Result addVisit(Long collectId) {
        // 增加一个访问数
        collectMapper.addVisit(collectId);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 修改收藏首页显示
     *
     * @param collectId 收藏id
     * @param home      是否首页显示
     * @return 修改结果
     */
    @Override
    public Result updateCollectHome(Long collectId, Integer home) {
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Collect collect = collectService.getOne(
                new LambdaQueryWrapper<Collect>()
                        .select(Collect::getCategoryId)
                        .eq(Collect::getId, collectId)
                        .eq(Collect::getUserId, userId)
        );
        // 如果该收藏没有绑定分类必须在首页显示
        if (collect != null && collect.getCategoryId().equals(0L)) {
            return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_COLLECT_ERROR);
        }
        // 修改
        if (collectService.update(
                new LambdaUpdateWrapper<Collect>()
                        .set(Collect::getHome, home)
                        .eq(Collect::getId, collectId)
                        .eq(Collect::getUserId, userId)
        )) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
    }

    /**
     * 删除用户收藏
     *
     * @param collectId 收藏id
     * @return 删除结果
     */
    @Override
    public Result removeCollect(Long collectId) {
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // 删除收藏与账号信息
        Integer integer = collectMapper.removeForCollectIdAndUserId(collectId, userId);
        if (integer > 0) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
    }

    /**
     * 通过收藏ID查看收藏
     *
     * @param collectId 收藏id
     * @return 收藏详情
     */
    @Override
    public Result getCollect(Long collectId) {
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // 通过id获取收藏信息
        Collect collect = collectService.getOne(
                new LambdaQueryWrapper<Collect>()
                        .select(Collect::getId, Collect::getCategoryId, Collect::getUrl, Collect::getTitle, Collect::getLogo, Collect::getIntroduce, Collect::getHome, Collect::getSort)
                        .eq(Collect::getUserId, userId)
                        .eq(Collect::getId, collectId)
        );
        return ResultGenerator.genSuccessResult(collect);
    }

    /**
     * 修改用户收藏
     *
     * @param param 收藏实体
     * @return 修改结果
     */
    @Override
    public Result updateCollect(CollectSaveParam param) {
        // 从用户的token信息中获取到用户的userId
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        param.setUserId(userId);
        if (param.getCategoryId() == null || param.getCategoryId() == 0L) {
            param.setCategoryId(0L);
            param.setHome(1);
        }
        if (collectMapper.updateCollect(param) > 0) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
    }

    /**
     * 排序用户收藏
     *
     * @param list 收藏集合
     * @return 排序结果
     */
    @Override
    public Result updateCollectSort(List<CollectSaveSortParam> list) {
        // 从用户的token信息中获取到用户的userId
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // 排序
        Integer integer = collectMapper.updateCollectSort(list, userId);
        if (integer > 0) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
    }

    /**
     * 增加用户收藏
     *
     * @param param 收藏实体
     * @return 增加结果
     */
    @Transactional
    @Override
    public Result insertCollect(CollectSaveParam param) {
        // 从用户的token信息中获取到用户的userId
        // 获取到当前登录用户ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        param.setUserId(userId);
        if (param.getCategoryId() == 0L) {
            param.setHome(1);
        }
        // 增加收藏
        if (collectMapper.insertCollect(param) > 0) {
            // 增加成功后修改排序为ID保证ID不重复
            collectService.update(new LambdaUpdateWrapper<Collect>()
                    .set(Collect::getSort, param.getId())
                    .eq(Collect::getId, param.getId())
            );
            // 增加收藏关联的账号密码
            accountService.save(Account.builder().collectId(param.getId()).build());
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResultMsg(ErrorConstant.OPERATION_ERROR);
    }

    /**
     * 获取目标网站相关信息
     *
     * @param param 网址参数
     * @return 目标网站相关信息
     */
    @Override
    public Result getUrlInfo(UrlInfoParam param) {
        Map<String, String> map = new HashMap<>();
        // 正则取网站域名
        String pattern = "(http|https)://(www.)?(\\w+(\\.)?)+";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(param.getUrl());
        List<String> logoList = new ArrayList<>();
        while (matcher.find()) {
            logoList.add(matcher.group());
        }
        // 得到网站域名/favicon.ico
        map.put("logo", logoList.get(0) + "/favicon.ico");
        Document doc = null;
        try {
            doc = Jsoup.connect(param.getUrl()).get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String title = doc.title();
        map.put("title", title);
        Elements metas = doc.head().select("meta");
        for (Element meta : metas) {
            String content = meta.attr("content");
            if ("description".equalsIgnoreCase(meta.attr("name"))) {
                map.put("description", content);
            }
        }
        return ResultGenerator.genSuccessResult(map);
    }
}
