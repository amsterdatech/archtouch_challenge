package br.com.flyingdutchman.archtouch_challenge.data

class WordRepository {
    private val list = listOf("Pera", "Uva", "Banana", "Laranja", "Morango", "Caju")
    fun fetchWords(): List<String> = list
}