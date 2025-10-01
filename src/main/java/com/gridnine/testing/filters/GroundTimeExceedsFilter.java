package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Фильтр для исключения перелётов с общим временем на земле более 2 часов
 */
public class GroundTimeExceedsFilter implements FlightFilter {
    private static final long MAX_GROUND_TIME_MINUTES = 120; // 2 часа в минутах

    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> {
                    List<Segment> segments = flight.getSegments();
                    if (segments.size() <= 1) {
                        return true;
                    }

                    long totalGroundTimeMinutes = 0;
                    for (int i = 0; i < segments.size() - 1; i++) {
                        LocalDateTime arrival = segments.get(i).getArrivalDate();
                        LocalDateTime nextDeparture = segments.get(i + 1).getDepartureDate();

                        totalGroundTimeMinutes += Duration.between(arrival, nextDeparture).toMinutes();

                        if (totalGroundTimeMinutes > MAX_GROUND_TIME_MINUTES) {
                            return false;
                        }
                    }
                    return true;
                })
                .toList();
    }
}