package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;

import java.util.List;

/**
 * Фильтр для исключения перелётов с сегментами, где дата прилёта раньше даты вылета
 */
class ArrivalBeforeDepartureFilter implements FlightFilter {
    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate())))
                .toList();
    }
}