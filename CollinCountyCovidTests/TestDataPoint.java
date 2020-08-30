///////////////////////////////////////////////////////////////////////////////
// Main Class File:    CovidTestLineGraph.java
// File:               TestDataPoint.java
// Author:             Sydney Wong
// Email:              syd.wong888@gmail.com
///////////////////////////////////////////////////////////////////////////////

/**
 * This class defines a TestDataPoint. Every TestDataPoint contains a date and
 * the count for the number of cumulative tests taken on the date. The line
 * graph contains TestDataPoints.
 *
 * Bugs: n/a
 *
 * @author Sydney Wong
 */
public class TestDataPoint {

    String date;
    Integer count;

    /**
     * Constructor for TestDataPoint
     *
     * @param date String in the format of M/D
     * @param count Number of cumulative COVID tests administered on the date
     *
     */
    public TestDataPoint(String date, Integer count) {
        this.date = date;
        this.count = count;
    }

    /**
     * Getter for date
     *
     * @return date String in format of M/D
     *
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Getter for count
     *
     * @return count Number of cumulative COVID tests administered on the date
     *
     */
    public Integer getCount() {
        return this.count;
    }

}
