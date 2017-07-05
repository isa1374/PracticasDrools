package Green;

public class Green {
	String name;
	
	public Green(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Green [name=" + name + "]";
	}
}
