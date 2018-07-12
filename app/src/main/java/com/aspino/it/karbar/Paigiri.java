package com.aspino.it.karbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Paigiri extends AppCompatActivity {
	private String karbarCode;

	private DatabaseHelper dbh;
	private TextView txtContent;
	private SQLiteDatabase db;
	private Typeface FontMitra;
	private ViewPagerAdapter viewPagerAdapter;
	private ViewPager viewPager;
	private TabLayout tabLayout;
	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.peigiri);
	viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
	viewPager=(ViewPager)findViewById(R.id.view_pager);
	tabLayout=(TabLayout) findViewById(R.id.tab_layout);
	dbh=new DatabaseHelper(getApplicationContext());
	try {

		dbh.createDataBase();

	} catch (IOException ioe) {

		throw new Error("Unable to create database");

	}

	try {

		dbh.openDataBase();

	} catch (SQLException sqle) {

		throw sqle;
	}
	try
	{
		karbarCode = getIntent().getStringExtra("karbarCode").toString();

	}
	catch (Exception e)
	{
		Cursor cursor = db.rawQuery("SELECT * FROM login",null);
		for(int i=0;i<cursor.getCount();i++){
			cursor.moveToNext();
			karbarCode=cursor.getString(cursor.getColumnIndex("karbarCode"));
		}
	}
	FontMitra = Typeface.createFromAsset(getAssets(), "font/BMitra.ttf");//set font for page
	txtContent=(TextView)findViewById(R.id.tvTextAbout);
	txtContent.setTypeface(FontMitra);
	//set fragment class and name in adapter
	viewPagerAdapter.addFragment(new Fragment_Service_Run(),"درحال انجام");
	viewPagerAdapter.addFragment(new Fragment_Service_Done(),"انجام شده");
	viewPagerAdapter.addFragment(new Fragment_Service_Cansel(),"لغو شده");
	//set adapter to view pager in class peigiri
	viewPager.setAdapter(viewPagerAdapter);
	//set tablayout to view pager for show diferent page cansel and done and run listView
	tabLayout.setupWithViewPager(viewPager);

}
@Override
public boolean onKeyDown( int keyCode, KeyEvent event )  {
    if ( keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 ) {
    	LoadActivity(MainMenu.class, "karbarCode", karbarCode);
    }

    return super.onKeyDown( keyCode, event );
}
public void LoadActivity(Class<?> Cls, String VariableName, String VariableValue)
	{
		Intent intent = new Intent(getApplicationContext(),Cls);
		intent.putExtra(VariableName, VariableValue);

		this.startActivity(intent);
	}
	public void LoadActivity2(Class<?> Cls, String VariableName, String VariableValue
			, String VariableName2, String VariableValue2) {
		Intent intent = new Intent(getApplicationContext(), Cls);
		intent.putExtra(VariableName, VariableValue);
		intent.putExtra(VariableName2, VariableValue2);

		this.startActivity(intent);
	}
}