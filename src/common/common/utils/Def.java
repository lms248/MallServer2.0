package common.utils;

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
	
	/*****************************缩略图宽高**********************************/
	/** 社区缩略图的宽度 */
	public static final int COMMUNITY_THUMB_WIDTH = 100;
	/** 社区缩略图的高度 */
	public static final int COMMUNITY_THUMB_HEIGHT = 100;
	
}