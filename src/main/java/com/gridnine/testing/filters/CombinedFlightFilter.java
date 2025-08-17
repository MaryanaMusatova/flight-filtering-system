package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;

import java.util.List;

/**
 * Комбинированный фильтр, применяющий несколько фильтров последовательно
 */
public class CombinedFlightFilter implements FlightFilter {
    private final FlightFilter[] filters;

    CombinedFlightFilter(FlightFilter... filters) {
        this.filters = filters;
    }

    @Override
    public List<Flight> filter(List<Flight> flights) {
        List<Flight> result = flights;
        for (FlightFilter filter : filters) {
            result = filter.filter(result);
        }
        return result;
    }
}