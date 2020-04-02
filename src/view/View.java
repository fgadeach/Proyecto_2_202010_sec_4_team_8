package view;

import model.logic.Modelo;
import model.data_structures.*;

public class View 
{
	/**
	 * Metodo constructor
	 */
	public View()
	{

	}

	public void printMenu()
	{
		System.out.println("0. Carga de datos.");
		System.out.println("1. Buscar en Linear Probing.");
		System.out.println("2. Buscar en Separate Chaining.");
		System.out.println("3. Prueba de desempeño.");
	}

	public void printMessage(String mensaje) {

		System.out.println(mensaje);
	}		

	public void printModelo(Modelo modelo)
	{
		// TODO implementar

	}
}
