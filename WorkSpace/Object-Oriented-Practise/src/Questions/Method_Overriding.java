package Questions;


class Sports {
    String getName() {
        return "Generic Sports";
    }
  
    void getNumberOfTeamMembers() {
        System.out.println("Each team has n players in " + getName());
    }
}

class Soccer extends Sports {
    @Override
    String getName() {
        return "Soccer Class";
    }
    
    @Override
    void getNumberOfTeamMembers() {
        System.out.println("Each team has 11 players in " + getName());
    }
}

class Basketball extends Sports {
    @Override
    String getName() {
        return "Basket Class";
    }
    
    @Override
    void getNumberOfTeamMembers() {
        System.out.println("Each team has 12 players in " + getName());
    }
}
public class Method_Overriding {
    public static void main(String[] args) {
        Sports c1 = new Sports();
        Soccer c2 = new Soccer();
        Basketball c3 = new Basketball();
        System.out.println(c1.getName());
        c1.getNumberOfTeamMembers();
        System.out.println(c2.getName());
        c2.getNumberOfTeamMembers();
        System.out.println(c3.getName());
        c3.getNumberOfTeamMembers();
    }
}
