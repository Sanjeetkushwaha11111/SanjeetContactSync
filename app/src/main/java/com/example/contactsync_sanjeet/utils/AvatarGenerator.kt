package com.example.contactsync_sanjeet.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.icu.lang.UCharacter.DecompositionType.CIRCLE
import android.text.TextPaint
import java.util.Locale

class AvatarGenerator(private val builder: AvatarBuilder) {

    class AvatarBuilder(private val context: Context) {
        val COLOR700 = 700

        private var textSize = 100
        private var size = 14
        private var name = " "
        private var shapeType = CIRCLE


        fun build(): BitmapDrawable {
            return avatarImageGenerate(
                context,
                size,
                shapeType,
                name,
                textSize,
                COLOR700
            )
        }


        private fun avatarImageGenerate(
            context: Context,
            size: Int,
            shape: Int,
            name: String,
            textSize: Int,
            colorModel: Int
        ): BitmapDrawable {
            uiContext = context

            texSize = calTextSize(textSize)
            val label = firstCharacter(name)
            val textPaint = textPainter()
            val painter = painter()
            painter.isAntiAlias = true
            val areaRect = Rect(0, 0, size, size)

            if (shape == 0) {
                val firstLetter = firstCharacter(name)
                val r = firstLetter[0]
                val list= arrayListOf("#534AE3","#04834A","#041893","#AD2F07")
                painter.color = Color.parseColor(list.random())
            } else {
                painter.color = Color.TRANSPARENT
            }

            val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawRect(areaRect, painter)

            //reset painter
            if (shape == 0) {
                painter.color = Color.TRANSPARENT
            } else {
                val firstLetter = firstCharacter(name)
                val r = firstLetter[0]
                val list= arrayListOf("#534AE3","#04834A","#041893","#AD2F07")
                painter.color = Color.parseColor(list.random())
            }

            val bounds = RectF(areaRect)
            bounds.right = textPaint.measureText(label, 0, 1)
            bounds.bottom = textPaint.descent() - textPaint.ascent()

            bounds.left += (areaRect.width() - bounds.right) / 2.0f
            bounds.top += (areaRect.height() - bounds.bottom) / 2.0f

            canvas.drawCircle(size.toFloat() / 2, size.toFloat() / 2, size.toFloat() / 2, painter)
            canvas.drawText(label, bounds.left, bounds.top - textPaint.ascent(), textPaint)
            return BitmapDrawable(uiContext.resources, bitmap)

        }

        private fun firstCharacter(name: String): String {
            if (name.isEmpty()) {
                return "-"
            }
            return name.first().toString()
        }

        private fun textPainter(): TextPaint {
            val textPaint = TextPaint()
            textPaint.isAntiAlias = true
            textPaint.textSize = texSize * uiContext.resources.displayMetrics.scaledDensity
            textPaint.color = Color.WHITE
            return textPaint
        }

        private fun painter(): Paint {
            return Paint()
        }

        private fun calTextSize(size: Int): Float {
            return (size).toFloat()
        }

    }


    /**
     * Deprecate and will be removed
     */
    companion object {
        private lateinit var uiContext: Context
        private var texSize = 0F
        val COLOR700 = 700

        fun avatarImage(context: Context, size: Int, shape: Int, name: String): BitmapDrawable {
            return avatarImageGenerate(context, size, shape, name, COLOR700)
        }


        fun avatarImage(
            context: Context,
            size: Int,
            shape: Int,
            name: String,
            colorModel: Int
        ): BitmapDrawable {
            return avatarImageGenerate(context, size, shape, name, colorModel)
        }

        private fun avatarImageGenerate(
            context: Context,
            size: Int,
            shape: Int,
            name: String,
            colorModel: Int
        ): BitmapDrawable {
            uiContext = context

            texSize = calTextSize(size)
            val label = firstCharacter(name)
            val textPaint = textPainter()
            val painter = painter()
            painter.isAntiAlias = true
            val areaRect = Rect(0, 0, size, size)

            if (shape == 0) {
                val firstLetter = firstCharacter(name)
                val r = firstLetter[0]
                val list= arrayListOf("#534AE3","#04834A","#041893","#AD2F07")
                painter.color = Color.parseColor(list.random())
            } else {
                painter.color = Color.TRANSPARENT
            }

            val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawRect(areaRect, painter)

            //reset painter
            if (shape == 0) {
                painter.color = Color.TRANSPARENT
            } else {
                val firstLetter = firstCharacter(name)
                val r = firstLetter[0]
                val list= arrayListOf("#534AE3","#04834A","#041893","#AD2F07")
                painter.color = Color.parseColor(list.random())
            }

            val bounds = RectF(areaRect)
            bounds.right = textPaint.measureText(label, 0, 1)
            bounds.bottom = textPaint.descent() - textPaint.ascent()

            bounds.left += (areaRect.width() - bounds.right) / 2.0f
            bounds.top += (areaRect.height() - bounds.bottom) / 2.0f

            canvas.drawCircle(size.toFloat() / 2, size.toFloat() / 2, size.toFloat() / 2, painter)
            canvas.drawText(label, bounds.left, bounds.top - textPaint.ascent(), textPaint)
            return BitmapDrawable(uiContext.resources, bitmap)

        }


        private fun firstCharacter(name: String): String {
            return if (!name.isNullOrEmpty())
                name.first().toString().toUpperCase(Locale.ROOT)
            else
                "A"
        }

        private fun textPainter(): TextPaint {
            val textPaint = TextPaint()
            textPaint.isAntiAlias = true
            textPaint.textSize = 120f
            textPaint.color = Color.WHITE
            return textPaint
        }

        private fun painter(): Paint {
            return Paint()
        }

        private fun calTextSize(size: Int): Float {
            return (size).toFloat()
        }
    }
}