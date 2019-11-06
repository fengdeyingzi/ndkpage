import java.io.*;
import java.util.*;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import android.renderscript.Path;
import android.util.Log;

public class FileUtils
{
    /*
    递归获取目录下所有文件
    */
	public static Collection<File> listFiles(File file,String[] miniType,boolean ischeck)
	{
		ArrayList<File> filelist = new ArrayList<File>();
		File[] files = file.listFiles();
		for(int i=0;i<files.length;i++)
		{
			if(files[i].isFile())
			{
			for(String type:miniType)
			if(files[i].getPath().endsWith(type))
			{
			filelist.add(files[i]);
			break;
			}
			}
			else 
			{
				Collection<File> filelist2 = listFiles(files[i],miniType,ischeck);
				for(File f:filelist2)
				filelist.add(f);
			}
		}
		return filelist;
	}
	
	//批量删除目录下指定格式文件
	public static void removeFiles(File path,String[] name)
	{
		Collection<File> files = listFiles(path,name,true);
		for(File file:files)
		{
			file.delete();
		}
	}
	
	/*
	创建软链接
	*/
	public void createFile(){
		File file = new File("");
		//file.crea
		
		//java.io.file.Files.createsymboliclink
		
	}
    
    /*
    写入一个文本文件
    */
	public static void writeText(String filename,String info) {
		File file = new File(filename);


		try
		{
			if (!file.isFile())file.createNewFile();
		}
		catch (Exception e)
		{}
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(file, false);
			fileOutputStream.write(info.getBytes());
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    /*
    读取一个文本文件
    */
	public static String read(File file,String encoding) throws IOException
	{
		String content = "";
		//	File file = new File(path);

		if(file.isFile())
		{
			FileInputStream input= new FileInputStream(file);

			byte [] buf=new byte[input.available()];
			input.read(buf);
			content = new String(buf,encoding);
		}
		return content;
	}
    
    

    /**  
          *  新建目录  
          *  @param  folderPath  String  如  c:/fqf  
          *  @return  boolean  
          */
    public static void newFolder(String folderPath)
    {
        try
        {
            String filePath=folderPath;
            filePath=filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            if(!myFilePath.exists())
            { 
                myFilePath.mkdirs();
            }
        }
        catch(Exception e)
        {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }

    /**  
          *  新建文件  
          *  @param  filePathAndName  String  文件路径及名称  如c:/fqf.txt  
          *  @param  fileContent  String  文件内容  
          *  @return  boolean  
          */
    public void newFile(String filePathAndName,String fileContent)
    {

        try
        {
            String filePath =filePathAndName;
            filePath=filePath.toString();//取的路径及文件名
            File myFilePath = new File(filePath);
            /**如果文件不存在就建一个新文件*/
            if(!myFilePath.exists())
            {
                myFilePath.createNewFile();
            }
            FileWriter resultFile =new FileWriter(myFilePath);//用来写入字符文件的便捷类, 在给出 File 对象的情况下构造一个 FileWriter 对象
            PrintWriter myFile=new PrintWriter(resultFile);//向文本输出流打印对象的格式化表示形式,使用指定文件创建不具有自动行刷新的新 PrintWriter。
            String strContent=fileContent;
            myFile.println(strContent);
            resultFile.close();

        }
        catch(Exception e)
        {
            System.out.println("新建文件操作出错");
            e.printStackTrace();

        }

    }

    /**  
          *  删除文件  
          *  @param  filePathAndName  String  文件路径及名称  如c:/fqf.txt  
          *  @param  fileContent  String  
          *  @return  boolean  
          */
    public void delFile(String filePathAndName)
    {
        try
        {
            String filePath= filePathAndName;
            filePath=filePath.toString();
            java.io.File myDelFile = new java.io.File(filePath);
            myDelFile.delete();

        }
        catch(Exception e)
        {
            System.out.println("删除文件操作出错");
            e.printStackTrace();

        }

    }

    /**  
          *  删除文件夹  
          *  @param  filePathAndName  String  文件夹路径及名称  如c:/fqf  
          *  @param  fileContent  String  
          *  @return  boolean  
          */
    public static void delFolder(String folderPath)
    {
        try
        {
            delAllFile(folderPath);//删除完里面所有内容  
            String filePath = folderPath;
            filePath=filePath.toString();
            java.io.File myFilePath =new java.io.File(filePath);
            myFilePath.delete();//删除空文件夹  

        }
        catch(Exception e)
        {
            System.out.println("删除文件夹操作出错");
            e.printStackTrace();

        }

    }

    /**  
          *  删除文件夹里面的所有文件  
          *  @param  path  String  文件夹路径  如  c:/fqf  
          */ 
    public static void delAllFile(String path)
    {
        File file = new File(path);
        if(!file.exists())
        {
            return;
        }
        
        if(file.isFile())
        {
            file.delete();
        }
        else
        {
        String[] tempList = file.list();
        if(tempList==null)
        {
            System.out.println("");
            return;
        }
        File temp = null;
        for(int i =0; i<tempList.length; i++)
        {
            if(path.endsWith(File.separator))
            {
                temp=new File(path+tempList[i]);
            }
            else
            {
                temp=new File(path+File.separator+tempList[i]); 
            }
            if(temp.isFile())
            {
                temp.delete();
            }
            if(temp.isDirectory())
            {
                delAllFile(path+File.separatorChar+tempList[i]);//先删除文件夹里面的文件  
                delFolder(path+File.separatorChar+tempList[i]);//再删除空文件夹  
            }
        }
        }
    }

    /**  
          *  复制单个文件  
          *  @param  oldPath  String  原文件路径  如：c:/fqf.txt  
          *  @param  newPath  String  复制后路径  如：f:/fqf.txt  
          *  @return  boolean  
          */
    public static void copyFile(String oldPath,String newPath)
    {
        try
        {
//           int  bytesum  =  0;  
            int byteread = 0;
            File oldfile = new File(oldPath); 
            if(oldfile.isFile())
            {//文件存在时  
                InputStream inStream=new FileInputStream(oldPath);//读入原文件 
                FileOutputStream out = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
//               int  length;  
                while((byteread=inStream.read(buffer))!=-1)
                {
//                   bytesum  +=  byteread;  //字节数  文件大小  
//                   System.out.println(bytesum);  
                    out.write(buffer, 0, byteread); 
                }
                inStream.close();

                //fs.flush();
                out.close();
            }
        }
        catch(Exception e)
        {
            System.out.println("复制单个文件操作出错"); 
            e.printStackTrace();

        }

    }

    /**  
          *  复制整个文件夹内容  
          *  @param  oldPath  String  原文件路径  如：c:/fqf  
          *  @param  newPath  String  复制后路径  如：f:/fqf/ff  
          *  @return  boolean  
          */
    public static void copyFolder(String oldPath,String newPath)
    {

        try
        {

            Log.e("copyFolder", ""+oldPath+" "+newPath);
            new File(newPath).mkdirs();//如果文件夹不存在  则建立新文件夹  
            File a=new File(oldPath);
            String[] file=a.list();
            File temp=null;
            for(int i = 0; i<file.length; i++)
            {
                if(oldPath.endsWith(File.separator))
                {
                    temp=new File(oldPath+file[i]);
                }
                else
                {
                    temp=new File(oldPath+File.separator+file[i]);
                }

                if(temp.isFile())
                {
                    FileInputStream input= new FileInputStream(temp); 
                    FileOutputStream output=new FileOutputStream(newPath+"/"+
                                                                 (temp.getName()).toString());
                    byte[] b = new byte[1024*5];
                    int len;
                    while((len=input.read(b))!=-1)
                    {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if(temp.isDirectory())
                {//如果是子文件夹  

                    copyFolder(oldPath+"/"+file[i], newPath+"/"+file[i]); 
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("复制整个文件夹内容操作出错"); 
            e.printStackTrace();

        }

    }

    /**  
          *  移动文件到指定目录  
          *  @param  oldPath  String  如：c:/fqf.txt  
          *  @param  newPath  String  如：d:/fqf.txt  
          */
    public void moveFile(String oldPath,String newPath)
    {
        copyFile(oldPath, newPath);
        delFile(oldPath);

    }

    /**  
          *  移动文件到指定目录  
          *  @param  oldPath  String  如：c:/fqf.txt  
          *  @param  newPath  String  如：d:/fqf.txt  
          */
    public void moveFolder(String oldPath,String newPath)
    {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);

    }
    
// 拷贝文件
    private void copyFile2(String source,String dest)
    {
        try
        {
            File in = new File(source);
            File out = new File(dest);
            FileInputStream inFile = new FileInputStream(in);
            FileOutputStream outFile = new FileOutputStream(out);
            byte[] buffer = new byte[10240];
            int i = 0;
            while((i=inFile.read(buffer))!=-1)
            {
                outFile.write(buffer, 0, i);
            }//end while
            inFile.close();
            outFile.close();
        }//end try
        catch(Exception e)
        {

        }//end catch
    }//end copyFile
    
	
      //------------------------------------------------------------------------------------------------------
         /** 
     3      * 解压tar.gz 文件 
     4      * @param file 要解压的tar.gz文件对象 
     5      * @param outputDir 要解压到某个指定的目录下 
     6      * @throws IOException 
     7      */ 
     /*
         public static void unTarGz(File file,String outputDir) throws IOException{  
                 TarInputStream tarIn = null;  
        10         try{  
            11             tarIn = new TarInputStream(new GZIPInputStream(  
                                                          12                     new BufferedInputStream(new FileInputStream(file))),  
            13                     1024 * 2);  
            14               
            15             createDirectory(outputDir,null);//创建输出目录  
            16 
            17             TarEntry entry = null;  
            18             while( (entry = tarIn.getNextEntry()) != null ){  
                19                   
                20                 if(entry.isDirectory()){//是目录
                    21                     entry.getName();
                    22                     createDirectory(outputDir,entry.getName());//创建空目录  
                    23                 }else{//是文件
                    24                     File tmpFile = new File(outputDir + "/" + entry.getName());  
                    25                     createDirectory(tmpFile.getParent() + "/",null);//创建输出目录  
                    26                     OutputStream out = null;  
                    27                     try{  
                        28                         out = new FileOutputStream(tmpFile);  
                        29                         int length = 0;  
                        30                           
                        31                         byte[] b = new byte[2048];  
                        32                           
                        33                         while((length = tarIn.read(b)) != -1){  
                            34                             out.write(b, 0, length);  
                            35                         }  
                        36                       
                        37                     }catch(IOException ex){  
                        38                         throw ex;  
                        39                     }finally{  
                        40                           
                        41                         if(out!=null)  
                            42                             out.close();  
                        43                     }  
                    44                 }
                45             }  
            46         }catch(IOException ex){  
            47             throw new IOException("解压归档文件出现异常",ex);  
            48         } finally{  
            49             try{  
                50                 if(tarIn != null){  
                    51                     tarIn.close();  
                    52                 }  
                53             }catch(IOException ex){  
                54                 throw new IOException("关闭tarFile出现异常",ex);  
                55             }  
            56         }  
        57     }  
        */
        
    public static void unTar(final String pathToTarFile, final String tempDirectory) throws IOException {
        try(TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(pathToTarFile))) {
            TarArchiveEntry entry = tais.getNextTarEntry();
            while (null != entry) {
                final String destPath = tempDirectory+(entry.getName());
                if (!entry.isDirectory()) {
                    final FileOutputStream fout = new FileOutputStream(destPath);
                    final byte[] buffer = new byte[8192];
                    int n;
                    while (-1 != (n = tais.read(buffer))) {
                        fout.write(buffer,0,n);
                    }
                    fout.close();
                }
                entry = tais.getNextTarEntry();
            }

        }
    }
        
}
