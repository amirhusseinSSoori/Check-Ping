package com.amirhusseinsoori.pingwithflow

object Di {
    fun injectConnection():SolutionType{
      return  NetworkConnecting()
    }
}