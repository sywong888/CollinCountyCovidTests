## Collin County, Texas COVID-19 Tests
Author: Sydney Wong
Purpose: The purpose of this project was to practice creating graphical user interfaces with JavaFX. Through this project I was able to create a line graph with time-relevant data.

This Java application plots the cumulative number of COVID-19 tests administered each day in a line graph.
The link to the data from the Texas Department of State Health Services is (https://dshs.texas.gov/coronavirus/additionaldata.aspx).

---

## CovidTestLineGraph.java

This file formats the line graph and application.
To initially generate the line graph, this file requires a comma-separated values file as the first argument.
Each line in the file must contain the date and the number of cumulative COVID tests administered on that date.
The format of the date must be a M/D, and the number of new cases must be a numerical value.
An example of a properly formatted csv file is provided (Collin_County_Test_Data.csv).

** To compile and run on Mac OS **

$ javac CovidTestLineGraph.java  
$ java CovidTestLineGraph Collin_County_Test_Data.csv  

** Addtional Features **

Users have the option to add a new date and its corresponding number of cumulative COVID tests to the line graph.
Using the prompts below the line graph, users can insert numerical values for the month, day, and count.
After the "submit" button is pressed, the graph updates.
The earliest TestDataPoint is removed and the newest DataPoint is added so that there remains only 50 TestDataPoints on the line graph.
Even though the line graph contains the most recent 50 TestDataPoints, ALL TestDataPoints are saved in the data ArrayList from the CovidTestData class.

In the case that a non-numerical value is submitted, a window with a warning message pops up. The message prompts users to input numerical values only. Once the warning message is exited, users may try to reenter a month, day and count.

---

## CovidTestData.java

This class fills the XYChart.Series with the information from the csv file.
The ArrayList is also filled with the information from the csv and continues to be updated as new TestDataPoints are placed on the line graph.
The ArrayList always holds all TestDataPoints, even those not plotted on the displayed line graph.

---

## TestDataPoint.java

This class defines a TestDataPoint to be placed on the line graph and added to the ArrayList of TestDataPoints defined in CovidTestData.java.
Each TestDataPoint object has date and count fields.

---

## Known Bugs ##

1. If a new TestDataPoint is added to the graph by the user, the updated graph's x-axis no longer increments by 5 days.
2. If a TestDataPoint with a replicate date is added by the user, the earliest TestDataPoint is still removed from the graph. Additionally, the replicate date is displayed on the graph as the most recent date (meaning one date is now out of order).

