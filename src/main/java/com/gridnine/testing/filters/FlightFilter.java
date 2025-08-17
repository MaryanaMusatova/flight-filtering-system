package com.gridnine.testing.filters;


import com.gridnine.testing.model.Flight;

import java.util.List;

/**
 * Интерфейс для фильтрации перелётов
 */
public interface FlightFilter {
    List<Flight> filter(List<Flight> flights);

    static FlightFilter departureBeforeNow() {
        return new DepartureBeforeNowFilter();
    }

    static FlightFilter arrivalBeforeDeparture() {
        return new ArrivalBeforeDepartureFilter();
    }

    static FlightFilter groundTimeExceedsTwoHours() {
        return new GroundTimeExceedsFilter();
    }


}