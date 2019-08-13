package framework.EventBus;

import framework.Machines.GameObject;
import framework.Machines.Tank;

public class TankMoveAction extends Action implements ActionInterface {
    public int aceleration;

    public TankMoveAction(Integer acc) {
        aceleration = acc;
    }

    public GameObject execute(GameObject object) {
        Tank tank = (Tank) object;
        tank.acceleration +=this.aceleration;
        return tank;
    }
}
