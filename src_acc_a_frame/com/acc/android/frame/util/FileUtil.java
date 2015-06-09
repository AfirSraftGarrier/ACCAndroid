/**
 * 
 * ACCAFrame - ACC Android Development Platform
 * Copyright (c) 2014, AfirSraftGarrier, afirsraftgarrier@qq.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
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
import java.util.List;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Base64;

import com.acc.android.frame.model.UploadFile;
import com.acc.android.frame.util.constant.UploadConstant;
import com.acc.frame.util.ListUtil;
import com.acc.frame.util.constant.HttpConstant;

public class FileUtil {
	private static String PATH_ROOT;
	public static String SECOND_PATH_ROOT;

	// private static String PATH_CONFIG;
	// private static String PATH_DATA;
	// private static String PATH_FILE;
	// private static String PATH_MAP_CONFIG;
	// private static String PATH_MAP_MAP;

	// private static String PATH_MAP_MAP_RESOURCE_IMAGES;

	public static List<UploadFile> getUploadImageFiles(List<String> imagePaths) {
		if (!ListUtil.isEmpty(imagePaths)) {
			List<UploadFile> uploadFiles = ListUtil.getArrayList();
			for (String imagePath : imagePaths) {
				UploadFile uploadFile = new UploadFile();
				uploadFile.setContentType(UploadConstant.JPG);
				uploadFile.setName("file");
				uploadFile.setFilePath(imagePath);
				uploadFiles.add(uploadFile);
			}
			return uploadFiles;
		}
		return null;
	}

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

	public static String getStringFromFile(File file
	// , String encoding
	) {
		InputStreamReader read;
		StringBuffer sb = new StringBuffer();
		try {
			read = new InputStreamReader(new FileInputStream(file),
			// encoding == null ?
					HttpConstant.ENCODE
			// : encoding
			);
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

	public static boolean saveStringToFile(File file, String string) {
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
		// String encoding = "utf-8";
		try {
			if (!file.exists()) {
				makeIfNotExistFileDir(file);
			}
			file.createNewFile();
			fos = new FileOutputStream(file);
			fos.write(string.getBytes(HttpConstant.ENCODE));
			fos.close();
			return true;
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
		return false;
	}

	// public static File getConfigFile(String fileName) {
	//
	// }
	//
	// public static File getDataFile() {
	//
	// }
	//

	// public static File getMapConfigFile(String fileName) {
	// String mapConfigPathName = getPathMapConfig();
	// // makeIfNotExistDir(mapConf/igPathName);
	// return new File(mapConfigPathName + File.separator + fileName);
	// }

	// public static File getMapMapFile(String fileName) {
	// String mapMapPathName = getPathFile();
	// // makeIfNotExistDir(mapMapPathName);
	// return new File(mapMapPathName + File.separator + fileName);
	// }

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
			PATH_ROOT += File.separator + "ACC" + File.separator
					+ (SECOND_PATH_ROOT != null ? SECOND_PATH_ROOT : "COMMON");
			makeIfNotExistFileRealDir(PATH_ROOT);
		}
		return PATH_ROOT;
	}

	// public static String getPathFile() {
	// if (PATH_MAP_CONFIG == null) {
	// PATH_MAP_CONFIG = getPathRoot() + "file";
	// }
	// return PATH_MAP_CONFIG;
	// }

	public static boolean isFileExist(String fileName) {
		File file = getFile(fileName);
		// LogUtil.systemOut(file.getPath());
		// LogUtil.systemOut(file.exists());
		return file.exists();
	}

	public static File getFile(String fileName) {
		// String filePathName = getPathFile();
		// makeIfNotExistDir(mapMapPathName);
		return new File(getFilePath(fileName));
	}

	// private static void makeSureInitDir(String dir) {
	// File file = new File(dir);
	// if (!file.exists()) {
	// // boolean isMakeSuccess =
	// file.mkdirs();
	// // LogUtil.systemOut("XXXXXXXXXXXXX");
	// // LogUtil.systemOut(isMakeSuccess);
	// }
	// }

	public static String getFilePath(String fileName) {
		return getPathRoot() + File.separator + fileName;
		// makeSureInitDir(filePathName);
		// return filePathName + fileName;
	}

	public static String getTimestampImageFilePath(String prefixString) {
		// String sdfds = getFilePath((prefixString != null ? prefixString + "_"
		// : "") + System.currentTimeMillis() + ".jpg");
		// System.out.println(sdfds);
		return getFilePath((prefixString != null ? prefixString + "_" : "")
				+ System.currentTimeMillis() + ".jpg");
	}

	// public static File getTimestampImageFile(String prefixString) {
	// return new File(getTimestampImageFilePath(prefixString));
	// }

	//
	// public static String getPathMapConfig() {
	// if (PATH_MAP_CONFIG == null) {
	// PATH_MAP_CONFIG = getPathRoot() + "MAP/CONFIG";
	// }
	// return PATH_MAP_CONFIG;
	// }
	//
	// public static String getPathMapMap() {
	// if (PATH_MAP_MAP == null) {
	// PATH_MAP_MAP = getPathRoot() + "MAP/MAP";
	// }
	// return PATH_MAP_MAP;
	// }

	// public static String getTestPath() {
	// return getPathMapMap() + "/resources/images/style_new/comb.png";
	// }

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
		makeIfNotExistFileDir(file.getAbsolutePath());
	}

	public static void makeIfNotExistFileDir(String filePath) {
		try {
			String fileName = filePath;
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

	public static void makeIfNotExistFileRealDir(String filePath) {
		try {
			String fileName = filePath;
			// String fileDirName = fileName.substring(0,
			// fileName.lastIndexOf('/'));
			File dirFile = new File(filePath);
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
		File file = new File(fileName);
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

	// public static boolean isMapBitmapFileExist(String fileName) {
	// File file = getMapMapFile(fileName);
	// return file.exists();
	// }

	// public static InputStream getMapBitmapFileInputStream(String fileName) {
	// File file = getMapMapFile(fileName);
	// FileInputStream fis = null;
	// try {
	// fis = new FileInputStream(file);
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return fis;
	// }

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

	public static boolean isFileExists(String filepath) {
		if (filepath == null) {
			return false;
		}
		File file = new File(filepath);
		if (!file.exists()) {
			return false;
		} else {
			return true;
		}
	}

	public static String getFileRealName(String filepath) {
		String[] nameStrings = filepath.split(File.separator);
		return nameStrings[nameStrings.length - 1];
	}

	public static boolean deleteFile(String strFilePath) {
		boolean result = false;
		if (strFilePath == null || "".equals(strFilePath)) {
			return result;
		}
		File file = new File(strFilePath);
		if (file.isFile() && file.exists()) {
			result = file.delete();
		}
		return result;
	}

	public static void copyFile(String oldPath, String newPath) {
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				makeIfNotExistFileDir(newPath);
				inputStream = new FileInputStream(oldPath);
				fileOutputStream = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inputStream.read(buffer)) != -1) {
					fileOutputStream.write(buffer, 0, byteread);
				}
				// inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}