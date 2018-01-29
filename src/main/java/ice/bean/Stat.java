package ice.bean;

import java.util.Map;

public class Stat {

	private Map<String, StatContent> monthStat;

	public Map<String, StatContent> getMonthStat() {
		return monthStat;
	}

	public void setMonthStat(Map<String, StatContent> monthStat) {
		this.monthStat = monthStat;
	}
}
