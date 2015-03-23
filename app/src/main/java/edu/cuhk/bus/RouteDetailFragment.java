package edu.cuhk.bus;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class RouteDetailFragment extends DialogFragment {

    public static final String ARG_ITEM_ID = "item_id";


    // DummyContent.DummyItem mItem;
    int routeId = 0;

    public RouteDetailFragment() {
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.setTitle(R.string.menu_info);
//        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return d;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            this.routeId = Integer.parseInt(getArguments().getString(
                    ARG_ITEM_ID));

            // mItem =
            // DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

//		// Get tracker.
//		Tracker t = ((CUBusApplication) getActivity().getApplication())
//				.getTracker(TrackerName.APP_TRACKER);
//
//		// Set screen name.
//		// Where path is a String representing the screen name.
//		t.setScreenName(this.getClass().getName());
//
//		// Send a screen view.
//		t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View rootView = inflater.inflate(R.layout.fragment_route_detail,
        // container, false);
        // if (mItem != null) {
        // ((TextView)
        // rootView.findViewById(R.id.route_detail)).setText(mItem.content);
        // }
        // return rootView;
        View v = inflater.inflate(R.layout.bus_detail, container, false);

        String[] notes = this.getResources().getStringArray(
                R.array.bus_route_notes);

        String[] routes = this.getResources()
                .getStringArray(R.array.bus_routes);
        String[] routeNames = this.getResources().getStringArray(
                R.array.bus_route_names);

        int[] times = {R.array.bus_route_1, R.array.bus_route_2,
                R.array.bus_route_3, R.array.bus_route_4, R.array.bus_route_5,
                R.array.bus_route_6, R.array.bus_route_7, R.array.bus_route_8,
                R.array.bus_route_n, R.array.bus_route_h,
                R.array.bus_route_la39, R.array.bus_route_lih,
                R.array.bus_route_lu, R.array.bus_route_ld};

        int[] stops = {R.array.bus_route_1_stops, R.array.bus_route_2_stops,
                R.array.bus_route_3_stops, R.array.bus_route_4_stops,
                R.array.bus_route_5_stops, R.array.bus_route_6_stops,
                R.array.bus_route_7_stops, R.array.bus_route_8_stops,
                R.array.bus_route_n_stops, R.array.bus_route_h_stops,
                R.array.bus_route_la39_stops, R.array.bus_route_lih_stops,
                R.array.bus_route_lu_stops, R.array.bus_route_ld_stops};

        ArrayAdapter<String> stopsAdapter = new ArrayAdapter<String>(
                this.getActivity(), R.layout.bus_detail_item, this
                .getActivity().getResources()
                .getStringArray(stops[routeId]));
        ArrayAdapter<String> timesAdapter = new ArrayAdapter<String>(
                this.getActivity(), R.layout.bus_detail_item, this
                .getActivity().getResources()
                .getStringArray(times[routeId]));
        //
        // super.onCreate(savedInstanceState);
        // setContentView(R.layout.bus_detail);

        // Bundle bundle = this.getIntent().getExtras();
        // int routeId = mNum;

        TextView routeTV = (TextView) v.findViewById(R.id.route);
        TextView routeDescTV = (TextView) v.findViewById(R.id.desc);
        TextView noteTV = (TextView) v.findViewById(R.id.note);

        routeTV.setText(routes[routeId]);
        routeDescTV.setText(routeNames[routeId]);
        noteTV.setText(notes[routeId]);

        GridView timeView = (GridView) v.findViewById(R.id.times);
        GridView stopsView = (GridView) v.findViewById(R.id.stops);

        timeView.setAdapter(timesAdapter);
        stopsView.setAdapter(stopsAdapter);


        ViewCompat.setTransitionName(v.findViewById(R.id.route), RouteDetailActivity.VIEW_NAME_ROUTE);
        ViewCompat.setTransitionName(v.findViewById(R.id.desc), RouteDetailActivity.VIEW_NAME_ROUTE_NAME);
        return v;
    }
}
