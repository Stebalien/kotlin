import junit.framework.TestCase

class Test1: TestCase() {
    fun test1() {}
}

class Test2: junit.framework.TestCase() {
    fun test2() {}
}

// CLASS: junit.framework.TestCase
// SEARCH: KotlinLightClass:Test1, KotlinLightClass:Test2