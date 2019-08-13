package framework.EventBus;

import framework.Machines.GameObject;

public abstract class Action {
   public abstract GameObject execute(GameObject object);
}
