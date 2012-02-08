package pdm.test.mappe;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class RunnerActivity extends MapActivity {

	MapView mapView;
	MyLocationOverlay myLocationOverlay;
	RadiusOverlay pos1, pos2, pos3, pos4;
	PendingIntent pendingIntent;

	ProximityBroadcast mProximityBroadcast = new ProximityBroadcast();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mapView = (MapView) findViewById(R.id.mapView);

		myLocationOverlay = new MyLocationOverlay(this, mapView);
		myLocationOverlay.runOnFirstFix(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mapView.getController().animateTo(
						myLocationOverlay.getMyLocation());
				mapView.setStreetView(true);
			}
		});

		pos1 = new RadiusOverlay(new GeoPoint(41901222, 12500882), 400,
				Color.BLUE); // TERMINI
		pos2 = new RadiusOverlay(new GeoPoint(41902622, 12495482), 300,
				Color.BLUE); // REPUBBLICA
		pos3 = new RadiusOverlay(new GeoPoint(41890310, 12492410), 500,
				Color.BLUE); // COLOSSEO
		pos4 = new RadiusOverlay(new GeoPoint(41890492, 12484823), 450,
				Color.BLUE); // ROMOLO E REMO

		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);
		mapView.setStreetView(true);

		mapView.getOverlays().add(pos1);
		mapView.getOverlays().add(pos2);
		mapView.getOverlays().add(pos3);
		mapView.getOverlays().add(pos4);

		mapView.getOverlays().add(myLocationOverlay);
	}

	@Override
	public void onPause() {
		super.onPause();
		myLocationOverlay.disableMyLocation();
		unregisterReceiver(mProximityBroadcast);
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.removeProximityAlert(pendingIntent);
	}

	@Override
	public void onResume() {
		super.onResume();
		myLocationOverlay.enableMyLocation();
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		registerReceiver(mProximityBroadcast,
				new IntentFilter("pdm.test.mappe"));

		Intent intentTermini = new Intent("pdm.test.mappe");
		intentTermini.putExtra("overlay", 1);
		pendingIntent = PendingIntent.getBroadcast(this, 1, intentTermini,
				PendingIntent.FLAG_CANCEL_CURRENT);
		locationManager.addProximityAlert(41901222 * 0.000001,
				12500882 * 0.000001, 400, -1, pendingIntent);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	class ProximityBroadcast extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			Log.d("ECCOCI", "PROXIMITY ALERT");
			Toast.makeText(getApplicationContext(), "Alert di Prossimità",
					Toast.LENGTH_LONG).show();
		}

	}

}