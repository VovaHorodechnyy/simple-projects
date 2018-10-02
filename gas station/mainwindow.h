#ifndef MAINWINDOW_H
#define MAINWINDOW_H


#include <QMainWindow>


namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void on_comboBox_currentIndexChanged(const QString &arg1);

    void on_radioButton_2_clicked();

    void on_radioButton_clicked();

    void on_lineEdit_textChanged(const QString &arg1);



    void on_lineEdit_2_textChanged(const QString &arg1);

    void on_checkBox_clicked(bool checked);

    void on_checkBox_2_clicked(bool checked);

    void on_checkBox_3_clicked(bool checked);

    void on_checkBox_4_clicked(bool checked);

    void on_lineEdit_3_textChanged(const QString &arg1);

    void on_lineEdit_8_textChanged(const QString &arg1);

    void on_lineEdit_9_textChanged(const QString &arg1);

    void on_lineEdit_10_textChanged(const QString &arg1);

    void calculate();



    void on_pushButton_2_clicked();

    void on_pushButton_clicked();

private:
   int m_nMoney_from_all_day=0;
   int m_pay_all=0;
   int m_to_pay_fuel=0;
   int m_to_pay_fast_food=0;
   int m_to_pay_fast_hot=0;
   int m_to_pay_fast_gamburg=0;
   int m_to_pay_fast_water=0;
   int m_to_pay_fast_potato=0;
   bool m_money;
    Ui::MainWindow *ui;
};

#endif // MAINWINDOW_H
