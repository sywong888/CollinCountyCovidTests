///////////////////////////////////////////////////////////////////////////////
// Title:              CovidTestLineGraph.java
// Files:              CovidTestData.java, TestDataPoint.java
// Author:             Sydney Wong
// Email:              syd.wong888@gmail.com
///////////////////////////////////////////////////////////////////////////////
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.text.Font;
import java.io.FileNotFoundException;

@SuppressWarnings("unchecked")
/**
 * Class that generates the line graph with the data from Collin County, Texas
 *
 * Bugs: When a new TestDataPoint is added to the graph, the x-axis is no longer
 *       incremented by 5 days. If a duplicate date is added to the graph, the
 *       earliest DataPoint is still removed.
 *
 * @author Sydney Wong
 */
public class CovidTestLineGraph extends Application {

    private Stage primaryStage;
    static XYChart.Series series;
    private final TextField monthText = new TextField();
    private final TextField dayText = new TextField();
    private final TextField countText = new TextField();
    private final Button submitBtn = new Button("Submit");

    //Graph Measurement Constants
    private static final int YAXIS_MIN = 50000;
    private static final int YAXIS_MAX = 140000;
    private static final int YAXIS_INTERVAL = 10000;
    private static final int TEXTFIELD_WIDTH = 50;
    private static final int XAXIS_INTERVAL = 5;
    private static final int GRAPH_WIDTH = 900;
    private static final int SCENE_WIDTH = 900;
    private static final int SCENE_HEIGHT = 560;

    //Label Constants
    private static final String XAXIS_LABEL = "Date";
    private static final String YAXIS_LABEL = "Number of Tests";
    private static final String GRAPH_TITLE = "Cumulative COVID-19 Tests in Collin County, Texas";
    private static final String STAGE_TITLE = "Collin County COVID-19 Tests Data";
    private static final String DATE_SEPARATOR = "/";
    private static final String EMPTY_STRING = "";

    //Button Constants
    private static final String ADD_DATAPOINT = "Add information for most recent day:";
    private static final String MONTH_PROMPT = "Month (i.e. 9 for September): ";
    private static final String DAY_PROMPT = "Day: ";
    private static final String COUNT_PROMPT = "Number of Cumulative Tests: ";
    private static final String WARNING_TITLE = "WARNING";
    private static final String WARNING_MESSAGE = "Please enter numerical values only";
    private static final String INVALID_FILENAME = "Please input valid filename";
    private static final String OK_BUTTON = "OK";
    private static final String BUTTON_COLOR = "-fx-background-color: #71df3e; ";

    // Fonts
    Font sansSerif12 = Font.font("sans-serif", 12);
    Font sansSerif18 = Font.font("sans-serif", 18);
    Font sansSerif15 = Font.font("sans-serif", 15);

    /**
     * Method that formats the line graph and Stage
     *
     * @param primaryStage Stage that contains the line graph and other elements
     *
     */
    public void start(Stage primaryStage) {

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(XAXIS_LABEL);
        xAxis.setTickLabelFont(sansSerif12);

        NumberAxis yAxis = new NumberAxis(YAXIS_LABEL, YAXIS_MIN, YAXIS_MAX,
                YAXIS_INTERVAL);
        yAxis.setTickLabelFont(sansSerif12);

        LineChart linechart = new LineChart(xAxis, yAxis);
        linechart.setTitle(GRAPH_TITLE);
        linechart.setPrefWidth(GRAPH_WIDTH);
        linechart.getData().add(series);
        linechart.setLegendVisible(false);

        //vbox contains every element in the scene
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        Label recentLabel = new Label(ADD_DATAPOINT);
        recentLabel.setFont(sansSerif18);

        //user input for month
        Label monthLabel = new Label(MONTH_PROMPT);
        monthLabel.setFont(sansSerif15);
        monthText.setPrefWidth(TEXTFIELD_WIDTH);

        //user input for day
        Label dayLabel = new Label(DAY_PROMPT);
        dayLabel.setFont(sansSerif15);
        dayText.setPrefWidth(TEXTFIELD_WIDTH);

        //user input for number of new COVID cases
        Label countLabel = new Label(COUNT_PROMPT);
        countLabel.setFont(sansSerif15);
        countText.setPrefWidth(TEXTFIELD_WIDTH);

        //button to add new DataPoint
        submitBtn.setAlignment(Pos.CENTER);
        submitBtn.setStyle(BUTTON_COLOR);
        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ev) {
                Integer count;
                int month;
                int day;

                try {
                    month = Integer.parseInt(monthText.getText().trim());
                    day = Integer.parseInt(dayText.getText().trim());
                    count = Integer.valueOf(countText.getText().trim());
                } catch (NumberFormatException e) {
                    showWarning(WARNING_TITLE, WARNING_MESSAGE);
                    return;
                }

                String date = month + DATE_SEPARATOR + day;
                series.getData().remove(0, 1);
                series.getData().add(new XYChart.Data(date, count));
                CovidTestData.addDataPoint(new TestDataPoint(date, count));
            }
        });

        //aligns user input prompts
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(12);
        grid.add(monthLabel, 0, 0);
        grid.add(monthText, 1, 0);
        grid.add(dayLabel, 0, 1);
        grid.add(dayText, 1, 1);
        grid.add(countLabel, 0, 2);
        grid.add(countText, 1, 2);

        vbox.getChildren().addAll(linechart, recentLabel, grid, submitBtn);

        primaryStage.setTitle(STAGE_TITLE);
        Scene scene = new Scene(new Group(vbox), SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

        ObservableList<Axis.TickMark<String>> list = xAxis.getTickMarks();
        // label every 5rd tick on x-axis
        for (int i = 0; i < list.size(); i++) {
            if (i % XAXIS_INTERVAL != 0) {
                list.get(i).setTextVisible(false);
            }
        }
    }

    /**
     * Main method that launches the line graph
     *
     * @throws FileNotFoundException if filename is invalid
     *
     */
    public static void main (String[] args) throws FileNotFoundException {
        String filename = args[0];
        if (filename == null || filename.equals(EMPTY_STRING)) {
            System.out.println(INVALID_FILENAME);
            return;
        }

        CovidTestData fillData = new CovidTestData(filename);
        fillData.fillSeries();
        series = fillData.getSeries();
        launch(args);
    }

    /**
     * Formats the window with warning message in case user does not input
     * numerical values when adding new DataPoint
     *
     * @param title The title for the window
     * @param message The warning message to be displayed
     *
     */
    public void showWarning(final String title, final String message) {
        final Stage dialog = new Stage(StageStyle.UTILITY);
        dialog.setTitle(title);

        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);

        Button btn = new Button(OK_BUTTON);
        btn.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    dialog.close();
                }
        });

        pane.getChildren().addAll(new Label(message), btn);
        dialog.setScene(new Scene(pane));
        dialog.showAndWait();
    }
}
