package com.bytesmyth.ui;

/**
 *  Positive values positions item inside container with padding
 *  Negative values positions item outside of container separated by padding
 * */
public interface RelativePositioning {

    HorizontalAlignment getHorizontalAlignment();

    float getHorizontalOffset();

    VerticalAlignment getVerticalAlignment();

    float getVerticalOffset();

}
