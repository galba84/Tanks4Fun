package framework.Machines;

import framework.primitives.Position;

public abstract class GameObject {
    public Integer id;
    public Position position;
    public TypeOfObject typeOfObject;
    public Integer ownerId;
    public void updatePosition(){}
}
