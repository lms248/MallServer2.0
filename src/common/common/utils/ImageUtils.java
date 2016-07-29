package common.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * 图片处理
 * @author lims
 * @date 2016-03-04
 */
public class ImageUtils {
	/** 文件对象 */
	private File file = null;
	/** 输出图路径 */
    private String outputUrl;
    /** 输出图文件名 */
    private String outputFileName;
    /** 默认输出图片宽 */
    private int outputWidth = 200; 
    /** 默认输出图片高 */
    private int outputHeight = 200;
    /** 是否等比缩放标记(默认为等比缩放) */
    private int proportion = ImageUtils.PROPORTION_RATE;
    /** 按原先图片的宽高大小进行压缩 */
    public final static int PROPORTION_OLD = 0;
    /** 按输入的宽高大小，选择最小的那个作为标准进行压缩 */
    public final static int PROPORTION_RATE = 1;
    /** 按输入的宽高大小进行压缩 */
    public final static int PROPORTION_INPUT = 2;
    /** 文件不存在 */
    public final static int FILE_NOT_EXIST = 0;
    /** 压缩失败 */
    public final static int COMPRESS_ERROR = -1;
    /** 压缩成功 */
    public final static int COMPRESS_SUCCESS = 1;
	
    public ImageUtils() { // 初始化变量
    	this.outputUrl = ""; 
    	this.outputFileName = ""; 
    	this.outputWidth = 200; 
    	this.outputHeight = 200; 
    }
    
	/** 通过file文件读取图片进行压缩 */
	private int compress(File file) {
		if (!file.exists()) {
			return ImageUtils.FILE_NOT_EXIST;
		}
		try {
			Image image = ImageIO.read(file);
			boolean success = compress(image);
			if (!success) {
				return ImageUtils.COMPRESS_ERROR;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ImageUtils.COMPRESS_SUCCESS;
	}
	
	/** 通过Stream流读取图片进行压缩 */
	private int compress(InputStream inputStream) {
		if (inputStream == null) {
			return ImageUtils.FILE_NOT_EXIST;
		}
		try {
			Image image = ImageIO.read(inputStream);
			boolean success = compress(image);
			if (!success) {
				return ImageUtils.COMPRESS_ERROR;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ImageUtils.COMPRESS_SUCCESS;
	}
	
	/**
	 * 读取图片进行压缩
	 * @param image (java.awt.Image)
	 * @return (boolean)success成功返回true，失败返回false
	 */
	private boolean compress(Image image) {
		boolean success = false;
		try {
			// 判断图片格式是否正确
			if (image.getWidth(null) == -1) {
				System.err.println("无法正确读取图片，请重试！");
				success = false;
			} else {
				int newWidth; int newHeight;
				// 判断是否等比缩放
				if (this.proportion == ImageUtils.PROPORTION_RATE) {
					// 为等比缩放计算输出的图片宽度及高度 
					double rate1 = ((double) image.getWidth(null)) / (double) outputWidth;
					double rate2 = ((double) image.getHeight(null)) / (double) outputHeight;
					// 根据缩放比率大的进行缩放控制 
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) image.getWidth(null)) / rate);
					newHeight = (int) (((double) image.getHeight(null)) / rate);
				} else if (this.proportion == ImageUtils.PROPORTION_INPUT) {
					newWidth = this.outputWidth; // 输出的图片宽度 
					newHeight = this.outputHeight; // 输出的图片高度 
					if (image.getWidth(null) < this.outputWidth) {
						newWidth = image.getWidth(null); // 输出的图片宽度 
					}
					if (image.getHeight(null) < this.outputHeight) {
						newHeight = image.getHeight(null); // 输出的图片高度 
					}
				} else {
					newWidth = image.getWidth(null); // 输出的图片宽度 
					newHeight = image.getHeight(null); // 输出的图片高度 
				}
				
				BufferedImage bufferedImage = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
				/** Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的优先级比速度高 生成的图片质量比较好 但速度慢 */
				bufferedImage.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream outputStream = new FileOutputStream(outputUrl + "/" + outputFileName);
				String extName = outputFileName.substring(outputFileName.lastIndexOf(".")).toLowerCase();//后缀名
				if (extName.equals(".png")) {
					Graphics2D g2d = bufferedImage.createGraphics();  
					bufferedImage = g2d.getDeviceConfiguration().createCompatibleImage(
							newWidth,newHeight,Transparency.TRANSLUCENT);  
					g2d.dispose();  
					g2d = bufferedImage.createGraphics();  
					Image from = image.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING);  
					g2d.drawImage(from, 0, 0, null);
					g2d.dispose();  
					
					ImageIO.write(bufferedImage, "png", outputStream); 
				} else {
					// JPEGImageEncoder可适用于其他图片类型的转换 
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
					encoder.encode(bufferedImage);
					outputStream.close();
				}
				
				success = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * 图片处理
	 * @param inputUrl 输入图路径 
	 * @param outputUrl 输出图路径
	 * @param inputFileName 输入图文件名
	 * @param outputFileName 输出图文件名
	 * @return compress(file);
	 */
	public int compress(String inputUrl, String outputUrl, String inputFileName, String outputFileName) { 
		setPathAndName(outputUrl, outputFileName);
        //获得源文件 
        file = new File(inputUrl + inputFileName); 
        if (!file.exists()) { 
                return ImageUtils.FILE_NOT_EXIST;
        }
        return compress(file); 
	}
	
	/**
	 * 图片处理
	 * @param inputUrl 输入图路径
	 * @param outputUrl 输出图路径
	 * @param inputFileName 输入图文件名 
	 * @param outputFileName 输出图文件名
	 * @param width 输出图宽度
	 * @param height 输出图高度
	 * @param proportion 是否是等比缩放 标记
	 * @return compress(file);
	 */
	public int compress(String inputUrl, String outputUrl, String inputFileName, String outputFileName, 
			int width, int height, int proportion) { 
		setPathAndName(outputUrl, outputFileName);
		setWidthAndHeight(width, height);
		setProportion(proportion);
        //获得源文件 
        file = new File(inputUrl + inputFileName); 
        if (!file.exists()) { 
                return ImageUtils.FILE_NOT_EXIST;
        }
        return compress(file); 
	}
	
	/**
	 * 图片处理
	 * @param inputStream 图片输入流
	 * @param outputUrl 输出图路径
	 * @param inputFileName 输入图文件名 
	 * @param outputFileName 输出图文件名
	 * @return compress(inputStream); 
	 */
	public int compress(InputStream inputStream, String outputUrl, String outputFileName) { 
		setPathAndName(outputUrl, outputFileName);
		return compress(inputStream); 
	}
	
	/**
	 * 图片处理
	 * @param inputStream 图片输入流
	 * @param outputUrl 输出图路径
	 * @param inputFileName 输入图文件名 
	 * @param outputFileName 输出图文件名
	 * @param width 输出图宽度
	 * @param height 输出图高度
	 * @param proportion 是否是等比缩放 标记
	 * @return compress(inputStream); 
	 */
	public int compress(InputStream inputStream, String outputUrl, String outputFileName, 
			int width, int height, int proportion) { 
		setPathAndName(outputUrl, outputFileName);
		setWidthAndHeight(width, height);
		setProportion(proportion);
		return compress(inputStream); 
	}
	
	/**
	 * 获得图片大小 
	 * @param path 图片路径 
	 * @return 图片大小
	 */
    public long getImageSize(String path) { 
            file = new File(path); 
            return file.length(); 
    }
	
	private void setPathAndName(String outputUrl, String outputFileName) {
        // 输出图路径 
        this.outputUrl = outputUrl; 
        // 输出图文件名
        this.outputFileName = outputFileName;
	}
	private void setWidthAndHeight(int width, int height) { 
        this.outputWidth = width;
        this.outputHeight = height; 
	}
	private void setProportion(int proportion) {
		this.proportion = proportion;
	}

}
