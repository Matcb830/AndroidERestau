package com.example.fr.isen.ORSO.androiderestaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fr.isen.ORSO.androiderestaurant.databinding.FragmentPhotoBinding
import com.squareup.picasso.Picasso

class PhotoFragment : Fragment() {

    private var image: String? = null
    lateinit var binding: FragmentPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            image = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        Picasso.get().load(image).into(binding.imageView2)
        return binding.root
    }

    companion object {
        val ARG_PARAM1 = "ARG_PARAM1"

        fun newInstance(image: String) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, image)
                }
            }
    }
}