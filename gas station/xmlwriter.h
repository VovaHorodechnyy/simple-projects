#ifndef XMLWRITER_H
#define XMLWRITER_H

#include<QtXml/QDomDocument>
#include<QtXml/QDomElement>
#include<qfile.h>
#include<QTextStream>
class xmlwriter
{

public:
    xmlwriter()
    {



    }
    xmlwriter(const QString& data)
    {
        QDomDocument doc("gasStation");

        QDomElement doc_element=doc.createElement("total");
        doc.appendChild(doc_element);

        QDomElement doc_element_day=doc.createElement("this_day");
        doc_element.appendChild(doc_element_day);

        QDomText dom_text=doc.createTextNode(data);
        doc_element_day.appendChild(dom_text);

        QFile file("gasStation.xml");
        if(file.open(QIODevice::WriteOnly))
        {
            QTextStream(&file)<<doc.toString();
            file.close();
        }
    }




};

#endif // XMLWRITER_H
