package com.example.logcat_crashreport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CustomExceptionHandler implements UncaughtExceptionHandler {

    private UncaughtExceptionHandler defaultUEH;

    private String localPath;

    private String url;
    Context context;
    Uri outputFileUri;
    /* 
     * if any of the parameters is null, the respective functionality 
     * will not be used 
     */
    public CustomExceptionHandler(Context context,String localPath, String url) {
        this.localPath = localPath;
        this.context=context;
        this.url = url;
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    public void uncaughtException(Thread t, Throwable e) 
    {
        String timestamp = "Crash";
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        
        
        String stacktrace="";
        
        stacktrace+="Date & Time: "+  DateUtils.getCurrentDateTime()+"\n";
        
        stacktrace+= result.toString();
        
        printWriter.close();
        String filename = timestamp +".txt";
        
        Log.v("stacktrace",stacktrace);
        Log.v("localPath","--"+localPath);
        if (localPath != null) {
        	
        	SaveAsFile( stacktrace );
            //writeToFile(stacktrace, filename);
        }
        if (url != null) {
            //sendToServer(stacktrace, filename);
        }

        defaultUEH.uncaughtException(t, e);
    }

    private void writeToFile(String stacktrace, String filename) {
        try {
            BufferedWriter bos = new BufferedWriter(new FileWriter(
                    localPath + "/" + filename));
            
            Log.v("Local path",localPath + "/" + filename);
            bos.write(stacktrace);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendToServer(String stacktrace, String filename) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("filename", filename));
        nvps.add(new BasicNameValuePair("stacktrace", stacktrace));
        try {
            httpPost.setEntity(
                    new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
    
    private void SaveAsFile( String ErrorContent )
    {
     try
     {
      Random generator = new Random();
      int random = generator.nextInt(99999);
      String FileName = "stack-" + random + ".txt";
      Log.v("filename","--"+FileName);
      BufferedWriter bos = new BufferedWriter(new FileWriter(
              localPath + "/" + FileName));
      bos.write(ErrorContent);
      bos.flush();
      bos.close();
     }
     catch(IOException ioe) {
      // ...
     }
    }
    
    private String[] GetErrorFileList()
    {
     File dir = new File( localPath + "/");
           // Try to create the files folder if it doesn't exist
           dir.mkdir();
           // Filter for ".stacktrace" files
           FilenameFilter filter = new FilenameFilter() {
                   public boolean accept(File dir, String name) {
                           return name.endsWith(".txt");
                   }
           };
           return dir.list(filter);
    }
    private boolean bIsThereAnyErrorFile()
    {
     return GetErrorFileList().length > 0;
    }
    public void CheckErrorAndSendMail(Context _context )
    {
     
    /**
     * Here you can send the saved crash report to server/email	
     */
     try
     {
      if ( bIsThereAnyErrorFile() )
      {
       String WholeErrorText = "";
       String[] ErrorFileList = GetErrorFileList();
       int curIndex = 0;
       // We limit the number of crash reports to send ( in order not to be too slow )
       final int MaxSendMail = 5;
       
       
       for ( String curString : ErrorFileList )
       {
        Log.d("curString",curString);
    	   
    	 if ( curIndex++ <= MaxSendMail )
        {
         WholeErrorText+="\n New Trace collected :\n";
         WholeErrorText+="=====================\n ";
        
         
   	   File root = new File(Environment.getExternalStorageDirectory(), "iLocare");
   		if (!root.exists()) {
   			root.mkdirs();
   		}
   		
   		 Uri outputFileUri = Uri.fromFile( root );
   		 String filePath = localPath+"/"+curString;
         BufferedReader input =  new BufferedReader(new FileReader(filePath));
         String line;
         while (( line = input.readLine()) != null)
         {
          WholeErrorText += line + "\n";
         }
         input.close();
        }
        // DELETE FILES !!!!
//        File curFile = new File( localPath + "/" + curString );
//        curFile.delete();
       }
       Log.e("custom exception1","custom exception1");
      
     
      }
     }
     catch( Exception e )
     {
      e.printStackTrace();
     }
    }
    

}
