package com.kelompok2.aplikasiplanme

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kelompok2.aplikasiplanme.databinding.FragmentKuliahBinding

class KuliahFragment : Fragment() {

    private lateinit var binding : FragmentKuliahBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKuliahBinding.inflate(layoutInflater)

        binding.tbKuliah.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.logout -> {
                    showLogOutDilog()
                    true
                }else -> false

            }
        }
        return binding.root
    }

    private fun showLogOutDilog() {
        val builder = AlertDialog.Builder(requireContext())
        val alertDialog = builder.create()
        builder.setTitle("Log Out")
            .setMessage("Apakah Anda ingin keluar?")
            .setPositiveButton("Ya"){_,_ ->
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }
            .setNegativeButton("Tidak") { _, _ ->
                alertDialog.dismiss()
            }
            .show()
            .setCancelable(false)
    }

}