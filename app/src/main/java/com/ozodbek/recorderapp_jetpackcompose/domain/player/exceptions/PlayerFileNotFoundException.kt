package com.ozodbek.recorderapp_jetpackcompose.domain.player.exceptions

class PlayerFileNotFoundException :
	Exception("Queried audio file Id is not found, please verify the source")