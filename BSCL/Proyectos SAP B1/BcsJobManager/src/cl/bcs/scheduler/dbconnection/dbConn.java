package cl.bcs.scheduler.dbconnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;


public class dbConn 
{
   static Properties con = Properties.getInstance();
   static Connection Connec=null;
  
   public static void main(String[] args)
   {
	   try {
		   /*
			con.getCredentials();
	   		Class.forName(con.getDRIVERHANA());
	   		String url = con.getDBURL()+con.getSCHEMADB();
	   		String user = con.getDBNAME();
	   		String password = con.getDBPASS();
	   		System.out.println("Url" + url);
	   		System.out.println("drive" + con.getDRIVERHANA());
	   		System.out.println("user" + user);
	   		System.out.println("pass" + password);
	   		Connection cn = java.sql.DriverManager.getConnection(url, user, password); */
		    Connec = getConnection();
		    habilitarTarea();
		    deshabilitarTarea();
	        ResultSet rs ;
	        
	      
	        //"call \"BSCL.Plataforma.BD.Servicios::HabilitarTareas\" (?)"
	   		//habilitarTarea();   
		  //  ResultSet rs = consultarTareas("ASIG_CRONO");
		      
		       rs = null;

	           rs =  consultarTareas("ASIG_CRONO");
	          
	           while(rs.next())
		    	{
		    	
		    		if(rs.getString("IdTipoTarea").equals("ASIG_CRONO"))
		    		{
            		 
		    			if(rs.getString("idEstado").equals("PEN"))
		    			{
		    				
		    				actualizarTareas(Integer.parseInt(rs.getString("IdPrograma")));
		    		
		    			}
		    		}
            	 
		    	}
		    	
		   
         
		    // ... do whatever with the results ...
		
		} catch (Exception e) {
		    e.printStackTrace();
		    Logger.getLogger(dbConn.class.getName()).log(Level.WARNING, e.getMessage(),e.getCause());
		} 
   }
   public static Connection dbConnection()
   {  
	    
	   try{
		if(Connec==null)
		{
		con.getCredentials();
   		Class.forName(con.getDRIVERHANA());
   		String url = con.getDBURL()+con.getSCHEMADB();
   		String user = con.getDBNAME();
   		String password = con.getDBPASS();
   		System.out.println("Url" + url);
   		System.out.println("drive" + con.getDRIVERHANA());
   		System.out.println("user" + user);
   		System.out.println("pass" + password);
   		 

   		Connec = java.sql.DriverManager.getConnection(url, user, password);
		}
   		return Connec;
		 
		
	   }catch(Exception e)
	   {
		   Logger.getLogger(dbConn.class.getName()).log(Level.SEVERE, e.getMessage(),e.getCause());
		   return null;
	   }
	   
   }
   public static ResultSet executeQuery(String query) throws SQLException
   {
	   Logger.getLogger(dbConn.class.getName()).log(Level.INFO, "Ejecutando : " + query);
	   ResultSet rs =  getConnection().createStatement().executeQuery(query);
		  
	 
	  
	   return rs;
   }
   public static ResultSet consultarTareas(String idTipoTarea)
   {
	   
	   String query ="call \"BSCL.Plataforma.BD.Servicios::BAT.ConsultarTareas\"('"+idTipoTarea+"',?) ";
	   
	   ResultSet rs = null;
	try {
		rs = executeQuery(query);
	} catch (SQLException e) {

		Logger.getLogger(dbConn.class.getName()).log(Level.WARNING, e.getMessage(),e.getCause());
		e.printStackTrace();
	}
	
	   return rs;
   }
   public static void actualizarTareas(int idTarea) throws SQLException
   {
	  
	   executeQuery("call \"BSCL.Plataforma.BD.Servicios::BAT.ActualizarTareas\"('"+idTarea+"') ");
    	 
   }
   
   public static void habilitarTarea() throws SQLException  
   {
	 ResultSet rs = executeQuery("call \"BSCL.Plataforma.BD.Servicios::BAT.HabilitarTareas\" (?)");
	 Date date = new Date();
	 String dateChar = date.toLocaleString().replace("AM", "").replace("PM", "");
	 
	 String aux = dateChar.substring(6, 10)+"-"+dateChar.substring(3,5)+"-"+dateChar.substring(0, 2);
	 

	
	 try
	 {
		 
		 
			 while(rs.next())
			 {
				 
			 
				 aux = aux+" "+rs.getString("Hora")+":"+rs.getString("Minuto")+":00";
				 executeQuery("call \"BSCL.Plataforma.BD.Servicios::BAT.AgregarTarea\" ( '1',"+rs.getString("IdAgenda")+",'"+rs.getString("IdTipoTarea")+"',0,'System','PEN','Esperando Ejecución','"+aux+"')");
			       //executeQuery("call \"BSCL.Plataforma.BD.Servicios::EliminarTarea\" ('"+rs.getString("IdPrograma")+"');");
			 }
		 
		 
	 }catch(SQLException e)
	 {
		 Logger.getLogger(dbConn.class.getName()).log(Level.SEVERE, e.getMessage(),e.getCause());
		 e.printStackTrace();
	 }
	
   }
   public static void deshabilitarTarea() throws SQLException  
   {
	   String c = "call \"BSCL.Plataforma.BD.Servicios::BAT.DeshabilitarTareas\"(?)";
	  
	 ResultSet rs = executeQuery("call \"BSCL.Plataforma.BD.Servicios::BAT.DeshabilitarTareas\"(?)");

	 try
	 {
		 
		 
			 while(rs.next())
			 {
				 
				 
				 executeQuery("call \"BSCL.Plataforma.BD.Servicios::BAT.EliminarTarea\" ('"+rs.getString("IdAgenda")+"')");
			   //executeQuery("call \"BSCL.Plataforma.BD.Servicios::EliminarTarea\" ('"+rs.getString("IdPrograma")+"');");
			 }
		 
		 
	 }catch(SQLException e)
	 {
		 Logger.getLogger(dbConn.class.getName()).log(Level.SEVERE, e.getMessage(),e.getCause());
		 e.printStackTrace();
	 }
	
   }
   public static Connection getConnection()
   {
	   if(Connec==null)
	   {
		  Connec = dbConnection();
	   }
	return Connec;
	   
   }
   
   
}
