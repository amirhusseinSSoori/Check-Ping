package com.amirhusseinsoori.pingwithflow

fun String.handlePing(): Boolean = Runtime.getRuntime().exec(this).waitFor() == 0