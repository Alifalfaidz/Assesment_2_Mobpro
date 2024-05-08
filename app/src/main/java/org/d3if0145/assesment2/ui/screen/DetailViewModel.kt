package org.d3if0145.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0145.assesment2.database.ParfumDao
import org.d3if0145.assesment2.model.Parfum
import java.text.SimpleDateFormat
import java.util.Locale

class DetailViewModel(private val dao: ParfumDao) : ViewModel() {
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(namaParfum: String, brandParfum: String, genderParfum: String){
        val parfum = Parfum(
            namaParfum = namaParfum,
            brandParfum = brandParfum,
            genderParfum = genderParfum
        )
        viewModelScope.launch(Dispatchers.IO){
            dao.insert(parfum)
        }
    }
    suspend fun getParfum(id: Long): Parfum? {
        return dao.getParfumById(id)
    }

    fun update(id: Long, namaParfum: String, brandParfum: String, genderParfum: String){
        val parfum = Parfum(
            id = id,
            namaParfum = namaParfum,
            brandParfum =  brandParfum,
            genderParfum  = genderParfum
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(parfum)
        }

    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }


}