package com.SomeWhereHalal;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

import com.google.android.maps.MapActivity;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.os.Bundle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.view.ViewPager.LayoutParams;

import android.view.View;
import android.widget.LinearLayout;

public class Gmap extends MapActivity  {
	GeoPoint p;
	MapController mc;
	Paint paintText;
	String cord[];
	class MapOverlay extends com.google.android.maps.Overlay
    {
        @Override
        public boolean draw(Canvas canvas, MapView mapView, 
        boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
 
            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            mapView.getProjection().toPixels(p, screenPts);
 
            //---add the marker---
            Bitmap bmp = BitmapFactory.decodeResource(
                getResources(), R.drawable.pushpin);            
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y-20,null);   
         //   paintText = new Paint(); 
         //   paintText.setColor(Color.RED); 
         //   paintText.setStyle(Style.FILL); 
         //   paintText.setAntiAlias(true);
         //   paintText.setTextSize(20); 
         //
         //   canvas.drawColor(Color.TRANSPARENT);
         //   canvas.drawText("VS2 office", screenPts.x, screenPts.y-100, paintText);
            //canvas.drawText("VS2 Office", screenPts.x, screenPts.y-100, null);
            return true;
        }
    } 
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gmap);
    
   Intent mapIntent = getIntent();
   if (mapIntent.hasExtra("cord"))
   {
   	cord=mapIntent.getStringArrayExtra("cord");
   }
   else
   {
   	cord= new String[]{"20.371237", "72.906340"};
   }
 
  MapView mapView = (MapView) findViewById(R.id.mapView);
  LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
  @SuppressWarnings("deprecation")
  View zoomView = mapView.getZoomControls(); 

  
  
  zoomLayout.addView(zoomView, 
      new LinearLayout.LayoutParams(
          LayoutParams.WRAP_CONTENT, 
          LayoutParams.WRAP_CONTENT)); 
  mapView.displayZoomControls(true);
  mc = mapView.getController();
//  String coordinates[] = {"20.371237", "72.906340"};
  double lat = Double.parseDouble(cord[0]);
  double lng = Double.parseDouble(cord[1]);

  p = new GeoPoint(
      (int) (lat * 1E6), 
      (int) (lng * 1E6));

  mc.animateTo(p);
  mc.setZoom(17); 
  MapOverlay mapOverlay = new MapOverlay();
  List<Overlay> listOfOverlays = mapView.getOverlays();
  listOfOverlays.clear();
  listOfOverlays.add(mapOverlay);       
  mapView.invalidate();
}



@Override
protected boolean isRouteDisplayed() {
	// TODO Auto-generated method stub
	return false;
}

}
