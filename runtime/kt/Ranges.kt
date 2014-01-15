// Generated by org.jetbrains.jet.generators.runtime.ranges.GenerateRanges

package jet

public class ByteRange(public override val start: Byte, public override val end: Byte) : Range<Byte>, Progression<Byte> {
    override val increment: Int
        get() = 1

    override fun contains(item: Byte) = start <= item && item <= end

    override fun iterator(): ByteIterator = ByteProgressionIterator(start, end, 1)

    fun equals(o: Any?): Boolean {
        val that = o as? ByteRange ?: return false
        return start == that.start && end == that.end
    }

    fun hashCode() = 31 * start.toInt() + end

    fun toString() = "$start..$end"

    class object {
        public val EMPTY: ByteRange = ByteRange(1, 0)
    }
}

public class CharRange(public override val start: Char, public override val end: Char) : Range<Char>, Progression<Char> {
    override val increment: Int
        get() = 1

    override fun contains(item: Char) = start <= item && item <= end

    override fun iterator(): CharIterator = CharProgressionIterator(start, end, 1)

    fun equals(o: Any?): Boolean {
        val that = o as? CharRange ?: return false
        return start == that.start && end == that.end
    }

    fun hashCode() = 31 * start.toInt() + end

    fun toString() = "$start..$end"

    class object {
        public val EMPTY: CharRange = CharRange(1.toChar(), 0.toChar())
    }
}

public class ShortRange(public override val start: Short, public override val end: Short) : Range<Short>, Progression<Short> {
    override val increment: Int
        get() = 1

    override fun contains(item: Short) = start <= item && item <= end

    override fun iterator(): ShortIterator = ShortProgressionIterator(start, end, 1)

    fun equals(o: Any?): Boolean {
        val that = o as? ShortRange ?: return false
        return start == that.start && end == that.end
    }

    fun hashCode() = 31 * start.toInt() + end

    fun toString() = "$start..$end"

    class object {
        public val EMPTY: ShortRange = ShortRange(1, 0)
    }
}

public class IntRange(public override val start: Int, public override val end: Int) : Range<Int>, Progression<Int> {
    override val increment: Int
        get() = 1

    override fun contains(item: Int) = start <= item && item <= end

    override fun iterator(): IntIterator = IntProgressionIterator(start, end, 1)

    fun equals(o: Any?): Boolean {
        val that = o as? IntRange ?: return false
        return start == that.start && end == that.end
    }

    fun hashCode() = 31 * start + end

    fun toString() = "$start..$end"

    class object {
        public val EMPTY: IntRange = IntRange(1, 0)
    }
}

public class LongRange(public override val start: Long, public override val end: Long) : Range<Long>, Progression<Long> {
    override val increment: Long
        get() = 1

    override fun contains(item: Long) = start <= item && item <= end

    override fun iterator(): LongIterator = LongProgressionIterator(start, end, 1)

    fun equals(o: Any?): Boolean {
        val that = o as? LongRange ?: return false
        return start == that.start && end == that.end
    }

    fun hashCode() = (31 * (start xor (start ushr 32)) + (end xor (end ushr 32))).toInt()

    fun toString() = "$start..$end"

    class object {
        public val EMPTY: LongRange = LongRange(1, 0)
    }
}

public class FloatRange(public override val start: Float, public override val end: Float) : Range<Float>, Progression<Float> {
    override val increment: Float
        get() = 1.0f

    override fun contains(item: Float) = start <= item && item <= end

    override fun iterator(): FloatIterator = FloatProgressionIterator(start, end, 1.0f)

    fun equals(o: Any?): Boolean {
        val that = o as? FloatRange ?: return false
        return java.lang.Float.compare(start, that.start) == 0 && java.lang.Float.compare(end, that.end) == 0
    }

    fun hashCode() = 31 * java.lang.Float.floatToIntBits(start) + java.lang.Float.floatToIntBits(end)

    fun toString() = "$start..$end"

    class object {
        public val EMPTY: FloatRange = FloatRange(1.0f, 0.0f)
    }
}

public class DoubleRange(public override val start: Double, public override val end: Double) : Range<Double>, Progression<Double> {
    override val increment: Double
        get() = 1.0

    override fun contains(item: Double) = start <= item && item <= end

    override fun iterator(): DoubleIterator = DoubleProgressionIterator(start, end, 1.0)

    fun equals(o: Any?): Boolean {
        val that = o as? DoubleRange ?: return false
        return java.lang.Double.compare(start, that.start) == 0 && java.lang.Double.compare(end, that.end) == 0
    }

    fun hashCode(): Int {
        var temp = java.lang.Double.doubleToLongBits(start)
        val result = (temp xor (temp ushr 32))
        temp = java.lang.Double.doubleToLongBits(end)
        return (31 * result + (temp xor (temp ushr 32))).toInt()
    }

    fun toString() = "$start..$end"

    class object {
        public val EMPTY: DoubleRange = DoubleRange(1.0, 0.0)
    }
}

