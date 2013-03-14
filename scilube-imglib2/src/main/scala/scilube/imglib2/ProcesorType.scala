package scilube.imglib2

import ij.process.{ByteProcessor, ColorProcessor, FloatProcessor, ShortProcessor, ImageProcessor}


/**
 * Enumeration of the different types of ImageJ [[ij.process.ImageProcessor]]s. You can get the
 * correct type using:
 * {{{
 * val imageProcessor: ImageProcessor = // ...
 * val processorType = ProcessorType.get(imageProcessor)
 * }}}
 *
 * @author Brian Schlining
 * @since 2012-03-14
 */
object ProcessorType extends Enumeration {

    val ByteType = Value("byte", 1, 255, 0)
    val ShortType = Value("short", 1, 65535, 0)
    val FloatType = Value("float", 1, Double.MaxValue, 0)
    val ColorType = Value("color", 3, 255, 0)
    val UnknownType = Value("unknown", 0, Double.NaN, Double.NaN)


    /**
     *
     * @param name The name of the type
     * @param channels The number of channels that this type should have
     * @param maxValue The maximum value for a pixel
     * @param minValue The default ''black'' or background value
     */
    class TypeVal(name: String, val channels: Int, val maxValue: Double, val minValue: Double)
            extends Val(nextId, name)

    protected final def Value(name: String, channels: Int, maxValue: Double, minValue: Double) =
        new TypeVal(name, channels, maxValue, minValue)

    def get(imageProcessor: ImageProcessor) = if (imageProcessor.isInstanceOf[ByteProcessor]) {
        ByteType
    }
    else if (imageProcessor.isInstanceOf[ShortProcessor]) {
        ShortType
    }
    else if (imageProcessor.isInstanceOf[FloatProcessor]) {
        FloatType
    }
    else if (imageProcessor.isInstanceOf[ColorProcessor]) {
        ColorType
    }
    else {
        UnknownType
    }

}
