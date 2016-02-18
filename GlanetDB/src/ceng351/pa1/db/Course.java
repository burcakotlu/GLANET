/**
 * 
 */
package ceng351.pa1.db;

/**
 * @author Burçak Otlu
 * @date Nov 23, 2015
 * @project GlanetDB 
 *
 */
public class Course implements CourseInterface{
	
	int courseID;
	String courseName;
	String courseDescription;
	
	

	public Course(int courseID, String courseName, String courseDescription) {
		super();
		this.courseID = courseID;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
	}




	/**
	 * 
	 */
	public Course() {
		// TODO Auto-generated constructor stub
	}
	
	


	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}




	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}




	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}




	@Override
	public int getCourseID() {
		// TODO Auto-generated method stub
		return courseID;
	}


	@Override
	public String getCourseName() {
		// TODO Auto-generated method stub
		return courseName;
	}


	@Override
	public String getCourseDescription() {
		// TODO Auto-generated method stub
		return courseDescription;
	}



}
