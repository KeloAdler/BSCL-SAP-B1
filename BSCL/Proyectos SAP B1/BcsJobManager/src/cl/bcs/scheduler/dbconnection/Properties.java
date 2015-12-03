package cl.bcs.scheduler.dbconnection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import sun.security.jca.GetInstance;

public class Properties 
{
	private  String DBNAME;
	private  String SCHEMADB;
	private  String DBURL;
	private  String DRIVERHANA;
	private  String DBPASS;
	private static Properties instance = null;
    
	public  String getDBNAME() {
		return DBNAME;
	}
	public  void setDBNAME(String dBNAME) {
		DBNAME = dBNAME;
	}
	public  String getSCHEMADB() {
		return SCHEMADB;
	}
	public  void setSCHEMADB(String SCHEMADB) {
		this.SCHEMADB = SCHEMADB;
	}
	public  String getDBURL() {
		return DBURL;
	}
	public  void setDBURL(String dBURL) {
		DBURL = dBURL;
	}
	public String getDRIVERHANA() {
		return DRIVERHANA;
	}
	public void setDRIVERHANA(String dRIVERHANA) {
		DRIVERHANA = dRIVERHANA;
	}
	public String getDBPASS() {
		return DBPASS;
	}
	public void setDBPASS(String dBPASS) {
		DBPASS = dBPASS;
	}
	public static Properties getInstance()  
	{
		if(instance==null)
		{
			System.out.println("Inicado con patrón singleton");
			instance = new Properties();
		}
		return instance;
		
	}
	public static Properties getCredentials() throws IOException
	{
		
		   // The name of the file to open.
		   // The name of the file to open.
        String fileName = "db.properties";
        
        // This will reference one line at a time
        String line = null;
        Properties prop =  getInstance();
        if(prop.DBURL!=null)
        {
        	return prop;
        }
      
        String[] separator ;
        String[] names;
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                
                
                separator = line.split(";");
                names = separator[0].split("=");
                map.put(names[0], names[1]);
                
                
            }   
            prop.setDBNAME(map.get("user"));
            prop.setDBPASS(map.get("password"));
            prop.setSCHEMADB(map.get("schema"));
            prop.setDBURL(map.get("url"));
            prop.setDRIVERHANA(map.get("driver"));
            
             
            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
		return prop;
		
	}
	public static void main(String[] args) throws IOException
	{
		 for(int i = 1; i<3;i++)
		 {
			 System.out.println("indice" + i);
	        getCredentials();
		 }
	}
	
	
	

	
	

}
