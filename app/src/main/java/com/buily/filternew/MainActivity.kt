package com.buily.filternew

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.buily.filternew.adapter.NewsAdapter
import com.buily.filternew.databinding.ActivityMainBinding
import com.buily.filternew.model.News
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NewsAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        val data = mutableListOf<News>()

        adapter = NewsAdapter(data)
        adapter.onItemClick = {

            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("link", it.link)
            startActivity(intent)

        }

        binding.recycleMain.adapter = adapter


    }


    @SuppressLint("CheckResult")
    private fun getData(key: String) {

        binding.recycleMain.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        Observable.create<List<News>> {

            val api = "https://news.google.de/news/feeds?pz=1&cf=vi_vn&ned=vi_vn&hl=vi_vn&q=$key"
            val factory: SAXParserFactory = SAXParserFactory.newInstance()
            val parser: SAXParser = factory.newSAXParser()
            val xmlParser = XMLParser()
            parser.parse(api, xmlParser)
            it.onNext(xmlParser.arr)
            it.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t: List<News>? ->
                t?.let { adapter.setData(it) }
                binding.recycleMain.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }

    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.menu_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_LONG).show()
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                query?.let { getData(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }
}




