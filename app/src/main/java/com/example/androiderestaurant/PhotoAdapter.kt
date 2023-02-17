package com.example.fr.isen.ORSO.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PhotoAdapter(val images: List<String>, activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return images.count()
    }

    override fun createFragment(position: Int): Fragment {
        return PhotoFragment.newInstance(images[position])
    }
}