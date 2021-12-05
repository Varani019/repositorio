package adapter

import android.content.ComponentCallbacks
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import model.Person

abstract class PersonListAdapter<view>(personList: ArrayList<Person>, internal var ctx: Context, private val callbacks: (Int) -> Unit):
        RecyclerView.Adapter<PersonListAdapter<Any?>.ViewHolder>(){

            inner class ViewHolder(view: view, itemView: View):RecyclerView.ViewHolder(itemView)
        }