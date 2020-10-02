package com.example.passkeep.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.passkeep.R



data class MenuItems(val site: String, val username: String, val password: String)


class RecyclerAdapter(private val webAndId: List<MenuItems>) : RecyclerView.Adapter<RecyclerAdapter.MenuViewHolder>(){

    class MenuViewHolder(view: View): RecyclerView.ViewHolder(view){
        var website: TextView= view.findViewById(R.id.siteView)
        var username: TextView= view.findViewById(R.id.idView)
        var password: TextView= view.findViewById(R.id.pwView)


        init {
                view.setOnClickListener {

                    when(password.visibility){
                        View.VISIBLE->{ password.visibility=View.GONE}
                        View.GONE->{ password.visibility= View.VISIBLE}
                    }
                }

        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.retrieve_recycler_item,parent,false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.website.text=webAndId[position].site
        holder.username.text=webAndId[position].username
        holder.password.text=webAndId[position].password


    }

    override fun getItemCount(): Int {
        return webAndId.size
    }


}

