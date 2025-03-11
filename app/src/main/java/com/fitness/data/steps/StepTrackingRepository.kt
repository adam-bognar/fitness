package com.fitness.data.steps

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StepTrackingRepository @Inject constructor(
    private val sensorManager: SensorManager,
    private val stepCounterSensor: Sensor?
) : IStepTrackingRepository, SensorEventListener {

    private val _stepCount = MutableStateFlow(0)
    override val stepCount: StateFlow<Int> get() = _stepCount

    private var initialStepsCount: Int? = null

    override fun startTracking() {
        stepCounterSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun stopTracking() {
        sensorManager.unregisterListener(this)
    }

    override fun resetSteps() {
        initialStepsCount = null
        _stepCount.value = 0
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            if (initialStepsCount == null) {
                initialStepsCount = event.values[0].toInt() // Set baseline
            }
            _stepCount.value = event.values[0].toInt() - initialStepsCount!!
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
