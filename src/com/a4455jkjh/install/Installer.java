package com.a4455jkjh.install;

import android.app.Application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Installer {
	public static final int CODE_3_2_171025 = 1710250003;
	public static final int CODE_3_2_2 = 1708010004;
	public static final int CODE_2_5_5 = 1;
	public static final int CODE_3_2_170801 = 1708010004;
	
	public static String
	NDK_3_2_2="20160801",
	NDK_3_2_2_CN145="20150805", //我的aide
	NDK_2_5_5="20130506",
	NDK_2_9_6="20141105"
	;
	private static final byte[] tmpdata = new byte[2048];

	public Installer() {
	Installer installer = this;
	}

	public static void install(String str, boolean z) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, SecurityException, IllegalAccessException, IllegalArgumentException, IOException, InterruptedException {
	boolean z2 = z;
	File file  = new File(str);
	install(file, z2);
	}

	public static void install(File file, boolean z) throws IOException, NoSuchMethodException, InterruptedException, InvocationTargetException, SecurityException, IllegalAccessException, IllegalArgumentException, ClassNotFoundException {
	install(file, z, 0);
	}

	public static void install(File file, boolean z, int i) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, SecurityException, IllegalAccessException, IllegalArgumentException, IOException, InterruptedException {
	install(getInstallDir(i), file, z);
	}

	public static void install(String str, boolean z, int i) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, SecurityException, IllegalAccessException, IllegalArgumentException, IOException, InterruptedException {
	boolean z2 = z;
	int i2 = i;
	File file =  new File(str);
	install(file, z2, i2);
	}

	private static void install(File file, File file2, boolean z) throws IOException, InterruptedException {
	File file3 = file;
	boolean z2 = z;
	ZipFile zipFile = new ZipFile(file2);
	ZipFile zipFile3 = zipFile;
	System.out.println("删除旧版本NDK");
	if (z2) {
	for (File file4 : file3.getParentFile().listFiles()) {
	if (file4.getName().startsWith("ndksupport-")) {
	delete(file4);
	}
	}
	} else {
	delete(file3);
	}
	System.out.println("解压zip");
	zipFile = zipFile3;
	File file5 = new File(file3, "tmp");
	unzip(zipFile, file5);
	execute(file3);
	}

	private static void execute(File file) throws IOException, InterruptedException {
	File file2 = file;
	ArrayList arrayList = new ArrayList(3);
	ArrayList arrayList3 = arrayList;
	boolean add = arrayList3.add("/system/bin/sh");
	arrayList = arrayList3;
	StringBuilder stringBuilder = new StringBuilder();
	add = arrayList.add(stringBuilder.append(file2.getAbsolutePath()).append("/tmp/install.sh").toString());
	add = arrayList3.add(file2.getAbsolutePath());
	System.out.println(arrayList3);
	ProcessBuilder processBuilder =  new ProcessBuilder(arrayList3);
	ProcessBuilder processBuilder3 = processBuilder;
	processBuilder = processBuilder3.redirectErrorStream(true);
	System.out.println("正在安装");
	Process start = processBuilder3.start();
	Reader reader =  new InputStreamReader(start.getInputStream());
	Reader reader2 = reader;
	BufferedReader bufferedReader = new BufferedReader(reader2);
	BufferedReader bufferedReader3 = bufferedReader;
	while (true) {
	String readLine = bufferedReader3.readLine();
	String str = readLine;
	if (readLine == null) {
	break;
	}
	System.out.println(str);
	}
	if (start.waitFor() == 0) {
	System.out.println("安装成功");
	} else {
	System.out.println("安装失败");
	}
	}

	private static void unzip(ZipFile zipFile, File file) throws IOException {
	ZipFile zipFile2 = zipFile;
	File file2 = file;
	boolean mkdirs;
	Enumeration entries = zipFile2.entries();
	if (!file2.exists()) {
	mkdirs = file2.mkdirs();
	}
	while (entries.hasMoreElements()) {
	ZipEntry zipEntry = (ZipEntry) entries.nextElement();
	File file3 = new File(file2, zipEntry.getName());
	File file5 = file3;
	System.out.println(file5);
	if (zipEntry.isDirectory()) {
	mkdirs = file5.mkdirs();
	} else {
	InputStream inputStream = zipFile2.getInputStream(zipEntry);
	OutputStream outputStream =  new FileOutputStream(file5);
	OutputStream outputStream2 = outputStream;
	copy(inputStream, outputStream2);
	inputStream.close();
	outputStream2.close();
	}
	}
	}

	private static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
	InputStream inputStream2 = inputStream;
	OutputStream outputStream2 = outputStream;
	while (true) {
	int read = inputStream2.read(tmpdata);
	int i = read;
	if (read > 0) {
	outputStream2.write(tmpdata, 0, i);
	} else {
	return;
	}
	}
	}

	private static File getInstallDir(int i) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
	int i2 = i;
	Application application = (Application) Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication", new Class[0]).invoke(null, new Object[0]);
	if (i2 == 0) {
	try {
	i2 = application.getPackageManager().getPackageInfo(application.getPackageName(), 0).versionCode;
	} catch (Throwable e) {
	Throwable th = e;
	throw new IOException(th);
	}
	}
	switch (i2) {
	case CODE_3_2_171025 /*1710250003*/:
		String str = "J0";
		Method declaredMethod = application.getClass().getClassLoader().loadClass("com.aide.ui.build.android.m").getDeclaredMethod(str, new Class[0]);
		declaredMethod.setAccessible(true);
		File file = new File(application.getFilesDir(), (String) declaredMethod.invoke(null, new Object[0]));
		return file;
	case CODE_3_2_2:
		
	default:

		StringBuilder stringBuilder = new StringBuilder();
		throw new IOException(stringBuilder.append("不支持的版本").append(i2).toString());

	}
	}

	private static void delete(File file) {
	File file2 = file;
	if (file2.exists()) {
	if (file2.isDirectory()) {
	for (File delete : file2.listFiles()) {
	delete(delete);
	}
	}
	boolean delete2 = file2.delete();
	}
	}

	public static void test(int i) throws NoSuchMethodException, InvocationTargetException, SecurityException, IllegalAccessException, IllegalArgumentException, ClassNotFoundException, IOException {
	System.out.println(getInstallDir(i));
	}
}
