package com.app.mdc.utils.viewbean.pieecharts;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：zl
 * @ Date       ：2019/4/9 14:32
 * @ Description
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Option {
    private Legend legend;

    private List<Serie> series;

    public Option() {
        this.legend = new Legend();
        this.series = new ArrayList<>();
    }

    public Legend getLegend() {
        return legend;
    }

    public void setLegend(Legend legend) {
        this.legend = legend;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }
}
