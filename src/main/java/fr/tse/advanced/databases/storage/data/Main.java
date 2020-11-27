package fr.tse.advanced.databases.storage.data;

import java.util.Map;

public class Main {

	public static void main(String[] args) {
		DataBase database = new DataBase();
		
		String name = "myName";
		Series<Int64> s = new Series<Int64>(name);
		database.addSeries(s);
		
		
		
		
		
		
		Series ser = database.getByName(name);
		
		System.out.println("Fin du main");
		
	}
	
}
