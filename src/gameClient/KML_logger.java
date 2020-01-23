package gameClient;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;  

public class KML_logger  {
	FileWriter fw=new FileWriter(System.getProperty("user.dir")+"/testkml.kml");
	static String Coordinates = "";
	static String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	static int Node_count=1;
	static int steps=1;
	static int fruit_count=1;
	
	public KML_logger() throws IOException
	{
		try{    
	               
	           fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
	           fw.write("\n");
	           fw.write("<kml xmlns=\"http://earth.google.com/kml/2.2\">"); 
	           fw.write("\n");
	           fw.write("<Document>"); 
	           fw.write("\n");
	           fw.write("<name>File Representing Robot Game!</name>");
	           fw.write("\n");
	           fw.write("<open>1</open>");
	           fw.write("\n");
	           fw.write("<description>KML format for robot game scenarios!</description>");
	           fw.write("\n");
	           
	         }	
		 catch(IOException e)
		{
			 System.out.println(e);
		}    
        
	}
	
	public static void Styleid(FileWriter fw,String styleid)
	{
		try {
			
			fw.write("    <Style id="+"\""+styleid+"\""+">\n");
			fw.write("      <IconStyle>\n");
			fw.write("        <Icon>\n");
			fw.write("          <href>"+styleid+".png"+"</href>\n");
			fw.write("        </Icon>\n");
			fw.write("      </IconStyle>\n");
			fw.write("      <LineStyle>\n");
			fw.write("        <width>2</width>\n");
			fw.write("      </LineStyle>\n");
			fw.write("    </Style>\n");
			
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
		
	public static void fillFruit(FileWriter fw,String position,String fruit) 
	{
		try {
			fw.write("<Placemark>\n");
			fw.write("<name>"+ "Fruit"+fruit_count+"</name> \n");
						
			fw.write("      <styleUrl>#"+fruit+"</styleUrl>\n" +"      <Point>\n");		
			fw.write("        <coordinates>");
			fw.write(position);
			fw.write("</coordinates>\n");
			fw.write("      </Point>\n");
			fw.write("    </Placemark>\n");
			fruit_count++;
				
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void fillRobot(FileWriter fw,String position)
	{
		try {
			fw.write("<Placemark>\n");
			fw.write("<name>"+ "Node"+Node_count+"</name> \n");
			Node_count++;
			fw.write("      <TimeStamp>\n"+"		<when>"+LocalDateTime.now()+"Z"+"</when> \n");
			fw.write(	"      </TimeStamp>\n" +"      <styleUrl>#paddle-a</styleUrl>\n" +"      <Point>\n");		
			fw.write("        <coordinates>");
			fw.write(position);
			fw.write(",0</coordinates>\n");
			fw.write("      </Point>\n");
			fw.write("    </Placemark>\n");
			
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void fillPlacemark(FileWriter fw,String position)
	{
		try {
			fw.write("<Placemark>\n");
			fw.write("<name>"+ "Node"+steps+"</name> \n");
			steps++;
			fw.write("      <TimeStamp>\n"+"		<when>"+LocalDateTime.now()+"Z"+"</when> \n");
			fw.write(	"      </TimeStamp>\n" +"      <styleUrl>#robot</styleUrl>\n" +"      <Point>\n");		
			fw.write("        <coordinates>");
			fw.write(position);
			fw.write(",0</coordinates>\n");
			fw.write("      </Point>\n");
			fw.write("      <TimeSpan>\n");
			fw.write("         <begin>"+LocalDateTime.now()+"Z"+"</begin>\n");
			fw.write("         <end>"+LocalDateTime.now()+"Z"+"</end>\n");
			fw.write("         </TimeSpan>\n");
			fw.write("    </Placemark>");
			Coordinates+=position.substring(0,28)+ " ";
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	

	public static void fillNodes(FileWriter fw,String position)
	{
		try {
			fw.write("<Placemark>\n");
			fw.write("<name>"+ "Node"+Node_count+"</name> \n");
			Node_count++;
			fw.write("      <TimeStamp>\n"+"		<when>"+LocalDateTime.now()+"Z"+"</when> \n");
			fw.write(	"      </TimeStamp>\n" +"      <styleUrl>#paddle-a</styleUrl>\n" +"      <Point>\n");		
			fw.write("        <coordinates>");
			fw.write(position);
			fw.write(",0</coordinates>\n");
			fw.write("      </Point>\n");
			fw.write("    </Placemark>\n");
			Coordinates+=position.substring(0,28)+ " ";	
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void fillEdges(FileWriter fw,String coordinates)
	{
		try {
			fw.write("\n<Placemark>\n");
			fw.write("<name> Graph Edges</name>\n");
			fw.write(" <LineString>\n");
			fw.write("  <coordinates>\n");
			fw.write(coordinates+"\n");
			fw.write("  </coordinates>\n");
			fw.write(" </LineString>\n\n");
			fw.write(" <Style>\n");
			fw.write("  <LineStyle>\n");
			fw.write("   <color>#ff0000ff</color>\n");
			fw.write("	 <width>5</width>\n");
			fw.write("  </LineStyle>\n");
			fw.write(" </Style>\n");
			fw.write("    </Placemark>\n");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void openKMLFolder(FileWriter fw,String foldername,String description)
	{
		try {
			fw.write("    <Folder>");
			fw.write("<name>"+ foldername +"</name>\n" + 
					"      <visibility>0</visibility>\n" + 
					"      <description>" +description +"</description>\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void closeKMLFolder(FileWriter fw)
	{
		try {
			fw.write("    </Folder> \n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

	
	public  void closekml() throws IOException
	{
		try
		{
			fw.write("\n </Document>"); 
			fw.write("\n");
	        fw.write("</kml>");
	        fw.write("\n");
	        fw.close();  
		}
		
		catch(IOException e)
		{
			 System.out.println(e);
		} 
		  
		
	}
	public static String readFile() throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/testkml.kml"));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    }finally {
	            br.close();
	        }
	}

		public static void main(String[] args) throws IOException {
				
}

		
}
