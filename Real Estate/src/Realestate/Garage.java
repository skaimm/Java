package Realestate;

import Common.Property;
import Common.Rentable;

public class Garage implements Property, Rentable {
	
	private double area;
	private int price_m2;
	private int number_of_rentedmonths;
	private boolean isit_heatable;
	private boolean isthere_Acar;
	

	public Garage(double area, int number_of_rentedmonths,int price_m2, boolean isit_heatable, boolean isthere_Acar) {
		super();
		this.area = area;
		this.price_m2 = price_m2;
		this.number_of_rentedmonths = number_of_rentedmonths;
		this.isit_heatable = isit_heatable;
		this.isthere_Acar = isthere_Acar;
	}
	
	public void useCar() {
		if(!isthere_Acar)
			isthere_Acar=true;
		
		isthere_Acar=false;
	}

	@Override
	public double costOfRent(int numberOfMonths) {
		
		if(isit_heatable)
			return (totalCosts()+ totalCosts()/2)*numberOfMonths;
		

		return totalCosts()*numberOfMonths;
	}

	@Override
	public boolean rent(int numberOfMonths) {
		if(isOccupied())
			return false;
		
		number_of_rentedmonths = numberOfMonths;
		return true;
	}
	
	@Override
	public boolean isOccupied() {
		if(number_of_rentedmonths>0 || isthere_Acar)
			return true;
		
		return false;
	}

	@Override
	public double totalCosts() {
		if(isit_heatable)
			return area*price_m2*3/2;
			
		return area*price_m2;
	}
	
	public String toString() {
		return this.getClass().getName() + " Area Of Garage :" + this.area 
				+ " Number Of Rented Months :" + this.number_of_rentedmonths
				+ " Price Of Garage (metersquare) :" + this.price_m2 
				+ " Is It Heatable :" + this.isthere_Acar 
				+ " Is There Car :" + this.isit_heatable;
	}

}
