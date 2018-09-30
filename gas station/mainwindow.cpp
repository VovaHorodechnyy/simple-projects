#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    QValidator *m_validator = new QIntValidator(1, 50, this);
QValidator *m_validator1 = new QIntValidator(1, 5000, this);
    ui->lineEdit->setValidator(m_validator);
     ui->lineEdit_2->setValidator(m_validator1);

}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_comboBox_currentIndexChanged(const QString &arg1)
{
    QString currenttext=arg1;

    if(currenttext=="A-76"){ui->lcdNumber->display(15);}
    if(currenttext=="A-98"){ui->lcdNumber->display(12.5);}
    if(currenttext=="A-99"){ui->lcdNumber->display(19.8);}
//    QMessageBox msgBox;
//   msgBox.setText(QString::number(ui->lcdNumber->value()));
//  msgBox.exec();
}

void MainWindow::on_radioButton_2_clicked()
{
    ui->lineEdit_2->setEnabled(true);
     ui->lineEdit->setEnabled(false);
      m_money=false;
      ui->label_6->setText("Litres");
}

void MainWindow::on_radioButton_clicked()
{
    ui->lineEdit_2->setEnabled(false);
     ui->lineEdit->setEnabled(true);
     m_money=true;
     ui->label_6->setText("$");
}

void MainWindow::on_lineEdit_textChanged(const QString &arg1)
{
m_to_pay_fuel=static_cast<int>(arg1.toInt()*ui->lcdNumber->value());
ui->lcdNumber_2->display(m_to_pay_fuel);
calculate();
}

void MainWindow::on_lineEdit_2_textChanged(const QString &arg1)
{
    m_to_pay_fuel=(arg1.toInt());
    int price_litre=static_cast<int>(ui->lcdNumber->value());
    ui->lcdNumber_2->display(m_to_pay_fuel/price_litre);
    calculate();
}

void MainWindow::on_checkBox_clicked(bool checked)
{

    (checked)?ui->lineEdit_3->setEnabled(true):ui->lineEdit_3->setEnabled(false);
    if(!checked)
    {
        m_to_pay_fast_hot =0;
        ui->lineEdit_3->setText("");
    }
 calculate();
//    QMessageBox msgBox;
//    const QVariant varValue(checked);
//    const QString strValue(varValue.toString());
//      msgBox.setText( strValue);
//     msgBox.exec();
}

void MainWindow::on_checkBox_2_clicked(bool checked)
{
     (checked)?ui->lineEdit_8->setEnabled(true):ui->lineEdit_8->setEnabled(false);
    if(!checked)
    {
        m_to_pay_fast_gamburg =0;
         ui->lineEdit_8->setText("");
    }
     calculate();
}

void MainWindow::on_checkBox_3_clicked(bool checked)
{
     (checked)?ui->lineEdit_9->setEnabled(true):ui->lineEdit_9->setEnabled(false);
    if(!checked)
    {
        m_to_pay_fast_potato =0;
          ui->lineEdit_9->setText("");
    }
     calculate();
}

void MainWindow::on_checkBox_4_clicked(bool checked)
{
     (checked)?ui->lineEdit_10->setEnabled(true):ui->lineEdit_10->setEnabled(false);
    if(!checked)
    {
        m_to_pay_fast_water =0;
          ui->lineEdit_10->setText("");
    }
     calculate();
}

void MainWindow::on_lineEdit_3_textChanged(const QString &arg1)
{
     m_to_pay_fast_hot=arg1.toInt()*ui->lineEdit_4->text().toInt();
     calculate();

}

void MainWindow::on_lineEdit_8_textChanged(const QString &arg1)
{
    m_to_pay_fast_gamburg=arg1.toInt()*ui->lineEdit_5->text().toInt();
    calculate();
}

void MainWindow::on_lineEdit_9_textChanged(const QString &arg1)
{
     m_to_pay_fast_potato=arg1.toInt()*ui->lineEdit_6->text().toInt();
     calculate();
}

void MainWindow::on_lineEdit_10_textChanged(const QString &arg1)
{
    m_to_pay_fast_water=arg1.toInt()*ui->lineEdit_7->text().toInt();
    calculate();
}

void MainWindow::calculate()
{
    m_to_pay_fast_food=0;
    if(ui->checkBox->isChecked())
    {
        m_to_pay_fast_food+=m_to_pay_fast_hot;
    }
    if(ui->checkBox_2->isChecked())
    {
        m_to_pay_fast_food+=m_to_pay_fast_gamburg;
    }
    if(ui->checkBox_3->isChecked())
    {
        m_to_pay_fast_food+=m_to_pay_fast_potato;
    }
    if(ui->checkBox_3->isChecked())
    {
        m_to_pay_fast_food+=m_to_pay_fast_water;
    }
     ui->lcdNumber_3->display(m_to_pay_fast_food);
     m_pay_all=static_cast<int>( m_to_pay_fuel+ui->lcdNumber_3->value());
     ui->lcdNumber_4->display( m_to_pay_fuel+ui->lcdNumber_3->value());
}


void MainWindow::on_pushButton_2_clicked()
{
    QVariant varValue(m_pay_all);
     QString strValue(varValue.toString());

xmlwriter q(strValue);


}
