package boids;

import java.util.ArrayList;
import java.util.List;

import physics.Body;
import processing.core.PVector;

public class Eye<T extends Body> {

    private List<T> trackBodies;
    private List<T> farSight;
    private List<T> nearSight;
    private Boid me;
    public T target;

    public Eye(Boid me, List<? extends T> trackBodies) {
        this.me = me;
        this.trackBodies = new ArrayList<>(trackBodies);
        if (!this.trackBodies.isEmpty()) {
            target = this.trackBodies.get(0);
        }
    }

    public Eye(Boid me, Eye<T> other) {
        this(me, other.trackBodies);
        this.target = other.target;
    }

    public void look() {
        farSight = new ArrayList<>();
        nearSight = new ArrayList<>();
        for (T b : trackBodies) {
            if (farSight(b.getPos())) {
                farSight.add(b);
            }
            if (nearSight(b.getPos())) {
                nearSight.add(b);
            }
        }
    }

    public List<T> getFarSight() {
        return farSight;
    }

    public List<T> getNearSight() {
        return nearSight;
    }

    private boolean inSight(PVector t, float maxDistance, float maxAngle) {
        PVector r = PVector.sub(t, me.getPos());
        float d = r.mag();
        float angle = PVector.angleBetween(r, me.getVel());
        return d > 0 && d < maxDistance && angle < maxAngle;
    }

    private boolean farSight(PVector t) {
        return inSight(t, me.dna.visionDistance, me.dna.visionAngle);
    }

    private boolean nearSight(PVector t) {
        return inSight(t, me.dna.visionSafeDistance, (float) Math.PI);
    }
}
