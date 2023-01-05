package com.udacity.asteroidradar.ui.detail


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroid.ui.main.MainViewModel
import com.udacity.asteroid.ui.main.MainViewModelFactory
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.Repository
import com.udacity.asteroidradar.data.room.SingletonDatabase
import com.udacity.asteroidradar.databinding.FragmentDetailBinding
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.ui.adapters.AsteroidAdapter
import com.udacity.asteroidradar.ui.main.MainFragmentDirections

@RequiresApi(Build.VERSION_CODES.N)
class DetailFragment : Fragment() {
    lateinit var binding:FragmentDetailBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.helpButton.setOnClickListener {
            displayAlertDialog()
        }

    }

    private fun displayAlertDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
