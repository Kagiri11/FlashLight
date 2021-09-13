package com.example.flashlight
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {


  private var _flashLightState = MutableLiveData<Boolean>()
  val flashLightState:LiveData<Boolean> = _flashLightState

  init {
      _flashLightState.value=false
  }

  fun changeFlashLightState(){
    when(_flashLightState.value){
      true -> _flashLightState.value=false
      false -> _flashLightState.value=true
    }

  }

  override fun onCleared() {
    super.onCleared()
    _flashLightState.value=false
  }


}