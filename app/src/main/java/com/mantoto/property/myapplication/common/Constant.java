package com.mantoto.property.myapplication.common;

/**
 * Created by Mr.liu
 * On 2016/7/13
 * At 18:39
 * My Application
 */
public class Constant {
/******************************       常量定义          *******************************************/
    public static final boolean DEBUG = true;

/************************          以下是网络请求接口地址         *********************************/
    /**
     * 测试服务器地址
     */
    public static final String URL = "http://dev.mantoto.com/";
    /**
     * 获取天气接口
     */
    public static final String GET_WEATHER = URL+"/Json/GetWeather.aspx";
    /**
     * 获取菜单项
     */
    public static final String GET_MENU_LIST = URL + "/Json/MenuListGet.aspx";
    /**
     * 通过手机号获取验证码
     */
    public static final String GET_PHONE_CODE = "http://123.56.96.68:8080/sendCode/sendCodeWithNum/";
    /**
     * 通过手机号验证验证码
     */
    public static final String GET_VERIFY_CODE = "http://123.56.96.68:8080/sendCode/verifyCode/";
    /**
     * 用户登陆
     */
    public static final String GET_USER_VERIFY = URL+"/Json/UserVerify.aspx";


}
