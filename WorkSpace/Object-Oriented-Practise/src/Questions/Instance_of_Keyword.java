package Questions;

import java.util.ArrayList;
import java.util.Scanner;

class Student{}
class Rockstar{}
class Hacker{}

public class Instance_of_Keyword{
   
   static String count(ArrayList list){
      int s = 0,r = 0,h = 0;
      for(int i = 0; i < list.size(); i++){
         Object element=list.get(i);
         if(element instanceof Student)
            s++;
         if(element instanceof Rockstar)
            r++;
         if(element instanceof Hacker)
            h++;
      }
      String ret = Integer.toString(s)+" "+ Integer.toString(r)+" "+ Integer.toString(h);
      return ret;
   }

   public static void main (String []args){
      ArrayList list = new ArrayList();
      Scanner sc = new Scanner(System.in);
      int t = sc.nextInt();
      for(int i=0; i<t; i++){
         String s=sc.next();
         if(s.equals("Student"))list.add(new Student());
         if(s.equals("Rockstar"))list.add(new Rockstar());
         if(s.equals("Hacker"))list.add(new Hacker());
      }
      System.out.println(count(list));
   }
}