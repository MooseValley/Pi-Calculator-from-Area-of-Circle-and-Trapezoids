/*
   Author: Mike O'Malley
   Description: PiCalculatorFromAreaCircle
   My solution for: Q26 - Chapter 9, p325.

   Structured Fortran 77 for Engineers and Scientists,
   D. M. Etter.
   Q26 - Chapter 9, p325.



A float gives you approx. 6-7 decimal digits precision while a double gives you approx. 15-16.
Also the range of numbers is larger for double.
A double needs 8 bytes of storage space while a float needs just 4 bytes.
float  = 32 bit
double = 64 bit


Question:

Write a program to compute PI using double precision (or better) variables.
The algorithm to be used should compute the area of a quarter circle with
a radius of 1.0 and multiply that area by 4.0 to get an approximation of PI.
To calculate the area of a quarter cicrle, you need to sum the areas of
subsections (slices) of the quarter circle.
The area of a subsection is approximately equal to the area of a trapizoid.


|.
|    ._____Y2
|    |\ .__Y1
|    | |  .
|    | |    .
|    | |      .
|    | |       .
|    | |        .
|    | |        .
|    | |        .
--------------------
     Xi

The diagram above shows a single slice.

i = current slice number (1, 2, 3, 4, ....)
DX = R / NumSlices
Xi = R - i x DX
Y1 = (R^2 - Xi^2)^0.5
Y2 = (R^2 - (Xi + DX)^2)^0.5

Area of a subsection (slice) = SUB = (Y2 + Y1) / 2.0 * DX

Area Quarter Circle = AREAQ = Sum SUB for each slice

Area circle = PI = AreaQ x 4.0
(because Radius = 1.0)
Area Circle = PI * R^2 = PI     (because R = 1.0)

For this question, use 2000 slices.

Compare your calculated value of PI against the real value
(PI = 3.14159265358979) and calculate and display the error%:

errorPct = (PIactual - PIcalc) / PIactual * 100.0;


*** Follow on Question(s) by Mike O

Vary the number of slices from 1000.0 up to 10,000.0 in steps of 1,000
and display the value of PI calculated with each number of slices and
the error% in the calculated PI value.

Vary the number of slices from 1.0 and double the number of slices each
iteration and keep looping while the number of slices is less than 1 million.
Display the value of PI calculated with each number of slices and
the error% in the calculated PI value.

*/

/*
Sample Output:

 Slices        PI              Error%
-------  ------------------  ---------
  1,000  3.1415554669110293  0.001184%
  2,000  3.1415795059119660  0.000419%
  3,000  3.1415854968639048  0.000228%
  4,000  3.1415880051481100  0.000148%
  5,000  3.1415893274305806  0.000106%
  6,000  3.1415901232921670  0.000081%
  7,000  3.1415906456451292  0.000064%
  8,000  3.1415910101111777  0.000052%
  9,000  3.1415912762678686  0.000044%
 10,000  3.1415914776113350  0.000037%



 Slices          PI                   Error%
---------  ------------------  --------------------
        1  2.0000000000000000  36.3380227632418000%
        2  2.7320508075688770  13.0361218394416410%
        4  2.9957090681024403  4.6436187492561630%
        8  3.0898191443571736  1.6480019831169570%
       16  3.1232530378277410  0.5837681005872281%
       32  3.1351024228771310  0.2065904599453058%
       64  3.1392969127796837  0.0730756995972422%
      128  3.1407807923966145  0.0258423443996748%
      256  3.1413055829572305  0.0091377420376725%
      512  3.1414911527196523  0.0032308730421092%
    1,024  3.1415567665390185  0.0011423203046551%
    2,048  3.1415799654114442  0.0004038772605120%
    4,096  3.1415881676077464  0.0001427932433725%
    8,192  3.1415910675497100  0.0000504852237398%
   16,384  3.1415920928388950  0.0000178492553581%
   32,768  3.1415924553342056  0.0000063106712504%
   65,536  3.1415925834958456  0.0000022311595473%
  131,072  3.1415926288077944  0.0000007888354188%
  262,144  3.1415926448280640  0.0000002788944009%
  524,288  3.1415926504921003  0.0000000986025243%

*/

import java.math.BigDecimal;


public class Ch09_Q26__PiCalculatorFromAreaCircle
{
   // Constants:

   public static double CalculatePI (int NumSlices)
   {
      double radius = 1.0;
      double incX = radius / NumSlices;
      double X1 = 0.0;
      double X2 = 0.0;
      double Y1 = 0.0;
      double Y2 = 0.0;
      double areaSlice = 0.0;
      double PI = 0.0;
      double areaQuarterCircle = 0.0;
      double areaCircle = 0.0;

      for (int k = 1; k <= NumSlices; k++)
      {
         X1 = radius - k * incX;
         X2 = X1 + incX;
         Y1 = Math.sqrt (radius * radius - X1 * X1);
         Y2 = Math.sqrt (radius * radius - X2 * X2);

         areaSlice = ((Y1 + Y2) / 2.0) * incX;

         areaQuarterCircle = areaQuarterCircle + areaSlice;
      }

      areaCircle = 4 * areaQuarterCircle;

      // areaCircle = PI * R * R
      // When R = 1, areaCircle = PI.

      return areaCircle; // PI
   }


   // REF: http://stackoverflow.com/questions/13649703/square-root-of-bigdecimal-in-java
   public static BigDecimal sqrt(BigDecimal A, final int SCALE)
   {
      BigDecimal x0 = new BigDecimal("0");
      BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
      BigDecimal TWO = new BigDecimal ("2.0");

      while (!x0.equals(x1))
      {
         x0 = x1;
         x1 = A.divide(x0,   SCALE, BigDecimal.ROUND_HALF_UP);
         x1 = x1.add(x0);
         x1 = x1.divide(TWO, SCALE, BigDecimal.ROUND_HALF_UP);
      }
      return x1;
   }

   public static BigDecimal CalculatePIBigDecimal (int NumSlices)
   {
      BigDecimal radius            = new BigDecimal ("1.0");
      BigDecimal incX              = radius.divide (new BigDecimal ("" + NumSlices));
      BigDecimal X1                = new BigDecimal ("0.0");
      BigDecimal X2                = new BigDecimal ("0.0");
      BigDecimal Y1                = new BigDecimal ("0.0");
      BigDecimal Y2                = new BigDecimal ("0.0");
      BigDecimal areaSlice         = new BigDecimal ("0.0");
      BigDecimal PI                = new BigDecimal ("0.0");
      BigDecimal areaQuarterCircle = new BigDecimal ("0.0");
      BigDecimal areaCircle        = new BigDecimal ("0.0");
      BigDecimal kBigD             = new BigDecimal ("0.0");
      BigDecimal radiusSquared     = new BigDecimal ("0.0");
      BigDecimal X1Squared         = new BigDecimal ("0.0");
      BigDecimal X2Squared         = new BigDecimal ("0.0");
      BigDecimal Y1PlusY2          = new BigDecimal ("0.0");
      BigDecimal Y1PlusY2Div2      = new BigDecimal ("0.0");
      //int        Scale             = 65536;
      int        Scale             = 1000;
      BigDecimal TWO               = new BigDecimal ("2.0");
      BigDecimal FOUR              = new BigDecimal ("4.0");

      for (int k = 1; k <= NumSlices; k++)
      {
         //X1 = radius - k * incX;
         //X2 = X1 + incX;

         kBigD = new BigDecimal("" + k);

         X1 = radius.subtract (kBigD.multiply(incX));
         X2 = X1.add (incX);

         //Y1 = Math.sqrt (radius * radius - X1 * X1);
         //Y2 = Math.sqrt (radius * radius - X2 * X2);

         radiusSquared = radius.multiply (radius);
         X1Squared     = X1.multiply (X1);
         X2Squared     = X2.multiply (X2);

         Y1 = sqrt (radiusSquared.subtract(X1Squared), Scale);
         Y1 = sqrt (radiusSquared.subtract(X2Squared), Scale);

         //areaSlice = ((Y1 + Y2) / 2.0) * incX;
         //areaSlice = (Y1.add (Y2).divide (new BigDecimal ("2.0").multiply (incX)));
         Y1PlusY2     = Y1.add (Y2);
         Y1PlusY2Div2 = Y1PlusY2.divide (TWO);

         areaSlice = Y1PlusY2Div2.multiply (incX);

         // areaQuarterCircle = areaQuarterCircle + areaSlice;
         areaQuarterCircle = areaQuarterCircle.add (areaSlice);
      }

      areaCircle = areaQuarterCircle.multiply (FOUR);

      // areaCircle = PI * R * R
      // When R = 1, areaCircle = PI.

      return areaCircle; // PI
   }


   public static void main (String[] args)
   {
      System.out.println (" Slices          PI                   Error%       ");
      System.out.println ("---------  ------------------  --------------------");

      double PIactual = 3.14159265358979;
      double PI = 0.0;
      double errorPct;

      //for (int slices = 1000; slices <= 10000; slices = slices + 1000)
      for (int slices = 1; slices <= 1000000; slices = slices * 2)
      {
         PI = CalculatePI (slices);

         errorPct = (PIactual - PI) / PIactual * 100.0;

         System.out.println (String.format ("%,9d",   slices) + "  " +
                             String.format ("%,.16f", PI)     + "  " +
                             String.format ("%,.16f",  errorPct) + "%");

      }

      System.exit (0);

      for (int slices = 1; slices <= 1000000; slices = slices * 2)
      {
         PI = CalculatePIBigDecimal (slices).doubleValue();

         errorPct = (PIactual - PI) / PIactual * 100.0;

         System.out.println (String.format ("%,9d",   slices) + "  " +
                             String.format ("%,.16f", PI)     + "  " +
                             String.format ("%,.16f",  errorPct) + "%");

      }



   }
} // public class Ch09_Q26__PiCalculatorFromAreaCircle
