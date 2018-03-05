package com.amar.itay.takego.model.backend;


import com.amar.itay.takego.model.datasource.List_DBManager;
import com.amar.itay.takego.model.datasource.MySQL_DBManager;

/**
 * Created by david salm on on 11/3/2017.
 */

/**
 * this class with the one function insure us to work with one data base manager that handle all out functions.
 */
public class FactoryMethod {
    static DB_manager manager = null ;

    public static DB_manager getManager() {
        if (manager == null)
            //manager = new List_DBManager();
        manager = new MySQL_DBManager();
        return manager;
    }
}
