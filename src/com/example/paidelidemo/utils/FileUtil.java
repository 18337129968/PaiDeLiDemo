package com.example.paidelidemo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;

/**
 * �ļ�����������
 * 
 * @author xiehaifeng
 */
public class FileUtil {

	/** ���湹�췽�� */
	public FileUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * ��ȡ�ı�����
	 * 
	 * @param context
	 *            ����������
	 * @param fileName
	 *            �ļ���
	 * @return String, ��ȡ�����ı����ݣ�ʧ�ܷ���null
	 */
	public static String readFile(Context context, String fileName) {
		if (!exists(context, fileName)) {
			return null;
		}
		FileInputStream fis = null;
		String content = null;
		try {
			fis = context.openFileInput(fileName);
			if (fis != null) {

				byte[] buffer = new byte[1024];
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				while (true) {
					int readLength = fis.read(buffer);
					if (readLength == -1)
						break;
					arrayOutputStream.write(buffer, 0, readLength);
				}
				fis.close();
				arrayOutputStream.close();
				content = new String(arrayOutputStream.toByteArray());

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			content = null;
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * �ļ��Ƿ����
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static boolean exists(Context context, String fileName) {
		return new File(context.getFilesDir(), fileName).exists();

	}

}
