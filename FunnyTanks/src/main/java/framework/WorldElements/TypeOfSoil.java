package framework.WorldElements;

public enum TypeOfSoil {

    Snow(2), Ground(1), Air(0);
    private final int id;

    TypeOfSoil(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }
    public static TypeOfSoil getValue(int value) {
        for(TypeOfSoil e: TypeOfSoil.values()) {
            if(e.id == value) {
                return e;
            }
        }
        return null;// not found
    }
}

