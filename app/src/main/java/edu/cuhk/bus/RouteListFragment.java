package edu.cuhk.bus;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

//import android.R;

public class RouteListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static Callbacks sDummyCallbacks = new Callbacks() {
        public void onItemSelected(String id, View routeView, View descView) {
        }
    };
    private Callbacks mCallbacks = sDummyCallbacks;
    //    	private int mActivatedPosition = ListView.INVALID_POSITION;
    private int mActivatedPosition = -1;
    //	private SimpleAdapter simpleAdapter;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // private Handler handler = new Handler();
    // private Runnable refresh;
    private SwipeRefreshLayout mSwipeLayout;

    public RouteListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        list.addAll(loadData());
        View v = inflater.inflate(R.layout.next_bus_grid, container, false);

        mSwipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
//        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);


        mRecyclerView = (RecyclerView) v.findViewById(R.id.gridview);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(this.list);
        mAdapter.setMyItemClickListener(new MyItemClickListener() {

            @Override
            public void onItemClick(View view) {
                int position = mRecyclerView.getChildPosition(view);
                mCallbacks.onItemSelected(String.valueOf(position), view.findViewById(R.id.route), view.findViewById(R.id.desc));

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }

    @Override
    public void onRefresh() {
        mAdapter.notifyDataSetChanged();
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//		simpleAdapter = new SimpleAdapter(getActivity(), list,
//				R.layout.next_bus_cell, new String[] { "route", "time", "desc",
//						"next_time", "last_time" }, new int[] { R.id.route,
//						R.id.time, R.id.desc, R.id.next_time, R.id.last_time });

//		setListAdapter(simpleAdapter);

        // refresh = new Runnable() {
        // public void run() {
        // // Do something
        // refresh();
        // // Toast.makeText(ctx, "R...", Toast.LENGTH_SHORT).show();
        // handler.postDelayed(refresh, 1000 * 15);
        // }
        // };
        // handler.post(refresh);
        // //
        // setListAdapter(new
        // ArrayAdapter<DummyContent.DummyItem>(getActivity(),
        // R.layout.simple_list_item_activated_1,
        // R.id.text1,
        // DummyContent.ITEMS));


    }

    private ArrayList<HashMap<String, Object>> loadData() {
        int[] availableRoutes = {R.array.bus_route_1, R.array.bus_route_2,
                R.array.bus_route_3, R.array.bus_route_4, R.array.bus_route_5,
                R.array.bus_route_6, R.array.bus_route_7, R.array.bus_route_8,
                R.array.bus_route_n, R.array.bus_route_h,
                R.array.bus_route_la39, R.array.bus_route_lih,
                R.array.bus_route_lu, R.array.bus_route_ld};

        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        String[] routes = getActivity().getResources().getStringArray(
                R.array.bus_routes);
        String[] routeNames = getActivity().getResources().getStringArray(
                R.array.bus_route_names);

        Date current = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
//		String currentTime = timeFormat.format(current);
        String today = dateFormat.format(current);

        ArrayList<String> holidays = new ArrayList<String>(
                Arrays.asList(getActivity().getResources().getStringArray(
                        R.array.holidays)));

        ArrayList<String> nonTeachingDays = new ArrayList<String>(
                Arrays.asList(getActivity().getResources().getStringArray(
                        R.array.non_teaching_days)));

        Calendar calendar = Calendar.getInstance();

        boolean[] service = new boolean[routes.length];
        Arrays.fill(service, true);

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                || holidays.contains(today)) {
            service[0] = false;
            service[1] = false;
            service[2] = false;
            service[3] = false;
            service[4] = false;
            service[5] = false;
            service[6] = false;
            service[7] = false;
            service[8] = false;
            service[10] = false;
            service[11] = false;
            service[12] = false;
            service[13] = false;
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || holidays.contains(today)) {
            service[9] = false;
            service[10] = false;
            service[11] = false;
        } else {
            service[9] = false;
        }
        if (nonTeachingDays.contains(today)) {
            service[4] = false;
            service[5] = false;
            service[6] = false;
            service[7] = false;
        }

//		Log.i("***", nonTeachingDays.toString());

        // int[] defaultOrder = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        // int[] nightOrder = { 8, 0, 1, 2, 3, 4, 5, 6, 7, 9 };
        // int[] holidayOrder = { 9, 0, 1, 2, 3, 4, 5, 6, 7, 8 };
        // // List order = Arrays.asList(defaultOrder);
        // int[] order = null;
        //
        // if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
        // || holidays.contains(today)) {
        // for (int i = 0; i <= 8; i++) {
        // service[i] = false;
        // }
        // order = holidayOrder;
        // } else {
        // service[9] = false;
        //
        // if (currentTime.compareTo("18:00") >= 0) {
        // order = nightOrder;
        // } else {
        // order = defaultOrder;
        // }
        // }

        // if (today.compareTo("2012.09.10") <= 0) {
        // int[] array = { 4, 5, 6, 7 };
        // for (int i : array) {
        // service[i] = false;
        // }
        // }

        // for (int i : order) {
        // for (int i: display){

        for (int i = 0; i < availableRoutes.length; i++) {
            String[] times = getActivity().getResources().getStringArray(
                    availableRoutes[i]);
            HashMap<String, Object> map = new HashMap<String, Object>();
            String next = this.findNext(times);
            String last = this.findLast(times);
            String afterNext = this.findAfterNext(times);
            map.put("route", routes[i]);
            if (service[i]) {
                map.put("last_time", last == null ? getActivity()
                        .getResources().getString(R.string.bus_out_of_service)
                        : last);
                map.put("next_time", afterNext == null ? getActivity()
                        .getResources().getString(R.string.bus_out_of_service)
                        : afterNext);
                map.put("time", next == null ? getActivity().getResources()
                        .getString(R.string.bus_not_available) : next);
            } else {
                map.put("time",
                        getActivity().getResources().getString(
                                R.string.bus_not_available));
                map.put("next_time",
                        getActivity().getResources().getString(
                                R.string.bus_out_of_service));
                map.put("last_time",
                        getActivity().getResources().getString(
                                R.string.bus_out_of_service));
            }
            map.put("desc", routeNames[i]);
            list.add(map);
        }

        return list;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState
                    .getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != -1) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

//	@Override
//	public void onListItemClick(ListView listView, View view, int position,
//			long id) {
//		super.onListItemClick(listView, view, position, id);
//		// mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
//		mCallbacks.onItemSelected(String.valueOf(position));
//	}

    private String findAfterNext(String[] list) {
        Date current = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String now = timeFormat.format(current);

        String r = null;

        for (int i = 0; i < list.length - 1; i++) {
            if (list[i].compareTo(now) > 0) {
                r = list[i + 1];
                break;
            }
        }
        return r;
    }

//	public void setActivateOnItemClick(boolean activateOnItemClick) {
//		getListView().setChoiceMode(
//				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
//						: ListView.CHOICE_MODE_NONE);
//	}

    public void setActivatedPosition(int position) {
//		if (position == -1) {
//			getListView().setItemChecked(mActivatedPosition, false);
//		} else {
//			getListView().setItemChecked(position, true);
//		}
        mRecyclerView.scrollToPosition(position);
        mActivatedPosition = position;
    }

    private String findNext(String[] list) {
        Date current = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String now = timeFormat.format(current);

        String r = null;

        for (String time : list) {
            if (time.compareTo(now) > 0) {
                r = time;
                break;
            }
        }

        return r;
    }

    private String findLast(String[] list) {
        Date current = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String now = timeFormat.format(current);

        String r = null;

        for (String time : list) {
            if (time.compareTo(now) <= 0) {
                r = time;
            }
        }
        return r;
    }

    public void onResume() {
        super.onResume();
//        refresh();
    }

//    public void refresh() {
//        // this.recreate();
//        list.clear();
//        list.addAll(this.loadData());
////		simpleAdapter.notifyDataSetChanged();
//
//    }

    public interface Callbacks {

        public void onItemSelected(String id, View routeView, View descView);
    }

    interface MyItemClickListener {
        public void onItemClick(View view);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        //    private String[] mDataset;
        private ArrayList<HashMap<String, Object>> mDataset = new ArrayList<HashMap<String, Object>>();
        private MyItemClickListener mMyItemClickListener;

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<HashMap<String, Object>> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.next_bus_cell, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
//        holder.mTextView.setText(mDataset[position]);
            HashMap<String, Object> entry = mDataset.get(position);
            holder.mTextViewPerv.setText(entry.get("last_time").toString());
            holder.mTextViewRoute.setText(entry.get("route").toString());
            holder.mTextViewAfterNext.setText(entry.get("next_time").toString());
            holder.mTextViewNext.setText(entry.get("time").toString());
            holder.mTextViewDesc.setText(entry.get("desc").toString());

            holder.mViewParent.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = mRecyclerView.getChildPosition(view);
//                    Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_LONG).show();
                    mCallbacks.onItemSelected(String.valueOf(position), view.findViewById(R.id.route), view.findViewById(R.id.desc));
                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        public void setMyItemClickListener(MyItemClickListener listener) {
            mMyItemClickListener = listener;
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public View mViewParent;
            public TextView mTextViewRoute;
            public TextView mTextViewNext;
            public TextView mTextViewPerv;
            public TextView mTextViewAfterNext;
            public TextView mTextViewDesc;

            public ViewHolder(View v) {
                super(v);
                mViewParent = v;
                mTextViewAfterNext = (TextView) v.findViewById(R.id.next_time);
                mTextViewNext = (TextView) v.findViewById(R.id.time);
                mTextViewPerv = (TextView) v.findViewById(R.id.last_time);
                mTextViewDesc = (TextView) v.findViewById(R.id.desc);
                mTextViewRoute = (TextView) v.findViewById(R.id.route);

            }


        }


    }

}
