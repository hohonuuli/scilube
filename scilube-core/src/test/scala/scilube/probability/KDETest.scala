package scilube.probability

import org.junit.Test
import org.junit.Assert._

/**
 *
 * @author Brian Schlining
 * @since 2012-06-12
 */
class KDETest {

    private[this] val tolerance = 0.00000000001

    private val expected = {

        val pdf = Array(0.088840244039276,
            0.088978282080010,
            0.089244699538477,
            0.089620685886177,
            0.090079261979980,
            0.090586681900934,
            0.091104171747251,
            0.091589906180539,
            0.092001112979546,
            0.092296192841760,
            0.092436746007551,
            0.092389408180454,
            0.092127414404379,
            0.091631829420033,
            0.090892404786313,
            0.089908044970800,
            0.088686885130398,
            0.087246001183325,
            0.085610787184176,
            0.083814045552500,
            0.081894842376200,
            0.079897183155971,
            0.077868564557172,
            0.075858455698778,
            0.073916758959922,
            0.072092295871935,
            0.070431358896827,
            0.068976365110149,
            0.067764643176828,
            0.066827380562699,
            0.066188753598900,
            0.065865258707407)

        val x = Array(0D,
            0.387096774193548,
            0.774193548387097,
            1.161290322580645,
            1.548387096774194,
            1.935483870967742,
            2.322580645161290,
            2.709677419354839,
            3.096774193548387,
            3.483870967741935,
            3.870967741935484,
            4.258064516129032,
            4.645161290322580,
            5.032258064516129,
            5.419354838709677,
            5.806451612903226,
            6.193548387096774,
            6.580645161290323,
            6.967741935483871,
            7.354838709677420,
            7.741935483870968,
            8.129032258064516,
            8.516129032258064,
            8.903225806451612,
            9.290322580645162,
            9.677419354838710,
            10.064516129032258,
            10.451612903225806,
            10.838709677419356,
            11.225806451612904,
            11.612903225806452,
            12.000000000000000)

        val bandwidth = 3.930807035414038

        val cdf = Array(0.033306695281050,
            0.066603486574857,
            0.099880557784425,
            0.133128268034729,
            0.166337237881724,
            0.199498433823517,
            0.232603250523444,
            0.265643590136667,
            0.298611938112687,
            0.331501434827647,
            0.364305942384866,
            0.397020105911842,
            0.429639408679354,
            0.462160220375400,
            0.494579837885496,
            0.526896517962952,
            0.559109501219372,
            0.591219026927531,
            0.623226338206249,
            0.655133677249527,
            0.686944270369182,
            0.718662302740056,
            0.750292882867517,
            0.781841996936115,
            0.813316453342791,
            0.844723817864960,
            0.876072340059591,
            0.907370871630481,
            0.938628777633903,
            0.969855841514086,
            1.001062165066539,
            1.032258064516129)

        new KDEResult(bandwidth, x, pdf, cdf)
    }

    private val data = Array[Double](1, 2, 3, 3,4, 4, 4, 4, 4, 5, 5, 5, 6, 7, 7, 8, 8, 9, 9, 10, 11)

    @Test
    def test() {
        val actual = KDE(data, 32)

        // Check bandwidth
        //assertEquals(expected.bandwidth, actual.bandwidth, tolerance)

        // Check xmesh
        assertEquals(expected.x.size, actual.x.size)
        for (i <- 0 until expected.x.size) {
            assertEquals(expected.x(i), actual.x(i), tolerance)
        }

        // Check PDF
        assertEquals(expected.pdf.size, actual.pdf.size)
        for (i <- 0 until expected.pdf.size) {
            assertEquals(expected.pdf(i), actual.pdf(i), tolerance)
        }

        // Check CDF
        assertEquals(expected.cdf.size, actual.cdf.size)
        for (i <- 0 until expected.cdf.size) {
            assertEquals(expected.cdf(i), actual.cdf(i), tolerance)
        }
    }


    @Test
    def testIdct1() {
        val data = Array( 1.000000000000000,
            0.140828744407576,
            -0.072015056576345,
            -0.002840227328726,
            -0.000100635132201,
            0.000000297031173,
            0.000000002436588,
            0.000000000000917,
            -0.000000000000001,
            -0.000000000000000,
            0.000000000000000,
            0.000000000000000,
            -0.000000000000000,
            0.000000000000000,
            -0.000000000000000,
            -0.000000000000000,
            0.000000000000000,
            0.000000000000000,
            0.000000000000000,
            0.000000000000000,
            0.000000000000000,
            -0.000000000000000,
            -0.000000000000000,
            -0.000000000000000,
            0.000000000000000,
            0.000000000000000,
            -0.000000000000000,
            -0.000000000000000,
            -0.000000000000000,
            -0.000000000000000,
            0.000000000000000,
            0.000000000000000)

        val expected = Array(1.066082928471318,
            1.067739384960115,
            1.070936394461719,
            1.075448230634126,
            1.080951143759759,
            1.087040182811203,
            1.093250060967015,
            1.099078874166463,
            1.104013355754550,
            1.107554314101118,
            1.109240952090610,
            1.108672898165447,
            1.105528972852552,
            1.099581953040397,
            1.090708857435762,
            1.078896539649602,
            1.064242621564774,
            1.046952014199900,
            1.027329446210112,
            1.005768546630001,
            0.982738108514402,
            0.958766197871651,
            0.934422774686060,
            0.910301468385335,
            0.887001107519065,
            0.865107550463226,
            0.845176306761924,
            0.827716381321782,
            0.813175718121935,
            0.801928566752387,
            0.794265043186802,
            0.790383104488888)

        val actual = KDE.idct1d(data)
        assertEquals(expected.size, actual.size)
        for (i <- 0 until expected.size) {
            assertEquals("Failed at idx = " + i, expected(i), actual(i), tolerance)
        }

    }

    @Test
    def testDct1() {
        val data = Array(0,
            0,
            0.047619047619048,
            0,
            0,
            0.047619047619048,
            0,
            0.095238095238095,
            0,
            0,
            0.238095238095238,
            0,
            0.142857142857143,
            0,
            0,
            0.047619047619048,
            0,
            0,
            0.095238095238095,
            0,
            0.095238095238095,
            0,
            0,
            0.095238095238095,
            0,
            0.047619047619048,
            0,
            0,
            0.047619047619048,
            0,
            0,
            0)

        val expected = Array(1.000000000000000,
            0.239140287271441,
            -0.598782451059319,
            -0.333431062399615,
            -0.480986238908456,
            0.166662546946371,
            0.462800664725046,
            0.169956483147541,
            -0.379556856326858,
            -0.342307695801268,
            0.080300076667175,
            0.322677612185143,
            -0.044581251402002,
            0.085480002633302,
            -0.385288935714309,
            -0.533592005731044,
            0.067343502970147,
            0.034275169449930,
            0.205782314300988,
            0.149299110144992,
            0.037308841895346,
            -0.271875015509457,
            -0.263476512110051,
            -0.562147922411950,
            0.667462174069067,
            1.462956357674135,
            -0.141988780415603,
            -0.204942112375843,
            -0.427207631499322,
            -0.522335389522029,
            0.346474016911608,
            0.619768448208295)


        val actual = KDE.dct1d(data)
        assertEquals(expected.size, actual.size)
        for (i <- 0 until expected.size) {
            assertEquals("Failed at idx = " + i, expected(i), actual(i), tolerance)
        }

    }

    @Test
    def testFixedPoint() {
        val t = 0
        val N = 11
        val I = Array[Double](1,
             4,
             9,
            16,
            25,
            36,
            49,
            64,
            81,
           100,
           121,
           144,
           169,
           196,
           225,
           256,
           289,
           324,
           361,
           400,
           441,
           484,
           529,
           576,
           625,
           676,
           729,
           784,
           841,
           900,
           961)
        val a2 = Array(0.014297019249067,
           0.089635105924151,
           0.027794068343234,
           0.057836940504826,
           0.006944101138663,
           0.053546113817486,
           0.007221301540970,
           0.036015851796182,
           0.029293639651193,
           0.001612025578189,
           0.026030210351376,
           0.000496871994142,
           0.001826707712547,
           0.037111890995966,
           0.071180107145020,
           0.001133786848073,
           0.000293696810205,
           0.010586590219768,
           0.005572556072522,
           0.000347987420893,
           0.018479006014567,
           0.017354968108419,
           0.079002571668018,
           0.111376438453251,
           0.535060326114793,
           0.005040203440978,
           0.010500317356268,
           0.045626590102815,
           0.068208564786782,
           0.030011061098716,
           0.096028232348630)

        val expected = -0.001594243046717

        val actual = KDE.fixedPoint(t, N, I, a2)
        assertEquals("fixedPoint method failed", expected, actual, tolerance)
    }

}