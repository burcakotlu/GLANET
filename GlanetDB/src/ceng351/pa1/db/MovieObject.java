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
public class MovieObject implements Movie {

	private String name;
	private String director;
	private String release_year;
	
	public MovieObject(String name, String director, String release_year) {
		this.name = name;
		this.director = director;
		this.release_year = release_year;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDirectorName() {
		return this.director;
	}

	@Override	
	public String getReleaseYear() {
		return this.release_year;
	}
	
	public boolean isFirstMovieOfDirector(){
		boolean retval = false;
		
		// TODO just an example
		
		return retval;
	}
	
	public int howOldIsMovie() {
		int age = 0;
		
		// TODO just an example
		
		return age;
	}

}
