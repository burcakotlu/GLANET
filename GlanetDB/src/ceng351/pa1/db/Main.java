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
public class Main {

	/**
	 * 
	 */
	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String args[]) {
		
		GlanetDatabaseInterface ops = GlanetDatabase.newInstance();
		
		ops.createTables();
		Movie m = new MovieObject("Flight", "Robert Zemeckis", "2012");
		ops.addMovie(m);
		ops.getMovies();

		
		

		ops.dropTables();
		
//		ops.createTables();
//		CourseInterface courseInterface = new Course(571491, "Graduate Project", "Must Course");
//		ops.addCourse(courseInterface);
		
		// ops.removeMovie("Flight");
	
		//Movie mv = ops.getMovie("Flight");
		//System.out.println(mv.getName() + " - " + mv.getDirectorName() + " - " + mv.getReleaseYear());
	}

}
