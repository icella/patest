package ice.bean;

import java.util.Map;

public class StatContent {
	private Map<String, Integer> statHourIn;
	private Map<String, Integer> statHourOut;
	private Map<String, Integer> friends;
	private Double activeStandard;
	private Float activeShortest;
	private Float activeLongest;
	private Float activeAverage;
	private Float call;
	private Float answer;
	private Float firstTime;
	private Float lastTime;

	public Map<String, Integer> getStatHourIn() {
		return statHourIn;
	}

	public void setStatHourIn(Map<String, Integer> statHourIn) {
		this.statHourIn = statHourIn;
	}

	public Map<String, Integer> getStatHourOut() {
		return statHourOut;
	}

	public void setStatHourOut(Map<String, Integer> statHourOut) {
		this.statHourOut = statHourOut;
	}

	public Double getActiveStandard() {
		return activeStandard;
	}

	public void setActiveStandard(Double activeStandard) {
		this.activeStandard = activeStandard;
	}

	public Float getActiveShortest() {
		return activeShortest;
	}

	public void setActiveShortest(Float activeShortest) {
		this.activeShortest = activeShortest;
	}

	public Float getActiveLongest() {
		return activeLongest;
	}

	public void setActiveLongest(Float activeLongest) {
		this.activeLongest = activeLongest;
	}

	public Float getActiveAverage() {
		return activeAverage;
	}

	public void setActiveAverage(Float activeAverage) {
		this.activeAverage = activeAverage;
	}

	public Float getCall() {
		return call;
	}

	public void setCall(Float call) {
		this.call = call;
	}

	public Float getAnswer() {
		return answer;
	}

	public void setAnswer(Float answer) {
		this.answer = answer;
	}

	public Map<String, Integer> getFriends() {
		return friends;
	}

	public void setFriends(Map<String, Integer> friends) {
		this.friends = friends;
	}

	public Float getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(Float firstTime) {
		this.firstTime = firstTime;
	}

	public Float getLastTime() {
		return lastTime;
	}

	public void setLastTime(Float lastTime) {
		this.lastTime = lastTime;
	}
}
