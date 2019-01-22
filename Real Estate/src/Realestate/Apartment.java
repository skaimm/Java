package Realestate;

import Common.Property;

public abstract class Apartment implements Property{
	protected double area; 
	protected int number_of_rooms;
	protected int number_of_tenants; 
	protected double price_for_m2;
	
	
	public Apartment(double area, int number_of_rooms, int number_of_tenants, double price_for_m2) {
		//super();
		this.area = area;
		this.number_of_rooms = number_of_rooms;
		if(moveIn(number_of_tenants))
			this.number_of_tenants = number_of_tenants;
		this.price_for_m2 = price_for_m2;
	}

	abstract boolean moveIn(int tenants);


	@Override
	public double totalCosts() {
		return this.area*this.price_for_m2;
	}
	
	protected int getNumberOfTenants(){
		return number_of_tenants;
	}
	public String toString() {
		return this.getClass().getName() + " Area Of Apartment : " + this.area 
				+ " Number Of Rooms :" + this.number_of_rooms
				+ " Number Of Tenants :" + this.number_of_tenants 
				+ " Price Of Apartment (metersquare):" + this.price_for_m2;
	}
	
	
}
