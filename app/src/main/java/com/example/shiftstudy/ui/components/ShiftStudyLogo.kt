package com.example.shiftstudy.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Custom ShiftStudy Logo
 * Features a stylized "S" with a book/graduation cap design
 */
@Composable
fun ShiftStudyLogo(
    size: Dp = 80.dp,
    backgroundColor: Color = Color.White.copy(alpha = 0.2f),
    foregroundColor: Color = Color.White
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size * 0.6f)) {
            val canvasSize = this.size
            val strokeWidth = canvasSize.width * 0.08f

            // Draw stylized "S" shape
            val sPath = Path().apply {
                // Top curve of S
                moveTo(canvasSize.width * 0.7f, canvasSize.height * 0.2f)
                cubicTo(
                    canvasSize.width * 0.7f, canvasSize.height * 0.05f,
                    canvasSize.width * 0.3f, canvasSize.height * 0.05f,
                    canvasSize.width * 0.3f, canvasSize.height * 0.2f
                )
                cubicTo(
                    canvasSize.width * 0.3f, canvasSize.height * 0.35f,
                    canvasSize.width * 0.6f, canvasSize.height * 0.4f,
                    canvasSize.width * 0.6f, canvasSize.height * 0.5f
                )

                // Bottom curve of S
                cubicTo(
                    canvasSize.width * 0.6f, canvasSize.height * 0.65f,
                    canvasSize.width * 0.3f, canvasSize.height * 0.7f,
                    canvasSize.width * 0.3f, canvasSize.height * 0.8f
                )
                cubicTo(
                    canvasSize.width * 0.3f, canvasSize.height * 0.95f,
                    canvasSize.width * 0.7f, canvasSize.height * 0.95f,
                    canvasSize.width * 0.7f, canvasSize.height * 0.8f
                )
            }

            // Draw the S
            drawPath(
                path = sPath,
                color = foregroundColor,
                style = Stroke(width = strokeWidth)
            )

            // Draw book pages accent (three lines on the right)
            val lineSpacing = canvasSize.height * 0.15f
            for (i in 0..2) {
                val y = canvasSize.height * 0.3f + (i * lineSpacing)
                drawLine(
                    color = foregroundColor,
                    start = Offset(canvasSize.width * 0.75f, y),
                    end = Offset(canvasSize.width * 0.95f, y),
                    strokeWidth = strokeWidth * 0.6f
                )
            }
        }
    }
}

/**
 * Alternative Logo Design: Stacked Books with "S"
 */
@Composable
fun ShiftStudyLogoAlt(
    size: Dp = 80.dp,
    backgroundColor: Color = Color.White.copy(alpha = 0.2f),
    foregroundColor: Color = Color.White
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size * 0.65f)) {
            val canvasSize = this.size

            // Draw stacked books (smaller, moved to bottom)
            val bookHeight = canvasSize.height * 0.12f
            val books = listOf(
                Pair(0.15f, 0.65f),  // x offset, y position
                Pair(0.1f, 0.53f),
                Pair(0.2f, 0.41f)
            )

            books.forEach { (xOffset, yPos) ->
                val bookPath = Path().apply {
                    moveTo(canvasSize.width * xOffset, canvasSize.height * yPos)
                    lineTo(canvasSize.width * (1f - xOffset), canvasSize.height * yPos)
                    lineTo(canvasSize.width * (1f - xOffset), canvasSize.height * yPos + bookHeight)
                    lineTo(canvasSize.width * xOffset, canvasSize.height * yPos + bookHeight)
                    close()
                }
                drawPath(
                    path = bookPath,
                    color = foregroundColor,
                    style = Stroke(width = canvasSize.width * 0.03f)
                )
            }

            // Draw proper "S" shape on top
            val sPath = Path().apply {
                // Start at top right
                moveTo(canvasSize.width * 0.7f, canvasSize.height * 0.1f)

                // Top curve - curves LEFT
                cubicTo(
                    canvasSize.width * 0.7f, canvasSize.height * 0.05f,
                    canvasSize.width * 0.3f, canvasSize.height * 0.05f,
                    canvasSize.width * 0.3f, canvasSize.height * 0.15f
                )

                // Middle section - goes back RIGHT
                cubicTo(
                    canvasSize.width * 0.3f, canvasSize.height * 0.25f,
                    canvasSize.width * 0.7f, canvasSize.height * 0.25f,
                    canvasSize.width * 0.7f, canvasSize.height * 0.35f
                )

                // Bottom curve - curves LEFT again
                cubicTo(
                    canvasSize.width * 0.7f, canvasSize.height * 0.45f,
                    canvasSize.width * 0.3f, canvasSize.height * 0.45f,
                    canvasSize.width * 0.3f, canvasSize.height * 0.55f
                )
            }

            drawPath(
                path = sPath,
                color = foregroundColor,
                style = Stroke(width = canvasSize.width * 0.08f)
            )
        }
    }
}

/**
 * Simple Modern Logo: Geometric "S" with Arrow
 */
@Composable
fun ShiftStudyLogoModern(
    size: Dp = 80.dp,
    backgroundColor: Color = Color.White.copy(alpha = 0.2f),
    foregroundColor: Color = Color.White
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size * 0.6f)) {
            val canvasSize = this.size
            val strokeWidth = canvasSize.width * 0.1f

            // Draw angular "S" shape
            val path = Path().apply {
                moveTo(canvasSize.width * 0.75f, canvasSize.height * 0.15f)
                lineTo(canvasSize.width * 0.25f, canvasSize.height * 0.15f)
                lineTo(canvasSize.width * 0.25f, canvasSize.height * 0.35f)
                lineTo(canvasSize.width * 0.65f, canvasSize.height * 0.35f)
                lineTo(canvasSize.width * 0.65f, canvasSize.height * 0.65f)
                lineTo(canvasSize.width * 0.25f, canvasSize.height * 0.65f)
                lineTo(canvasSize.width * 0.25f, canvasSize.height * 0.85f)
                lineTo(canvasSize.width * 0.75f, canvasSize.height * 0.85f)
            }

            drawPath(
                path = path,
                color = foregroundColor,
                style = Stroke(width = strokeWidth)
            )

            // Draw upward arrow (shift symbol)
            val arrowPath = Path().apply {
                moveTo(canvasSize.width * 0.85f, canvasSize.height * 0.4f)
                lineTo(canvasSize.width * 0.85f, canvasSize.height * 0.7f)

                // Arrow head
                moveTo(canvasSize.width * 0.75f, canvasSize.height * 0.5f)
                lineTo(canvasSize.width * 0.85f, canvasSize.height * 0.4f)
                lineTo(canvasSize.width * 0.95f, canvasSize.height * 0.5f)
            }

            drawPath(
                path = arrowPath,
                color = foregroundColor,
                style = Stroke(width = strokeWidth * 0.7f)
            )
        }
    }
}