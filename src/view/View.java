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
		System.out.println("1. M comparendos de mayor gravedad.");
		System.out.println("2. Comparendos por mes y dia de las semana.");
		System.out.println("3. Comparendo por fecha y hora en rango.");
		System.out.println("4. M comparendos mas cercanos a estacion.");
		System.out.println("5. Buscar los comparendos por medio de detección, clase de vehículo, tipo de servicio y localidad.");
		System.out.println("6. Buscar los comparendos que tienen una latitud en un rango dado y que involucraron un tipo de vehículo particular.");
		System.out.println("7. Tabla ASCII.");
		System.out.println("8. Costo de espera.");
		System.out.println("9. Comparador Tiempo.");

	}

	public void printMessage(String mensaje) {

		System.out.println(mensaje);
	}		

	public void printModelo(Modelo modelo)
	{
		// TODO implementar

	}
}
