///////////////////////////////////////////////////////////////////////////////
// Main Class File:    CovidTestLineGraph.java
// File:               CovidTestData.java
// Author:             Sydney Wong
// Email:              syd.wong888@gmail.com
///////////////////////////////////////////////////////////////////////////////
import javafx.application.Application;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("unchecked")
/**
 * Class that contains all the data, including the data that is not
 * plotted on the line chart.
 *
 * Bugs: n/a
 *
 * @author Sydney Wong
 */
public class CovidTestData {

    static ArrayList<TestDataPoint> data;
    static XYChart.Series series;

    // Constants
    private static final int DATA_LENGTH = 50;
    private static final String CSV_DELIMITER = ",";
    private static final int DAY_INDEX = 0;
    private static final int COUNT_INDEX = 1;
    private static final String EMPTY_STRING = "";

    /**
     * Constructor that fills series with date and count information
     *
     * @param filename The name of csv file with date and count information
     * @throws FileNotFoundException if invalid filename is given
     *
     */
    public CovidTestData(String filename) throws FileNotFoundException {

        if (filename == null || filename.equals(EMPTY_STRING)) {
            System.out.println("WARNING: Please input valid filename");
            return;
        }

        File file = new File(filename);

        Scanner countScanner = new Scanner(file);
        int numLines = 0;
        while (countScanner.hasNext()) {
            String line = countScanner.next();
            numLines++;
        }

        if (numLines != 50) {
            System.out.println("WARNING: Please input csv filename with 50 TestDataPoints");
            return;
        }

        Scanner inputScanner = new Scanner(file);
        series = new XYChart.Series();
        data = new ArrayList<TestDataPoint>();

        while (inputScanner.hasNext()) {
            String[] dayData = inputScanner.next().trim().split(CSV_DELIMITER);
            String day = dayData[DAY_INDEX];
            Integer count = Integer.parseInt(dayData[COUNT_INDEX]);
            TestDataPoint insert = new TestDataPoint(day, count);
            data.add(insert);
        }
    }

    /**
     * Getter for series
     *
     * @return series XYChart.Series that contains all the XYChart.DataPoint
     *
     */
    public XYChart.Series getSeries() {
        return series;
    }

    /**
     * Setter for series
     *
     * @param series XYChart.Series that contains all the XYChart.DataPoint
     *
     */
    public void setSeries(XYChart.Series series) {
        this.series = series;
    }

    /**
     * Getter for data
     *
     * @return data ArrayList that contains all TestDataPoint objects (even those
     *              not plotted on the line graph)
     *
     */
    public ArrayList<TestDataPoint> getData() {
        return data;
    }

    /**
     * Setter for data
     *
     * @param data ArrayList that contains all TestDataPoint objects (even those
     *              not plotted on the line graph)
     *
     */
    public void setData(ArrayList<TestDataPoint> data) {
        this.data = data;
    }

    /**
     * Fills the XYChart.Series with information from the ArrayList data so
     * that the line graph contains the correct data
     *
     */
    public void fillSeries() {
        for (int i = 0; i < DATA_LENGTH; i++) {
            series.getData().add(new XYChart.Data(data.get(i).getDate(),
                    data.get(i).getCount()));
        }
    }

    /**
     * Adds a TestDataPoint to the end of the ArrayList data
     *
     * @param dp The new TestDataPoint to be added
     *
     */
    public static void addDataPoint(TestDataPoint dp) {
        data.add(dp);
    }

}
