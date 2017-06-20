package com.cloudring.commonlib.utils;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * PackageUtils
 * <ul>
 * <strong>Install package</strong>
 * <li>{@link IoUtils#getInstance(Context)}</li>
 * <li>{@link IoUtils#getInstance(Context)}</li>
 * <li>{@link IoUtils#getInstance(Context)}</li>
 * </ul>
 * 
 * @author axiang 2015-7-15
 */



public class IoUtils {

	public static final String DIRECTORY_PUBLIC_BASE = "Cloudring";
	public static final String TMP_FILE_PREFIX = "cloudring-";
	public static final String TMP_DIRECTORY_NAME = "tmp";
	public static final String IMG_CACHE = DIRECTORY_PUBLIC_BASE + "/Cache";
	public static final String IMAGES = "images";
	
	 static IoUtils  temp;
     Context context;
	

	public IoUtils(Context context ) {	
		
		this.context = context;
	}
	
	public static IoUtils getInstance(Context context){
		
		if(null != temp )
		{
			return temp ;
		}else{
			
			temp = new IoUtils(context);
			
			return temp ;
		}
	}

	public static boolean saveFile(File file, byte[] content) {
		FileOutputStream outputStream;
		try {
			outputStream = openFileOutputStream(file);
		} catch (FileNotFoundException e) {
			LampLog.d("[saveFile] Couldn't open output");
			return false;
		}
		boolean saveOk = true;
		try {
			outputStream.write(content);
		} catch (IOException e) {
			LampLog.d("[saveFile] Couldn't write");
			return false;
		} finally {
			if (!close(outputStream)) {
				saveOk = false;
			}
		}
		if (!saveOk) {
			//noinspection ResultOfMethodCallIgnored
			file.delete();
		}
		return saveOk;
	}

	public static boolean saveBitmap(File file, Bitmap bitmap,
									 Bitmap.CompressFormat format, int quality) {
		FileOutputStream outputStream;
		try {
			outputStream = openFileOutputStream(file);
		} catch (FileNotFoundException e) {
			LampLog.d("[saveBitmap] Couldn't open file output");
			return false;
		}
		boolean saveOk = true;
		try {
			bitmap.compress(format, quality, outputStream);
		} finally {
			if (!close(outputStream)) {
				saveOk = false;
			}
		}
		if (!saveOk) {
			//noinspection ResultOfMethodCallIgnored
			file.delete();
		}
		return saveOk;
	}

	public static boolean saveBitmap(ContentResolver contentResolver, Uri uri, Bitmap bitmap,
									 Bitmap.CompressFormat format, int quality) {
		OutputStream outputStream;
		try {
			outputStream = contentResolver.openOutputStream(uri);
		} catch (FileNotFoundException e) {
			LampLog.d( "[saveBitmap] Couldn't open uri output");
			return false;
		}
		boolean saveOk = true;
		try {
			bitmap.compress(format, quality, outputStream);
		} finally {
			if (!close(outputStream)) {
				saveOk = false;
			}
		}
		if (!saveOk) {
			contentResolver.delete(uri, null, null);
		}
		return saveOk;
	}

	public static boolean copyAssets(AssetManager assetManager, String assetsPath, File dst) {
		InputStream is;
		try {
			is = assetManager.open(assetsPath);
		} catch (IOException e) {
			LampLog.d( "[copyAssets] Couldn't open assets");
			return false;
		}
		try {
			return copyStream(is, dst);
		} finally {
			close(is);
		}
	}

	public static boolean copyFile(File src, File dst) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(src);
		} catch (FileNotFoundException e) {
			LampLog.d("[copyFile] Couldn't open input");
			return false;
		}
		try {
			return copyStream(fis, dst);
		} finally {
			close(fis);
		}
	}

	public static boolean copyFile(ContentResolver contentResolver, Uri src, File dst) {
		InputStream inputStream;
		try {
			inputStream = contentResolver.openInputStream(src);
		} catch (FileNotFoundException e) {
			LampLog.d("[copyFile] Couldn't open uri input");
			return false;
		}
		try {
			return copyStream(inputStream, dst);
		} finally {
			close(inputStream);
		}
	}

	public static boolean copyStream(InputStream src, File dst) {
		FileOutputStream fos;
		try {
			fos = openFileOutputStream(dst);
		} catch (FileNotFoundException e) {
			LampLog.d( "[copyStream] Couldn't open output");
			return false;
		}
		boolean copyOk = true;
		try {
			copyStream(src, fos);
		} catch (IOException e) {
			copyOk = false;
			LampLog.d( "[copyStream] Couldn't copy");
		} finally {
			if (!close(fos)) {
				copyOk = false;
			}
		}
		if (!copyOk) {
			LampLog.d("[copyStream] Copy failed");
			//noinspection ResultOfMethodCallIgnored
			dst.delete();
		}
		return copyOk;
	}

	public static void copyStream(InputStream src, OutputStream dst) throws IOException {
		byte[] buffer = new byte[1024];
		int len;
		while ((len = src.read(buffer)) != -1) {
			dst.write(buffer, 0, len);
		}
	}

	public static FileOutputStream openFileOutputStream(File file) throws FileNotFoundException {
		ensureDirsExist(file.getParentFile());
		return new FileOutputStream(file);
	}

	public static boolean close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				LampLog.d("[close] Close failed");
				return false;
			}
		}
		return true;
	}

	public static String readString(File file) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			LampLog.d( "[readString] Couldn't open input: %s "+ file);
			return "";
		}
		try {
			return readString(fis);
		} catch (Exception e) {
			LampLog.d( "[readString] Couldn't read input: %s "+file);
		} finally {
			close(fis);
		}
		return "";
	}

	/**
	 * 从InputStream中读取String，该函数不执行关闭操作
	 *
	 * @param in InputStream
	 * @return String
	 * @throws Exception
	 */
	public static String readString(InputStream in) throws Exception {
		return new String(toByteArray(in), "UTF-8");
	}

	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n;
		while (-1 != (n = input.read(buffer))) {
			byteArrayOutputStream.write(buffer, 0, n);
		}
		return byteArrayOutputStream.toByteArray();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	private static long getExtStorageAvailableApi18() {
		if (hasExtStorage()) {
			File extStorage = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(extStorage.getAbsolutePath());
			return sf.getAvailableBlocksLong() * sf.getBlockSizeLong();
		}
		return 0;
	}

	@SuppressWarnings("deprecation")
	private static long getExtStorageAvailableSmallerThanApi18() {
		if (hasExtStorage()) {
			File extStorage = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(extStorage.getAbsolutePath());
			return ((long) sf.getAvailableBlocks()) * sf.getBlockSize();
		}
		return 0;
	}

	public static long getExtStorageAvailable() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			return getExtStorageAvailableApi18();
		} else {
			return getExtStorageAvailableSmallerThanApi18();
		}
	}

	public static boolean hasExtStorage() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	public static boolean rmFile(File file) {
		return !file.exists() || (file.isFile() && file.delete());
	}

	/**
	 * rm -r
	 */
	public static boolean rmR(File file) {
		if (!file.exists()) {
			return true;
		}
		boolean result = true;
		if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				result &= rmR(subFile);
			}
		} else {
			result = file.delete();
		}
		return result;
	}

	public static long getTotalLength(File file) {
		if (file.isDirectory()) {
			long total = 0;
			for (File child : file.listFiles()) {
				total += getTotalLength(child);
			}
			return total;
		} else {
			return file.length();
		}
	}

	/**
	 * 保证目录存在
	 *
	 * @param dir
	 * @return
	 */
	public static boolean ensureDirsExist(File dir) {
		if (!dir.isDirectory()) {
			if (!dir.mkdirs()) {
				// recheck existence in case of cross-process race
				if (!dir.isDirectory()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 获得在文件存储中的文件
	 */
	public static File fromFilesDir(Context context, String path) {
		if (path != null && path.startsWith(File.separator)) {
			path = path.substring(File.separator.length());
		}
		File filesDir = context.getExternalFilesDir(null);
		File rollback = filesDir;
		if (filesDir != null && !ensureDirsExist(filesDir)) {
			filesDir = null;
		}
		if (filesDir == null) {
			filesDir = context.getFilesDir();
		}
		if (filesDir == null) {
			filesDir = rollback;
		}
		if (path == null) {
			return filesDir;
		} else {
			return new File(filesDir, path);
		}
	}

	/**
	 * 获得在缓存存储中的文件
	 *
	 * @param path
	 * @return
	 */
	public static File fromCacheDir(Context context, String path) {
		if (path != null && path.startsWith(File.separator)) {
			path = path.substring(File.separator.length());
		}
		File cacheDir = context.getExternalCacheDir();
		File rollback = cacheDir;
		if (cacheDir != null && !ensureDirsExist(cacheDir)) {
			cacheDir = null;
		}
		if (cacheDir == null) {
			// maybe null
			cacheDir = context.getCacheDir();
		}
		if (cacheDir == null) {
			cacheDir = rollback;
		}
		if (path == null) {
			return cacheDir;
		} else {
			return new File(cacheDir, path);
		}
	}

	/**
	 * 是否应用私有的文件
	 *
	 * @param file
	 * @return
	 */
	public static boolean isAppPrivate(Context context, File file) {
		return isInDataDir(context, file);
	}

	/**
	 * 文件是否在 内部 Data 目录下
	 *
	 * @param file
	 * @return
	 */
	public static boolean isInDataDir(Context context, File file) {
		if (file == null) {
			return false;
		}
		String dataDir = context.getApplicationInfo().dataDir;
		if (dataDir != null) {
			dataDir = new File(dataDir).getAbsolutePath();  // Removing any trailing slash
			String filePath = file.getAbsolutePath();
			return filePath.equals(dataDir) || filePath.startsWith(dataDir + File.separator);
		}
		return false;
	}

	@NonNull
	public static File getDCIMDirectory(String type) {
		File directory = getAvailableExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		return new File(directory, type);
	}

	@NonNull
	public static File getAvailableExternalStoragePublicDirectory(String type) {
		File result = Environment.getExternalStoragePublicDirectory(type);
		if (ensureDirsExist(result)) {
			return result;
		}
		LampLog.d("Fail to create public storage directory: +%S "+ result);
		return result;
	}

	@NonNull
	public static File fromPublicPath(Context context, String path) {
		String publicBase = DIRECTORY_PUBLIC_BASE;
		if (hasExtStorage()) {
			File result = new File(Environment.getExternalStorageDirectory(), publicBase);
			if (ensureDirsExist(result)) {
				if (path == null) {
					return result;
				}
				result = new File(result, path);
				ensureDirsExist(result.getParentFile());
				return result;
			}
		}
		File result = fromFilesDir(context, publicBase);
		ensureDirsExist(result);
		if (path != null) {
			result = new File(result, path);
			ensureDirsExist(result.getParentFile());
		}
		return result;
	}

	/**
	 * 以当前时间为名称, 生成文件
	 *
	 * @param directory 目录
	 * @param suffix    后缀名
	 * @return 生成的文件
	 */
	@NonNull
	public static File generateDatedFile(File directory, String suffix) {
		return generateDatedFile(directory, suffix, false, false);
	}

	/**
	 * 以当前时间为名称, 生成文件
	 *
	 * @param directory 目录
	 * @param suffix    后缀名
	 * @param twoStage  是否生成二级文件, 即存放在当前月的文件夹下
	 * @return 生成的文件
	 */
	@NonNull
	public static File generateDatedFile(File directory, String suffix, boolean twoStage) {
		return generateDatedFile(directory, suffix, twoStage, false);
	}

	/**
	 * 以当前时间为名称, 生成文件
	 *
	 * @param directory     目录
	 * @param suffix        后缀名
	 * @param twoStage      是否生成二级文件, 即存放在当前月的文件夹下
	 * @param createNewFile 是否需要创建新文件
	 * @return 生成的文件
	 */
	@NonNull
	public static File generateDatedFile(File directory, String suffix, boolean twoStage, boolean createNewFile) {
		boolean dirOk = ensureDirsExist(directory);
		if (!dirOk) {
			LampLog.e("Fail to create directory: ");
		}
		String filename = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
		if (twoStage) {
			String month = filename.substring(0, 6);
			return generateDatedFile(new File(directory, month), suffix, false);
		}
		File result = new File(directory, filename + suffix);

		if (!createNewFile) {
			return result;
		}

		int i = -1;
		try {
			while (!result.createNewFile()) {
				result = new File(directory, filename + (i + suffix));
				i--;
			}
		} catch (IOException e) {
			LampLog.e( "Fail to create new file: ");
		}
		return result;
	}

	@NonNull
	public static File createTempFile(Context context, String suffix, boolean isPublic) throws IOException {
		File directory;
		if (isPublic) {
			directory = null;
		} else {
			directory = fromCacheDir(context, TMP_DIRECTORY_NAME);
			if (directory != null && !ensureDirsExist(directory)) {
				LampLog.w("Could not create tmp directory in cacheDir");
				directory = null;
			}
		}
		File tmp = File.createTempFile(TMP_FILE_PREFIX, suffix, directory);
		LampLog.d("Create tmp file: %s "+ tmp);
		if (isPublic) {
			setOtherWritable(tmp);
		}
		return tmp;
	}

	private static void setOtherWritable(File file) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			boolean succeeded = file.setWritable(true, false);
			if (!succeeded) {
				LampLog.d("Fail to setWritable(true, false): %s "+ file);
			}
		}
	}

	public static final String SCHEME_FILE = "file";

	public static boolean isFile(Uri uri) {
		return uri != null && SCHEME_FILE.equals(uri.getScheme());
	}

	public static File toFile(Uri uri) {
		if (uri != null && isFile(uri)) {
			return new File(uri.getPath());
		}
		return null;
	}


	public static byte[] file2Byte(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) return buffer;

			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	public static void byte2File(byte[] buf, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {
				dir.mkdirs();
			}
			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}




	public IoUtils() {	
		
	
	}
	

}
