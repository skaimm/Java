package Realestate;


import Common.Rentable;

public class RentedApartment extends Apartment implements Rentable{
	
	int number_of_months; // gives the number of months for which the apartment is rented.
	
	public RentedApartment(double area, int number_of_rooms,
			int number_of_tenants, double price_for_m2) {
		super(area, number_of_rooms, number_of_tenants, price_for_m2);
		this.number_of_months=0;
	}



	@Override
	public double costOfRent(int numberOfMonths) {
		if(number_of_tenants==0)
			return -1;
		
		double cost = totalCosts()/number_of_tenants;
		return cost;
	}

	@Override
	public boolean isOccupied() {
		if(number_of_months>0)
			return true;
		
		return false;
	}

	@Override
	public boolean rent(int numberOfMonths) {
		if(isOccupied())
			return false;
		
		number_of_months=numberOfMonths;;
		return true;
	}


	@Override
	boolean moveIn(int tenants) {
		if(tenants>0 && tenants<8 && (area/tenants)>2)
		{
			System.out.println("The rent is succesfull");
			return true;
		}
		
		System.out.println("They can't move in Aparment");
		return false;
		
	}


	@Override
	public String toString() {
		return super.toString() + " Number Of Rented Months :" + this.number_of_months;
	}
	
	

}
