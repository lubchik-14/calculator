package ml.lubster.calculator.stackCalculator;

import java.util.Arrays;

public class CalcTest {
    public static void main(String[] args) {
        System.out.println("5*(70+5555)+251");
        System.out.println(Arrays.toString("5*(70+5555)+251".split("(?<=[-+()*/])|(?=[-+()*/])")));
        System.out.println(Calculator.expressionToRpn("5*(70+5555)+251"));
        System.out.println(Calculator.RpnToResult(Calculator.expressionToRpn("5*(70+5555)+251")));
        System.out.println("----------------------------------------");
        System.out.println("2+2*2");
        System.out.println(Arrays.toString("2+2*2".split("(?<=[-+()*/])|(?=[-+()*/])")));
        System.out.println(Calculator.expressionToRpn("2+2*2"));
        System.out.println(Calculator.RpnToResult(Calculator.expressionToRpn("2+2*2")));
        System.out.println("----------------------------------------");
        System.out.println("(2+2)*2");
        System.out.println(Arrays.toString("(2+2)*2".split("(?<=[-+()*/])|(?=[-+()*/])")));
        System.out.println(Calculator.expressionToRpn("(2+2)*2"));
        System.out.println(Calculator.RpnToResult(Calculator.expressionToRpn("(2+2)*2")));
        System.out.println("----------------------------------------");
        System.out.println("(2222+22)*222");
        System.out.println(Arrays.toString("(2222+22)*222".split("(?<=[-+()*/])|(?=[-+()*/])")));
        System.out.println(Calculator.expressionToRpn("(2222+22)*222"));
        System.out.println(Calculator.RpnToResult(Calculator.expressionToRpn("(2222+22)*222")));
        System.out.println("----------------------------------------");
        System.out.println("4-5*(-2)");
        System.out.println(Arrays.toString("4-5*(-2)".split("(?<=[-+()*/])|(?=[-+()*/])")));
        System.out.println(Calculator.expressionToRpn("4-5*(-2)"));
        System.out.println(Calculator.RpnToResult(Calculator.expressionToRpn("4-2*5")));
        System.out.println(4-2*5);

    }
}
