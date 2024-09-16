package com.example.kotlindemo.nestedRecyclerView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R

class NestedRecyclerViewActivity : AppCompatActivity() {

    val parentListItem = ArrayList<ParentDataClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_recycler_view)

        val rvParent: RecyclerView = findViewById(R.id.rv_parent)

        rvParent.setHasFixedSize(true)
        rvParent.layoutManager = LinearLayoutManager(this)

        val adapter = ParentAdapter(parentListItem)
        SetData()
        rvParent.adapter = adapter


    }

    fun SetData() {


        val childItemList = ArrayList<ChildDataClass>()
        childItemList.add(ChildDataClass(R.drawable.god1))
        childItemList.add(ChildDataClass(R.drawable.god2))
        childItemList.add(ChildDataClass(R.drawable.god3))
        childItemList.add(ChildDataClass(R.drawable.god4))
        childItemList.add(ChildDataClass(R.drawable.god5))
        childItemList.add(ChildDataClass(R.drawable.god6))
        childItemList.add(ChildDataClass(R.drawable.god7))
        childItemList.add(ChildDataClass(R.drawable.god8))
        childItemList.add(ChildDataClass(R.drawable.god9))

        parentListItem.add(ParentDataClass("God1", childItemList))


        val childItemList1 = ArrayList<ChildDataClass>()
        childItemList1.add(ChildDataClass(R.drawable.god10))
        childItemList1.add(ChildDataClass(R.drawable.god12))
        childItemList1.add(ChildDataClass(R.drawable.god13))
        childItemList1.add(ChildDataClass(R.drawable.god14))
        childItemList1.add(ChildDataClass(R.drawable.god15))
        childItemList1.add(ChildDataClass(R.drawable.god16))
        childItemList1.add(ChildDataClass(R.drawable.god17))
        childItemList1.add(ChildDataClass(R.drawable.god11))


        parentListItem.add(ParentDataClass("God2", childItemList1))


        val childItemList2 = ArrayList<ChildDataClass>()
        childItemList2.add(ChildDataClass(R.drawable.god17))
        childItemList2.add(ChildDataClass(R.drawable.god2))
        childItemList2.add(ChildDataClass(R.drawable.god9))
        childItemList2.add(ChildDataClass(R.drawable.god4))
        childItemList2.add(ChildDataClass(R.drawable.god15))
        childItemList2.add(ChildDataClass(R.drawable.god6))
        childItemList2.add(ChildDataClass(R.drawable.god17))
        childItemList2.add(ChildDataClass(R.drawable.god3))


        parentListItem.add(ParentDataClass("God3", childItemList2))

    }
}