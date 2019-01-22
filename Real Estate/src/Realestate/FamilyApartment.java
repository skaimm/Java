package Realestate;

public class FamilyApartment extends Apartment{

	int number_of_children;

	public FamilyApartment(double area, int number_of_rooms, int number_of_tenants, double price_for_m2) {
		super(area, number_of_rooms, 0, price_for_m2);
		number_of_children=0;
	}
	
	boolean childIsBorn(){
		if(number_of_tenants<2)
		{
			System.out.println("There is no enough people");
			return false;
		}
		
		number_of_children++;
		System.out.println("The child was born");
		return true;
	}

	@Override
	boolean moveIn(int tenants) {
		if(tenants==2 && area/getNumberOfTenants()>10)
		{
			System.out.println("The rent is succesfull");
			return true;
		}
		
		System.out.println("They can't move in Aparment");
		return false;
	}

	@Override
	public int getNumberOfTenants() {
		
		return number_of_tenants+(number_of_children/2);
	}

	@Override
	public String toString() {
		return super.toString() + " Number Of Family' children" + number_of_children;
	}

	
	
	
	
	
	

}
