package cl.bcs.scheduler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BcsJobManager implements Runnable{

	private final ExecutorService executor;
	private final ExecutorService managerService = Executors.newSingleThreadExecutor();
	private final int pollIntervalSeconds;
	
	public BcsJobManager(int concurrentJobs, int pollIntervalSeconds){
		this.executor = Executors.newFixedThreadPool(concurrentJobs);
		this.pollIntervalSeconds = pollIntervalSeconds;
		this.managerService.submit(this);
	}

	protected abstract List<BcsJob> poolJobs();
	
	@Override
	public void run(){
		while(true){
			try {
				Thread.sleep(1000*pollIntervalSeconds);
			} catch (InterruptedException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Job Manager interrumpido", e);
				throw new RuntimeException(e);
			}
			List<BcsJob> jobs = poolJobs();
			for(BcsJob j : jobs){
				executor.submit(j);
			}
		}
	}
}
