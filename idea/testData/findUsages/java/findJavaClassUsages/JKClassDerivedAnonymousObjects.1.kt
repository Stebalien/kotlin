fun foo() {
    public trait T: A

    val a = object: A() {
        fun zoo() {
            val c = object: T
        }
    }

    fun bar() {
        val b = object: T
    }
}
