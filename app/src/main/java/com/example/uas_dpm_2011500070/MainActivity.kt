package com.example.uas_dpm_2011500070

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var adpDosen : AdapterDosen
    private lateinit var dataDosen : ArrayList<Dosen>
    private lateinit var lvDosen: ListView
    private lateinit var linTidakAda: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvDosen = findViewById(R.id.lvMataKuliah)
        linTidakAda = findViewById(R.id.LinTidakAda)

        dataDosen= ArrayList()
        adpDosen = AdapterDosen(this@MainActivity, dataDosen)

        lvDosen.adapter = adpDosen


        refresh()
        btnTambah.setOnClickListener {
            val i = Intent(this@MainActivity, EntriDosenActivity::class.java)
            startActivity(i)
        }
    }

    override fun onResume(){
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) refresh()
    }

    private fun refresh(){
        val db =  campuss(this@MainActivity)
        val data = db.tampil()
        repeat(dataDosen.size) { dataDosen.removeFirst() }
        if(data.count>0){
            while(data.moveToNext()) {
                val dosen = Dosen(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adpDosen.add(dosen)
                adpDosen.notifyDataSetChanged()
            }
            lvDosen.visibility = View.VISIBLE
            linTidakAda.visibility = View.GONE
        } else {
            lvDosen.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}
