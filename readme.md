Cofee Machine Design

Classes - 
1.    coffeeMachine.Beverage{
        Name;
        prepTime;
        ingredient List;
        getBeverage();
        }

2.    Ingredient{
      var:
        Name;
        Amount;
      methods:
        getRefill();
        checkAmount();
        reduceAmount();  
        }
        
3.    Outlet implements runnable{
        for running outlets in different threads.
        }
4.    Runner{
        Map<String, coffeeMachine.Beverage> name, beverage  initialize from json object
        map<name, ingredient> name, ingredient  initialize from json object   
        }
5.    Printer{
        notify(); to print the notification in synchronized way top avoid dirty writes

        }
6.    Store{
        storeState();   to store the state of machine in case of any failure store the remaining ingredient in json file which can be read after restart
        writeLog();     write the orders which are completed and store in a log file 
        }
