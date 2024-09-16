package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.SAXException
import java.io.IOException
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class DomParserActivity : AppCompatActivity() {

    var empDataHashMap = HashMap<String, String>()
    var empList: ArrayList<HashMap<String, String>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dom_parser)

        try {
            val lv = findViewById<ListView>(R.id.listView_)
            val istream = assets.open("empdetails.xml")
            val builderFactory = DocumentBuilderFactory.newInstance()
            val docBuilder = builderFactory.newDocumentBuilder()
            val doc = docBuilder.parse(istream)

            val nList = doc.getElementsByTagName("employee")
            for (i in 0 until nList.getLength()) {
                if (nList.item(0).getNodeType().equals(Node.ELEMENT_NODE) ) {
                    //creating instance of HashMap to put the data of node value
                    empDataHashMap = HashMap()
                    val element = nList.item(i) as Element
                    empDataHashMap.put("name", getNodeValue("name", element))
                    empDataHashMap.put("salary", getNodeValue("salary", element))
                    empDataHashMap.put("designation", getNodeValue("designation", element))

                    empList.add(empDataHashMap)
                }
            }
            val adapter = SimpleAdapter(this@DomParserActivity, empList, R.layout.employee_list, arrayOf("name", "salary", "designation"), intArrayOf(R.id.name, R.id.salary, R.id.designation))
            lv.setAdapter(adapter)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
        } catch (e: SAXException) {
            e.printStackTrace()
        }

    }

    protected fun getNodeValue(tag: String, element: Element): String {
        val nodeList = element.getElementsByTagName(tag)
        val node = nodeList.item(0)
        if (node != null) {
            if (node.hasChildNodes()) {
                val child = node.getFirstChild()
                while (child != null) {
                    if (child.getNodeType() === Node.TEXT_NODE) {
                        return child.getNodeValue()
                    }
                }
            }
        }
        return ""
    }
}

