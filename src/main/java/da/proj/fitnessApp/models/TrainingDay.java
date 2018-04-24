package da.proj.fitnessApp.models;

import java.util.List;

public class TrainingDay {

	private Long id;
	private Integer no;
	private String title;
	private String date;
	private List<ExerciseRow> exercseRows;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<ExerciseRow> getExercseRows() {
		return exercseRows;
	}
	public void setExercseRows(List<ExerciseRow> exercseRows) {
		this.exercseRows = exercseRows;
	}
	
	
	
}
