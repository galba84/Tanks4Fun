package framework.WorldElements;

public class Cellar  {


    Cellar() {

    }


    public Cellar(Long l) {

            type = TypeOfSoil.getValue(l.intValue());
    }

    public TypeOfSoil type;

}
