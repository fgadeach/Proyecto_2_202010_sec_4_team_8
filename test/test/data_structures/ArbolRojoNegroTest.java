package test.data_structures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

import model.data_structures.ArbolRojoNegro;

public class ArbolRojoNegroTest 
{
	private ArbolRojoNegro<Integer, String> arbol;
	
	public void setUpScenario1()
	{
		arbol = new ArbolRojoNegro<>();
	}
	
	public void setUpScenario2() 
	{
		arbol = new ArbolRojoNegro<>();
	
		try
		{
			arbol.put(0,"Primero");
			arbol.put(1,"Segundo");
			arbol.put(2,"Tercero");
			arbol.put(3,"Cuarto");
			arbol.put(4,"Quinto");
			
			for(int i =0; i<5;i++)
			{
				System.out.println(arbol.get(i));
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSize() 
	{
		setUpScenario1();
		assertEquals(0, arbol.size(), "El tamaño no es correcto. No deberian haber elementos");
		
		try 
		{
			setUpScenario2();
			assertEquals(5, arbol.size(), "El numero de elementos no es correcto");
		} 
		catch (Exception e) 
		{
			fail("No deberia mandar exception");
		}
	}
	

	@Test
	public void testPut() 
	{
		try 
		{
			setUpScenario1();
			arbol.put(0, "Primero");
			assertEquals(1, arbol.size() , "No se agrego correctamente");
			assertEquals("Primero", arbol.get(0), "No se agrego correctamente");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDelete() 
	{
		try 
		{
			setUpScenario2();
			
			for(int i = 0; i< 5;i++)
			{
				arbol.delete(i);
			}
			
			assertEquals(0, arbol.size(), "No se estan sacando los elementos");
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void testIsEmpty()
	{
		setUpScenario1();
		assertTrue(arbol.isEmpty(), "La lista esta vacia");
		
		try 
		{
			setUpScenario2();
			assertFalse(arbol.isEmpty(), "La lista no esta vacia");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}