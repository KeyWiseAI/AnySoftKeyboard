package com.menny.android.anysoftkeyboard.BiAffect.bridge;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private double duration;
    private DateTime timestamp;
    private List<Keylogs> keylogs;
    private List<Accelerations> accelerations;

    public Session() {
        duration = 2.3;
        timestamp = DateTime.now();

        keylogs = new ArrayList<>();
        keylogs.add(new Keylogs());

        accelerations = new ArrayList<>();
        accelerations.add(new Accelerations());
    }

    private static class Keylogs {
        private DateTime timestamp;
        private String value;
        private double duration;
        private double distanceFromCenter;

        private Keylogs() {
            timestamp = DateTime.now();
            value = "test";
            duration = 0.83;
            distanceFromCenter = 3.2;
        }
    }

    private static class Accelerations {
        private double x;
        private double y;
        private double z;

        private Accelerations() {
            x = 0.33289;
            y = -0.9381;
            z = 0.319742;
        }
    }
}
