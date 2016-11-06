package de.fh_kiel.functionalprogramming;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Simple representation of an animal that has to be fed.
 * @author Created by tom on 15.10.2016.
 */
public class Animal {

    private LocalDateTime lastFeedingTime;

    /**
     * Conctructs a new Animal with an initial time it was last fed.
     * @param lastFeedingTime the time the animal was last fed; use some time way in the past, if
     *                        unknown, since this parameter must not be {@code null}
     */
    public Animal(final LocalDateTime lastFeedingTime) {
        if (Objects.isNull(lastFeedingTime)) throw new IllegalArgumentException("last feeding time " +
                "must be given");
        this.lastFeedingTime = lastFeedingTime;
    }

    public void feed() {
        final LocalDateTime now = LocalDateTime.now();

        if ( lastFeedingTime.isBefore(now.minusHours(4L))) {
            // actual act of feeding, maybe a caretaker gets a text on his smartphone
            lastFeedingTime = now;
        }
    }

}
