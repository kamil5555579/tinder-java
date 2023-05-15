package utilities;

import java.awt.Image;

import javax.swing.ImageIcon;

public class User {

	String firstname;
	String lastname;
	String university;
	String gender;
	int age;
	Image image;
	int id;
	String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User(int id, String firstname, String lastname, String university, String gender, int age, Image image, String description) {
		this.id=id;
		this.firstname=firstname;
		this.lastname=lastname;
		this.university=university;
		this.gender=gender;
		this.age=age;
		this.image=image;
		this.description=description;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getFirstname()
	{
		return firstname;
	}
	
	public String getLastname()
	{
		return lastname;
	}
	
	public String getUniversity()
	{
		return university;
	}
	
	public String getGender()
	{
		return gender;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public Image getImage()
	{
		return image;
	}

}
