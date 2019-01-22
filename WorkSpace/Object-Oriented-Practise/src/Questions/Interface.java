package Questions;

import java.util.Scanner;

interface AdvancedArithmetic {
    int divisor_sum(int n);
}

class MyCalculator implements AdvancedArithmetic {
	
    public int divisor_sum(int n) {
    	if( n>1000) return -1;
    	
    	int sum = 0;
    	for(int i = 1 ; i <= n ; i++)
    	{
    		if (n % i == 0)
    			sum += i; 
    	}
        return sum;
    }
}

class Interface {
    public static void main(String[] args) {
        MyCalculator my_calculator = new MyCalculator();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.print("I implemented: " + my_calculator.getClass().getName() + "\n");
        System.out.print(my_calculator.divisor_sum(n) + "\n");
        sc.close();
    }
}
