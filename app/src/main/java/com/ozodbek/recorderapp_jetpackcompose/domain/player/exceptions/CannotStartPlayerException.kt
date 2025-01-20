package com.ozodbek.recorderapp_jetpackcompose.domain.player.exceptions

class CannotStartPlayerException :
	Exception("Cannot configure as some other thread maybe preparing the player")