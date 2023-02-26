package com.lucas.xunta.common.constant;

/**
 * @description: 提示常量类
 * @author: LucasMeng Email:a@wk2.cn
 * @data: 2020/10/28 8:37
 **/

public class ErrorConstant {
    //登录错误
    public final static String LOGIN_ERROR = "登录失败，账号或密码错误";
    public final static String LOGIN_PHONE_ERROR = "短信登录失败，请重试";
    //注册错误
    public final static String REGISTER_PASSWORD_ERROR = "注册失败，两次密码不一致";
    public final static String REGISTER_USERNAME_ERROR = "注册失败，账号已经注册过";
    //绑定错误
    public final static String QQ_REPETITION_BINDING_ERROR = "该QQ已被绑定到其它账号，可使用该QQ登录解除绑定";
    public final static String QQ_BINDING_ERROR = "QQ绑定失败，请重试";
    public final static String QQ_RELIEVE_BINDING_ERROR = "解除QQ绑定失败，请重试";
    public final static String PHONE_RELIEVE_BINDING_ERROR = "该手机号已被绑定，可使用手机号登录注销账号";
    //权限错误
    public final static String ROLE_ERROR = "抱歉，您没有权限进行操作，请登录";
    //验证码错误
    public final static String SEND_SMS_ERROR = "发送验证码失败，请重试";
    public final static String CAPTCHA_ERROR = "验证码错误，请重新输入";
    //文件错误
    public final static String FILE_UPLOAD_ERROR = "文件上传失败，请重试";
    //操作错误
    public final static String OPERATION_ERROR = "操作失败，请重试";
    public final static String OPERATION_USER_USERNAME_ERROR = "操作失败，用户名已被使用，请重新输入";
    public final static String OPERATION_USER_PASSWORD_ERROR = "操作失败，原密码不正确";
    public final static String OPERATION_USER_USERNAME_PASSWORD_SIZE_ERROR = "长度至少六位数，请重试";
    public final static String OPERATION_USER_REPETITION_DOMAIN_ERROR = "操作失败，该个人主页已被使用，请重新输入";
    public final static String OPERATION_COLLECT_ERROR = "操作失败，该收藏未绑定分类，必须在首页显示";
    public final static String OPERATION_CATEGORY_CHOOSE_ERROR = "所属分类不能选择自己或者自己的子级分类，请重试";
}
