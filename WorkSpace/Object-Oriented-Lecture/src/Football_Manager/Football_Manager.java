package Football_Manager;

import java.util.*;
import java.text.*;

public class Football_Manager {
	
	
	public static void main(String[] args) {
		League_Manager pl = new League("Premier League", 18);
		League_Manager ll = new League("La Liga", 18);
		League_Manager sa = new League("Seria A", 18);
		
		
		display_Menu(pl,ll);
	}
	
	public static void display_Menu(League_Manager a , League_Manager b) 
	{
		Scanner scanner = new Scanner(System.in);
		while(true) 
		{
			System.out.println("1 - Premier League Menu: ");
			System.out.println("2 - La_Liga ");
			String line = scanner.nextLine();
			int command = 0;
			try {
				command = Integer.parseInt(line);
			}catch(Exception e) { }
			switch(command) 
			{
				case 1 : a.display_Menu();
				case 2 : b.display_Menu();
				default:
				System.out.println("Wrong Command");	
			}
		}
	}

}
