package edu.cuhk.bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;

public class RouteListActivity extends AppCompatActivity implements
        RouteListFragment.Callbacks {
    private boolean mTwoPane;
    private FirebaseAnalytics mFirebaseAnalytics;
//	private int mClickCount = 0;

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        if (findViewById(R.id.route_detail_container) != null) {
            mTwoPane = true;


            // Set page = 0
            Bundle arguments = new Bundle();
            arguments.putString(RouteDetailFragment.ARG_ITEM_ID, "0");
            RouteDetailFragment fragment = new RouteDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.route_detail_container, fragment).commit();
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, new Bundle());

    }

    public void onItemSelected(String id, View routeView, View descView) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(RouteDetailFragment.ARG_ITEM_ID, id);
            RouteDetailFragment fragment = new RouteDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.route_detail_container, fragment).commit();

        } else {
            Intent detailIntent = new Intent(this,
                    RouteDetailActivity.class);
            detailIntent.putExtra(RouteDetailFragment.ARG_ITEM_ID, id);


            /**
             * Now create an {@link android.app.ActivityOptions} instance using the
             * {@link ActivityOptionsCompat#makeSceneTransitionAnimation(Activity, Pair[])} factory
             * method.
             */
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,

                    // Now we provide a list of Pair items which contain the view we can transitioning
                    // from, and the name of the view it is transitioning to, in the launched activity
                    new Pair<View, String>(routeView,
                            RouteDetailActivity.VIEW_NAME_ROUTE),
                    new Pair<View, String>(descView,
                            RouteDetailActivity.VIEW_NAME_ROUTE_NAME));

            ActivityCompat.startActivity(this, detailIntent, activityOptions.toBundle());

        }
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
