Cofee Machine Design

Classes - 
1.    coffeeMachine.Beverage{
        Name;
        prepTime;
        ingredient List;
        getBeverage();
        }

2.    Ingredient{
        Name;
        Amount;
        getRefill();
        showNotification();
        getAmount();  
        }
        
3.    Outlet implements runnable{
        for running outlets in different threads.
        }
4.    Runner{
        Map<String, coffeeMachine.Beverage> name, beverage  initialize from json object
        map<name, ingredient> name, ingredient  initialize from json object   
        }