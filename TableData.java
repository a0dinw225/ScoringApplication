package application;

import javafx.beans.property.SimpleStringProperty;

public class TableData {
	
	SimpleStringProperty number,grade,name;
	public TableData(String number,String grade,String name) {
		this.number = new SimpleStringProperty(number);
		this.grade = new SimpleStringProperty(grade);
		this.name = new SimpleStringProperty(name);
	}
	public String getNumber() {
		return number.get();
	}
	public void setNumber(String number) {
		this.number.set(number);
	}
	public String getGrade() {
		return grade.get();
	}
	public void setGrade(String grade) {
		this.grade.set(grade);
	}
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
}
