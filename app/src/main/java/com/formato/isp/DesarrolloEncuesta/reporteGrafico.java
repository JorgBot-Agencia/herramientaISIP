package com.formato.isp.DesarrolloEncuesta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Paint;
import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.charts.Radar;
import com.anychart.core.radar.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Align;
import com.anychart.enums.MarkerType;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import com.formato.isp.R;
import com.formato.isp.utils.Tools;

public class reporteGrafico extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_grafico);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Radar radar = AnyChart.radar();

        radar.title("ESTADISTICA FINAL DE COMPONENTES");

        radar.yScale().minimum(0d);
        radar.yScale().minimumGap(0d);
        radar.yScale().ticks().interval(50d);

        radar.xAxis().labels().padding(5d, 5d, 5d, 5d);

        radar.legend()
                .align(Align.CENTER)
                .enabled(true);

        List<DataEntry> data = new ArrayList<>();
        data.add(new CustomDataEntry("Área1", 136, 199, 43, 54));
        data.add(new CustomDataEntry("Área2", 79, 125, 56, 65));
        data.add(new CustomDataEntry("Área3", 149, 173, 101, 122));
        data.add(new CustomDataEntry("Área4", 135, 33, 202, 222));
        data.add(new CustomDataEntry("Área5", 158, 64, 196, 212));

        Set set = Set.instantiate();
        set.data(data);
        Mapping shamanData = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping warriorData = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping priestData = set.mapAs("{ x: 'x', value: 'value3' }");
        Mapping gestionData = set.mapAs("{ x: 'x', value: 'value4' }");

        Line shamanLine = radar.line(shamanData);
        shamanLine.name("Capacitación");
        shamanLine.markers()
                .enabled(true)
                .type(MarkerType.CIRCLE)
                .size(3d);

        Line warriorLine = radar.line(warriorData);
        warriorLine.name("Acceso a capital");
        warriorLine.markers()
                .enabled(true)
                .type(MarkerType.CIRCLE)
                .size(3d);

        Line priestLine = radar.line(priestData);
        priestLine.name("Construcción de marca");
        priestLine.markers()
                .enabled(true)
                .type(MarkerType.CIRCLE)
                .size(3d);

        Line gestionLine = radar.line(gestionData);
        gestionLine.name("Gestión de nuevos mercados");
        gestionLine.markers()
                .enabled(true)
                .type(MarkerType.CIRCLE)
                .size(3d);
        radar.tooltip().format("Value: {%Value}");

        anyChartView.setChart(radar);
    }

    private class CustomDataEntry extends ValueDataEntry {
        public CustomDataEntry(String x, Number value, Number value2, Number value3, Number value4) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
            setValue("value4", value4);
        }
    }
}
