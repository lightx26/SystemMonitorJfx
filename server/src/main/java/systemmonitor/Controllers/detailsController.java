package systemmonitor.Controllers;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;
import systemmonitor.Utilities.DataAccess;

public class detailsController {
    @FXML
    private AreaChart<String, Number> memoryChart;
    @FXML
    private Text totalmemTxt;
    @FXML
    private Text inusememTxt;
    @FXML
    private AreaChart<String, Number> cpuChart;
    @FXML
    private Text utilizationTxt;
    @FXML
    private AreaChart<String, Number> ethernetChart;
    @FXML
    private TextField textField;
    private double memtimeIndex = 1;
    private double memTimestep = 0.5;
    private int memSample = 100; // <= 100

    private double cputimeIndex = 1;
    private double cpuTimestep = 0.5;
    private int cpuSample = 100; // <= 100

    public void initialize() throws IOException, InterruptedException {
        initializeMemChart();
        initializeCpuChart();
    }

    private void initializeMemChart() throws IOException, InterruptedException {
        memoryChart.setTitle("MEMORY");
        memoryChart.setLegendVisible(false);

        NumberAxis yAxis = (NumberAxis) memoryChart.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(16000); // TODO: Max memory of client's ram
        yAxis.setTickUnit(1000);

        XYChart.Series<String, Number> memDataSeries = new XYChart.Series<>();
        memDataSeries.setName("Memory usage (MB)");

        DataAccess da = new DataAccess();

        // // Add data points to the series
        memDataSeries.getData().clear();
        memoryChart.getData().clear();

        // ArrayList<Long> ar = da.getMemoryUsages();
        // for (Long mem : ar) {
        // memDataSeries.getData().add(new XYChart.Data<String,
        // Number>(Integer.toString(timeIndex++), mem));
        // }

        for (int i = 0; i < memSample - 1; i++) {
            memDataSeries.getData()
                    .add(new XYChart.Data<String, Number>(Double.toString(memtimeIndex += memTimestep), 0));
        }

        Long mem = da.getCurrentMemoryUsage();

        inusememTxt.setText(Long.toString(mem));
        memDataSeries.getData()
                .add(new XYChart.Data<String, Number>(Double.toString(memtimeIndex += memTimestep), mem));
        memoryChart.getData().add(memDataSeries);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(memTimestep), event -> updatememChartData()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updatememChartData() {
        // Update the chart data
        // XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> memDataSeries = memoryChart.getData().get(0);

        DataAccess da = new DataAccess();

        // dataSeries.getData().clear();
        // lineChart.getData().clear();

        Long mem = da.getCurrentMemoryUsage();

        inusememTxt.setText(Long.toString(mem));

        if (memDataSeries.getData().size() > memSample)
            memDataSeries.getData().remove(0);
        memDataSeries.getData()
                .add(new XYChart.Data<String, Number>(Double.toString(memtimeIndex += memTimestep), mem));
    }

    private void initializeCpuChart() throws IOException, InterruptedException {
        cpuChart.setTitle("CPU");
        cpuChart.setLegendVisible(false);

        NumberAxis yAxis = (NumberAxis) cpuChart.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);

        XYChart.Series<String, Number> cpuDataSeries = new XYChart.Series<>();
        cpuDataSeries.setName("CPU (%)");

        DataAccess da = new DataAccess();

        // Add data points to the series
        cpuDataSeries.getData().clear();
        cpuChart.getData().clear();

        for (int i = 0; i < cpuSample - 1; i++) {
            cpuDataSeries.getData()
                    .add(new XYChart.Data<String, Number>(Double.toString(cputimeIndex += cpuTimestep), 0));
        }

        Double cpu = da.getCurrentCpuUsage();

        utilizationTxt.setText(String.format("%.2f", cpu));
        cpuDataSeries.getData()
                .add(new XYChart.Data<String, Number>(Double.toString(cputimeIndex += cpuTimestep), cpu));
        cpuChart.getData().add(cpuDataSeries);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(cpuTimestep), event -> updatecpuChartData()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updatecpuChartData() {
        // Update the chart data
        XYChart.Series<String, Number> cpuDataSeries = cpuChart.getData().get(0);

        DataAccess da = new DataAccess();

        Double cpu = da.getCurrentCpuUsage();

        utilizationTxt.setText(String.format("%.2f", cpu));

        if (cpuDataSeries.getData().size() > cpuSample)
            cpuDataSeries.getData().remove(0);
        cpuDataSeries.getData()
                .add(new XYChart.Data<String, Number>(Double.toString(cputimeIndex += cpuTimestep), cpu));
    }
}
