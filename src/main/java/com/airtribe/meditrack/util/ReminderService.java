package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Appointment;
import java.util.Timer;
import java.util.TimerTask;

public final class ReminderService {
    private final Timer timer = new Timer("MediTrack-Reminder", true);

    public void scheduleReminder(Appointment appointment, long delayMillis) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.printf("Reminder: Appointment %s for %s with %s is scheduled at %s.%n",
                        appointment.getId(), appointment.getPatient().getName(), appointment.getDoctor().getName(), appointment.getScheduledAt());
            }
        }, delayMillis);
    }

    public void stop() {
        timer.cancel();
    }
}
