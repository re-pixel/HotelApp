package models;

import java.util.HashMap;

public class Pricing {
	private DateInterval period;
	private HashMap<String, Double> prices;
	
	public Pricing(DateInterval period, HashMap<String, Double> prices) {
		this.period = period;
		this.prices = prices;
	}
	
	public HashMap<String, Double> getPrices(){
		return prices;
	}
	
	public DateInterval getPeriod() {
		return period;
	}
	
	public double getPrice(String s) {
		return prices.get(s);
	}
	
	public String toString() {
		String s = period.toString() + ",";
		for(String key: prices.keySet()) {
			s += key + ":" + prices.get(key) + " ";
		}
		return s;
	}
}
