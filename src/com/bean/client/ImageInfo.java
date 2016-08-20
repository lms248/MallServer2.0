package bean.client;

import java.io.InputStream;

import common.utils.Def;

/**
 * 图片信息
 */
public class ImageInfo {
	
	private String originName; //上传的原文件名
	private String fileName; //保存的原图文件名
	private String fileName_thumb; //保存的缩略图文件名
	private String suffix; //后缀名
	private String folder = Def.SAVE_PATH_IMAGE; //原图存储文件夹
	private String folder_thumb = Def.SAVE_PATH_THUMB; //缩略图存储文件夹
	private String savePath; //原图存储路径
	private String savePath_thumb; //缩略图存储路径
	
	private int width = Def.IMAGE_WIDTH_DEFAULT; //原图宽度
	private int height = Def.IMAGE_HEIGHT_DEFAULT; //原图高度
	private boolean aspectRatio = false; //不保持原纵横比（原图）
	private int width_thumb = Def.THUMB_WIDTH_DEFAULT; //缩略图宽度
	private int height_thumb = Def.THUMB_HEIGHT_DEFAULT; //缩略图高度
	private boolean aspectRatio_thumb = false; //不保持原纵横比（缩略图）
	
	private InputStream inputStream; //上传原图的数据流
	private InputStream inputStream_thumb; //上传缩略图的数据流
	
	
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName_thumb() {
		return fileName_thumb;
	}
	public void setFileName_thumb(String fileName_thumb) {
		this.fileName_thumb = fileName_thumb;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getFolder_thumb() {
		return folder_thumb;
	}
	public void setFolder_thumb(String folder_thumb) {
		this.folder_thumb = folder_thumb;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getSavePath_thumb() {
		return savePath_thumb;
	}
	public void setSavePath_thumb(String savePath_thumb) {
		this.savePath_thumb = savePath_thumb;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public boolean isAspectRatio() {
		return aspectRatio;
	}
	public void setAspectRatio(boolean aspectRatio) {
		this.aspectRatio = aspectRatio;
	}
	public int getWidth_thumb() {
		return width_thumb;
	}
	public void setWidth_thumb(int width_thumb) {
		this.width_thumb = width_thumb;
	}
	public int getHeight_thumb() {
		return height_thumb;
	}
	public void setHeight_thumb(int height_thumb) {
		this.height_thumb = height_thumb;
	}
	public boolean isAspectRatio_thumb() {
		return aspectRatio_thumb;
	}
	public void setAspectRatio_thumb(boolean aspectRatio_thumb) {
		this.aspectRatio_thumb = aspectRatio_thumb;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public InputStream getInputStream_thumb() {
		return inputStream_thumb;
	}
	public void setInputStream_thumb(InputStream inputStream_thumb) {
		this.inputStream_thumb = inputStream_thumb;
	}
	
	@Override
	public String toString() {
		return "ImageInfo [originName=" + originName + ", fileName=" + fileName
				+ ", fileName_thumb=" + fileName_thumb + ", suffix=" + suffix
				+ ", folder=" + folder + ", folder_thumb=" + folder_thumb
				+ ", savePath=" + savePath + ", savePath_thumb="
				+ savePath_thumb + ", width=" + width + ", height=" + height
				+ ", aspectRatio=" + aspectRatio + ", width_thumb="
				+ width_thumb + ", height_thumb=" + height_thumb
				+ ", aspectRatio_thumb=" + aspectRatio_thumb + ", inputStream="
				+ inputStream + ", inputStream_thumb=" + inputStream_thumb
				+ "]";
	}
	
}
