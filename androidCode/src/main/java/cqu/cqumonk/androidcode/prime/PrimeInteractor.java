package cqu.cqumonk.androidcode.prime;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CQUMonk on 2015/4/7.
 */
public class PrimeInteractor {

    private Handler mHandler;
    public void calculatePrimes(final int upper, final OnCalculateFinished listener){

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Integer> nums=new ArrayList<Integer>();
                for(int i=2;i<=upper;++i){
                    if(isPrime(i)){
                        nums.add(i);
                    }
                }
                listener.onSucess(nums);
            }
        }).start();




    }
    //计算质数
    private boolean isPrime(int num){
        if(num<=1) return false;
        int sqrt=(int) Math.sqrt(num);
        for(int i=2;i<=sqrt;++i){
            if(num%i==0){
                return false;
            }
        }

        return true;

    }

}
