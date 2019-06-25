#include <QCoreApplication>
#include <QSet>
#include <QMultiMap>
#include <QTextStream>
#include <QMap>
#include <QJsonDocument>
#include <QDateTime>
#include <QJsonArray>
#include <QJsonObject>
#include <QDirIterator>
#include <QFileInfo>
#include <QList>
#include <QDir>
#include <iostream>
#include <string>
#include <algorithm>

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);


    std::cout << "Please use only \'/\' instead of \'\\\'"<<std::endl;
    QJsonObject json; // обєкт джсн я заповнюю його на 159 рядку
    QJsonArray arrayFiles; // не використовуэться
    QList<QString>listAllDir; // список в якому будуть всі директорії , починаючи від початкової яку вводить користувач до самої наглибшої
    ////////////////////////////
    QTextStream s(stdin);
    QString sPath = s.readLine(); //отримую директорію яку сканити
    /////////////////////////////

    QDirIterator it(sPath,QDir::Dirs ,QDirIterator::Subdirectories); // ітератор який рекрсивно дістає всі під папки

    /////////////////////////////////////////////////
    auto get_files_json_for_path = [&](QString & path) // лямбда алк можна вважати що це фун-ція , щоб вернути масив файлів по шляху
    {   QJsonArray array;
        QDirIterator iterator(path);
        while(iterator.hasNext())
        {
            QFileInfo info(iterator.next());///дозволяє витянути інфу про файл
            if(info.isFile())
            {
                QJsonObject file;
                file["Name"] = info.baseName();
                file["Size"] = QString::number(info.size()) + " B";//QString::number() це як + B C# , але на qt не можна int + string
                file["Path"] = info.path();
                array.push_back(file);
            }
        }
        return  array;


    };
    //////////////////////////////////////////////
    while (it.hasNext())
    {

        QFileInfo info(it.next());
        if(info.isDir())
        {
            ///// починаючи з qt 5.2 QDirIterator і QDir .Зустрічаються шлях типу D:/Core/. або D:/Core/.. я видаляю ці крапки
            QString tmp = it.next();
            QString path = tmp;
            if(tmp[tmp.size()-1]== '.')
            {
                int nLastSymbol = tmp.lastIndexOf('/');
                 path = tmp.remove(nLastSymbol,tmp.size() - nLastSymbol);
            }
            /////
            if(!listAllDir.contains(path))
            listAllDir.push_back(path);

        }


    }


    auto begin = listAllDir.begin(),end = listAllDir.end();

    QMap<QString,QJsonObject>mapObjs; // для кожної папки буде свій json object .На 2019 рік qt не дозволяэ змінювати масив який в обєкті json
    foreach (auto str, listAllDir)
    {
        // str - це шлях до папки
        QJsonObject obj;
        mapObjs[str] = obj;
        // оператор[] в QMap не має перевірки чи цей ключ вже існуэ .Тому відбудеться або зміна значення за ключе або створення цього ключа
    }

    for (int ix = listAllDir.size()-1;begin!=end;++begin)
    {
       // проходжусь по всім папкам ,і витягаю дані і файли ,які в них є
       QFileInfo info(*begin);
       mapObjs[*begin]["Name"] = info.baseName();
       mapObjs[*begin]["DateCreated"] = info.birthTime().toString();
       mapObjs[*begin]["Files"] = get_files_json_for_path(*begin);


    }

    auto is_subdir = [](QString dir,QString subdir) // ще одна лямбда, яка перевіряє чи ця папка є дочерня до батька
    {
        int pos = subdir.lastIndexOf('/');
        auto tmp = subdir.remove(pos,subdir.size()-pos);
        if (tmp == dir)
            return true;
        return false;
    };

    QMultiMap<QString,QString>multimapSubdirs; // ця колекція є для того ,щоб зберігати дочерні папки до дочерніх
                                                // ключ D:\avz4
                                                // значення D:\avz4\Base  і D:\avz4\LOG
    QString dir = *listAllDir.begin();
    auto listBegin = listAllDir.begin(),listEnd = listAllDir.end();
    ++listBegin; //
    for (;listBegin!=listEnd;++listBegin)
    {
        if(is_subdir(dir,*listBegin))
        {
            multimapSubdirs.insert(dir,*listBegin);
        }
        else
        {
            auto path = (*listBegin);
            int pos = path.lastIndexOf('/');
            auto tmp = path.remove(pos,path.size()-pos);

            dir = tmp;
            multimapSubdirs.insert(dir,*(listBegin));
        }
    }















    QList<QString>listFolders; // список в якому э всі(батьки) папки

    auto multimapBegin = multimapSubdirs.begin() , multimapEnd = multimapSubdirs.end() ;
    auto listAllFolders = multimapSubdirs.keys();
    for(auto fld : listAllFolders)
    {
        if(!listFolders.contains(fld))
            listFolders.push_front(fld);
    }
    std::reverse(listAllFolders.begin(),listAllFolders.end()); // роблю реверс ,щоб в подальшому не використовувати рекурсію, Скоріш за все через це в файлі спочатку йдуть діти , файли , час створення , імя
    for (auto fld : listAllFolders)
    {
        auto listSubdirs = multimapSubdirs.values(fld);

        QJsonArray array;
        for(int i=0;i<array.count();i++) {
            array.removeAt(i); // QJsonArray не має методу який видаляє всі елементи
        }
        for (auto fld : listSubdirs)
        {
            array.push_back(mapObjs[fld]);
        }
        mapObjs[fld]["Children"] = array; // fld це папка в яку добавляю масив папок
        json = mapObjs[fld]; // в результаті це буде початкова папка
    }

   // auto correctPath = sPath.re
   // json = mapObjs.value();














    //запис файла
    QFile saveFile(
           QStringLiteral("save.json")
          );
    saveFile.open(QIODevice::WriteOnly);

    QJsonDocument doc(json);
    saveFile.write(doc.toJson());
    saveFile.close();

    std::cout<<"END";
    return a.exec();

}
