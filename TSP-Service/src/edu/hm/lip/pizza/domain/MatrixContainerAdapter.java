package edu.hm.lip.pizza.domain;

import edu.hm.lip.pizza.domain.google.GoogleDistanceElement;
import edu.hm.lip.pizza.domain.google.GoogleDistanceMatrix;
import edu.hm.lip.pizza.domain.google.GoogleDistanceRow;

public class MatrixContainerAdapter {

    public enum Measurements {
        DISTANCE, DURATION
    }

    private final MatrixContainer<Integer> convertedMatrix;

    public MatrixContainerAdapter(GoogleDistanceMatrix distanceMatrix,
            Measurements measurement) {
        convertedMatrix = convertGoogleDistanceMatrix(distanceMatrix,
                measurement);
    }

    private MatrixContainer<Integer> convertGoogleDistanceMatrix(
            GoogleDistanceMatrix distanceMatrix, Measurements measurement) {

        MatrixContainer<Integer> returnMatrix = new MatrixContainer<Integer>();

        int x = 0;
        for (GoogleDistanceRow row : distanceMatrix.getDistanceRows()) {
            int y = 0;
            for (GoogleDistanceElement element : row.getElements()) {

                Integer value = 0;
                if (measurement == Measurements.DISTANCE) {
                    value = Integer.parseInt(element.getDistance().getValue());
                } else if (measurement == Measurements.DURATION) {
                    value = Integer.parseInt(element.getDuration().getValue());
                }

                returnMatrix.set(x, y, value);
                y++;
            }
            x++;
        }

        return returnMatrix;
    }

    public MatrixContainer<Integer> getInstance() {
        return convertedMatrix;
    }

}
