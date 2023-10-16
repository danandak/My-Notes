package com.danan.uts.data

import android.content.Context
import com.danan.uts.model.MataKuliah

class MatkulDataSource {
    fun loadData(context : Context) : MutableList<MataKuliah> {
        var listMatkul = mutableListOf<MataKuliah>()

        for(i in 1..15) {
            val matkulId = context.resources.getIdentifier("matkul_" + i, "string", context.packageName)
            val sksId = context.resources.getIdentifier("sks_" + i, "string", context.packageName)
            val logoId = context.resources.getIdentifier("img_" + i, "drawable", context.packageName)

            listMatkul.add(MataKuliah(matkulId, sksId, logoId))
        }

        return listMatkul
    }
}