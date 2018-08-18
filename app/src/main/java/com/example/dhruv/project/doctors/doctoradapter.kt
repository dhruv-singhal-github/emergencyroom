package com.example.dhruv.project.doctors

import android.content.Context
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dhruv.project.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.doctorlayout.view.*


public class doctorAdapter (var doctors:ArrayList<doctor>): RecyclerView.Adapter<doctorAdapter.DoctorViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int):DoctorViewHolder =

            DoctorViewHolder((p0.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.doctorlayout, p0, false))




    override fun getItemCount(): Int=doctors.size


    override fun onBindViewHolder(p0: DoctorViewHolder, p1: Int) {

        p0.BindData(doctors[p1])

    }



    class DoctorViewHolder (ItemView: View): RecyclerView.ViewHolder(ItemView)
    {

        val name=ItemView.dname
        val designation=ItemView.designation
        val speciality=ItemView.speciality
        val bio=ItemView.bio
        val addre=ItemView.address
        val photo=ItemView.photo
        val no=ItemView.pno



        public  fun BindData(cour: doctor)
        {
            name.text=cour.first_name+" "+cour.last_name
            designation.text=cour.title
            speciality.text=cour.uid+":"+cour.description
            bio.text=cour.bio

            no.text="Contact no. :"+cour.number
            addre.text=cour.street+","+cour.city+","+cour.state_long
            Picasso.get().load(cour.image_url).into(photo)


        }


    }


}


