package cl.bcs.scheduler;

public abstract class BcsJob implements Runnable{

	public abstract void transaction() throws Throwable;
	public abstract void rollback(Throwable t);
	
	@Override
	public void run() {
		try {
			transaction();
		} catch (Throwable e) {
			rollback(e);
		}
	}
}
