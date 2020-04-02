package model.data_structures;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import model.logic.Comparendos;

public class LinearProbingHashST <K extends Comparable<K>, V> implements ILinearProbingHashST<K,V>
{
	private K[] keys;

	private V[] values;

	private int capacity;

	private int size = 0;
	
	private int numero;
	
	

	@SuppressWarnings("unchecked")
	public LinearProbingHashST(Class<K> pClass, Class<V> pClassDos, int pCapacity)
	{
		capacity = pCapacity;

		keys = (K[])Array.newInstance(pClass, pCapacity);
		values = (V[])Array.newInstance(pClassDos, pCapacity);
	}

	public int numeroRehash()
	{
		return numero;
	}
	
	public boolean isEmpty()
	{
		return size() == 0;
	}

	
	public V get(K key)
	{
		if(key == null)
			throw new IllegalArgumentException("argument to delete() is null");
		
		for(int i = hash(key); keys[i] != null;  i = (i+1)%capacity)
			if(keys[i].equals(key))
			{
				return values[i];
			}
		
		return null;
	}
	
	public void put(K key, V valu)
	{
		if(key == null)
			throw new IllegalArgumentException("argument to out is null");
		
		if(valu == null)
		{
			delete(key);
			return;
		}
		
		int i;
		for(i = hash(key); keys[i] != null; i = (i + 1) % capacity)
		{
			if(keys[i].equals(key))
			{
				values[i] = valu;
				return;
			}
		}
		size++;
		keys[i] = key;
		values[i] = valu;	

	}
	
	public void delete(K key)
	{
		if(key == null)
			throw new IllegalArgumentException("The argument is null");
		
		if(!contains(key))
			return;
		
		int i = hash(key);
		while(!key.equals(keys[i]))
		{
			i = (i + 1) % capacity;
		}
		
		keys[i] = null;
		values[i] = null;
		
		i = (i + 1) % capacity;
		while(keys[i] != null)
		{
			K keyToReHash = keys[i];
			V valueToReHash = values[i];
			
			keys[i] = null;
			values[i] = null;
			size--;
			
			put(keyToReHash, valueToReHash);
			
			i = (i + 1) % capacity;
			numero++;
		}
		
		size--;
	}
	
	
	public boolean contains(K key)
	{
		if(key == null)
			throw new IllegalArgumentException("The argument is null");
		
		return get(key) != null;
	}
	
	 public Iterator<K> keys()
	 {
	        Queue<K> list = new Queue<>();
	        for (int i = 0; i < capacity; i++)
	            if (keys[i] != null) 
	            	list.enqueue(keys[i]);
	        
	        return list.iterator();
	    }
	
	private int hash(K key)
	{
		return(key.hashCode() & 0x7fffffff) % capacity;
	}
	
	public int size()
	{
		return size;
	}	
}