package model.logic;

import java.awt.List;



import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.data_structures.ArbolRojoNegro;
import model.data_structures.Array;
import model.data_structures.LinearProbingHashST;
import model.data_structures.Queue;
import model.data_structures.SeparateChainingHashST;


/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {

	int numeroElementos = 0;

	int m = 1500;

	private SeparateChainingHashST<String, Array<Comparendos>> hashSectoresSC = new SeparateChainingHashST<String, Array<Comparendos>>(m);

	private ArbolRojoNegro<String,Comparendos> listaComparendos = new ArbolRojoNegro<>();
	private ArbolRojoNegro<String,Comparendos> listaComparendosMayorGravedad = new ArbolRojoNegro<>();

	public void loadComparendos (String comparendosFile)
	{
		JSONParser parser = new JSONParser();

		try {     
			Object obj = parser.parse(new FileReader(comparendosFile));

			JSONObject jsonObject =  (JSONObject) obj;
			JSONArray jsArray = (JSONArray) jsonObject.get("features");

			for(Object o: jsArray) {
				JSONObject comp = (JSONObject) o;	
				JSONObject properties =  (JSONObject) comp.get("properties");
				JSONObject geometry =  (JSONObject) comp.get("geometry");
				JSONArray coordinates = (JSONArray) geometry.get("coordinates");
				Comparendos comparendo = new Comparendos(String.valueOf(comp.get("type")), Integer.parseInt(String.valueOf(properties.get("OBJECTID"))), String.valueOf(properties.get("FECHA_HORA")),String.valueOf(properties.get("MEDIO_DETECCION")), String.valueOf(properties.get("CLASE_VEHICULO")), String.valueOf(properties.get("TIPO_SERVICIO")), String.valueOf(properties.get("INFRACCION")), String.valueOf(properties.get("DES_INFRACCION")), String.valueOf(properties.get("LOCALIDAD")),String.valueOf(properties.get("MUNICIPIO")), String.valueOf(geometry.get("type")), String.valueOf(coordinates));
				String objectId = Integer.toString(comparendo.getOBJECTID());

				String Key = comparendo.getFECHA_HORA()+comparendo.getCLASE_VEHI()+comparendo.getINFRACCION();


				listaComparendos.put(objectId, comparendo);

				if(hashSectoresSC.get(Key) != (null))
				{
					hashSectoresSC.get(Key).append(comparendo);
				}

				else
				{
					hashSectoresSC.put(Key, new Array<Comparendos>());
					hashSectoresSC.get(Key).append(comparendo);
				}


			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e){
			e.printStackTrace();
		}
	}

	public int numeroComparendos() 
	{
		return listaComparendos.size();
	}
	public String valorMinimoObjectId() 
	{
		return listaComparendos.min();	
	}
	public String valorMaximoObjectId() 
	{
		return listaComparendos.max();	
	}

	public void buscarComparendoId(String id) 
	{
		Comparendos comparendo = listaComparendos.get(id);

		System.out.println("OBJECTID: " + comparendo.getOBJECTID() + "\nFECHA_HORA: " + comparendo.getFECHA_HORA() + "\nTIPO_SERVI: " + comparendo.getTIPO_SERVI() + "\nCLASE_VEHI: " + comparendo.getCLASE_VEHI() + "\nINFRACCION: " + comparendo.getINFRACCION());
	}

	public void consultarComparendosRango(String idMenor, String idMayor) 
	{
		Iterator<String> iter = listaComparendos.keysInRange(idMenor, idMayor);
		System.out.println(iter.hasNext());
		while(iter.hasNext()) 
		{	
			String llave = iter.next();
			Comparendos comparendo = listaComparendos.get(llave);
			System.out.println("OBJECTID: " + comparendo.getOBJECTID() + "\nFECHA_HORA: " + comparendo.getFECHA_HORA() + "\nTIPO_SERVI: " + comparendo.getTIPO_SERVI() + "\nCLASE_VEHI: " + comparendo.getCLASE_VEHI() + "\nINFRACCION: " + comparendo.getINFRACCION());
		}
	}

	public void datosR() 
	{
		System.out.println("Altura" + listaComparendos.altura());
		System.out.println("Total nodos" + listaComparendos.size());
	}

	public void comparendosMayorGravedad(int m) 
	{

		Iterator<String> iter = listaComparendos.keys();
		int numero = -20;

		while(iter.hasNext()) 
		{	
			String llave = iter.next();
			Comparendos comparendo = listaComparendos.get(llave);

			if(comparendo.getTIPO_SERVI().equals("Público")) 
			{
				numero = 3;
			}
			else if(comparendo.getTIPO_SERVI().equals("Oficial")) 
			{
				numero = 2;
			}
			else if(comparendo.getTIPO_SERVI().equals("Particular")) 
			{
				numero = 1;
			}

			String objectId = numero+comparendo.getINFRACCION();
			listaComparendosMayorGravedad.put(objectId, comparendo);

		}
		Iterator<String> iter2 = listaComparendosMayorGravedad.keys();
		while(iter2.hasNext() && m>0) 
		{	

			String llave = iter2.next();
			Comparendos comparendo = listaComparendosMayorGravedad.get(listaComparendosMayorGravedad.max());
			System.out.println("OBJECTID: " + comparendo.getOBJECTID() + "\nFECHA_HORA: " + comparendo.getFECHA_HORA() + "\nTIPO_SERVI: " + comparendo.getTIPO_SERVI() + "\nCLASE_VEHI: " + comparendo.getCLASE_VEHI() + "\nINFRACCION: " + comparendo.getINFRACCION());
			listaComparendosMayorGravedad.deleteMax();
			m--;
		}
	}

	public void comparendosMasCercanos(int m) 
	{

		Iterator<String> iter = listaComparendos.keys();
		double numero = -20;
		double comparador = 0;
		double latitudC = 4.647586;
		double longitudC = 74.078122;

		while(iter.hasNext()) 
		{	
			String llave = iter.next();
			Comparendos comparendo = listaComparendos.get(llave);
			String coordenadas = comparendo.getCOORDINATES();
			String[] parts = coordenadas.split(",");

			String part1 = parts[0];
			Double longitud = Double.parseDouble(part1.replaceAll("\\[",""));

			String part2 = parts[1];
			Double latitud = Double.parseDouble(part2.replaceAll("\\[",""));

			numero = Math.sqrt((latitudC-latitud)*(latitudC-latitud) + (longitudC-longitud)*(longitudC-longitud));
			String objectId = numero/comparador+comparendo.getINFRACCION();

			listaComparendosMayorGravedad.put(objectId, comparendo);	
		}
		Iterator<String> iter2 = listaComparendosMayorGravedad.keys();

		while(iter2.hasNext() && m>0) 
		{	
			String llave = iter2.next();
			Comparendos comparendo = listaComparendosMayorGravedad.get(listaComparendosMayorGravedad.min());
			System.out.println("OBJECTID: " + comparendo.getOBJECTID() + "\nFECHA_HORA: " + comparendo.getFECHA_HORA() + "\nTIPO_SERVI: " + comparendo.getTIPO_SERVI() + "\nCLASE_VEHI: " + comparendo.getCLASE_VEHI() + "\nINFRACCION: " + comparendo.getINFRACCION());
			listaComparendosMayorGravedad.deleteMin();
			m--;
		}
	}

	@SuppressWarnings("deprecation")
	public void comparendosMesyDia(int m, String d) throws Exception 
	{
		Iterator<String> iter = listaComparendos.keys();
		int diaInt = 6;
		int contador = 0;

		if(d.equalsIgnoreCase("d")) 
		{
			diaInt = 0;
		}
		if(d.equalsIgnoreCase("l")) 
		{
			diaInt = 1;
		}
		if(d.equalsIgnoreCase("m")) 
		{
			diaInt = 2;
		}
		if(d.equalsIgnoreCase("i")) 
		{
			diaInt = 3;

		}
		if(d.equalsIgnoreCase("j")) 
		{
			diaInt = 4;

		}
		if(d.equalsIgnoreCase("v")) 
		{
			diaInt = 5;

		}


		while(iter.hasNext()) 
		{	

			String llave = iter.next();
			Comparendos comparendo = listaComparendos.get(llave);

			String coordenadas = comparendo.getFECHA_HORA();
			String[] parts = coordenadas.split("-");

			String part1 = parts[0];
			String part2 = parts[1];
			String part3 = parts[2];

			String[] diaP = part3.split("T");		
			int anio = Integer.parseInt(part1);		
			int mes = Integer.parseInt(part2);


			int dia = Integer.parseInt(diaP[0]);

			Date fecha = new Date(anio,mes,dia);
			int diaSemana = fecha.getDay();

			String objectId = mes + "" + diaSemana;

			String l = m + "" + diaInt;

			if(objectId.equals(l)) {
				contador ++;
				objectId = mes + "" + diaSemana + contador;
			}

			listaComparendosMayorGravedad.put(objectId, comparendo);	
		}

		Iterator<String> iter2 = listaComparendosMayorGravedad.keys();

		while(iter2.hasNext() && contador>0) 
		{	
			String llave = iter2.next();
			System.out.println("/////////");
			String l = m + "" + diaInt;
			try 
			{
				Comparendos comparendo = listaComparendosMayorGravedad.get(l+contador);
				System.out.println("OBJECTID: " + comparendo.getOBJECTID() + "\nFECHA_HORA: " + comparendo.getFECHA_HORA() + "\nTIPO_SERVI: " + comparendo.getTIPO_SERVI() + "\nCLASE_VEHI: " + comparendo.getCLASE_VEHI() + "\nINFRACCION: " + comparendo.getINFRACCION());
				contador --;
			}
			catch (Exception e) {
				System.out.println("Esos son todos los comparendos que cumplen la condicion");
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void comparendosFechayHora(String limite_bajo, String limite_Alto) throws Exception 
	{
		Iterator<String> iter = listaComparendos.keys();

		int contador = 0;

		while(iter.hasNext()) 
		{	
			String llave = iter.next();
			Comparendos comparendo = listaComparendos.get(llave);
			String objectId =comparendo.getFECHA_HORA() + contador;

			listaComparendosMayorGravedad.put(objectId, comparendo);	
		}

		Iterator<String> iter2 = listaComparendosMayorGravedad.keysInRange(limite_bajo, limite_Alto);

		while(iter2.hasNext()) 
		{	
			try 
			{
				String llave = iter2.next();
				Comparendos comparendo = listaComparendosMayorGravedad.get(llave);
				System.out.println("OBJECTID: " + comparendo.getOBJECTID() + "\nFECHA_HORA: " + comparendo.getFECHA_HORA() + "\nTIPO_SERVI: " + comparendo.getTIPO_SERVI() + "\nCLASE_VEHI: " + comparendo.getCLASE_VEHI() + "\nINFRACCION: " + comparendo.getINFRACCION());
			}
			catch (Exception e) {
				System.out.println("Esos son todos los comparendos que cumplen la condicion");
				break;
			}
			contador--;
		}
	}

	public void comparendosMayorDetecClaseEtc(String detec, String clase, String tipo, String localidad) 
	{

		Iterator<String> iter = listaComparendos.keys();
		int contador = 0;

		while(iter.hasNext()) 
		{	
			String llave = iter.next();
			Comparendos comparendo = listaComparendos.get(llave);

			String objectId = comparendo.getMEDIO_DETE()+comparendo.getCLASE_VEHI()+comparendo.getTIPO_SERVI()+comparendo.getLOCALIDAD()+contador;
			listaComparendosMayorGravedad.put(objectId, comparendo);
		}


		Iterator<String> iter2 = listaComparendosMayorGravedad.keys();

		while(iter2.hasNext()) 
		{	
			String llave = iter2.next();
			System.out.println("/////////");
			String l = detec + clase + tipo+localidad+ contador;
			try 
			{
				Comparendos comparendo = listaComparendosMayorGravedad.get(l);
				System.out.println("OBJECTID: " + comparendo.getOBJECTID() + "\nFECHA_HORA: " + comparendo.getFECHA_HORA() + "\nTIPO_SERVI: " + comparendo.getTIPO_SERVI() + "\nCLASE_VEHI: " + comparendo.getCLASE_VEHI() + "\nINFRACCION: " + comparendo.getINFRACCION());
				contador --;
			}
			catch (Exception e) {
				System.out.println("Esos son todos los comparendos que cumplen la condicion");
				break;
			}
		}
	}

	public void comparendosRangoLatitudyTipo(String latitud_menor, String latitud_mayor, String tipo) 
	{

		Iterator<String> iter = listaComparendos.keys();

		while(iter.hasNext()) 
		{	
			String llave = iter.next();
			Comparendos comparendo = listaComparendos.get(llave);
			String coordenadas = comparendo.getCOORDINATES();
			String[] parts = coordenadas.split(",");

			String part2 = parts[1];
			String latitud = part2.replaceAll("\\[","");

			String objectId = latitud;
			listaComparendosMayorGravedad.put(objectId, comparendo);	
		}

		Iterator<String> iter2 = listaComparendosMayorGravedad.keysInRange(latitud_menor, latitud_mayor);
		while(iter2.hasNext()) 
		{	
			String llave = iter2.next();
			Comparendos comparendo = listaComparendosMayorGravedad.get(llave);
			if(comparendo.getCLASE_VEHI().equalsIgnoreCase(tipo)) 
			{
				System.out.println("OBJECTID: " + comparendo.getOBJECTID() + "\nFECHA_HORA: " + comparendo.getFECHA_HORA() + "\nTIPO_SERVI: " + comparendo.getTIPO_SERVI() + "\nCLASE_VEHI: " + comparendo.getCLASE_VEHI() + "\nINFRACCION: " + comparendo.getINFRACCION());
			}
		}
	}

	public void TablaAscii(int d) 
	{
		Iterator<String> iter = listaComparendos.keys();

		int diaC = 01;
		int mesC = 01;
		int anioC = 2018;
		int prueba = 0;
		String key = anioC+"/" + mesC+"/" + diaC; 

		int contadorMaximo = 1500;
		int contadorGeneral =0;

		while(iter.hasNext()) 
		{
			String llave = iter.next();
			Comparendos comparendo = listaComparendos.get(llave);

			String coordenadas = comparendo.getFECHA_HORA();
			String[] parts = coordenadas.split("-");

			String part1 = parts[0];
			String part2 = parts[1];
			String part3 = parts[2];

			String[] diaP = part3.split("T");
			int anio = Integer.parseInt(part1);		
			int mes = Integer.parseInt(part2);
			int dia = Integer.parseInt(diaP[0]);

			String codigo = anio+"/"+mes+"/"+dia;

			if(hashSectoresSC.get(codigo) != (null))
			{

				hashSectoresSC.get(codigo).append(comparendo);
			}
			else
			{
				hashSectoresSC.put(codigo, new Array<Comparendos>());
				hashSectoresSC.get(codigo).append(comparendo);
			}	

		}

		Iterator<String> iterhash = hashSectoresSC.keys();

		while(iterhash.hasNext())
		{
			diaC = diaC+prueba;
			if(diaC>31) {mesC+=1;diaC=diaC-31;}
			key = anioC+"/" + mesC+"/" + diaC;

			String llave = iterhash.next();

			hashSectoresSC.get(key);

			if(key.compareTo(llave)>=0 && prueba<=d)
			{

				for(int i = 0; i < hashSectoresSC.get(llave).size(); i++) 
				{
					if(contadorMaximo>0) 
					{
						contadorGeneral++;
						contadorMaximo--;
					}else 
					{
						break;
					}
				}
				contadorMaximo = 1500;
				prueba++;
			}

			else if(prueba>d)
			{
				System.out.println(llave + "-" + key + " | numero Comparendos: " + contadorGeneral);
				contadorGeneral = 0;
				diaC = diaC+prueba;
				prueba = 0;
			}
		}
	}

	public void costoDeTiempo(int d) 
	{
		Iterator<String> iter = listaComparendos.keys();

		int diaC = 01;
		int mesC = 01;
		int anioC = 2018;
		int prueba = 0;
		int contadortiempo=0;
		int entra = 0;
		String key = anioC+"/" + mesC+"/" + diaC; 

		int contadorMaximo = 1500;
		int contadorGeneral =0;

		while(iter.hasNext()) 
		{
			String llave = iter.next();
			Comparendos comparendo = listaComparendos.get(llave);

			String coordenadas = comparendo.getFECHA_HORA();
			String[] parts = coordenadas.split("-");

			String part1 = parts[0];
			String part2 = parts[1];
			String part3 = parts[2];

			String[] diaP = part3.split("T");
			int anio = Integer.parseInt(part1);		
			int mes = Integer.parseInt(part2);
			int dia = Integer.parseInt(diaP[0]);

			String codigo = anio+"/"+mes+"/"+dia;

			if(hashSectoresSC.get(codigo) != (null))
			{

				hashSectoresSC.get(codigo).append(comparendo);
			}
			else
			{
				hashSectoresSC.put(codigo, new Array<Comparendos>());
				hashSectoresSC.get(codigo).append(comparendo);
			}	

		}

		Iterator<String> iterhash = hashSectoresSC.keys();

		while(iterhash.hasNext())
		{
			diaC = diaC+prueba;
			if(diaC>31) {mesC+=1;diaC=diaC-31;}
			key = anioC+"/" + mesC+"/" + diaC;

			String llave = iterhash.next();

			hashSectoresSC.get(key);

			if(key.compareTo(llave)>=0 && prueba<=d)
			{

				for(int i = 0; i < hashSectoresSC.get(llave).size(); i++) 
				{
					entra++;
					if(contadorMaximo>0) 
					{
						contadorGeneral++;
						contadorMaximo--;
					}
					else 
					{
						contadortiempo++;
					}
				}
				contadorMaximo = 1500;
				prueba++;
			}
			else if(prueba>d)
			{
				System.out.println(llave + "-" + key + " | numero Comparendos: " + contadorGeneral + "| comparendos perdidos: " + contadortiempo);
				contadorGeneral = 0;
				contadortiempo=0;
				diaC = diaC+prueba;
				prueba = 0;
			}
			else {
				diaC = diaC+prueba;
			}
		}
		System.out.println(entra);
	}
}

