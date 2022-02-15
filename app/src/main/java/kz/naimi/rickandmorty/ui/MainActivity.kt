package kz.naimi.rickandmorty.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kz.naimi.rickandmorty.databinding.ActivityMainBinding
import kz.naimi.rickandmorty.network.models.Character
import kz.naimi.rickandmorty.ui.adapter.CharactersRecyclerAdapter
import kz.naimi.rickandmorty.utils.DataState
import kz.naimi.rickandmorty.utils.PaginationScrollListener

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: CharactersRecyclerAdapter
    private lateinit var mCharacters: MutableList<Character>
    private lateinit var mViewModel: MainViewModel
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setUpRecycler()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mViewModel.getCharacters(mCharacters.size)
        mViewModel.state.observe(this, Observer {
            when (it) {
                is DataState.Success -> {
                    hideProgressBar()
                    mCharacters.addAll(it.characters)
                    mAdapter.notifyDataSetChanged()
                }
                is DataState.Progress -> showProgressBar()
                is DataState.Error -> {
                    hideProgressBar()
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setUpRecycler() {
        mCharacters = ArrayList()
        mAdapter = CharactersRecyclerAdapter(mCharacters)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mBinding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
                override fun loadMoreItems() {
                    Toast.makeText(this@MainActivity, "pagination requested", Toast.LENGTH_SHORT)
                        .show()
                    isLoading = true
                    mViewModel.getCharacters(mCharacters.size)
                }

                override fun isLastPage() = isLastPage

                override fun isLoading() = isLoading

            })
        }
    }

    private fun showProgressBar() {
        mBinding.progressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideProgressBar() {
        mBinding.progressBar.visibility = View.GONE
        isLoading = false
    }
}