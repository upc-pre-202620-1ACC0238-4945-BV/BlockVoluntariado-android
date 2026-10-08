package com.example.blockvoluntariado.core.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
val location_on: ImageVector
    get() {
        if (_location_on != null) {
            return _location_on!!
        }
        _location_on =
            ImageVector.Builder(
                name = "location_on",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.Companion.NonZero,
                    ) {
                        moveTo(13.41f, 11.41f)
                        quadTo(14f, 10.83f, 14f, 10f)
                        quadTo(14f, 9.17f, 13.41f, 8.59f)
                        reflectiveQuadTo(12f, 8f)
                        reflectiveQuadTo(10.59f, 8.59f)
                        reflectiveQuadTo(10f, 10f)
                        reflectiveQuadToRelative(0.59f, 1.41f)
                        reflectiveQuadTo(12f, 12f)
                        reflectiveQuadToRelative(1.41f, -0.59f)
                        close()
                        moveTo(12f, 19.35f)
                        quadToRelative(3.05f, -2.8f, 4.53f, -5.09f)
                        quadTo(18f, 11.98f, 18f, 10.2f)
                        quadTo(18f, 7.47f, 16.26f, 5.74f)
                        quadTo(14.53f, 4f, 12f, 4f)
                        reflectiveQuadTo(7.74f, 5.74f)
                        quadTo(6f, 7.47f, 6f, 10.2f)
                        quadToRelative(0f, 1.78f, 1.48f, 4.06f)
                        reflectiveQuadTo(12f, 19.35f)
                        close()
                        moveTo(12f, 22f)
                        quadTo(7.98f, 18.58f, 5.99f, 15.64f)
                        reflectiveQuadTo(4f, 10.2f)
                        quadTo(4f, 6.45f, 6.41f, 4.22f)
                        reflectiveQuadTo(12f, 2f)
                        reflectiveQuadToRelative(5.59f, 2.22f)
                        reflectiveQuadTo(20f, 10.2f)
                        quadToRelative(0f, 2.5f, -1.99f, 5.44f)
                        quadTo(16.03f, 18.58f, 12f, 22f)
                        close()
                        moveTo(12f, 10f)
                        close()
                    }
                }
                .build()
        return _location_on!!
    }

private var _location_on: ImageVector? = null


@Suppress("CheckReturnValue")
val west: ImageVector
    get() {
        if (_west != null) {
            return _west!!
        }
        _west =
            ImageVector.Builder(
                name = "west",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.Companion.NonZero,
                    ) {
                        moveTo(9f, 19f)
                        lineTo(2f, 12f)
                        lineTo(9f, 5f)
                        lineToRelative(1.4f, 1.4f)
                        lineTo(5.83f, 11f)
                        horizontalLineTo(22f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(5.83f)
                        lineToRelative(4.6f, 4.6f)
                        lineTo(9f, 19f)
                        close()
                    }
                }
                .build()
        return _west!!
    }

private var _west: ImageVector? = null


@Suppress("CheckReturnValue")
val calendar_today: ImageVector
    get() {
        if (_calendar_today != null) {
            return _calendar_today!!
        }
        _calendar_today =
            ImageVector.Builder(
                name = "calendar_today",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.Companion.NonZero,
                    ) {
                        moveTo(5f, 22f)
                        quadTo(4.18f, 22f, 3.59f, 21.41f)
                        reflectiveQuadTo(3f, 20f)
                        verticalLineTo(6f)
                        quadTo(3f, 5.18f, 3.59f, 4.59f)
                        reflectiveQuadTo(5f, 4f)
                        horizontalLineTo(6f)
                        verticalLineTo(2f)
                        horizontalLineTo(8f)
                        verticalLineTo(4f)
                        horizontalLineToRelative(8f)
                        verticalLineTo(2f)
                        horizontalLineToRelative(2f)
                        verticalLineTo(4f)
                        horizontalLineToRelative(1f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        quadTo(21f, 5.18f, 21f, 6f)
                        verticalLineTo(20f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(19f, 22f)
                        horizontalLineTo(5f)
                        close()
                        moveTo(5f, 20f)
                        horizontalLineTo(19f)
                        verticalLineTo(10f)
                        horizontalLineTo(5f)
                        verticalLineTo(20f)
                        close()
                        moveTo(5f, 8f)
                        horizontalLineTo(19f)
                        verticalLineTo(6f)
                        horizontalLineTo(5f)
                        verticalLineTo(8f)
                        close()
                        moveTo(5f, 8f)
                        verticalLineTo(6f)
                        verticalLineTo(8f)
                        close()
                    }
                }
                .build()
        return _calendar_today!!
    }

private var _calendar_today: ImageVector? = null



@Suppress("CheckReturnValue")
val share: ImageVector
    get() {
        if (_share != null) {
            return _share!!
        }
        _share =
            ImageVector.Builder(
                name = "share",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.Companion.NonZero,
                    ) {
                        moveTo(17f, 22f)
                        quadToRelative(-1.25f, 0f, -2.13f, -0.88f)
                        reflectiveQuadTo(14f, 19f)
                        quadToRelative(0f, -0.15f, 0.08f, -0.7f)
                        lineTo(7.05f, 14.2f)
                        quadToRelative(-0.4f, 0.38f, -0.93f, 0.59f)
                        reflectiveQuadTo(5f, 15f)
                        quadTo(3.75f, 15f, 2.88f, 14.13f)
                        reflectiveQuadTo(2f, 12f)
                        reflectiveQuadTo(2.88f, 9.88f)
                        reflectiveQuadTo(5f, 9f)
                        quadTo(5.6f, 9f, 6.13f, 9.21f)
                        reflectiveQuadTo(7.05f, 9.8f)
                        lineTo(14.08f, 5.7f)
                        quadTo(14.03f, 5.52f, 14.01f, 5.36f)
                        reflectiveQuadTo(14f, 5f)
                        quadTo(14f, 3.75f, 14.88f, 2.88f)
                        reflectiveQuadTo(17f, 2f)
                        reflectiveQuadToRelative(2.13f, 0.88f)
                        reflectiveQuadTo(20f, 5f)
                        reflectiveQuadTo(19.13f, 7.13f)
                        reflectiveQuadTo(17f, 8f)
                        quadTo(16.4f, 8f, 15.88f, 7.79f)
                        reflectiveQuadTo(14.95f, 7.2f)
                        lineTo(7.93f, 11.3f)
                        quadToRelative(0.05f, 0.18f, 0.06f, 0.34f)
                        reflectiveQuadTo(8f, 12f)
                        reflectiveQuadTo(7.99f, 12.36f)
                        reflectiveQuadTo(7.93f, 12.7f)
                        lineToRelative(7.03f, 4.1f)
                        quadToRelative(0.4f, -0.38f, 0.92f, -0.59f)
                        reflectiveQuadTo(17f, 16f)
                        quadToRelative(1.25f, 0f, 2.13f, 0.88f)
                        reflectiveQuadTo(20f, 19f)
                        reflectiveQuadToRelative(-0.88f, 2.13f)
                        reflectiveQuadTo(17f, 22f)
                        close()
                        moveToRelative(0f, -2f)
                        quadToRelative(0.43f, 0f, 0.71f, -0.29f)
                        quadTo(18f, 19.43f, 18f, 19f)
                        reflectiveQuadTo(17.71f, 18.29f)
                        reflectiveQuadTo(17f, 18f)
                        reflectiveQuadToRelative(-0.71f, 0.29f)
                        reflectiveQuadTo(16f, 19f)
                        reflectiveQuadToRelative(0.29f, 0.71f)
                        reflectiveQuadTo(17f, 20f)
                        close()
                        moveTo(5f, 13f)
                        quadToRelative(0.43f, 0f, 0.71f, -0.29f)
                        quadTo(6f, 12.43f, 6f, 12f)
                        reflectiveQuadTo(5.71f, 11.29f)
                        reflectiveQuadTo(5f, 11f)
                        quadTo(4.58f, 11f, 4.29f, 11.29f)
                        reflectiveQuadTo(4f, 12f)
                        reflectiveQuadToRelative(0.29f, 0.71f)
                        reflectiveQuadTo(5f, 13f)
                        close()
                        moveTo(17.71f, 5.71f)
                        quadTo(18f, 5.43f, 18f, 5f)
                        reflectiveQuadTo(17.71f, 4.29f)
                        reflectiveQuadTo(17f, 4f)
                        reflectiveQuadTo(16.29f, 4.29f)
                        reflectiveQuadTo(16f, 5f)
                        reflectiveQuadToRelative(0.29f, 0.71f)
                        reflectiveQuadTo(17f, 6f)
                        reflectiveQuadTo(17.71f, 5.71f)
                        close()
                        moveTo(17f, 19f)
                        close()
                        moveTo(5f, 12f)
                        close()
                        moveTo(17f, 5f)
                        close()
                    }
                }
                .build()
        return _share!!
    }

private var _share: ImageVector? = null




@Suppress("CheckReturnValue")
val person: ImageVector
    get() {
        if (_person != null) {
            return _person!!
        }
        _person =
            ImageVector.Builder(
                name = "person",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.Companion.NonZero,
                    ) {
                        moveTo(9.18f, 10.83f)
                        quadTo(8f, 9.65f, 8f, 8f)
                        reflectiveQuadTo(9.18f, 5.18f)
                        reflectiveQuadTo(12f, 4f)
                        reflectiveQuadToRelative(2.83f, 1.18f)
                        reflectiveQuadTo(16f, 8f)
                        reflectiveQuadToRelative(-1.17f, 2.82f)
                        reflectiveQuadTo(12f, 12f)
                        reflectiveQuadTo(9.18f, 10.83f)
                        close()
                        moveTo(4f, 20f)
                        verticalLineTo(17.2f)
                        quadTo(4f, 16.35f, 4.44f, 15.64f)
                        quadTo(4.88f, 14.93f, 5.6f, 14.55f)
                        quadTo(7.15f, 13.77f, 8.75f, 13.39f)
                        reflectiveQuadTo(12f, 13f)
                        reflectiveQuadToRelative(3.25f, 0.39f)
                        reflectiveQuadToRelative(3.15f, 1.16f)
                        quadToRelative(0.72f, 0.38f, 1.16f, 1.09f)
                        reflectiveQuadTo(20f, 17.2f)
                        verticalLineTo(20f)
                        horizontalLineTo(4f)
                        close()
                        moveTo(6f, 18f)
                        horizontalLineTo(18f)
                        verticalLineTo(17.2f)
                        quadToRelative(0f, -0.27f, -0.14f, -0.5f)
                        quadTo(17.73f, 16.48f, 17.5f, 16.35f)
                        quadTo(16.15f, 15.68f, 14.78f, 15.34f)
                        reflectiveQuadTo(12f, 15f)
                        reflectiveQuadTo(9.23f, 15.34f)
                        reflectiveQuadTo(6.5f, 16.35f)
                        quadTo(6.28f, 16.48f, 6.14f, 16.7f)
                        quadTo(6f, 16.93f, 6f, 17.2f)
                        verticalLineTo(18f)
                        close()
                        moveTo(13.41f, 9.41f)
                        quadTo(14f, 8.82f, 14f, 8f)
                        reflectiveQuadTo(13.41f, 6.59f)
                        reflectiveQuadTo(12f, 6f)
                        reflectiveQuadTo(10.59f, 6.59f)
                        quadTo(10f, 7.18f, 10f, 8f)
                        reflectiveQuadToRelative(0.59f, 1.41f)
                        reflectiveQuadTo(12f, 10f)
                        reflectiveQuadTo(13.41f, 9.41f)
                        close()
                        moveTo(12f, 8f)
                        close()
                        moveToRelative(0f, 10f)
                        close()
                    }
                }
                .build()
        return _person!!
    }

private var _person: ImageVector? = null

@Suppress("CheckReturnValue")
val search: ImageVector
    get() {
        if (_search != null) {
            return _search!!
        }
        _search =
            ImageVector.Builder(
                name = "search",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.Companion.NonZero,
                    ) {
                        moveTo(19.6f, 21f)
                        lineTo(13.3f, 14.7f)
                        quadToRelative(-0.75f, 0.6f, -1.72f, 0.95f)
                        reflectiveQuadTo(9.5f, 16f)
                        quadTo(6.78f, 16f, 4.89f, 14.11f)
                        quadTo(3f, 12.23f, 3f, 9.5f)
                        quadTo(3f, 6.77f, 4.89f, 4.89f)
                        reflectiveQuadTo(9.5f, 3f)
                        reflectiveQuadToRelative(4.61f, 1.89f)
                        reflectiveQuadTo(16f, 9.5f)
                        quadToRelative(0f, 1.1f, -0.35f, 2.07f)
                        reflectiveQuadTo(14.7f, 13.3f)
                        lineTo(21f, 19.6f)
                        lineTo(19.6f, 21f)
                        close()
                        moveTo(9.5f, 14f)
                        quadToRelative(1.88f, 0f, 3.19f, -1.31f)
                        reflectiveQuadTo(14f, 9.5f)
                        reflectiveQuadTo(12.69f, 6.31f)
                        reflectiveQuadTo(9.5f, 5f)
                        reflectiveQuadTo(6.31f, 6.31f)
                        reflectiveQuadTo(5f, 9.5f)
                        reflectiveQuadToRelative(1.31f, 3.19f)
                        reflectiveQuadTo(9.5f, 14f)
                        close()
                    }
                }
                .build()
        return _search!!
    }

private var _search: ImageVector? = null


@Suppress("CheckReturnValue")
val notifications: ImageVector
    get() {
        if (_notifications != null) {
            return _notifications!!
        }
        _notifications =
            ImageVector.Builder(
                name = "notifications",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.Companion.NonZero,
                    ) {
                        moveTo(4f, 19f)
                        verticalLineTo(17f)
                        horizontalLineTo(6f)
                        verticalLineTo(10f)
                        quadTo(6f, 7.93f, 7.25f, 6.31f)
                        reflectiveQuadTo(10.5f, 4.2f)
                        verticalLineTo(3.5f)
                        quadToRelative(0f, -0.63f, 0.44f, -1.06f)
                        reflectiveQuadTo(12f, 2f)
                        reflectiveQuadToRelative(1.06f, 0.44f)
                        reflectiveQuadTo(13.5f, 3.5f)
                        verticalLineTo(4.2f)
                        quadToRelative(2f, 0.5f, 3.25f, 2.11f)
                        reflectiveQuadTo(18f, 10f)
                        verticalLineToRelative(7f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(4f)
                        close()
                        moveToRelative(8f, -7.5f)
                        close()
                        moveTo(12f, 22f)
                        quadToRelative(-0.82f, 0f, -1.41f, -0.59f)
                        reflectiveQuadTo(10f, 20f)
                        horizontalLineToRelative(4f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(12f, 22f)
                        close()
                        moveTo(8f, 17f)
                        horizontalLineToRelative(8f)
                        verticalLineTo(10f)
                        quadTo(16f, 8.35f, 14.83f, 7.18f)
                        reflectiveQuadTo(12f, 6f)
                        reflectiveQuadTo(9.18f, 7.18f)
                        reflectiveQuadTo(8f, 10f)
                        verticalLineToRelative(7f)
                        close()
                    }
                }
                .build()
        return _notifications!!
    }

private var _notifications: ImageVector? = null



@Suppress("CheckReturnValue")
val leaderboard: ImageVector
    get() {
        if (_leaderboard != null) {
            return _leaderboard!!
        }
        _leaderboard =
            ImageVector.Builder(
                name = "leaderboard",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.Companion.NonZero,
                    ) {
                        moveTo(4f, 19f)
                        horizontalLineTo(8f)
                        verticalLineTo(11f)
                        horizontalLineTo(4f)
                        verticalLineToRelative(8f)
                        close()
                        moveToRelative(6f, 0f)
                        horizontalLineToRelative(4f)
                        verticalLineTo(5f)
                        horizontalLineTo(10f)
                        verticalLineTo(19f)
                        close()
                        moveToRelative(6f, 0f)
                        horizontalLineToRelative(4f)
                        verticalLineTo(13f)
                        horizontalLineTo(16f)
                        verticalLineToRelative(6f)
                        close()
                        moveTo(2f, 21f)
                        verticalLineTo(9f)
                        horizontalLineTo(8f)
                        verticalLineTo(3f)
                        horizontalLineToRelative(8f)
                        verticalLineToRelative(8f)
                        horizontalLineToRelative(6f)
                        verticalLineTo(21f)
                        horizontalLineTo(2f)
                        close()
                    }
                }
                .build()
        return _leaderboard!!
    }

private var _leaderboard: ImageVector? = null