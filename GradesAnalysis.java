package application;

import java.util.List;

public class GradesAnalysis {
	private String id;
	private String score;
	private String pic;
	private List<String> adjusts;
	private List<String> rate;
	public GradesAnalysis() {
		
	}
	public GradesAnalysis(String id,String score,String pic,List<String> adjusts) {
		this.id = id;
		this.score = score;
		this.pic = pic;
		this.adjusts = adjusts;
	}
	public GradesAnalysis(List<String> adjusts) {
		this.adjusts = adjusts;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScore() {
		return this.score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getPic() {
		return this.pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public List<String> getAdjusts() {
		return this.adjusts;
	}
	public void setAdjusts(List<String> adjusts) {
		this.adjusts = adjusts;
	}
	public List<String> getRate() {
		return this.rate;
	}
	public void setRate(List<String> rate) {
		this.rate = rate;
	}
	@Override
	public boolean equals(Object o) {
		if(this.id != null) {
			return this.id.equals(((GradesAnalysis)o).id);
		}
		return false;
	}
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	@Override
	public String toString() {
		return "[id:" + this.id + ", score:" + this.score + ", pic:" + this.pic + ", adjusts:" + this.adjusts + "]";
	}
}
