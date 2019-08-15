package framework.Machines;

public class TankSettings {
    public int maxSpeed;
    public int wheelRadius;
    public String name = "";


    public static TankSettings getSettings(TankModels tm) {
        switch (tm) {
            case T84:
                return new T84();
            case Abrams:
                return new Abrams();
            case Challenger2:
                return new Challenger2();
            case Type99:
                return new Type99();
        }
        return new T84();
    }

}

class Abrams extends TankSettings {
    Abrams() {
        super();
        maxSpeed = 67;
        wheelRadius = 5;
    }
}

class T84 extends TankSettings {
    T84() {
        super();
        maxSpeed = 65;
        wheelRadius = 6;
    }
}

class Challenger2 extends TankSettings {
    Challenger2() {
        super();
        maxSpeed = 56;
        wheelRadius = 5;
    }
}

class Type99 extends TankSettings {
    Type99() {
        super();
        maxSpeed = 80;
        wheelRadius = 5;
    }

}