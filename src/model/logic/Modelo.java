package model.logic;

import java.awt.List;


import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.data_structures.Array;
import model.data_structures.LinearProbingHashST;
import model.data_structures.SeparateChainingHashST;


/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {

	int numeroElementos = 0;

	int m = 11000;

	private SeparateChainingHashST<String, Array<Comparendos>> hashSectoresSC = new SeparateChainingHashST<String, Array<Comparendos>>(m);

	private LinearProbingHashST<String, Comparendos> hashSectoresLP = new LinearProbingHashST<String, Comparendos>(String.class, Comparendos.class, m);

	public void loadComparendos (String comparendosFile)
	{
		JSONParser parser = new JSONParser();
		try {     
			Object obj = parser.parse(new FileReader(comparendosFile));

			JSONObject jsonObject =  (JSONObject) obj;
			JSONArray jsArray = (JSONArray) jsonObject.get("features");

			for(Object o: jsArray) 
			{
				JSONObject comp = (JSONObject) o;	
				JSONObject properties =  (JSONObject) comp.get("properties");
				JSONObject geometry =  (JSONObject) comp.get("geometry");
				JSONArray coordinates = (JSONArray) geometry.get("coordinates");
				Comparendos comparendo = new Comparendos(String.valueOf(comp.get("type")), Integer.parseInt(String.valueOf(properties.get("OBJECTID"))), String.valueOf(properties.get("FECHA_HORA")), String.valueOf(properties.get("CLASE_VEHI")), String.valueOf(properties.get("TIPO_SERVI")), String.valueOf(properties.get("INFRACCION")), String.valueOf(properties.get("DES_INFRAC")), String.valueOf(properties.get("LOCALIDAD")), String.valueOf(geometry.get("type")), String.valueOf(coordinates));
				String Key = comparendo.getFECHA_HORA()+comparendo.getCLASE_VEHI()+comparendo.getINFRACCION();

				if(hashSectoresLP.contains(Key))
				{
					Key = Key + "a";
					hashSectoresLP.put(Key, comparendo);
				}

				else 
				{
					hashSectoresLP.put(Key, comparendo);
				}

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

	public void numeroDuplasLP() 
	{
		System.out.println(hashSectoresLP.size());
	}
	public void numeroDuplasSC() 
	{
		System.out.println(hashSectoresSC.size());
	}

	public void tamanioInicialLP() {
		System.out.println(m);
	}
	public void tamanioInicialSC() {
		System.out.println(m);
	}

	public void tamanioFinalLP() {
		System.out.println(m-hashSectoresLP.size());
	}
	public void tamanioFinalSC() {
		System.out.println(m-hashSectoresSC.size());
	}

	public void factorCargaLP() 
	{
		System.out.println(hashSectoresLP.size()/m);
	}

	public void factorCargaSC() 
	{
		System.out.println(hashSectoresSC.size()/m);
	}

	public int numeroRehashLP() {
		return hashSectoresLP.numeroRehash();
	}

	public void buscarComparendoLP(String fecha, String clase, String infraccion) 
	{
		String Key = fecha+clase+infraccion; 
		Iterator<String> iter = hashSectoresLP.keys();

		while(iter.hasNext())
		{
			String llave = iter.next();
			if(Key.equals(llave)  || llave.equals(Key + "a"))
			{
				System.out.println("OBJECTID: " + hashSectoresLP.get(llave).getOBJECTID() + "\nFECHA_HORA: " + hashSectoresLP.get(llave).getFECHA_HORA() + "\nTIPO_SERVI: " + hashSectoresLP.get(llave).getTIPO_SERVI() + "\n CLASE_VEHI: " + hashSectoresLP.get(llave).getCLASE_VEHI() + "\nINFRACCION: " + hashSectoresLP.get(llave).getINFRACCION());
			}
		}
	}

	public void buscarComparendoSC(String fecha, String clase, String infraccion) 
	{
		String Key = fecha+clase+infraccion; 
		Iterator<String> iter = hashSectoresSC.keys();

		while(iter.hasNext())
		{
			String llave = iter.next();
			if(Key.equals(llave)  || llave.equals(Key + "a"))
			{
				for(int i = 0; i < hashSectoresSC.get(Key).size(); i++) 
				{
					Comparendos comparendo = hashSectoresSC.get(Key).get(i);
					System.out.println("OBJECTID: " + comparendo.getOBJECTID() + "\nFECHA_HORA: " + comparendo.getFECHA_HORA() + "\nTIPO_SERVI: " + comparendo.getTIPO_SERVI() + "\n CLASE_VEHI: " + comparendo.getCLASE_VEHI() + "\nINFRACCION: " + comparendo.getINFRACCION());
				}
			}
		}
	}

	public void desempenioSC() 
	{
		int i =10000;
		Integer nonKey = 2000;
		ArrayList <String> validKey = new ArrayList();

		Iterator<String> iter = hashSectoresSC.keys();
		String primera = iter.next();

		while(iter.hasNext())
		{
			String llave = iter.next();
			validKey.add(llave);

		}
		while(validKey.size()<8000) 
		{
			validKey.add(primera);
		}
		while(validKey.size()<10000) {
			validKey.add(nonKey.toString());
			nonKey--;
		}
		long tiempoI = System.nanoTime();
		
		while(i>0) 
		{
			for (int j = 0; j < validKey.size(); j++) 
			{
				hashSectoresSC.get(validKey.get(j));
				i--;
			}
			i--;
		}
		long tiempoF = System.nanoTime();
		double demora = (tiempoF - tiempoI)/ 1e6;

		System.out.println("Tiempo de demora SC: "+ demora);
	}

	public void desempenioLP() 
	{
		int i =10000;
		Integer nonKey = 2000;
		ArrayList <String> validKey = new ArrayList();

		Iterator<String> iter = hashSectoresLP.keys();
		Iterator<String> iterT = hashSectoresLP.keys();
		
		String primera = iter.next();

		while(iter.hasNext())
		{
			String llave = iter.next();
			validKey.add(llave);
		}
		while(validKey.size()<8000) 
		{
			validKey.add(primera);
		}
		
		while(validKey.size()<10000) {
			validKey.add(nonKey.toString());
			nonKey--;
		}
		
		
		long tiempoI = System.nanoTime();

		while(i>0)
		{
			for (int j = 0; j < validKey.size(); j++) 
			{
				hashSectoresLP.get(validKey.get(j));
				i--;
			}
			i--;
		}
		long tiempoF = System.nanoTime();
		double demora = (tiempoF - tiempoI)/ 1e6;

		System.out.println("Tiempo de demora LP: "+ demora);
	}

}

