package com.example.volodya.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    double m_dblSumInMemory;

    double m_dblSumSoFar;

    double m_dblFactorSoFar;

    String m_sPendingAdditiveOperator;

    String m_sPendingMultiplicativeOperator;

    Boolean m_fWaitingForOperand;

    EditText m_pDisplay ;

    public void pointClicked(View view)
    {
        if (m_fWaitingForOperand)
            m_pDisplay.setText("0");
        if (!m_pDisplay.getText().toString().contains("."))
            m_pDisplay.setText(m_pDisplay.getText().toString() + ".");
        m_fWaitingForOperand = false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_pDisplay = (EditText)findViewById(R.id.display);
        m_sPendingAdditiveOperator= new String();
        m_sPendingMultiplicativeOperator= new String();
        m_dblSumInMemory = 0.0;
        m_dblSumSoFar = 0.0;
        m_dblFactorSoFar = 0.0;
        m_fWaitingForOperand = true;
    }
    public void clear(View view)
    {
        if (m_fWaitingForOperand)
            return;

        m_pDisplay.setText("0");
        m_fWaitingForOperand = true;
    }
    public void backspaceClicked(View view)
    {
        if (m_fWaitingForOperand)
            return;

        String text = m_pDisplay.getText().toString();
        text = text.substring(0, text.length() - 1);
        if (text.isEmpty()) {
            text = "0";
            m_fWaitingForOperand = true;
        }
        m_pDisplay.setText(text);
    }
    public void clearAll(View view)
    {
        m_dblSumSoFar = 0.0;
        m_dblFactorSoFar = 0.0;
        m_sPendingAdditiveOperator="";
        m_sPendingMultiplicativeOperator="";
        m_pDisplay.setText("0");
        m_fWaitingForOperand = true;
    }
    public void clearAll_()
    {
        m_dblSumSoFar = 0.0;
        m_dblFactorSoFar = 0.0;
        m_sPendingAdditiveOperator="";
        m_sPendingMultiplicativeOperator="";
        m_pDisplay.setText("0");
        m_fWaitingForOperand = true;
    }
    public void clearMemory(View v)
    {
        m_dblSumInMemory = 0.0;
    }
    public void readMemory(View v)
    {
        m_pDisplay.setText(m_dblSumInMemory+"", TextView.BufferType.EDITABLE);
        m_fWaitingForOperand = true;
    }
    public void changeSignClicked(View v)
    {
        String text = m_pDisplay.getText().toString();
        double value = Double.parseDouble(text);

        if (value > 0.0) {

            text="-"+text;
        } else if (value < 0.0) {
            text=removeCharAt(text,0);
        }
        m_pDisplay.setText(text);
    }
    public void abortOperation()
    {
        clearAll_();
        m_pDisplay.setText("");
    }
    public Boolean calculate(double rightOperand,  String pendingOperator)
    {
        if (pendingOperator.equals("+")) {
            m_dblSumSoFar += rightOperand;
        } else if (pendingOperator.equals ("-")) {
            m_dblSumSoFar -= rightOperand;
        } else if (pendingOperator.equals("*")) {
            m_dblFactorSoFar *= rightOperand;
        } else if (pendingOperator.equals("/")) {
            if (rightOperand == 0.0)
                return false;
            m_dblFactorSoFar /= rightOperand;
        }
        return true;
    }
    public void equalClicked(View view)
    {
        double operand = Double.parseDouble(m_pDisplay.getText().toString());

        if (!m_sPendingMultiplicativeOperator.isEmpty()) {
            if (!calculate(operand, m_sPendingMultiplicativeOperator)) {
                abortOperation();
                return;
            }
            operand = m_dblFactorSoFar;
            m_dblFactorSoFar = 0.0;
            m_sPendingMultiplicativeOperator="";
        }
        if (!m_sPendingAdditiveOperator.isEmpty()) {
            if (!calculate(operand, m_sPendingAdditiveOperator)) {
                abortOperation();
                return;
            }
            m_sPendingAdditiveOperator="";
        } else {
            m_dblSumSoFar = operand;
        }

        m_pDisplay.setText(m_dblSumSoFar+"");
        m_dblSumSoFar = 0.0;
        m_fWaitingForOperand = true;
    }
    public void digitClicked(View view)
    {
        Button clickedButton = (Button)view;
        int digitValue = Integer.parseInt(clickedButton.getText().toString());
        if (m_pDisplay.getText().toString().equals("0") && digitValue == 0.0)
            return;

        if (m_fWaitingForOperand) {
            m_pDisplay.setText("");
            m_fWaitingForOperand = false;
        }
        m_pDisplay.setText(m_pDisplay.getText().toString() + String.valueOf(digitValue));
    }
    public void unaryOperatorClicked(View view)
    {
        Button clickedButton = (Button)view;
        String clickedOperator = clickedButton.getText().toString();
        double operand = Double.parseDouble(m_pDisplay.getText().toString());
        double result = 0.0;

        if (clickedOperator.equals("Sqrt")) {
            if (operand < 0.0) {
                abortOperation();
                return;
            }
            result = Math.sqrt(operand);
        } else if (clickedOperator.equals("X2")) {
            result = Math.pow(operand, 2.0);
        } else if (clickedOperator.equals("1/x")) {
            if (operand == 0.0) {
                abortOperation();
                return;
            }
            result = 1.0 / operand;
        }
        m_pDisplay.setText(String.valueOf(result));
        m_fWaitingForOperand = true;
    }

    public void additiveOperatorClicked(View view)

    {
        Button clickedButton = (Button)view;
        String clickedOperator = clickedButton.getText().toString();
        double operand = Double.parseDouble(m_pDisplay.getText().toString());


        if (!m_sPendingMultiplicativeOperator.isEmpty()) {

            if (!calculate(operand, m_sPendingMultiplicativeOperator)) {
                abortOperation();
                return;
            }
            m_pDisplay.setText(String.valueOf(m_dblFactorSoFar));
            operand = m_dblFactorSoFar;
            m_dblFactorSoFar = 0.0;
            m_sPendingMultiplicativeOperator="";
        }


        if (!m_sPendingAdditiveOperator.isEmpty()) {

            if (!calculate(operand, m_sPendingAdditiveOperator)) {
                abortOperation();
                return;
            }
            m_pDisplay.setText(String.valueOf(m_dblSumSoFar));
        } else {
            m_dblSumSoFar = operand;
        }


        m_sPendingAdditiveOperator = clickedOperator;

        m_fWaitingForOperand = true;
    }

    public void multiplicativeOperatorClicked(View view)
    {
        Button clickedButton = (Button)view;
        String clickedOperator = clickedButton.getText().toString();
        double operand = Double.parseDouble(m_pDisplay.getText().toString());

        if (!m_sPendingMultiplicativeOperator.isEmpty()) {
            if (!calculate(operand, m_sPendingMultiplicativeOperator)) {
                abortOperation();
                return;
            }
            m_pDisplay.setText(String.valueOf(m_dblFactorSoFar));
        } else {
            m_dblFactorSoFar = operand;
        }

        m_sPendingMultiplicativeOperator = clickedOperator;
        m_fWaitingForOperand = true;
    }
    public void setMemory(View view)
    {
        equalClicked(view);
        m_dblSumInMemory = Double.parseDouble(m_pDisplay.getText().toString());
    }

    public void addToMemory(View view)
    {
        equalClicked(view);
        m_dblSumInMemory = Double.parseDouble(m_pDisplay.getText().toString());
    }
    public  String removeCharAt(String s, int pos)
    {
        return s.substring(0, pos) + s.substring(pos + 1); // Возвращаем подстроку s, которая начиная с нулевой позиции переданной строки (0) и заканчивается позицией символа (pos), который мы хотим удалить, соединенную с другой подстрокой s, которая начинается со следующей позиции после позиции символа (pos + 1), который мы удаляем, и заканчивается последней позицией переданной строки.
    }
}
