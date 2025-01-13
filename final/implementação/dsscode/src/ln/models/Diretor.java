package ln.models;

public class Diretor extends User {
    private final String name;

    public Diretor(String email, String password, String name){
        super(email, password, UserType.DIRECTOR);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
