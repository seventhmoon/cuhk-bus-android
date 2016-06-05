package edu.cuhk.bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;


public class RouteDetailActivity extends AppCompatActivity {

    private static final String TAG = RouteDetailActivity.class.getSimpleName();

    private FirebaseAnalytics mFirebaseAnalytics;
    //	private AdView mAdView;

    // View name of the header image. Used for activity scene transitions
    public static final String VIEW_NAME_ROUTE = "detail:header:route";

    // View name of the header title. Used for activity scene transitions
    public static final String VIEW_NAME_ROUTE_NAME = "detail:header:name";

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    // @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(RouteDetailFragment.ARG_ITEM_ID, getIntent()
                    .getStringExtra(RouteDetailFragment.ARG_ITEM_ID));
            RouteDetailFragment fragment = new RouteDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.route_detail_container, fragment).commit();
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, new Bundle());

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
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
