package com.example.cyxteratest.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cyxteratest.R
import com.example.cyxteratest.data.models.UserAttempt
import kotlinx.android.synthetic.main.attempt_item_view.view.*

class AttemptsAdapter (private val attemptsList: List<UserAttempt>) : RecyclerView.Adapter<AttemptsAdapter.AttemptsViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AttemptsViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.attempt_item_view, viewGroup, false)
        return AttemptsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return attemptsList.size
    }

    override fun onBindViewHolder(viewHolder: AttemptsViewHolder, i: Int) {
        viewHolder.itemView.attemptTextView.text = String.format(viewHolder.itemView.context.getString(R.string.description_attempt),
            attemptsList[i].date,
            when(attemptsList[i].type) {
                true -> viewHolder.itemView.context.getString(R.string.description_success)
                else -> viewHolder.itemView.context.getString(R.string.description_wrong)
            })
    }

    class AttemptsViewHolder(view: View) : RecyclerView.ViewHolder(view)
}