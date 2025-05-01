package com.aspacelife.spaceBookingApp.common.util;

import java.time.LocalDate;

/**
 * @author AHMAD BUBA
 * Date:4/30/25
 * Time:14:34
 */

public class DateUtil {
  private DateUtil(){}

  public static LocalDate parseDate(final String date) {
      return LocalDate.parse(date);
  }
}
