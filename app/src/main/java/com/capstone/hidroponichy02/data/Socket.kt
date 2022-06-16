package com.capstone.hidroponichy02.data


import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object Socket {
    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket(){
        try{
            mSocket = IO.socket("https://floating-falls-99674.herokuapp.com/")
        }catch (e: URISyntaxException){

        }
    }
    @Synchronized
    fun getSocket(): Socket{
        return mSocket
    }
    @Synchronized
    fun establishConnection() {
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}