package ice.bean;

import java.util.*;
import java.util.Map.Entry;

public class ContactTagRs {
	private String jsonData;
	public static final Integer SUCCEED = 0; 	// 有相关数据
	public static final Integer NO_RESULT = 1; 	// 没有相关数据

	private Integer result;
	private String msg;
	private Map<String, Object> data;

	private static final String hourstat_out = "hourstat_out";
	private static final String hourstat_in = "hourstat_in";

	private static final String ACT = "act";
	private static final String STATA = "statA";
	private static final String STATB = "statB";
	private static final String TIME = "time";
	private static final String RECORDS = "records";
	public static final String B2A = "B2A";
	public static final String A2B = "A2B";
	
	private static final String ACTIVESTANDARD = "activeStandard";
	private static final String ACTIVESHORTEST = "activeShortest";
	private static final String ACTIVELONGEST = "activeLongest";
	private static final String ACTIVEAVERAGE = "activeAverage";
	private static final String CALL = "call";
	private static final String ANSWER = "answer";
	private static final String FRIENDS = "friends";
	private static final String FALSE = "false";
	private static final String FIRTSTTIME = "firstTime";
	private static final String LASTTIME = "lastTime";

	private List<QihuTagModel> allRecord;
	private List<QihuTagModel> a2bRecords;
	private List<QihuTagModel> b2aRecords;
	
	private Stat statA;
	private Stat statB;
	private boolean hasStatA;
	private boolean hasStatB;
	
	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public static QihuTagModel newRecord() {
		return new QihuTagModel();
	}

	public List<QihuTagModel> getA2bRecords() {
		return a2bRecords;
	}

	public void setA2bRecords(List<QihuTagModel> a2bRecords) {
		this.a2bRecords = a2bRecords;
	}

	public List<QihuTagModel> getB2aRecords() {
		return b2aRecords;
	}

	public void setB2aRecords(List<QihuTagModel> b2aRecords) {
		this.b2aRecords = b2aRecords;
	}

	public List<QihuTagModel> getAllRecord() {
		return allRecord;
	}

	public void setAllRecord(List<QihuTagModel> allRecord) {
		this.allRecord = allRecord;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public Stat getStatA() {
		return statA;
	}

	public void setStatA(Stat statA) {
		this.statA = statA;
	}

	public Stat getStatB() {
		return statB;
	}

	public void setStatB(Stat statB) {
		this.statB = statB;
	}
	
	public boolean isHasStatA() {
		return hasStatA;
	}

	public void setHasStatA(boolean hasStatA) {
		this.hasStatA = hasStatA;
	}

	public boolean isHasStatB() {
		return hasStatB;
	}

	public void setHasStatB(boolean hasStatB) {
		this.hasStatB = hasStatB;
	}

	public boolean initData() {
		allRecord = new ArrayList();

		a2bRecords = initABRecords(A2B);
		b2aRecords = initABRecords(B2A);
		
		if(a2bRecords != null){
			allRecord.addAll(a2bRecords);
		}
		if(b2aRecords != null){
			allRecord.addAll(b2aRecords);
		}

		if (!this.data.get(STATA).toString().equals(FALSE)) {
			statA = initStatContent(STATA);
			hasStatA = true;
		}
		if (!this.data.get(STATB).toString().equals(FALSE)) {
			statB = initStatContent(STATB);
			hasStatB = true;
		}
		Collections.sort(allRecord);
		return true;
	}

	private List<QihuTagModel> initABRecords(String type) {
		List<QihuTagModel> list = null;

		List<Map<String, Object>> data = (List<Map<String, Object>>) this.data.get(RECORDS);
		if(!data.isEmpty()){
			list =  new ArrayList<>();
			for (Map<String, Object> map : data) {
				String act = map.get(ACT).toString();
				if (act.equalsIgnoreCase(type))
					list.add(new QihuTagModel(Long.parseLong(map.get(TIME).toString()), map.get(ACT).toString()));
			}
		}

		return list;
	}

	private Stat initStatContent(String type) {
		Stat stat = new Stat();
		Map<String, StatContent> statMap = new HashMap<>();
		
		Map<String, Object> mapContent = (Map<String, Object>) this.data.get(type); 
		for (Entry<String, Object> contactRecord : mapContent.entrySet()) {
			String month = contactRecord.getKey();
			
			Map<String, Object> valueMap = (Map<String, Object>)contactRecord.getValue();
			StatContent content = parseStatContent(valueMap);
			statMap.put(month, content);
		}
		
		stat.setMonthStat(statMap);
		
		return stat;
	}

	private StatContent parseStatContent(Map<String, Object> contentMap) {
		if(contentMap != null && contentMap.size() > 0){
			StatContent stat = new StatContent();
			
			if(contentMap.get(hourstat_in) instanceof Map)
				stat.setStatHourIn((Map<String, Integer>) contentMap.get(hourstat_in));
			
			if(contentMap.get(hourstat_out) instanceof Map)
				stat.setStatHourOut((Map<String, Integer>) contentMap.get(hourstat_out));
			
			String activeStandard = contentMap.get(ACTIVESTANDARD) == null ? "0" : contentMap.get(ACTIVESTANDARD).toString();
			String activeShortest = contentMap.get(ACTIVESHORTEST) == null ? "0" : contentMap.get(ACTIVESHORTEST).toString();
			String activeLongest = contentMap.get(ACTIVELONGEST) == null ? "0" : contentMap.get(ACTIVELONGEST).toString();
			String activeAverage = contentMap.get(ACTIVEAVERAGE) == null? "0" : contentMap.get(ACTIVEAVERAGE).toString();
			String call = contentMap.get(CALL) == null ? "0" : contentMap.get(CALL).toString();
			String answer = contentMap.get(ANSWER) == null ? "0" : contentMap.get(ANSWER).toString();
			String firstTime = contentMap.get(FIRTSTTIME) == null ? "0" : contentMap.get(FIRTSTTIME).toString();
			String lastTime = contentMap.get(LASTTIME) == null ? "0" : contentMap.get(LASTTIME).toString();
			
			stat.setActiveStandard(Double.parseDouble(activeStandard));
			stat.setActiveShortest(Float.parseFloat(activeShortest));
			stat.setActiveLongest(Float.parseFloat(activeLongest));
			stat.setActiveAverage(Float.parseFloat(activeAverage));
			stat.setCall(Float.parseFloat(call));
			stat.setAnswer(Float.parseFloat(answer));
			stat.setFirstTime(Float.valueOf(firstTime));
			stat.setLastTime(Float.valueOf(lastTime));
			
			if(contentMap.get(FRIENDS) instanceof Map)
				stat.setFriends((Map<String, Integer>)contentMap.get(FRIENDS));
			
			return stat;
		}
		
		return null;
	}
}
