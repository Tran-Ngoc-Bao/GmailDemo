package com.example.gmaildemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Data user
        val name: Array<String> = resources.getStringArray(R.array.name)
        val time: Array<String> = resources.getStringArray(R.array.time)
        val topic: Array<String> = resources.getStringArray(R.array.topic)
        val content: Array<String> = resources.getStringArray(R.array.content)

        // List name for Find name
        val findName: AutoCompleteTextView = findViewById(R.id.find_name)
        val adapterName: ArrayAdapter<String> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, name)
        findName.setAdapter(adapterName)

        val mailBox = arrayListOf<Gmail>()
        for (i in 0..9) {
            val nameLower: String = name[i].lowercase()
            mailBox.add(
                Gmail(
                    name[i], time[i], topic[i], content[i], false,
                    resources.getIdentifier("char_${nameLower[0]}", "mipmap", packageName)
                )
            )
        }

        val listGmail = findViewById<ListView>(R.id.list_gmail)
        listGmail.adapter = GmailAdapter(mailBox)
    }
}

data class Gmail(
    val name: String,
    val time: String,
    val topic: String,
    val content: String,
    var selected: Boolean,
    val imageResource: Int
)

class GmailAdapter(private val items: ArrayList<Gmail>) : BaseAdapter() {
    override fun getCount(): Int = items.size
    override fun getItem(p0: Int): Any = items[p0]
    override fun getItemId(p0: Int): Long = p0.toLong()
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        // Use ViewHolder for low setting
        var viewHolder: MyViewHolder
        var itemView: View
        if (p1 == null) {
            itemView = LayoutInflater.from(p2?.context)
                .inflate(R.layout.custom_gmail, p2, false)
            viewHolder = MyViewHolder()
            viewHolder.name = itemView.findViewById(R.id.name)
            viewHolder.time = itemView.findViewById(R.id.time)
            viewHolder.topic = itemView.findViewById(R.id.topic)
            viewHolder.content = itemView.findViewById(R.id.content)
            viewHolder.image = itemView.findViewById(R.id.avatar)
            viewHolder.checkSelect = itemView.findViewById(R.id.selected)
            itemView.tag = viewHolder
        } else {
            itemView = p1
            viewHolder = itemView.tag as MyViewHolder
        }
        viewHolder.name.text = items[p0].name
        viewHolder.time.text = items[p0].time
        viewHolder.topic.text = items[p0].topic
        viewHolder.content.text = items[p0].content
        viewHolder.image.setImageResource(items[p0].imageResource)
        viewHolder.checkSelect.isChecked = items[p0].selected
        viewHolder.checkSelect.setOnClickListener {
            items[p0].selected = !items[p0].selected
            notifyDataSetChanged()
        }
        return itemView
    }

    class MyViewHolder {
        lateinit var name: TextView
        lateinit var time: TextView
        lateinit var topic: TextView
        lateinit var content: TextView
        lateinit var image: ImageView
        lateinit var checkSelect: CheckBox
    }
}
