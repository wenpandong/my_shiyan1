package com.example.my_shiyan1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.*;

public class MainActivity extends Activity {
	private ImageView image;//图片对象
	private Bitmap bitmap;
	private Matrix mat = new Matrix();
	private Matrix mat1 = new Matrix();
	private PointF startPoint = new PointF();
	private EditText text;
	
	private int mode;
	private float odis, ndis;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		image = (ImageView) findViewById(R.id.image_wen);
		/*获取sd卡根目录的路径*/
		String path = Environment.getExternalStorageDirectory() + "/";
		/*获取SD卡根目录下的图片文件路径*/
        String name = path + "19.png";
        
        
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 1;        
        Bitmap bm=null;
        bm = BitmapFactory.decodeFile(name, option);

		
		image.setImageBitmap(bm);
		
		mat.set(image.getImageMatrix());
		image.setImageMatrix(mat1);
		
		
	}
	public boolean onTouchEvent(MotionEvent event){
		switch(event.getAction() & MotionEvent.ACTION_MASK){
		case MotionEvent.ACTION_DOWN:
			startPoint.set(event.getX(),event.getY());

			mode = 1;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			odis = FloatMath.sqrt((event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1))+
					(event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)));
			mode = 2;
			break;
		case MotionEvent.ACTION_MOVE:
			if(mode == 1){
				mat.setTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
				//startPoint.set(e.getX(), e.getY());
			}
			if(mode == 2){
				
				ndis = FloatMath.sqrt((event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1))+
						(event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)));
				if(ndis > odis){
					/*放大*/
					float p = ndis /odis;
					mat.setScale(p, p);
				}
				else{
					/*缩小*/
					float p = ndis/odis;
					mat.setScale(p, p);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_POINTER_UP:
			mode = 0;
		}
		
		image.setImageMatrix(mat);
		mat1.set(mat);
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
