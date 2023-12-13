package com.kelompok2.aplikasiplanme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kelompok2.aplikasiplanme.databinding.FragmentKerjaBinding

class KerjaFragment : Fragment() {

    private lateinit var binding : FragmentKerjaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKerjaBinding.inflate(layoutInflater)
        binding.fabAssignWork.setOnClickListener {
            findNavController().navigate(R.id.action_kerjaFragment_to_assignWorkFragment)
        }
        return binding.root
    }

}