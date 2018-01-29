package ice.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ContactCommonRs {
	private static final String RECORDS = "records";

	private String jsonData;
	private String phoneA;
	private String phoneB;

	private Integer result;
	private String msg;
	private Map<String, Object> data;

	private List<String> commonImeis; 									// 共同的设备号
	private Map<String, Map<String, List<CommonCallRecord>>> records; 	// 共同设备号的通话详单
	private Map<String, List<CommonCallRecord>> phoneARecords;			//phoneA与设备的通话详单
	private Map<String, List<CommonCallRecord>> phoneBRecords;			//phoneB与设备的通话详单

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

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public List<String> getCommonImeis() {
		return commonImeis;
	}

	public void setCommonImeis(List<String> commonImeis) {
		this.commonImeis = commonImeis;
	}

	public String getPhoneA() {
		return phoneA;
	}

	public void setPhoneA(String phoneA) {
		this.phoneA = phoneA;
	}

	public String getPhoneB() {
		return phoneB;
	}

	public void setPhoneB(String phoneB) {
		this.phoneB = phoneB;
	}
	
	public Map<String, Map<String, List<CommonCallRecord>>> getRecords() {
		return records;
	}

	public void setRecords(Map<String, Map<String, List<CommonCallRecord>>> records) {
		this.records = records;
	}

	public Map<String, List<CommonCallRecord>> getPhoneARecords() {
		return phoneARecords;
	}

	public void setPhoneARecords(Map<String, List<CommonCallRecord>> phoneARecords) {
		this.phoneARecords = phoneARecords;
	}

	public Map<String, List<CommonCallRecord>> getPhoneBRecords() {
		return phoneBRecords;
	}

	public void setPhoneBRecords(Map<String, List<CommonCallRecord>> phoneBRecords) {
		this.phoneBRecords = phoneBRecords;
	}

	@SuppressWarnings("unchecked")
	public boolean initData(String phoneA, String phoneB) {
		this.phoneA = phoneA;
		this.phoneB = phoneB;
		commonImeis = new ArrayList<>();
		records = new HashMap<>();
		phoneARecords = new HashMap<>();
		phoneBRecords = new HashMap<>();

		if (this.getData().get(RECORDS) instanceof JSONObject) {
			Map<String, Object> datas = (Map<String, Object>) this.getData().get(RECORDS);

			for (Entry<String, Object> dataMap : datas.entrySet()) {
				String imei = dataMap.getKey();
				Map<String, List<CommonCallRecord>> callRecordList = new HashMap<>();

				Map<String, Object> detailList = (Map<String, Object>) dataMap.getValue();
				for (Entry<String, Object> detailRecord : detailList.entrySet()) {
					String phoneMd5 = detailRecord.getKey();
					List<CommonCallRecord> lists = new ArrayList<>();

					List<String> callList = (List<String>) detailRecord.getValue();
					for (String callStr : callList) {
						String[] splits = callStr.split(":");
						CommonCallRecord callDetialRecord = new CommonCallRecord(Long.parseLong(splits[0]),
								Integer.parseInt(splits[1]));
						lists.add(callDetialRecord);
					}
					callRecordList.put(phoneMd5, lists);
					if(phoneMd5.equals(this.getPhoneA())){
						phoneARecords.put(imei, lists);
					} else if(phoneMd5.equals(this.getPhoneB())){
						phoneBRecords.put(imei, lists);
					}
				}

				commonImeis.add(imei);
				records.put(imei, callRecordList);
			}
		}

		return true;
	}

}
