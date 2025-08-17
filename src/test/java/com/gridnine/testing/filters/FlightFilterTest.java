package com.gridnine.testing.filters;


import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FlightFilterTest {

    @Test
    public void testDepartureBeforeNowFilter() {
        LocalDateTime now = LocalDateTime.now();
        Segment pastSegment = new Segment(now.minusDays(1), now.plusHours(2));
        Flight pastFlight = new Flight(List.of(pastSegment));

        Segment futureSegment = new Segment(now.plusDays(1), now.plusDays(1).plusHours(2));
        Flight futureFlight = new Flight(List.of(futureSegment));

        List<Flight> filtered = new DepartureBeforeNowFilter().filter(List.of(pastFlight, futureFlight));

        assertEquals("Should filter out past flights", 1, filtered.size());
        assertTrue("Future flight should remain", filtered.contains(futureFlight));
    }
}