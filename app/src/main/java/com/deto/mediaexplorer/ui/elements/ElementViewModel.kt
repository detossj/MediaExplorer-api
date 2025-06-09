package com.deto.mediaexplorer.ui.elements

import androidx.lifecycle.ViewModel
import com.deto.mediaexplorer.data.Element
import com.deto.mediaexplorer.data.ElementRepository
import kotlinx.coroutines.flow.Flow

class ElementViewModel(private val elementRepository: ElementRepository) : ViewModel() {

    fun getElement(id : Int): Flow<Element> {
        return elementRepository.getElementStream(id)
    }
}