package Common;


public interface Rentable {
	
	public double costOfRent(int numberOfMonths); // double value of rental fee
	public boolean isOccupied(); // returns with a boolean value.
	public boolean rent(int numberOfMonths); //returns with a boolean value if the property can be rented, or not.

}
