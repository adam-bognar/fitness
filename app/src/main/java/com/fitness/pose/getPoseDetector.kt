package com.fitness.pose

import android.graphics.PointF
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import kotlin.math.abs

fun getPoseDetector(): PoseDetector {
    val options = PoseDetectorOptions.Builder()
        .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
        .build()

    return PoseDetection.getClient(options)
}

fun getAngle(first: PointF, mid: PointF, last: PointF): Double {
    val result = Math.toDegrees(
        kotlin.math.atan2((last.y - mid.y).toDouble(), (last.x - mid.x).toDouble()) -
        kotlin.math.atan2((first.y - mid.y).toDouble(), (first.x - mid.x).toDouble())
    )
    var angle = kotlin.math.abs(result)
    if (angle > 180) angle = 360.0 - angle
    return angle
}

fun isSquatting(pose: Pose): Boolean {
    val hip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)?.position
    val knee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)?.position
    val ankle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)?.position

    if (hip != null && knee != null && ankle != null) {
        val angle = getAngle(hip, knee, ankle)
        return angle < 100
    }
    return false
}

fun isDoingPushUp(pose: Pose): Boolean {
    val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)?.position
    val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)?.position
    val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)?.position

    return if (leftShoulder != null && leftElbow != null && leftWrist != null) {
        val angle = getAngle(leftShoulder, leftElbow, leftWrist)
        angle < 90
    } else false
}

fun isJumpingJackOpen(pose: Pose): Boolean {
    val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)?.position
    val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)?.position
    val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)?.position
    val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)?.position

    return if (leftWrist != null && rightWrist != null && leftAnkle != null && rightAnkle != null) {
        val handsUp = leftWrist.y < 400 && rightWrist.y < 400
        val feetApart = abs(leftAnkle.x - rightAnkle.x) > 200
        handsUp && feetApart
    } else false
}

fun isDoingPullUp(pose: Pose): Boolean {
    val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)?.position
    val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)?.position

    return if (leftWrist != null && leftShoulder != null) {
        leftWrist.y < leftShoulder.y
    } else false
}

fun isDoingBenchPress(pose: Pose): Boolean {
    val shoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)?.position
    val elbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)?.position
    val wrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)?.position

    return if (shoulder != null && elbow != null && wrist != null) {
        val angle = getAngle(shoulder, elbow, wrist)
        angle < 90
    } else false
}


fun isDoingBicepsCurl(pose: Pose): Boolean {
    val shoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)?.position
    val elbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)?.position
    val wrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)?.position

    return if (shoulder != null && elbow != null && wrist != null) {
        val angle = getAngle(shoulder, elbow, wrist)
        angle < 50
    } else false
}

fun isDoingLatPulldown(pose: Pose): Boolean {
    val wrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)?.position
    val shoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)?.position

    return if (wrist != null && shoulder != null) {
        wrist.y >= shoulder.y
    } else false
}

fun isDoingTricepsPushdown(pose: Pose): Boolean {
    val shoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)?.position
    val elbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)?.position
    val wrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)?.position

    return if (shoulder != null && elbow != null && wrist != null) {
        val angle = getAngle(shoulder, elbow, wrist)
        angle > 150
    } else false
}

fun isDoingShoulderPress(pose: Pose): Boolean {
    val wrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)?.position
    val shoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)?.position

    return if (wrist != null && shoulder != null) {
        wrist.y < shoulder.y
    } else false
}

fun isDoingLateralRaise(pose: Pose): Boolean {
    val wrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)?.position
    val shoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)?.position

    return if (wrist != null && shoulder != null) {
        abs(wrist.y - shoulder.y) < 100
    } else false
}

fun isDoingLegExtension(pose: Pose): Boolean {
    val hip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)?.position
    val knee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)?.position
    val ankle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)?.position

    return if (hip != null && knee != null && ankle != null) {
        val angle = getAngle(hip, knee, ankle)
        angle > 160
    } else false
}

