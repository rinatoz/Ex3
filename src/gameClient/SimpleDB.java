	package gameClient;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
/**
 * This class represents a simple example of using MySQL Data-Base.
 * Use this example for writing solution. 
 * @author boaz.benmoshe
 *
 */
public class SimpleDB {
	public static final String jdbcUrl="jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
	public static final String jdbcUser="student";
	public static final String jdbcUserPassword="OOP2020student";
	
	/**
	 * Simple main for demonstrating the use of the Data-base
	 * @param args
	 */
	public static void main(String[] args) {
			int id1 = 999;  // "dummy existing ID  
			int level = 0;
			//allUsers();
			//GamesPlayed("208935791");
			//BestResult(0,"208935791");
			//printLog("208935791");
			//StageRank(0);
			//CurrentStage("0");
			String kml = getKML(id1,level);
			System.out.println("***** KML file example: ******");
			System.out.println(kml);
		}
	/** simply prints all the games as played by the users (in the database).
	 * 
	 */
		public static void printLog(String ID) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = 
				DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
				Statement statement = connection.createStatement();
				String allCustomersQuery = "SELECT * FROM Logs WHERE userID ="+ID+";";
				ResultSet resultSet = statement.executeQuery(allCustomersQuery);
				
				while(resultSet.next())
				{
					System.out.println("Id: " + resultSet.getInt("UserID")+","+resultSet.getInt("levelID")+","+resultSet.getInt("moves")+","+resultSet.getDate("time")+","+resultSet.getInt("score"));
				}
				resultSet.close();
				statement.close();		
				connection.close();		
			}
			
			catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
				System.out.println("Vendor Error: " + sqle.getErrorCode());
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		/** prints all games played on the DB by given ID
		 * 
		 */
		public static int GamesPlayed(String ID)
		{
			int total_games=1;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
				Statement statement = connection.createStatement();
				String allCustomersQuery = "SELECT * FROM Logs WHERE userID = "+ID+";"; //filter only to given id
				ResultSet resultSet = statement.executeQuery(allCustomersQuery);
				
				while(resultSet.next()) //iterate thought all games with total games and return it
				{
					total_games++;
				}
				resultSet.close();
				statement.close();		
				connection.close();		
			}
			
			catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
				System.out.println("Vendor Error: " + sqle.getErrorCode());
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return total_games;
		}
		/** returns the best result to a given level and ID
		 * 
		 */
		public static int BestResult(int Level,String ID)
		{
			int bestresult = 0;	
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
				Statement statement = connection.createStatement();
				String allCustomersQuery = "SELECT * FROM Logs WHERE levelID ="+ Level+" AND userID = "+ID+"ORDER BY score desc;"; //filter levelID by given userID and sort it by descending order
				ResultSet resultSet = statement.executeQuery(allCustomersQuery);
				
				if(resultSet.next()) //just get the first result since it is already sorted and filtered
				{
					System.out.println("User ID:"+resultSet.getInt("UserID")+","+"Level ID:"+resultSet.getInt("levelID")+","+"Score: "+resultSet.getInt("score"));
				}
				resultSet.close();
				statement.close();		
				connection.close();		
				}
			
			catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
				System.out.println("Vendor Error: " + sqle.getErrorCode());
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			return bestresult;
			
			
		}
		/** returns the current stage for given ID
		 * 
		 */
		public static int CurrentStage(String ID)
		{
			int CurrentStage = 0;	
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
				Statement statement = connection.createStatement();
				String allCustomersQuery = "select * from Logs where userID = "+ID+" ORDER BY LevelID desc;"; //select all elements of an ID and sort them by descending order
				ResultSet resultSet = statement.executeQuery(allCustomersQuery);
				
				if(resultSet.next()) //than just take the first result and this is the level the ID is in and this is already sorted 
				{
					System.out.println("Your'e level is: "+resultSet.getInt("levelID"));
					CurrentStage=resultSet.getInt("levelID");
				}
				resultSet.close();
				statement.close();		
				connection.close();		
				}
			
			catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
				System.out.println("Vendor Error: " + sqle.getErrorCode());
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			return CurrentStage;
			
			
		}
		/** returns the current stage rank for given ID
		 * 
		 */
		public static int StageRank(int Level)
		{
			
			int counter=0;
			boolean flag = true;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
				Statement statement = connection.createStatement();
				String allCustomersQuery = "SELECT * FROM Logs WHERE levelID="+Level+" ORDER BY score desc;"; //sort by level id
				ResultSet resultSet = statement.executeQuery(allCustomersQuery);
				
				while(resultSet.next() && flag==true)
				{
					
					
					if(resultSet.getInt("UserID")==208935791) //if the userID is equal to mine than set flag to false to stop and set counter to current place
					{
						flag=false;
						counter=resultSet.getRow();
						
					}
						
						
				}
				resultSet.close();
				statement.close();		
				connection.close();
				System.out.println("rank: "+counter); //prints the counter
				}
			
			catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
				System.out.println("Vendor Error: " + sqle.getErrorCode());
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			
			return counter;
			
			
		}
		
		
			
		
	/**
	 * this function returns the KML string as stored in the database (userID, level);
	 * @param id
	 * @param level
	 * @return
	 */
			public static String getKML(int id, int level) {
				String ans = null;
				String allCustomersQuery = "SELECT * FROM Users where userID="+id+";";
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);		
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(allCustomersQuery);
					if(resultSet!=null && resultSet.next()) {
						ans = resultSet.getString("kml_"+level);
					}
				}
				catch (SQLException sqle) {
					System.out.println("SQLException: " + sqle.getMessage());
					System.out.println("Vendor Error: " + sqle.getErrorCode());
				}
				
				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return ans;
			}
		public static int allUsers() {
			int ans = 0;
			String allCustomersQuery = "SELECT * FROM Users;";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = 
						DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);		
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(allCustomersQuery);
				while(resultSet.next()) {
					System.out.println("Id: " + resultSet.getInt("UserID"));
					ans++;
				}
				resultSet.close();
				statement.close();		
				connection.close();
			}
			catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
				System.out.println("Vendor Error: " + sqle.getErrorCode());
			}
			
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return ans;
		}
	}
		
