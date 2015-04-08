package cqu.cqumonk.androidcode.prime;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by CQUMonk on 2015/4/7.
 */
public class PrimePresenter implements OnCalculateFinished {
    private IPrimeView primeView;
    private PrimeInteractor primeInteractor;
    public PrimePresenter(IPrimeView view){
        this.primeView=view;
        this.primeInteractor =new PrimeInteractor();
    }
    public void calculatePrime(String input){
        if (TextUtils.isEmpty(input)){
            primeView.showNumError("输入不能为空");
            return;
        }
        int num=Integer.parseInt(input);
        if (num<0){
            primeView.showNumError("数字不能为负");
        }else {
            this.primeInteractor.calculatePrimes(num,this);
        }

    }


    @Override
    public void onSucess(List<Integer> nums) {
        primeView.showResult(nums.toString());
    }


}
