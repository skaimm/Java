package Realestate;

import java.util.ArrayList;

import Common.Property;


public class ApartmentBlock {
	
	int max_num_of_apartments;
	int max_num_of_garages;
	public ArrayList<Property> aparts_garages;
	
	public ApartmentBlock(int max_num_of_apartments, int max_num_of_garages) {
		super();
		this.max_num_of_apartments = max_num_of_apartments;
		this.max_num_of_garages = max_num_of_garages;
		this.aparts_garages = new ArrayList<Property>();
	}
		
	public boolean addProperty(ArrayList<String> arguments) {
		if((max_num_of_apartments+max_num_of_garages)==aparts_garages.size())
			return false;
		
		int apart=0,garage=0;
		Property temp = null;
		
		if(aparts_garages.size()>0)
		{
			for(int i=0;i<aparts_garages.size();i++)
			{
				Property element = aparts_garages.get(i);
				if(element instanceof Apartment)
					apart++;
				if(element instanceof Garage)
					garage++;
			}
		}
		
		if(arguments.get(0).equals("rentedapartment"))
		{
			if(apart==max_num_of_apartments)
				return false;
			arguments.get(0);
			arguments.get(1);
			arguments.get(2);
			arguments.get(3);
			arguments.get(4);
			temp = new RentedApartment(Double.parseDouble((String) arguments.get(1)),
					Integer.parseInt((String) arguments.get(2)), 
					Integer.parseInt((String) arguments.get(3)), 
					Double.parseDouble((String) arguments.get(4)));
		}
		if(arguments.get(0).equals("familyapartment"))
		{
			if(apart==max_num_of_apartments)
				return false;
			temp = new FamilyApartment(Double.parseDouble((String) arguments.get(1)),
					Integer.parseInt((String) arguments.get(2)), 
					Integer.parseInt((String) arguments.get(3)), 
					Double.parseDouble((String) arguments.get(4)));
		}
		if(arguments.get(0).equals("garage"))
		{
			if(garage==max_num_of_garages)
				return false;
			
			Boolean heat= false,car=false;
			if(arguments.get(4).equals("heatable"))
				heat =true;
			if(arguments.get(4).equals("yes"))
				car = true;
			temp = new Garage(Double.parseDouble((String) arguments.get(1)),
					Integer.parseInt((String) arguments.get(2)), 
					Integer.parseInt((String) arguments.get(3)), heat,car);
		}
		
		if(!temp.equals(null))
			aparts_garages.add(temp);
		
		for(int i=0;i<aparts_garages.size();i++) {
			aparts_garages.get(i);
		}
	
		return true;
	}
	
	public int numberOfTenants() {
		int living_people=0;
		for(int i=0;i<aparts_garages.size();i++)
		{
			Property element=aparts_garages.get(i);
			
			if(element instanceof RentedApartment)
	        	 living_people += ((RentedApartment) element).number_of_tenants;
	         if(element instanceof FamilyApartment)
	        	 living_people += ((FamilyApartment) element).number_of_tenants;
		}
		return living_people;
	}
	
	public int usedPropertyValue(){
		
		int totalcost=0;
		for(int i=0;i<aparts_garages.size();i++)
		{
			Property element=aparts_garages.get(i);
	         if(element instanceof RentedApartment)
	         {
	        	 if(!((RentedApartment) element).isOccupied())
	        		 totalcost +=((RentedApartment) element).totalCosts(); 
	         }
	         if(element instanceof FamilyApartment)
	         {
	        	 if(((FamilyApartment) element).getNumberOfTenants()>0)
	 	            totalcost += ((FamilyApartment) element).totalCosts();
	         }
	         if(element instanceof Garage)
	         {
	        	 if(((Garage) element).isOccupied())
	        	 totalcost +=((Garage) element).totalCosts();
	         }
		}
	
		return totalcost;
	}
	
	public void printblocks() {
		
		for(int i=0;i<aparts_garages.size();i++) {
			System.out.println(aparts_garages.get(i));
		}
	}
	

}
