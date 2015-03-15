package edu.cuhk.bus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

//to be finish
public class RouteManager {
	private int[] availableRoutes = { R.array.bus_route_1, R.array.bus_route_2,
			R.array.bus_route_3, R.array.bus_route_4, R.array.bus_route_5,
			R.array.bus_route_6, R.array.bus_route_7, R.array.bus_route_8,
			R.array.bus_route_n, R.array.bus_route_h,
			R.array.bus_route_la39, R.array.bus_route_lih,
			R.array.bus_route_lu, R.array.bus_route_ld };
	boolean[] service = new boolean[availableRoutes.length];
//	private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

	
	private ArrayList<String> holidays;
	private ArrayList<String> nonTeachingDays;
	
	public RouteManager(ArrayList<String> nonTeachingDays, ArrayList<String> holidays){
		
//		this.list = loadData();
		this.holidays = holidays;
		this.nonTeachingDays = nonTeachingDays;
		
		Date current = new Date();
		String today = dateFormat.format(current);
		Calendar calendar = Calendar.getInstance();
		
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
	}
	
	public boolean isAvailable(int routeId){
		return service[routeId];
	}
	

}
