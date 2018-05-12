package da.proj.fitnessApp.models;

public class ExerciseRow {

	private Long id;
	private String exerciseNo;
	private Exercise exercise;
	private String sets;
	private String reps;
	private String weight;
	private String comment;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getExerciseNo() {
		return exerciseNo;
	}
	public void setExerciseNo(String exerciseNo) {
		this.exerciseNo = exerciseNo;
	}
	public Exercise getExercise() {
		return exercise;
	}
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
	public String getSets() {
		return sets;
	}
	public void setSets(String sets) {
		this.sets = sets;
	}
	public String getReps() {
		return reps;
	}
	public void setReps(String reps) {
		this.reps = reps;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
