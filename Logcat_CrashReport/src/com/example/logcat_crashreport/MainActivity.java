package com.example.logcat_crashreport;

import java.io.File;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener
{
	Button button;
	
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        File root = new File(Environment.getExternalStorageDirectory(), "Crash Report");
			if (!root.exists()) {
				root.mkdirs();
			}
			Uri outputFileUri = Uri.fromFile( root );
			
			try 
	        {
				Class.forName("android.os.AsyncTask");
			} 
	        catch (ClassNotFoundException e)
	        {
				e.printStackTrace();
			}
	       
			Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this,
		        		outputFileUri.getPath(),""));
	        
	        CustomExceptionHandler error=new CustomExceptionHandler (this,outputFileUri.getPath(),"");
	        error.CheckErrorAndSendMail(this);
	       
	        button=(Button)findViewById(R.id.button);
	        button.setOnClickListener(this);
	  }
	@Override
	public void onClick(View arg0) 
	{
		
	}
  }
