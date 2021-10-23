package application;

import java.util.List;

public class AnswerRate {
	private String rate;
	private List<String> rates;
	public AnswerRate() {
		
	}
	/*public AnswerRate(String rate) {
		this.rate = rate;
	}*/
	public AnswerRate(List<String> rates) {
		this.rates = rates;
	}
	/*public String getRate() {
		return this.rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}*/
	public List<String> getRates(){
		return this.rates;
	}
	public void setRates(List<String> rates) {
		this.rates = rates;
	}
	@Override
	public String toString() {
		return  "rates=" + this.rates;
	}
}
