package br.com.flyingdutchman.archtouch_challenge.domain

object GameState {

    private var gameScore: Int = 0
    private lateinit var wordResults: List<String>
    private val wordAlreadyGuessed: MutableList<String> = mutableListOf()

    fun answers(words: List<String>) {
        wordResults = words
    }

    fun currentScore(): Int =
        gameScore

    fun reset() {
        gameScore = 0
        wordAlreadyGuessed.clear()
    }

    fun hasWon(): Boolean = (gameScore == wordResults.size - 1)

    fun checkWord(guess: String): Boolean {
        if (wordResults.contains(guess) && !wordAlreadyGuessed.contains(guess)) {
            addPoint()
            wordAlreadyGuessed.add(guess)
            return true
        }
        return false
    }

    fun totalScore(): Int = wordResults.size - 1

    private fun addPoint() {
        ++gameScore
    }
}