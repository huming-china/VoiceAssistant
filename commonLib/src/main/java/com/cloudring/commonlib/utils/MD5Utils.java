package com.cloudring.commonlib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils
{
	
	/**
	 * 123456加密后是：123456:e10adc3949ba59abbe56e057f20f883f
	 */
	
	/** * 16进制字符集 */
	private static final char HEX_DIGITS[] =
	{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	/** * 指定算法为MD5的MessageDigest */
	private static MessageDigest messageDigest = null;
	
	/** * 初始化messageDigest的加密算法为MD5 */
	static
	{
		try
		{
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 用于获取一个String的md5值
	 * 
	 * @param str
	 * @return
	 */
	public static String getMd5(String str)
	{
		byte[] bs = messageDigest.digest(str.getBytes());
		StringBuilder sb = new StringBuilder(40);
		for (byte x : bs)
		{
			if ((x & 0xff) >> 4 == 0)
			{
				sb.append("0").append(Integer.toHexString(x & 0xff));
			} else
			{
				sb.append(Integer.toHexString(x & 0xff));
			}
		}
		return sb.toString();
	}
	
	/**
	 * * 获取文件的MD5值
	 * 
	 * @param file
	 *            目标文件
	 * 
	 * @return MD5字符串
	 */
	public static String getFileMD5String(File file)
	{
		String ret = "";
		FileInputStream in = null;
		FileChannel ch = null;
		try
		{
			in = new FileInputStream(file);
			ch = in.getChannel();
			ByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			messageDigest.update(byteBuffer);
			ret = bytesToHex(messageDigest.digest());
		} catch (IOException e)
		{
			e.printStackTrace();
			
		} finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (ch != null)
			{
				try
				{
					ch.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	/**
	 * * 获取文件的MD5值
	 * 
	 * @param file
	 *            目标文件
	 * 
	 * @return MD5字符串
	 */
	public static String getFileMD5String(File file, long time)
	{
		String ret = "";
		FileInputStream in = null;
		FileChannel ch = null;
		try
		{
			in = new FileInputStream(file);
			ch = in.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) (file.length()));
			byte[] fileData = new byte[(int) file.length() + 4];
			byteBuffer.get(fileData, 0, fileData.length - 4);
			byte[] times = u32Time(System.currentTimeMillis() / 1000);
			System.arraycopy(times, 0, fileData, fileData.length - 3, 4);
			ByteBuffer buf = ByteBuffer.wrap(fileData);
			messageDigest.update(buf);
			ret = bytesToHex(messageDigest.digest());
		} catch (IOException e)
		{
			e.printStackTrace();
			
		} finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (ch != null)
			{
				try
				{
					ch.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	public static byte[] u32Time(long time)
	{
		byte[] buf = new byte[4];
		buf[3] = (byte) (time & 0xff);
		buf[2] = (byte) ((time >> 8) & 0xff);
		buf[1] = (byte) ((time >> 16) & 0xff);
		buf[0] = (byte) ((time >> 24) & 0xff);
		return buf;
	}
	
	/**
	 * * 获取文件的MD5值
	 * 
	 * @param fileName
	 *            目标文件的完整名称
	 * 
	 * @return MD5字符串
	 */
	public static String getFileMD5String(String fileName)
	{
		return getFileMD5String(new File(fileName));
	}
	
	/**
	 * * 检验文件的MD5值
	 * 
	 * @param file
	 *            目标文件
	 * 
	 * @param md5
	 *            基准MD5值
	 * 
	 * @return 检验结果
	 */
	public static boolean checkFileMD5(File file, String md5)
	{
		return getFileMD5String(file).equalsIgnoreCase(md5);
		
	}
	
	/**
	 * * 检验文件的MD5值
	 * 
	 * @param fileName
	 *            目标文件的完整名称
	 * 
	 * @param md5
	 *            基准MD5值
	 * 
	 * @return 检验结果
	 */
	public static boolean checkFileMD5(String fileName, String md5)
	{
		return checkFileMD5(new File(fileName), md5);
		
	}
	
	/**
	 * * 将字节数组转换成16进制字符串
	 * 
	 * @param bytes
	 *            目标字节数组
	 * 
	 * @return 转换结果
	 */
	public static String bytesToHex(byte bytes[])
	{
		return bytesToHex(bytes, 0, bytes.length);
		
	}
	
	/**
	 * * 将字节数组中指定区间的子数组转换成16进制字符串
	 * 
	 * @param bytes
	 *            目标字节数组
	 * 
	 * @param start
	 *            起始位置（包括该位置）
	 * 
	 * @param end
	 *            结束位置（不包括该位置）
	 * 
	 * @return 转换结果
	 */
	public static String bytesToHex(byte bytes[], int start, int end)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < start + end; i++)
		{
			sb.append(byteToHex(bytes[i]));
		}
		return sb.toString();
		
	}
	
	/**
	 * * 将单个字节码转换成16进制字符串
	 * 
	 * @param bt
	 *            目标字节
	 * 
	 * @return 转换结果
	 */
	public static String byteToHex(byte bt)
	{
		return HEX_DIGITS[(bt & 0xf0) >> 4] + "" + HEX_DIGITS[bt & 0xf];
		
	}
	
}
