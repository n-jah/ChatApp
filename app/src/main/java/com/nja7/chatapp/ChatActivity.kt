package com.nja7.chatapp

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nja7.chatapp.databinding.ActivityChatBinding
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.FileInputStream

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var adapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference

    private var receiverToken: String? = null
    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        receiverToken = intent.getStringExtra("token")

        mDbRef = FirebaseDatabase.getInstance().reference

        messageRecyclerView = binding.cheatRecyclerView
        messageBox = binding.ChatBox
        sendButton = binding.sendButton
        messageList = ArrayList()

        adapter = MessageAdapter(this, messageList)
        messageRecyclerView.adapter = adapter
        messageRecyclerView.layoutManager = LinearLayoutManager(this)

// to make the rooms name static unique
         senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title = name.toString()
        binding.ChatBox.requestFocus()

        if (name == null || receiverUid == null || senderUid == null) {
            Toast.makeText(this, "Missing data from intent", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Get the data from the database
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                        adapter.notifyDataSetChanged()
                        messageRecyclerView.scrollToPosition(messageList.size - 1)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChatActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })

        // Send the message to the database
        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Message(message, senderUid.toString())
            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
            messageRecyclerView.scrollToPosition(messageList.size - 1)
            adapter.notifyDataSetChanged()
            // Send the notification to the receiver
            getSenderNameAndSendNotification(senderUid!!, receiverUid, messageObject)
        }
    }

    private fun getSenderNameAndSendNotification(senderUid: String, receiverUid: String, messageObject: Message) {
        mDbRef.child("user").child(senderUid).child("name")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val senderName = dataSnapshot.getValue(String::class.java)
                    if (senderName != null) {
                        sendNotification(receiverUid, messageObject, senderName)
                    } else {
                        Toast.makeText(this@ChatActivity, "Sender name not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@ChatActivity, "Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun sendNotification(receiverUid: String, messageObject: Message, senderName: String) {
        Handler().postDelayed({
            val sendNotification = SendNotification(receiverToken!!, senderName, messageObject.message.toString(), this@ChatActivity,receiverUid)
            sendNotification.sendNotification()
        }, 300)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        messageRecyclerView.scrollToPosition(messageList.size - 1)
        adapter.notifyDataSetChanged()
    }
}
