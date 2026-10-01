package io.github.dogeiscut.a_little_more.content.goals;

import com.google.common.collect.Streams;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;

import javax.annotation.Nullable;
import java.util.List;

public class RideParentGoal extends Goal {
    private final Animal animal;
    private final boolean dismountWhenAttacked;
    private final int stackMax;
    @Nullable
    private Animal parent;

    public RideParentGoal(Animal animal, boolean dismountWhenAttacked, int stackMax) {
        this.animal = animal;
        this.dismountWhenAttacked = dismountWhenAttacked;
        this.stackMax = stackMax;
    }

    @Override
    public boolean canUse() {
        if (this.animal.getAge() >= 0) {
            return false;
        } else {
            List<? extends Animal> list = this.animal.level().getEntitiesOfClass(this.animal.getClass(), this.animal.getBoundingBox().inflate( 2.0d));
            @Nullable Animal animal = null;

            for(Animal iteratedAnimal : list) {
                if (iteratedAnimal.getAge() >= 0) {
                    animal = iteratedAnimal;
                    break;
                }
            }

            if (animal == null) {
                return false;
            } else {
                this.parent = animal;
                return true;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.animal.getAge() >= 0) {
            return false;
        } else return this.parent != null && this.parent.isAlive();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        if (this.parent != null) {
            if (this.animal.getVehicle() != null && this.animal.getVehicle().getType() == this.animal.getType()) {
                this.animal.stopRiding();
            }
        }
        this.parent = null;
    }

    @Override
    public void tick() {
        if (this.animal.isPassenger()) {
            return;
        }
        if (this.parent != null && this.parent.getPassengers().size() <= stackMax) {
            @Nullable Entity controller = this.parent.getControllingPassenger();
            this.animal.startRiding(controller == null ? this.parent : controller);
        }
    }
}
