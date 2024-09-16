package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.Model.Contact
import com.example.kotlindemo.adapter.ContactAdapter

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val rv_contact: RecyclerView = findViewById<RecyclerView>(R.id.rv_contact)

        val layoutManager = LinearLayoutManager(this)
        rv_contact.layoutManager = layoutManager
        val usetContact = ContactList()

        val contactAdapter = ContactAdapter(this, usetContact)
        rv_contact.adapter = contactAdapter

    }
}

private fun ContactList(): List<Contact> {

    val contactList = mutableListOf<Contact>()

    contactList.add(Contact("Tinu", R.drawable.user, "123335677895"))
    contactList.add(Contact("Divu", R.drawable.user, "547446589871"))
    contactList.add(Contact("Ram", R.drawable.user, "123345677895"))
    contactList.add(Contact("Lakhan", R.drawable.user, "2455432716887"))
    contactList.add(Contact("Krishna", R.drawable.user, "123345767895"))
    contactList.add(Contact("Manya", R.drawable.user, "767868377234"))
    contactList.add(Contact("Daivik", R.drawable.user, "1233456587895"))
    contactList.add(Contact("Tanvi", R.drawable.user, "94697876384724"))
    contactList.add(Contact("Nirva", R.drawable.user, "565986558698955"))

    return contactList

}
