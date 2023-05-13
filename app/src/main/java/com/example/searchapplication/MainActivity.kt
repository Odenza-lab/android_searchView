package com.example.searchapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), UserAdapter.ClickListener {

    private lateinit var recycleView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
    }

    private fun initData() {
        recycleView = findViewById(R.id.recycleView)
        toolbar = findViewById(R.id.tbToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        initRecycleView()
    }

    private fun initRecycleView() {
        val linearLayoutManager = LinearLayoutManager(this)
        recycleView.layoutManager = linearLayoutManager
        recycleView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        userAdapter = UserAdapter(this)
        recycleView.adapter = userAdapter
        showData()
    }

    private fun showData() {
        var userList = ArrayList<UserModel>()
        userList.add(UserModel("Richard"))
        userList.add(UserModel("Alice"))
        userList.add(UserModel("Hanna"))
        userList.add(UserModel("Devid"))
        userAdapter.setData(userList)
    }

    override fun clickItem(userModel: UserModel) {
        val intent = Intent(this, SelectedUserActivity::class.java).putExtra("username", userModel.username)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        var menuItem = menu!!.findItem(R.id.searchView)
        var searchView: SearchView = menuItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userAdapter.filter.filter(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}