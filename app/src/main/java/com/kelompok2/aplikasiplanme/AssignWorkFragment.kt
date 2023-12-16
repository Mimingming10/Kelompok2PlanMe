package com.kelompok2.aplikasiplanme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kelompok2.aplikasiplanme.databinding.FragmentAssignWorkBinding

class AssignWorkFragment : Fragment() {

    private lateinit var binding : FragmentAssignWorkBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAssignWorkBinding.inflate(layoutInflater)
        binding.tbAssignWork.apply {
            setNavigationOnClickListener { activity?.onBackPressed() }
        }
        return binding.root
    }

}