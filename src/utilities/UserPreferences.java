package utilities;
import java.util.List;
import java.util.ArrayList;
public class UserPreferences {
	
	int id;
	List<String> genders = new ArrayList<String>();
	List<String> faculties = new ArrayList<String>();
	int ageMin;
	int ageMax;
	
	public UserPreferences(int id, List<String> genders, List<String> faculties, int ageMin, int ageMax) {
		super();
		this.id = id;
		this.genders = genders;
		this.faculties = faculties;
		this.ageMin = ageMin;
		this.ageMax = ageMax;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<String> getGenders() {
		return genders;
	}

	public void setGenders(List<String> genders) {
		this.genders = genders;
	}

	public List<String> getFaculties() {
		return faculties;
	}

	public void setFaculties(List<String> faculties) {
		this.faculties = faculties;
	}

	public int getAgeMin() {
		return ageMin;
	}

	public void setAgeMin(int ageMin) {
		this.ageMin = ageMin;
	}

	public int getAgeMax() {
		return ageMax;
	}

	public void setAgeMax(int ageMax) {
		this.ageMax = ageMax;
	}

	public UserPreferences() {
		// TODO Auto-generated constructor stub
	}

}
