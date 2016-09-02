package common.utils;

import java.util.Arrays;

import common.config.Config;

/**
 * 常量定义
 */
public class Def {
	/*****************************货币**********************************/
	/**CNY(Chinese Yuan)人民币*/
	public static final String CNY="CNY";
	/**USD(United States Dollar)美元*/
	public static final String USD="USD";
	/**NTD(New Taiwan Dollar)新台币*/
	public static final String NTD="NTD";
	
	/*****************************Security***************************/
	/**登陆密码secure*/
	public static final String PasswordSecret="keyhAimOan#";
	
	/** 充返礼包key */
	public static final String PayGiftKey="key#G&i$f*t@pay";
	
	
	
	/*****************************支付状态**********************************/
	public static final int PaySucceed=0;
	public static final int PayFail=1;
	
	
	/*****************************请求控制**********************************/
	/**不连通服务器的情况下默认请求次数*/
	public static final int RequestLoop=3;
	
	/*****************************返回码***************************/
	/******************0-19 基础业务******************/
	/**操作成功*/
	public static final int CODE_SUCCESS=0;
	/**操作失败*/
	public static final int CODE_FAIL=1;
	/**操作异常*/
	public static final int CODE_EXCEPTION=2;
	/**业务路由失败*/
	public static final int CODE_ROUTE_FAIL=3;
	
	/*****************************商店类型**********************************/
	
	
	/*****************************商品类型**********************************/
	/**商品促销活动banner*/
	public static final int GOODS_TYPE_BANNER=0;
	/**商品精选促销*/
	public static final int GOODS_TYPE_PROMOTION=1;
	
	/*****************************图片存储路径**********************************/
	/** 原图存储路径：默认 */
	public static final String SAVE_PATH_IMAGE = Config.WEB_BASE+"/upload/image";
	/** 缩略图存储路径：默认 */
	public static final String SAVE_PATH_THUMB = Config.WEB_BASE+"/upload/thumb";
	
	/*****************************原图宽高**********************************/
	/** 原图宽度：默认 */
	public static final int IMAGE_WIDTH_DEFAULT = 200;
	/** 原图高度：默认 */
	public static final int IMAGE_HEIGHT_DEFAULT = 200;
	
	/*****************************缩略图宽高**********************************/
	/** 缩略图宽度：默认 */
	public static final int THUMB_WIDTH_DEFAULT = 100;
	/** 缩略图高度：默认 */
	public static final int THUMB_HEIGHT_DEFAULT = 100;
	/** 缩略图宽度：用户头像 */
	public static final int THUMB_WIDTH_AVATAR = 160;
	/** 缩略图高度：用户头像 */
	public static final int THUMB_HEIGHT_AVATAR = 160;
	/** 缩略图宽度：社区 */
	public static final int THUMB_WIDTH_COMMUNITY = 200;
	/** 缩略图高度：社区 */
	public static final int THUMB_HEIGHT_COMMUNITY = 200;
	/** 缩略图宽度：店铺 */
	public static final int THUMB_WIDTH_SHOP = 320;
	/** 缩略图高度：店铺 */
	public static final int THUMB_HEIGHT_SHOP = 480;
	/** 缩略图宽度：商品 */
	public static final int THUMB_WIDTH_GOODS = 320;
	/** 缩略图高度：商品 */
	public static final int THUMB_HEIGHT_GOODS = 480;
	
	/*****************************初始昵称**********************************/
	/** 默认昵称 */
	public static final String[] NICKNAME_DEFAULT = {"小明","小红","小江","小青","大白","大山","大明","大强","小白","小林"};
	/** 默认头像 */
	public static final String AVATAR_DEFAULT = "/res/img/user-avatar.png";
	
	
}