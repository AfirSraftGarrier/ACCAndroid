package com.acc.android.frame.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Base64;

public class FileUtil {
	private static String PATH_ROOT;
	// private static String PATH_CONFIG;
	// private static String PATH_DATA;
	private static String PATH_FILE;

	private static String PATH_MAP_CONFIG;
	private static String PATH_MAP_MAP;

	// private static String PATH_MAP_MAP_RESOURCE_IMAGES;

	public static byte[] readFileToBytes(String filename) {
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(filename);
			data = new byte[in.available()];
			in.read(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// String str = new String(Base64.encode(data, Base64.DEFAULT));
		return data;
	}

	public static String getStringFromFile(File file, String encoding) {
		InputStreamReader read;
		StringBuffer sb = new StringBuffer();
		try {
			read = new InputStreamReader(new FileInputStream(file), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTXT = null;
			while ((lineTXT = bufferedReader.readLine()) != null) {
				sb.append(lineTXT.toString().trim());
			}
			read.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// LogUtil.systemOut("getStringFromFile:", sb.toString());
		return sb.toString();
	}

	public static String readFileToString(String filename) {
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(filename);
			data = new byte[in.available()];
			in.read(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String str = new String(Base64.encode(data, Base64.DEFAULT));
		return str;
	}

	public static void saveStringToFile(File file, String string) {
		// if (string == null) {
		// return;
		// }
		// try {
		// file.createNewFile();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// FileOutputStream fos;
		// try {
		// fos = new FileOutputStream(file);
		// fos.write(string.getBytes(encoding));
		// fos.close();
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// File fo = new File(fileName);
		// 文件不存在,就创建该文件
		// if (!file.exists()) {
		// String fileName = file.getName();
		// // 先创建文件的目录
		// File pFile = new File(path);
		// pFile.mkdirs();
		// }
		// try {
		// String fileName = file.getName();
		// String fileDirName = fileName.substring(0,
		// fileName.lastIndexOf('.'));
		// makeIfNotExistDir(fileDirName);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// try {
		// file.createNewFile();
		// } catch(Exception e) {
		//
		// }
		FileOutputStream fos;
		String encoding = "utf-8";
		try {
			if (!file.exists()) {
				makeIfNotExistFileDir(file);
			}
			file.createNewFile();
			fos = new FileOutputStream(file);
			fos.write(string.getBytes(encoding));
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 创建文件输出流
		// FileOutputStream fos = new FileOutputStream(fo);
		// 创建XML文件对象输出类实例
		// XMLEncoder encoder = new XMLEncoder(fos);
		// 对象序列化输出到XML文件
		// encoder.writeObject(obj);
		// encoder.flush();
		// 关闭序列化工具
		// encoder.close();
		// 关闭输出流
		// fos.close();
	}

	// public static File getConfigFile(String fileName) {
	//
	// }
	//
	// public static File getDataFile() {
	//
	// }
	//

	public static File getMapConfigFile(String fileName) {
		String mapConfigPathName = getPathMapConfig();
		// makeIfNotExistDir(mapConf/igPathName);
		return new File(mapConfigPathName + File.separator + fileName);
	}

	public static File getMapMapFile(String fileName) {
		String mapMapPathName = getPathFile();
		// makeIfNotExistDir(mapMapPathName);
		return new File(mapMapPathName + File.separator + fileName);
	}

	public static String getPathRoot() {
		if (PATH_ROOT == null) {
			boolean sdCardExist = Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED);
			if (sdCardExist) {
				File sdDir = Environment.getExternalStorageDirectory();
				// Environment.getRootDirectory()
				// Environment.getExternalStoragePublicDirectory(type)
				PATH_ROOT = sdDir.toString();
				// if (AppConstant.IS_MORE_SDCARD) {
				// PATH_ROOT += "/external_sd";
				// }
				// System.out.println(PATH_ROOT);
			} else {
				PATH_ROOT = "";
			}
			PATH_ROOT += "/PDA/HF/";
		}
		return PATH_ROOT;
	}

	public static String getPathFile() {
		if (PATH_MAP_CONFIG == null) {
			PATH_MAP_CONFIG = getPathRoot() + "file";
		}
		return PATH_MAP_CONFIG;
	}

	public static boolean isFileExist(String fileName) {
		File file = getFile(fileName);
		// LogUtil.systemOut(file.getPath());
		// LogUtil.systemOut(file.exists());
		return file.exists();
	}

	public static File getFile(String fileName) {
		// String filePathName = getPathFile();
		// makeIfNotExistDir(mapMapPathName);
		return new File(getFileName(fileName));
	}

	private static void makeSureInitDir(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			// boolean isMakeSuccess =
			file.mkdirs();
			// LogUtil.systemOut("XXXXXXXXXXXXX");
			// LogUtil.systemOut(isMakeSuccess);
		}
	}

	public static String getFileName(String fileName) {
		String filePathName = getPathFile() + File.separator;
		makeSureInitDir(filePathName);
		return filePathName + fileName;
	}

	public static String getPathMapConfig() {
		if (PATH_MAP_CONFIG == null) {
			PATH_MAP_CONFIG = getPathRoot() + "MAP/CONFIG";
		}
		return PATH_MAP_CONFIG;
	}

	public static String getPathMapMap() {
		if (PATH_MAP_MAP == null) {
			PATH_MAP_MAP = getPathRoot() + "MAP/MAP";
		}
		return PATH_MAP_MAP;
	}

	public static String getTestPath() {
		return getPathMapMap() + "/resources/images/style_new/comb.png";
	}

	// public static File getMainDir(String folderName) {
	// String fileName = getRootPath() + "/AGCOM/PANYU" + folderName + "/";
	// return makeIfNotExistDir(fileName);
	// }

	// private static File makeIfNotExistDir(String dirName) {
	// final File file = new File(dirName);
	// if (!file.exists()) {
	// if (android.os.Environment.getExternalStorageState().equals(
	// android.os.Environment.MEDIA_MOUNTED)) {
	// file.mkdirs();
	// }
	// }
	// return file;
	// }

	private static void makeIfNotExistFileDir(File file) {
		try {
			String fileName = file.getAbsolutePath();
			String fileDirName = fileName.substring(0,
					fileName.lastIndexOf('/'));
			File dirFile = new File(fileDirName);
			if (!dirFile.exists()) {
				if (android.os.Environment.getExternalStorageState().equals(
						android.os.Environment.MEDIA_MOUNTED)) {
					dirFile.mkdirs();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveMapBitmapFile(String fileName, Bitmap bitmap) {
		File file = getMapMapFile(fileName);
		try {
			if (!file.exists()) {
				makeIfNotExistFileDir(file);
			}
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isMapBitmapFileExist(String fileName) {
		File file = getMapMapFile(fileName);
		return file.exists();
	}

	public static InputStream getMapBitmapFileInputStream(String fileName) {
		File file = getMapMapFile(fileName);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fis;
	}

	// public static Bitmap getBitmap(String urlString, String bitmapPath) {
	// Bitmap targetBitmap = null;
	// if (FileUtil.isMapBitmapFileExist(bitmapPath)) {
	// InputStream inputStream = FileUtil
	// .getMapBitmapFileInputStream(bitmapPath);
	// targetBitmap = BitmapFactory.decodeStream(inputStream);
	// } else {
	// targetBitmap = BitmapUtil.getBitmapFromNet(urlString);
	// if (targetBitmap != null && !targetBitmap.isRecycled()) {
	// FileUtil.saveMapBitmapFile(bitmapPath, targetBitmap);
	// }
	// }
	// return targetBitmap;
	// }
	//
	// public static BitmapDrawable getBitmapDrawable(Context context,
	// String urlString, String bitmapPath) {
	// // BitmapDrawable targetBitmapDrawable = null;
	// Bitmap targetBitmap = null;
	// if (FileUtil.isMapBitmapFileExist(bitmapPath)) {
	// InputStream inputStream = FileUtil
	// .getMapBitmapFileInputStream(bitmapPath);
	// targetBitmap = BitmapFactory.decodeStream(inputStream);
	// } else {
	// targetBitmap = BitmapUtil.getBitmapFromNet(urlString);
	// if (targetBitmap != null && !targetBitmap.isRecycled()) {
	// FileUtil.saveMapBitmapFile(bitmapPath, targetBitmap);
	// }
	// }
	// BitmapDrawable targetBitmapDrawable = new BitmapDrawable(targetBitmap);
	// targetBitmapDrawable.setTargetDensity(ResourcesManager
	// .getInstance(context).getResources().getDisplayMetrics());
	// return targetBitmapDrawable;
	// }
}
