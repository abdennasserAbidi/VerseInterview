package com.example.verseinterview.base

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {

    private lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
// "http://10.0.2.2:3000" is the network your Android emulator must use to join the localhost network on your computer
// "http://localhost:3000/" will not work
// If you want to use your physical phone you could use the your ip address plus :3000
// This will allow your Android Emulator and physical device at your home to connect to the server
            mSocket = IO.socket("http://10.0.2.2:3000")
        } catch (e: URISyntaxException) {

        }
    }

    @Synchronized
    fun getSocket(): Socket = mSocket

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }

    fun sendData(key: String) {
        mSocket.emit(key)
    }

    fun receiveData(key: String, receiveDataHandler: (data: String)-> Unit) {
        mSocket.on(key) { args ->
            if (args[0] != null) {
                val urlVideo = args[0] as String
                receiveDataHandler(urlVideo)
            }
        }
    }
}