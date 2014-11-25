package com.acc.android.frame.database.base;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.acc.android.frame.manager.SharedPreferencesManager;
import com.acc.android.frame.util.constant.ACCALibConstant;
import com.acc.frame.util.ListUtil;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public abstract class BaseDatabaseHelper extends OrmLiteSqliteOpenHelper {
	private Map<Class, Dao<?, Integer>> daoMap;

	// name of the database file for your application
	// private static final String DATABASE_NAME="augurcity.db";
	// public static final String DATABASE_REAL_NAME = "hfpda";
	// private final String databaseName;
	// any time you make changes to your database objects, you may have to
	// increase the database version
	// private final static int DATABASEVERSION = 1;

	// public final String databaseDir;

	// public final String databasePath;

	// static {
	// System.out.println(DATABASE_PATH);
	// }

	// private final Context context;

	// the DAO object we use to access the SimpleData table
	// private Dao<CcProblem, Integer> ccProblemDao;
	// private Dao<Task, Integer> taskDao;
	//
	// private Dao<ProblemCategory, Integer> problemCategoryDao;
	// private Dao<ZmDlInfo, Integer> zmDlInfoDao;
	// private Dao<ZmDlSubInfo, Integer> zmDlSubInfoDao;
	// private Dao<BigCategory, Integer> bigCategoryDao;
	// private Dao<SmallCategory, Integer> smallCategoryDao;
	// private Dao<ChildCategory, Integer> childCategoryDao;
	// private Dao<EmergencyDegree, Integer> emergencyDegreeDao;
	// private Dao<MaterialDict, Integer> materialDictDao;
	// private Dao<BelongArea, Integer> belongAreaDao;
	// private Dao<SysCodeItem, Integer> complainSourceDao;
	// private Dao<Dl, Integer> dlDao;
	// private Dao<DlPart, Integer> dlPartDao;
	// private Dao<Ql, Integer> qlDao;
	// private Dao<QlPart, Integer> qlPartDao;

	// private Dao<GPSInfo, Integer> gPSInfoDao = null;
	// private Dao<ComponentInfo, Integer> componentInfoDao = null;
	// private Dao<ComponentInfoUpd, String> componentInfoUpdDao = null;

	public static void postUpdate(Context context) {
		SharedPreferencesManager.getInstance(context).setInt(
				ACCALibConstant.SHAREKEY_DATABASEVERSION,
				SharedPreferencesManager.getInstance(context).getInt(
						ACCALibConstant.SHAREKEY_DATABASEVERSION, 0) + 1);
	}

	public BaseDatabaseHelper(Context context, String databaseName,
			List<Class> classList
	// , List<Class> classList
	) {
		super(context, databaseName, null, SharedPreferencesManager
				.getInstance(context).getInt(
						ACCALibConstant.SHAREKEY_DATABASEVERSION, 0));
		this.initDaoMap(classList);
		// Share
		// this.context = context;
		// this.databaseName = databaseName;
		// // this.databaseName = "acccommon.db";
		// // any time you make changes to your database objects, you may have
		// to
		// // increase the database version
		// // private static int DATABASE_VERSION = 1;
		//
		// this.databaseDir = File.separator + "data"
		// + Environment.getDataDirectory().getAbsolutePath()
		// + File.separator + context.getPackageName();

		// this.databasePath = this.databaseDir + File.separator + DATABASENAME;
	}

	private void initDaoMap(List<Class> classList) {
		this.daoMap = new HashMap<Class, Dao<?, Integer>>();
		if (!ListUtil.isEmpty(classList)) {
			for (Class clazz : classList) {
				this.daoMap.put(clazz, null);
			}
		}
	}

	protected static BaseDatabaseHelper instance;

	public abstract BaseDatabaseHelper getInstance(Context context);

	// public static DatabaseHelper getInstance(Context context,
	// String databaseName, List<Class> classList) {
	// if (instance == null) {
	// instance = new DatabaseHelper(context, databaseName, classList);
	// }
	// // else {
	// // Boolean isOpen = instance.isOpen();
	// // if (!isOpen) {
	// // instance = new DatabaseHelper(context);
	// // // instance.onOpen(instance.getReadableDatabase());
	// // }
	// // }
	// return instance;
	// }

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			if (this.daoMap.keySet().size() > 0) {
				for (Class clazz : this.daoMap.keySet()) {
					TableUtils.createTableIfNotExists(connectionSource, clazz);
				}
			}
			// Log.i(DatabaseHelper.class.getName(), "onCreate");
			// TableUtils
			// .createTableIfNotExists(connectionSource, CcProblem.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// ComponentInfo.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// ComponentInfoUpd.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// SysUser.class);
			// TableUtils.createTableIfNotExists(connectionSource, Task.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// ProblemCategory.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// BigCategory.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// SmallCategory.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// ChildCategory.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// EmergencyDegree.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// ProblemCategory.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// ZmDlInfo.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// MaterialDict.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// BelongArea.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// SysCodeItem.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// ZmDlSubInfo.class);
			// TableUtils.createTableIfNotExists(connectionSource, Dl.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// DlPart.class);
			// TableUtils.createTableIfNotExists(connectionSource, Ql.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// QlPart.class);
			// TableUtils.createTableIfNotExists(connectionSource, Task.class);
			// TableUtils.createTableIfNotExists(connectionSource,
			// GPSInfo.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			if (this.daoMap.keySet().size() > 0) {
				for (Class clazz : this.daoMap.keySet()) {
					TableUtils.createTableIfNotExists(connectionSource, clazz);
				}
			}
			// Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			// TableUtils.dropTable(connectionSource, CcProblem.class, true);
			// TableUtils.dropTable(connectionSource, ComponentInfo.class,
			// true);
			// TableUtils.dropTable(connectionSource, Integer.class, true);
			// TableUtils.dr
			// TableUtils.dropTable(connectionSource, Task.class, true);
			// TableUtils.dropTable(connectionSource, ProblemCategory.class,
			// true);
			// TableUtils.dropTable(connectionSource, BigCategory.class, true);
			// TableUtils.dropTable(connectionSource, SmallCategory.class,
			// true);
			// TableUtils.dropTable(connectionSource, ChildCategory.class,
			// true);
			// TableUtils.dropTable(connectionSource, EmergencyDegree.class,
			// true);
			// TableUtils.dropTable(connectionSource, ProblemCategory.class,
			// true);
			// TableUtils.dropTable(connectionSource, ZmDlInfo.class, true);
			// TableUtils.dropTable(connectionSource, MaterialDict.class, true);
			// TableUtils.dropTable(connectionSource, BelongArea.class, true);
			// TableUtils.dropTable(connectionSource, SysCodeItem.class, true);
			// TableUtils.dropTable(connectionSource, ZmDlSubInfo.class, true);
			// TableUtils.dropTable(connectionSource, Dl.class, true);
			// TableUtils.dropTable(connectionSource, DlPart.class, true);
			// TableUtils.dropTable(connectionSource, Ql.class, true);
			// TableUtils.dropTable(connectionSource, QlPart.class, true);
			// TableUtils.dropTable(connectionSource, Task.class, true);
			// TableUtils.dropTable(connectionSource, GPSInfo.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (Exception e) {
			// Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	// private Dao<ComponentInfo, Integer> getComponentInfoDao()
	// throws SQLException {
	// if (componentInfoDao == null) {
	// componentInfoDao = getDao(ComponentInfo.class);
	// }
	// return componentInfoDao;
	// }
	//
	// private Dao<ComponentInfoUpd, String> getComponentInfoUpdDao()
	// throws SQLException {
	// if (componentInfoUpdDao == null) {
	// componentInfoUpdDao = getDao(ComponentInfoUpd.class);
	// }
	// return componentInfoUpdDao;
	// }

	// private Dao<CcProblem, Integer> getCcProblemDao() throws SQLException {
	// if (ccProblemDao == null) {
	// ccProblemDao = getDao(CcProblem.class);
	// }
	// return ccProblemDao;
	// }
	//
	// // private Dao<SysUser, Integer> getSysUserDao() throws SQLException {
	// // if (userDao == null) {
	// // userDao = getDao(SysUser.class);
	// // }
	// // return userDao;
	// // }
	//
	// private Dao<Task, Integer> getTaskDao() throws SQLException {
	// if (taskDao == null) {
	// taskDao = getDao(Task.class);
	// }
	// return taskDao;
	// }
	//
	// // 问题类型
	// private Dao<ProblemCategory, Integer> getProblemCategoryDao()
	// throws SQLException {
	// if (problemCategoryDao == null) {
	// problemCategoryDao = getDao(ProblemCategory.class);
	// }
	// return problemCategoryDao;
	// }
	//
	// private Dao<ZmDlInfo, Integer> getZmDlInfoDao() throws SQLException {
	// if (zmDlInfoDao == null) {
	// zmDlInfoDao = getDao(ZmDlInfo.class);
	// }
	// return zmDlInfoDao;
	// }
	//
	// private Dao<ZmDlSubInfo, Integer> getZmDlSubInfoDao() throws SQLException
	// {
	// if (zmDlSubInfoDao == null) {
	// zmDlSubInfoDao = getDao(ZmDlSubInfo.class);
	// }
	// return zmDlSubInfoDao;
	// }
	//
	// // 问题大类
	// private Dao<BigCategory, Integer> getBigCategoryDao() throws SQLException
	// {
	// if (bigCategoryDao == null) {
	// bigCategoryDao = getDao(BigCategory.class);
	// }
	// return bigCategoryDao;
	// }
	//
	// // 问题小类
	// private Dao<SmallCategory, Integer> getSmallCategoryDao()
	// throws SQLException {
	// if (smallCategoryDao == null) {
	// smallCategoryDao = getDao(SmallCategory.class);
	// }
	// return smallCategoryDao;
	// }
	//
	// // 问题子类
	// private Dao<ChildCategory, Integer> getChildCategoryDao()
	// throws SQLException {
	// if (childCategoryDao == null) {
	// childCategoryDao = getDao(ChildCategory.class);
	// }
	// return childCategoryDao;
	// }
	//
	// // 损害程度
	// private Dao<EmergencyDegree, Integer> getDamageDegreeDao()
	// throws SQLException {
	// if (emergencyDegreeDao == null) {
	// emergencyDegreeDao = getDao(EmergencyDegree.class);
	// }
	// return emergencyDegreeDao;
	// }
	//
	// private Dao<MaterialDict, Integer> getMaterialDictDao() throws
	// SQLException {
	// if (materialDictDao == null) {
	// materialDictDao = getDao(MaterialDict.class);
	// }
	// return materialDictDao;
	// }
	//
	// private Dao<BelongArea, Integer> getBelongAreaDao() throws SQLException {
	// if (belongAreaDao == null) {
	// belongAreaDao = getDao(BelongArea.class);
	// }
	// return belongAreaDao;
	// }
	//
	// private Dao<SysCodeItem, Integer> getComplainSourceDao()
	// throws SQLException {
	// if (complainSourceDao == null) {
	// complainSourceDao = getDao(SysCodeItem.class);
	// }
	// return complainSourceDao;
	// }
	//
	// private Dao<Dl, Integer> getDlDao() throws SQLException {
	// if (dlDao == null) {
	// dlDao = getDao(Dl.class);
	// }
	// return dlDao;
	// }
	//
	// private Dao<DlPart, Integer> getDlPartDao() throws SQLException {
	// if (dlPartDao == null) {
	// dlPartDao = getDao(DlPart.class);
	// }
	// return dlPartDao;
	// }
	//
	// private Dao<Ql, Integer> getQlDao() throws SQLException {
	// if (qlDao == null) {
	// qlDao = getDao(Ql.class);
	// }
	// return qlDao;
	// }
	//
	// private Dao<QlPart, Integer> getQlPartDao() throws SQLException {
	// if (qlPartDao == null) {
	// qlPartDao = getDao(QlPart.class);
	// }
	// return qlPartDao;
	// }

	// private Dao<GPSInfo, Integer> getGPSInfoDao() throws SQLException {
	// if (gPSInfoDao == null) {
	// gPSInfoDao = getDao(GPSInfo.class);
	// }
	// this.getNewDao(GPSInfo.class);
	// return gPSInfoDao;
	// }

	// public <T> Dao<T, Integer> getNewDao(Class<T> classT) {
	// // TableUtils.dropTable(connectionSource, CcProblem.class, true);
	// // TableUtils.dropTable(connectionSource, ComponentInfo.class, true);
	// // TableUtils.dropTable(connectionSource, SysUser.class, true);
	// // TableUtils.dropTable(connectionSource, Task.class, true);
	// // TableUtils.dropTable(connectionSource, ProblemCategory.class, true);
	// // TableUtils.dropTable(connectionSource, BigCategory.class, true);
	// // TableUtils.dropTable(connectionSource, SmallCategory.class, true);
	// // TableUtils.dropTable(connectionSource, ChildCategory.class, true);
	// // TableUtils.dropTable(connectionSource, DamageDegree.class, true);
	// // TableUtils.dropTable(connectionSource, GPSInfo.class, true);
	// Dao resultDao = null;
	// try {
	// if (classT == CcProblem.class) {
	// resultDao = this.getCcProblemDao();
	// }
	// // else if (classT == ComponentInfo.class) {
	// // resultDao = this.getComponentInfoDao();
	// // }
	// // else if (classT == SysUser.class) {
	// // resultDao = this.getSysUserDao();
	// // }
	// // else if (classT == Task.class) {
	// // resultDao = this.getTaskDao();
	// // }
	// // else if (classT == ProblemCategory.class) {
	// // resultDao = this.getProblemCategoryDao();
	// // }
	// else if (classT == BigCategory.class) {
	// resultDao = this.getBigCategoryDao();
	// } else if (classT == SmallCategory.class) {
	// resultDao = this.getSmallCategoryDao();
	// } else if (classT == ChildCategory.class) {
	// resultDao = this.getChildCategoryDao();
	// } else if (classT == EmergencyDegree.class) {
	// resultDao = this.getDamageDegreeDao();
	// } else if (classT == ProblemCategory.class) {
	// resultDao = this.getProblemCategoryDao();
	// } else if (classT == ZmDlInfo.class) {
	// resultDao = this.getZmDlInfoDao();
	// } else if (classT == ZmDlSubInfo.class) {
	// resultDao = this.getZmDlSubInfoDao();
	// } else if (classT == MaterialDict.class) {
	// resultDao = this.getMaterialDictDao();
	// } else if (classT == BelongArea.class) {
	// resultDao = this.getBelongAreaDao();
	// } else if (classT == SysCodeItem.class) {
	// resultDao = this.getComplainSourceDao();
	// } else if (classT == Dl.class) {
	// resultDao = this.getDlDao();
	// } else if (classT == DlPart.class) {
	// resultDao = this.getDlPartDao();
	// } else if (classT == Ql.class) {
	// resultDao = this.getQlDao();
	// } else if (classT == QlPart.class) {
	// resultDao = this.getQlPartDao();
	// } else if (classT == Task.class) {
	// resultDao = this.getTaskDao();
	// }
	// // else if (classT == GPSInfo.class) {
	// // resultDao = this.getGPSInfoDao();
	// // }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return resultDao;
	// }

	// @Override
	// public void close() {
	// super.close();
	// // userDao = null;
	// // CcProblemDao = null;
	// // taskDao = null;
	// // pCategoryDao = null;
	// // bigCategoryDao = null;
	// // smallCategoryDao = null;
	// // childCategoryDao = null;
	// // damageDegreeDao = null;
	// // gPSInfoDao = null;
	// // componentInfoDao = null;
	// // componentInfoUpdDao = null;
	// }

	// public <T> void deleteDaoAll(T t) throws SQLException {
	// Where where = new DatabaseHelper();
	// .;
	// dao.delete(dao.queryForAll());
	// TableUtils.clearTable(getConnectionSource(), t.getClass());
	// dao.delete(dao.deleteBuilder().where().raw("1=1").prepare());
	// }

	@Override
	public <D extends Dao<T, ?>, T extends Object> D getDao(Class<T> clazz)
			throws SQLException {
		return null;
	};

	// public Dao getDao(Class<T> classT) throws SQLException{
	// // this.getDao(classT);
	// return null;
	// }

	public void addDaoAll(Dao dao, List objects) throws SQLException {
		for (Object object : objects) {
			dao.create(object);
		}
	}

	public <T> void refreshDao(Dao dao, List objects) throws SQLException {
		if (ListUtil.isEmpty(objects)) {
			return;
		}
		TableUtils.clearTable(getConnectionSource(), objects.get(0).getClass());
		// this.deleteDaoAll(dao);
		this.addDaoAll(dao, objects);
	}

	// @Override
	// public synchronized SQLiteDatabase getWritableDatabase() {
	// // if (TestConstant.isUseExistDatabase) {
	// // this.makeSureInitDatabaseFile(this.context);
	// // }
	// // return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
	// // SQLiteDatabase.OPEN_READWRITE);
	// return this.getMWritableDatabase();
	// }
	//
	// @Override
	// public synchronized SQLiteDatabase getReadableDatabase() {
	// // if (TestConstant.isUseExistDatabase) {
	// // this.makeSureInitDatabaseFile(this.context);
	// // }
	// // return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
	// // SQLiteDatabase.OPEN_READONLY);
	// return this.getMReadableDatabase();
	// }

	// private SQLiteDatabase getMReadableDatabase() {
	// // context.getDatabasePath(name)
	// return context.openOrCreateDatabase(DATABASE_NAME,
	// context.MODE_WORLD_READABLE, null);
	// }
	//
	// private SQLiteDatabase getMWritableDatabase() {
	// return context.openOrCreateDatabase(DATABASE_NAME,
	// context.MODE_WORLD_WRITEABLE, null);
	// }

	// private void makeSureInitDatabaseFile(Context context) {
	// if (true) {
	// return;
	// }
	// try {
	// if (!isDatabaseFileExist()) {
	// InputStream inputStream = context.getResources()
	// .openRawResource(R.raw.hfpda);
	// FileOutputStream fileOutputStream = new FileOutputStream(
	// getDatabaseFile());
	// byte[] buffer = new byte[400000];
	// int count = 0;
	// while ((count = inputStream.read(buffer)) > 0) {
	// fileOutputStream.write(buffer, 0, count);
	// }
	// fileOutputStream.close();
	// inputStream.close();
	// }
	// } catch (NotFoundException e) {
	// e.printStackTrace();
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	// public void refreshDatabase() {
	// closeDatabase();
	// context.deleteDatabase(DATABASENAME);
	// // instance = null;
	// // DATABASE_VERSION++;
	// // instance = null;
	// // context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE,
	// // null);
	// }

	// public void closeDatabase() {
	// this.close();
	// }

	// public static File getDatabaseFile() {
	// return new File(DATABASE_PATH);
	// }

	// public static boolean isDatabaseFileExist() {
	// return getDatabaseFile().exists();
	// }

	// public static void deleteDatabaseFile() {
	// new File(DATABASE_PATH).delete();
	// }
}
