package com.example.myapplication

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
class ShakeDetector// Constructor that sets the shake listener
    (shakeListener:OnShakeListener):SensorEventListener {
    // Arrays to store gravity and linear acceleration values
    private val mGravity = floatArrayOf(0.0f, 0.0f, 0.0f)
    private val mLinearAcceleration = floatArrayOf(0.0f, 0.0f, 0.0f)
    // OnShakeListener that will be notified when the shake is detected
    private val mShakeListener:OnShakeListener
    // Start time for the shake detection
    internal var startTime:Long = 0
    // Counter for shake movements
    internal var moveCount = 0
    private// Start by setting the value to the x value
    // Check if the y value is greater
    // Check if the z value is greater
    // Return the greatest value
    val maxCurrentLinearAcceleration:Float
        get() {
            var maxLinearAcceleration = mLinearAcceleration[X]
            if (mLinearAcceleration[Y] > maxLinearAcceleration)
            {
                maxLinearAcceleration = mLinearAcceleration[Y]
            }
            if (mLinearAcceleration[Z] > maxLinearAcceleration)
            {
                maxLinearAcceleration = mLinearAcceleration[Z]
            }
            return maxLinearAcceleration
        }
    init{
        mShakeListener = shakeListener
    }
    override fun onSensorChanged(event:SensorEvent) {
        // This method will be called when the accelerometer detects a change.
        // Call a helper method that wraps code from the Android developer site
        setCurrentAcceleration(event)
        // Get the max linear acceleration in any direction
        val maxLinearAcceleration = maxCurrentLinearAcceleration
        // Check if the acceleration is greater than our minimum threshold
        if (maxLinearAcceleration > MIN_SHAKE_ACCELERATION)
        {
            val now = System.currentTimeMillis()
            // Set the startTime if it was reset to zero
            if (startTime == 0L)
            {
                startTime = now
            }
            val elapsedTime = now - startTime
            // Check if we're still in the shake window we defined
            if (elapsedTime > MAX_SHAKE_DURATION)
            {
                // Too much time has passed. Start over!
                resetShakeDetection()
            }
            else
            {
                // Keep track of all the movements
                moveCount++
                // Check if enough movements have been made to qualify as a shake
                if (moveCount > MIN_MOVEMENTS)
                {
                    // It's a shake! Notify the listener.
                    mShakeListener.onShake()
                    // Reset for the next one!
                    resetShakeDetection()
                }
            }
        }
    }
    override fun onAccuracyChanged(sensor:Sensor, accuracy:Int) {
        // Intentionally blank
    }
    private fun setCurrentAcceleration(event:SensorEvent) {
        /*
     * BEGIN SECTION from Android developer site. This code accounts for
     * gravity using a high-pass filter
     */
        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate
        val alpha = 0.8f
        // Gravity components of x, y, and z acceleration
        mGravity[X] = alpha * mGravity[X] + (1 - alpha) * event.values[X]
        mGravity[Y] = alpha * mGravity[Y] + (1 - alpha) * event.values[Y]
        mGravity[Z] = alpha * mGravity[Z] + (1 - alpha) * event.values[Z]
        // Linear acceleration along the x, y, and z axes (gravity effects removed)
        mLinearAcceleration[X] = event.values[X] - mGravity[X]
        mLinearAcceleration[Y] = event.values[Y] - mGravity[Y]
        mLinearAcceleration[Z] = event.values[Z] - mGravity[Z]
        /*
     * END SECTION from Android developer site
     */
    }
    private fun resetShakeDetection() {
        startTime = 0
        moveCount = 0
    }
    // (I'd normally put this definition in it's own .java file)
    interface OnShakeListener {
        fun onShake()
    }
    companion object {
        // Minimum acceleration needed to count as a shake movement
        private val MIN_SHAKE_ACCELERATION = 5
        // Minimum number of movements to register a shake
        private val MIN_MOVEMENTS = 2
        // Maximum time (in milliseconds) for the whole shake to occur
        private val MAX_SHAKE_DURATION = 500
        // Indexes for x, y, and z values
        private val X = 0
        private val Y = 1
        private val Z = 2
    }
}