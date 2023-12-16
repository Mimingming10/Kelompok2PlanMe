package com.kelompok2.aplikasiplanme

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kelompok2.aplikasiplanme.databinding.FragmentKuliahBinding

class KuliahFragment : Fragment() {

    private lateinit var binding : FragmentKuliahBinding
    private lateinit var kuliahAdapter: KuliahAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKuliahBinding.inflate(layoutInflater)

        binding.apply {
            tbKuliah.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.logout -> {
                        showLogOutDilog()
                        true
                    }else -> false
                }
            }
        }
        prepareRvForKuliahAdapter()
        showAllKuliah()
        return binding.root
    }

    private fun showAllKuliah() {
        Utils.showDialog(requireContext())
        FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val kulList = arrayListOf<Users>()
                for(kuliah in snapshot.children){
                    val currentUser = kuliah.getValue(Users::class.java)
                    if(currentUser?.userType == "Kuliah"){
                        kulList.add(currentUser)
                    }
                }
                kuliahAdapter.differ.submitList(kulList)
                Utils.hideDialog()
            }

            override fun onCancelled(error: DatabaseError) {
                Utils.apply {
                    hideDialog()
                    showToast(requireContext(), error.message)
                }
            }
        })
    }

    private fun prepareRvForKuliahAdapter() {
        kuliahAdapter = KuliahAdapter()
        binding.rvfmKuliah.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = kuliahAdapter
        }
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