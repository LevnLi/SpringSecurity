package com.ruoyi.project.storage.util;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.project.storage.domain.Advertisement;
import com.ruoyi.project.storage.domain.User;
import com.ruoyi.project.storage.msg.Msg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author :lihao
 * @date :2020/05/19
 * @description :信息校验工具类
 */
public class InfoUtil extends Msg {

    /**
     * 判断注册用户信息
     * @param user 用户对象
     */
    public static void judgeRegisterInfo(User user){
        // 账号判断
        judgeUserNameInfo(user.getUserName());
        // 密码判断
        judgePasswordInfo(user.getPassword());
        // 手机号判断
        judgeNumberInfo(user.getPhonenumber());
        // 姓名判断
        judgeNameInfo(user.getNickName());
        // 邮箱判断
        judgeEmailInfo(user.getEmail());
        // 性别判断
        judgeSexInfo(user.getSex());
    }

    /**
     * 判断新增用户信息
     * @param user 用户对象
     */
    public static void judgeUserInfo(User user){
        // 账号判断
        judgeUserNameInfo(user.getUserName());
        // 手机号判断
        judgeNumberInfo(user.getPhonenumber());
        // 姓名判断
        judgeNameInfo(user.getNickName());
        // 邮箱判断
        judgeEmailInfo(user.getEmail());
        // 性别判断
        judgeSexInfo(user.getSex());
    }

    /**
     * 判断更新信息
     * @param user 用户对象
     */
    public static void judgeUpdateInfo(User user){
        // 如果姓名不为空
        if (user.getNickName()!=null){
            // 判断姓名
            judgeNameInfo(user.getNickName());
        }
        // 如果手机号不为空
        if (user.getPhonenumber()!=null){
            // 判断手机号
            judgeNumberInfo(user.getPhonenumber());
        }
        // 如果性别不为空
        if (user.getSex()!=null){
            // 判断姓名
            judgeSexInfo(user.getSex());
        }
        // 如果邮箱不为空
        if (user.getEmail()!=null){
            // 判断邮箱
            judgeEmailInfo(user.getEmail());
        }
    }

    /**
     * 判断新增广告信息
     * @param advertisement 广告对象
     */
    public static void insertAdvertisementInfo(Advertisement advertisement){
        // 判断标题
        judgeTitleInfo(advertisement.getTitle());
        // 判断内容
        judgeContent(advertisement.getContent());
        // 判断积分
        judgePoint(advertisement.getPoints());
        // 判断序号
        judgeSortNo(advertisement.getSortNo());
    }

    /**
     * 判断更新广告信息
     * @param advertisement 广告对象
     */
    public static void updateAdvertisementInfo(Advertisement advertisement){
        if (advertisement.getTitle() != null){
            judgeTitleInfo(advertisement.getTitle());
        }
        if (advertisement.getContent() != null){
            judgeContent(advertisement.getContent());
        }
        if (advertisement.getPoints() != null){
            judgePoint(advertisement.getPoints());
        }
        if (advertisement.getPoints() != null){
            judgeSortNo(advertisement.getSortNo());
        }
    }

    /**
     * 判断账号是否合格
     * @param userName 账号
     */
    public static void judgeUserNameInfo(String userName){
        if (userName == null || "".equals(userName)){
            throw new CustomException("账号不能为空");
        }
        if (userName.length()>USERNAME_MAX_LENGTH){
            throw new CustomException("账号过长");
        }
        if (!isHaveChinese(userName)){
            throw new CustomException("账号格式为: 字母/数字/字母数字");
        }
    }

    /**
     * 判断密码是否合格
     * @param password 密码
     */
    public static void judgePasswordInfo(String password){
        if (password == null || "".equals(password)){
            throw new CustomException("密码为空");
        }
        if (password.length()>PASSWORD_MAX_LENGTH){
            throw new CustomException("密码过长");
        }
        if (!isHaveChinese(password)){
            throw new CustomException("密码格式为: 字母/数字/字母数字");
        }
    }

    /**
     * 判断手机号是否合格
     * @param number 手机号
     */
    public static void judgeNumberInfo(String number){
        if (number == null || "".equals(number)){
            throw new CustomException("手机号为空");
        }
        if (number.length()>NUMBER_MAX_LENGTH){
            throw new CustomException("手机号码过长");
        }
        if (!isOnlyHaveNum(number)){
            throw new CustomException("只能存在数字");
        }
    }

    /**
     * 判断姓名是否合格
     * @param name 姓名
     */
    public static void judgeNameInfo(String name){
        if (name == null || "".equals(name)){
            throw new CustomException("姓名为空");
        }
        if (name.length()>NAME_MAX_LENGTH){
            throw new CustomException("姓名过长");
        }
    }

    /**
     * 判断性别是否合格
     * @param sex 性别
     */
    public static void judgeSexInfo(String sex){
        if (sex == null || "".equals(sex)){
            throw new CustomException("性别为空");
        }
        if (!SEX_MAN.equals(sex) && !SEX_WOMAN.equals(sex)){
            throw new CustomException("性别格式错误");
        }
    }

    /**
     * 判断邮箱格式
     * @param email 邮箱格式
     */
    public static void judgeEmailInfo(String email){
        if (email == null || "".equals(email)){
            throw new CustomException("邮箱不能为空");
        }
        if (!isEmail(email)){
            throw new CustomException("邮箱格式错误");
        }
    }

    /**
     * 判断标题
     * @param title 标题
     */
    public static void judgeTitleInfo(String title){
        if (title == null || "".equals(title)){
            throw new CustomException("标题不能为空");
        }
        if (title.length() > TITLE_MAX_LENGTH){
            throw new CustomException("标题过长");
        }
    }

    /**
     * 判断内容
     * @param content 内容
     */
    public static void judgeContent(String content){
        if (content == null || "".equals(content)){
            throw new CustomException("内容不能为空");
        }
        if (content.length() > CONTENT_MAX_LENGTH){
            throw new CustomException("内容过长");
        }
    }

    public static void judgePoint(Long point){
        if (point.intValue()<=ERROR){
            throw new CustomException("积分需大于零");
        }
    }

    public static void judgeSortNo(Long sortNo){
        if (sortNo.intValue()<=ERROR){
            throw new CustomException("广告排序需大于零");
        }
    }

    /**
     * 判断上门时间段
     * @param time 时间段
     */
    public static void judgeTime(String time){
        String one = time.substring(0,2);
        one += time.substring(3,5);
        String tree = time.substring(6,8);
        tree += time.substring(9,11);
        int tow = Integer.parseInt(one);
        int four = Integer.parseInt(tree);
        if (tow >= four){
            throw new CustomException("上门时间段错误");
        }
    }

    /**
     * 判断只有字母数字
     * @param str 字符串
     * @return 结果
     */
    public static boolean isHaveChinese(String str){
        String regex = "^[A-Za-z0-9]+$";
        return str.matches(regex);
    }

    /**
     * 判断邮箱格式
     * @param str 字符串
     * @return 结果
     */
    public static boolean isEmail(String str){
        String regex = "^[A-Za-z0-9-._]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})$";
        return str.matches(regex);
    }

    /**
     * 是否只有数字
     * @param str 字符串
     * @return 结果
     */
    public static boolean isOnlyHaveNum(String str){
        String regex = "^[0-9]+$";
        return str.matches(regex);
    }

    /**
     * 是否存在非法箱子规格
     * @param str 字符串
     * @return 结果
     */
    public static boolean isHaveIllegalBoxStandard(String str){
        String regex = "^[0-9*]+$";
        return str.matches(regex);
    }

    /**
     * 是否存在非法字符
     * @param info 判断信息
     * @return 结果
     */
    public static boolean isHaveIllegalChar(String info){
        String str = "[`~!#$%^&*()+=|{}':;',//[//].<>/?~！#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p=Pattern.compile(str);
        Matcher m=p.matcher(info);
        return m.find();
    }
}
