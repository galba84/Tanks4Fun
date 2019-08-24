package framework.Machines;

public class TankSettings {
    public int maxSpeedKmPerHour;
    public int wheelRadiusCentimetre;
    public String name = "";
    public String description = "";

    public int lengthCentimetre;
    public int heightCentimetre;
    public int weightKg;
    public int horsePower;


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
        maxSpeedKmPerHour = 67;
        wheelRadiusCentimetre = 500;
        lengthCentimetre = 792;
        heightCentimetre = 240;
        weightKg = 63_000;
        horsePower = 1_500;
    }
}

class T84 extends TankSettings {
    T84() {
        maxSpeedKmPerHour = 65;
        wheelRadiusCentimetre = 600;
        lengthCentimetre = 792;
        heightCentimetre = 240;
        weightKg = 63_000;
        horsePower = 1_500;
    }
}

class Challenger2 extends TankSettings {
    Challenger2() {
        maxSpeedKmPerHour = 56;
        wheelRadiusCentimetre = 500;
        lengthCentimetre = 792;
        heightCentimetre = 240;
        weightKg = 63_000;
        horsePower = 1_500;
    }
}

class Type99 extends TankSettings {
    Type99() {
        maxSpeedKmPerHour = 80;
        wheelRadiusCentimetre = 500;
        lengthCentimetre = 792;
        heightCentimetre = 240;
        weightKg = 63_000;
        horsePower = 1_500;
    }

}