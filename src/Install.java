import java.util.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.*;
import com.xl.game.math.Str;
import org.zeroturnaround.zip.ZipUtil;
import com.xl.game.tool.TermTool;
import android.app.Activity;
import com.xl.game.tool.AppTool;
import android.widget.Toast;
import android.os.Handler;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import com.xl.game.tool.FileWork;
import com.xl.game.tool.FilesInfo;
/*
aide安装测试

*/
public class Install
{
	static String tcc = "/data/data/com.aide.ui/files/tcc data/data/com.aide.ui/files/main.c main.o\necho tcc\nls\n";
	
	static String page_aide="com.aide.ui";
	static String versionName="";
	static int versionCode=0;
	static Handler handler =null;
	static Activity activity=null;
	static boolean isNoBackUp=true;
	static String updateUrl = "http://www.yzjlb.net/app/aide/tools/";
	static String str_help="使用本软件安装ndk需要4步：\n"
	+"\n"
	+"1.准备一个ndk的zip压缩包，注意，是直接压缩ndk-supportxxx这个文件夹，如果你没有这个ndk压缩包，请前往下载：http://www.yzjlb.net/app/aide/tools/\n"
	+"2.检测你的aide的ndk安装路径，使用本软件的(2)号命令即可检测(检测方法是创建很多可能是ndk目录的文件夹)\n"
	+"3.重启aide(为什么要重启？？因为重启aide之后，aide会自动检测ndk是否安装，aide会删除files目录下除ndk文件夹以外的文件夹，这样，留下ndk文件夹，就实现了检测功能) ，重启aide之后，使用本软件的1号命令可以查看files目录下ndk文件夹是否创建成功，如果files目录下木有文件夹，那么表示ndk文件夹检测失败。\n"
	+"4.解压ndk的zip压缩包，解压成功，那么恭喜你！ndk安装完成！\n";
	
	
	public static void toast(final String text,final int type)
	{
		if(handler!=null)
		{
			handler.post(new Runnable()
				{

					@Override
					public void run()
					{
						// TODO: Implement this method
						Toast.makeText(activity,text,type).show();
					}
					
				
			});
		}
		else
		{
			System.out.println("handler 获取失败");
		}
	}
	
	
	static String sh = "/sdcard/.aide/gcc2222222.sh";
	
	static String data_files = "/data/data/com.aide.ui/files/";
	//20160801
	public static void main(String[] args) throws InterruptedException, IOException
	{
		//
		Thread.sleep(500);
		if(isNoBackUp){
			data_files = "/data/data/com.aide.ui/no_backup/";
			System.out.println("files路径:"+data_files);
		}
		
  	activity = TermTool.getActivity();
		if(activity==null)
			System.out.println("activity获取失败，可尝试重新运行本程序");
		//获取包名
		else
		{
			if(isNoBackUp){
				data_files = "/data/data/"+AppTool.getPageName(activity)+"/no_backup/";
			}
			else
		data_files = activity.getFilesDir().getPath();
		
		//data_files = activity.getDatabasePath("no_backup").toString();
		//data_files = activity.getDir("no_backup",0).getParentFile().getPath()+"/no_backup";
		page_aide = AppTool.getPageName(activity);
		versionCode = AppTool.getVersionCode(activity);
		versionName = AppTool.getVersionName(activity);
		System.out.println(page_aide);
		System.out.println("当前aide版本:"+versionName+"("+versionCode+")");
		System.out.println("files路径："+data_files);
		handler = new Handler(activity.getMainLooper());
		handler.post(new Runnable()
			{

				@Override
				public void run()
				{
				// TODO: Implement this method
				activity.getActionBar().setTitle("NDK安装助手");
				}

			
		});
		
		}
		System.out.println("AIDE NDK安装助手\n免root安装AIDE的C/C++支持库\n");
		System.out.println("风的影子 制作\n");
		
		toast("风的影子 制作",0);
		System.out.println("请输入以下数字：");
		System.out.println("1.查看aide的files目录下文件");
		System.out.println("2.检测当前aide的ndk安装目录");
		System.out.println("3.备份当前aide的ndk(备份files/ndksupport-xxx目录)");
		System.out.println("4.解压zip到ndk目录(安装ndk)");
		System.out.println("5.查看帮助");
		System.out.println("6.检查更新");
		System.out.println("7.关于");
		System.out.println("8.其它功能");
		
		while(true)
		{
				Scanner input = new Scanner(System.in);
		
		String menu1 = input.nextLine();
		if(menu1.equals("1")) //查看files目录
		{
			File file_aide = new File(data_files);
			File list[] = file_aide.listFiles();
			for(File value:list)
			{
				if(value.isDirectory())
			  System.out.println("[文件夹]"+value.getName());
				else
					System.out.println(value.getName());
			}
		}
		else if(menu1.equals("2")) //检测ndk安装目录
		{
			if(isNoBackUp){
				createDirAll_new();
			}
			else
			createDirAll();
		}
		else if(menu1.equals("3")) //备份
		{
			File file_files = new File(data_files+"");
			File[] files = file_files.listFiles();
			File file_pack=null;
			for(File file:files)
			{
				if(file.getName().startsWith("ndksupport-"))
				{
					file_pack = file;
					break;
				}
			}
			if(file_pack!=null)
			{
				System.out.println("正在备份:"+file_pack.getName());
				ZipUtil.pack(file_pack, new File("/mnt/sdcard/"+file_pack.getName()+".zip"));
				System.out.println("压缩完成："+file_pack.getPath());
				toast("压缩完成",1);
				toast("压缩完成",1);
			}
			else
				System.out.println("未检测到ndk文件夹");
			}
		else if(menu1.equals("4")) //解压
		{
			System.out.println("请预先将要解压的文件放到内存卡(mnt/sdcard)目录\n然后在这里输入要解压的文件名：");
			String name =input.nextLine();
            
			if(!name.endsWith(".tar") && !name.endsWith(".zip") && !name.endsWith(".ZIP"))
			{
				System.out.println("你输入的文件名无效，后缀必须是.zip才行！");
				continue;
			}
            
			File file_undir = new File(data_files);
		File file_ndk = null;
		File file_dir[] = file_undir.listFiles();
		for(File file : file_dir)
		{
		if(file.getName().startsWith("ndksupport-"))
		{
		file_ndk = file;
		break;
		}
		
		}
		    if(file_ndk==null)
			{
				System.out.println("ndk文件夹未找到，无法解压！");
				continue;
			}
			System.out.println("开始解压文件:"+name);
			System.out.println("解压到："+file_ndk);
			System.out.println("可能需要很长很长很长的时间。。。请耐心等待解压完成");
            if(name.endsWith(".tar"))
            {
               FileUtils.unTar("mnt/sdcard/"+name,file_ndk.getPath());
            }
            else
			ZipUtil.unpack(new File("/mnt/sdcard/"+name), file_ndk);
			System.out.println("解压完成！输入任意字符返回上级");
			toast("解压完成",0);
			//创建.installed文件
			
			File file_installed = new File(file_ndk,".installed");
			if(!file_installed.isFile())
			{
			if(file_installed.createNewFile())
			System.out.println(".installed文件创建成功");
			}
			name = input.nextLine();
			
		}
		else if(menu1.equals("5")) //帮助
		{
			dlg_help(activity);
			
		}
		else if(menu1.equals("6")) //检查更新
		{
      System.out.println("\n更新地址："+updateUrl);
		}
		else if(menu1.equals("7")) //关于
		{
      System.out.println("\n风的影子 制作");
		}
		else if(menu1.equals("8")) //其它功能
		{
     // System.out.println("\n支持一些基本命令：(暂未实现)");
      toOther();
			//System.out.println("zip\nunzip\nls\ncd");
		}
		}
	}
	
	//进入其它功能
    public static void toOther()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("1.卸载ndk");
        System.out.println("2.复制system/bin文件夹到ndk");
        System.out.println("3.检测内存卡各文件夹大小");
       while(true)
       {
        String text = input.nextLine();
        if(text.equals("1"))
        {
            File file_aide = new File("data/data/"+page_aide+"/files");
            File list[] = file_aide.listFiles();
            for(File value:list)
            {
                if(value.isDirectory()){
                    System.out.println("正在删除文件夹："+value.getName());
                    FileUtils.delAllFile(value.getPath());
                    System.out.println("[文件夹]"+value.getName()+"删除成功");
                    }
                
			}
        }
        if(text.equals("3"))
        {
            File file = new File("mnt/sdcard/");
            File[] list = file.listFiles();
            for(int i=0;i<list.length;i++)
            {
                File temp = list[i];
                if(temp.isDirectory())
                {
                FilesInfo info = FileWork.getFileInfo(temp,false);
                if(info.mSize>0)
                System.out.println("文件夹："+temp.getName()+"大小："+ FileWork.GetSize( info.mSize));
                }
            }
            System.out.println("检测完成");
        }
       }
        
    }
	
	//保存文本到路径
	public static boolean saveText(String pathfile,String text)
	{
		try
		{
			FileOutputStream outStream =new FileOutputStream(pathfile);
				outStream.write(text.getBytes());
				outStream.close();
		}
		catch (FileNotFoundException e)
		{}
		catch (IOException e)
		{}
		return true;
	}
	
	public static void createDirAll_new()
	{
		File file = null;
		File file_in=null;
		for(int year=17;year<20;year++)
		{
			for(int month=1;month<=12;month++)
			{
				for(int day=1;day<31;day++)
				{
					for(int n=0;n<9;n++){
					String dir = Str.sprintf(data_files+"ndksupport-%d%02d%02d000%d",year,month,day,n);
					file=new File(dir);
					file_in = new File(file,".installed");

					if(file.exists())
					{
						System.out.println(file.getName()+"已存在");
					}
					else
					{
						file.mkdirs();
						try
						{
							file_in.createNewFile();
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
					}
				}
			}
		}
		System.out.println("创建完毕");
	}
	
	//批量创建文件夹用于检测aide安装目录，如果有一个目录木有被aide删除，那么那个目录就是安装目录
	public static void createDirAll()
	{
		File file = null;
		File file_in=null;
		for(int year=2011;year<2020;year++)
		{
			for(int month=1;month<=12;month++)
			{
				for(int day=1;day<31;day++)
				{
					String dir = Str.sprintf(data_files+"ndksupport-%d%02d%02d",year,month,day);
					file=new File(dir);
					file_in = new File(file,".installed");
					
					if(file.exists())
					{
						System.out.println(file.getName()+"已存在");
					}
					else
					{
						file.mkdirs();
					try
					{
					file_in.createNewFile();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
					}
				}
			}
		}
		System.out.println("创建完毕");
	}
	
	
	
	public static void dlg_help(final Context context)
	{
		if(context!=null)
		handler.post(new Runnable()
				{

					@Override
					public void run()
					{
						// TODO: Implement this method
						new AlertDialog.Builder(context)
							.setTitle("帮助")
							.setMessage(str_help)
							.setPositiveButton("确定", new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialogInterface, int n) {


								}
							})


							.setCancelable(false)
							.show();
					}
					
			
		});
		else
			System.out.println(str_help);
		
	}
	
}
