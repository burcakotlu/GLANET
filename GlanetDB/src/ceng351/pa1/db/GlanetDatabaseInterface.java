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
public interface GlanetDatabaseInterface {
	
	public void createTables();
	public void dropTables();
	public Movie getMovie(String name);
	public Movie getMovies();
	public void addMovie(Movie movie);
	public void removeMovie(String name);
	
	public void addCourse(CourseInterface courseInterface);

}
