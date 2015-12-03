package cl.bcs.scheduler.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cl.bcs.scheduler.BcsJob;
import cl.bcs.scheduler.BcsJobManager;
import cl.bcs.scheduler.dbconnection.dbConn;
import cl.bcs.scheduler.orders.BcsJobOrderChronologicAllocation;

//crear una clase Servlet que incialice con el contenedor
//instanciar esta clase desde el Servlet, con propiedades 2, 30

public class BcsJobManagerImpl extends BcsJobManager {

	private static BcsJobManagerImpl instance = null;
	private dbConn con;
	
	public BcsJobManagerImpl()
	{
		super(2, 30);
	}
	public BcsJobManagerImpl(int concurrentJobs, int pollIntervalSeconds) {
		super(concurrentJobs, pollIntervalSeconds);
	}
	ExecutorService executor = Executors.newFixedThreadPool(1);
	@Override
	public List<BcsJob> poolJobs() {
		List<BcsJob> jobs = new ArrayList<BcsJob>();
	     ResultSet rs = dbConn.consultarTareas("ASIG_CRONO");
	     int contador = 0;
	     try {
			while(rs.next())
			 {
				BcsJobOrderChronologicAllocation r =  new BcsJobOrderChronologicAllocation(Integer.parseInt(rs.getString("IdPrograma")));
			    r.transaction();
			 }
		
		} catch (Throwable e) {
		    e.printStackTrace();
		}
	    
	     
		//si el job es de tipo ASIGNACION_CRONOLOGICA entonces inicializar con clase BcsJobOrderChronologicAllocation
	    
		return jobs;
	}
	public static BcsJobManagerImpl getInstance()
	{
		if(instance==null)
		{
			System.out.println("Inicado con patrón singleton");
			instance = new BcsJobManagerImpl();
		}
		
			return instance;
		
	}
	public static void main(String[] args) {
		try
		{
			getInstance().poolJobs();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}

	

}
