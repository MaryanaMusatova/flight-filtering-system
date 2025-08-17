package com.gridnine.testing;

import com.gridnine.testing.filters.FlightFilter;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.util.FlightBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("Все перелёты:");
        flights.forEach(System.out::println);

        System.out.println("\n1. Исключены перелёты с вылетом до текущего момента времени:");
        FlightFilter departureBeforeNowFilter = FlightFilter.departureBeforeNow();
        departureBeforeNowFilter.filter(flights).forEach(System.out::println);

        System.out.println("\n2. Исключены перелёты с сегментами, где дата прилёта раньше даты вылета:");
        FlightFilter arrivalBeforeDepartureFilter = FlightFilter.arrivalBeforeDeparture();
        arrivalBeforeDepartureFilter.filter(flights).forEach(System.out::println);

        System.out.println("\n3. Исключены перелёты с общим временем на земле более 2 часов:");
        FlightFilter groundTimeExceedsFilter = FlightFilter.groundTimeExceedsTwoHours();
        groundTimeExceedsFilter.filter(flights).forEach(System.out::println);
    }
}