package com.udacity.asteroidradar.ui.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroid.ui.main.MainViewModel
import com.udacity.asteroid.ui.main.MainViewModelFactory
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.Repository
import com.udacity.asteroidradar.data.room.SingletonDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.ui.adapters.AsteroidAdapter

@RequiresApi(Build.VERSION_CODES.N)
class MainFragment : Fragment() {
    // Dependencies
    private lateinit var adapter:AsteroidAdapter

    private val repository by lazy {
        Repository(SingletonDatabase.getDatabase(requireContext()).asteroidDao)
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity() , MainViewModelFactory(repository))[MainViewModel::class.java]
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = AsteroidAdapter(AsteroidAdapter.AsteroidListener { asteroid ->
            viewModel.onItemClicked(asteroid)
        })

        setupAsteroidRecycler(binding)


        viewModel.loading.observe(viewLifecycleOwner){ isLoading ->
//            Log.d("this is an test" , isLoading.toString())
            if (isLoading){
                binding.loadingDataSpinner.visibility = View.VISIBLE
            }else{
                binding.loadingDataSpinner.visibility = View.GONE
            }
        }
        viewModel.fragmentDetailsNavigate!!.observe(viewLifecycleOwner){ asteroid ->
            asteroid?.let {
                val action = MainFragmentDirections.actionShowDetail(asteroid)
                findNavController().navigate(action)
                viewModel.navigateToFragmentDetails()
            }
        }

        binding.executePendingBindings()
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupAsteroidRecycler(binding: FragmentMainBinding) {
        binding.rvAsteroid.adapter = adapter
        binding.rvAsteroid.layoutManager =  LinearLayoutManager(requireContext())
        viewModel.asteroids.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }

    // Menu Item
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_rent_menu -> {
                adapter.submitList(adapter.currentList.filter {
                    it.closeApproachDate == viewModel.getToday()
                })
            }
            R.id.show_buy_menu -> {
                adapter.submitList(viewModel.asteroids.value)
            }
            R.id.show_all_menu->{
                adapter.submitList(viewModel.allAsteroidsItems.value)
            }
            else -> return true
        }
        return true
    }

}
