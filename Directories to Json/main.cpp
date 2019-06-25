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
    QJsonObject json;
    QJsonArray arrayFiles;
    QList<QString>listAllDir;
    ////////////////////////////
    QTextStream s(stdin);
    QString sPath = s.readLine();
    /////////////////////////////

    QDirIterator it(sPath,QDir::Dirs ,QDirIterator::Subdirectories);

    /////////////////////////////////////////////////
    auto get_files_json_for_path = [&](QString & path)
    {   QJsonArray array;
        QDirIterator iterator(path);
        while(iterator.hasNext())
        {
            QFileInfo info(iterator.next());
            if(info.isFile())
            {
                QJsonObject file;
                file["Name"] = info.baseName();
                file["Size"] = QString::number(info.size()) + " B";
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

            QString tmp = it.next();
            QString path = tmp;
            if(tmp[tmp.size()-1]== '.')
            {
                int nLastSymbol = tmp.lastIndexOf('/');
                 path = tmp.remove(nLastSymbol,tmp.size() - nLastSymbol);
            }

            if(!listAllDir.contains(path))
            listAllDir.push_back(path);

        }


    }


    auto begin = listAllDir.begin(),end = listAllDir.end();

    QMap<QString,QJsonObject>mapObjs;
    foreach (auto str, listAllDir)
    {

        QJsonObject obj;
        mapObjs[str] = obj;

    }

    for (int ix = listAllDir.size()-1;begin!=end;++begin)
    {

       QFileInfo info(*begin);
       mapObjs[*begin]["Name"] = info.baseName();
       mapObjs[*begin]["DateCreated"] = info.birthTime().toString();
       mapObjs[*begin]["Files"] = get_files_json_for_path(*begin);


    }

    auto is_subdir = [](QString dir,QString subdir)
    {
        int pos = subdir.lastIndexOf('/');
        auto tmp = subdir.remove(pos,subdir.size()-pos);
        if (tmp == dir)
            return true;
        return false;
    };

    QMultiMap<QString,QString>multimapSubdirs;
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















    QList<QString>listFolders;

    auto multimapBegin = multimapSubdirs.begin() , multimapEnd = multimapSubdirs.end() ;
    auto listAllFolders = multimapSubdirs.keys();
    for(auto fld : listAllFolders)
    {
        if(!listFolders.contains(fld))
            listFolders.push_front(fld);
    }
    std::reverse(listAllFolders.begin(),listAllFolders.end());
    for (auto fld : listAllFolders)
    {
        auto listSubdirs = multimapSubdirs.values(fld);

        QJsonArray array;
        for(int i=0;i<array.count();i++) {
            array.removeAt(i);
        }
        for (auto fld : listSubdirs)
        {
            array.push_back(mapObjs[fld]);
        }
        mapObjs[fld]["Children"] = array;
        json = mapObjs[fld];
    }

















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
