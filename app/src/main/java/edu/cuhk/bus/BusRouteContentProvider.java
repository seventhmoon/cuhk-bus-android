package edu.cuhk.bus;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

//Draft
public class BusRouteContentProvider extends ContentProvider {
    public static final String PROVIDER_NAME = "edu.cuhk.bus.provider";

    private ArrayList<HashMap<String, Object>> mRoutes = new ArrayList<HashMap<String, Object>>();
    private UriMatcher mUriMatcher;

    public BusRouteContentProvider() {
        mRoutes = loadData();
    }

    private boolean[] getRouteAvailabiltiy(){
        Calendar calendar = Calendar.getInstance();
        Date current = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
//		String currentTime = timeFormat.format(current);
        String today = dateFormat.format(current);

        ArrayList<String> holidays = new ArrayList<String>(
                Arrays.asList(this.getContext().getResources().getStringArray(
                        R.array.holidays)));

        ArrayList<String> nonTeachingDays = new ArrayList<String>(
                Arrays.asList(this.getContext().getResources().getStringArray(
                        R.array.non_teaching_days)));

        boolean[] service = new boolean[12];
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

        return service;
    }

    private ArrayList<HashMap<String, Object>> loadData() {
        int[] availableRoutes = {R.array.bus_route_1, R.array.bus_route_2,
                R.array.bus_route_3, R.array.bus_route_4, R.array.bus_route_5,
                R.array.bus_route_6, R.array.bus_route_7, R.array.bus_route_8,
                R.array.bus_route_n, R.array.bus_route_h,
                R.array.bus_route_la39, R.array.bus_route_lih,
                R.array.bus_route_lu, R.array.bus_route_ld};

        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        String[] routes = this.getContext().getResources().getStringArray(
                R.array.bus_routes);
        String[] routeNames = this.getContext().getResources().getStringArray(
                R.array.bus_route_names);

        Date current = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
//		String currentTime = timeFormat.format(current);
        String today = dateFormat.format(current);

        boolean[] service = getRouteAvailabiltiy();

        for (int i = 0; i < availableRoutes.length; i++) {
            String[] times = this.getContext().getResources().getStringArray(
                    availableRoutes[i]);
            HashMap<String, Object> map = new HashMap<String, Object>();
            String next = this.findNext(times);
            String last = this.findLast(times);
            String afterNext = this.findAfterNext(times);
            map.put("route", routes[i]);
            if (service[i]) {
                map.put("last_time", last == null ? this.getContext()
                        .getResources().getString(R.string.bus_out_of_service)
                        : last);
                map.put("next_time", afterNext == null ? this.getContext()
                        .getResources().getString(R.string.bus_out_of_service)
                        : afterNext);
                map.put("time", next == null ? this.getContext().getResources()
                        .getString(R.string.bus_not_available) : next);
            } else {
                map.put("time",
                        this.getContext().getResources().getString(
                                R.string.bus_not_available));
                map.put("next_time",
                        this.getContext().getResources().getString(
                                R.string.bus_out_of_service));
                map.put("last_time",
                        this.getContext().getResources().getString(
                                R.string.bus_out_of_service));
            }
            map.put("desc", routeNames[i]);
            list.add(map);
        }

        return list;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(PROVIDER_NAME, "route", 1);
        mUriMatcher.addURI(PROVIDER_NAME, "schedule/#", 2);

        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {



        /*
         * Choose the table to query and a sort order based on the code returned for the incoming
         * URI. Here, too, only the statements for table 3 are shown.
         */
        switch (mUriMatcher.match(uri)) {
            case 1: break;
            case 2: break;
            default: break;
        }
        throw new UnsupportedOperationException("Not yet implemented");
        }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
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

}
