package main;

import javax.swing.ImageIcon;

public class User {

	String firstname;
	String lastname;
	String university;
	String gender;
	int age;
	ImageIcon image;
	int id;
	
	public User(int id, String firstname, String lastname, String university, String gender, int age, ImageIcon image) {
		this.id=id;
		this.firstname=firstname;
		this.lastname=lastname;
		this.university=university;
		this.gender=gender;
		this.age=age;
		this.image=image;
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
	
	public ImageIcon getImage()
	{
		return image;
	}

}
