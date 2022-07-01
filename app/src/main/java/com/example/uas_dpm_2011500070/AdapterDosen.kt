package com.example.uas_dpm_2011500070

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.media.Image
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class AdapterDosen(
    private val getContext: Context,
    private val customListItem: ArrayList<Dosen>
) : ArrayAdapter<Dosen>(getContext, 0, customListItem){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val inflateList = (getContext as Activity).layoutInflater
            listLayout = inflateList.inflate(R.layout.layout_item, parent, false)
            holder = ViewHolder()
            with(holder){
                tvNmDosen = listLayout.findViewById(R.id.tvNmDosen)
                tvNIDN = listLayout.findViewById(R.id.tvNIDN)
                tvProgramStudi = listLayout.findViewById(R.id.tvProgramStudi)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        } else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNmDosen!!.setText(listItem.nmDosen)
        holder.tvNIDN!!.setText(listItem.nidn)
        holder.tvProgramStudi!!.setText(listItem.program)

        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context, EntriDosenActivity::class.java)
            i.putExtra("kode", listItem.nidn)
            i.putExtra("nama", listItem.nmDosen)
            i.putExtra("Jabatan", listItem.Jabatan)
            i.putExtra("golongan_pangkat", listItem.golongan)
            i.putExtra("Pendidikan", listItem.pendidikan)
            i.putExtra("Keahlian", listItem.keahlian)
            i.putExtra("Program_studi", listItem.program)
            context.startActivity(i)
        }

        holder.btnHapus!!.setOnClickListener{
            val db = campuss(context)
            val alb = AlertDialog.Builder(context)
            val kode = holder.tvNIDN!!.text
            val nama = holder.tvNmDosen!!.text
            val program = holder.tvProgramStudi!!.text
            with(alb) {
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                    Apakah Anda yakin akan menghapus data 
                    ini?
                    
                                    $nama 
                                    [$kode-$program]
                """.trimIndent())
                setPositiveButton("Ya") { _, _ ->
                    if (db.hapus("$kode"))
                        Toast.makeText(
                            context,
                            "Data Dosen berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data Dosen gagal dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }

        return listLayout!!
    }

    class ViewHolder {
        internal var tvNmDosen: TextView? = null
        internal var tvNIDN: TextView? = null
        internal var tvProgramStudi: TextView? = null
        internal var btnEdit: ImageButton? = null
        internal var btnHapus: ImageButton? = null
    }
}