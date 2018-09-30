#include <QApplication>
#include <QMessageBox>
#include<QtXml>
#include"xmlreader.h"




#include "mainwindow.h"
\

int main(int argc, char *argv[])
{

    QApplication a(argc, argv);
    MainWindow w;
    w.show();
    XmlReader d;
    QFile file("gasStation.xml");

\



    return a.exec();
}
