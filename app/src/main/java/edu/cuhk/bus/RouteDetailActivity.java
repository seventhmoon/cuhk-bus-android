package edu.cuhk.bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import edu.cuhk.bus.CUBusApplication.TrackerName;

public class RouteDetailActivity extends ActionBarActivity {
//	private AdView adView;

	public void onStart() {
		super.onStart();
		// EasyTracker.getInstance().activityStart(this); // Add this method.
	}

	public void onStop() {
		super.onStop();
		// EasyTracker.getInstance().activityStop(this); // Add this method.
	}

	// @SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_detail);

//		adView = (AdView) this.findViewById(R.id.ad);
        // setContentView(R.layout.bus_detail);

//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
//		}

		if (savedInstanceState == null) {
			Bundle arguments = new Bundle();
			arguments.putString(RouteDetailFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(RouteDetailFragment.ARG_ITEM_ID));
			RouteDetailFragment fragment = new RouteDetailFragment();
			fragment.setArguments(arguments);
			getFragmentManager().beginTransaction()
					.add(R.id.route_detail_container, fragment).commit();
		}
		startTracking();
//		loadAd();
    }

	private void loadAd() {
		Builder builder = new AdRequest.Builder();
		AdRequest adRequest = builder.addTestDevice(
				AdRequest.DEVICE_ID_EMULATOR).build();
		// .addTestDevice("TEST_DEVICE_ID").build();

//		if (adView != null) {
//			adView.setAdListener(new AdListener() {
//				@Override
//				public void onAdFailedToLoad(int errorCode) {
//					adView.setVisibility(AdView.GONE);
//				}
//
//				public void onAdLoaded() {
//					adView.setVisibility(AdView.VISIBLE);
//				}
//			});
//			adView.loadAd(adRequest);
//		}
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpTo(this, new Intent(this,
					RouteListActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPause() {
//		if (adView != null) {
//			adView.pause();
//		}
        super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
//		if (adView != null) {
//
//			adView.resume();
//		}
    }

	@Override
	public void onDestroy() {
//		if (adView != null) {
//			adView.destroy();
//		}
        super.onDestroy();
	}

	private void startTracking() {
		// Get tracker.
		Tracker t = ((CUBusApplication) this.getApplication()).getTracker(

		TrackerName.APP_TRACKER);

		// Set screen name.
		// Where path is a String representing the screen name.
		t.setScreenName(this.getLocalClassName());

		// Send a screen view.
		t.send(new HitBuilders.AppViewBuilder().build());
	}
}
