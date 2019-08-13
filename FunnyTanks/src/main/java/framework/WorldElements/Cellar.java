package framework.WorldElements;

public class Cellar  {


    Cellar() {

    }


    public Cellar(Integer l) {

            type = TypeOfSoil.getValue(l);
    }

    public TypeOfSoil type;

}
