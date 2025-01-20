package com.ozodbek.recorderapp_jetpackcompose.domain.player.model

enum class PlayerState {

	IDLE,
	PLAYER_READY,
	COMPLETED;

	val canAdvertiseCurrentPosition: Boolean
		get() = this == PLAYER_READY

}