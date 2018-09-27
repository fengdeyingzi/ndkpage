package com.xl.game.tool;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import com.xl.game.math.Str;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/*
aide 控制台工具

*/
public class TermTool
{
	
	//获取activity
	public static Activity getActivity() {
	Class activityThreadClass = null;
	try {
	activityThreadClass = Class.forName("android.app.ActivityThread");
	Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
	Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
	activitiesField.setAccessible(true);
	Map activities = (Map) activitiesField.get(activityThread);
	for (Object activityRecord : activities.values()) {
	Class activityRecordClass = activityRecord.getClass();
	Field pausedField = activityRecordClass.getDeclaredField("paused");
	pausedField.setAccessible(true);
	if (!pausedField.getBoolean(activityRecord)) {
	Field activityField = activityRecordClass.getDeclaredField("activity");
	activityField.setAccessible(true);
	Activity activity =  (Activity) activityField.get(activityRecord);
	return activity;
	}
	}
	} catch (ClassNotFoundException e) {
	e.printStackTrace();
	} catch (NoSuchMethodException e) {
	e.printStackTrace();
	} catch (IllegalAccessException e) {
	e.printStackTrace();
	} catch (InvocationTargetException e) {
	e.printStackTrace();
	} catch (NoSuchFieldException e) {
	e.printStackTrace();
	}
	return null;
	}
	
	//获取APP版本号
	
	//获取APP版本
	/**
	 　　* 获取版本号
	 　　* @return 当前应用的版本号　
	 　*/
	public static int getVersionCode(Context context) 
	{
	try
	{
	PackageManager manager = context.getPackageManager();

	PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

	String version = ""+info.versionCode;
	return  info.versionCode;
	}
	catch (PackageManager.NameNotFoundException e)
	{
	return 0;
	}

	}

//获取APP版本名
	public static String getVersionName(Context context) 
	{
	try
	{
	PackageManager manager = context.getPackageManager();

	PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

	String version = info.versionName;
	return  version;
	}
	catch (PackageManager.NameNotFoundException e)
	{
	return null;
	}

	}
	//获取当前ndk文件夹
	
	
	//r文件
	public static int getInt(String className)
	{
	int num=0;
	//拆分类和名字
	int end=Str.strrchr(className.toCharArray(),'.');
	String class_pack= className.substring(0,end);
	String class_name= className.substring(end+1);

	try
	{
	Class r = Class.forName(class_pack);
	Object obj=null;
	try
	{
	obj= r.newInstance();
	//obj = new R.drawable();
	}
	catch (InstantiationException e)
	{
	e.printStackTrace();
	}
	catch (IllegalAccessException e)
	{
	e.printStackTrace();
	}

	try
	{
	Field id = obj.getClass().getField(class_name);
	try
	{
	num = id.getInt(obj);
	}
	catch (IllegalAccessException e)
	{
	e.printStackTrace();
	}
	catch (IllegalArgumentException e)
	{
	e.printStackTrace();
	}
	}
	catch (NoSuchFieldException e)
	{
	e.printStackTrace();
	}
	}
	catch (ClassNotFoundException e)
	{
	e.printStackTrace();
	}
	return num;
	}
	
	//获取一个静态变量值(String/int/boolean)
	public static String getString(String className)
	{
	String num=null;
	//拆分类和名字
	int end=Str.strrchr(className.toCharArray(),'.');
	String class_pack= className.substring(0,end);
	String class_name= className.substring(end+1);

	try
	{
	Class r = Class.forName(class_pack);
	Object obj=null;
	try
	{
	obj= r.newInstance();
	//obj = new R.drawable();
	}
	catch (InstantiationException e)
	{
	e.printStackTrace();
	}
	catch (IllegalAccessException e)
	{
	e.printStackTrace();
	}

	try
	{
	Field id = obj.getClass().getField(class_name);

	try
	{
	if(id.getType().getName().equals("int"))
	{
	num = ""+id.getInt(obj);
	}
	else if(id.getType().getName().equals("boolean"))
	{
		num = ""+id.getBoolean(obj);
	}
	else
		num = (String)id.get(obj);
	}
	catch (IllegalAccessException e)
	{
	e.printStackTrace();
	}
	catch (IllegalArgumentException e)
	{
	e.printStackTrace();
	}
	}
	catch (NoSuchFieldException e)
	{
	e.printStackTrace();
	}
	}
	catch (ClassNotFoundException e)
	{
	e.printStackTrace();
	}
	return num;
	}

	
	//输出当前view树
	public static void printAllViews(View view) {

	List<View> allchildren = new ArrayList<View>();

	if (view instanceof ViewGroup) {

	ViewGroup vp = (ViewGroup) view;

	  for (int i = 0; i < vp.getChildCount(); i++) {
    System.out.println(" ");
	  View viewchild = vp.getChildAt(i);
    System.out.println(view.getClass().getSimpleName());
	  
		printAllViews(viewchild);

	  }

	}
	else
	{
		System.out.println( view.getClass().getSimpleName());
	}



	}
	
	//为根布局下所有按钮设置监听
	public static void setOnClickListenerAllButtons(View.OnClickListener listener)
	{
	//获取根布局
	//ViewGroup group = (ViewGroup)((ViewGroup)getActivity(). findViewById(android.R.id.content)).getChildAt(0);
	//setOnClickListenerAllButtons(group,listener);
	}

	//为所有按钮设置监听
	private static void setOnClickListenerAllButtons(View view,View.OnClickListener listener) {

	List<View> allchildren = new ArrayList<View>();

	if(view instanceof Button)
	{
	if(view.getId()!= -1)
		view.setOnClickListener(listener);
	}
	else if(view instanceof ImageButton)
	{
	if(view.getId()!=-1)
		view.setOnClickListener(listener);
	}

	else if (view instanceof ViewGroup) {

	ViewGroup vp = (ViewGroup) view;

	for (int i = 0; i < vp.getChildCount(); i++) {

	View viewchild = vp.getChildAt(i);

	setOnClickListenerAllButtons(viewchild,listener);

	}

	}



	}
	//添加一个View控件
	public static void addView(View view)
	{
	ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		ViewGroup group =((ViewGroup)(getActivity().findViewById(16908290)));
		ViewGroup linearLayout = (ViewGroup)group.getChildAt(0);
		group. addView(view,params);
	}
	
	
	
	
}
