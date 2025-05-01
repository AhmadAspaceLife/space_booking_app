package com.aspacelife.spaceBookingApp;


import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author AHMAD BUBA
 * Date:3/17/25
 * Time:12:39
 */

@SpringBootTest
class BookingTests {

//  private final Space spaceA = new Space(UUID.randomUUID().toString(),"spaceA", true);
//  private final Space spaceB = new Space(UUID.randomUUID().toString(),"spaceB", true);
//
//  @Test
//  void testBookingsDoNotOverlapIfDifferentSpaces() {
//    final Booking booking1 = new Booking("1", "user1", spaceA,
//                                         LocalDateTime.of(2024, 7, 20, 14, 0),
//                                         LocalDateTime.of(2024, 7, 20, 16, 0),
//                                         BookingStatus.CONFIRMED);
//
//    Booking booking2 = new Booking("2", "user2", spaceB, // Different space
//                                   LocalDateTime.of(2024, 7, 20, 15, 0),
//                                   LocalDateTime.of(2024, 7, 20, 17, 0),
//                                   BookingStatus.CONFIRMED);
//
//    assertFalse(booking1.overlapsWith(booking2));
//  }
//
//  @Test
//  void testBookingsOverlapWhenTimesIntersect() {
//    Booking booking1 = new Booking("1", "user1", spaceA,
//                                   LocalDateTime.of(2024, 7, 20, 14, 0),
//                                   LocalDateTime.of(2024, 7, 20, 16, 0),
//                                   BookingStatus.CONFIRMED);
//
//    Booking booking2 = new Booking("2", "user2", spaceA, // Same space
//                                   LocalDateTime.of(2024, 7, 20, 15, 0), // Overlaps with booking1
//                                   LocalDateTime.of(2024, 7, 20, 17, 0),
//                                   BookingStatus.CONFIRMED);
//
//    assertTrue(booking1.overlapsWith(booking2));
//  }
//
//  @Test
//  void testBookingsDoNotOverlapIfTimesAreAdjacent() {
//    final Booking booking1 = new Booking("1", "user1", spaceA,
//                                         LocalDateTime.of(2024, 7, 20, 14, 0),
//                                         LocalDateTime.of(2024, 7, 20, 16, 0),
//                                         BookingStatus.CONFIRMED);
//
//    final Booking booking2 = new Booking("2", "user2", spaceA, // Same space
//                                         LocalDateTime.of(2024, 7, 20, 16, 0), // Starts exactly when booking1 ends
//                                         LocalDateTime.of(2024, 7, 20, 18, 0),
//                                         BookingStatus.CONFIRMED);
//
//    assertFalse(booking1.overlapsWith(booking2));
//  }
//
//  @Test
//  void testBookingOverlapsWhenCompletelyContainedWithinAnother() {
//    final Booking booking1 = new Booking("1", "user1", spaceA,
//                                         LocalDateTime.of(2024, 7, 20, 14, 0),
//                                         LocalDateTime.of(2024, 7, 20, 18, 0),
//                                         BookingStatus.CONFIRMED);
//
//    final Booking booking2 = new Booking("2", "user2", spaceA, // Same space
//                                         LocalDateTime.of(2024, 7, 20, 15, 0), // Fully inside booking1
//                                         LocalDateTime.of(2024, 7, 20, 17, 0),
//                                         BookingStatus.CONFIRMED);
//
//    assertTrue(booking1.overlapsWith(booking2));
//  }
//
//  @Test
//  void testBookingOverlapsWhenSameStartOrEndTime() {
//    final Booking booking1 = new Booking("1", "user1", spaceA,
//                                         LocalDateTime.of(2024, 7, 20, 14, 0),
//                                         LocalDateTime.of(2024, 7, 20, 16, 0),
//                                         BookingStatus.CONFIRMED);
//
//    final Booking booking2 = new Booking("2", "user2", spaceA, // Same space
//                                         LocalDateTime.of(2024, 7, 20, 14, 0), // Starts at same time
//                                         LocalDateTime.of(2024, 7, 20, 15, 0),
//                                         BookingStatus.CONFIRMED);
//
//    final Booking booking3 = new Booking("3", "user3", spaceA, // Same space
//                                         LocalDateTime.of(2024, 7, 20, 16, 0), // Ends at same time
//                                         LocalDateTime.of(2024, 7, 20, 17, 0),
//                                         BookingStatus.CONFIRMED);
//
//    assertTrue(booking1.overlapsWith(booking2)); // Starts at same time, should overlap
//    assertFalse(booking1.overlapsWith(booking3)); // Starts when booking1 ends, should not overlap
//  }
}
