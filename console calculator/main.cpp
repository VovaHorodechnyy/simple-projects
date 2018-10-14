#include <QCoreApplication>
#include<iostream>

int* calculate(int aAO,int aBO,char aOper);
void initialize(int &aAO,int &aBO,char &aOper);

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    int nAOper,nBOper;
    char oper;
    do
    {
        initialize(nAOper,nBOper,oper);
        auto res=calculate(nAOper,nBOper,oper);
        if(res==nullptr)
        {
            std::cout<<"try again"<<std::endl;
        }
        else
        {
          std::cout<<nAOper<<" + "<<nBOper<<" = "<< *res <<std::endl;
          std::cout<<"Try again?Yes(y) No(n) y/n:";
          std::cin>>oper;
        }

    }while(oper!='n');

    return a.exec();
}

int* calculate(int aAO,int aBO,char aOper)
{
    int* res=new int;
    switch (aOper) {
        case '+':
        {
            *res=aAO+aBO;
            break;
        }
        case '-':
        {
            *res=aAO-aBO;
            break;
        }
        case '*':
        {
            *res=aAO*aBO;
            break;
        }
        case '/':
        {
            if(aBO==0)
            {
              res=nullptr;
              break;
            }
            *res=aAO/aBO;
            break;
        }
        default:
            res=nullptr;
    }
    return res;

}
void initialize(int &aAO,int &aBO,char &aOper)
{
    std::cout<<"Enter first operand: ";
    std::cin>>aAO;
    std::cout<<"Enter second operand: ";
    std::cin>>aBO;
    std::cout<<"Enter operation(+,-,/,*): ";
    std::cin>>aOper;
}
