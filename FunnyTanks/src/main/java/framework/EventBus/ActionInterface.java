package framework.EventBus;

import framework.Machines.GameObject;

public interface ActionInterface {
    GameObject execute(GameObject object);
}
