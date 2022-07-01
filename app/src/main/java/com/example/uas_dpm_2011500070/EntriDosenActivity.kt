package com.example.uas_dpm_2011500070

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EntriDosenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_pengentri_data)

        val modeEdit = intent.hasExtra("kode") && intent.hasExtra("nama") &&
                intent.hasExtra("Jabatan") && intent.hasExtra("golongan_pangkat") &&
                intent.hasExtra("Pendidikan") && intent.hasExtra("Keahlian") &&
                intent.hasExtra("Program_studi")
        title = if (modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etNIDN = findViewById<EditText>(R.id.etNIDN)
        val etNmDosen = findViewById<EditText>(R.id.etNmDosen)
        val spnJabatan = findViewById<Spinner>(R.id.spnJabatan)
        val spnGolongan = findViewById<Spinner>(R.id.spnGolongan)
        val rdS2 = findViewById<RadioButton>(R.id.rdS2)
        val rdS3 = findViewById<RadioButton>(R.id.rdS3)
        val etBidangKeahlian = findViewById<EditText>(R.id.etBidangKeahlian)
        val etProgramStudi = findViewById<EditText>(R.id.etProgramStudi)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val Jabatan = arrayOf("Tenaga Pengajar", "Asisten Ahli", "Lektor", "Lektor Kepala", "Guru Besar")
        val golongan = arrayOf("III/a - Penata Muda", "III/b - Penata Muda Tingkat I",
                               "III/c - Penata", "III/d - Penata Tingkat I", "IV/a - Pembina",
                               "IV/b - Pembina Tingkat I", "IV/c - Pembina Utama Muda",
                                "IV/d - Pembina Utama Madya", "IV/e - Pembina Utama")
        val adpJabatan = ArrayAdapter(
            this@EntriDosenActivity,
            android.R.layout.simple_spinner_dropdown_item,
            Jabatan
        )
        val adpGolongan = ArrayAdapter(
            this@EntriDosenActivity,
            android.R.layout.simple_spinner_dropdown_item,
           golongan
        )
        spnJabatan.adapter = adpJabatan
        spnGolongan.adapter = adpGolongan

        if (modeEdit) {
            val kode = intent.getStringExtra("kode")
            val nama = intent.getStringExtra("nama")
            val jabatan = intent.getStringExtra("Jabatan",)
            val golonganPangkat = intent.getStringExtra("golongan_pangkat")
            val pendidikanTerakhir = intent.getStringExtra("pendidikan")
            val bidangKeahlian = intent.getStringExtra("Keahlian")
            val programStudi = intent.getStringExtra("Program_studi")

            etNIDN.setText(kode)
            etNmDosen.setText(nama)
            spnJabatan.setSelection(Jabatan.indexOf(jabatan))
            spnGolongan.setSelection(golongan.indexOf(golonganPangkat))
            if (pendidikanTerakhir == "S2") rdS2.isChecked = true else rdS3.isChecked = true
            etBidangKeahlian.setText(bidangKeahlian)
            etProgramStudi.setText(programStudi)
        }
        etNIDN.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if ("${etNIDN.text}".isNotEmpty() && "${etNmDosen.text}".isNotEmpty() &&
                (rdS2.isChecked || rdS3.isChecked)) {
                val db = campuss(this@EntriDosenActivity)
                db.NIDN = "${etNIDN.text}"
                db.nmDosen= "${etNmDosen.text}"
                db.Jabatan = spnJabatan.selectedItem as String
                db.GolonganPangkat = spnGolongan.selectedItem as String
                db.PendidikanTerakhir = if (rdS2.isChecked) "S2" else "S3"
                db.BidangKeahlian = "${etBidangKeahlian.text}"
                db.ProgramStudi = "${etProgramStudi.text}"
                if (if (!modeEdit) db.simpan() else db.ubah("${etNIDN.text}")) {
                    Toast.makeText(
                        this@EntriDosenActivity,
                        "Data Dosen berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else
                    Toast.makeText(
                        this@EntriDosenActivity,
                        "Data Dosen gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }
}