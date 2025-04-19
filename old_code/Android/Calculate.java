package com.example.calculador_resistencias;

import android.annotation.SuppressLint;

import java.util.regex.Pattern;

public class Calculate {
    private float Voltage;
    private float Capacitance;
    private float Resistance;

    private String Prefix_Voltage;
    private String Prefix_Capacitance;
    private String Prefix_Resistance;
    private float Power;
    private float Energy;
    private float Tau;
    private float Tau5;
    public String Id;
    private String Prefix_Power;
    private String Unit_Label;
    private float Value_Power;
    private String Recommended_Power;

    public Calculate(String Id) {
        this.Id = Id;
    }

    public void Set_Prefix_Voltage(String P) { this.Prefix_Voltage = P;}
    public void Set_Prefix_Capacitance(String P)
    {
        this.Prefix_Capacitance = P;
    }
    public void Set_Prefix_Resistance(String P)
    {
        this.Prefix_Resistance = P;
    }

    private void Set_Voltage(float Unit)
    {
        float Multiplier = 1;
        if(this.Prefix_Voltage.equals("kV")) {Multiplier = (float) 1e3 ;}
        if(this.Prefix_Voltage.equals("mV")) {Multiplier = (float) 1e-3;}
        if(this.Prefix_Voltage.equals("uV")) {Multiplier = (float) 1e-6;}
        Voltage = Unit * Multiplier;
    }

    private void Set_Resistance(float Unit)
    {
        float Multiplier = 1;
        if(this.Prefix_Resistance.equals("MOhms")) {Multiplier = (float) 1e6 ;}
        if(this.Prefix_Resistance.equals("kOhms")) {Multiplier = (float) 1e3;}
        Resistance = Unit * Multiplier;
    }

    private void Set_Capacitance(float Unit)
    {
        float Multiplier = 1;
        if(this.Prefix_Capacitance.equals("mF")) {Multiplier = (float) 1e-3 ;}
        if(this.Prefix_Capacitance.equals("uF")) {Multiplier = (float) 1e-6;}
        if(this.Prefix_Capacitance.equals("nF")) {Multiplier = (float) 1e-9;}
        if(this.Prefix_Capacitance.equals("pF")) {Multiplier = (float) 1e-12;}
        Capacitance = Unit * Multiplier;
    }

    private String Set_Commercial_Power(float Value)
    {
        String Commercial_Power;
        Prefix_Power = "W";
        if(Value <= 83.3e-3)
        {
            Commercial_Power = "1/8";
        }
        else if(Value <= 166e-3 && Value > 83.3e-3)
        {
            Commercial_Power = "1/4";
        }
        else if(Value <= 333e-3  && Value > 166e-3)
        {
            Commercial_Power = "1/2";
        }
        else if(Value <= 666e-3 && Value > 333e-3)
        {
            Commercial_Power = "1";
        }
        else
            {
                Commercial_Power = String.valueOf(Value);
                Prefix_Power = Unit_Label;
            }
        return Commercial_Power;
    }

    private void Set_Power()
    {
        if(Resistance != 0) {Power = (float) (Math.pow(Voltage,2) / Resistance);}
    }

    public String Get_Prefix_Power(){ return Prefix_Power;}

    public String Get_Value_Power()
    {
        String Value_Power_String;
        if(Pattern.compile("/").matcher(Recommended_Power).find() || Recommended_Power == "1" )
        {
            Value_Power_String = Recommended_Power;
        }
        else
            {
                if(Pattern.compile(".0", Pattern.LITERAL).matcher(Recommended_Power).find())
                {
                    Value_Power_String = String.format( "%.0f", Value_Power);
                }
                else{
                Value_Power_String = String.format( "%.1f", Value_Power);
                }
            }
        return Value_Power_String;
    }

    @SuppressLint("DefaultLocale")
    public String Get_Value(String Label)
    {
        String StringValue;
        Unit_Label = Label;
        float Selected_Unit = 0;
        float Buffer_Selected_Unit = 0;
        float Multiplier = 1;

        if(Unit_Label.equals("W")) {Selected_Unit = Power;}
        if(Unit_Label.equals("J")) {Selected_Unit = Energy;}
        if(Unit_Label.equals("T")) {Selected_Unit = Tau; Unit_Label = "s";}
        if(Unit_Label.equals("T5")) {Selected_Unit = Tau5; Unit_Label = "s";}

        if(Resistance != 0)
        {
            if(Selected_Unit < 1e-6 && Selected_Unit >= 1e-9){Unit_Label = "n"      +Unit_Label;Multiplier = (float) 1e9;}
            else if(Selected_Unit < 1e-3 && Selected_Unit >= 1e-6){Unit_Label = "u" +Unit_Label;Multiplier = (float) 1e6;}
            else if(Selected_Unit < 1    && Selected_Unit >= 1e-3){Unit_Label = "m" +Unit_Label;Multiplier = (float) 1e3;}
            else if(Selected_Unit < 1e6  && Selected_Unit >= 1e3) {Unit_Label = "k" +Unit_Label;Multiplier = (float) 1e-3;}
            else if(Selected_Unit < 1e9  && Selected_Unit >= 1e6) {Unit_Label = "M" +Unit_Label;Multiplier = (float) 1e-6;}

            Buffer_Selected_Unit = Selected_Unit;
            Selected_Unit = Selected_Unit * Multiplier;
            StringValue = String.format("%.4f", Selected_Unit) + " " +Unit_Label;
        }
        else
        {
            StringValue = "Invalid Resistance";
        }

       //Calculate the commercial and recommended power value of resistor
        if(Pattern.compile("W$").matcher(Unit_Label).find())
        {
            Value_Power = (float) Math.ceil(Selected_Unit * 1.5);
            Recommended_Power =  Set_Commercial_Power(Buffer_Selected_Unit);
            System.out.println(Recommended_Power);
        }
        return StringValue;
    }

    private void Set_Energy()
    {
        Energy = (float) (Math.pow(Voltage,2)*Capacitance / 2);
    }

    private void Set_Tau()
    {
        Tau = Resistance * Capacitance;
        Tau5 = Tau *5;
    }


    public void Calculate_All(float Unit_Voltage, float Unit_Capacitance, float Unit_Resistance)
    {
        Set_Voltage(Unit_Voltage);
        Set_Capacitance(Unit_Capacitance);
        Set_Resistance(Unit_Resistance);

        Set_Tau();
        Set_Power();
        Set_Energy();
    }
}