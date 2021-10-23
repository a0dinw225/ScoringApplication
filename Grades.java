package application;

import java.util.*;
public class Grades {
	private String id;
	private String name;
	private String originalpic;
	private String pic;
	private String score;
	private List<String> answers;
	private String answer;
	private String number;
	private Integer sum;
	private List<String> adjusts;
	public Grades() {
		super();
		answers = new ArrayList<String>();
		adjusts = new ArrayList<String>();
	}
	public Grades(String number,String answer,String score) {
		super();
		this.number = number;
		this.answer = answer;
		this.score = score;
	}
	public Grades(String number,String id,List<String> answers) {
		super();
		this.number = number;
		this.id = id;
		this.answers = answers;
		adjusts = new ArrayList<String>();
	}
	public Grades(String answer,String score) {
		this.answer = answer;
		this.score = score;
	}
	public String getNumber() {
		return this.number;
	}
	public void SetNumber(String number) {
		this.number = number;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOriginalPic() {
		return this.originalpic;
	}
	public void setOriginalPic(String originalpic) {
		this.originalpic = originalpic;
	}
	public String getPic() {
		return this.pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getAnswer() {
		return this.answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getScore() {
		return this.score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public List<String> getAnswers(){
		return this.answers;
	}
	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}
	public List<String> getAdjusts(){
		return this.adjusts;
	}
	public void setAdjusts(List<String> adjusts) {
		this.adjusts = adjusts;
	}
	public Integer getSum() {
		return this.sum;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}
	@Override
	public boolean equals(Object o) {
		if(this.name != null) {
			return this.name.equals(((Grades)o).name);
		}
		return false;
	}
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	public String writeList() {
		String s = "";
		for(int i = 0;i < getAdjusts().size();i++) {
			s += getAdjusts().get(i) + ",";
		}
		s.substring(0,s.length()-1);
		return this.id + "," + this.sum + "," + this.pic + "," + s + "\r\n";
	}
	@Override
	public String toString() {
		return  "[number=" + number + ", Grades [id=]" + id + ", name=" + name + ", pic=" + pic + ", answer=" + answer + ",score=" + score + ", adjusts=" + adjusts + "]";
	}
}
