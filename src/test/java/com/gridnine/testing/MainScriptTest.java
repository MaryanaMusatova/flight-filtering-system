package com.gridnine.testing;

import com.gridnine.testing.filters.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.filters.DepartureBeforeNowFilter;
import com.gridnine.testing.filters.GroundTimeExceedsFilter;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.util.FlightBuilder;
import org.junit.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainScriptTest {

    @Test
    public void testMainFilterScenario() {
        List<Flight> flights = FlightBuilder.createFlights();

        List<Flight> filteredByDeparture = new DepartureBeforeNowFilter().filter(flights);
        assertEquals(5, filteredByDeparture.size());

        List<Flight> filteredByArrival = new ArrivalBeforeDepartureFilter().filter(filteredByDeparture);
        assertEquals(4, filteredByArrival.size());

        List<Flight> finalFiltered = new GroundTimeExceedsFilter().filter(filteredByArrival);
        assertEquals(2, finalFiltered.size());

        for (Flight flight : finalFiltered) {
            assertTrue(
                    flight.getSegments().size() == 1 ||
                            calculateGroundTime(flight) <= 2
            );
        }
    }

    private long calculateGroundTime(Flight flight) {
        long totalHours = 0;
        List<Segment> segments = flight.getSegments();

        for (int i = 0; i < segments.size() - 1; i++) {
            Duration duration = Duration.between(
                    segments.get(i).getArrivalDate(),
                    segments.get(i + 1).getDepartureDate()
            );
            totalHours += duration.toHours();
        }
        return totalHours;
    }
}