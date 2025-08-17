package com.gridnine.testing;

import com.gridnine.testing.filters.FlightFilter;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.util.FlightBuilder;
import org.junit.Test;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;

public class MainScriptTest {

    @Test
    public void testMainFilterScenario() {
        List<Flight> flights = FlightBuilder.createFlights();

        List<Flight> step1 = FlightFilter.departureBeforeNow().filter(flights);
        assertThat(step1, hasSize(5));

        List<Flight> step2 = FlightFilter.arrivalBeforeDeparture().filter(step1);
        assertThat(step2, hasSize(4));

        List<Flight> step3 = FlightFilter.groundTimeExceedsTwoHours().filter(step2);
        assertThat(step3, hasSize(2));

        for (Flight flight : step3) {
            assertTrue(
                    flight.getSegments().size() == 1 ||
                            calculateTotalGroundTime(flight) <= 2
            );
        }
    }

    private long calculateTotalGroundTime(Flight flight) {
        long total = 0;
        for (int i = 0; i < flight.getSegments().size() - 1; i++) {
            Duration groundTime = Duration.between(
                    flight.getSegments().get(i).getArrivalDate(),
                    flight.getSegments().get(i + 1).getDepartureDate()
            );
            total += groundTime.toHours();
        }
        return total;
    }
}