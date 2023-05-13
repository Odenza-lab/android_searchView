package com.example.searchapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SelectedUserActivity : AppCompatActivity() {

    private lateinit var selectedUserTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_user)

        initData()
    }

    private fun initData() {
        selectedUserTextView = findViewById(R.id.tvSelectedUser)
        getData()
    }

    private fun getData() {
        var intent = intent.extras ?: return
        val username = intent.getString("username")

        selectedUserTextView.text = username
    }
}