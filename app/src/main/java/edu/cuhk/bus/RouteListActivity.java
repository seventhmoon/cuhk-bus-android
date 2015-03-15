package edu.cuhk.bus;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import edu.cuhk.bus.CUBusApplication.TrackerName;

public class RouteListActivity extends ActionBarActivity implements
		RouteListFragment.Callbacks {
//	private InterstitialAd interstitialAd;
	private boolean mTwoPane;
//	private AdView adView;
//	private int mClickCount = 0;

	public void onStart() {
		super.onStart();
		// EasyTracker.getInstance().activityStart(this); // Add this method.

	}

	public void onStop() {
		super.onStop();
		// EasyTracker.getInstance().activityStop(this); // Add this method.
	}

//	private void prepareInterstitalAd() {
//		// Create the interstitial.
//		interstitialAd = new InterstitialAd(this);
//		interstitialAd.setAdUnitId(getResources().getString(
//				R.string.ad_unit_id_interstitial));
//
//		// Create ad request.
//		Builder builder = new AdRequest.Builder();
//
//		AdRequest adRequest = builder.build();
//
//		// Begin loading your interstitial.
//		interstitialAd.loadAd(adRequest);
//	}

//	private void showInterstitalAd() {
//		if (interstitialAd.isLoaded()) {
//			interstitialAd.show();
//		}
//	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_list);

//        getActionBar().setIcon(R.drawable.ic_launcher);

//		adView = (AdView) this.findViewById(R.id.ad);
		if (findViewById(R.id.route_detail_container) != null) {
			mTwoPane = true;
//			((RouteListFragment) getSupportFragmentManager().findFragmentById(
//					R.id.route_list)).setActivateOnItemClick(true);

			// Set page = 0
			Bundle arguments = new Bundle();
			arguments.putString(RouteDetailFragment.ARG_ITEM_ID, "0");
			RouteDetailFragment fragment = new RouteDetailFragment();
			fragment.setArguments(arguments);
			getFragmentManager().beginTransaction()
					.replace(R.id.route_detail_container, fragment).commit();
		}
		startTracking();
//		loadAd();
//		prepareInterstitalAd();
	}

//	private void loadAd() {
//
//		Builder builder = new AdRequest.Builder();
//		AdRequest adRequest = builder.addTestDevice(
//				AdRequest.DEVICE_ID_EMULATOR).build();
//		// .addTestDevice("TEST_DEVICE_ID").build();
//
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
//	}

	public void onItemSelected(String id) {
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putString(RouteDetailFragment.ARG_ITEM_ID, id);
			RouteDetailFragment fragment = new RouteDetailFragment();
			fragment.setArguments(arguments);
			getFragmentManager().beginTransaction()
					.replace(R.id.route_detail_container, fragment).commit();

		} else {
			// Intent detailIntent = new Intent(this,
			// RouteDetailActivity.class);
			// detailIntent.putExtra(RouteDetailFragment.ARG_ITEM_ID, id);
			// startActivity(detailIntent);

			Bundle arguments = new Bundle();
			arguments.putString(RouteDetailFragment.ARG_ITEM_ID, id);
			RouteDetailFragment fragment = new RouteDetailFragment();
			fragment.setArguments(arguments);
			fragment.show(getFragmentManager(), id);
			// fragment.show(transaction, id);

			// Bundle transitationBundle =
			// ActivityOptions.makeCustomAnimation(this, R.anim.abc_fade_in,
			// R.anim.abc_fade_out).toBundle();

			// android.support.v4.app.FragmentTransaction transaction =
			// getSupportFragmentManager().beginTransaction();
			// // DialogFragment fragment =
			// RouteDetailFragment.newInstance(position);
			// Bundle arguments = new Bundle();
			// arguments.putString(RouteDetailFragment.ARG_ITEM_ID, id);
			// RouteDetailFragment fragment = new RouteDetailFragment();
			// fragment.setArguments(arguments);
			// fragment.show(transaction, id);
			// //transaction.commit();
		}
//		if (mClickCount++ >= 1) {
//			showInterstitalAd();
//		}
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
//			adView.resume();
//		}
		// showInterstitalAd();
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
