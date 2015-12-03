package cl.bcs.scheduler.orders;

import cl.bcs.scheduler.BcsJob;

//toda implementacion debe tener test junit
public class BcsJobOrderChronologicAllocation extends BcsJob{

	private int idTarea;
 
	public int getIdTarea() {
		return idTarea;
	}

	public BcsJobOrderChronologicAllocation(int idTarea) {
		 
		this.idTarea = idTarea;
	}

	public void setIdTarea(int idTarea) {
		this.idTarea = idTarea;
	}

	@Override
	public void transaction() throws Throwable {
		
		System.out.println("Entré a BcsJobOrderChronologicAllocation");

		esperarXsegundos(1);
		System.out.println("Estoy con id = " + this.getIdTarea());
		
		//traer informacion relacionada de la asignacion: ordenes, palos y asignaciones fijas
		
		//realizar asignacion
		
		//escribir asignacion en base de datos
	}

	@Override
	public void rollback(Throwable t) {
		//si ocurre un problema se debe dejar todo tal cual como estaba
		
		//loggear
	}
	private void esperarXsegundos(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

}
