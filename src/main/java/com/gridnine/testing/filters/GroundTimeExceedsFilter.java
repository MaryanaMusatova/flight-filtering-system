package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Фильтр для исключения перелётов с общим временем на земле более 2 часов
 */
class GroundTimeExceedsFilter implements FlightFilter {
    private static final long MAX_GROUND_TIME_HOURS = 2;

    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> {
                    List<Segment> segments = flight.getSegments();
                    if (segments.size() <= 1) {
                        return true; // Нет пересадок - нет времени на земле
                    }

                    long totalGroundTime = 0;
                    for (int i = 0; i < segments.size() - 1; i++) {
                        LocalDateTime arrival = segments.get(i).getArrivalDate();
                        LocalDateTime nextDeparture = segments.get(i + 1).getDepartureDate();
                        totalGroundTime += Duration.between(arrival, nextDeparture).toHours();

                        if (totalGroundTime > MAX_GROUND_TIME_HOURS) {
                            return false;
                        }
                    }
                    return true;
                })
                .toList();
    }
}