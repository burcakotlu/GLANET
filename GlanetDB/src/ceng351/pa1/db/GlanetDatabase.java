/**
 * 
 */
package ceng351.pa1.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Burçak Otlu
 * @date Nov 23, 2015
 * @project GlanetDB 
 *
 */
public class GlanetDatabase implements GlanetDatabaseInterface{
	
	private String user = "e1941699";
	private String password = "829349";
	private String database = "db1941699";
	private String host = "144.122.71.101";
	private int port = 3306;
	
//	private String user = "burcak";
//	private String password = "ceng351";
//	private String database = "test";
//	private String host = "144.122.71.101";
//	private int port = 3306;
	
	//private String host = "localhost";
	
	private Connection con;
	

	public GlanetDatabase() {
		
		String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.con =  DriverManager.getConnection(url, this.user, this.password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static GlanetDatabaseInterface newInstance() {
		
		GlanetDatabase dt = new GlanetDatabase();
		
		return (GlanetDatabaseInterface)dt;
	}
	
	@Override
	public void createTables() {
		int result;
		String query = "create table movie (" + 
					   "name varchar(50)," + 
					   "director varchar(50) not null," + 
					   "year varchar(4) not null," + 
					   "primary key (name))";
		
		try {
			Statement st = this.con.createStatement();
			result = st.executeUpdate(query);
			System.out.println(result);
			
		} catch (SQLException e) {
			// TODO 
		}
	}

	@Override
	public void dropTables() {
		int result;
		String query = "drop table if exists movie";
		
		try {
			Statement st = this.con.createStatement();
			result = st.executeUpdate(query);
			System.out.println(result);
			
		} catch (SQLException e) {
			// TODO 
		}
	}

	@Override
	public Movie getMovie(String name) {
		ResultSet rs;
		String query = "select * from movie where name = '" + name + "'";
		
		try {
			Statement st = this.con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				String m_name = rs.getString("name");
				String m_director = rs.getString("director");
				String m_year = rs.getString("year");
				
				return (Movie)new MovieObject(m_name, m_director, m_year);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public void addMovie(Movie movie) {
		int result;
		String query = "insert into movie values ('" + movie.getName() + "','" + movie.getDirectorName() + "','" + movie.getReleaseYear() + "')";
		
		try {
			Statement st = this.con.createStatement();
			result = st.executeUpdate(query);
			System.out.println(result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void removeMovie(String name) {
		int result;
		String query = "delete from movie where name = '" + name +"'";
		
		try {
			Statement st = this.con.createStatement();
			result = st.executeUpdate(query);
			System.out.println(result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addCourse(CourseInterface courseInterface) {
		int result;
		String query = "insert into course values ('" + courseInterface.getCourseID() + "','" + courseInterface.getCourseName() + "','" + courseInterface.getCourseDescription() + "')";
		
		try {
			Statement st = this.con.createStatement();
			result = st.executeUpdate(query);
			System.out.println(result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Movie getMovies() {
		ResultSet rs;
		String query = "select * from movie";
		
		try {
			Statement st = this.con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				
				String m_name = rs.getString("name");
				String m_director = rs.getString("director");
				String m_year = rs.getString("year");
				
				return (Movie)new MovieObject(m_name, m_director, m_year);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

}
