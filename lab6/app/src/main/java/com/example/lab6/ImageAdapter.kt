package com.example.lab6

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter(private val imageUris: List<Uri>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val descriptions = mutableMapOf<Int, String>()

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val descriptionText: TextView = itemView.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = imageUris.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageURI(imageUris[position])
        holder.descriptionText.text = descriptions[position] ?: ""

        holder.itemView.setOnLongClickListener {
            showDescriptionDialog(holder.itemView.context, position)
            true
        }
    }

    private fun showDescriptionDialog(context: Context, position: Int) {
        val editText = EditText(context).apply {
            hint = "Введите описание"
            setText(descriptions[position] ?: "")
        }

        AlertDialog.Builder(context)
            .setTitle("Описание изображения")
            .setView(editText)
            .setPositiveButton("Сохранить") { _, _ ->
                descriptions[position] = editText.text.toString()
                notifyItemChanged(position)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
}
