package main.encrypt

import kotlin.text.*

open class encrypt{
    open fun encryption(string: input_msg): String{
        // Get hash
        val hashstring = input_msg.hashCode()
        return hashstring
    }

}