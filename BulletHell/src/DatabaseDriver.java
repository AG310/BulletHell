import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDriver {
	
	private Connection connection;
	
	public DatabaseDriver() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite::resource:database/scoredb.sqlite");
		} catch (SQLException e) {
			System.out.println("Connection to database failed");
			connection = null;
		}
	}
	
	public boolean isDatabaseConnected() {
		try {
			if(connection==null || connection.isClosed()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public int[] getTopScores() {
		int[] scoreList = {0,0,0};
        Statement statement;
		try {
			statement = connection.createStatement();
	        ResultSet rs = statement.executeQuery("SELECT * FROM scores ORDER BY score DESC LIMIT 3");
	        int i = 0;
	        while(rs.next())
	         {
	           scoreList[i]=rs.getInt("score");
	           i++;
	         }
		} catch (SQLException e) {
			System.out.println("Scores could not be printed.");
		}
		return scoreList;
	}
	
	public void addScore(int newScore) {
        PreparedStatement statement;
		try {
			statement = connection.prepareStatement("INSERT INTO scores(score) values(?)");
	        statement.setInt(1, newScore);
	        statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Scores could not be added.");
		}
	}
	
	public void deleteAllScores() {
        Statement statement;
		try {
			statement = connection.createStatement();
	        statement.executeUpdate("DELETE FROM scores");
		} catch (SQLException e) {
			System.out.println("Scores could not be printed.");
		}
	}
}
