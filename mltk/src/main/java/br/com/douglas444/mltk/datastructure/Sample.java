package br.com.douglas444.mltk.datastructure;

import java.util.*;
import java.util.stream.IntStream;

public class Sample {

    private long t;
    private double[] x;
    private Integer y;
    private Integer clusterId;

    public Sample(int t, double[] x, Integer y) {
        this.t = t;
        this.x = x.clone();
        this.y = y;
    }

    public Sample(double[] x) {
        this.x = x.clone();
        this.y = y;
    }

    public Sample(double[] x, Integer y) {
        this.x = x.clone();
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sample sample = (Sample) o;
        return t == sample.t &&
                Arrays.equals(x, sample.x) &&
                Objects.equals(y, sample.y) &&
                Objects.equals(clusterId, sample.clusterId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(this.t, this.y);
        result = 31 * result + Arrays.hashCode(this.x);
        return result;
    }

    public double distance(final Sample sample) {
        double sum = 0;
        for (int i = 0; i < sample.getX().length; ++i) {
            sum += (sample.getX()[i] - this.getX()[i]) * (sample.getX()[i] - this.getX()[i]);
        }
        return Math.sqrt(sum);
    }

    public void sum(final Sample sample) {

        for (int i = 0; i < this.x.length; ++i) {
            this.x[i] += sample.getX()[i];
        }

    }

    public void divide(final double scalar) {

        for (int i = 0; i < this.x.length; ++i) {
            this.x[i] /= scalar;
        }

    }

    public void multiply(final double scalar) {

        for (int i = 0; i < this.x.length; ++i) {
            this.x[i] *= scalar;
        }

    }

    public Sample difference(final Sample sample) {

        final Sample difference = new Sample(this.getX().clone(), this.y);

        for (int i = 0; i < this.x.length; ++i) {
            difference.getX()[i] -= sample.getX()[i];
        }

        return difference;

    }

    public void pow(final int exp) {

        for (int i = 0; i < this.x.length; ++i) {
            this.x[i] = Math.pow(this.x[i], 2);
        }

    }

    public Sample copy() {

        Sample sample = new Sample(new double[this.x.length], this.y);
        for (int i = 0; i < this.x.length; ++i) {
            sample.getX()[i] = this.x[i];
        }
        return sample;

    }

    @Deprecated
    public Optional<Sample> getClosestSample(final List<Sample> samples) {
        return samples.stream().min(Comparator.comparing(this::distance));
    }

    public Sample calculateClosestSample(final List<Sample> samples) {

        if (samples.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return samples.stream().min(Comparator.comparing(this::distance)).orElseGet(() -> samples.get(0));

    }

    public int calculateClosestSample(final Sample[] samples) {

        if (samples.length == 0) {
            throw new IllegalArgumentException();
        }

        final OptionalInt minIndex = IntStream.range(0, samples.length)
                .reduce((i, j) -> samples[i].distance(this) > samples[j].distance(this) ? j : i);

        return minIndex.orElse(0);

    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getClusterId() {
        return clusterId;
    }

    public void setClusterId(Integer clusterId) {
        this.clusterId = clusterId;
    }
}
