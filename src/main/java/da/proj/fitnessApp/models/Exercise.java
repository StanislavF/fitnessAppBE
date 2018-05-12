package da.proj.fitnessApp.models;

public class Exercise {
	
	private Long id;
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(Object o) {

		if(o instanceof Exercise) {
			return ((Exercise)o).getName().equals(this.getName());
		}
		
		return o.equals(this);
	}
	

}
