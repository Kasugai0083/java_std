package com.example.sample1app;

import java.io.Serializable;
import java.util.List;

public interface PersonDAO <T> extends Serializable{
	public List<T> getAll();
	
	public T findById(long id);
	public List<T> findByName(String name);
	
	public List<T> find(String fsfr);
	
	public List<T> findByAge(int min, int max);
	
	public List<T> getPage(int page, int limit);
}
