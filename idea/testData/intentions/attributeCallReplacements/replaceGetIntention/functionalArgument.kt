// IS_APPLICABLE: false
fun test() {
    class Test{
        fun get(fn: (i: Int) -> Int) : Int = 0
    }
    val test = Test()
    test.g<caret>et() { i -> i }
}
