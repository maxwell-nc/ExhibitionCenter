/*
 * Copyright (C) 2014 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.camera;

import android.graphics.Point;
import android.hardware.Camera;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Utility methods for configuring the Android camera.
 *
 * @author Sean Owen
 */
public final class CameraConfigurationUtils {

    private static final String TAG = "CameraConfiguration";

    private static final int MIN_PREVIEW_PIXELS = 480 * 320; // normal screen
    private static final double MAX_ASPECT_DISTORTION = 0.15;

    private CameraConfigurationUtils() {
    }

    public static void setFocus(Camera.Parameters parameters,
                                boolean autoFocus, boolean disableContinuous, boolean safeMode) {
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        String focusMode = null;
        if (autoFocus) {
            if (safeMode || disableContinuous) {
                focusMode = findSettableValue("focus mode",
                        supportedFocusModes, Camera.Parameters.FOCUS_MODE_AUTO);
            } else {
                focusMode = findSettableValue("focus mode",
                        supportedFocusModes,
                        // Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE,
                        //低于4.0版本不支持，暂时注释掉
                        Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO,
                        Camera.Parameters.FOCUS_MODE_AUTO);
            }
        }
        // Maybe selected auto-focus but not available, so fall through here:
        if (!safeMode && focusMode == null) {
            focusMode = findSettableValue("focus mode", supportedFocusModes,
                    Camera.Parameters.FOCUS_MODE_MACRO,
                    Camera.Parameters.FOCUS_MODE_EDOF);
        }
        if (focusMode != null) {
            if (focusMode.equals(parameters.getFocusMode())) {
                Log.i(TAG, "Focus mode already set to " + focusMode);
            } else {
                parameters.setFocusMode(focusMode);
            }
        }
    }


    public static Point findBestPreviewSizeValue(Camera.Parameters parameters,
                                                 Point screenResolution) {

        List<Camera.Size> rawSupportedSizes = parameters
                .getSupportedPreviewSizes();
        if (rawSupportedSizes == null) {
            Log.w(TAG,
                    "Device returned no supported preview sizes; using default");
            Camera.Size defaultSize = parameters.getPreviewSize();
            if (defaultSize == null) {
                throw new IllegalStateException(
                        "Parameters contained no preview size!");
            }
            return new Point(defaultSize.width, defaultSize.height);
        }

        // Sort by size, descending
        List<Camera.Size> supportedPreviewSizes = new ArrayList<Camera.Size>(
                rawSupportedSizes);
        Collections.sort(supportedPreviewSizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size a, Camera.Size b) {
                int aPixels = a.height * a.width;
                int bPixels = b.height * b.width;
                if (bPixels < aPixels) {
                    return -1;
                }
                if (bPixels > aPixels) {
                    return 1;
                }
                return 0;
            }
        });

        if (Log.isLoggable(TAG, Log.INFO)) {
            StringBuilder previewSizesString = new StringBuilder();
            for (Camera.Size supportedPreviewSize : supportedPreviewSizes) {
                previewSizesString.append(supportedPreviewSize.width)
                        .append('x').append(supportedPreviewSize.height)
                        .append(' ');
            }
            Log.i(TAG, "Supported preview sizes: " + previewSizesString);
        }

        double screenAspectRatio = (double) screenResolution.x
                / (double) screenResolution.y;

        // Remove sizes that are unsuitable
        Iterator<Camera.Size> it = supportedPreviewSizes.iterator();
        while (it.hasNext()) {
            Camera.Size supportedPreviewSize = it.next();
            int realWidth = supportedPreviewSize.width;
            int realHeight = supportedPreviewSize.height;
            if (realWidth * realHeight < MIN_PREVIEW_PIXELS) {
                it.remove();
                continue;
            }

            boolean isCandidatePortrait = realWidth < realHeight;
            int maybeFlippedWidth = isCandidatePortrait ? realHeight
                    : realWidth;
            int maybeFlippedHeight = isCandidatePortrait ? realWidth
                    : realHeight;
            double aspectRatio = (double) maybeFlippedWidth
                    / (double) maybeFlippedHeight;
            double distortion = Math.abs(aspectRatio - screenAspectRatio);
            if (distortion > MAX_ASPECT_DISTORTION) {
                it.remove();
                continue;
            }

            if (maybeFlippedWidth == screenResolution.x
                    && maybeFlippedHeight == screenResolution.y) {
                Point exactPoint = new Point(realWidth, realHeight);
                Log.i(TAG, "Found preview size exactly matching screen size: "
                        + exactPoint);
                return exactPoint;
            }
        }

        // If no exact match, use largest preview size. This was not a great
        // idea on older devices because
        // of the additional computation needed. We're likely to get here on
        // newer Android 4+ devices, where
        // the CPU is much more powerful.
        if (!supportedPreviewSizes.isEmpty()) {
            Camera.Size largestPreview = supportedPreviewSizes.get(0);
            Point largestSize = new Point(largestPreview.width,
                    largestPreview.height);
            Log.i(TAG, "Using largest suitable preview size: " + largestSize);
            return largestSize;
        }

        // If there is nothing at all suitable, return current preview size
        Camera.Size defaultPreview = parameters.getPreviewSize();
        if (defaultPreview == null) {
            throw new IllegalStateException(
                    "Parameters contained no preview size!");
        }
        Point defaultSize = new Point(defaultPreview.width,
                defaultPreview.height);
        Log.i(TAG, "No suitable preview sizes, using default: " + defaultSize);
        return defaultSize;
    }

    private static String findSettableValue(String name,
                                            Collection<String> supportedValues, String... desiredValues) {
        Log.i(TAG,
                "Requesting " + name + " value from among: "
                        + Arrays.toString(desiredValues));
        Log.i(TAG, "Supported " + name + " values: " + supportedValues);
        if (supportedValues != null) {
            for (String desiredValue : desiredValues) {
                if (supportedValues.contains(desiredValue)) {
                    Log.i(TAG, "Can set " + name + " to: " + desiredValue);
                    return desiredValue;
                }
            }
        }
        Log.i(TAG, "No supported values match");
        return null;
    }


}
