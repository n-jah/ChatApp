package com.nja7.chatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.nja7.chatapp.databinding.UserLayoutBinding

class UserAdapter(val context: Context, val users: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private lateinit var binding: UserLayoutBinding

    // بترجعله ال item ممسوك متلبس هنا
//    onCreateViewHolder:
//
//    Creates new UserViewHolder instances as needed. This method inflates the item layout and initializes the ViewHolder.
//    Called when the RecyclerView needs a new view to display an item.
//    onBindViewHolder:
//
//    Binds data to the UserViewHolder. This method sets the data for each item based on its position in the data set.
//    Called by the RecyclerView to display data at the specified position.
//    getItemCount:
//
//    Returns the total number of items in the data set held by the adapter.
//    Used by the RecyclerView to determine how many items need to be displayed.

    class UserViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName  = itemView.findViewById<TextView>(R.id.nameTextView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {

        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = users[position]
        holder.textName.text = currentUser.name
        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.uid)
            intent.putExtra("token", currentUser.token)
            context.startActivity(intent)

        }

    }

}