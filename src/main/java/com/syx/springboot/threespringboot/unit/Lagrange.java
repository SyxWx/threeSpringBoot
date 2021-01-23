package com.syx.springboot.threespringboot.unit;

import java.math.BigDecimal;

public class Lagrange {
    public static double[] BELIE(double x[], double y[], double x0[]) {

        int m = x.length;
        int n = x0.length;
        double y0[] = new double[n];

        for(int yi = 0;yi < n;yi ++) {
            try {
                double j = 0,k = 0,l = 0;
                int ic = 0;
                for(int ia = 0;ia < m;ia ++) {
                    double w = 1;
                    for(int ib = 0;ib < m;ib ++) {
                        if(ia != ib) w /= (x[ia] - x[ib]);
                    }
                    k += (w / ((x0[yi] - x[ic]))) * y[ic];
                    l += (w / ((x0[yi] - x[ic])));
                    ic ++ ;
                    j = k / l;
                }
                System.out.println("j的原始数据"+j );
                BigDecimal b   =   new   BigDecimal(j );

                j   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();

                System.out.println("j的最终返回值"+j );
                y0[yi] = j;
            }catch (Exception e){
                e.getMessage();
                y0[yi] =0;
            }
        }
        return y0;
    }
}
