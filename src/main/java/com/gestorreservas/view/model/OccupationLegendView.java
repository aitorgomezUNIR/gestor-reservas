package com.gestorreservas.view.model;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
@Builder
public class OccupationLegendView {
    private int totalResources;

    private int free;
    private int occupied;
    private int partiallyOccupied;

    private float getPercentage(int value) {
        return (float) (value * 100) / totalResources;
    }

    public String getFormattedPercentage(int value) {
        int percentage = (int) this.getPercentage(value);
        String percentageString = String.valueOf(percentage);
        return percentageString.concat("%");
    }
}
