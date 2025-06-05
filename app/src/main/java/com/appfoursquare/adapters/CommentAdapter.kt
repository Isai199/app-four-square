package com.appfoursquare.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appfoursquare.CommentFragment
import com.appfoursquare.R
import com.appfoursquare.models.opinions.OpinionWithUser

class CommentAdapter(
    val context: CommentFragment
): RecyclerView.Adapter<CommentAdapter.ViewHolder>() {


    private val allComments: ArrayList<OpinionWithUser> = arrayListOf()


    // on below line we are creating a view holder class.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorTV = itemView.findViewById<TextView>(R.id.tvCommentAuthor)
        val opinionTV = itemView.findViewById<TextView>(R.id.tvItemCommentOpinion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_comment,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.authorTV.setText(allComments.get(position).user_name)
        holder.opinionTV.setText(allComments.get(position).opinion_comment)
    }

    override fun getItemCount(): Int {
        // on below line we are
        // returning our list size.
        return allComments.size
    }

    fun updateList(newComment: ArrayList<OpinionWithUser>) {

        allComments.clear()

        allComments.addAll(newComment)

        notifyDataSetChanged()
    }
}