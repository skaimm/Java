package Questions;
import java.util.*;


import java.util.*;
public class Iterator1{
   
   static Iterator func(ArrayList mylist){
      Iterator it=mylist.iterator();
      while(it.hasNext()){
         Object element = it.next();
         if (element.equals("###"))
            break;
      }
      return it;
      
   }
   
   public static void main(String []args){
      ArrayList list = new ArrayList();
      Scanner sc = new Scanner(System.in);
      int n = sc.nextInt();
      int m = sc.nextInt();
      for(int i = 0;i<n;i++){
         list.add(sc.nextInt());
      }
      
      list.add("###");
      
      for(int i=0;i<m;i++){
         list.add(sc.next());
      }
      
      Iterator it=func(list);
      while(it.hasNext()){
         Object element = it.next();
         System.out.println((String)element);
      }
   }
}