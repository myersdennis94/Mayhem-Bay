package myers.test.entities.ships;

public class DefaultShip extends Ship{

    public DefaultShip() {
        shapeHX = 8;
        shapeHY = 16;
        linearVelocity = 3;
        angularVelocity = 1.5f;
        restitution = 0.15f;
        density = 0;
        sprite = null;
    }
}
