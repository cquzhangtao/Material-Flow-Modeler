package ui.utilities;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.ImageIcon;

import com.mortennobel.imagescaling.ImageUtils;
import com.mortennobel.imagescaling.ResampleOp;

public class ImageUtilities {
	public static boolean resizeUsingJavaAlgo(String source, File dest,
			int width, int height) throws IOException {
		BufferedImage sourceImage = ImageIO.read(new FileInputStream(source));
		double ratio = (double) sourceImage.getWidth()
				/ sourceImage.getHeight();
		if (width < 1) {
			width = (int) (height * ratio + 0.4);
		} else if (height < 1) {
			height = (int) (width / ratio + 0.4);
		}

		Image scaled = sourceImage.getScaledInstance(width, height,
				Image.SCALE_AREA_AVERAGING);
		BufferedImage bufferedScaled = new BufferedImage(scaled.getWidth(null),
				scaled.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedScaled.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.drawImage(scaled, 0, 0, width, height, null);
		dest.createNewFile();
		writeJpeg(bufferedScaled, dest.getCanonicalPath(), 1.0f);
		return true;
	}

	/**
	 * Write a JPEG file setting the compression quality.
	 * 
	 * @param image
	 *            a BufferedImage to be saved
	 * @param destFile
	 *            destination file (absolute or relative path)
	 * @param quality
	 *            a float between 0 and 1, where 1 means uncompressed.
	 * @throws IOException
	 *             in case of problems writing the file
	 */
	private static void writeJpeg(BufferedImage image, String destFile,
			float quality) throws IOException {
		ImageWriter writer = null;
		FileImageOutputStream output = null;
		try {
			writer = ImageIO.getImageWritersByFormatName("jpeg").next();
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(quality);
			output = new FileImageOutputStream(new File(destFile));
			writer.setOutput(output);
			IIOImage iioImage = new IIOImage(image, null, null);
			writer.write(null, iioImage, param);
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (writer != null) {
				writer.dispose();
			}
			if (output != null) {
				output.close();
			}
		}
	}

	public static Cursor createCursor(URL img) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		BufferedImage bi = null;
		try {
			bi = ImageIO.read(img);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		BufferedImage imgcur = getScaledInstance(bi,5,5);
		Point hotspot = new Point(0, 0);
		
		Cursor cur=toolkit.createCustomCursor(toolkit.getImage(img), hotspot, "Custom Cursor");
		
return cur;
	}
	
	public static BufferedImage resize(URL url,int width,int height){
		try {
			BufferedImage sourceImage = ImageIO.read(url);
			return getScaledInstance(sourceImage,width,height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage getScaledInstance(BufferedImage img,
			int targetWidth, int targetHeight) {
		ResampleOp  resampleOp = new ResampleOp (targetWidth,targetHeight);
		BufferedImage rescaledTomato = resampleOp.filter(img, null);
//		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
//				: BufferedImage.TYPE_INT_ARGB;
//		BufferedImage ret = (BufferedImage) img;
//
//		BufferedImage tmp = new BufferedImage(targetHeight, targetHeight, type);
//		Graphics2D g2 = tmp.createGraphics();
//		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//		g2.drawImage(ret, 0, 0, targetWidth, targetHeight, null);
//		g2.dispose();

		return rescaledTomato;
	}

	public static BufferedImage getScaledInstance(BufferedImage img,
			int targetWidth, int targetHeight, boolean higherQuality) {
		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = (BufferedImage) img;
		int w, h;
		if (higherQuality) {
			// Use multi-step technique: start with original size, then
			// scale down in multiple passes with drawImage()
			// until the target size is reached
			w = img.getWidth();
			h = img.getHeight();
		} else {
			// Use one-step technique: scale directly from original
			// size to target size with a single drawImage() call
			w = targetWidth;
			h = targetHeight;
		}

		do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}

			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}

			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			// g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret = tmp;
		} while (w != targetWidth || h != targetHeight);

		return ret;
	}
}
