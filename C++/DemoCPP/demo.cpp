#include <iostream>
#include <fstream>
using namespace std;
int main(){
    int num=5;
    float fl=3.14f;
    double dou=3.14;
    char letter='A';
    bool boolean=true;
    string s="Hello World";
    string s1=", Greetings";

    string line;
    ifstream input;
    input.open("/mnt/Data/gitpath",ios_base::in);

    while(getline(input,line)){
        cout<<line<<endl;
    }


}