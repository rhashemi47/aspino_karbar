package com.aspino.it.karbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class About extends AppCompatActivity {
	private String karbarCode;

	private DatabaseHelper dbh;
	private TextView txtContent;
	private SQLiteDatabase db;
//	private Button btnOrder;
//	private Button btnAcceptOrder;
//	private Button btncredite;
//	private GoogleMap map;
	private Typeface FontMitra;
//	private LatLng point;

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.about);
//	btnOrder=(Button)findViewById(R.id.btnOrderBottom);
//	btnAcceptOrder=(Button)findViewById(R.id.btnAcceptOrderBottom);
//	btncredite=(Button)findViewById(R.id.btncrediteBottom);
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
//	String Query="UPDATE UpdateApp SET Status='1'";
//	db=dbh.getWritableDatabase();
//	db.execSQL(Query);
//	db=dbh.getReadableDatabase();
//	Cursor cursor2 = db.rawQuery("SELECT OrdersService.*,Servicesdetails.name FROM OrdersService " +
//			"LEFT JOIN " +
//			"Servicesdetails ON " +
//			"Servicesdetails.code=OrdersService.ServiceDetaileCode WHERE Status ='0'", null);
//	if (cursor2.getCount() > 0) {
//		btnOrder.setText("درخواست ها: " + cursor2.getCount());
//	}
//	cursor2 = db.rawQuery("SELECT * FROM OrdersService WHERE Status in (1,2,6,7,12,13)", null);
//	if (cursor2.getCount() > 0) {
//		btnAcceptOrder.setText("پذیرفته شده ها: " + cursor2.getCount());
//	}
//	cursor2 = db.rawQuery("SELECT * FROM AmountCredit", null);
//	if (cursor2.getCount() > 0) {
//		cursor2.moveToNext();
//		try {
//			String splitStr[]=cursor2.getString(cursor2.getColumnIndex("Amount")).toString().split("\\.");
//			if(splitStr[1].compareTo("00")==0)
//			{
//				btncredite.setText("اعتبار: " +splitStr[0]);
//			}
//			else
//			{
//				btncredite.setText("اعتبار: " + cursor2.getString(cursor2.getColumnIndex("Amount")));
//			}
//
//		} catch (Exception ex) {
//			btncredite.setText("اعتبار: " + "0");
//		}
//	}
//	btnOrder.setOnClickListener(new View.OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			String QueryCustom;
//			QueryCustom="SELECT OrdersService.*,Servicesdetails.name FROM OrdersService " +
//					"LEFT JOIN " +
//					"Servicesdetails ON " +
//					"Servicesdetails.code=OrdersService.ServiceDetaileCode WHERE Status ='0'";
//			LoadActivity2(List_Order.class, "karbarCode", karbarCode, "QueryCustom", QueryCustom);
//		}
//	});
//	btnAcceptOrder.setOnClickListener(new View.OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			String QueryCustom;
//			QueryCustom="SELECT OrdersService.*,Servicesdetails.name FROM OrdersService " +
//					"LEFT JOIN " +
//					"Servicesdetails ON " +
//					"Servicesdetails.code=OrdersService.ServiceDetaileCode WHERE Status in (1,2,6,7,12,13)";
//			LoadActivity2(List_Order.class, "karbarCode", karbarCode, "QueryCustom", QueryCustom);
//		}
//	});
//	btncredite.setOnClickListener(new View.OnClickListener() {
//		@Override
//		public void onClick(View v) {
//
//			LoadActivity(Credit.class, "karbarCode", karbarCode);
//		}
//	});
//	((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map3)).getMapAsync(new OnMapReadyCallback() {
//		@Override
//
//		public void onMapReady(GoogleMap googleMap) {
//			map = googleMap;
//			point = new LatLng(0, 0);
//			map.addMarker(new MarkerOptions().position(point).title("آسپینو").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
//			map.moveCamera(CameraUpdateFactory.newLatLngZoom(point,17));
//			map.getUiSettings().setZoomControlsEnabled(true);
//		}
//	});
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
