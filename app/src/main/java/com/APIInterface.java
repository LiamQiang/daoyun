package com;

public class APIInterface {
    public static String API_HOST="http://119.23.216.223:8081";
    public static String IMAGE_DIR="http://192.168.31.81/upload_temp/";
    //public static String IMAGE_DIR="http://29m68021p1.eicp.vip/upload_temp/";
    public static String LOGIN="/student/login";
    public static String REG="/users/register";
    public static String SIGN="/sign-in";
    public static String QUICK_REG="/users/quick-register";
    public static String QUICK_LOG="/quick-login";
    public static String GITHUB="/oauth/redirect";
    public static String GITHUB_BIND="/third-party";
    public static String EDIT_INFO="/users";

    public static String ADD_COURSE="/teacher-course";
    public static String COURSE_INFO="/teacher-course/";
    public static String COURSE_ALLOW="/teacher-course/allowJoin/";
    public static String JOIN_COURSE="/student-course";
    public static String TEC_COURSE="/teacher-course/info";
    public static String STU_COURSE="/student-course/info";
    public static String STU_LIST="/student-course/studentinfo/";

    public static String SIGN_INFO="/sign-in/info";
    public static String STU_SIGN="/sign-in-record";
    public static String SIGN_RECORD="/sign-in/sign_in_students/";
    public static String SIGN_STOP="/sign-in/";


    public static String SMS="/sms/";
    public static String SMS_LOGIN="/phone/login";
    public static String USER_INFO="/users/info";

    public static String TEC_COURSE_ID="/teacher-course/info/";

    public static String HOME="/item/search";
    public static String ITEM_VIEW="/item/view";
    public static String UPLOAD="/upload/";
    public static String ITEM_POST="/item/post";
    public static String FAVOUR_ITEM="/item/favor";
    public static String MY_FAVOR_ITEM="/item/myFavourItem";
    public static String MY_ITEM="/item/myItem";
    public static String DEL_ITEM="/item/del";

    public static String COMMENT_POST="/comment/post";
    public static String COMMENT_DEL="/comment/del";
    public static String COMMENT_MY="/comment/my";
    public static String COMMENT_READ="/comment/read";
    public static String SYS_MSG="/comment/sysMsg";


}
